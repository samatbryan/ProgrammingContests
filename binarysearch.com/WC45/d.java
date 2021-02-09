import java.util.*;

class Solution {
    int[] nums;
    String digits;

    public String adjust(String lower, int length) {
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < Math.max(0, length - lower.length()); i++) {
            res.append("0");
        }
        res.append(lower);
        return res.toString();
    }

    public String solve(String digits, String lower) {
        int m = digits.length();
        lower = adjust(lower, m);
        nums = new int[10];
        for (int i = 0; i < digits.length(); i++)
            nums[digits.charAt(i) - '0']++;

        int[] res = new int[m];

        // call fil here
        return Arrays.toString(res);
    }

    public int[] fill(char[] res, int idx, boolean greater, String digits) {
        if (idx >= res.length) { // base case
            if (greater)
                return res;
            return null;
        }

        int cur_num = digits.charAt(idx) - '0';
        int min = cur_num;
        if (greater)
            min = 0;

        for (int cand = min; cand <= 9; cand++) {
            if (nums[cand] <= 0)
                continue;
            if (cand > cur_num)
                greater = true;
            int[] next = fill(res, idx + 1, greater, digits);
            if (next != null)
                return next;
        }
        return null;

    }
}