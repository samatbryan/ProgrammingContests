/*
https://leetcode.com/contest/biweekly-contest-41/problems/sum-of-absolute-differences-in-a-sorted-array/

You are given an integer array nums sorted in non-decreasing order.

Build and return an integer array result with the same length as nums such that result[i] is equal to the summation of absolute differences between nums[i] and all the other elements in the array.

In other words, result[i] is equal to sum(|nums[i]-nums[j]|) where 0 <= j < nums.length and j != i (0-indexed).

Solution - Main Idea:

We know that for some number at index i, every index j < i is smaller in a sorted array so the absolute value sum will
be subtraction of the js and addition of the is. Meanwhile, every index j > i will be addition of the j's and subtraction of the i's
*/

class Solution {
    public int[] getSumAbsoluteDifferences(int[] nums) {
        int n = nums.length;
        int[] left = new int[n];
        int[] right = new int[n];

        left[0] = nums[0];
        right[n - 1] = nums[n - 1];
        for (int i = 1; i < n; i++) {
            left[i] = left[i - 1] + nums[i];
        }
        for (int i = n - 2; i >= 0; i--) {
            right[i] = right[i + 1] + nums[i];
        }

        int[] res = new int[n];

        for (int i = 0; i < n; i++) {
            int left_sum = 0;
            int right_sum = 0;
            if (i - 1 >= 0) {
                left_sum = left[i - 1];
            }
            if (i + 1 < n) {
                right_sum = right[i + 1];
            }
            res[i] = (i * nums[i] - left_sum) + (right_sum - nums[i] * (n - i - 1));
        }
        return res;
    }
}
