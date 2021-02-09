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
        int H = in.ii();
        int[] hotdogs = in.nextIntArray(H);
        int B = in.ii();
        int[] buns = in.nextIntArray(B);
        int bmax = 0;
        int hmax = 0;
        for (int hh : hotdogs)
            hmax += hh;
        for (int bb : buns)
            bmax += bb;
        int total = Math.min(hmax, bmax);
        Long[][] hdp = new Long[total + 1][H + 1];
        Long[][] bdp = new Long[total + 1][B + 1];

        long big = (long) 1e10;
        for (int i = 0; i <= H; i++)
            hdp[0][i] = 0l;
        for (int i = 0; i <= B; i++)
            bdp[0][i] = 0l;

        for (int i = 1; i <= total; i++)
            Arrays.fill(hdp[i], big);
        for (int i = 1; i <= total; i++)
            Arrays.fill(bdp[i], big);

        Arrays.sort(hotdogs);
        Arrays.sort(buns);

        for (int sum = 1; sum <= total; sum++) {
            for (int idx = 1; idx <= H; idx++) {
                int cur_dog = hotdogs[idx - 1];
                hdp[sum][idx] = hdp[sum][idx - 1];
                if (sum - cur_dog >= 0) {
                    long c1 = hdp[sum][idx - 1];
                    long c2 = hdp[sum - cur_dog][idx - 1] + 1;
                    hdp[sum][idx] = Math.min(c1, c2);
                }
            }
            for (int idx = 1; idx <= B; idx++) {
                int cur_bun = buns[idx - 1];
                bdp[sum][idx] = bdp[sum][idx - 1];

                if (sum - cur_bun >= 0) {
                    long c1 = bdp[sum][idx - 1];
                    long c2 = bdp[sum - cur_bun][idx - 1] + 1;
                    bdp[sum][idx] = Math.min(c1, c2);
                }
            }
        }
        long res = Long.MAX_VALUE;
        for (int sum = 1; sum <= total; sum++) {
            long h_best = Long.MAX_VALUE;
            long b_best = Long.MAX_VALUE;
            for (int idx = 1; idx <= B; idx++)
                b_best = Math.min(b_best, bdp[sum][idx]);
            for (int idx = 1; idx <= H; idx++)
                h_best = Math.min(h_best, hdp[sum][idx]);

            if (h_best == big || b_best == big)
                continue;
            res = Math.min(res, hdp[sum][H] + bdp[sum][B]);
        }
        if (res == Long.MAX_VALUE)
            w.println("impossible");
        else {
            w.println(res);
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