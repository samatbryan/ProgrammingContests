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
    final int IMAX = Integer.MAX_VALUE;
    final long LMAX = Long.MIN_VALUE;

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter w = new PrintWriter(System.out);
        int T = 1;

        h ok = new h();

        for (int i = 0; i < T; i++)
            ok.solve(in, w);
        w.close();
    }

    static ArrayList<Integer> primes;
    static int[] isComposite;
    static HashSet<Integer> hs;

    static void sieve(int N) // O(N log log N)
    {
        isComposite = new int[N + 1];
        isComposite[0] = isComposite[1] = 1; // 0 indicates a prime number
        primes = new ArrayList<Integer>();

        for (int i = 2; i <= N; ++i) // can loop till i*i <= N if primes array is not needed O(N log log sqrt(N))
            if (isComposite[i] == 0) // can loop in 2 and odd integers for slightly better performance
            {
                primes.add(i);
                if (1l * i * i <= N)
                    for (int j = i * i; j <= N; j += i) // j = i * 2 will not affect performance too much, may alter in
                                                        // modified sieve
                        isComposite[j] = 1;
            }
    }

    public void two_sum(int x) {
        int count = 0;
        for (int i = 0; i < primes.size(); i++) {
            int left = primes.get(i);
            if (hs.contains(x - left)) {
                if (x - left >= left) {
                    count++;
                }
            }
        }
        System.out.println(x + " has " + count + " representation(s)");
        for (int i = 0; i < primes.size(); i++) {
            int left = primes.get(i);
            if (hs.contains(x - left)) {
                if (x - left >= left) {
                    System.out.println(left + "+" + (x - left));
                }
            }
        }
        System.out.println();
    }

    public void solve(InputReader in, PrintWriter w) {
        sieve(32100);
        hs = new HashSet();
        Collections.sort(primes);
        for (int p : primes) {
            hs.add(p);
        }
        int n = in.ii();
        for (int i = 0; i < n; i++) {
            int x = in.ii();
            two_sum(x);
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