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

public class i {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter w = new PrintWriter(System.out);
        int T = 1;

        i ok = new i();

        for (int i = 0; i < T; i++)
            ok.solve(in, w);
        w.close();
    }

    public void print(long[][] g) {
        for (int i = 0; i < g.length; i++) {
            System.out.println();
            for (int j = 0; j < g[0].length; j++) {
                System.out.print(g[i][j] + " ");
            }
        }
    }

    public void solve(InputReader in, PrintWriter w) {
        int n = in.ii(); // num nodes
        int m = in.ii(); // edges
        int t = in.ii(); // repairs
        int d = in.ii(); // max distance u can ride
        int src = 1;
        int dst = n;
        ArrayList<Integer> repairs = new ArrayList();
        repairs.add(1);
        for (int i = 0; i < t; i++)
            repairs.add(in.ii());
        long[][] graph = new long[n + 1][n + 1];
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                graph[i][j] = Long.MAX_VALUE;
            }
        }
        for (int i = 1; i <= n; i++)
            graph[i][i] = 0;
        for (int i = 0; i < m; i++) {
            int u = in.ii();
            int v = in.ii();
            long weight = in.ll();
            graph[u][v] = weight;
            graph[v][u] = weight;
        }
        for (int mid = 1; mid <= n; mid++) {
            for (int start = 1; start <= n; start++) {
                for (int end = 1; end <= n; end++) {
                    if (graph[start][mid] == Long.MAX_VALUE)
                        continue;
                    if (graph[mid][end] == Long.MAX_VALUE)
                        continue;
                    if (graph[start][mid] + graph[mid][end] < graph[start][end]) {
                        graph[start][end] = graph[start][mid] + graph[mid][end];
                    }
                }
            }
        }
        long[][] new_graph = new long[n + 1][n + 1];
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                if (i != j)
                    new_graph[i][j] = Long.MAX_VALUE;
            }
        }
        for (int start : repairs) {
            for (int i = 1; i <= n; i++) {
                if (graph[start][i] <= d)
                    new_graph[start][i] = graph[start][i];
            }
        }
        for (int mid = 1; mid <= n; mid++) {
            for (int start = 1; start <= n; start++) {
                for (int end = 1; end <= n; end++) {
                    if (new_graph[start][mid] == Long.MAX_VALUE)
                        continue;
                    if (new_graph[mid][end] == Long.MAX_VALUE)
                        continue;
                    if (new_graph[start][mid] + new_graph[mid][end] < new_graph[start][end]) {
                        new_graph[start][end] = new_graph[start][mid] + new_graph[mid][end];
                    }
                }
            }
        }
        if (new_graph[1][n] != Long.MAX_VALUE)
            w.println(new_graph[1][n]);
        else {
            w.println("stuck");
        }
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