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

public class l {

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter w = new PrintWriter(System.out);
        int T = in.ii();
        for (int i = 0; i < T; i++)
            solve(in, w, i);
        w.close();
    }

    public static void solve(InputReader in, PrintWriter w, int idd) {
        Line l1 = generate_line(new Pair(0.0, 0.0), new Pair(1.8, 1.0));
        Line l2 = generate_line(new Pair(0.0, 2.0), new Pair(1.0, 0.2));

        // for every permutation...
        double[][] points = new double[7][2];
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 2; j++) {
                points[i][j] = in.nextDouble();
            }
        }

        double prob = in.nextDouble();
        int[] start = { 1, 2, 3, 4, 5, 6, 7 };
        /*
         * if (idd == 954) { for (int i = 0; i < 7; i++) { for (int j = 0; j < 2; j++) {
         * w.print(points[i][j] + " "); } w.println(); } w.println(prob); }
         */
        // first ordering of the points.
        while (start != null) {
            // try this permutation of points.
            ArrayList<Pair<Double, Double>> cur_points = new ArrayList();
            for (int idx : start) {
                cur_points.add(new Pair(points[idx - 1][0], points[idx - 1][1]));
            }
            ArrayList<Line> lines = new ArrayList();
            for (int i = 1; i < cur_points.size(); i++) {
                lines.add(generate_line(cur_points.get(i), cur_points.get(i - 1)));
            }
            lines.add(generate_line(cur_points.get(0), cur_points.get(cur_points.size() - 1)));
            // try this polygon

            if (simple_polygon(lines)) {
                double area = polygon_area(cur_points);
                double single = area / 4.0;
                double cand_prob = single * single * single;
                if (Math.abs(prob - cand_prob) <= 1e-5) {
                    for (int qq = 0; qq < 6; qq++)
                        w.print(start[qq] + " ");
                    w.print(start[6]);
                    w.println();
                    return;
                }
            }
            start = next_permutation(start);
        }
    }

    static double polygon_area(ArrayList<Pair<Double, Double>> points) {
        double area = 0;
        int n = points.size();
        int j = n - 1;
        for (int i = 0; i < n; i++) {
            area += points.get(i).second * points.get(j).first - points.get(i).first * points.get(j).second;
            j = i;
        }
        return Math.abs(area) / 2;
    }

    static int[] next_permutation(int[] nums) {
        // if last, then no more:
        boolean changed = false;
        int[] res = (int[]) nums.clone();
        int n = res.length;
        for (int i = n - 1; i > 0; i--) {
            if (res[i - 1] < res[i]) {
                // find the smallest index
                int swap_idx = i;
                int min_value = Integer.MAX_VALUE;
                for (int k = i; k < n; k++) {
                    if (res[k] > res[i - 1] && res[k] < min_value) {
                        swap_idx = k;
                        min_value = res[k];
                    }
                }
                res[swap_idx] = res[i - 1];
                res[i - 1] = min_value;
                Arrays.sort(res, i, n);
                changed = true;
                break;
            }
        }
        if (!changed)
            return null;
        return res;
    }

    static class Line {
        double A, B, C;
        double x1, y1, x2, y2;

        Line(double x1, double y1, double x2, double y2) {
            this.A = y2 - y1;
            this.B = x1 - x2;
            this.C = A * x1 + B * y1;
            this.x1 = x1;
            this.y1 = y1;
            this.x2 = x2;
            this.y2 = y2;
        }
    }

    static boolean intersect(Line line1, Line line2, boolean segment) {
        // return true if line1 and line2 intersect
        double det = line1.A * line2.B - line2.A * line1.B;
        if (det == 0) {
            return false;
        }
        // this is line intersection
        double X = (line2.B * line1.C - line1.B * line2.C) / det;
        double Y = (line1.A * line2.C - line2.A * line1.C) / det;

        if (segment) { // line segment version
            double tolerance = 1e-6;
            if (X < Math.min(line1.x1, line1.x2) - tolerance || X > Math.max(line1.x1, line1.x2) + tolerance)
                return false;
            if (Y < Math.min(line1.y1, line1.y2) - tolerance || Y > Math.max(line1.y1, line1.y2) + tolerance)
                return false;
            if (X < Math.min(line2.x1, line2.x2) - tolerance || X > Math.max(line2.x1, line2.x2) + tolerance)
                return false;
            if (Y < Math.min(line2.y1, line2.y2) - tolerance || Y > Math.max(line2.y1, line2.y2) + tolerance)
                return false;
            return true;
        }
        return true;
    }

    static boolean simple_polygon(ArrayList<Line> lines) {
        // check that every pair of nonadjacent line segments dont intersect
        int n = lines.size();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (Math.abs(i - j) <= 1)
                    continue;
                if (i == 0 && j == n - 1)
                    continue;
                if (i == n - 1 && j == 0)
                    continue;

                if (intersect(lines.get(i), lines.get(j), true)) {
                    return false;
                }
            }
        }
        return true;
    }

    static Line generate_line(Pair<Double, Double> p1, Pair<Double, Double> p2) {
        double x1 = p1.first, y1 = p1.second;
        double x2 = p2.first, y2 = p2.second;
        return new Line(x1, y1, x2, y2);
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