"""
1695. Maximum Erasure Value
User Accepted:2829
User Tried:3657
Total Accepted:2897
Total Submissions:6816
Difficulty:Medium
You are given an array of positive integers nums and want to erase a subarray containing unique elements. The score you get by erasing the subarray is equal to the sum of its elements.

Return the maximum score you can get by erasing exactly one subarray.

An array b is called to be a subarray of a if it forms a contiguous subsequence of a, that is, if it is equal to a[l],a[l+1],...,a[r] for some (l,r).
"""
class Solution:
    def maximumUniqueSubarray(self, nums: List[int]) -> int:
        d = set()
        n = len(nums)
        right = 0
        tot = 0
        
        res = float('-inf')
        while right < n and nums[right] not in d:
            d.add(nums[right])
            tot += nums[right]
            right +=1
            res = max(res, tot)
        
        for left in range(1, n):
            d.remove(nums[left-1])
            tot -= nums[left-1]
            while right < n and nums[right] not in d:
                d.add(nums[right])
                tot += nums[right]
                right +=1
            res = max(res, tot)
        return res
