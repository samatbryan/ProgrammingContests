class Solution
{
public:
    int maxOperations(vector<int> &nums, int k)
    {
        unordered_map<int, int> hm;
        int res = 0;
        for (int num : nums)
        {
            if (hm[k - num] > 0)
            {
                hm[k - num]--;
                res++;
                continue;
            }
            hm[num]++;
        }
        return res;
    }
};