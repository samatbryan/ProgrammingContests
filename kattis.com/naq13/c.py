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
m = rri()
graph = dict()
for i in range(m):
    graph[i] = set()
n = rri()
for i in range(n):
    s, t = rrm()
    graph[s].add(t)

import itertools
nodes = [i for i in range(m)]
res = 0
seen = set()
for length in range(2, m+1): 
    for order in itertools.permutations(nodes, length):
        can = True
        for i in range(length):
            cur, prev = order[i], order[i-1]
            if not cur in graph[prev]:
                can = False
        if can:
            if order not in seen:
                for shift in range(length):
                    seen.add(order[shift:] + order[:shift])
                res +=1
                
                

print(res)