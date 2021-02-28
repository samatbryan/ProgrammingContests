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

public class a {
    final int IMAX = Integer.MAX_VALUE;
    final long LMAX = Long.MIN_VALUE;

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter w = new PrintWriter(System.out);
        int T = 1;

        a ok = new a();

        for (int i = 0; i < T; i++)
            ok.solve(in, w);
        w.close();
    }

    public void solve(InputReader in, PrintWriter w) {
        int n = in.ii();
        int[][][] bingos = new int[n][5][5];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < 5; j++) {
                bingos[i][j] = in.nextIntArray(5);
            }
        }
        for (int i = 0; i < 5 * n; i++) {
            for (int j = 0; j < 5 * n; j++) {
                int board1 = i / 5, board2 = j / 5;
                if (board1 >= board2)
                    continue;
                int row1 = i % 5, row2 = j % 5;
                HashMap<Integer, Integer> numbers = new HashMap();
                for (int num : bingos[board1][row1])
                    numbers.put(num, numbers.getOrDefault(num, 0) + 1);
                for (int num : bingos[board2][row2])
                    numbers.put(num, numbers.getOrDefault(num, 0) + 1);
                for (int k : numbers.keySet()) {
                    if (numbers.get(k) == 2) {
                        // try this number as the winning number
                        boolean winning_row = true;

                        // for every row on a board that is not any of the two rows, check that none of
                        // them have won already
                        for (int bnum = 0; bnum < n; bnum++) {
                            int[][] cur_board = bingos[bnum];

                            for (int row_num = 0; row_num < 5; row_num++) {
                                if (bnum == board1 && row_num == row1)
                                    continue;
                                if (bnum == board2 && row_num == row2)
                                    continue;
                                int[] cur_row = cur_board[row_num];

                                HashSet<Integer> hs = new HashSet();
                                for (int cc : cur_row)
                                    hs.add(cc);
                                for (int had : numbers.keySet()) {
                                    if (had == k)
                                        continue;
                                    if (hs.contains(had))
                                        hs.remove(had);
                                }
                                if (hs.size() == 0)
                                    winning_row = false;
                            }
                        }
                        if (winning_row) {
                            w.println((board1 + 1) + " " + (board2 + 1));
                            return;
                        }
                    }
                }
            }
        }
        w.println("no ties");
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