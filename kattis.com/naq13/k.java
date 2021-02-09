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
    final int IMAX = Integer.MAX_VALUE;
    final long LMAX = Long.MIN_VALUE;

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter w = new PrintWriter(System.out);
        int T = 1;

        k ok = new k();

        for (int i = 0; i < T; i++)
            ok.solve(in, w);
        w.close();
    }

    public int f(int subset, Integer[] dp, int[] locs, int m, HashSet<Integer> have) {
        int res = 10000;
        if (subset == 0)
            return 0;
        if (dp[subset] != null)
            return dp[subset];

        for (int submask = subset; submask > 0; submask = (submask - 1) & subset) {
            // 1s of this submask means we have selected it
            ArrayList<Integer> intervals = new ArrayList();
            for (int i = 0; i < locs.length; i++) {
                if ((submask & (1 << i)) != 0) {
                    intervals.add(locs[i]);
                }
            }
            Collections.sort(intervals);
            int new_submask = subset ^ submask;
            int dist = 10000;
            if (intervals.size() > 1)
                dist = intervals.get(1) - intervals.get(0);
            boolean can = true;
            for (int i = 1; i < intervals.size(); i++) {
                if (intervals.get(i) - intervals.get(i - 1) != dist) {
                    can = false;
                }
            }
            int left = intervals.get(0) - dist;
            while (left >= 0) {
                if (!have.contains(left)) {
                    can = false;
                    break;
                }
                left -= dist;
            }
            int right = intervals.get(intervals.size() - 1) + dist;
            while (right < m) {
                if (!have.contains(right)) {
                    can = false;
                    break;
                }
                right += dist;
            }
            /*
             * System.out.println("choosing"); for (int iv : intervals) {
             * System.out.print(iv + " "); } System.out.println("possible? " + can);
             * System.out.println("new submask " + submask);
             */
            if (can) {
                res = Math.min(res, 1 + f(new_submask, dp, locs, m, have));
            }
        }

        return dp[subset] = res;
    }

    public void solve(InputReader in, PrintWriter w) {
        while (true) {
            try {
                int m = in.ii();
                int n = in.ii();
                int[] locs = in.nextIntArray(n);
                Arrays.sort(locs);
                HashSet<Integer> have = new HashSet();
                for (int l : locs) {
                    have.add(l);
                }
                Integer[] dp = new Integer[(1 << n) + 2];

                w.println(f((1 << n) - 1, dp, locs, m, have));
            } catch (Exception e) {
                break;
            }
        }

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

        public int ii() {
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

        public long ll() {
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
                a[i] = ii();
            }
            return a;
        }

        public long[] nextLongArray(int n) {
            long a[] = new long[n];
            for (int i = 0; i < n; i++) {
                a[i] = ll();
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