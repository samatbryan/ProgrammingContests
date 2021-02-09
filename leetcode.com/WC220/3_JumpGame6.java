/*
 * 1696. Jump Game VI User Accepted:1252 User Tried:2513 Total Accepted:1308
 * Total Submissions:5585 Difficulty:Medium You are given a 0-indexed integer
 * array nums and an integer k.
 * 
 * You are initially standing at index 0. In one move, you can jump at most k
 * steps forward without going outside the boundaries of the array. That is, you
 * can jump from index i to any index in the range [i + 1, min(n - 1, i + k)]
 * inclusive.
 * 
 * You want to reach the last index of the array (index n - 1). Your score is
 * the sum of all nums[j] for each index j you visited in the array.
 * 
 * Return the maximum score you can get.
 */

class Solution {
    public class MaxQ<T extends Comparable<T>> {
        private LinkedList<T> q;

        /**
         * Class constructor for the MaxQ class using Generic Type. A MaxQ inserts item
         * in O(1) time and fetches the maximum element in O(1) time example usage: MaxQ
         * maxq = new MaxQ();
         */
        public MaxQ() {
            this.q = new LinkedList<T>();
        }

        /**
         * Time Complexity: O(1) Returns the maximum of all elements currently in the
         * queue
         * 
         * @return The maximum element in the queue
         */
        public T get_max() {
            return q.getFirst();
        }

        /**
         * Time Complexity: O(1) Pops and removes the maximum of all elements currently
         * in the queue
         * 
         * @return The maximum element in the queue
         */
        public T pop_max() {
            return q.removeFirst();
        }

        /**
         * Time Complexity: O(1) Adds an item to the queue and maintains the monotonic
         * property of the queue
         * 
         * @param item The item to add to the queue
         */
        public void add(T item) {
            while (!q.isEmpty() && (item.compareTo(q.getLast()) > 0)) {
                q.removeLast();
            }
            q.addLast(item);
        }

        /**
         * Time Complexity: O(1) Returns the number of elements in the queue
         * 
         * @return the size of the queue
         */
        public int size() {
            return this.q.size();
        }
    }

    public int maxResult(int[] nums, int k) {
        MaxQ<Integer> maxq = new MaxQ<Integer>();

        int n = nums.length;
        int[] dp = new int[n];

        dp[n - 1] = nums[n - 1];
        maxq.add(dp[n - 1]);
        for (int i = n - 2; i >= 0; i--) {
            if (i + k + 1 < n && maxq.get_max() == dp[i + k + 1]) {
                maxq.pop_max();
            }
            dp[i] = nums[i] + maxq.get_max();
            maxq.add(dp[i]);
        }
        return dp[0];
    }
}