import java.util.*;

/*
https://binarysearch.com/room/Weekly-Contest-39-0uhtXXJ4hj?questionsetIndex=2
You are given a list of integers nums and a list of unique integers target. Return the number of non-empty sublists in nums that don't contain every number in target. Mod the result by 10 ** 9 + 7.

Constraints

0 ≤ n ≤ 100,000 where n is the length of nums
0 ≤ m ≤ 100,000 where m is the length of target
*/
class Solution {
    public int solve(int[] nums, int[] target) {
        if (target.length == 0)
            return 0;

        long n = (long) nums.length;
        long mod = (long) 1e9 + 7;
        long total_sublists = ((n * (n + 1)) % mod) / 2;
        long sublists_containing_target = 0;

        HashMap<Integer, Integer> hm = new HashMap();
        HashSet<Integer> target_set = new HashSet();
        for (int t : target)
            target_set.add(t);

        int right = 0;
        for (int left = 0; left < n; left++) {
            if (left - 1 >= 0) {
                int out = nums[left - 1];
                if (hm.containsKey(out)) {
                    hm.put(out, hm.getOrDefault(out, 0) - 1);
                    if (hm.get(out) == 0) {
                        hm.remove(out);
                    }
                }
            }

            while (right < n && hm.size() < target_set.size()) {
                int cur = nums[right];
                if (target_set.contains(cur)) {
                    hm.put(cur, hm.getOrDefault(cur, 0) + 1);
                }
                right++;
            }

            if (hm.size() == target_set.size()) {
                sublists_containing_target += (n - right + 1);
            }
        }
        return (int) (total_sublists - sublists_containing_target);
    }
}