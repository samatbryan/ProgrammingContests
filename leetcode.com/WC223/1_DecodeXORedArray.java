class Solution {
    public int[] decode(int[] e, int first) {
        int n = e.length;
        int[] res = new int[n + 1];
        res[0] = first;
        for (int i = 0; i < n; i++) {
            res[i + 1] = res[i] ^ e[i];
        }
        return res;
    }
}