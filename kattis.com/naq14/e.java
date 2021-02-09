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

public class e {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter w = new PrintWriter(System.out);
        int T = 1;

        e ok = new e();

        for (int i = 0; i < T; i++)
            ok.solve(in, w);
        w.close();
    }

    double walk(double[][] locs, int x, int y) {
        double d = Math.pow(locs[x][0] - locs[y][0], 2) + Math.pow(locs[x][1] - locs[y][1], 2);
        d = Math.pow(d, 0.5);
        return d / 5.0;
    }

    double cannon(double[][] locs, int x, int y) {
        double d = Math.pow(locs[x][0] - locs[y][0], 2) + Math.pow(locs[x][1] - locs[y][1], 2);
        d = Math.pow(d, 0.5);
        d = Math.abs(d - 50);
        return 2 + (d / 5.0);
    }

    public void solve(InputReader in, PrintWriter w) {
        double src_x = in.nextDouble(), src_y = in.nextDouble();
        double dst_x = in.nextDouble(), dst_y = in.nextDouble();
        int n = in.ii();
        double[][] locs = new double[n + 2][2];
        locs[0][0] = src_x;
        locs[0][1] = src_y;
        locs[n + 1][0] = dst_x;
        locs[n + 1][1] = dst_y;
        int src = 0, dst = n + 1;
        for (int i = 1; i <= n; i++) {
            locs[i][0] = in.nextDouble();
            locs[i][1] = in.nextDouble();
        }
        double[][] dist = new double[n + 2][n + 2];
        for (int i = 0; i <= n + 1; i++) { // compute the walk from i to j
            for (int j = 0; j <= n + 1; j++) {
                if (i == j)
                    continue;
                dist[i][j] = walk(locs, i, j);
            }
        }
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                if (i == j)
                    continue;
                dist[i][j] = Math.min(dist[i][j], cannon(locs, i, j));
            }
            dist[i][src] = Math.min(dist[i][src], cannon(locs, i, src));
            dist[i][dst] = Math.min(dist[i][dst], cannon(locs, i, dst));

        }

        for (int i = 0; i <= n + 1; i++) {
            for (int start = 0; start <= n + 1; start++) {
                for (int end = 0; end <= n + 1; end++) {
                    if (start == end)
                        continue;
                    if (dist[start][i] + dist[i][end] < dist[start][end]) {
                        dist[start][end] = dist[start][i] + dist[i][end];
                    }
                }
            }
        }
        w.println(dist[src][dst]);

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