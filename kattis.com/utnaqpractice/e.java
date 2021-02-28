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
    final int IMAX = Integer.MAX_VALUE;
    final long LMAX = Long.MAX_VALUE;
    final long LMIN = Long.MIN_VALUE;

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter w = new PrintWriter(System.out);
        int T = 1;

        e ok = new e();

        for (int i = 0; i < T; i++)
            ok.solve(in, w);
        w.close();
    }

    static class Node {
        int node;
        long dist;

        Node(int node, long dist) {
            this.node = node;
            this.dist = dist;
        }
    }

    static class Edge {
        int x;
        int y;
        long dist;

        Edge(int x, int y, long dist) {
            this.x = x;
            this.y = y;
            this.dist = dist;
        }
    }

    public class Dsu<T> {

        private HashMap<T, T> parent;
        private HashMap<T, Integer> size;
        private int roots;

        /**
         * Class constructor for the Disjoint Set Union class, uses generics. Does not
         * require any initial set number of nodes in the graph. example usage: using
         * integers: Dsu<Integer> dsu = new Dsu<Integer>() using pairs:
         * Dsu<Pair<Integer,Integer>> dsu = new Dsu<Pair<Integer,Integer>>();
         */
        public Dsu() {
            this.parent = new HashMap<T, T>();
            this.size = new HashMap<T, Integer>();
            this.roots = 0;
        }

        /**
         * Time Complexity: O(1) Takes a new vertex v and creates a new set as a parent
         * of itself in O
         * 
         * @param v The vertex to add to the DSU
         */
        void make_set(T v) {
            parent.put(v, v);
            size.put(v, 1);
            roots++;
        }

        /**
         * Time Complexity: O(log(n)) Takes a new vertex v and returns the root. Uses
         * Path Compression Path Compression is where you find the root, and set all
         * node's parent along that path to root
         * 
         * @param v The vertex to find the root of
         * @return The root of the vertex v
         */
        T find_set(T v) {
            if (parent.get(v).equals(v))
                return v;
            T root = find_set(parent.get(v));
            parent.put(v, root);
            return root;
        }

        /**
         * Time Complexity: O(log(n)) Takes a two vertex a and b and joins the two sets.
         * Uses Union by Size
         * 
         * @param a The first set to join
         * @param b The second set to join
         */
        void union_set(T a, T b) {
            a = find_set(a);
            b = find_set(b);

            if (!a.equals(b)) {
                if (size.get(a) < size.get(b)) {
                    parent.put(a, b);
                    size.put(b, size.get(b) + size.get(a));
                } else {
                    parent.put(b, a);
                    size.put(a, size.get(b) + size.get(a));
                }
                roots--;
            }
        }

        /**
         * Time Complexity: O(1) Returns the number of roots
         * 
         * @return The number of distinct roots
         */
        int num_roots() {
            return this.roots;
        }

        /**
         * Time Complexity: O(1) Returns the size of set that contains a
         * 
         * @return The size of the set rooted at a
         */
        int set_size(T a) {
            if (!parent.containsKey(a))
                return 0;
            return this.size.get(find_set(a));
        }

        /**
         * Time Complexity: O(LogN) Returns the size of set that contains a
         * 
         * @return True if a and b are in the same set
         */
        boolean connected(T a, T b) {
            return find_set(a) == find_set(b);
        }
    }

    public void solve(InputReader in, PrintWriter w) {
        int n = in.ii(); // buildings from 1 to n
        int m = in.ii();
        int p = in.ii();

        int[] ins = in.nextIntArray(p);

        HashSet<Integer> insecure = new HashSet();
        HashSet<Integer> secure = new HashSet();
        for (int insec : ins) {
            insecure.add(insec);
        }
        for (int i = 1; i <= n; i++) {
            if (!insecure.contains(i)) {
                secure.add(i);
            }
        }

        ArrayList<Node>[] graph = new ArrayList[n + 1];
        for (int i = 1; i <= n; i++)
            graph[i] = new ArrayList();
        ArrayList<Edge> edges = new ArrayList();
        for (int i = 0; i < m; i++) {
            int x = in.ii();
            int y = in.ii();
            long l = in.ll();
            graph[x].add(new Node(y, l));
            graph[y].add(new Node(x, l));
            edges.add(new Edge(x, y, l));
        }

        Collections.sort(edges, (a, b) -> (Long.compare(a.dist, b.dist)));
        Dsu<Integer> dsu = new Dsu<Integer>();
        for (int sec : secure)
            dsu.make_set(sec);
        long cost = 0;
        for (Edge e : edges) {
            int x = e.x;
            int y = e.y;
            if (secure.contains(x) && secure.contains(y) && !dsu.connected(x, y)) {
                dsu.union_set(x, y);
                cost += e.dist;
            }
        }
        if (secure.size() > 0 && dsu.num_roots() != 1) { // if the MST wasnt even constructable
            w.println("impossible");
            return;
        }
        if (secure.size() == 0) {
            if (insecure.size() > 2) {
                w.println("impossible");
                return;
            }
            if (insecure.size() == 2) {
                if (graph[1].size() == 0) {
                    w.println("impossible");
                    return;
                } else {
                    w.println(graph[1].get(0).dist);
                    return;
                }
            }
            w.println(0);
            return;
        }
        for (int insecure_node : insecure) {
            if (secure.size() == 0)
                continue;
            long best = LMAX;
            for (Node neighbor : graph[insecure_node]) {
                if (secure.contains(neighbor.node)) {
                    best = Math.min(best, neighbor.dist);
                }
            }
            if (best == LMAX) {
                w.println("impossible");
                return;
            }
            cost += best;
        }
        w.println(cost);
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