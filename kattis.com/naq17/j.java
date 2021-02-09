import java.io.*;
import java.util.*;

public class j {
    /*
     * Example Usage
     * 
     * InputReader in = new InputReader(System.in); PrintWriter w = new
     * PrintWriter(System.out);
     * 
     * int t = in.nextInt(); while (t-- > 0) { int a[][] = new int[3][3]; for (int i
     * = 0; i < 3; i++) a[i] = in.nextIntArray(3); w.println(ans); } w.close();
     */

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter w = new PrintWriter(System.out);
        int T = in.nextInt();
        for (int i = 0; i < T; i++)
            solve(in, w);
        w.close();
    }

    public static void solve(InputReader in, PrintWriter w) {
        String s = in.nextLine();

        if (s.charAt(0) == 'e') {
            char[] c = s.substring(2, s.length()).toCharArray();
            int n = c.length;
            int[] enc = new int[c.length];
            for (int i = 0; i < n; i++) {
                if (c[i] == ' ') {
                    enc[i] = 0;
                } else {
                    enc[i] = c[i] - 'a' + 1;
                }
            }
            for (int i = 1; i < n; i++)
                enc[i] = (enc[i - 1] + enc[i]) % 27;
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < n; i++) {
                if (enc[i] == 0)
                    sb.append(' ');
                else {
                    sb.append((char) ('a' + (enc[i] - 1)));
                }
            }
            w.println(sb.toString());
        } else {
            char[] c = s.substring(2, s.length()).toCharArray();
            int n = c.length;
            int[] dec = new int[n];
            int[] res = new int[n];
            for (int i = 0; i < n; i++) {
                if (c[i] == ' ') {
                    dec[i] = 0;
                } else {
                    dec[i] = c[i] - 'a' + 1;
                }
            }
            res[0] = dec[0];
            for (int i = 1; i < n; i++) {
                res[i] = (dec[i] - dec[i - 1]) % 27;
                res[i] += 27;
                res[i] %= 27;
            }

            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < n; i++) {
                if (res[i] == 0)
                    sb.append(' ');
                else {
                    sb.append((char) ('a' + (res[i] - 1)));
                }
            }
            w.println(sb.toString());

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