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
n, m = rrm()
nums = []
for i in range(m):
    nums.append(rrm())


def f(idx, uses, have):
    if uses < 0:
        return False
    if idx >= m:
        return True
    x, y = nums[idx]
    if(x not in have) and (y not in have) and uses == 0:
        return False
    res = False
    if x in have or y in have:
        res = f(idx+1, uses, have)
    return res or f(idx+1, uses-1, have + [x]) or f(idx+1, uses-1, have + [y])


if (f(0, 2, [])):
    print("YES")
else:
    print("NO")
