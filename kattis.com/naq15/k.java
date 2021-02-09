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

public class k {
    static class DSU {
        int[] parents;
        int n;

        DSU(int n) {
            this.n = n;
            this.parents = new int[n];
            for (int i = 0; i < n; i++)
                parents[i] = i;
        }

        int get_parent(int a) {
            if (parents[a] == a)
                return a;
            return parents[a] = get_parent(parents[a]);
        }

        void union(int a, int b) {
            a = get_parent(a);
            b = get_parent(b);
            parents[a] = b;
        }
    }

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter w = new PrintWriter(System.out);
        int T = 1;

        k ok = new k();

        for (int i = 0; i < T; i++)
            ok.solve(in, w);
        w.close();
    }

    public void solve(InputReader in, PrintWriter w) {
        int n = in.ii();
        int[][] sensors = new int[n][3];
        for (int i = 0; i < n; i++)
            sensors[i] = in.nextIntArray(3);

        // x = 200, y = 300

        DSU dsu = new DSU(220);

        for (int k = 0; k < n; k++) {
            int[] cur_sensor = sensors[k];
            int x1 = cur_sensor[0], r1 = cur_sensor[2];
            if (r1 == 0)
                continue;
            // connect all left and right
            if (x1 - r1 >= 0)
                dsu.union(k, 205);
            if (x1 + r1 <= 200)
                dsu.union(x1, 210);

            for (int prev = 0; prev < k; prev++) {
                int[] prev_sensor = sensors[prev];
                if (connected(cur_sensor, prev_sensor)) {
                    dsu.union(k, prev);
                }
            }
            if (dsu.get_parent(0) == dsu.get_parent(200)) {
                w.println(k);
                return;
            }
        }
    }

    boolean connected(int[] circle1, int[] circle2) {
        int x1 = circle1[0], y1 = circle1[1], r1 = circle1[2];
        int x2 = circle2[0], y2 = circle2[1], r2 = circle2[2];
        int dist = (int) (Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
        if ((r1 + r2) * (r1 + r2) >= dist)
            return true;
        return false;
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