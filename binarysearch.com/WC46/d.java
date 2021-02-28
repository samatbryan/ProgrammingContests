import java.util.*;

class Solution {

    static class item {
        int val;
        int count;

        item(int val, int count) {
            this.val = val;
            this.count = count;
        }
    }

    public int solve(int[] nums) {

        int n = nums.length;
        int[] prefix = get_prefix(nums);
        int[] suffix = get_suffix(nums);
        int res = 0;
        for (int i = 0; i < n; i++) {
            int prev = 0;
            int next = 0;
            int cur = nums[i];
            if (i - 1 >= 0)
                prev = prefix[i - 1];
            if (i + 1 < n)
                next = suffix[i + 1];
            res = Math.max(res, cur + prev + next);
        }
        return res;
    }

    public int[] get_suffix(int[] nums) {
        int n = nums.length;
        int[] prefix = new int[n];
        LinkedList<item> q = new LinkedList();

        int sum = 0;
        for (int i = n - 1; i >= 0; i--) {
            int cur = nums[i];
            if (q.size() == 0) {
                q.addLast(new item(cur, 1));
                sum += 1;
            } else {
                int removed = 0;
                while (q.size() > 0 && cur <= q.getLast().val) {
                    item curitem = q.removeLast();
                    int value_diff = curitem.val - cur;
                    int count = curitem.count;
                    removed += count;
                    sum -= value_diff * count;
                }
                q.addLast(new item(cur, removed + 1));
                sum += (removed + 1) * cur;
            }
            prefix[i] = sum;
        }
        return prefix;
    }

    public int[] get_prefix(int[] nums) {
        int n = nums.length;
        int[] prefix = new int[n];
        LinkedList<item> q = new LinkedList();

        int sum = 0;
        for (int i = 0; i < n; i++) {
            int cur = nums[i];
            if (q.size() == 0) {
                q.addLast(new item(cur, 1));
                sum += 1;
            } else {
                int removed = 0;
                while (q.size() > 0 && cur <= q.getLast().val) {
                    item curitem = q.removeLast();

                    int value_diff = curitem.val - cur;
                    int count = curitem.count;
                    removed += count;
                    sum -= value_diff * count;
                }
                q.addLast(new item(cur, removed + 1));
                sum += (removed + 1) * cur;
            }
            prefix[i] = sum;
        }
        return prefix;
    }

}