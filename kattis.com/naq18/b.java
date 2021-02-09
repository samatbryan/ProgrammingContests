class Solution {
    int cj;
    int mj;
    String[] grid;
    int fo_r;
    int fo_c;

    public boolean canMouseWin(String[] grid, int catJump, int mouseJump) {
        this.grid = grid;
        this.cj = catJump;
        this.mj = mouseJump;

        int m = grid.length, n = grid[0].length();
        int mo_r = 0, mo_c = 0, co_r = 0, co_c = 0, fo_r = 0, fo_c = 0;

        for (int r = 0; r < m; r++) {
            for (int c = 0; c < n; c++) {
                if (grid[r].charAt(c) == ('M')) {
                    mo_r = r;
                    mo_c = c;
                } else if (grid[r].charAt(c) == ('M')) {
                    co_r = r;
                    co_c = c;
                } else if (grid[r].charAt(c) == ('F')) {
                    this.fo_r = r;
                    this.fo_c = c;
                }
            }
        }
        boolean dp[][][][][] = new boolean[m][n][m][n][2];

    }

    public boolean CAN_WIN(int m_r, int m_c, int c_r, int c_c, int turn_num, Boolean dp[][][][][]) {
        int m = grid.length, n = grid[0].length();

        if (turn_num > 1000) {
            if (turn_num % 2 == 0)
                return false;
            return true;
        }
        if (dp[m_r][m_c][c_r][c_c][turn_num % 2] != null)
            return dp[m_r][m_c][c_r][c_c][turn_num % 2];
        if (m_r == fo_r && m_c == fo_c) {
            if (turn_num % 2 == 1)
                return false;
            return true;
        }
        if (c_r == fo_r && c_c == fo_c) {
            if (turn_num % 2 == 0)
                return false;
            return true;
        }

        if (c_r == m_r && c_c == m_c) {
            if (turn_num % 2 == 0)
                return false;
            return true;
        }

        if (turn_num % 2 == 0) { // mouses turn
            for (int left_c = m_c - 1; left_c >= Math.max(0, m_c - this.mj); left_c--) {
                if (grid[m_r].charAt(left_c) == '#')
                    break;
                if (CAN_WIN(m_r, left_c, c_r, c_c, turn_num + 1, dp))
                    return dp[m_r][m_c][c_r][c_c][turn_num % 2] = true;
            }

            for (int right_c = m_c + 1; right_c <= Math.max(n - 1, m_c + this.mj); right_c++) {
                if (grid[m_r].charAt(right_c) == '#')
                    break;
                if (CAN_WIN(m_r, right_c, c_r, c_c, turn_num + 1, dp))
                    return dp[m_r][m_c][c_r][c_c][turn_num % 2] = true;
            }

            for (int up_r = m_r - 1; up_r >= Math.max(0, m_r - this.mj); up_r--) {
                if (grid[up_r].charAt(m_c) == '#')
                    break;
                if (CAN_WIN(up_r, m_c, c_r, c_c, turn_num + 1, dp))
                    return dp[m_r][m_c][c_r][c_c][turn_num % 2] = true;
            }
            for (int d_r = m_r + 1; d_r >= Math.max(m - 1, m_r + this.mj); d_r++) {
                if (grid[d_r].charAt(m_c) == '#')
                    break;
                if (CAN_WIN(d_r, m_c, c_r, c_c, turn_num + 1, dp))
                    return dp[m_r][m_c][c_r][c_c][turn_num % 2] = true;
            }
            return dp[m_r][m_c][c_r][c_c][turn_num % 2] = false;
        } else {
            for (int left_c = c_c - 1; left_c >= Math.max(0, c_c - this.cj); left_c--) {
                if (grid[c_r].charAt(left_c) == '#')
                    break;
                if (CAN_WIN(m_r, m_c, c_r, left_c, turn_num + 1, dp))
                    return dp[m_r][m_c][c_r][c_c][turn_num % 2] = true;
            }
            for (int right_c = c_c + 1; right_c <= Math.max(n - 1, c_c + this.cj); right_c++) {
                if (grid[c_r].charAt(right_c) == '#')
                    break;
                if (CAN_WIN(m_r, m_c, c_r, right_c, turn_num + 1, dp))
                    return dp[m_r][m_c][c_r][c_c][turn_num % 2] = true;
            }
            for (int up_r = c_r - 1; up_r >= Math.max(0, c_r - this.cj); up_r--) {
                if (grid[up_r].charAt(c_c) == '#')
                    break;
                if (CAN_WIN(c_r, m_c, up_r, c_c, turn_num + 1, dp))
                    return dp[m_r][m_c][c_r][c_c][turn_num % 2] = true;
            }
            for (int d_r = c_r + 1; d_r >= Math.max(m - 1, c_r + this.cj); d_r++) {
                if (grid[d_r].charAt(c_c) == '#')
                    break;
                if (CAN_WIN(m_r, m_c, d_r, c_c, turn_num + 1, dp))
                    return dp[m_r][m_c][c_r][c_c][turn_num % 2] = true;
            }

            return dp[m_r][m_c][c_r][c_c][turn_num % 2] = false;
        }

    }
}