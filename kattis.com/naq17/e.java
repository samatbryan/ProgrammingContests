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
public class e {
    static int r = 0;

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter w = new PrintWriter(System.out);
        int T = 1;
        for (int i = 0; i < T; i++)
            solve(in, w);
        w.close();
    }

    static class Node {
        String name;
        double speed;
        String supervisor;
        ArrayList<Node> children;

        Node(String name, double speed, String supervisor) {
            this.name = name;
            this.speed = speed;
            this.supervisor = supervisor;
            this.children = new ArrayList();
        }
    }

    public static void solve(InputReader in, PrintWriter w) {
        int n = in.nextInt();
        HashMap<String, Node> hm = new HashMap();
        Node root = null;

        HashSet<String> hs = new HashSet();
        for (int i = 0; i < n; i++) {
            String name = in.readString();
            hs.add(name);
            double speed = in.nextDouble();
            String supervisor = in.readString();
            Node cur = new Node(name, speed, supervisor);
            if (supervisor.equals("CEO")) {
                root = cur;
            }
            hm.put(name, cur);
        }

        for (Node node : hm.values()) {
            String supervisor = node.supervisor;
            if (supervisor.equals("CEO"))
                continue;
            hm.get(supervisor).children.add(node);
        }
        Info[] res = dfs(root);
        if (res[0].count == res[1].count)
            w.println(res[0].count + " "
                    + 1.0 * Math.max(res[0].total_speed / res[0].count, res[1].total_speed / res[1].count));
        else if (res[0].count > res[1].count) {
            w.println(res[0].count + " " + 1.0 * res[0].total_speed / res[0].count);
        } else {
            w.println(res[1].count + " " + 1.0 * res[1].total_speed / res[1].count);
        }
        w.println("recursive calls : " + r);
    }

    static class Info {
        double total_speed;
        int count;

        Info(double speed, int count) {
            this.total_speed = speed;
            this.count = count;
        }
    }

    // return useInfo(total_speed, num_count], dontUseInfo(total_speed, num_count)
    public static Info[] dfs(Node root) {
        if (root == null || root.children.size() == 0) {
            return new Info[] { new Info(0, 0), new Info(0, 0) };
        }
        r++;
        ArrayList<Info[]> subproblems = new ArrayList();
        for (Node child : root.children) {
            subproblems.add(dfs(child));
        }
        // for(Node child: )
        double use = 0, dont_use = 0;
        int use_count = 0, dont_use_count = 0;

        for (int i = 0; i < root.children.size(); i++) {

            Info[] subproblem = subproblems.get(i);
            Info subuse = subproblem[0], subdontuse = subproblem[1];
            if (subdontuse.count > subuse.count) {
                dont_use += subdontuse.total_speed;
                dont_use_count += subdontuse.count;
            } else if (subdontuse.count < subuse.count) {
                dont_use += subuse.total_speed;
                dont_use_count += subuse.count;
            } else {
                dont_use += Math.max(subuse.total_speed, subdontuse.total_speed);
                dont_use_count += Math.max(subuse.count, subdontuse.count);
            }

        }

        for (int i = 0; i < root.children.size(); i++) {
            Node child = root.children.get(i);
            Info[] subproblem = subproblems.get(i);

            Info subuse = subproblem[0], subdontuse = subproblem[1];

            int temp_count = dont_use_count;
            double temp_speed = dont_use;

            // attempt to use this child
            if (child != null) {
                if (subdontuse.count > subuse.count) {
                    temp_speed -= subdontuse.total_speed;
                    temp_count -= subdontuse.count;
                } else if (subdontuse.count < subuse.count) {
                    temp_speed -= subuse.total_speed;
                    temp_count -= subuse.count;
                } else {
                    temp_speed -= Math.max(subuse.total_speed, subdontuse.total_speed);
                    temp_count -= Math.max(subuse.count, subdontuse.count);
                }
                temp_count += subdontuse.count + 1;
                temp_speed += subdontuse.total_speed + Math.min(root.speed, child.speed);

                if (temp_count > use_count) {
                    use_count = temp_count;
                    use = temp_speed;

                } else if (temp_count == use_count)
                    use = Math.max(use, temp_speed);
            }
        }
        return new Info[] { new Info(use, use_count), new Info(dont_use, dont_use_count) };
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