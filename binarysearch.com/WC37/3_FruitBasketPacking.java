/*
https://binarysearch.com/room/Weekly-Contest-37-u2kU8duwTB?questionsetIndex=2

You are given a two-dimensional list of integers fruits. Each fruits[i] contains [cost, size, total], meaning that fruit i costs cost each, each one has size of size, and there are total of total of them. You're also given k number of fruit baskets of size size.

You want to fill the fruit baskets with the following constraints in this order:

Each basket can only contain fruits of the same kind
Each basket should be as full as possible
Each basket should be as cheap as possible
Return the minimum cost required to fill as many baskets as possible.

Constraints

n ≤ 100,000 where n is the length of fruits
0 ≤ k < 2 ** 31

*/

import java.util.*;

class Solution {
    // largest number <= N divisible by K
    public int findNum(int N, int K) {
        int rem = N % K;
        if (rem == 0)
            return N;
        else
            return N - rem;
    }
    public int solve(int[][] fruits, int k, int size) {
        int res = 0;

        PriorityQueue<Integer> pq = new PriorityQueue<Integer>(
            (i, j)
                -> (size - findNum(Math.min(fruits[i][2] * fruits[i][1], size), fruits[i][1]))
                    == (size - findNum(Math.min(fruits[j][2] * fruits[j][1], size), fruits[j][1]))
                ? fruits[i][0]
                        * (findNum(Math.min(fruits[i][2] * fruits[i][1], size), fruits[i][1])
                            / fruits[i][1])
                    - fruits[j][0]
                        * (findNum(Math.min(fruits[j][2] * fruits[j][1], size), fruits[j][1])
                            / fruits[j][1])
                : size - findNum(Math.min(fruits[i][2] * fruits[i][1], size), fruits[i][1])
                    - (size - findNum(Math.min(fruits[j][2] * fruits[j][1], size), fruits[j][1])));

        for (int i = 0; i < fruits.length; i++) {
            pq.add(i);
        }
        for (int i = 0; i < k; i++) {
            int f = pq.poll();
            int a_cost = fruits[f][0];
            int a_size = fruits[f][1];
            int a_amount = fruits[f][2];

            if (a_amount <= 0)
                return res;

            res += (a_cost * (findNum(Math.min(a_amount * a_size, size), a_size) / a_size));
            fruits[f][2] -= (findNum(Math.min(a_amount * a_size, size), a_size) / a_size);
            pq.add(f);
        }
        return res;
    }
}
