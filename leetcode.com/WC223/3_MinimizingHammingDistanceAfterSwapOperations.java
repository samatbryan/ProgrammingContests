class Solution {
    public int minimumHammingDistance(int[] source, int[] target, int[][] swaps) {
        int n = source.length;
        ArrayList<Integer>[] graph = new ArrayList[n];
        for (int i = 0; i < n; i++)
            graph[i] = new ArrayList();

        HashSet<Integer> indices = new HashSet();

        for (int[] s : swaps) {
            int u = s[0], v = s[1];
            graph[u].add(v);
            graph[v].add(u);
            indices.add(u);
            indices.add(v);
        }
        HashSet<Integer> visited = new HashSet();
        int res = n;
        for (int i = 0; i < n; i++) {
            if (!indices.contains(i)) {
                if (source[i] == target[i])
                    res--;
            } else if (!visited.contains(i)) {
                HashMap<Integer, Integer> left = new HashMap();
                HashMap<Integer, Integer> right = new HashMap();
                dfs(graph, source, target, visited, i, left, right);
                for (int num : left.keySet()) {
                    res -= Math.min(left.get(num), right.getOrDefault(num, 0));
                }
            }

        }
        return res;
    }

    public void dfs(ArrayList<Integer>[] graph, int[] source, int[] target, HashSet<Integer> visited, int cur,
            HashMap<Integer, Integer> left, HashMap<Integer, Integer> right) {
        visited.add(cur);
        left.put(source[cur], left.getOrDefault(source[cur], 0) + 1);
        right.put(target[cur], right.getOrDefault(target[cur], 0) + 1);
        for (int neighbor : graph[cur]) {
            if (!visited.contains(neighbor)) {
                dfs(graph, source, target, visited, neighbor, left, right);
            }
        }
    }
}