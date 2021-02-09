/*
 * 1701. Average Waiting Time User Accepted:2677 User Tried:2875 Total
 * Accepted:2754 Total Submissions:4833 Difficulty:Medium There is a restaurant
 * with a single chef. You are given an array customers, where customers[i] =
 * [arrivali, timei]:
 * 
 * arrivali is the arrival time of the ith customer. The arrival times are
 * sorted in non-decreasing order. timei is the time needed to prepare the order
 * of the ith customer. When a customer arrives, he gives the chef his order,
 * and the chef starts preparing it once he is idle. The customer waits till the
 * chef finishes preparing his order. The chef does not prepare food for more
 * than one customer at a time. The chef prepares food for customers in the
 * order they were given in the input.
 * 
 * Return the average waiting time of all customers. Solutions within 10-5 from
 * the actual answer are considered accepted.
 */

class Solution {
    public double averageWaitingTime(int[][] customers) {
        double res = 0;
        int start = 0;
        for (int[] order : customers) {
            int arrive = order[0];
            int time = order[1];

            res += (Math.max(arrive, start) + time - arrive);

            start = Math.max(arrive, start) + time;
        }
        return res / customers.length;
    }
}
