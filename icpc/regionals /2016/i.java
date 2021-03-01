import java.io.*;
import java.util.*;

public class i {
    final int IMAX = Integer.MAX_VALUE;
    final int IMIN = Integer.MIN_VALUE;
    final long LMAX = Long.MAX_VALUE;
    final long LMIN = Long.MIN_VALUE;

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter w = new PrintWriter(System.out);
        int T = 1;

        i ok = new i();

        for (int i = 0; i < T; i++)
            ok.solve(in, w);
        w.close();
    }

    static class House {
        int pos;
        int mail;

        House(int pos, int mail) {
            this.pos = pos;
            this.mail = mail;
        }
    }

    public void solve(InputReader in, PrintWriter w) {
        int n = in.ii();
        int k = in.ii();
        int[] x = new int[n];
        int[] m = new int[n];
        for (int i = 0; i < n; i++) {
            x[i] = in.ii();
            m[i] = in.ii();
        }
        ArrayList<House> pos_x = new ArrayList();
        ArrayList<House> neg_x = new ArrayList();
        for (int i = 0; i < n; i++) {
            if (x[i] > 0) {
                pos_x.add(new House(x[i], m[i]));
            } else {
                neg_x.add(new House(-1 * x[i], m[i]));
            }
        }
        Collections.sort(pos_x, (a, b) -> (a.pos - b.pos));
        Collections.sort(neg_x, (a, b) -> (a.pos - b.pos));
        long res = 0;
        long carry = 0;
        for (int i = pos_x.size() - 1; i >= 0; i--) {
            House h = pos_x.get(i);
            long mail = h.mail;
            long pos = h.pos;
            long use = Math.min(carry, mail);
            mail -= use;
            carry -= use;
            if (mail == 0)
                continue;
            long trips = mail / k;
            if (mail % k != 0)
                trips++;
            carry += trips * k;
            carry -= mail;

            res += trips * 2 * pos;
        }
        carry = 0;
        for (int i = neg_x.size() - 1; i >= 0; i--) {
            House h = neg_x.get(i);
            long mail = h.mail;
            long pos = h.pos;
            long use = Math.min(carry, mail);
            mail -= use;
            carry -= use;
            if (mail == 0)
                continue;
            long trips = mail / k;
            if (mail % k != 0)
                trips++;
            carry += trips * k;
            carry -= mail;
            res += trips * 2 * Math.abs(pos);

        }
        w.println(res);

    }

    public static class InputReader {

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
}