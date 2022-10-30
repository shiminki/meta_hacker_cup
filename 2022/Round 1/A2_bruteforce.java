import java.util.*;
import java.io.*;

public class A2_bruteforce {
    public static void main(String[] args) throws IOException {
        // TODO: Change filename
        String filename = "consecutive_cuts_chapter_2_validation_input.txt";
        BufferedReader br = new BufferedReader(new FileReader(filename));
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

            boolean valid = false;
            List<Integer> validOffset = new ArrayList<>();

            for (int j = 0; j < N; j++) {
                if (circularMatch(A, B, j)) {
                    valid = true;
                    validOffset.add(j);
                }
            }
            if (validOffset.size() == 1) {
                int j = validOffset.get(0);
            
                if (K == 0 && j != 0) {
                    valid = false;
                } else if (K == 1 && j == 0) {
                    valid = false;
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
    static boolean circularMatch(int[] A, int[] B, int j) {
        int N = A.length;

        for (int i = 0; i < N; i++) {
            if (A[i] != B[(i + j) % N]) {
                return false;
            }
        }
        return true;
    }
}