
class Solution {

    static class item {
        int node;
        int level;

        item(int node, int level) {
            this.node = node;
            this.level = level;
        }
    }

    int gcd(int a, int b) {
        if (a == 0)
            return b;
        return gcd(b % a, a);
    }

    public boolean coprime(int a, int b) {
        return gcd(a, b) == 1;
    }

    int[] res;
    ArrayList<Integer>[] graph;
    int[] nums;
    HashMap<Integer, LinkedList<item>> hm;

    public int[] getCoprimes(int[] nums, int[][] edges) {
        this.hm = new HashMap(); // maps node values to indices
        this.nums = nums;
        int n = nums.length;
        graph = new ArrayList[n];
        for (int i = 0; i < n; i++)
            graph[i] = new ArrayList();
        for (int[] e : edges) {
            int u = e[0], v = e[1];
            graph[u].add(v);
            graph[v].add(u);
        }
        this.res = new int[n];
        dfs(0, 0, new HashSet());

        return res;
    }

    public void dfs(int cur, int level, HashSet<Integer> visited) {
        if (visited.contains(cur))
            return;
        visited.add(cur);
        int cur_val = nums[cur];
        int best_level = -1;
        int best = -1;
        for (int val = 1; val <= 50; val++) {
            if (!coprime(val, cur_val))
                continue;
            if (hm.containsKey(val) && hm.get(val).size() > 0) {
                item cur_item = hm.get(val).getLast();
                if (cur_item.level > best_level) {
                    best = cur_item.node;
                    best_level = cur_item.level;
                }
            }
        }
        res[cur] = best;
        hm.putIfAbsent(cur_val, new LinkedList());
        hm.get(cur_val).addLast(new item(cur, level));
        for (int neighbor : graph[cur]) {
            if (!visited.contains(neighbor)) {
                dfs(neighbor, level + 1, visited);
            }
        }

        hm.get(cur_val).removeLast();
    }
}