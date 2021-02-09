import java.util.*;

class Solution {
    public boolean solve(int[][] edges, int a, int b) {
        HashMap<Integer, ArrayList<Integer>> graph = new HashMap();

        for (int[] edge : edges) {
            int parent = edge[0];
            int child = edge[1];

            graph.putIfAbsent(child, new ArrayList());
            graph.get(child).add(parent);
        }

        HashSet<Integer> path1 = new HashSet();
        HashSet<Integer> path2 = new HashSet();
        dfs(graph, path1, new HashSet(), a);
        dfs(graph, path2, new HashSet(), b);

        for (int p : path1) {
            if (path2.contains(p))
                return true;
        }
        return false;
    }

    public void dfs(HashMap<Integer, ArrayList<Integer>> graph, HashSet<Integer> path1, HashSet<Integer> visited,
            int c) {
        path1.add(c);
        visited.add(c);

        for (int neighbor : graph.getOrDefault(c, new ArrayList<Integer>())) {
            if (!visited.contains(neighbor)) {
                dfs(graph, path1, visited, neighbor);
            }
        }
    }
}