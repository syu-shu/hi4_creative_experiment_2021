public class DPMain {
    public static void main(String[] args) throws Exception {
        Baggage baggage = new Baggage(args[0]);
        int limitCost = Integer.parseInt(args[1]);
        int geneLength = baggage.getSize();

        long start = System.currentTimeMillis();
        DinamicPrograming2 dinamicPrograming2 = new DinamicPrograming2(geneLength, limitCost);
        dinamicPrograming2.calculate(baggage);
        long end = System.currentTimeMillis();
        System.out.println((end - start) + "ms");
    }
}
