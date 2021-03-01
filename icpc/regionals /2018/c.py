# from collections import defaultdict

import sys
from functools import lru_cache
import io
import os
import math
input = io.BytesIO(os.read(0, os.fstat(0).st_size)).readline
# WARNING
# this turns binary strings ex. "0011" to ints by default
# making rr(), read impossible as a string

sys.setrecursionlimit(10000)


def rr(): return input()
def rri(): return int(input())
def rrm(): return list(map(int, input().split()))


INF = float('inf')


n, k = rrm()
nums = rrm()

mod = 998244353
nums.sort()
last = dict()
for i in range(len(nums)):
    last[nums[i]] = i


@lru_cache(None)
def f(i, chosen):
    if i == len(nums):
        return 1 if chosen == k else 0
    res = f(last[nums[i]] + 1, chosen + 1)
    res = (res + f(i+1, chosen)) % mod
    return res


print(f(0, 0))
