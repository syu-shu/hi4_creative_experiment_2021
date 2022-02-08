public class GAMain {
    public static void main(String[] args) throws Exception {
        Baggage baggage = new Baggage(args[0]);
        int limitCost = Integer.parseInt(args[1]);
        int answer = Integer.parseInt(args[2]);
        int maxGeneration = 1000;
        int geneCount = 1000;

        long start = System.currentTimeMillis();
        GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm(maxGeneration, geneCount, baggage.getSize(),
                limitCost);
        geneticAlgorithm.calculate(baggage, answer);
        long end = System.currentTimeMillis();
        System.out.println((end - start) + "ms");
    }
}
