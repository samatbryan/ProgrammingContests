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

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter w = new PrintWriter(System.out);
        int T = 1;
        for (int i = 0; i < T; i++)
            solve(in, w);
        w.close();
    }

    public static void solve(InputReader in, PrintWriter w) {
        int n = in.ni();
        int[] locs = in.nextIntArray(n);
        int m = in.ni();
        ArrayList<Node>[] graph = new ArrayList[n + 1];
        for (int i = 1; i <= n; i++)
            graph[i] = new ArrayList();
        for (int i = 1; i <= m; i++) {
            int a = in.ni(), b = in.ni(), d = in.ni();
            graph[a].add(new Node(b, d, 0));
            graph[b].add(new Node(a, d, 0));
        }

        // get ready for dijkstras
        int[] dists = new int[n + 1];
        ArrayList<Integer>[] parents = new ArrayList[n + 1];
        for (int i = 1; i <= n; i++)
            parents[i] = new ArrayList();
        PriorityQueue<Node> pq = new PriorityQueue<Node>(
                (a, b) -> (a.cost == b.cost ? b.balls - a.balls : a.cost - b.cost));
        HashSet<Integer> popped = new HashSet();

        int[] balls = new int[n + 1];

        pq.add(new Node(1, 0, locs[0]));
        Arrays.fill(dists, Integer.MAX_VALUE);
        dists[1] = 0;
        balls[1] = locs[0];

        while (pq.size() > 0) {
            Node node = pq.poll();
            int cur = node.node, cost = node.cost;
            popped.add(cur);

            for (Node neighbor_node : graph[cur]) {
                int neighbor = neighbor_node.node;
                int edge = neighbor_node.cost;
                if (popped.contains(neighbor))
                    continue;
                if (dists[cur] + edge < dists[neighbor]) {
                    dists[neighbor] = dists[cur] + edge;
                    balls[neighbor] = node.balls + locs[neighbor - 1];
                    pq.add(new Node(neighbor, dists[neighbor], node.balls + locs[neighbor - 1]));
                }
            }
        }

        if (dists[n] == Integer.MAX_VALUE) {
            w.println("impossible");
        } else {
            w.println(dists[n] + " " + balls[n]);
        }

    }

    static class Node {
        int node, cost, balls;

        Node(int node, int cost, int balls) {
            this.node = node;
            this.cost = cost;
            this.balls = balls;
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

        public int ni() {
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

        public long nl() {
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
                a[i] = ni();
            }
            return a;
        }

        public long[] nextLongArray(int n) {
            long a[] = new long[n];
            for (int i = 0; i < n; i++) {
                a[i] = nl();
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