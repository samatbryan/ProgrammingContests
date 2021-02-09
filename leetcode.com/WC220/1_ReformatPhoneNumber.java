/*
 * https://leetcode.com/contest/weekly-contest-220/problems/reformat-phone-
 * number/
 * 
 * 1694. Reformat Phone Number User Accepted:3782 User Tried:3960 Total
 * Accepted:3879 Total Submissions:5688 Difficulty:Easy You are given a phone
 * number as a string number. number consists of digits, spaces ' ', and/or
 * dashes '-'.
 * 
 * You would like to reformat the phone number in a certain manner. Firstly,
 * remove all spaces and dashes. Then, group the digits from left to right into
 * blocks of length 3 until there are 4 or fewer digits. The final digits are
 * then grouped as follows:
 * 
 * 2 digits: A single block of length 2. 3 digits: A single block of length 3. 4
 * digits: Two blocks of length 2 each. The blocks are then joined by dashes.
 * Notice that the reformatting process should never produce any blocks of
 * length 1 and produce at most two blocks of length 2.
 * 
 * Return the phone number after formatting.
 */
class Solution {
    public String reformatNumber(String nb) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < nb.length(); i++) {
            char c = nb.charAt(i);
            if (c == '-' || c == ' ') {
                continue;
            }
            sb.append(c);
        }
        int n = sb.length();
        StringBuilder res = new StringBuilder();

        int i = 0;
        while (i < n) {
            int remaining = n - i;
            if (remaining == 4) {
                res.append(sb.substring(i, i + 2));
                res.append("-");
                res.append(sb.substring(i + 2, i + 4));
                return res.toString();
            } else {
                int nxt = Math.min(i + 3, n);
                res.append(sb.substring(i, nxt));
                res.append("-");
                i = nxt;
            }
        }
        if (res.charAt(res.length() - 1) == '-') {
            return res.substring(0, res.length() - 1);
        }
        return res.toString();
    }
}