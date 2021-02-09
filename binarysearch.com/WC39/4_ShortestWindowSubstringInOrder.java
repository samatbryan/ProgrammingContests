import java.util.*;

/*
https://binarysearch.com/room/Weekly-Contest-39-0uhtXXJ4hj?questionsetIndex=3
Given a lowercase alphabet string s, return the length of the shortest substring containing all alphabet characters in order from "a" to "z". If there's no solution, return -1.

Constraints

0 ≤ n ≤ 100,000 where n is the length of s

*/
class Solution {
    public int solve(String s) {
        int[] alpha_idx = new int[26];
        int[] dists = new int[26];

        Arrays.fill(alpha_idx, -1);
        int n = s.length();
        int res = Integer.MAX_VALUE;
        for (int i = 0; i < n; i++) {
            int c = s.charAt(i) - 'a';
            if (c == 0) { // if character is an a
                alpha_idx[0] = i;
                dists[0] = 1;
            } else {
                if (alpha_idx[c - 1] != -1) {
                    alpha_idx[c] = i;
                    dists[c] = dists[c - 1] + (i - alpha_idx[c - 1]);
                    if (c == 25) // character is a z
                        res = Math.min(res, dists[c]);
                }
            }
        }
        if (res == Integer.MAX_VALUE)
            return -1;
        return res;
    }
}