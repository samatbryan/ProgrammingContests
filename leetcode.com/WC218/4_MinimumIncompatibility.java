class Solution {
    int res = Integer.MAX_VALUE;
    int bucket_size;
    int n;

    public int minimumIncompatibility(int[] nums, int k) {
        n = nums.length;
        bucket_size = n / k;

        Set<HashSet<Integer>> visited = new HashSet();

        List<HashSet<Integer>> buckets = new ArrayList();
        for (int i = 0; i < k; i++)
            buckets.add(new HashSet());

        backtrack(nums, 0, buckets, 0);
        if (res == Integer.MAX_VALUE)
            return -1;
        return res;
    }

    public void backtrack(int[] nums, int idx, List<HashSet<Integer>> buckets, int compat) {
        if (idx >= n) {
            res = Math.min(res, compat);
            return;
        }
        Set<HashSet<Integer>> visited = new HashSet();

        for (HashSet<Integer> bucket : buckets) {
            // continue conditions
            if (bucket.contains(nums[idx]) || bucket.size() == bucket_size || visited.contains(bucket))
                continue;

            int new_compat = calculate(compat, bucket, nums[idx]);
            if (new_compat < res) {
                bucket.add(nums[idx]);
                backtrack(nums, idx + 1, buckets, new_compat);
                bucket.remove(nums[idx]);
            }
        }
    }

    public int calculate(int compat, HashSet<Integer> bucket, int new_val) {
        if (bucket.size() == 0)
            return compat;
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        for (int b : bucket) {
            min = Math.min(min, b);
            max = Math.max(max, b);
        }
        if (new_val > max)
            return compat + (new_val - max);
        if (new_val < min)
            return compat + (min - new_val);
        return compat;
    }
}