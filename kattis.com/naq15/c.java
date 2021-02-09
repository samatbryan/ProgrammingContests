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

public class c {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter w = new PrintWriter(System.out);
        int T = 1;

        c ok = new c();
        for (int i = 0; i < T; i++)
            ok.solve(in, w);
        w.close();
    }

    HashMap<String, HashSet<String>> g;
    HashMap<String, HashSet<String>> rg;

    public void solve(InputReader in, PrintWriter w) {
        int n = in.ii();
        HashMap<String, HashSet<String>> graph = new HashMap();
        HashMap<String, Integer> lang_count = new HashMap();
        HashSet<String> nodes = new HashSet();
        rg = new HashMap();
        g = new HashMap();

        for (int i = 0; i < n; i++) {
            String[] tokens = in.nextLine().split(" ");
            String lang = tokens[1];
            lang_count.put(lang, lang_count.getOrDefault(lang, 0) + 1);
            graph.putIfAbsent(lang, new HashSet());
            nodes.add(lang);
            for (int j = 2; j < tokens.length; j++) {
                graph.get(lang).add(tokens[j]);
            }
        }
        HashMap<String, HashSet<String>> new_graph = new HashMap();
        for (String lang : graph.keySet()) {
            HashSet<String> neighbors = new HashSet();
            for (String neigh : graph.get(lang)) {
                if (nodes.contains(neigh)) {
                    neighbors.add(neigh);
                }
            }
            new_graph.put(lang, neighbors);
        }
        this.g = new_graph;
        Stack<String> stack = new Stack();
        HashSet<String> visited1 = new HashSet();
        for (String cur_node : g.keySet()) {
            rg.put(cur_node, new HashSet());
            if (!visited1.contains(cur_node)) {
                dfs(cur_node, visited1, stack);
            }
        }

        // reverse the graph
        for (String cur_node : g.keySet()) {
            for (String neighbor : g.get(cur_node)) {
                rg.get(neighbor).add(cur_node);
            }
        }
        int group = 0;
        HashMap<Integer, ArrayList<String>> groups = new HashMap();
        HashSet<String> visited2 = new HashSet();
        while (stack.size() > 0) {
            String cur = stack.pop();
            if (visited2.contains(cur))
                continue;
            groups.putIfAbsent(group, new ArrayList());
            explore(groups, cur, group, visited2);
            group++;
        }
        int res = n;
        int max_scc = 0;
        for (int group_num : groups.keySet()) {
            int tmp = 0;
            for (String curlang : groups.get(group_num)) {
                tmp += lang_count.get(curlang);
            }
            max_scc = Math.max(max_scc, tmp);
        }
        w.println(res - max_scc);
        // find the largest SCC:

    }

    public void explore(HashMap<Integer, ArrayList<String>> groups, String cur, int group, HashSet<String> visited) {
        visited.add(cur);
        groups.get(group).add(cur);

        for (String neighbor : rg.get(cur)) {
            if (!visited.contains(neighbor))
                explore(groups, neighbor, group, visited);
        }
    }

    public void dfs(String cur, HashSet<String> visited, Stack<String> stack) {
        visited.add(cur);
        for (String neighbor : g.get(cur)) {
            if (!visited.contains(neighbor))
                dfs(neighbor, visited, stack);
        }
        stack.push(cur);

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