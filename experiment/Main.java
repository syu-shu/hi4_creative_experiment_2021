public class Main {
    public static void main(String[] args) throws Exception {
        int geneLength =3000;
        int maxCost = 3000;
        int maxValue = 100;
        int limitCost = 2700000;
        int maxGeneration = 100;
        int geneCount = 100;
        Baggage baggage = new Baggage(geneLength, maxCost, maxValue);

        long start = System.currentTimeMillis();
        GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm(maxGeneration, geneCount, geneLength, limitCost);
        geneticAlgorithm.calculate(baggage);
        long end = System.currentTimeMillis();
        System.out.println((end - start) + "ms");
        geneticAlgorithm = null;
        System.out.println("---");
        start = System.currentTimeMillis();
        DinamicPrograming2 dinamicPrograming2 = new DinamicPrograming2(geneLength, limitCost);
        dinamicPrograming2.calculate(baggage);
        end = System.currentTimeMillis();
        System.out.println((end - start) + "ms");
    }
}