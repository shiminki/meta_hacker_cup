import java.util.*;
import java.io.*;

public class A2_testcase {
    public static void main(String[] args) throws IOException {
        PrintWriter pw = new PrintWriter(new FileWriter("perfectly_balanced_chapter_2_validation_input.txt"));
        int T = 10;
        pw.println(T);
        
        for (int t = 1; t <= T; t++) {
            int N = 10;
            pw.println(N);
            pw.print((int) (Math.random() * N / 2));
            for (int i = 1; i < N; i++) {
                pw.print(String.format(" %d", (int) (Math.random() * N / 2)));
            }
            pw.println();
            int Q = 10;
            pw.println(Q);

            for (int q = 0; q < Q; q++) {
                int type = Math.random() < 0.5 ? 1 : 2;
                int x = (int) (Math.random() * N) + 1;
                int y = (int) (Math.random() * N) + 1;
                if (x > y) {
                    int temp = x;
                    x = y;
                    y = temp;
                }
                pw.println(String.format("%d %d %d", type, x, y));
            }
        }
        pw.flush();
        pw.close();
    }
}
