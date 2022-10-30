import java.util.*;
import java.io.*;

public class B {
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
            long x_sum = 0, y_sum = 0;

            for (int j = 0; j < Q; j++) {
                ans += (N * (X[j] * X[j] + Y[j] * Y[j]) % NUM) % NUM;
                ans %= NUM;
                x_sum += X[j];
                y_sum += Y[j];
                x_sum %= NUM;
                y_sum %= NUM;
            }
            for (int i = 0; i < N; i++) {
                ans += (Q * (A[i] * A[i] + B[i] * B[i]) % NUM) % NUM;
                ans -= (2 * (A[i] * x_sum + B[i] * y_sum) % NUM) % NUM;
                ans %= NUM;
            }
            if (ans < 0) {
                ans += NUM;
            }

            pw.println(String.format("Case #%d: %d", t, ans));
        }
        pw.flush();
        pw.close();
        br.close();
    }
}