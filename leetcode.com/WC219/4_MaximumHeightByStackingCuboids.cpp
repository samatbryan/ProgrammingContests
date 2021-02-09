class Solution
{
    struct Cube
    {
        int w;
        int l;
        int h;
        int id;

        Cube(int w, int l, int h, int id)
        {
            this->w = w;
            this->l = l;
            this->h = h;
            this->id = id;
        }
    };

    static bool fn(const Cube &a, const Cube &b)
    {
        if (a.w == b.w)
        {
            if (a.l == b.l)
                return a.h < b.h;
            return a.l < b.l;
        }
        return a.w < b.w;
    }

public:
    int maxHeight(vector<vector<int>> &cuboids)
    {
        vector<Cube> cubes;
        for (int i = 0; i < cuboids.size(); i++)
        {
            vector<int> &c = cuboids[i];
            int w = c[0];
            int l = c[1];
            int h = c[2];

            cubes.push_back(Cube{w, l, h, i});
            cubes.push_back(Cube{w, h, l, i});
            cubes.push_back(Cube{l, w, h, i});
            cubes.push_back(Cube{l, h, w, i});
            cubes.push_back(Cube{h, l, w, i});
            cubes.push_back(Cube{h, w, l, i});
        }

        sort(cubes.begin(), cubes.end(), &fn);
        int n = cubes.size();
        int dp[805] = {0};

        int res = INT_MIN;
        for (int i = 1; i <= n; i++)
        {
            Cube &cur = cubes[i - 1];
            dp[i] = cur.h;

            for (int j = 1; j < i; j++)
            {
                Cube &prev = cubes[j - 1];

                if (prev.id != cur.id && prev.w <= cur.w && prev.l <= cur.l && prev.h <= cur.h)
                {
                    dp[i] = max(dp[i], dp[j] + cur.h);
                }
            }
            res = max(res, dp[i]);
        }

        return res;
    }
};