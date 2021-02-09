import java.util.*;

class Solution {
    static class Dsu {
        int n;
        int[] parents;

        Dsu(int n) {
            this.parents = new int[n];
            this.n = n;
        }

        void connect(int a, int b) {
            this.a = get_parent(a);
            this.b = get_parent(b);
            parents[a] = b;
        }

        int get_parent(int a) {
            if (parent[a] == a)
                return a;
            return parent[a] = get_parent(parent[a]);
        }

    }

    static class Edge {
        int u, v, weight;

        Edge(int u, int v, int weight) {
            this.u = u;
            this.v = v;
            this.weight = weight;
        }
    }

    public int solve(int[][] matrix) {
        int[] dr = new int[] { 1, 0 };
        int[] dc = new int[] { 0, 1 };

        ArrayList<Edge> edges = new ArrayList();
        int m = matrix.length, n = matrix[0].length;
        int left = 0;
        int right = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                for (int neighbor = 0; neighbor < 2; neighbor++) {
                    int new_i = i + dr[neighbor];
                    int new_j = j + dc[neighbor];

                    if (new_i < m && new_j < n) {
                        int u = new_i * m + new_j;
                        int v = i * m + j;
                        int w = Math.abs(matrix[i][j] - matrix[new_i][new_j]);
                        edges.add(new Edge(u, v, w));
                        right = Math.max(w, right);
                    }
                }
            }
        }
        Collections.sort(edges, (a, b) -> (a.weight - b.weight));
        int res = right;
        while (left <= right) {
            int mid = left + (right - left) / 2;

            Dsu dsu = new dsu(m * n);
            for (Edge e : edges) {
                int u = e.u, v = e.v, weight = e.weight;
                if (weight > mid)
                    break;
                dsu.connect(u, v);
            }
            if (dsu.get_parent(0) == dsu.get_parent((m - 1) * (n - 1))) {
                res = Math.min(res, m);
                r = mid - 1;
            } else {
                l = mid + 1;
            }
        }

        return res;
    }

    public int hash(int x, int y, int m) {
        return x * m + y;
    }
}