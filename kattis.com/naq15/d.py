# from collections import defaultdict
import io
import os
import math
import sys
sys.setrecursionlimit(10000)

input = io.BytesIO(os.read(0, os.fstat(0).st_size)).readline
# WARNING
# this turns binary strings ex. "0011" to ints by default
# making rr(), read impossible as a string


def rr(): return input()
def rri(): return int(input())
def rrm(): return list(map(int, input().split()))


INF = float('inf')

n = rri()
nums = []
for i in range(n):
    pair =rrm()
    nums.append(pair)

from functools import lru_cache

@lru_cache(None)
def f(idx, x, y, used):
    res = 0
    if idx == n:
        if used and x == 0 and y == 0:
            return 1
        return 0
    
    if idx < n:
        # skip this
        res += f(idx+1, x, y, used)

        # use this
        res += f(idx+1, x+nums[idx][0], y+nums[idx][1], True)
    return res

print(f(0,0,0, False)) 
