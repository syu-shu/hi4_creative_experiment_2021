import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public final class GeneticAlgorithm {
    public static final int ELITE_COUNT = 5; // x / 100
    public static final int INTERSECTION_PROBABILITY = 80;  // x / 100

    private List<Gtype> gtypes;
    private int maxGeneration;
    private int geneCount;
    private int generation;
    private boolean isSorted;
    private int maxScore;
    private int limitCost;
    private int geneLength;

    public GeneticAlgorithm (int maxGeneration, int geneCount, int geneLength, int limitCost) {
        gtypes = new ArrayList<Gtype>(geneCount);
        this.geneCount = geneCount;
        this.maxGeneration = maxGeneration;
        for (int i = 0; i < geneCount; i++) {
            gtypes.add(new Gtype(geneLength));
        }
        this.geneLength = geneLength;
        generation = 0;
        isSorted = false;
        maxScore = -1;
        this.limitCost = limitCost;
    }

    public void sortByScore (Baggage baggage) {
        if (isSorted) return;
        gtypes.sort((a, b) -> b.getScore(baggage, limitCost) - a.getScore(baggage, limitCost));
        isSorted = true;
    }

    public double getMaxScoreBySort (Baggage baggage) {
        if (isSorted) {
            return maxScore = gtypes.get(0).getScore(baggage, limitCost);
        }
        gtypes.sort((a, b) -> b.getScore(baggage, limitCost) - a.getScore(baggage, limitCost));
        return maxScore = gtypes.get(0).getScore(baggage, limitCost);
    }

    public double getMaxScore (Baggage baggage) {
        int temp = 0;
        for (Gtype gtype : gtypes) {
            int score = gtype.getScore(baggage, limitCost);
            if (score > temp) temp = score;
        }
        return temp;
    }

    public void setNewGenerationByElite (Baggage baggage) {
        sortByScore(baggage);
        int elite = geneCount * ELITE_COUNT / 100;
        int notElite = geneCount - elite;
        Random random = new Random();
        List<Gtype> parents = new ArrayList<>();
        for (int i = 0; i < notElite; i++) {
            int a = random.nextInt(geneCount);
            int b = random.nextInt(geneCount);
            parents.add(gtypes.get(a < b ? a : b));
        }
        List<Gtype> newGenerations = new ArrayList<>();
        for (int i = 0; i < elite; i++) {
            newGenerations.add(gtypes.get(i));
        }
        for (int i = 0; i < notElite; i++) {
            if (i < notElite - 1 && random.nextInt(100) < INTERSECTION_PROBABILITY) {
                Map<Integer, Gtype> map = Gtype.intersection(parents.get(i), parents.get(i + 1));
                i++;
                newGenerations.add(map.get(0));
                newGenerations.add(map.get(1));
                map.clear();
            }
            else {
                newGenerations.add(parents.get(i).mutation(100));
                // newGenerations.add(parents.get(i).mutation(2));
            }
        }
        gtypes.clear();
        gtypes = newGenerations;
        isSorted = false;
    }
    /*
    // public void setNewGenerationByElite2 (Baggage baggage) {
    //     Random random = new Random();
    //     List<Gtype> parents = new ArrayList<>();
    //     while (parents.size() <= geneCount - ELITE_COUNT) {
    //         Gtype a = gtypes.get(random.nextInt(geneCount));
    //         Gtype b = gtypes.get(random.nextInt(geneCount));
    //         parents.add(a.getScore(baggage, limitCost) > b.getScore(baggage, limitCost) ? a : b);
    //     }
    //     List<Gtype> newGenerations = new ArrayList<>();
    //     for (int i = 0; i < geneCount * ELITE_COUNT / 100; i++) {
    //         int limit = newGenerations.isEmpty() ? (int)1e8 : newGenerations.get(newGenerations.size() - 1).getScore(baggage, limitCost);
    //         int max = 0;
    //         int index = 0;
    //         for (int j = 0; j < geneCount; j++) {
    //             Gtype gtype = gtypes.get(j);
    //             if (max < gtype.getScore(baggage, limitCost) && gtype.getScore(baggage, limitCost) < limit) {
    //                 index = j;
    //             }
    //         }
    //         newGenerations.add(gtypes.get(index));
    //     }
    //     for (int i = 0; i < geneCount - ELITE_COUNT; i++) {
    //         if (i < geneCount - 2 && random.nextInt(100) < INTERSECTION_PROBABILITY) {
    //             Map<Integer, Gtype> map = Gtype.intersection(parents.get(i), parents.get(i + 1));
    //             i++;
    //             newGenerations.add(map.get(0));
    //             newGenerations.add(map.get(1));
    //             map.clear();
    //         }
    //         else {
    //             newGenerations.add(parents.get(i).mutation());
    //             // newGenerations.add(parents.get(i).mutation(2));
    //         }
    //     }
    //     gtypes.clear();
    //     gtypes = newGenerations;
    //     isSorted = false;
    // }
    */

    public void setNewGeneration (Baggage baggage) {
        Random random = new Random();
        List<Gtype> parents = new ArrayList<>();
        for (int i = 0; i < geneCount; i++) {
            Gtype a = gtypes.get(random.nextInt(geneCount));
            Gtype b = gtypes.get(random.nextInt(geneCount));
            parents.add(a.getScore(baggage, limitCost) > b.getScore(baggage, limitCost) ? a : b);
        }
        List<Gtype> newGenerations = new ArrayList<>();
        for (int i = 0; i < geneCount - 1; i++) {
            if (i < geneCount - 2 && random.nextInt(100) < INTERSECTION_PROBABILITY) {
                Map<Integer, Gtype> map = Gtype.intersection(parents.get(i), parents.get(i + 1));
                i++;
                newGenerations.add(map.get(0));
                newGenerations.add(map.get(1));
                map.clear();
            }
            else {
                newGenerations.add(parents.get(i).mutation());
                // newGenerations.add(parents.get(i).mutation((int)(2 + (geneLength - 2) * (double)(generation) / maxGeneration)));
                // newGenerations.add(parents.get(i).mutation(2));
            }
        }
        gtypes.clear();
        gtypes = newGenerations;
        isSorted = false;
    }

    public void calculate (Baggage baggage) {
        for (int generation = 0; generation < maxGeneration; generation++) {
            // System.out.println(getMaxScore(baggage));
            // setNewGeneration(baggage);
            setNewGenerationByElite(baggage);
        }
        System.out.println(getMaxScoreBySort(baggage));
        // for (Gtype gtype : gtypes) {
        //     System.out.println(gtype.getScore(baggage, limitCost));
        // }
    }

    public void calculate (Baggage baggage, int answer) throws Exception{
        String fileName = "./results/" + geneCount + "/" + baggage.getSize() + "/" + System.currentTimeMillis();
        BufferedWriter bWriter = new BufferedWriter(new FileWriter(fileName));
        int generation = 0;
        bWriter.write(generation + " " + this.getMaxScore(baggage) + "\n");
        for (generation = 0; this.getMaxScore(baggage) < answer; generation++) {
            setNewGenerationByElite(baggage);
            bWriter.write((generation + 1) + " " + this.getMaxScore(baggage) + "\n");
            // System.out.println((generation + 1) + " " + this.getMaxScore(baggage) + "\n");
        }
        bWriter.close();
        System.out.println(getMaxScoreBySort(baggage));
        System.out.println(generation);
    }
}
