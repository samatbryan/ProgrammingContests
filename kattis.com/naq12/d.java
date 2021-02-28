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

    static class Node {
        int node;
        double dist;

        Node(int node, double dist) {
            this.node = node;
            this.dist = dist;
        }
    }

    public double area(int a, int b, int h) {
        double res = (a + b) / 2.0;
        return res * 1.0 * h * 0.02;
    }

    public void solve(InputReader in, PrintWriter w) {
        while (true) {
            int n = in.ii();
            if (n == 0)
                break;
            HashMap<Integer, HashSet<Integer>> graph = new HashMap();
            HashMap<Pair<Integer, Integer>, Double> edge = new HashMap();
            for (int i = 0; i < n; i++) {
                int a = in.ii();
                int b = in.ii();
                int h = in.ii();
                double ar = area(a, b, h);
                graph.putIfAbsent(a, new HashSet());
                graph.putIfAbsent(b, new HashSet());
                graph.get(a).add(b);
                graph.get(b).add(a);
                double e = edge.getOrDefault(new Pair(a, b), Double.MAX_VALUE);
                edge.put(new Pair(a, b), Math.min(e, ar));
                edge.put(new Pair(b, a), Math.min(e, ar));
            }
            int src = in.ii();
            int dst = in.ii();
            PriorityQueue<Node> pq = new PriorityQueue<Node>((a, b) -> (Double.compare(a.dist, b.dist)));
            HashSet<Integer> popped = new HashSet();
            HashMap<Integer, Double> dists = new HashMap();
            pq.add(new Node(src, 0));
            dists.put(src, 0.0);
            while (pq.size() > 0) {
                Node cur = pq.poll();
                int cur_node = cur.node;
                if (popped.contains(cur_node))
                    continue;
                popped.add(cur_node);
                for (int neighbor : graph.getOrDefault(cur_node, new HashSet<Integer>())) {
                    if (popped.contains(neighbor))
                        continue;
                    double already = dists.getOrDefault(neighbor, Double.MAX_VALUE);
                    double dist = edge.get(new Pair(cur_node, neighbor));
                    if (dist + dists.get(cur_node) < already) {
                        dists.put(neighbor, dist + dists.get(cur_node));
                        pq.add(new Node(neighbor, dists.get(neighbor)));
                    }
                }
            }
            w.println(dists.get(dst));
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