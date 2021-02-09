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

public class j {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter w = new PrintWriter(System.out);
        int T = 1;

        j ok = new j();

        for (int i = 0; i < T; i++)
            ok.solve(in, w);
        w.close();
    }

    public void resolve(HashMap<String, Pair<String, Integer>> graph, String left, String right, int factor) {
        if (graph.containsKey(right)) {
            Pair<String, Integer> pre = graph.get(right);
            String pre_left = pre.first;
            int pre_factor = pre.second;

            if (pre_factor > factor) {
                resolve(graph, pre_left, left, pre_factor / factor);
            } else {
                resolve(graph, left, pre_left, factor / pre_factor);
            }

        } else {
            graph.put(right, new Pair(left, factor));
        }
    }

    public void solve(InputReader in, PrintWriter w) {
        while (true) {
            int n = in.ii();
            if (n == 0)
                return;
            ArrayList<String> units = new ArrayList();
            for (int i = 0; i < n; i++)
                units.add(in.readString());
            HashMap<String, Pair<String, Integer>> graph = new HashMap();
            ArrayList<String> smaller_units = new ArrayList();
            for (int i = 0; i < n - 1; i++) {
                String left = in.readString();
                in.readString();
                int factor = Integer.parseInt(in.readString());
                String right = in.readString();
                resolve(graph, left, right, factor);
            }
            for (String k : graph.keySet()) {
                units.remove(k);
            }
            String largest = units.get(0);
            ArrayList<Pair<String, Integer>> unit_scores = new ArrayList();
            for (String smaller : graph.keySet()) {
                unit_scores.add(new Pair(smaller, convert(graph, smaller, largest)));
            }
            Collections.sort(unit_scores, (a, b) -> (a.second - b.second));
            StringBuilder res = new StringBuilder();
            res.append("1" + largest);
            for (Pair<String, Integer> rest : unit_scores) {
                res.append(" = ");
                res.append(Integer.toString(rest.second));
                res.append(rest.first);
            }
            w.println(res.toString());
        }

    }

    public int convert(HashMap<String, Pair<String, Integer>> graph, String cur, String largest) {
        if (cur.equals(largest))
            return 1;
        Pair<String, Integer> neighbor = graph.get(cur);
        return neighbor.second * convert(graph, neighbor.first, largest);
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