#include <string>
#include <iostream>

using namespace std;

bool dp[5001][5003][3];
int main()
{
    string s;
    getline(cin, s);
    int n = s.length();

    int BEFORE = 0;
    int MIDDLE = 1;
    int AFTER = 2;
    for (int i = 0; i <= n; i++)
    {
        for (int j = 0; j <= n + 1; j++)
        {
            for (int k = 0; k < 3; k++)
                dp[i][j][k] = false;
        }
    }
    for (int i = 0; i < 3; i++)
        dp[n][n / 2][i] = true;
    for (int idx = n - 1; idx >= 0; idx--)
    {
        for (int lefts = 0; lefts <= n; lefts++)
        {
            int next_left = lefts, next_flip_left = lefts;
            if (s[idx] == '(')
                next_left++;
            else
            {
                next_flip_left++;
            }
            int right = idx - lefts;

            if (lefts < right)
                continue;
            // BEFORE state
            dp[idx][lefts][BEFORE] = dp[idx + 1][next_left][BEFORE] || dp[idx + 1][next_flip_left][MIDDLE];

            // MIDDLE state
            dp[idx][lefts][MIDDLE] = dp[idx + 1][next_left][AFTER] || dp[idx + 1][next_flip_left][MIDDLE];

            // AFTER state
            dp[idx][lefts][AFTER] = dp[idx + 1][next_left][AFTER];
        }
    }

    if (dp[0][0][BEFORE] == true)
        cout << "possible";
    else
    {
        cout << "impossible";
    }
    return 0;
}
