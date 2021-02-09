import java.lang.ProcessBuilder.Redirect.Type;

class Solution {
    public boolean[] canEat(int[] candies, int[][] queries) {
        int n = candies.length;

        long[] prefix = new long[n];
        long[] pp = new long[n];
        pp[0] = (long) candies[0];
        for (int i = 1; i < n; i++) {
            pp[i] = pp[i - 1] + (long) candies[i];
        }
        for (int i = 0; i < n - 1; i++) {
            prefix[i + 1] = prefix[i] + (long) candies[i];
        }
        int q = queries.length;
        boolean[] res = new boolean[q];

        for (int i = 0; i < q; i++) {
            int[] query = queries[i];
            int type = query[0];
            long day = (long) query[1], cap = (long) query[2];
            long eatable = (day + 1) * cap;
            long lower_bound = day + 1;

            if (pp[type] >= lower_bound && prefix[type] < eatable) {
                res[i] = true;
            } else {
                res[i] = false;
            }
        }
        return res;
    }
}