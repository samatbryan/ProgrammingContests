public class d {

    static long solve(Long[][][] dp, String s, String debug, int idx, int tight, int mod_sum, long total_sum) {
        long mod = (long) 1e9 + 7;

        if (idx == s.length())
            return 0;
        if (dp[idx][mod_sum][tight] != null)
            return dp[idx][mod_sum][tight];

        // System.out.println(debug);
        int limit = 9;
        if (tight == 1)
            limit = s.charAt(idx) - '0';

        long res = 0;

        for (int next = 0; next <= limit; next++) {
            if (idx > 0 && total_sum == 0 && next == 0)
                continue;
            int new_tight = 0;
            if (next == s.charAt(idx) - '0') {
                new_tight = tight;
            }

            int new_mod_sum = (mod_sum + next) % 3;
            String next_debug = debug + Integer.toString(next);
            if (total_sum != 0 && next % 5 == 0 && new_mod_sum == 0) {
                System.out.println(next_debug);
                res = (res + 1) % mod;
            }
            long next_total = total_sum + next;
            res = (res + solve(dp, s, next_debug, idx + 1, new_tight, new_mod_sum, next_total)) % mod;
        }
        return res;
    }

    public static void main(String[] args) {
        String s = "1000";
        Long[][][] dp = new Long[s.length()][10][2];
        long res = solve(dp, s, "", 0, 1, 0, 0L);
        System.out.println("count " + res);
    }

}