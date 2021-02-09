class Solution {
    public int concatenatedBinary(int n) {
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= n; i++) {
            sb.append(Integer.toBinaryString(i));
        }
        long mod = (long) 1e9 + 7;
        String b = sb.toString();
        long idx = 0;
        long res = 0;
        long pow = 1;
        for (int i = b.length() - 1; i >= 0; i--) {
            long d = (long) b.charAt(i) - '0';
            if (d == 1) {
                res = (res + pow) % mod;
            }
            pow = (pow * 2) % mod;
            idx += 1;
        }
        return (int) res;
    }
}