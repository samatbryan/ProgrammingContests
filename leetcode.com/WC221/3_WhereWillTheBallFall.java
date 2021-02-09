/*
 * 1706. Where Will the Ball Fall User Accepted:1666 User Tried:1872 Total
 * Accepted:1712 Total Submissions:3515 Difficulty:Medium You have a 2-D grid of
 * size m x n representing a box, and you have n balls. The box is open on the
 * top and bottom sides.
 * 
 * Each cell in the box has a diagonal board spanning two corners of the cell
 * that can redirect a ball to the right or to the left.
 * 
 * A board that redirects the ball to the right spans the top-left corner to the
 * bottom-right corner and is represented in the grid as 1. A board that
 * redirects the ball to the left spans the top-right corner to the bottom-left
 * corner and is represented in the grid as -1. We drop one ball at the top of
 * each column of the box. Each ball can get stuck in the box or fall out of the
 * bottom. A ball gets stuck if it hits a "V" shaped pattern between two boards
 * or if a board redirects the ball into either wall of the box.
 * 
 * Return an array answer of size n where answer[i] is the column that the ball
 * falls out of at the bottom after dropping the ball from the ith column at the
 * top, or -1 if the ball gets stuck in the box.
 */
class Solution {
    public int[] findBall(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;

        HashSet<Pair<Integer, Integer>> bad = new HashSet();

        for (int r = 0; r < m; r++) {
            for (int c = 0; c < n - 1; c++) {
                if (grid[r][c] == 1 && grid[r][c + 1] == -1) {
                    bad.add(new Pair(r, c));
                    bad.add(new Pair(r, c + 1));
                }
            }
        }
        int[] res = new int[n];

        for (int c = 0; c < n; c++) {
            int cur_r = 0;
            int cur_c = c;

            while (true) {
                Pair<Integer, Integer> cur = new Pair(cur_r, cur_c);
                if (bad.contains(cur)) {
                    res[c] = -1;
                    break;
                }
                if (cur_r == m) {
                    res[c] = cur_c;
                    break;
                }
                if (grid[cur_r][cur_c] == 1) {
                    cur_c++;
                } else {
                    cur_c--;
                }

                cur_r++;
                if (cur_c < 0 || cur_c >= n) {
                    res[c] = -1;
                    break;
                }

            }
        }
        return res;
    }
}