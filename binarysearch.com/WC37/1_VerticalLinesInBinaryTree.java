/*

https://binarysearch.com/room/Weekly-Contest-37-u2kU8duwTB?questionsetIndex=0

Vertical Lines in Binary Tree
Question 1 of 4
Given a binary tree root, return the number of unique vertical lines that can be drawn such that every node has a line intersecting it. Each left child is angled at 45 degrees to its left, while the right child is angled at 45 degrees to the right.

For example, root and root.left.right are on the same vertical line.

Constraints

n ≤ 100,000 where n is the

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
