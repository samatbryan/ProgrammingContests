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
public class k {

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter w = new PrintWriter(System.out);
        int T = 1;
        for (int i = 0; i < T; i++)
            solve(in, w);
        w.close();
    }

    public static void solve(InputReader in, PrintWriter w) {
        int n = in.nextInt(), k = in.nextInt();
        long t1 = in.nextLong(), t2 = in.nextLong();
        long[] m = in.nextLongArray(n);
        long[][] blacklists = new long[k][2];
        for (int i = 0; i < k; i++) // no overlap and b < e
            blacklists[i] = in.nextLongArray(2);

        TreeMap<Long, Long> intervals = new TreeMap();
        long bad = 0;

        for (long m_val : m) {
            for (long[] b : blacklists) {
                long t_left = b[0] - m_val;
                long t_right = b[1] - m_val;

                t_left = Math.max(t_left, t1);
                t_right = Math.min(t_right, t2);

                if (t_left <= t_right) {
                    Long floor_key = intervals.floorKey(t_left);
                    if (floor_key != null && intervals.get(floor_key) >= t_left) {
                        t_left = floor_key;
                        t_right = Math.max(t_right, intervals.get(floor_key));
                        intervals.remove(floor_key);
                    }
                    while (intervals.floorKey(t_right) != null) {
                        if (intervals.floorKey(t_right) < t_left)
                            break;
                        long tmp_left = intervals.floorKey(t_right);
                        t_right = Math.max(t_right, intervals.get(tmp_left));
                        intervals.remove(tmp_left);
                    }
                    intervals.put(t_left, t_right);

                }
            }
        }
        for (long a : intervals.keySet()) {
            bad += intervals.get(a) - a;
        }

        w.println(1.0 - (bad * 1.0) / (t2 - t1));
    }

    public static boolean survives(TreeMap<Integer, Integer> intervals, int[] m, double t) {
        for (int mm : m) {
            double new_t = (double) t + mm;
            int floor_new_t = (int) new_t;

            Integer prev = intervals.floorKey(floor_new_t);
            if (prev != null && (double) intervals.get(prev) >= new_t) {
                return false;
            }
        }
        return true;
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

        public long[] nextLongArray(int n) {
            long a[] = new long[n];
            for (int i = 0; i < n; i++) {
                a[i] = nextLong();
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
