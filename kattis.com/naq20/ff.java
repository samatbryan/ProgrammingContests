import java.util.Scanner;

public class ff {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        int t = scan.nextInt();
        scan.nextLine();

        for (int i = 0; i < t; i++) {
            String s = scan.nextLine();
            if (s.startsWith("Simon says"))
                System.out.println(s.substring(10, s.length()));
        }

        scan.close();
    }
}