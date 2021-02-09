class Solution {
    public int minCost(int n, int[] c) {
        int[] cuts = new int[n + 2];
        cuts[0] = 0;
        cuts[n + 1] = n;
        for (int i = 1; i <= n; i++)
            cuts[i] = c[i - 1];

        int m = cuts.length;
        int[][] dp = new int[m][m];
        for (int length = 3; length <= n; length++) {
            for (int left = 0; left <= m - length; left++) {
                int right = left + length - 1;
                int tmp = Integer.MAX_VALUE;
                for (int i = left + 1; left <= right - 1; left++) {
                    tmp = Math.min(tmp, cuts[right] - cuts[left] + dp[left][i - 1] + dp[i + 1][right]);
                }
                dp[left][right] = tmp;
            }
        }
        return dp[1][n];
    }

}