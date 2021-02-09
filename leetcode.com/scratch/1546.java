class Solution {
    public int maxNonOverlapping(int[] nums, int target) {
        int res = 0, n = nums.length, sum = 0;
        HashMap<Integer, Integer> hm = new HashMap();
        hm.put(0, -1);
        int prev = -2;
        for (int i = 0; i < n; i++) {
            sum += nums[i];
            if (hm.containsKey(sum - target)) {
                int idx = hm.get(sum - target) + 1;
                if (idx > prev) {
                    prev = i;
                    res++;
                }
            }
            hm.put(sum, i);
        }
        return res;
    }
}