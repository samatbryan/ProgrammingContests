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

public class d {
    final int IMAX = Integer.MAX_VALUE;
    final int IMIN = Integer.MIN_VALUE;

    final long LMAX = Long.MIN_VALUE;

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter w = new PrintWriter(System.out);
        int T = 1;

        d ok = new d();

        for (int i = 0; i < T; i++)
            ok.solve(in, w);
        w.close();
    }

    public int gcd(int a, int b) {
        if (a == 0)
            return b;
        return gcd(b % a, a);
    }

    static int V, source, target; // s != t
    static ArrayList<Integer>[] adjList;
    static int[][] edge_val; // instead of edge_val, you can use c[][], f[][] so as not to destroy the graph
    static int[] p; // edge_val (edge_validual) = c (capacity) - f (flow)

    static int edmondsKarp() // O(min(VE^2, flow * E)) for adjList, O(V^3E)
    {
        int INF = (int) 1e9;
        int mf = 0;
        while (true) {
            Queue<Integer> q = new LinkedList<Integer>();
            p = new int[V];
            Arrays.fill(p, -1);
            q.add(source);
            p[source] = source;
            while (!q.isEmpty()) {
                int u = q.remove();
                if (u == target)
                    break;
                for (int v : adjList[u])
                    if (edge_val[u][v] > 0 && p[v] == -1) {
                        p[v] = u;
                        q.add(v);
                    }
            }

            if (p[target] == -1)
                break;
            mf += augment(target, INF);
        }
        return mf;
    }

    static int augment(int v, int flow) {
        if (v == source)
            return flow;
        flow = augment(p[v], Math.min(flow, edge_val[p[v]][v]));
        edge_val[p[v]][v] -= flow;
        edge_val[v][p[v]] += flow;

        return flow;
    }

    public void solve(InputReader in, PrintWriter w) {
        int n = in.ii();
        int[] roomnum = in.nextIntArray(n);
        ArrayList<Integer>[] graph = new ArrayList[n];
        int[][] flow = new int[n][n];
        int src = -1, dst = -1, max = IMIN, min = IMAX;
        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList();
            if (roomnum[i] > max) {
                dst = i;
                max = roomnum[i];
            }
            if (roomnum[i] < min) {
                src = i;
                min = roomnum[i];
            }
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i == j)
                    continue;
                int g = gcd(roomnum[i], roomnum[j]);
                if (g > 1) {
                    flow[i][j] = g;
                    graph[i].add(j);
                }
            }
        }
        source = src;
        target = dst;
        V = n;
        adjList = graph;
        edge_val = flow;
        w.println(edmondsKarp());
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