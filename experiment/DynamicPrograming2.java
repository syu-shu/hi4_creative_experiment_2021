public final class DynamicPrograming2 {
    private int[][] dp;
    private int baggageCount;
    private int limitCost;

    public DynamicPrograming2(int baggageCount, int limitCost) {
        this.baggageCount = baggageCount;
        this.limitCost = limitCost;
        dp = new int[2][limitCost + 1];
    }

    public void calculate(Baggage baggage) {
        for (int i = 0; i < baggageCount; i++) {
            int value = baggage.values.get(i);
            int cost = baggage.costs.get(i);
            for (int j = 0; j < limitCost + 1; j++) {
                if (j - cost >= 0 && dp[i % 2][j] < dp[i % 2][j - cost] + value) {
                    dp[(i + 1) % 2][j] = dp[i % 2][j - cost] + value;
                } else {
                    dp[(i + 1) % 2][j] = dp[i % 2][j];
                }
            }
        }
        System.out.println(dp[baggageCount % 2][limitCost]);
    }
}
