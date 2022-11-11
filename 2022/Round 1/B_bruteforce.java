import java.util.*;
import java.io.*;

public class B_bruteforce {
    public static void main(String[] args) throws IOException {
        // TODO: Change the input file name for submission
        String filename = "watering_well_chapter_2_validation_input.txt";

        BufferedReader br = new BufferedReader(new FileReader(filename));
        PrintWriter pw = new PrintWriter(new FileWriter("output.txt"));
        int T = Integer.parseInt(br.readLine());
        final long NUM = 1000000007;

        for (int t = 1; t <= T; t++) {
            int N = Integer.parseInt(br.readLine());

            long[] A = new long[N], B = new long[N];

            for (int i = 0; i < N; i++) {
                StringTokenizer st = new StringTokenizer(br.readLine());
                A[i] = Long.parseLong(st.nextToken());
                B[i] = Long.parseLong(st.nextToken());
            }
            int Q = Integer.parseInt(br.readLine());

            long[] X = new long[Q], Y = new long[Q];

            for (int j = 0; j < Q; j++) {
                StringTokenizer st = new StringTokenizer(br.readLine());
                X[j] = Long.parseLong(st.nextToken());
                Y[j] = Long.parseLong(st.nextToken());
            }
            long ans = 0;

            for (int i = 0; i < N; i++) {
                for (int j = 0; j < Q; j++) {
                    ans += (A[i] - X[j]) * (A[i] - X[j]);
                    ans += (B[i] - Y[j]) * (B[i] - Y[j]);
                    ans %= NUM;
                }
            }

            pw.println(String.format("Case #%d: %d", t, ans));
        }
        pw.flush();
        pw.close();
        br.close();
    }
}