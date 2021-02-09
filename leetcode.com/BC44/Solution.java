import java.util.*;

class Solution {
    public int solve(int[] a, int[] b, int k) {
        Arrays.sort(a);
        Arrays.sort(b);
        int m = a.length;
        int n = b.length;
        LinkedList<int[]> q = new LinkedList();
        // q has [a_idx, b_idx]
        ArrayList<Integer> nums = new ArrayList();
        HashSet<Pair<Integer, Integer>> visited = new HashSet();
        if (a[0] * b[0] > a[m - 1] * b[n - 1]) {
            q.addLast(new int[] { 0, 0 });
            visited.add(new Pair(0, 0));
            nums.add(a[0] * b[0]);
        } else {
            q.addLast(new int[] { m - 1, n - 1 });
            visited.add(new Pair(m - 1, n - 1));
            nums.add(a[m-1] * b[n-1]);
        }

        while (q.size() > 0) {
            if (visited.size() >= k+1)
                break;

            int size = q.size();
            for (int i = 0; i < size; i++) {
                int[] cur = q.removeFirst();
                int a_idx = cur[0];
                int b_idx = cur[1];

                int cand = Integer.MIN_VALUE;
                Pair<Integer, Integer> next = null;
                if (a_idx - 1 >= 0 && a[a_idx - 1] * b[b_idx] > cand && !visited.contains(new Pair(a_idx-1, b_idx))) {
                    cand = a[a_idx - 1] * b[b_idx];
                    next = new Pair(a_idx - 1, b_idx);
                }
                if (b_idx - 1 >= 0 && a[a_idx] * b[b_idx - 1] > cand && !visited.contains(new Pair(a_idx, b_idx-1))) {
                    cand = a[a_idx] * b[b_idx - 1];
                    next = new Pair(a_idx, b_idx - 1);
                }
                if (!visited.contains(new Pair(0, 0)) && a[0] * b[0] > cand ) {
                    cand = a[0] * b[0];
                    next = new Pair(0, 0);
                }
                if (a_idx + 1 < m && a[a_idx + 1] * b[b_idx] > cand && !visited.contains(new Pair(a_idx+1, b_idx))) {
                    cand = a[a_idx + 1] * b[b_idx];
                    next = new Pair(a_idx + 1, b_idx);
                }
                if (b_idx + 1 >= 0 && a[a_idx] * b[b_idx + 1] > cand && !visited.contains(new Pair(a_idx, b_idx+1))) {
                    cand = a[a_idx] * b[b_idx + 1];
                    next = new Pair(a_idx, b_idx + 1);
                }

                if(next != null){
                    visited.add(next);
                    q.addLast(new int[]{next.getKey(), next.getValue()});
                    nums.add(cand);
                }

            }
        }

        Collections.sort(nums, Collections.reverseOrder());
        return nums.get(k);
        /*
         * a = [-2, 3, 4] b = [5, 7] k = 2
         * 
         */
    }
}