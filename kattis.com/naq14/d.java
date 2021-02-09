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

public class d {
    int[] dr = { 0, 0, -1, 1, 0 };
    int[] dc = { 1, -1, 0, 0, 0 };

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter w = new PrintWriter(System.out);
        int T = 1;
        d ok = new d();
        for (int i = 0; i < T; i++)
            ok.solve(in, w);
        w.close();
    }

    ArrayList<String> get_copy(ArrayList<String> cur) {
        ArrayList<String> res = new ArrayList();
        for (String c : cur) {
            String cc = c;
            res.add(cc);
        }
        return res;
    }

    public void solve(InputReader in, PrintWriter w) {
        int n = in.ii();

        // preprocess first
        HashMap<ArrayList<String>, Integer> hm = new HashMap();
        LinkedList<ArrayList<String>> q = new LinkedList();
        HashSet<ArrayList<String>> hs = new HashSet();
        ArrayList<String> start = new ArrayList();
        start.add("...");
        start.add("...");
        start.add("...");
        q.addLast(start);
        hs.add(start);
        int level = 0;
        while (q.size() > 0) {
            int size = q.size();
            for (int i = 0; i < size; i++) {
                ArrayList<String> cur = q.removeFirst();
                hm.put(cur, level);
                for (int r = 0; r < 3; r++) {
                    for (int c = 0; c < 3; c++) {
                        ArrayList<String> copy = get_copy(cur);

                        for (int move = 0; move < 5; move++) {
                            int new_r = r + dr[move];
                            int new_c = c + dc[move];
                            if (new_r < 0 || new_r >= 3 || new_c < 0 || new_c >= 3) {
                                continue;
                            }
                            char[] cur_str = copy.get(new_r).toCharArray();
                            if (cur_str[new_c] == '.') {
                                cur_str[new_c] = '*';
                            } else {
                                cur_str[new_c] = '.';
                            }
                            copy.set(new_r, String.valueOf(cur_str));
                        }
                        if (!hs.contains(copy)) {
                            hs.add(copy);
                            q.addLast(copy);
                        }
                    }
                }
            }
            level++;
        }
        for (int i = 0; i < n; i++) {
            ArrayList<String> dis = new ArrayList();
            for (int l = 0; l < 3; l++) {
                dis.add(in.nextLine());
            }
            w.println(hm.get(dis));
        }
        return;
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