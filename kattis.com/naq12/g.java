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

public class g {
    final int IMAX = Integer.MAX_VALUE;
    final long LMAX = Long.MIN_VALUE;

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter w = new PrintWriter(System.out);
        int T = 1;

        g ok = new g();

        for (int i = 0; i < T; i++)
            ok.solve(in, w);
        w.close();
    }

    public void solve(InputReader in, PrintWriter w) {
        HashMap<String, int[]> moves = new HashMap();
        moves.put("S", new int[] { 0, -1 });
        moves.put("E", new int[] { 1, 0 });
        moves.put("W", new int[] { -1, 0 });
        moves.put("N", new int[] { 0, 1 });

        int n = in.ii();
        for (int i = 0; i < n; i++) {
            int s = in.ii();
            Pair<Integer, Integer> start = new Pair(0, 0);
            Pair<Integer, Integer> cur = new Pair(0, 0);

            HashMap<Pair<Integer, Integer>, ArrayList<Pair<Integer, Integer>>> graph = new HashMap();
            for (int j = 0; j < s; j++) {
                String mv = in.nextLine();
                int[] change = moves.get(mv);
                Pair<Integer, Integer> next = new Pair(cur.first + change[0], cur.second + change[1]);
                graph.putIfAbsent(cur, new ArrayList());
                graph.get(cur).add(next);
                graph.putIfAbsent(next, new ArrayList());
                graph.get(next).add(cur);
                cur = next;
            }

            HashSet<Pair<Integer, Integer>> visited = new HashSet();
            Pair<Integer, Integer> dest = cur;
            LinkedList<Pair<Integer, Integer>> q = new LinkedList();
            q.addLast(start);
            int level = 0;
            boolean found = false;
            visited.add(start);
            while (q.size() > 0) {
                if (found)
                    break;
                int size = q.size();
                for (int qq = 0; qq < size; qq++) {
                    Pair<Integer, Integer> loc = q.removeFirst();
                    if (loc.first == dest.first && loc.second == dest.second) {
                        found = true;
                        break;
                    }
                    for (Pair<Integer, Integer> neighbor : graph.get(loc)) {
                        if (!visited.contains(neighbor)) {
                            q.addLast(neighbor);
                            visited.add(neighbor);
                        }
                    }
                }
                if (found)
                    break;
                level++;

            }
            w.println(level);
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