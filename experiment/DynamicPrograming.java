public class DinamicPrograming {
    private int[][] dp;
    private int baggageCount;
    private int limitCost;

    public DinamicPrograming (int baggageCount, int limitCost) {
        this.baggageCount = baggageCount;
        this.limitCost = limitCost;
        dp = new int[baggageCount + 1][limitCost + 1];
        dp[0][0] = 0;
    }

    public void calculate (Baggage baggage) {
        for (int i = 0; i < baggageCount; i++) {
            int value = baggage.values.get(i);
            int cost = baggage.costs.get(i);
            for (int j = 0; j < limitCost + 1; j++) {
                if (j - cost >= 0 && dp[i][j] < dp[i][j - cost] + value) {
                    dp[i + 1][j] = dp[i][j - cost] + value;
                }
                else {
                    dp[i + 1][j] = dp[i][j];
                }
            }
        }
        System.out.println(dp[baggageCount][limitCost]);
    }
}
