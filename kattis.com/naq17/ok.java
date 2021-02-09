class Solution {
    public int minimumTimeRequired(int[] jobs, int k) {
        int total_bitmask = (1 << jobs.length) - 1; // 1 1 1 1 1 11 1 1

        Integer[][] dp = new Integer[total_bitmask + 1][k];
        return f(total_bitmask, 0, dp, jobs, k);
    }

    public int working_time(int job_mask, int[] jobs) {
        int j = jobs.length, res = 0;
        for (int i = 0; i < j; i++) {
            if (((1 << i) & job_mask) != 0) {
                res += jobs[i];
            }
        }
        return res;
    }

    public int f(int job_mask, int cur_worker, Integer[][] dp, int[] jobs, int k) {
        if (dp[job_mask][cur_worker] != null)
            return dp[job_mask][cur_worker];

        if (job_mask == 0)
            return 0;
        if (cur_worker == k - 1) {
            return dp[job_mask][cur_worker] = working_time(job_mask, jobs);
        }
        int total_bitmask = (1 << jobs.length) - 1;
        int res = Integer.MAX_VALUE;

        for (int submask = mask;; submask = (submask - 1) & mask) {
            // do something

            if (submask == 0)
                break;
        }
        for (int i = 0; i <= total_bitmask; i++) {
            // use this subset of the mask, if we can.
            if ((job_mask | i) == job_mask) {
                int cand = working_time(i, jobs);
                cand = Math.max(cand, f(job_mask & ~(i), cur_worker + 1, dp, jobs, k));
                res = Math.min(res, cand);
            }
        }
        return dp[job_mask][cur_worker] = res;
    }
    /*
     * dp(avaliable_jobs, cur_worker) = maximum working time of assigning
     * avavaliable_jobs to cur_worker:
     * 
     * 
     * Base case:
     * 
     * dp(0, _) = 0
     * 
     * 
     * Transition:
     * 
     * dp(avaliable_jobs, cur_worker) = go through all possible subsets... return
     * max(cur_worker use this job, dp(new_subset, next_worker))
     * 
     * 
     * 
     * return minimum of all dp(subset, 0) for all subsets
     */
}