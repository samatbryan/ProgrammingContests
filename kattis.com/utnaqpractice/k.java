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
        int T = in.ii();

        k ok = new k();

        for (int i = 0; i < T; i++)
            ok.solve(in, w);
        w.close();
    }

    public void traverse(Boolean[] values, int idx) {
        if (sat)
            return;
        if (idx == values.length) {
            if (evaluate(values)) {
                sat = true;
            }
            return;
        }
        boolean[] v = { true, false };
        if (values[idx] != null) {
            traverse(values, idx + 1);
        } else {
            for (boolean nxt : v) {
                values[idx] = nxt;
                traverse(values, idx + 1);
                values[idx] = null;
            }
        }
    }

    public boolean evaluate(Boolean[] values) {
        // evaluate all clauses and make sure theyre all true given the truth values of
        // each variable.
        for (ArrayList<Integer> c : clause_values) {
            boolean truthy = false;
            for (int c_idx : c) {
                boolean t = values[Math.abs(c_idx) - 1];
                if (c_idx < 0)
                    t = !t;
                truthy = truthy || t;
                if (truthy)
                    break;
            }

            if (!truthy)
                return false;
        }
        return true;
    }

    ArrayList<String> clauses;
    ArrayList<ArrayList<Integer>> clause_values;

    boolean sat;

    void fill_values(Boolean[] values) {
        clause_values = new ArrayList();
        for (String c : clauses) {
            ArrayList<Integer> clause_idx = new ArrayList();

            String[] tokens = c.split(" ");
            if (tokens.length == 1) {
                String t = tokens[0];
                if (t.charAt(0) == '~') {
                    Strwing num = t.substring(2, t.length());
                    int idx = Integer.parseInt(num);
                    values[idx - 1] = false;
                } else {
                    String num = t.substring(1, t.length());
                    int idx = Integer.parseInt(num);
                    values[idx - 1] = true;
                }
            }
            for (String token : tokens) {
                if (token.equals("v"))
                    continue;
                String num = " ";
                boolean neg = false;
                if (token.charAt(0) == '~') {
                    neg = true;
                    num = token.substring(2, token.length());
                } else {
                    num = token.substring(1, token.length());
                }
                int idx = Integer.parseInt(num);
                if (neg)
                    idx *= -1;
                clause_idx.add(idx);
            }
            clause_values.add(clause_idx);
        }
    }

    public void solve(InputReader in, PrintWriter w) {
        int n = in.ii();
        int m = in.ii();
        clauses = new ArrayList();
        sat = false;
        for (int i = 0; i < m; i++)
            clauses.add(in.nextLine());

        Boolean[] values = new Boolean[n];
        fill_values(values);
        traverse(values, 0);

        if (sat) {
            w.println("satisfiable");
        } else {
            w.println("unsatisfiable");
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