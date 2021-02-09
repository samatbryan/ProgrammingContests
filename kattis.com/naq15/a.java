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

public class a {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter w = new PrintWriter(System.out);
        int T = in.ii();

        a ok = new a();

        for (int i = 0; i < T; i++)
            ok.solve(in, w);
        w.close();
    }

    public static void base_one(String left, String op, String right, StringBuilder res, String equal) {
        int left_count = 0, right_count = 0, equal_count = 0;
        for (int i = 0; i < left.length(); i++) {
            if (left.charAt(i) != '1')
                return;
            left_count++;
        }
        for (int i = 0; i < right.length(); i++) {
            if (right.charAt(i) != '1')
                return;
            right_count++;
        }
        for (int i = 0; i < equal.length(); i++) {
            if (equal.charAt(i) != '1')
                return;
            equal_count++;
        }
        if (op.equals("+")) {
            if (left_count + right_count == equal_count)
                res.append("1");
        } else if (op.equals("*")) {
            if (left_count * right_count == equal_count)
                res.append("1");
        } else if (op.equals("-")) {
            if (left_count - right_count == equal_count)
                res.append("1");
        } else {
            if (right_count == 0)
                return;
            if (left_count == right_count * equal_count)
                res.append("1");
        }
    }

    public static void solve(InputReader in, PrintWriter w) {
        String s = in.nextLine();
        String[] tokens = s.split(" ");

        String left = tokens[0];
        String op = tokens[1];
        String right = tokens[2];
        String equal = tokens[4];
        StringBuilder res = new StringBuilder();
        // try base 1
        base_one(left, op, right, res, equal);
        // try base 2-36
        for (int base = 2; base <= 36; base++) {
            String ch = Integer.toString(base);
            if (base >= 10) {
                ch = Character.toString((char) ('a' + (base - 10)));
            }
            if (base == 36) {
                ch = "0";
            }
            try {
                int left_count = Integer.parseInt(left, base);
                int right_count = Integer.parseInt(right, base);
                int equal_count = Integer.parseInt(equal, base);
                if (op.equals("+")) {
                    if (left_count + right_count == equal_count)
                        res.append(ch);
                } else if (op.equals("*")) {
                    if (left_count * right_count == equal_count)
                        res.append(ch);
                } else if (op.equals("-")) {
                    if (left_count - right_count == equal_count)
                        res.append(ch);
                } else {
                    if (right_count != 0 && left_count == right_count * equal_count)
                        res.append(ch);
                }
            } catch (Exception e) {
                continue;
            }
        }
        if (res.length() == 0)
            w.println("invalid");
        else {
            w.println(res.toString());

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