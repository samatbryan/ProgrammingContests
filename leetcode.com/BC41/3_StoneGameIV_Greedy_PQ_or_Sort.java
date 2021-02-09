/*
1686. Stone Game VI <BC 41 Contest 12/12 > Alice and Bob take turns playing a game, with Alice starting first.
There are n stones in a pile. On each player's turn, they can remove a stone from the pile and receive points based on the stone's value. Alice and Bob may value the stones differently.
You are given two integer arrays of length n, aliceValues and bobValues. Each aliceValues[i] and bobValues[i] represents how Alice and Bob, respectively, value the ith stone.
The winner is the person with the most points after all the stones are chosen. If both players have the same amount of points, the game results in a draw. Both players will play optimally.
Determine the result of the game, and:
* If Alice wins, return 1.
* If Bob wins, return -1.
* If the game results in a draw, return 0.

Solution
Sort stones by their sum value for Alice and Bob. If a stone is valued for Alice, Alice wants to take it. If a stone is valued for Alice, Alice also wants to take it. Because she doesn't want Bob to take it.

Explanation

Here is more convinced explanation. Assume a stone valued [a,b] for Alice and Bod. Alice takes it, worth a for Alice, Bob takes it, worth b for Bob, we can also consider that it worth -b for Alice. The difference will be a+b. That's the reason why we need to sort based on a+b. And Alice and Bob will take one most valued stone each turn.

Complexity
Time O(nlogn) Space O(n)
*/

class Solution {
    public int stoneGameVI(int[] av, int[] bv) {
        int n = av.length;

        PriorityQueue<Integer> pq =
            new PriorityQueue<Integer>((i, j) -> (av[j] + bv[j]) - (av[i] + bv[i]));
        for (int i = 0; i < n; i++) {
            pq.add(i);
        }
        boolean turn = true;
        // true = alice
        int alice = 0;
        int bob = 0;
        while (pq.size() > 0) {
            int idx = pq.poll();
            if (turn) {
                alice += av[idx];
            } else {
                bob += bv[idx];
            }
            turn = !turn;
        }

        if (alice == bob)
            return 0;
        if (alice < bob)
            return -1;
        return 1;
    }
}
