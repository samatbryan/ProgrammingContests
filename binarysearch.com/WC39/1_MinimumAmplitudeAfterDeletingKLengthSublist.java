import java.util.*;

// https://binarysearch.com/room/Weekly-Contest-39-0uhtXXJ4hj
/*
You are given a list of integers nums and an integer k. Given that you must first remove a sublist of length k, return the minimum resulting max(nums) - min(nums).
Constraints
0 ≤ k < n ≤ 100,000 where n is the length of nums
*/
class Solution {
    public int solve(int[] nums, int k) {

        int n = nums.length;
        if (n == k)
            return 0;
        int[] premax = new int[n];
        int[] premin = new int[n];

        int[] sufmax = new int[n];
        int[] sufmin = new int[n];

        premax[0] = nums[0];
        premin[0] = nums[0];

        sufmax[n - 1] = nums[n - 1];
        sufmin[n - 1] = nums[n - 1];

        for (int i = 1; i < n; i++) {
            premax[i] = Math.max(premax[i - 1], nums[i]);
            premin[i] = Math.min(premin[i - 1], nums[i]);
        }

        for (int i = n - 2; i >= 0; i--) {
            sufmax[i] = Math.max(sufmax[i + 1], nums[i]);
            sufmin[i] = Math.min(sufmin[i + 1], nums[i]);
        }
        int res = Integer.MAX_VALUE;
        for (int i = 0; i < n; i++) {
            if (i + k - 1 >= n)
                break;
            if (i == 0) {
                res = Math.min(res, sufmax[i + k] - sufmin[i + k]);
            } else if (i + k == n) {
                res = Math.min(res, premax[i - 1] - premin[i - 1]);
            } else {
                res = Math.min(res, Math.max(sufmax[i + k], premax[i - 1]) - Math.min(sufmin[i + k], premin[i - 1]));
            }
        }
        return res;
    }
}