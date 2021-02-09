class Solution {
    List<List<Integer>> g;
    int size1;
    int size2;
    Integer[] dp;

    public int connectTwoGroups(List<List<Integer>> cost) {
        this.g = cost;
        this.size1 = cost.size();
        this.size2 = cost.get(0).size();
        this.dp = new Integer[size1];
        return dp((1 << size2) - 1, 0);
    }

    public int dp(int two, int idx) {
        if (dp[one] != null)
            return dp[one];
        if (idx == size1)
            return dp[one] = calculate(two);
        int res = Integer.MAX_VALUE;

        for (int j = 0; j < size2; j++) {
            int new_two = two & ~(1 << j);
            int cost = g.get(idx).get(j);
            res = Math.min(res, cost + dp(new_two, idx + 1));
        }
        return dp[one] = res;

    }

    public int calculate(int two) {
        int res = 0;
        for (int i = 0; i < size2; i++) {
            if (((1 << i) & two) != 0) {
                int tmp = Integer.MAX_VALUE;
                for (int q = 0; q < size1; q++) {
                    tmp = Math.min(tmp, g.get(q).get(i));
                }
                res += tmp;
            }
        }
        return res;
    }
}