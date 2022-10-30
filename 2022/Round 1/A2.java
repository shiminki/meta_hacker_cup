import java.util.*;
import java.io.*;

public class A2 {
    public static void main(String[] args) throws IOException {

        // BufferedReader br = new BufferedReader(new
        // FileReader("consecutive_cuts_chapter_2_input.txt"));
        BufferedReader br = new BufferedReader(new FileReader("consecutive_cuts_chapter_2_validation_input.txt"));
        PrintWriter pw = new PrintWriter(new FileWriter("output.txt"));
        int T = Integer.parseInt(br.readLine());

        for (int t = 1; t <= T; t++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int N = Integer.parseInt(st.nextToken());
            int K = Integer.parseInt(st.nextToken());
            int[] A = new int[N], B = new int[N];

            StringTokenizer st1 = new StringTokenizer(br.readLine());
            StringTokenizer st2 = new StringTokenizer(br.readLine());

            for (int i = 0; i < N; i++) {
                A[i] = Integer.parseInt(st1.nextToken());
                B[i] = Integer.parseInt(st2.nextToken());
            }

            int h1 = 0, h2 = 0, cnt = 0;
            boolean valid = true, flag = false;

            while (true) {
                if (A[h1] == B[h2]) {
                    cnt++;
                    h1 = (h1 + 1) % N;
                    h2 = (h2 + 1) % N;
                    if (cnt == N) {
                        valid = true;
                        break;
                    }

                    // pw.println(String.format("Match for %d at %d %d", A[h1], h1, h2));
                } else {
                    h1 = 0;
                    cnt = 0;
                    h2 = (h2 + 1) % N;
                    if (flag) {
                        valid = false;
                        break;
                    }
                }
                if (h2 == 0) {
                    flag = true;
                }
            }
            System.out.println(t + " " + valid);
            if (valid) {
                int j = h2;
                if (K == 0 && j != 0) {
                    valid = false;
                } else if (K == 1 && j == 0) {
                    h1 = 0;
                    h2 = 1;
                    cnt = 0;
                    valid = true;
                    flag = false;

                    while (true) {
                        if (A[h1] == B[h2]) {
                            cnt++;
                            h1 = (h1 + 1) % N;
                            h2 = (h2 + 1) % N;
                            if (cnt == N) {
                                valid = true;
                                break;
                            }

                            // pw.println(String.format("Match for %d at %d %d", A[h1], h1, h2));
                        } else {
                            h1 = 0;
                            cnt = 0;
                            h2 = (h2 + 1) % N;
                            if (flag) {
                                valid = false;
                                break;
                            }
                        }
                        if (h2 == 0) {
                            flag = true;
                        }
                    }
                    if (h2 == 0) {
                        valid = false;
                    }
                } else if (N == 2 && j != K % 2) {
                    valid = false;
                }
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