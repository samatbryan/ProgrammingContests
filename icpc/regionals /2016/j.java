import java.io.*;
import java.util.*;

public class j {
    public class SegmentTree {
        int n;
        int size;
        long[] vals;

        /**
         * Implemented similarly to binary heap tree O(log(size)) operation for Gives
         * O(log(size)) operations for both set() and sum()
         * 
         * @param n The total number of elements in the array
         */
        public SegmentTree(int n) {
            this.n = n;
            this.size = 1;
            while (this.size < n) {
                this.size *= 2; // get the smallest power of 2 less than n
            }
            this.vals = new long[size * 2]; // binary heap takes size 2*size
            Arrays.fill(vals, Integer.MAX_VALUE);
        }

        /**
         * O(log(size)) operation for setting the value at idx to val. Also changes the
         * sums array to change the sums of all the nodes that cover the index idx
         * 
         * @param idx index in the user view array to change value
         * @param val The value to change to
         */
        public void set(int idx, int val) {
            set(idx, val, 0, 0, size);
        }

        /**
         * Builds the segment tree in O(N) time
         * 
         * @param a The array of numbers to build the segment tree from
         */
        public void build(long[] a) {
            build(a, 0, 0, size);
        }

        /**
         * O(log(size)) operation for getting the sum of interval [left, right).
         * 
         * @param left  inclusive idx
         * @param right exclusive idx
         */
        public long query(int left, int right) {
            return query(left, right, 0, 0, size);
        }

        /**
         * Return the sum of all elements in the interval [left,right)
         * 
         * @param left  Inclusive index of the left to get the sum of
         * @param right Exclusive index of the right to get the sum of
         * @param x     The index representation of the binary heap
         * @param l_idx The left index of user array covered by node x inclusive
         * @param r_idx The right index of user array covered by node x exclusive
         */
        private long query(int left, int right, int x, int l_idx, int r_idx) {
            if (l_idx >= right || left >= r_idx)
                return Integer.MAX_VALUE;
            if (l_idx >= left && r_idx <= right)
                return vals[x];

            int m_idx = (l_idx + r_idx) / 2;
            long left_sum = query(left, right, 2 * x + 1, l_idx, m_idx);
            long right_sum = query(left, right, 2 * x + 2, m_idx, r_idx);
            return Math.min(left_sum, right_sum);
        }

        /**
         * Time Complexity: O(log(size)) Sets the value at idx to val and changes all
         * the corresponding relevant nodes in the segment tree property of the queue
         * 
         * @param idx   The index of the user view array
         * @param val   The value to set the index to
         * @param x     The index representation of the binary heap
         * @param l_idx The left index of user array covered by node x inclusive
         * @param r_idx The right index of user array covered by node x exclusive
         */
        private void set(int idx, int val, int x, int l_idx, int r_idx) {
            if (r_idx - l_idx == 1) { // the leaf node
                vals[x] = val;
                return;
            }
            int m_idx = (l_idx + r_idx) / 2;
            if (idx < m_idx) {
                set(idx, val, 2 * x + 1, l_idx, m_idx);
            } else {
                set(idx, val, 2 * x + 2, m_idx, r_idx);
            }
            vals[x] = Math.min(vals[2 * x + 1], vals[2 * x + 2]);
        }

        public int first_index_less_than(int left, int right, long val) {
            if (vals[0] > val)
                return -1;
            int x = size + left - 1;
            // System.out.println("tree node " + x + " corresponds to " + left);
            while (vals[x] > val) {
                // if were a right child, then go up and right
                while (x % 2 == 0) {
                    x = x / 2 - 1;
                }
                x++;
            }
            while (x < size - 1) { // while its an internal node
                x = 2 * x + 1; // left child
                if (vals[x] > val) // go to right
                    x++;
            }
            int res = x - (size - 1);
            if (res <= right)
                return res;
            return -1;
        }

        private void build(long[] a, int x, int l_idx, int r_idx) {
            if (r_idx - l_idx == 1) {
                if (l_idx < a.length)
                    vals[x] = a[l_idx];
                return;
            }
            int m_idx = (l_idx + r_idx) / 2;
            build(a, 2 * x + 1, l_idx, m_idx);
            build(a, 2 * x + 2, m_idx, r_idx);
            vals[x] = Math.min(vals[2 * x + 1], vals[2 * x + 2]);
        }
    }

    final int IMAX = Integer.MAX_VALUE;
    final int IMIN = Integer.MIN_VALUE;
    final long LMAX = Long.MAX_VALUE;
    final long LMIN = Long.MIN_VALUE;

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter w = new PrintWriter(System.out);
        int T = 1;
        j ok = new j();

        for (int i = 0; i < T; i++)
            ok.solve(in, w);
        w.close();
    }

    public void solve(InputReader in, PrintWriter w) {
        int n = in.ii();
        int q = in.ii();
        long[] nums = in.nextLongArray(n);
        SegmentTree st = new SegmentTree(n);
        st.build(nums);

        for (int i = 0; i < q; i++) {
            long v = in.ll();
            int l = in.ii() - 1;
            int r = in.ii() - 1;
            // System.out.println("query " + i);
            while (v > 0 && l <= r) {
                int idx = st.first_index_less_than(l, r, v);
                // System.out.println(idx);
                if (idx == -1)
                    break;
                v = v % nums[idx];
                l = idx + 1;
            }
            w.println(v);
        }
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