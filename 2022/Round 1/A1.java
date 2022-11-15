import java.util.*;
import java.io.*;

public class A1 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("consecutive_cuts_chapter_1_input.txt"));
        PrintWriter pw = new PrintWriter(new FileWriter("A1_output.txt"));
        int T = Integer.parseInt(br.readLine());

        for (int t = 1; t <= T; t++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int N = Integer.parseInt(st.nextToken());
            int K = Integer.parseInt(st.nextToken());
            int[] A = new int[N], B = new int[N];
            int[] idx = new int[N + 1];

            StringTokenizer st1 = new StringTokenizer(br.readLine());
            StringTokenizer st2 = new StringTokenizer(br.readLine());

            for (int i = 0; i < N; i++) {
                A[i] = Integer.parseInt(st1.nextToken());
                B[i] = Integer.parseInt(st2.nextToken());
                idx[A[i]] = i;
            }

            // check if A and B are circular

            int j = idx[B[0]];
            boolean valid = true;

            for (int i = 0; i < N; i++) {
                int k = idx[B[i]] - i;

                if (k < 0) {
                    k += N;
                }

                if (j == k) {
                    continue;
                } else {
                    valid = false;
                    break;
                }
            }
            if (K == 0 && j != 0) {
                valid = false;
            } else if (K == 1 && j == 0) {
                valid = false;
            } else if (N == 2 && j != K % 2) {
                valid = false;
            }
            if (valid)
                pw.println(String.format("Case #%d: YES", t));
            else
                pw.println(String.format("Case #%d: NO", t));
        }
        pw.flush();
        pw.close();
        br.close();
    }
}
