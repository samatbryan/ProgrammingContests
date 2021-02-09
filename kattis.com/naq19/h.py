# from collections import defaultdict

import io
import os
import math
input = io.BytesIO(os.read(0, os.fstat(0).st_size)).readline
# WARNING
# this turns binary strings ex. "0011" to ints by default
# making rr(), read impossible as a string


def rr(): return input()
def rri(): return int(input())
def rrm(): return list(map(int, input().split()))


INF = float('inf')


n = rri()
edges = list()
for i in range(n):
    edges.append(rrm())

from functools import lru_cache

@lru_cache(None)
def f(left, right): 
    if left >= right:
        return 0
    res = f(left, right-1)
    for mid in range(left, right):
        if edges[mid][right] == 1:
            res = max(res, 1 + f(left, mid - 1) + f(mid+1, right-1))
    return res

print(f(0,n-1))