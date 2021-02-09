import java.util.*;

class Solution {
    public int solve(int[] votes) {
        int n = votes.length;
        ArrayList<Integer>[] graph = new ArrayList[n];
        LinkedList<Integer> q = new LinkedList();

        for (int i = 0; i < n; i++)
            graph[i] = new ArrayList();
        for (int i = 0; i < n; i++) {
            if (votes[i] < 0)
                q.addLast(i);
            else if (votes[i] >= n)
                q.addLast(i);
            else {
                graph[votes[i]].add(i);
            }
        }

        int a_votes = 0;
        while (q.size() > 0) {
            int size = q.size();
            for (int i = 0; i < size; i++) {
                int cur_idx = q.removeFirst();
                if (votes[cur_idx] < 0)
                    a_votes++;
                for (int neighbor : graph[cur_idx]) {
                    q.addLast(neighbor);
                    if (votes[cur_idx] < 0)
                        votes[neighbor] = -1;
                }
            }
        }
        return a_votes;
    }
}