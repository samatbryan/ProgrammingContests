/*
 * 1707. Maximum XOR With an Element From Array User Accepted:234 User Tried:785
 * Total Accepted:254 Total Submissions:1516 Difficulty:Hard You are given an
 * array nums consisting of non-negative integers. You are also given a queries
 * array, where queries[i] = [xi, mi].
 * 
 * The answer to the ith query is the maximum bitwise XOR value of xi and any
 * element of nums that does not exceed mi. In other words, the answer is
 * max(nums[j] XOR xi) for all j such that nums[j] <= mi. If all elements in
 * nums are larger than mi, then the answer is -1.
 * 
 * Return an integer array answer where answer.length == queries.length and
 * answer[i] is the answer to the ith query.
 */

class Solution {
    Node root = new Node();

    // left = 1
    // right = 0
    static class Node {
        Node left;
        Node right;
        boolean root;
        int val;

        Node() {
            this.root = false;
            this.val = -1;
            this.left = null;
            this.right = null;
        }
    }

    public void add_node(int num) {

        Node traverse = root;
        for (int i = 31; i >= 0; i--) {
            // left to right bit

            // 1 bit at ith bit
            if ((num & (1 << i)) != 0) {
                if (traverse.left == null) {
                    traverse.left = new Node();
                }
                traverse = traverse.left;
            } else {
                if (traverse.right == null) {
                    traverse.right = new Node();
                }
                traverse = traverse.right;
            }
        }
        traverse.root = true;
        traverse.val = num;
    }

    public int get_max(int val) {
        Node traverse = root;
        for (int i = 31; i >= 0; i--) {
            if (traverse.left == null && traverse.right == null) {
                return -1;
            }
            // 1 bit at ith bit, so go to the one thats 0 i.e go right.
            if ((val & (1 << i)) != 0) {
                if (traverse.right != null) {
                    traverse = traverse.right;
                } else {
                    traverse = traverse.left;
                }
            } else {
                if (traverse.left != null) {
                    traverse = traverse.left;
                } else {
                    traverse = traverse.right;
                }
            }
        }
        if (traverse == null)
            return -1;
        if (traverse.val == -1) {
            System.out.println("the fuck");
            return -1;
        }
        return traverse.val ^ val;
    }

    static class LOL {
        int xor;
        int ceiling;
        int idx;

        LOL(int a, int b, int c) {
            this.xor = a;
            this.ceiling = b;
            this.idx = c;
        }
    }

    public int[] maximizeXor(int[] nums, int[][] queries) {
        int q = queries.length;
        int[] res = new int[q];

        ArrayList<LOL> a = new ArrayList();
        for (int i = 0; i < queries.length; i++) {
            int[] query = queries[i];
            LOL l = new LOL(query[0], query[1], i);
            a.add(l);
        }

        Collections.sort(a, (c, d) -> (c.ceiling - d.ceiling));

        int idx = 0;
        Arrays.sort(nums);

        for (LOL query : a) {
            int xor = query.xor;
            int ceiling = query.ceiling;

            while (idx < nums.length && nums[idx] <= ceiling) {
                add_node(nums[idx]);
                idx++;
            }

            res[query.idx] = get_max(xor);

        }
        return res;

    }
}