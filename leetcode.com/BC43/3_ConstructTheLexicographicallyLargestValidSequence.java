class Solution {
    int[] res;
    int max_val;

    public int[] constructDistancedSequence(int n) {
        max_val = n;
        res = null;
        int[] nums = new int[1 + (2 * (n - 1))];

        backtrack(nums, 0, (1 << (n)) - 1);
        return res;
    }

    public void backtrack(int[] nums, int idx, int used) {
        // print(nums);
        if (res != null)
            return;
        if (used == 0) { // base case
            res = (int[]) nums.clone();
            return;
        } else if (idx >= nums.length)
            return;

        if (nums[idx] != 0)
            backtrack(nums, idx + 1, used);
        else {
            for (int i = max_val - 1; i >= 0; i--) {
                if (((1 << i) & used) != 0) {
                    int cur_num = i + 1;
                    int next_used = used & ~(1 << i);
                    if (cur_num == 1) {
                        nums[idx] = cur_num;
                        backtrack(nums, idx + 1, next_used);
                        nums[idx] = 0;
                    } else if (idx + cur_num < nums.length && nums[idx + cur_num] == 0) {
                        nums[idx] = cur_num;
                        nums[idx + cur_num] = cur_num;
                        backtrack(nums, idx + 1, next_used);
                        nums[idx] = 0;
                        nums[idx + cur_num] = 0;
                    }
                }
            }
        }

    }
}