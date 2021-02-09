import sys
s = sys.stdin.readline().rstrip()
n = len(s)
sys.setrecursionlimit(10000)

BEFORE = 0
MIDDLE = 1
AFTER = 2
from functools import lru_cache

@lru_cache(None)
def f(idx, used, lefts):
    rights = idx - lefts
    if idx == n:
        return lefts == rights
    if lefts < rights:
        return False
    res, next_lefts, next_flip_lefts = False, lefts, lefts
    if s[idx] == '(':
        next_lefts +=1
    else:
        next_flip_lefts +=1

    if used == BEFORE or used == MIDDLE:
        # 1. try to use it
        res = res or f(idx+1, MIDDLE, next_flip_lefts)
        
        # 2. try not to use it
        next_used = BEFORE
        if used == MIDDLE:
            next_used = AFTER
        res = res or f(idx+1, next_used, next_lefts)
    else:
        # only one option, cant use it anymore
        res = res or f(idx+1, AFTER, next_lefts)

    return res

if n%2 == 1:
    print("impossible")
elif f(0,BEFORE,0):
    print("possible")
else:
    print("impossible")