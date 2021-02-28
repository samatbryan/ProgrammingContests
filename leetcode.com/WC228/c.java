class Solution {
    public int minimumSize(int[] nums, int maxOperations) {
        int l = 1;
        int r = 0;
        for (int num : nums)
            r = Math.max(r, num);

        int res = r;
        while (l <= r) {
            int m = l + (r - l) / 2;
            if (ok(nums, m, maxOperations)) {
                res = Math.min(res, m);
                r = m - 1;
            } else {
                l = m + 1;
            }
        }
        return res;
    }

    public boolean ok(int[] nums, int size, int ops){
        int used = 0;
        for(int num : nums){
            if(num <= size){
                continue;
            }
            if(num % size == 0){
                used += (num/size) - 1;
            } else{
                used += (num/size);
            }
        }
        return used <= ops;
    }
}
