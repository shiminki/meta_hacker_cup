import java.util.*;
import java.io.*;

public class B1 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("B1_input.txt"));
        PrintWriter pw = new PrintWriter(new FileWriter("B1_output.txt"));
        int T = Integer.parseInt(br.readLine());

        for (int t = 1; t <= T; t++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int R = Integer.parseInt(st.nextToken());
            int C = Integer.parseInt(st.nextToken());
            boolean[][] tree = new boolean[R][C];
            boolean treePresent = false;

            for (int i = 0; i < R; i++) {
                String line = br.readLine();
                for (int j = 0; j < C; j++) {
                    tree[i][j] = line.charAt(j) == '^';
                    if (tree[i][j]) {
                        treePresent = true;
                    }
                }
            }
            if ((R == 1 || C == 1) && treePresent) {
                // not valid
                pw.println(String.format("Case #%d: Impossible", t));
            } else {
                pw.println(String.format("Case #%d: Possible", t));

                for (int i = 0; i < R; i++) {
                    for (int j = 0; j < C; j++) {
                        if (treePresent) {
                            pw.print('^');
                        } else {
                            pw.print('.');
                        }
                    }
                    pw.println();
                }

            }
        }
        pw.flush();
        pw.close();
        br.close();
    }
}