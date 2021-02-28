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

public class new_c {
    final int IMAX = Integer.MAX_VALUE;
    final long LMAX = Long.MIN_VALUE;

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter w = new PrintWriter(System.out);
        int T = 1;

        new_c ok = new new_c();

        for (int i = 0; i < T; i++)
            ok.solve(in, w);
        w.close();
    }

    public void solve(InputReader in, PrintWriter w) {
        String[] board = new String[8];
        for (int i = 0; i < 8; i++) {
            board[i] = in.nextLine();
        }
        ArrayList<Pair<Integer, Integer>> queens = new ArrayList();
        int[][] matrix = new int[8][8];
        for (int r = 0; r < 8; r++) {
            String s = board[r];
            for (int c = 0; c < 8; c++) {
                if (s.charAt(c) == '*') {
                    matrix[r][c] = 1;
                }
            }
        }
        if (solve(matrix)) {
            w.println("valid");
        } else {
            w.println("invalid");
        }
    }

    public boolean solve(int[][] matrix) {
        // Write your code here
        int count = 0;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if (matrix[i][j] == 1) {
                    count++;
                    if (!checkValid(i, j, matrix)) {
                        return false;
                    }
                }
            }
        }
        if (count != matrix.length) {
            return false;
        }
        return true;
    }

    public boolean checkValid(int i, int j, int[][] matrix) {
        for (int r = 0; r < i; r++) {
            if (matrix[r][j] == 1) {
                return false;
            }
        }

        for (int r = i + 1; r < matrix.length; r++) {
            if (matrix[r][j] == 1) {
                return false;
            }
        }

        for (int c = 0; c < j; c++) {
            if (matrix[i][c] == 1) {
                return false;
            }
        }

        for (int c = j + 1; c < matrix[0].length; c++) {
            if (matrix[i][c] == 1) {
                return false;
            }
        }

        int m = matrix.length;
        int n = matrix[0].length;

        int prevI = i;
        int prevJ = j;
        i++;
        j++;
        while (i < m && j < n) {
            if (matrix[i][j] == 1) {
                return false;
            }
            i++;
            j++;
        }

        i = prevI;
        j = prevJ;

        i--;
        j--;
        while (i >= 0 && j >= 0) {
            if (matrix[i][j] == 1) {
                return false;
            }
            i--;
            j--;
        }

        i = prevI;
        j = prevJ;

        i++;
        j--;
        while (i >= 0 && j >= 0 && i < m && j < n) {
            if (matrix[i][j] == 1) {
                return false;
            }
            i++;
            j--;

        }

        i = prevI;
        j = prevJ;

        i--;
        j++;
        while (i >= 0 && j >= 0 && i < m && j < n) {
            if (matrix[i][j] == 1) {
                return false;
            }
            i--;
            j++;

        }
        return true;
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