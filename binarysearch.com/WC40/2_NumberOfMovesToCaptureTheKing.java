;
import java.util.*;

class Solution {
    int[] dr = {-1, -1, 1, 1, 2, 2, -2, -2};
    int[] dc = {2, -2, 2, -2, 1, -1, 1, -1};

    public int solve(int[][] board) {
        LinkedList<Pair<Integer, Integer>> q = new LinkedList();
        HashSet<Pair<Integer, Integer>> hs = new HashSet();
        int m = board.length, n = board[0].length;
        int king_r = 0, king_c = 0;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] == 1) {
                    q.addLast(new Pair(i, j));
                    hs.add(new Pair(i, j));
                } else if (board[i][j] == 2) {
                    king_r = i;
                    king_c = j;
                }
            }
        }
        int moves = 0;
        while (q.size() > 0) {
            int size = q.size();
            for (int i = 0; i < size; i++) {
                Pair<Integer, Integer> cur = q.removeFirst();
                int cur_r = cur.getKey(), cur_c = cur.getValue();
                if (cur_r == king_r && cur_c == king_c)
                    return moves;
                for (int move = 0; move < 8; move++) {
                    int new_r = cur_r + dr[move];
                    int new_c = cur_c + dc[move];
                    if (new_r < 0 || new_c < 0 || new_c >= n || new_r >= m)
                        continue;
                    if (hs.contains(new Pair(new_r, new_c)))
                        continue;
                    hs.add(new Pair(new_r, new_c));
                    q.addLast(new Pair(new_r, new_c));
                }
            }
            moves++;
        }
        return -1;
    }
}