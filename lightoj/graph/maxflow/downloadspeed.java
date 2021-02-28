import java.io.*;
import java.util.*;

public class downloadspeed {
    final int IMAX = Integer.MAX_VALUE;
    final int IMIN = Integer.MIN_VALUE;
    final long LMAX = Long.MAX_VALUE;
    final long LMIN = Long.MIN_VALUE;

    static class Edge {
        int node;
        int flow;
        int capacity;
        int rev;

        /**
         * Directed edge to "node" with capacity
         * 
         * @param node     The "to" node of the edge
         * @param flow     The amount of flow in this edge toward node
         * @param capacity The total capacity that this edge has
         * @param rev      A helper indexing integer that gives us the index of the
         *                 reverse node. We can use graph[node].get(rev) to get the
         *                 corresponding reverse edge
         */
        Edge(int node, int flow, int capacity, int rev) {
            this.node = node;
            this.flow = flow;
            this.capacity = capacity;
            this.rev = rev;
        }
    }

    public class MaxFlowDinic {

        ArrayList<Edge>[] graph; // represents the residual graph of flows and capacitys between nodes
        int n; // the total number of nodes in the graph
        int[] level; // stores the level of each node. Source node will always be 0. This helps us
                     // tells if there is an edge from a node to another

        /**
         * @param n The total number of nodes in the (residual) graph
         */
        public MaxFlowDinic(int n) {
            this.n = n;
            this.graph = new ArrayList[n];
            this.level = new int[n];
            for (int i = 0; i < n; i++)
                graph[i] = new ArrayList<Edge>();

        }

        /**
         * Adds a directed edge u->v with capacity to graph. Initializes 0 flow, and
         * also adds the reverse back edge since this is a residual graph.
         * 
         * @param u        The beginning node of the edge
         * @param v        The ending node of the edge
         * @param capacity The total capacity that this edge can send
         */
        void addEdge(int u, int v, int capacity) {
            Edge forward = new Edge(v, 0, capacity, graph[v].size());
            Edge backward = new Edge(u, 0, 0, graph[u].size()); // we set the capacity to 0 since this is a residual
                                                                // edge.
                                                                // Allows us to code cleanly because we subtract the
                                                                // flow
                                                                // later in the sendFlow() function
            graph[u].add(forward);
            graph[v].add(backward);
        }

        /**
         * Returns true if more flow can be sent from src to dst. Also CHANGES graph,
         * such that each node is assigned a level. Levels resemble if a path exists
         * from one node to another iff level +=1
         * 
         * @param src The beginning node
         * @param dst The ending node
         * @return True if there is still more flow left to send from src to dst. Else
         *         False.
         */
        boolean bfs(int src, int dst) {
            LinkedList<Integer> q = new LinkedList<Integer>();
            this.level = new int[n]; // determines the level of each node
            Arrays.fill(this.level, -1);
            this.level[src] = 0; // level of source node is 0. This array can also be used as a visited array

            q.addLast(src);

            while (q.size() > 0) {
                int cur = q.removeFirst();
                for (Edge e : graph[cur]) { // for each neighbor
                    if (this.level[e.node] == -1 && e.flow < e.capacity) {
                        this.level[e.node] = this.level[cur] + 1;
                        q.addLast(e.node);
                    }
                }
            }
            return this.level[dst] != -1;
        }

        /**
         * Returns the amount of flow that can be sent from cur to dst
         * 
         * @param cur   The current node we are at
         * @param flow  The minimum flow we have seen from src to cur
         * @param dst   The destination node we want to flow to
         * @param start This is for performance reasons. We want to pick up at the edge
         *              we left off for the cur node. Since we dont want to reiterate
         *              through every neighbor again from the beginning, this allows us
         *              to start from where we left off
         * @return A candidate flow from src to dst. 0 if now flow is possible
         */
        long sendFlow(int cur, long flow, int dst, int[] start) {
            if (cur == dst)
                return flow;
            for (; start[cur] < graph[cur].size(); start[cur]++) {
                Edge e = this.graph[cur].get(start[cur]);
                if (this.level[e.node] == this.level[cur] + 1 && e.flow < e.capacity) {
                    long new_flow = Math.min(flow, e.capacity - e.flow); // this is the new flow
                    long candidate_flow = sendFlow(e.node, new_flow, dst, start);
                    if (candidate_flow > 0) { // this flow direction was valid
                        e.flow += candidate_flow; // adjust this edge's flow
                        this.graph[e.node].get(e.rev).flow -= candidate_flow; // adjust the reverse directions flow
                        return candidate_flow;
                    }
                }
            }
            return 0;

        }

        void printGraph() {
            for (int i = 1; i < this.n; i++) {
                System.out.println("from node " + i);
                for (Edge e : this.graph[i]) {
                    System.out.println(" to " + e.node + " flow " + e.flow + " capacity " + e.capacity);
                }
            }
        }

        /**
         * Uses Dinic's algorithm, running in O(E*V*V) time. This is faster than Edmond
         * Karp's which is O(E*E*V
         * 
         * 1. Initialize residual graph G as given graph.
         * 
         * 2) Do BFS of G to construct a level graph (or assign levels to vertices) and
         * also check if more flow is possible.
         * 
         * 2a) If more flow is not possible, then return.
         * 
         * 2b) Send multiple flows in G using level graph until blocking flow is
         * reached. Here using level graph means, in every flow, levels of path nodes
         * should be 0, 1, 2... (in order) from s to t.)
         * 
         * @param src The source node of the network flow
         * @param dst The destination node of the network flow
         * @return The long maximum flow from src to dst. Returns -1 if src == dst
         */
        long maxFlow(int src, int dst) {
            if (src == dst) // edge case
                return -1;
            long total_flow = 0;

            while (bfs(src, dst)) { // while there is a still a valid flow from src to dst
                int[] start = new int[this.n];

                while (true) {
                    long flow = sendFlow(src, Integer.MAX_VALUE, dst, start);
                    if (flow == 0)
                        break;
                    total_flow += flow;
                }
            }
            return total_flow;
        }

    }

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter w = new PrintWriter(System.out);
        int T = 1;

        downloadspeed ok = new downloadspeed();

        for (int i = 0; i < T; i++)
            ok.solve(in, w);
        w.close();
    }

    public void solve(InputReader in, PrintWriter w) {
        int n = in.ii();
        int m = in.ii();
        // computers numbered from 1 to n
        MaxFlowDinic maxflow = new MaxFlowDinic(n + 1);
        int src = 1, dst = n;
        for (int i = 0; i < m; i++) {
            int u = in.ii();
            int v = in.ii();
            int c = in.ii();
            maxflow.addEdge(u, v, c);
        }
        w.println(maxflow.maxFlow(src, dst));
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