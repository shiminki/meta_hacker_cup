import java.util.*;
import java.io.*;

public class A1_special {
    public static void main(String[] args) throws IOException {
		// TODO: Change the input file name for submission
		String filename = "perfectly_balanced_chapter_1_validation_input.txt";
		BufferedReader br = new BufferedReader(new FileReader(filename));
		PrintWriter pw = new PrintWriter(new FileWriter("output.txt"));
		int T = Integer.parseInt(br.readLine());

		for (int t = 1; t <= T; t++) {
			String S = br.readLine();

            int Q = Integer.parseInt(br.readLine());
            int cnt = 0;

            for (int q = 0; q < Q; q++) {
                StringTokenizer st = new StringTokenizer(br.readLine());
				int l = Integer.parseInt(st.nextToken()) - 1;
				int r = Integer.parseInt(st.nextToken());

                int yCnt = 0, jCnt = 0;

                for (int i = l; i < r; i++) {
                    if (S.charAt(i) == 'y') yCnt ++;
                    if (S.charAt(i) == 'j') jCnt ++;
                }

                if (Math.abs(yCnt - jCnt) == 1) {
                    cnt ++;
                }
            }
            pw.println(String.format("Case #%d: %d", t, cnt));
        }
    }
}
