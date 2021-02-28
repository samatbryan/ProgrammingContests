import java.util.HashSet;

class Solution {
    public int[][] highestPeak(int[][] isWater) {
        LinkedList<int[]> q = new LinkedList();
        int m = isWater.length;
        int n = isWater[0].length;

        Integer[][] res = new Integer[m][n];
        int[] dr = { 0, 0, 1, -1 };
        int[] dc = { 1, -1, 0, 0 };
        HashSet<Long> visited = new HashSet();

        for (int r = 0; r < m; r++) {
            for (int c = 0; c < n; c++) {
                if (isWater[r][c] == 1) {
                    visited.add(H(r, c));
                    for (int z = 0; z < 4; z++) {
                        res[r][c] = 0;
                        int new_r = r + dr[z];
                        int new_c = c + dc[z];
                        if (new_r < 0 || new_c < 0 || new_r >= m || new_c >= n)
                            continue;
                        long hash = H(new_r, new_c);
                        if (isWater[new_r][new_c] == 1)
                            continue;
                        if (!visited.contains(hash)) {
                            q.addLast(new int[] { new_r, new_c });
                            visited.add(hash);
                        }
                    }
                }
            }
        }

        while (q.size() > 0) {
            int size = q.size();
            for (int i = 0; i < size; i++) {
                int[] cur = q.removeFirst();
                int r = cur[0], c = cur[1];
                int val = Integer.MAX_VALUE;
                for (int z = 0; z < 4; z++) {

                    int new_r = r + dr[z];
                    int new_c = c + dc[z];
                    if (new_r < 0 || new_c < 0 || new_r >= m || new_c >= n)
                        continue;

                    if (res[new_r][new_c] != null)
                        val = Math.min(val, res[new_r][new_c]);
                    long hash = H(new_r, new_c);
                    if (!visited.contains(hash)) {
                        visited.add(hash);
                        q.addLast(new int[] { new_r, new_c });
                    }
                }
                res[r][c] = val + 1;
            }
        }
        int[][] rr = new int[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                int v = res[i][j];
                rr[i][j] = v;
            }
        }
        return rr;
    }

    public long H(int r, int c) {
        long rr = r, cc = c;
        return 1007 * rr + cc;
    }

}