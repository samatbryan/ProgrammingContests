/*
 * 1705. Maximum Number of Eaten Apples User Accepted:1213 User Tried:3020 Total
 * Accepted:1259 Total Submissions:7924 Difficulty:Medium There is a special
 * kind of apple tree that grows apples every day for n days. On the ith day,
 * the tree grows apples[i] apples that will rot after days[i] days, that is on
 * day i + days[i] the apples will be rotten and cannot be eaten. On some days,
 * the apple tree does not grow any apples, which are denoted by apples[i] == 0
 * and days[i] == 0.
 * 
 * You decided to eat at most one apple a day (to keep the doctors away). Note
 * that you can keep eating after the first n days.
 * 
 * Given two integer arrays days and apples of length n, return the maximum
 * number of apples you can eat.
 */
class Solution {
    public int eatenApples(int[] apples, int[] days) {
        // count, expiration
        PriorityQueue<Pair<Integer, Integer>> pq = new PriorityQueue<Pair<Integer, Integer>>(
                (a, b) -> ((a.getValue() - a.getKey()) - (b.getValue() - b.getKey())));
        int res = 0;

        for (int i = 0; i < apples.length; i++) {
            int day = days[i];
            int apple = apples[i];

            int k = Math.min(day, apple);
            if (apple > 0) {
                pq.add(new Pair(k, i + day));
            }

            while (pq.size() > 0) {
                Pair<Integer, Integer> p = pq.poll();
                int num_apples = p.getKey();
                int expiration = p.getValue();

                if (expiration > i) {
                    num_apples--;
                    res++;
                    if (num_apples > 0) {
                        pq.add(new Pair(num_apples, expiration));
                    }
                    break;
                }
            }
        }
        int cur_time = apples.length;

        while (true) {
            if (pq.size() == 0)
                break;
            while (pq.size() > 0) {
                Pair<Integer, Integer> p = pq.poll();
                int num_apples = p.getKey();
                int expiration = p.getValue();

                if (expiration > cur_time) {
                    num_apples--;
                    res++;
                    if (num_apples > 0) {
                        pq.add(new Pair(num_apples, expiration));
                    }
                    break;
                }
            }
            cur_time++;
        }
        return res;
    }
}