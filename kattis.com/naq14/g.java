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
    /**
     * Time Complexity: O(1) Returns a list of center of circles determined by two
     * points and a radius
     * 
     * https://stackoverflow.com/questions/36211171/finding-center-of-a-circle-given-two-points-and-radius
     * 
     * @param point1 First x,y point on the cartesion plane
     * @param point2 Second x,y point on the cartesion plane
     * @param r      The radius of the circle
     * @return A list of (x,y) points that each represent the center of a circle
     *         determined by the given two points and a radius
     */
    static double[][] get_circle_centers(double[] point1, double[] point2, double r) {
        double x1 = point1[0], y1 = point1[1];
        double x2 = point2[0], y2 = point2[1];
        double xc = (x1 + x2) / 2, yc = (y1 + y2) / 2;
        double q = Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
        double err = 1e-6;
        if (Math.abs(q - 2 * r) <= err) { // one exact circle can be determined
            return new double[][] { { xc, yc } };
        } else if (q > 2 * r) // no circles can be determined
            return null;
        double dx = (y1 - y2) / q * Math.sqrt(r * r - q * q / 4);
        double dy = (x2 - x1) / q * Math.sqrt(r * r - q * q / 4);
        return new double[][] { { xc + dx, yc + dy }, { xc - dx, yc - dy } };
    }

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter w = new PrintWriter(System.out);
        int T = in.ii();
        g ok = new g();

        for (int i = 0; i < T; i++)
            ok.solve(in, w);
        w.close();
    }

    public void solve(InputReader in, PrintWriter w) {
        int m = in.ii();
        double diameter = in.nextDouble();
        double radius = diameter / 2.0;
        double[][] points = new double[m][2];
        for (int i = 0; i < m; i++) {
            points[i][0] = in.nextDouble();
            points[i][1] = in.nextDouble();
        }
        double err = 1e-6;
        int res = 1;
        for (int i = 0; i < m; i++) {
            for (int j = i + 1; j < m; j++) {
                double[][] centers = get_circle_centers(points[i], points[j], radius);
                if (centers == null)
                    continue;
                for (double[] c : centers) {
                    double x = c[0], y = c[1];
                    int tmp = 0;
                    for (double[] p : points) {
                        double x1 = p[0], y1 = p[1];
                        double dist = (x1 - x) * (x1 - x) + (y1 - y) * (y1 - y);
                        dist = Math.sqrt(dist);
                        if (dist <= radius + err) {
                            tmp++;
                        }
                    }
                    res = Math.max(tmp, res);
                }
            }
        }
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