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
    static public class Pair<S extends Comparable<S>, T extends Comparable<T>> implements Comparable<Pair<S, T>> {
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
    /*
     * Given N points (10^5 of them), Find the number of pair of points that have
     * distance K From point (x,y), expand out until you get distance 2019,
     */

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter w = new PrintWriter(System.out);
        int T = 1;
        for (int i = 0; i < T; i++)
            solve(in, w);
        w.close();
    }

    public static void solve(InputReader in, PrintWriter w) {
        int n = in.nextInt();
        ArrayList<Pair<Long, Long>> points = new ArrayList();
        HashMap<Pair<Long, Long>, Integer> hm = new HashMap();

        for (int i = 0; i < n; i++) {
            Pair<Long, Long> p = new Pair(in.nextLong(), in.nextLong());
            points.add(p);
            hm.put(p, i);
        }
        int res = 0;
        HashSet<Pair<Integer, Integer>> seen = new HashSet();

        ArrayList<Pair<Long,Long>> target = new ArrayList();
        for(long a=-2018; a<=2018; a ++){
            for(long b=-2018; b<=2018; b++){
                if(a*a + b*b == 2018*2018) target.add(new Pair(a,b));
            }
        }
        for(Pair<Long,Long> one : points){
            long first_x = one.first, first_y = one.second;
            int one_idx = hm.get(one);
            for(Pair<Long,Long> t: target){
                long t1 = t.first, t2 = t.second;
                if(hm.containsKey(new Pair(first_x - t1, first_y - t2))){
                    int two_idx = hm.get(new Pair(first_x - t1, first_y - t2));
                    if(!seen.contains(new Pair(one_idx, two_idx))){
                        res ++;
                        seen.add(new Pair(one_idx, two_idx));
                        seen.add(new Pair(two_idx, one_idx));
                    }
                }
            }
            // case1: 0,2018
            // 1118, 1680
            // 1680, 1118
            // 2018, 0
        }


        // a^2 + b^2 = 2018*2018
        // find all squares
        w.println(res);
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