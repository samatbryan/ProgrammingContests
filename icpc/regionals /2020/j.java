import java.io.*;
import java.util.*;

public class j {
    final int IMAX = Integer.MAX_VALUE;
    final int IMIN = Integer.MIN_VALUE;
    final long LMAX = Long.MAX_VALUE;
    final long LMIN = Long.MIN_VALUE;

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter w = new PrintWriter(System.out);
        int T = in.ii();

        j ok = new j();

        for (int i = 0; i < T; i++)
            ok.solve(in, w);
        w.close();
    }

    public boolean possible(long[][] og_dist, long mid) {
        int n = og_dist.length;
        long[][] floyd = new long[n][n];
        for (int i = 0; i < n; i++)
            Arrays.fill(floyd[i], Integer.MAX_VALUE);
        for (int m = 0; m < n; m++) {
            for (int s = 0; s < n; s++) {
                if (og_dist[m][s] <= mid)
                    floyd[m][s] = og_dist[m][s];
                else {
                    floyd[m][s] = Integer.MAX_VALUE;
                }
            }
        }
        for (int m = 0; m < n; m++) {
            for (int s = 0; s < n; s++) {
                for (int t = 0; t < n; t++) {
                    if (s == t)
                        continue;
                    if (floyd[s][m] != Integer.MAX_VALUE && floyd[m][t] != Integer.MAX_VALUE) {
                        if (floyd[s][m] + floyd[m][t] < floyd[m][t]) {
                            floyd[s][t] = floyd[s][m] + floyd[m][t];
                        }
                    }
                }
            }
        }

        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (floyd[i][j] > mid)
                    return false;
            }
        }
        return true;
    }

    public void solve(InputReader in, PrintWriter w) {
        int n = in.ii();
        int k = in.ii();
        int m = in.ii();
        Integer[][] graph = new Integer[n][n];

        for (int i = 0; i < m; i++) {
            int u = in.ii();
            int v = in.ii();
            int d = in.ii();
            graph[u][v] = d;
            graph[v][u] = d;
        }

        long[][] dists = new long[n][n];
        for (int i = 0; i < n; i++) {
            Arrays.fill(dists[i], Integer.MAX_VALUE);
        }
        for (int mid = 0; mid < n; mid++) {
            for (int s = 0; s < n; s++) {
                for (int t = 0; t < n; t++) {
                    if (s == t)
                        continue;
                    if (dists[s][mid] + dists[mid][t] < dists[s][t]) {
                        dists[s][t] = dists[s][mid] + dists[mid][t];
                    }
                }
            }
        }
        long l = 0;
        long r = (int) (1e9);
        long res = Integer.MAX_VALUE;
        while (l <= r) {
            long mid = (l + (r - l) / 2);
            if (possible(dists, mid)) {
                res = Math.min(res, mid);
                r = mid - 1;
            } else {
                l = mid + 1;
            }
        }
        w.println(res);
    }

    public static class InputReader {

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
}