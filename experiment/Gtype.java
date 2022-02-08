import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public final class Gtype {
    private List<Boolean> gene;
    private int score;

    public Gtype (int knapsackSize) {
        gene = new ArrayList<Boolean>(knapsackSize);
        Random random = new Random();
        for (int i = 0; i < knapsackSize; i++) {
            gene.add(random.nextBoolean());
        }
        score = -1;
    }

    public Gtype (List<Boolean> gene1, List<Boolean> gene2, int knapsackSize) {
        gene = new ArrayList<Boolean>(knapsackSize);
        gene.addAll(gene1);
        gene.addAll(gene2);
        score = -1;
    }

    public int getGeneLength () {
        return gene.size();
    }

    public List<Boolean> subGene (int fromIndex, int toIndex) {
        return gene.subList(fromIndex, toIndex);
    }

    public int getScore (Baggage baggage, int limitCost) {
        if (this.score >= 0) {
            return this.score;
        }
        int value = 0;
        int cost = 0;
        for (int i = 0; i < gene.size(); i++) {
            if (!gene.get(i)) continue;
            cost += baggage.costs.get(i);
            value += baggage.values.get(i);
            if (cost > limitCost) {
                this.score = 0;
                return 0;
            }
        }
        this.score = value;
        return value;
    }

    public Gtype mutation () {
        int knapsackSize = gene.size();
        Random random = new Random();
        boolean isMutated = false;
        for (int i = 0; i < knapsackSize; i++) {
            if (random.nextInt() % knapsackSize == 0) {
                isMutated = true;
                gene.set(i, !gene.get(i));
            }
        }
        this.score = -1;
        return this;
    }

    public Gtype mutation (int mutationProbably) {
        int knapsackSize = gene.size();
        Random random = new Random();
        boolean isMutated = false;
        for (int i = 0; i < knapsackSize; i++) {
            if (random.nextInt() % mutationProbably == 0) {
                isMutated = true;
                gene.set(i, !gene.get(i));
            }
        }
        this.score = -1;
        return this;
    }

    public static Map<Integer, Gtype> intersection (Gtype gtype1, Gtype gtype2) {
        Random random = new Random();
        int intersectionIndex = random.nextInt(gtype1.getGeneLength());
        Map<Integer, Gtype> map = new HashMap<>();
        map.put(0,
            new Gtype(gtype1.subGene(0, intersectionIndex),
                gtype2.subGene(intersectionIndex, gtype2.getGeneLength()),
                gtype1.getGeneLength()));
        map.put(1,
            new Gtype(gtype2.subGene(0, intersectionIndex),
                gtype1.subGene(intersectionIndex, gtype1.getGeneLength()),
                gtype1.getGeneLength()));
        return map;
    }
}
