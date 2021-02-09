class Solution {
    public int maximumGain(String s, int x, int y) {
        StringBuilder sb = new StringBuilder();
        ArrayList<String> tmp = new ArrayList();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == 'a' || c == 'b') {
                sb.append(c);
            } else {
                if (sb.length() == 0)
                    continue;
                tmp.add(sb.toString());
                sb = new StringBuilder();
            }
        }
        if (sb.length() > 0)
            tmp.add(sb.toString());
        int res = 0;
        for (String t : tmp)
            res += gain(t, x, y);
        return res;
    }

    // x = 'ab'
    // y = 'ba'
    // String of a'bs and b's
    public int gain(String s, int x, int y) {
        int res = 0;
        char left = 'a';
        char right = 'b';

        if (y > x) {
            left = 'b';
            right = 'a';
            int tmp = x;
            x = y;
            y = tmp;
        }
        LinkedList<Character> stack = new LinkedList();

        // pop the left and right combos
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (stack.size() > 0 && stack.getLast() == left && c == right) {
                stack.removeLast();
                res += x;
            } else {
                stack.addLast(c);
            }
        }

        StringBuilder remaining = new StringBuilder();
        while (stack.size() > 0) {
            remaining.append(stack.removeFirst());
        }

        s = remaining.toString();
        // pop the left and right combos
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (stack.size() > 0 && stack.getLast() == right && c == left) {
                stack.removeLast();
                res += y;
            } else {
                stack.addLast(c);
            }
        }
        return res;
        // pop the right and left combo
    }
}

// given some string of 1's and 0's, whats the maximum points you can get given
// that '10' can be removed with X points and '01' removed with Y points

/*
 * 
 * lets just say theyre both X
 * 
 * 
 * 001010
 */
