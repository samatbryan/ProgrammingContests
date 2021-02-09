import java.io.*;
import java.util.*;

/* Example Usage

InputReader in = new InputReader(System.in);
PrintWriter w = new PrintWriter(System.out);

int t = in.nextInt();
while (t-- > 0) {
    int a[][] = new int[3][3];
    for (int i = 0; i < 3; i++)
        a[i] = in.nextIntArray(3);
    w.println(ans);
}
w.close();
*/
public class h {

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter w = new PrintWriter(System.out);
        int T = 1;
        for (int i = 0; i < T; i++)
            solve(in, w);
        w.close();
    }

    public static void solve(InputReader in, PrintWriter w) {
        int n = in.nextInt(), t = in.nextInt();
        int[][] pos = new int[n][3];
        TreeMap<Integer, int[]> positions = new TreeMap();
        for (int i = 0; i < n; i++) {
            pos[i] = in.nextIntArray(3);
            positions.put(pos[i][2], new int[] { pos[i][0], pos[i][1] });
        }

        // calculate real distance
        double real = 0;
        for (int i = 1; i < n; i++) {
            int prev_x = pos[i - 1][0], prev_y = pos[i - 1][1];
            int cur_x = pos[i][0], cur_y = pos[i][1];
            real += Math.pow(Math.pow(cur_x - prev_x, 2) + Math.pow(cur_y - prev_y, 2), 0.5);
        }
        // w.println(real);

        // calculate captured distance
        double fake = 0;
        int max_time = pos[pos.length - 1][2], start_time = t;
        ArrayList<Integer> times = new ArrayList();
        times.add(pos[0][2]);

        double cur_x = (double) pos[0][0];
        double cur_y = (double) pos[0][1];

        while (start_time < max_time) {
            times.add(start_time);
            start_time += t;
        }
        times.add(max_time);
        for (int i = 1; i < times.size(); i++) {
            Info info = dist_interval(positions, times.get(i), cur_x, cur_y);
            fake += info.dist;
            cur_x = info.x;
            cur_y = info.y;
        }
        // w.println(fake);
        w.println(100.0 * (real - fake) / real);
    }

    static class Info {
        double x, y, dist;

        Info(double x, double y, double dist) {
            this.x = x;
            this.y = y;
            this.dist = dist;
        }
    }

    // given an array of time stamps, find the two stamps that this belongs in
    public static Info dist_interval(TreeMap<Integer, int[]> positions, int cur_time, double prev_x, double prev_y) {
        // find the previous interval and the next interval that the cur_time lies in
        double res = 0;
        int closest_time = positions.floorKey(cur_time);
        double cur_x = positions.get(closest_time)[0];
        double cur_y = positions.get(closest_time)[1];
        if (closest_time == cur_time) {
            return new Info(cur_x, cur_y, Math.pow(Math.pow(cur_x - prev_x, 2) + Math.pow(cur_y - prev_y, 2), 0.5));
        }

        int next_time = positions.ceilingKey(cur_time + 1);
        double next_x = (double) positions.get(next_time)[0];
        double next_y = (double) positions.get(next_time)[1];

        // System.out.println("prev_x, prev_y" + cur_x + "," + cur_y + " next_x, next_y
        // " + next_x + "," + next_y);
        double dt = (double) (cur_time - closest_time) / (next_time - closest_time);
        double dx = next_x - cur_x, dy = next_y - cur_y;
        // System.out.println("time diff : " + time_diff);
        double new_x = cur_x + dx * dt;
        double new_y = cur_y + dy * dt;

        // System.out.println("time: " + cur_time + " prev: " + prev_x + " " + prev_y +
        // " new:" + new_x + " " + new_y);
        return new Info(new_x, new_y, Math.pow(Math.pow(new_x - prev_x, 2) + Math.pow(new_y - prev_y, 2), 0.5));
    }

    static class InputReader {

        private final InputStream stream;
        private final byte[] buf = new byte[8192];
        private int curChar, snumChars;
        private SpaceCharFilter filter;

        public InputReader(InputStream stream) {
            this.stream = stream;
        }

        public int snext() {
            if (snumChars == -1)
                throw new InputMismatchException();
            if (curChar >= snumChars) {
                curChar = 0;
                try {
                    snumChars = stream.read(buf);
                } catch (IOException e) {
                    throw new InputMismatchException();
                }
                if (snumChars <= 0)
                    return -1;
            }
            return buf[curChar++];
        }

        public int nextInt() {
            int c = snext();
            while (isSpaceChar(c)) {
                c = snext();
            }
            int sgn = 1;
            if (c == '-') {
                sgn = -1;
                c = snext();
            }
            int res = 0;
            do {
                if (c < '0' || c > '9')
                    throw new InputMismatchException();
                res *= 10;
                res += c - '0';
                c = snext();
            } while (!isSpaceChar(c));
            return res * sgn;
        }

        public long nextLong() {
            int c = snext();
            while (isSpaceChar(c)) {
                c = snext();
            }
            int sgn = 1;
            if (c == '-') {
                sgn = -1;
                c = snext();
            }
            long res = 0;
            do {
                if (c < '0' || c > '9')
                    throw new InputMismatchException();
                res *= 10;
                res += c - '0';
                c = snext();
            } while (!isSpaceChar(c));
            return res * sgn;
        }

        public int[] nextIntArray(int n) {
            int a[] = new int[n];
            for (int i = 0; i < n; i++) {
                a[i] = nextInt();
            }
            return a;
        }

        public String readString() {
            int c = snext();
            while (isSpaceChar(c)) {
                c = snext();
            }
            StringBuilder res = new StringBuilder();
            do {
                res.appendCodePoint(c);
                c = snext();
            } while (!isSpaceChar(c));
            return res.toString();
        }

        public String nextLine() {
            int c = snext();
            while (isSpaceChar(c))
                c = snext();
            StringBuilder res = new StringBuilder();
            do {
                res.appendCodePoint(c);
                c = snext();
            } while (!isEndOfLine(c));
            return res.toString();
        }

        public boolean isSpaceChar(int c) {
            if (filter != null)
                return filter.isSpaceChar(c);
            return c == ' ' || c == '\n' || c == '\r' || c == '\t' || c == -1;
        }

        private boolean isEndOfLine(int c) {
            return c == '\n' || c == '\r' || c == -1;
        }

        public interface SpaceCharFilter {
            public boolean isSpaceChar(int ch);
        }
    }
}