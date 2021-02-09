class Solution {
    int res;
    int[] jobs;

    public int minimumTimeRequired(int[] j, int k) {
        jobs = j;
        res = Integer.MAX_VALUE;

        HashSet<Integer>[] workers = new HashSet[k];
        for (int i = 0; i < k; i++)
            workers[i] = new HashSet();
        backtrack(workers, 0, 0);
        return res;
    }

    public void backtrack(HashSet<Integer>[] workers, int cur_job, int max_time) {
        if (cur_job == jobs.length) {
            res = Math.min(res, max_time);
            return;
        }
        HashSet<HashSet<Integer>> visited = new HashSet();

        for (int i = 0; i < workers.length; i++) {
            HashSet<Integer> worker = workers[i];
            if (visited.contains(worker))
                continue;
            int new_max_time = new_time(worker, cur_job, max_time);
            if (new_max_time < res) {
                worker.add(cur_job);
                backtrack(workers, cur_job + 1, new_max_time);
                worker.remove(cur_job);
                visited.add(worker);
            }
        }
    }

    private int new_time(HashSet<Integer> worker, int job, int max_time) {
        int t = jobs[job];
        for (int j : worker)
            t += jobs[j];
        return Math.max(t, max_time);
    }
}