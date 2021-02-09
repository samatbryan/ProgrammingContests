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
public class c {

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter w = new PrintWriter(System.out);
        int T = 1;
        for (int i = 0; i < T; i++)
            solve(in, w);
        w.close();
    }

    public static void solve(InputReader in, PrintWriter w) {
        int P = in.nextInt(), D = in.nextInt();
        HashMap<Long, long[]> districts = new HashMap();
        for (long i = 1; i <= D; i++) {
            districts.put(i, new long[] { 0, 0 });
        }

        for (int i = 0; i < P; i++) {
            long[] info = in.nextLongArray(3);
            long dis = info[0], a = info[1], b = info[2];
            districts.get(dis)[0] += a;
            districts.get(dis)[1] += b;
        }
        long a_wins = 0;
        long b_wins = 0;
        long total_votes = 0;
        long t_wasted_a = 0, t_wasted_b = 0;

        for (long i = 1; i <= D; i++) {
            long wasted_a = 0;
            long wasted_b = 0;
            long[] info = districts.get(i);
            total_votes += info[0] + info[1];
            long maj = (info[0] + info[1]) / 2 + 1;
            if (info[0] > info[1]) {
                a_wins++;
                wasted_a += info[0] - maj;
                wasted_b += info[1];
                t_wasted_a += info[0] - maj;
                t_wasted_b += info[1];
                w.println("A " + Long.toString(wasted_a) + " " + Long.toString(wasted_b));
            } else {
                b_wins++;
                wasted_b += info[1] - maj;
                wasted_a += info[0];
                t_wasted_b += info[1] - maj;
                t_wasted_a += info[0];
                w.println("B " + Long.toString(wasted_a) + " " + Long.toString(wasted_b));
            }
        }
        w.println((double) (Math.abs(t_wasted_a - t_wasted_b)) / total_votes);

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

        public double nextDouble() {
            return Double.parseDouble(readString());
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