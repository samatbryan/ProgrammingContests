import java.io.*;
import java.util.*;

/* Example Usage

InputReader in = new InputReader(System.in);
PrintWriter w = new PrintWriter(System.out);

int t = in.nextInt();
while (t-- > 0) {
    int a[][] = new int[3][3];
    for (int i = 0; i < 3; i++)
        a[i] = in.nextIntArray(3);
    w.println(ans);
}
w.close();
*/

public class h {
    final int IMAX = Integer.MAX_VALUE;
    final int IMIN = Integer.MIN_VALUE;
    final long LMAX = Long.MAX_VALUE;
    final long LMIN = Long.MIN_VALUE;

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter w = new PrintWriter(System.out);
        int T = 1;

        h ok = new h();

        for (int i = 0; i < T; i++)
            ok.solve(in, w);
        w.close();
    }

    String code;
    int[][] grid;
    Integer[][][] dp;

    public int f(int r, int c, int code_idx) {
        int m = grid.length;
        int n = grid[0].length;
        if (r == 0 && c == grid[0].length - 1) {
            return grid[r][c];
        }
        if (dp[r][c][code_idx] != null)
            return dp[r][c][code_idx];
        int res = Integer.MAX_VALUE;
        // try step right
        if (c + 1 < n) {
            res = Math.min(res, f(r, c + 1, code_idx));
        }
        // try step up
        if (r - 1 >= 0) {
            res = Math.min(res, f(r - 1, c, code_idx));
        }
        if (code_idx < code.length()) {
            int hop = (int) (code.charAt(code_idx) - '0');
            // try hop right
            if (c + hop + 1 < n) {
                res = Math.min(res, f(r, c + hop + 1, code_idx + 1));
            }
            // try hop up
            if (r - hop - 1 >= 0) {
                res = Math.min(res, f(r - hop - 1, c, code_idx + 1));
            }
        }

        return dp[r][c][code_idx] = (res + grid[r][c]);

    }

    public void solve(InputReader in, PrintWriter w) {
        int x = in.ii();
        int y = in.ii();
        grid = new int[y][x];
        code = in.nextLine();
        int start_r = y - 1;
        int start_c = 0;

        dp = new Integer[y + 1][x + 1][code.length() + 2];
        for (int i = 0; i < y; i++) {
            String s = in.nextLine();
            for (int j = 0; j < x; j++) {
                int c = (int) (s.charAt(j) - '0');
                grid[i][j] = c;
            }
        }
        w.println(f(y - 1, 0, 0));

    }

    /*
     * IGNORE THESE ARE JUST INPUT READER CODE * IGNORE THESE ARE JUST INPUT READER
     * CODE IGNORE THESE ARE JUST INPUT READER CODE IGNORE THESE ARE JUST INPUT
     * READER CODE IGNORE THESE ARE JUST INPUT READER CODE * IGNORE THESE ARE JUST
     * INPUT READER CODE IGNORE THESE ARE JUST INPUT READER CODE IGNORE THESE ARE
     * JUST INPUT READER CODE IGNORE THESE ARE JUST INPUT READER CODE
     * 
     */
    static class InputReader {

        private final InputStream stream;
        private final byte[] buf = new byte[8192];
        private int curChar, snumChars;
        private SpaceCharFilter filter;

        public InputReader(InputStream stream) {
            this.stream = stream;
        }

        public int snext() {
            if (snumChars == -1)
                throw new InputMismatchException();
            if (curChar >= snumChars) {
                curChar = 0;
                try {
                    snumChars = stream.read(buf);
                } catch (IOException e) {
                    throw new InputMismatchException();
                }
                if (snumChars <= 0)
                    return -1;
            }
            return buf[curChar++];
        }

        public int ii() {
            int c = snext();
            while (isSpaceChar(c)) {
                c = snext();
            }
            int sgn = 1;
            if (c == '-') {
                sgn = -1;
                c = snext();
            }
            int res = 0;
            do {
                if (c < '0' || c > '9')
                    throw new InputMismatchException();
                res *= 10;
                res += c - '0';
                c = snext();
            } while (!isSpaceChar(c));
            return res * sgn;
        }

        public long ll() {
            int c = snext();
            while (isSpaceChar(c)) {
                c = snext();
            }
            int sgn = 1;
            if (c == '-') {
                sgn = -1;
                c = snext();
            }
            long res = 0;
            do {
                if (c < '0' || c > '9')
                    throw new InputMismatchException();
                res *= 10;
                res += c - '0';
                c = snext();
            } while (!isSpaceChar(c));
            return res * sgn;
        }

        public double nextDouble() {
            return Double.parseDouble(readString());
        }

        public int[] nextIntArray(int n) {
            int a[] = new int[n];
            for (int i = 0; i < n; i++) {
                a[i] = ii();
            }
            return a;
        }

        public long[] nextLongArray(int n) {
            long a[] = new long[n];
            for (int i = 0; i < n; i++) {
                a[i] = ll();
            }
            return a;
        }

        public String readString() {
            int c = snext();
            while (isSpaceChar(c)) {
                c = snext();
            }
            StringBuilder res = new StringBuilder();
            do {
                res.appendCodePoint(c);
                c = snext();
            } while (!isSpaceChar(c));
            return res.toString();
        }

        public String nextLine() {
            int c = snext();
            while (isSpaceChar(c))
                c = snext();
            StringBuilder res = new StringBuilder();
            do {
                res.appendCodePoint(c);
                c = snext();
            } while (!isEndOfLine(c));
            return res.toString();
        }

        public boolean isSpaceChar(int c) {
            if (filter != null)
                return filter.isSpaceChar(c);
            return c == ' ' || c == '\n' || c == '\r' || c == '\t' || c == -1;
        }

        private boolean isEndOfLine(int c) {
            return c == '\n' || c == '\r' || c == -1;
        }

        public interface SpaceCharFilter {
            public boolean isSpaceChar(int ch);
        }
    }
}

class Pair<S extends Comparable<S>, T extends Comparable<T>> implements Comparable<Pair<S, T>> {
    S first;
    T second;

    Pair(S f, T s) {
        first = f;
        second = s;
    }

    @Override
    public int compareTo(Pair<S, T> o) {
        int t = first.compareTo(o.first);
        if (t == 0)
            return second.compareTo(o.second);
        return t;
    }

    @Override
    public int hashCode() {
        return (31 + first.hashCode()) * 31 + second.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Pair))
            return false;
        if (o == this)
            return true;
        Pair p = (Pair) o;
        return first.equals(p.first) && second.equals(p.second);
    }

    @Override
    public String toString() {
        return "Pair{" + first + ", " + second + "}";
    }
}