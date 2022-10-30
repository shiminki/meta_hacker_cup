import java.util.*;
import java.io.*;

public class A1 {
	public static void main(String[] args) throws IOException {
		// TODO: Change the input file name for submission
		String filename = "perfectly_balanced_chapter_1_input.txt";
		BufferedReader br = new BufferedReader(new FileReader(filename));
		PrintWriter pw = new PrintWriter(new FileWriter("output.txt"));
		int T = Integer.parseInt(br.readLine());

		for (int t = 1; t <= T; t++) {
			// Need O(c*(|S| + Q)) solution
			String S = br.readLine();
			int[][] cnt = new int[S.length() + 1][26];

			for (int i = 0; i < S.length(); i++) {
				for (int j = 0; j < 26; j++) {
					cnt[i + 1][j] = cnt[i][j];
				}
				int idx = (int) S.charAt(i) - 'a';
				cnt[i + 1][idx]++;
			}

			// System.out.println(Arrays.deepToString(cnt));

			int Q = Integer.parseInt(br.readLine());
			int num = 0;
			for (int q = 0; q < Q; q++) {
				StringTokenizer st = new StringTokenizer(br.readLine());
				int l = Integer.parseInt(st.nextToken()) - 1;
				int r = Integer.parseInt(st.nextToken());

				int mismatch1 = 0, mismatch2 = 0;
				for (int c = 0; c < 26; c++) {
					int mid = (l + r) / 2;
					int diff1 = Math.abs((cnt[r][c] - cnt[mid][c]) - (cnt[mid][c] - cnt[l][c]));
					int diff2 = Math.abs((cnt[r][c] - cnt[mid + 1][c]) - (cnt[mid + 1][c] - cnt[l][c]));
					mismatch1 += diff1;
					mismatch2 += diff2;
				}
				if (mismatch1 == 1 || mismatch2 == 1) {
					num++;					
				}
			}

			pw.println(String.format("Case #%d: %d", t, num));

		}
		pw.flush();
		pw.close();
		br.close();
	}
}