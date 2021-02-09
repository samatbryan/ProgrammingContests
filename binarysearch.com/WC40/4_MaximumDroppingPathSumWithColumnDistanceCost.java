import java.util.*;

class Solution {
    public int solve(int[][] matrix) {
        int N = matrix.length;
        int M = matrix[0].length;
        int NEGINF = Integer.MIN_VALUE;
        int[][] dp = new int[N][M];
        for (int j = 0; j < M; j++) {
            dp[0][j] = matrix[0][j];
        }

        for (int i = 1; i < N; i++) {
            int[] right = new int[M + 1];
            right[M] = NEGINF;
            for (int j = M - 1; j >= 0; j--)
                right[j] = Math.max(right[j + 1], dp[i - 1][j] - j);
            int left = NEGINF;
            for (int j = 0; j < M; j++) {
                left = Math.max(left, dp[i - 1][j] + j);
                dp[i][j] = matrix[i][j] + Math.max(left - j, right[j] + j);
            }
        }
        int ans = NEGINF;
        for (int j = 0; j < M; j++) {
            ans = Math.max(ans, dp[N - 1][j]);
        }
        return ans;
    }
}