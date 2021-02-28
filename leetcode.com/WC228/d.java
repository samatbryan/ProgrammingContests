class Solution {
    public int minTrioDegree(int n, int[][] edges) {
        HashSet<Integer>[] graph = new HashSet[n];
        for (int i = 0; i < n; i++)
            graph[i] = new HashSet();
        for (int[] e : edges) {
            int u = e[0];
            int v = e[1];
            graph[u].add(v);
            graph[v].add(u);
        }
        int res = Integer.MAX_VALUE;
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                for (int k = j + 1; k < n; k++) {
                    if (graph.get(i).contains(j) && graph.get(j).contains(k) && graph.get(k).contains(i)) {
                        // cycle here
                        int tmp = graph.get(i).size() + graph.get(j).size() + graph.get(k).size() - 6;
                        res = Math.min(res, tmp);
                    }
                }
            }
        }
        if (res == Integer.MAX_VALUE)
            return -1;
        return res;
    }
}