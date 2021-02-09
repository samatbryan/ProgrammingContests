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

public class d {
    static double FIXED_VELOCITY;

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter w = new PrintWriter(System.out);
        int T = 1;
        for (int i = 0; i < T; i++)
            solve(in, w);
        w.close();
    }

    public static void solve(InputReader in, PrintWriter w) {
        int n = in.ni();

        int[][] mice_loc = new int[n][3];
        for (int i = 0; i < n; i++)
            mice_loc[i] = in.nextIntArray(3);
        double m_factor = in.nextDouble();

        double min_speed = 0;
        double max_speed = 100000;

        while (min_speed <= max_speed) {
            double try_speed = min_speed + (max_speed - min_speed) / 2;
            FIXED_VELOCITY = try_speed;
            double[][] dp = new double[(1 << n) - 1][n];

            double time_taken = f((1<<n)-1, 0, dp, n, mice_loc, m_factor);
            if(time_taken <= ){

            }
        }
    }
say it took 10 seconds to eat all the mice... all the mice expire > than that?

    /*
     * State: F(mice_bitmask, cur_mice), returns the fastest time the fastest time
     * to eat all mice_bitmask given Velocity V computed by V_Start * Mi of all
     * eaten.
     * 
     * Transition: F(mice_bitmask, cur_mice) = minimum of the following: eat mice i:
     * time to travel to mice i at the current velocity + F(new_bitmask, eaten_mice)
     * 
     * Base case: F(0, anything) = 0;
     * 
     * return F(mice_bitmask, cur_mice) at the fixed velocity is <= time all the
     * mice pop down
     * 
     */
    public static double f(int mice_subset, int cur_mice, double[][] dp, int n, int[][] mice_loc, double m_factor) {
        if (mice_subset == 0) // Base case: Ate all the mice so time it takes is 0
            return 0;

        double res = Double.MAX_VALUE;
        double velocity = FIXED_VELOCITY;

        for (int i = 0; i < n; i++) { // calculate the current velocity we are at
            if (((mice_subset) & (1 << i)) == 0) { // we already ate this mice
                velocity *= m_factor;
            }
        }
        for (int i = 0; i < n; i++) {
            if (((mice_subset) & (1 << i)) != 0) { // this mice is avaliable
                int new_subset = (mice_subset) & ~(1 << i); // turn this mice to 0
                double candidate = time(cur_mice, i, velocity, mice_loc)
                        + f(new_subset, i + 1, dp, n, mice_loc, m_factor);
                res = Math.min(res, candidate);
            }
        }

        return dp[mice_subset][cur_mice] = res;

    }

    // calculates time it takes to get from mice1 to mice2 with velocity
    public static double time(int cur_loc, int next_loc, double velocity, int[][] mice_loc) {
        int cur_x = 0, cur_y = 0;
        if (cur_loc - 1 >= 0) {
            cur_x = mice_loc[cur_loc - 1][0];
            cur_y = mice_loc[cur_loc - 1][1];
        }

        int next_x = mice_loc[next_loc - 1][0], next_y = mice_loc[next_loc - 1][1];

        double distance = Math.pow(cur_x - next_x, 2) + Math.pow(cur_y - next_y, 2);
        distance = distance * distance;

        return distance / velocity;
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

        public int ni() {
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

        public long nl() {
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

        public double nextDouble() {
            return Double.parseDouble(readString());
        }

        public int[] nextIntArray(int n) {
            int a[] = new int[n];
            for (int i = 0; i < n; i++) {
                a[i] = ni();
            }
            return a;
        }

        public long[] nextLongArray(int n) {
            long a[] = new long[n];
            for (int i = 0; i < n; i++) {
                a[i] = nl();
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

class Pair<S extends Comparable<S>, T extends Comparable<T>> implements Comparable<Pair<S, T>> {
    S first;
    T second;

    Pair(S f, T s) {
        first = f;
        second = s;
    }

    @Override
    public int compareTo(Pair<S, T> o) {
        int t = first.compareTo(o.first);
        if (t == 0)
            return second.compareTo(o.second);
        return t;
    }

    @Override
    public int hashCode() {
        return (31 + first.hashCode()) * 31 + second.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Pair))
            return false;
        if (o == this)
            return true;
        Pair p = (Pair) o;
        return first.equals(p.first) && second.equals(p.second);
    }

    @Override
    public String toString() {
        return "Pair{" + first + ", " + second + "}";
    }
}