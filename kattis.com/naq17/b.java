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

public class b {

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter w = new PrintWriter(System.out);
        int T = 1;
        for (int i = 0; i < T; i++)
            solve(in, w);
        w.close();
    }

    public static void solve(InputReader in, PrintWriter w) {
        int n = in.ni(), m = in.ni(), f = in.ni(), s = in.ni(), t = in.ni();
        ArrayList<Integer>[] graph = new ArrayList[n];
        ArrayList<int[]> flights = new ArrayList();
        HashMap<Pair<Integer, Integer>, Integer> costs = new HashMap();

        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList();
        }
        for (int itr = 0; itr < m; itr++) {
            int i = in.ni(), j = in.ni(), c = in.ni();

            costs.put(new Pair(i, j), Math.min(costs.getOrDefault(new Pair(i, j), Integer.MAX_VALUE), c));
            costs.put(new Pair(j, i), Math.min(costs.getOrDefault(new Pair(j, i), Integer.MAX_VALUE), c));
            graph[i].add(j);
            graph[j].add(i);
        }

        for (int i = 0; i < f; i++) {
            flights.add(in.nextIntArray(2));
        }

        long[] from_src = dijkstras(graph, s, costs);
        long[] from_dst = dijkstras(graph, t, costs);

        long res = from_dst[s];

        for (int[] flight : flights) {
            int u = flight[0], v = flight[1];
            if (from_src[u] == Long.MAX_VALUE || from_dst[v] == Long.MAX_VALUE)
                continue;
            res = Math.min(res, from_src[u] + from_dst[v]);
        }

        w.println(res);
    }

    static class Node implements Comparator<Node> {
        int node;
        long cost;

        Node() {
        }

        Node(int node, long cost) {
            this.node = node;
            this.cost = cost;
        }

        @Override
        public int compare(Node node1, Node node2) {
            return Long.compare(node1.cost, node2.cost);
        }
    }

    public static long[] dijkstras(ArrayList<Integer>[] graph, int source,
            HashMap<Pair<Integer, Integer>, Integer> costs) {
        int n = graph.length;
        long[] dists = new long[graph.length];
        Arrays.fill(dists, Long.MAX_VALUE);
        dists[source] = 0;

        PriorityQueue<Node> pq = new PriorityQueue<Node>(new Node());
        pq.add(new Node(source, 0));
        HashSet<Integer> processed = new HashSet();

        while (pq.size() > 0) {
            Node cur_node = pq.poll();
            if (processed.contains(cur_node))
                continue;
            processed.add(cur_node.node);
            for (int neighbor : graph[cur_node.node]) {
                if (processed.contains(neighbor))
                    continue;
                long cost = (long) costs.get(new Pair(cur_node.node, neighbor));
                if (cost + dists[cur_node.node] < dists[neighbor]) {
                    dists[neighbor] = cost + dists[cur_node.node];
                    pq.add(new Node(neighbor, (long) dists[neighbor]));
                }
            }
        }
        return dists;
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

        public long nextLong() {
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
                a[i] = nextLong();
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