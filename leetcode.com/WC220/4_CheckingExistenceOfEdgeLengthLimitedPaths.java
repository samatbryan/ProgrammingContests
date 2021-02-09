/*
 * https://leetcode.com/contest/weekly-contest-220/problems/checking-existence-
 * of-edge-length-limited-paths/
 * 
 * 1697. Checking Existence of Edge Length Limited Paths User Accepted:325 User
 * Tried:731 Total Accepted:354 Total Submissions:1384 Difficulty:Hard An
 * undirected graph of n nodes is defined by edgeList, where edgeList[i] = [ui,
 * vi, disi] denotes an edge between nodes ui and vi with distance disi. Note
 * that there may be multiple edges between two nodes.
 * 
 * Given an array queries, where queries[j] = [pj, qj, limitj], your task is to
 * determine for each queries[j] whether there is a path between pj and qj such
 * that each edge on the path has a distance strictly less than limitj .
 * 
 * Return a boolean array answer, where answer.length == queries.length and the
 * jth value of answer is true if there is a path for queries[j] is true, and
 * false otherwise.
 */

class Solution {
    public class LightDsu {
        private int[] size;
        private int[] parents;
        private int roots;
        private int N;

        /**
         * Class constructor for the Light Disjoint Set Union class, uses integers
         * exclusively Instantiates N exclusive sets indexed from 0 to N-1 example
         * usage: LightDsu dsu = new LightDsu();
         */
        LightDsu(int N) {
            this.N = N;
            this.roots = N;
            this.size = new int[N];
            this.parents = new int[N];
            for (int i = 0; i < N; i++)
                this.parents[i] = i;
            Arrays.fill(size, 1);
        }

        /**
         * Time Complexity: O(log(n)) Takes a new vertex a and returns the root. Uses
         * Path Compression Path Compression is where you find the root, and set all
         * node's parent along that path to root
         * 
         * @param a The vertex to find the root of
         * @return The root of the vertex a
         */
        public int find_set(int a) {
            if (this.parents[a] == a)
                return a;
            return this.parents[a] = find_set(this.parents[a]);
        }

        /**
         * Time Complexity: O(log(n)) Takes a two vertex a and b and joins the two sets.
         * Uses Union by Size
         * 
         * @param a The first set to join
         * @param b The second set to join
         */
        public void union_set(int a, int b) {
            a = find_set(a);
            b = find_set(b);

            if (a != b) {
                this.roots--;
                if (this.size[a] < this.size[b]) {
                    int c = a;
                    a = b;
                    b = c;
                }
                this.size[a] += this.size[b];
                this.parents[b] = a;
            }
        }

        /**
         * Time Complexity: O(1) Returns the number of roots
         * 
         * @return The number of distinct roots
         */
        public int num_roots() {
            return this.roots;
        }
    }

    static class Triple {
        int u;
        int v;
        int w;
        int idx;

        Triple(int u, int v, int w, int idx) {
            this.u = u;
            this.v = v;
            this.w = w;
            this.idx = idx;
        }
    }

    public boolean[] distanceLimitedPathsExist(int n, int[][] edgeList, int[][] queries) {
        LightDsu dsu = new LightDsu(n);
        int q = queries.length;

        Arrays.sort(edgeList, new Comparator<int[]>() {
            @Override
            public int compare(int[] a, int[] b) {
                return a[2] - b[2];
            }
        });

        ArrayList<Triple> query = new ArrayList();
        for (int i = 0; i < q; i++) {
            int[] qq = queries[i];
            query.add(new Triple(qq[0], qq[1], qq[2], i));
        }
        Collections.sort(query, (a, b) -> (a.w - b.w));

        boolean[] res = new boolean[q];

        int edge_idx = 0;
        int q_idx = 0;

        for (int i = 0; i < q; i++) {
            Triple cur_query = query.get(i);
            int u = cur_query.u;
            int v = cur_query.v;
            int weight = cur_query.w;
            int idx = cur_query.idx;

            while (edge_idx < edgeList.length && edgeList[edge_idx][2] < weight) {
                dsu.union_set(edgeList[edge_idx][0], edgeList[edge_idx][1]);
                edge_idx++;
            }

            if (dsu.find_set(u) == dsu.find_set(v)) {
                res[idx] = true;
            } else {
                res[idx] = false;
            }
        }
        return res;

    }
}