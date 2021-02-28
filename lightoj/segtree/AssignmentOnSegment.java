import java.io.*;
import java.util.*;

public class AssignmentOnSegment {
    final int IMAX = Integer.MAX_VALUE;
    final int IMIN = Integer.MIN_VALUE;
    final long LMAX = Long.MAX_VALUE;
    final long LMIN = Long.MIN_VALUE;

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter w = new PrintWriter(System.out);
        int T = 1;

        AssignmentOnSegment ok = new AssignmentOnSegment();

        for (int i = 0; i < T; i++)
            ok.solve(in, w);
        w.close();
    }

    public class SegmentTree {
        int n;
        int size;
        long[] vals;
        int NO_OPERATION = Integer.MAX_VALUE - 1;
        long OPERATION_BASE_CASE = 0; // FILL THIS FOR INITIALIZATION

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
            // FILL THIS FOR INITIALIZATION
        }

        /**
         * 
         * @param a first param
         * @param b second param
         * @return Returns applying some operation to a from b
         */
        long apply_operation(long a, long b) {
            // FILL THIS
            if (b == NO_OPERATION) {
                return a;
            }
            return b;
        }

        /**
         * Propagate the operation at node X down to its two children
         */
        void propagate(int x, int lx, int rx) {
            if (rx - lx == 1)
                return;
            this.vals[2 * x + 1] = apply_operation(this.vals[2 * x + 1], this.vals[x]);
            this.vals[2 * x + 2] = apply_operation(this.vals[2 * x + 2], this.vals[x]);
            this.vals[x] = NO_OPERATION; // push this operation to both children and now we dont have anymore
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
         * Time Complexity: O(log(size)) Sets the value at idx to val and changes all
         * the corresponding relevant nodes in the segment tree property of the queue
         * 
         * @param idx   The index of the user view array
         * @param val   The value to set the index to
         * @param x     The index representation of the binary heap
         * @param l_idx The left index of user array covered by node x inclusive
         * @param r_idx The right index of user array covered by node x exclusive
         */
        void set(int idx, int val, int x, int l_idx, int r_idx) {
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
            vals[x] = apply_operation(vals[2 * x + 1], vals[2 * x + 2]);
        }

        /**
         * Builds the segment tree in O(N) time
         * 
         * @param a The array of numbers to build the segment tree from
         */
        public void build(int[] a) {
            build(a, 0, 0, size);
        }

        /**
         * 
         * @param a     The int array to build the segtree from
         * @param x     The current node in the heap
         * @param l_idx The leftmost index covered by node x
         * @param r_idx The rightmost index covered by node x exclusive
         */
        void build(int[] a, int x, int l_idx, int r_idx) {
            if (r_idx - l_idx == 1) {
                if (l_idx < a.length)
                    vals[x] = a[l_idx];
                return;
            }
            int m_idx = (l_idx + r_idx) / 2;
            build(a, 2 * x + 1, l_idx, m_idx);
            build(a, 2 * x + 2, m_idx, r_idx);
            vals[x] = apply_operation(vals[2 * x + 1], vals[2 * x + 2]);
        }

        /**
         * O(log(size)) operation for getting the sum of interval [left, right).
         * 
         * @param left  inclusive idx
         * @param right exclusive idx
         */
        public long calc(int left, int right) {
            return calc(left, right, 0, 0, size);
        }

        /**
         * Calculates applying operation on the interval [left, right-1]
         * 
         * @param left  Inclusive index of the left to get the sum of
         * @param right Exclusive index of the right to get the sum of
         * @param x     The index representation of the binary heap
         * @param l_idx The left index of user array covered by node x inclusive
         * @param r_idx The right index of user array covered by node x exclusive
         */
        long calc(int left, int right, int x, int l_idx, int r_idx) {
            if (l_idx >= right || left >= r_idx)
                return OPERATION_BASE_CASE;
            if (l_idx >= left && r_idx <= right)
                return vals[x];

            int m_idx = (l_idx + r_idx) / 2;
            long left_calc = calc(left, right, 2 * x + 1, l_idx, m_idx);
            long right_calc = calc(left, right, 2 * x + 2, m_idx, r_idx);
            return apply_operation(left_calc, right_calc);
        }

        /**
         * O(logn) - Apply some operation with val to the segment defined by [l,r-1]:
         * The operator we apply (add in this case) must be ASSOCIATIVE and COMMUTATIVE
         * 
         * @param l   The leftmost of the segment
         * @param r   The rightmost of the segment
         * @param val The value to add to
         */
        public void modify(int l, int r, int val) {
            modify(l, r, val, 0, 0, this.size);
        }

        /**
         * 
         * @param l   The left index of the user array
         * @param r   The right index of the user array exclusive
         * @param val The value to add to segment [l, r-1]
         * @param x   The node in the heap
         * @param lx  The leftmost index that node x covers
         * @param rx  The rightmost index that node x covers exclusive
         */
        void modify(int l, int r, int val, int x, int lx, int rx) {
            propagate(x, lx, rx);
            if (lx >= r || l >= rx)
                return;
            if (lx >= l && rx <= r) {
                vals[x] = apply_operation(vals[x], val);
                return;
            }
            int mx = (lx + rx) / 2;
            modify(l, r, val, 2 * x + 1, lx, mx);
            modify(l, r, val, 2 * x + 2, mx, rx);
        }

        /**
         * O(logn) - Gets the val at inx in the user view array
         * 
         * @param idx The index to get the value in the array from
         * @return The long in the index
         */
        public long get(int idx) {
            return get(idx, 0, 0, size);
        }

        /**
         * 
         * @param idx The index of the user array to fetch from
         * @param x   The node in the heap
         * @param lx  The leftmost user mode idx that node x covers
         * @param rx  The rightmost user mode idx that node x covers. Exclusive
         * @return The value at idx
         */
        long get(int idx, int x, int lx, int rx) {
            if (rx - lx == 1) {
                return vals[x];
            }
            int mx = (lx + rx) / 2;
            long res = 0;
            if (idx < mx) {
                res = get(idx, 2 * x + 1, lx, mx);
            } else {
                res = get(idx, 2 * x + 2, mx, rx);
            }
            return apply_operation(res, vals[x]);
        }
    }

    public void solve(InputReader in, PrintWriter w) {
        int n = in.ii();
        int m = in.ii();
        SegmentTree st = new SegmentTree(n);
        for (int i = 0; i < m; i++) {
            int t = in.ii();
            if (t == 1) {
                int l = in.ii();
                int r = in.ii();
                int v = in.ii();
                st.modify(l, r, v);
            } else {
                int idx = in.ii();
                w.println(st.get(idx));
            }
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