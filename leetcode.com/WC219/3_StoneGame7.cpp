class Solution
{
public:
    int stoneGameVII(vector<int> &stones)
    {
        int n = stones.size();
        int prefix[1005] = {0};
        for (int i = 1; i <= n; i++)
        {
            prefix[i] = prefix[i - 1] + stones[i - 1];
        }
        int dp[1005][1005] = {0};

        for (int L = 2; L <= n; L++)
        {
            for (int i = 0; i < n; i++)
            {
                if (i + L - 1 >= n)
                    break;
                // remove left: [i+1, i+L-1]
                dp[i][i + L - 1] = (prefix[i + L] - prefix[i + 1]) - dp[i + 1][i + L - 1];
                // remove right: [i, i+L-2]
                dp[i][i + L - 1] = dp[i][i + L - 1] = max(dp[i][i + L - 1], (prefix[i + L - 1] - prefix[i]) - dp[i][i + L - 2]);
            }
        }
        return dp[0][n - 1];
    }
};