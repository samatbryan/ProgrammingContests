# from collections import defaultdict

from functools import lru_cache
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


total = 0
nums = []


@lru_cache(None)
def less(idx, cur_sum):
    if cur_sum > total:
        return 0
    if idx == len(nums):
        return 1 if cur_sum < total else 0
    res = 0
    for i in range(31):
        res += less(idx+1, cur_sum + i)
    return res


@lru_cache(None)
def same(idx, tight, cur_sum):
    if cur_sum > total:
        return 0
    if idx == len(nums):
        return 1 if cur_sum == total else 0
    res, up = 0, 30
    if tight:
        up = nums[idx]
    for i in range(up+1):
        new_tight = tight
        if tight and i < nums[idx]:
            new_tight = False
        res += same(idx+1, new_tight, cur_sum + i)
    return res


while True:

    nums = rrm()
    if(nums[0] == 0):
        break
    nums = nums[1:]
    total = sum(nums)
    less.cache_clear()
    same.cache_clear()

    print(less(0, 0) + same(0, True, 0))
