import java.util.*;

/*
https://binarysearch.com/room/Weekly-Contest-39-0uhtXXJ4hj?questionsetIndex=1
An arithmetic sequence is a list of integers where the difference between one integer and the next is the same.

You are given a list of integers nums and a two-dimensional list of integers queries. Each element in queries contains [i, j] and asks whether the sublist of nums from [i, j], inclusive, is an arithmetic sequence. Return the number of queries that are true.

Constraints

0 ≤ n ≤ 100,000 where n is the length of nums
0 ≤ m ≤ 100,000 where m is the length of queries
*/
class Solution {
    public int solve(int[] nums, int[][] queries) {
        HashMap<Integer, Integer> hm = new HashMap();
        int n = nums.length;

        // length of arithmetic subsequence dp
        hm.put(n - 1, 1);
        hm.put(n - 2, 2);

        for (int i = n - 3; i >= 0; i--) {
            int this_diff = nums[i] - nums[i + 1];
            int next_diff = nums[i + 1] - nums[i + 2];

            if (this_diff == next_diff) {
                hm.put(i, 1 + hm.get(i + 1));
            } else {
                hm.put(i, 2);
            }
        }

        int res = 0;
        for (int[] q : queries) {
            int left = q[0];
            int right = q[1];
            if (hm.get(left) >= (right - left + 1)) {
                res++;
            }
        }
        return res;
    }
}