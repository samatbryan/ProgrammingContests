class Solution {
    public int maximumScore(int[] nums, int[] mult) {
        int m = mult.length;
        int n = nums.length;
        int[][] dp = new int[m + 1][m + 1]; // used i lefts used j rights
        int res = Integer.MIN_VALUE;
        for (int use = 1; use <= m; use++) {
            for (int left = 0; left <= use; left++) {
                int right = use - left;
                // either use left or use right
                int use_left = 0;
                int use_right = 0;
                if (left > 0) {
                    use_left = mult[use - 1] * nums[left - 1];
                    use_left += dp[left-1][right];
                }
                if (right > 0) {
                    use_right = mult[use - 1] * nums[n - right];
                    use_right += dp[left][right - 1];
                }

                dp[left][right] = Math.max(use_right, use_left);
                if(use == m){
                    res = Math.max(res, dp[left][right]);
                }
            }
        }

        return res;
    }
}