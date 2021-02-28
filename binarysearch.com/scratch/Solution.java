import java.util.*;

class Solution {
    static class Node {
        int node;
        long cost;
        int bus;

        Node(int node, long cost, int bus) {
            this.node = node;
            this.cost = cost;
            this.bus = bus;
        }
    }

    public int solve(int[][] connections) {
        HashMap<Integer, ArrayList<Node>> graph = new HashMap();
        int src = 0;
        int dst = 0;
        for (int[] c : connections) {
            int s = c[0], t = c[1], b = c[2];
            graph.putIfAbsent(s, new ArrayList());
            graph.get(s).add(new Node(t, 0l, b));
            dst = Math.max(s, dst);
            dst = Math.max(t, dst);
        }

        PriorityQueue<Node> pq = new PriorityQueue<Node>((a, b) -> (Long.compare(a.cost, b.cost)));
        pq.add(new Node(0, 0, -1));
        HashMap<Integer, Long> dists = new HashMap();
        dists.put(0, 0L);
        HashSet<Integer> popped = new HashSet();

        while (pq.size() > 0) {
            Node cur_node = pq.poll();
            int cur = cur_node.node;
            long cost = cur_node.cost;
            int bus = cur_node.bus;
            if (popped.contains(cur))
                continue;
            popped.add(cur);

            for (Node neighbor_node : graph.getOrDefault(cur, new ArrayList<Node>())) {
                int neighbor = neighbor_node.node;
                int next_bus = neighbor_node.bus;
                if (popped.contains(neighbor))
                    continue;

                long prev_dist = dists.getOrDefault(neighbor, (long) Integer.MAX_VALUE);
                long edge_cost = next_bus == bus ? 0 : 1;
                if (edge_cost + cost < prev_dist) {
                    dists.put(neighbor, edge_cost + cost);
                    pq.add(new Node(neighbor, edge_cost + cost, next_bus));
                }
            }
        }

        long res = dists.get(dst);
        return (int) res;
    }
}