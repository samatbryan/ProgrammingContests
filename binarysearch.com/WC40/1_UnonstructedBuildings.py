class Solution:
    def solve(self, heights):
        n, maxheight = len(heights), -10000000
        res = []
        for i in range(n-1, -1, -1):
            if heights[i] > maxheight:
                res.append(i)
            maxheight = max(maxheight, heights[i])
        res.sort()
        return res
