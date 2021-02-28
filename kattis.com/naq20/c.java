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

public class c {
    final int IMAX = Integer.MAX_VALUE;
    final int IMIN = Integer.MIN_VALUE;
    final long LMAX = Long.MAX_VALUE;
    final long LMIN = Long.MIN_VALUE;

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter w = new PrintWriter(System.out);
        int T = 1;

        c ok = new c();

        for (int i = 0; i < T; i++)
            ok.solve(in, w);
        w.close();
    }

    public boolean ok(Pair<Integer, Integer> pos, String[] board) {
        // down right diagonal
        int r = pos.first + 1;
        int c = pos.second + 1;
        while (r < 8 && c < 8) {
            if (board[r].charAt(c) == '*') {
                return false;
            }
            r++;
            c++;
        }

        // up left diagonal
        r = pos.first - 1;
        c = pos.second - 1;
        while (r >= 0 && c >= 0) {
            if (board[r].charAt(c) == '*') {
                return false;
            }
            r--;
            c--;
        }

        // up right diagonal
        r = pos.first - 1;
        c = pos.second + 1;
        while (r >= 0 && c < 8) {
            if (board[r].charAt(c) == '*') {
                return false;
            }
            r--;
            c++;
        }

        // down left diagonal
        r = pos.first + 1;
        c = pos.second - 1;
        while (r < 8 && c >= 0) {
            if (board[r].charAt(c) == '*') {
                return false;
            }
            r++;
            c--;
        }
        // up
        r = pos.first - 1;
        c = pos.second;

        while (r >= 0) {
            if (board[r].charAt(c) == '*') {
                return false;
            }
            r--;
        }
        // down
        r = pos.first + 1;
        c = pos.second;
        while (r < 8) {
            if (board[r].charAt(c) == '*') {
                return false;
            }
            r++;
        }

        // right
        r = pos.first;
        c = pos.second + 1;

        while (c < 8) {
            if (board[r].charAt(c) == '*') {
                return false;
            }
            c++;
        }
        // left
        r = pos.first;
        c = pos.second - 1;
        while (c >= 0) {
            if (board[r].charAt(c) == '*') {
                return false;
            }
            c--;
        }
        return true;
    }

    public void solve(InputReader in, PrintWriter w) {
        String[] board = new String[8];
        for (int i = 0; i < 8; i++) {
            board[i] = in.nextLine();
        }
        ArrayList<Pair<Integer, Integer>> queens = new ArrayList();
        for (int r = 0; r < 8; r++) {
            String s = board[r];
            for (int c = 0; c < 8; c++) {
                if (s.charAt(c) == '*') {
                    queens.add(new Pair(r, c));
                }
            }
        }
        for (Pair<Integer, Integer> pos : queens) {
            if (!ok(pos, board)) {
                w.println("invalid");
                return;
            }
        }
        w.println("valid");
    }

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