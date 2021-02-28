import java.util.*;

class Solution {
    public int solve(int[] a, int[] b, int lower, int upper) {
        int[] sq_a = new int[a.length];
        int[] sq_b = new int[b.length];

        for (int i = 0; i < a.length; i++) {
            sq_a[i] = a[i] * a[i];
        }
        for (int i = 0; i < b.length; i++) {
            sq_b[i] = b[i] * b[i];
        }
        Arrays.sort(sq_a);
        Arrays.sort(sq_b);
        int res = 0;
        for (int x : a) {
            /// binary search for the left
            int l = 0;
            int r = b.length - 1;

            int first = n;
            int second = -1;

            while (l <= r) {
                int m = l + (r - l) / 2;
                if (b[m] + x >= lower) {
                    first = Math.min(first, m);
                    r = m - 1;
                } else {
                    l = m + 1;
                }
            }
            //// binary search for the right
            l = 0;
            r = b.length - 1;
            while (l <= r) {
                int m = l + (r - l) / 2;
                if (b[m] + x <= higher) {
                    second = Math.max(m, second);
                    l = m + 1;
                } else {
                    r = m - 1;
                }
            }
            if (first == n || second == -1) {
                continue;
            }
            res += second - first + 1;
        }
        return res;

    }
}