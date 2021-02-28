import java.util.*;
import java.io.*;

class DItem {
    int i;
    int count;
    int mask;

    public DItem(int i, int count, int mask) {
        this.i = i;
        this.count = count;
        this.mask = mask;
    }
}

class Solver {
    int n;
    int[] x;
    int[] y;
    int[] s;
    double m;
    double[][] dist;

    public Solver(int n, int[] x, int[] y, int[] s, double m) {
        this.n = n;
        this.x = x;
        this.y = y;
        this.s = s;
        this.m = m;
        solve();
    }

    boolean dp(double v0) {
        double INF = Double.MAX_VALUE;
        double[][] minTime = new double[1 << n][n + 1];
        for (int mask = 1; mask < (1 << n); mask++)
            Arrays.fill(minTime[mask], INF);

        Deque<DItem> queue = new ArrayDeque<DItem>();
        queue.offerLast(new DItem(n, 0, 0));

        while (!queue.isEmpty()) {
            DItem item = queue.pollFirst();
            double t = minTime[item.mask][item.i];
            double v = v0 * Math.pow(m, item.count);
            for (int j = 0; j < n; j++) {
                if ((item.mask & (1 << j)) != 0)
                    continue;
                double t2 = t + dist[item.i][j] / v;
                if (t2 > s[j])
                    continue;
                int mask = item.mask | (1 << j);
                if (minTime[mask][j] <= t2)
                    continue;
                minTime[mask][j] = t2;
                if (mask != (1 << n) - 1)
                    queue.offerLast(new DItem(j, item.count + 1, mask));
            }
            ;
        }

        for (int i = 0; i < n; i++) {
            if (minTime[(1 << n) - 1][i] < INF)
                return true;
        }

        return false;
    }

    void solve() {
        dist = new double[n + 1][n + 1];
        for (int i = 0; i <= n; i++) {
            for (int j = i + 1; j <= n; j++) {
                dist[i][j] = dist[j][i] = Math.sqrt((Math.pow(x[i] - x[j], 2) + Math.pow(y[i] - y[j], 2)));
            }
        }

        double epsilon = 10e-4;
        double low = 0;
        double high = Math.pow(2, 31);

        while ((high - low) >= epsilon) {
            double mid = (low + high) / 2;
            if (dp(mid)) {
                high = mid;
            } else {
                low = mid;
            }
        }

        System.out.println(low);
    }
}

public class CatAndMice {
    public static void main(String[] args) {
        Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        in.useLocale(Locale.ENGLISH);

        int n = in.nextInt();
        int[] x = new int[n + 1];
        int[] y = new int[n + 1];
        int[] s = new int[n + 1];
        for (int i = 0; i < n; i++) {
            x[i] = in.nextInt();
            y[i] = in.nextInt();
            s[i] = in.nextInt();
        }
        double m = in.nextDouble();

        in.close();
        new Solver(n, x, y, s, m);
    }
}