import java.util.*;
import java.io.*;

public class A {
    public static void main(String[] args) throws IOException {
        // TODO: Change the input file name for submission
        String filename = "runway_input.txt";
        BufferedReader br = new BufferedReader(new FileReader(filename));
        PrintWriter pw = new PrintWriter(new FileWriter("output.txt"));
        int T = Integer.parseInt(br.readLine());

        for (int t = 1; t <= T; t++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int N = Integer.parseInt(st.nextToken());
            int M = Integer.parseInt(st.nextToken());
            int[][] P = new int[N + 1][M]; // P[0] = S

            for (int i = 0; i <= N; i++) {
                st = new StringTokenizer(br.readLine());
                for (int j = 0; j < M; j++) {
                    P[i][j] = Integer.parseInt(st.nextToken());
                }
                Arrays.sort(P[i]); // O(N log N)
                // TODO: use radix sort for O(N) implementation
            }
            int ans = 0;

            Map<Integer, Integer> unused = new HashMap<>();

            for (int i = 0; i < M; i++) {
                if (!unused.containsKey(P[0][i])) {
                    unused.put(P[0][i], 0);
                }
                unused.put(P[0][i], unused.get(P[0][i]) + 1);
            }

            for (int i = 0; i < N; i++) {
                boolean[] changed = new boolean[M];
                int idx = 0;
                int[] current = P[i], next = P[i + 1];

                for (int j = 0; j < M; j++) {
                    if (idx < M) {
                        while (idx < M && current[j] > next[idx]) {
                            idx++;
                        }
                        if (idx < M && current[j] == next[idx]) {
                            idx++;
                            changed[j] = true;
                        }
                    }
                }
                for (int j = 0; j < M; j++) {
                    if (!changed[j]) {
                        if (unused.containsKey(current[j])) {
                            unused.put(current[j], unused.get(current[j]) - 1);
                            if (unused.get(current[j]) == 0) {
                                unused.remove(current[j]);
                            }
                        } else {
                            ans++;
                        }
                    }
                }
            }

            pw.println(String.format("Case #%d: %d", t, ans));
        }
        pw.close();
        br.close();
    }
}