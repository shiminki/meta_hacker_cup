import java.util.*;
import java.io.*;

public class A {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter pw = new PrintWriter(new FileWriter("A_output.txt"));
		int T = Integer.parseInt(br.readLine());

		for (int t = 1; t <= T; t++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
            int N = Integer.parseInt(st.nextToken());
            int K = Integer.parseInt(st.nextToken());

            boolean valid = N <= 2 * K;
            st = new StringTokenizer(br.readLine());

            if (valid) {
                int[] cnt = new int[101]; // S[i] <= 100
                for (int i = 0; i < N; i++) {
                    int s = Integer.parseInt(st.nextToken());
                    cnt[s]++;
                    if (cnt[s] > 2) {
                        valid = false;
                        break;
                    }
                }
            }
            if (valid) {
                pw.println(String.format("Case #%d: YES", t));
            } else {
                pw.println(String.format("Case #%d: NO", t));
            }
		}
		pw.flush();
		pw.close();
		br.close();
	}
}