import java.util.*;
import java.io.*;

public class C1 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("C1_input.txt"));
        PrintWriter pw = new PrintWriter(new FileWriter("C1_output.txt"));
        int T = Integer.parseInt(br.readLine());

        for (int t = 1; t <= T; t++) {
            int N = Integer.parseInt(br.readLine());
            String C1 = br.readLine();
            int L = C1.length();
            char a = C1.charAt(L - 1);
            char b = a == '.' ? '-' : '.';

            pw.println(String.format("Case #%d:", t));
            for (int i = 0; i < 200; i++) {
                pw.print(b);
            }
            pw.println();
            for (int i = 0; i < N - 2; i++) {
                for (int j = 0; j < L + i; j++) {
                    pw.print(b);
                }
                pw.println(a);
            }
        }
        pw.flush();
        pw.close();
        br.close();
    }
}