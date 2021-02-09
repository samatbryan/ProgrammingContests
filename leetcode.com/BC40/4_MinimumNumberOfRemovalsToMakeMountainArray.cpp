class Solution {
    public:
        int minimumMountainRemovals(vector<int>& nums) {
            int n = nums.size();
            vector<int> copy = nums;
            reverse(copy.begin(), copy.end());
            int res = n;
            
            vector<int> end_at = lis(nums);
            vector<int> start_at = lis(copy);
            
            reverse(start_at.begin(), start_at.end());
            
            for(int i=1; i<n-1; i++){
                if(end_at[i] == 1 || start_at[i] == 1) continue;
                res = min(res, n-(end_at[i] + start_at[i] - 1));
            }
            return res;
        }
        
        vector<int> lis(vector<int> &nums)
    {
        int n = nums.size();
        vector<int> dp;
        vector<int> lis(n+1, INT_MAX);
            
        lis[0] = INT_MIN;
    
        for (int num : nums)
        {
            int left = 0;
            int right = lis.size() - 1;
            int insert_idx = 1;
    
            // find the number of elements strictly less than num
            while (left <= right)
            {
                int mid = left + (right - left) / 2;
                if (lis[mid] < num)
                {
                    insert_idx = max(insert_idx, mid + 1);
                    left = mid + 1;
                }
                else
                {
                    right = mid - 1;
                }
            }
            dp.push_back(insert_idx);
            lis[insert_idx] = num;
        }
        return dp;
    }
    };