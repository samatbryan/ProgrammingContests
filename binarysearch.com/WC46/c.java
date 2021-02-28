import java.util.*;

class Solution {
    public int[] solve(int[][] points) {
        HashMap<Integer, ArrayList<Integer>> y_hm = new HashMap(); // stores ys
        HashMap<Integer, ArrayList<Integer>> x_hm = new HashMap();
        for (int[] p : points) {
            int x = p[0], y = p[1];
            y_hm.putIfAbsent(x, new ArrayList());
            x_hm.putIfAbsent(y, new ArrayList());

            y_hm.get(x).add(y);
            x_hm.get(y).add(x);
        }
        int[] res = new int[points.length];

        int i = 0;
        for(int k: y_hm.keySet()){
            Collections.sort(y_hm.get(k));
        }
        for(int k: x_hm.keySet()){
            Collections.sort(x_hm.get(k));
        }
        for (int[] p : points) {
            int x = p[0], y = p[1];

            // find the closest x sharing
            res[i] = Math.min(closest(y_hm.get(x), y), closest(x_hm.get(y), x));

            i++;
        }
        return res;
    }

    public int closest(ArrayList<Integer> nums, int num) {
        int l = 0;
        int r = nums.size() - 1;
        int idx = -1;
        while (l <= r) {
            int m = l + (r - l) / 2;
            if (nums.get(m) == num) {
                idx = m;
                break;
            } else if (nums.get(m) < num) {
                l = m + 1;
            } else {
                r = m - 1;
            }
        }

        int res = Integer.MAX_VALUE;
        if (idx - 1 >= 0) {
            res = Math.min(res, num - nums.get(idx - 1));
        }
        if (idx + 1 < nums.size()) {
            res = Math.min(res, nums.get(idx + 1) - num);
        }
        return res;
    }
}