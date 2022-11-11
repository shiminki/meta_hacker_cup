import java.util.*;
import java.io.*;

public class A2_v2 {
    public static void main(String[] args) throws IOException {
        // TODO: change filename for submission
        String filename = "consecutive_cuts_chapter_2_input.txt";
        BufferedReader br = new BufferedReader(new FileReader(filename));
        PrintWriter pw = new PrintWriter(new FileWriter("output.txt"));
        int T = Integer.parseInt(br.readLine());

        for (int t = 1; t <= T; t++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int N = Integer.parseInt(st.nextToken());
            int K = Integer.parseInt(st.nextToken());
            int[] A = new int[N], B = new int[2 * N];

            StringTokenizer st1 = new StringTokenizer(br.readLine());
            StringTokenizer st2 = new StringTokenizer(br.readLine());

            for (int i = 0; i < N; i++) {
                A[i] = Integer.parseInt(st1.nextToken());
                B[i] = Integer.parseInt(st2.nextToken());
                B[i + N] = B[i];
            }

            int[] table = getTable(A);
            List<Integer> pos = getPos(B, A, table);

            boolean valid = false;

            if (pos.size() > 0) {
                valid = true;

                if (K == 0 && pos.get(0) != 0) {
                    valid = false;
                } else if (pos.size() == 1 && K == 1 && pos.get(0) == 0) {
                    valid = false;
                } else if (N == 2 && pos.size() == 1 && K != pos.get(0) % 2) {
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

    static int[] getTable(int[] w) {
        int[] T = new int[w.length + 1];
        int pos = 1, cnd = 0;
        T[0] = -1;

        while (pos < w.length) {
            if (w[pos] == w[cnd]) {
                T[pos] = T[cnd];
            } else {
                T[pos] = cnd;
                while (cnd >= 0 && w[pos] != w[cnd]) {
                    cnd = T[cnd];
                }
            }
            pos++;
            cnd++;
        }
        T[pos] = cnd;

        return T;
    }

    static List<Integer> getPos(int[] s, int[] w, int[] T) {
        int j = 0, k = 0, np = 0;
        List<Integer> p = new ArrayList<>();

        while (j < s.length) {
            if (w[k] == s[j]) {
                j++;
                k++;
                if (k == w.length) {
                    p.add(j - k);
                    k = T[k];
                }
            } else {
                k = T[k];
                if (k < 0) {
                    j++;
                    k++;
                }
            }
        }
        return p;
    }
}