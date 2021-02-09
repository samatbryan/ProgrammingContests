/*
https://binarysearch.com/room/Weekly-Contest-37-u2kU8duwTB?questionsetIndex=1

You are given two lists of integers cores and tasks. Each cores[i] represents the number of cores available in server i. And each tasks[i] represents the number of cores needed to run that task.

Each task can be run in only one server but each server can run multiple tasks. Return whether it's possible to run all the tasks with the given cores.

Constraints

n ≤ 15 where n is the length of cores
m ≤ 15 where m is the length of tasks


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
