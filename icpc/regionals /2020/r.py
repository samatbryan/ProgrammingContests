from functools import lru_cache
import math
import os
import io
# from collections import defaultdict

input = io.BytesIO(os.read(0, os.fstat(0).st_size)).readline
# WARNING
# this turns binary strings ex. "0011" to ints by default
# making rr(), read impossible as a string


def rr(): return input()
def rri(): return int(input())
def rrm(): return list(map(int, input().split()))


INF = float('inf')

kk = rrm()
h, b, t, x, y, z = kk
b1, b2 = t, b
bbb = [x, y, z]


@lru_cache(None)
def f(cur_h):
    res = 0
    for cand in bbb:
        if cur_h + cand <= h:
            y = cur_h + cand
            next_width = y*b1/h + (h-y)*b2/h
            # closest multiple of cand <= next_width
            boxes = int(next_width // cand)
            res = max(res, boxes * boxes * cand * cand * cand + f(y))
    return res


print(f(0))
