import java.util.*;

public class c {

    public static ArrayList<Integer> solve(int[] arr, int k, List<List<Integer>> queries) {
        int n = arr.length;
        int[][] left_freq = new int[1001][n];
        int[][] right_freq = new int[1001][n];

        left_freq[arr[0]][0] += 1;
        right_freq[arr[n - 1]][n - 1] += 1;

        for (int i = 1; i < n; i++) {
            int val = arr[i];
            left_freq[val][i] = left_freq[val][i - 1] + 1;
        }
        for (int i = n - 2; i >= 0; i--) {
            int val = arr[i];
            right_freq[val][i] = right_freq[val][i + 1] + 1;
        }

        ArrayList<Integer> ret = new ArrayList();

        for (List<Integer> q : queries) {
            int left = q.get(0);
            int right = q.get(1);
            int val = q.get(2);

            int dist = right - left + 1;
            int res = 0;
            // reduce the [left , right] range to within the bounds of the array
            res += (dist / n) * left_freq[val][n - 1];

            left = left % n;
            right = right % n;

            if (left <= right) {
                if (left == 0) {
                    res += left_freq[val][right];
                } else {
                    res += (left_freq[val][right] - left_freq[val][left - 1]);
                }
            } else {
                res += (right_freq[val][right] + left_freq[val][left]);
            }

            ret.add(res);
        }
        return res;

    }

    public static void main(String[] args) {
        int[] arr = { 4, 2, 2 };
        int k = 3;

        ArrayList<ArrayList<Integer>> queries = new ArrayList();
        ArrayList<Integer> q = new ArrayList();
        q.add(5);
        q.add(8);
        q.add(2);

        queries.add(q);

        System.out.println(arr, k, queries);
    }
}