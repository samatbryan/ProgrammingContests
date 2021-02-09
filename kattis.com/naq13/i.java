import java.util.Scanner;

public class i {

    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);

        while (in.hasNextLine()) {

            String[] bottom = in.nextLine().split(" ");
            String[] top = in.nextLine().split(" ");

            double d1 = -1 * Double.parseDouble(bottom[0]) + Double.parseDouble(top[0]) + 100000;
            double d2 = -1 * Double.parseDouble(bottom[1]) + Double.parseDouble(top[1]);
            double d3 = -1 * Double.parseDouble(bottom[2]) + Double.parseDouble(top[2]);
            double d4 = -1 * Double.parseDouble(bottom[3]) + Double.parseDouble(top[3]);

            Polynomial difference = new Polynomial(d1, d2, d3, d4);

            double delta = 0.00001;
            double ans = 0;
            double min = Double.MAX_VALUE;

            for (double i = 0; i <= 1; i += delta) {

                double candidate = difference.evaluate(i);

                if (candidate < min) {
                    min = candidate;
                }

                if (candidate > ans) {
                    ans = candidate;
                }

            }

            System.out.println(String.format("%.6f", ans - min));

        }

    }

    public static class Polynomial {

        double t0, t1, t2, t3;

        public Polynomial(double a, double b, double c, double d) {
            t0 = a;
            t1 = b;
            t2 = c;
            t3 = d;

        }

        double evaluate(double d) {
            double ans = t0;
            ans += (d * t1);
            ans += (d * d * t2);
            ans += (d * d * d * t3);

            return ans;
        }

        public String toString() {
            return String.format("%f + %fx + %fx^2 + %fx^3", t0, t1, t2, t3);
        }

    }

}
