import java.util.*;
import java.io.*;

public class D {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader("D_input.txt"));
		PrintWriter pw = new PrintWriter(new FileWriter("D_output.txt"));
		int T = Integer.parseInt(br.readLine());

		for (int t = 1; t <= T; t++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
            int N = Integer.parseInt(st.nextToken());
            int M = Integer.parseInt(st.nextToken());
            int Q = Integer.parseInt(st.nextToken());

            List<Map<Integer, Integer>> adj = new ArrayList<>();
            List<Map<Integer, Long>> ans = new ArrayList<>();

            for (int i = 0; i <= N; i++) {
                adj.add(new HashMap<>());
                ans.add(new HashMap<>());
            }

            for (int j = 0; j < M; j++) {
                st = new StringTokenizer(br.readLine());
                int a = Integer.parseInt(st.nextToken());
                int b = Integer.parseInt(st.nextToken());
                int c = Integer.parseInt(st.nextToken());
                // bi-directional graph
                adj.get(a).put(b, c);
                adj.get(b).put(a, c);
            }
            List<Long> answerList = new ArrayList<>();

            for (int q = 0; q < Q; q++) {
                st = new StringTokenizer(br.readLine());
                int x = Integer.parseInt(st.nextToken());
                int y = Integer.parseInt(st.nextToken());

                if (ans.get(x).containsKey(y)) {
                    answerList.add(ans.get(x).get(y));
                } else {
                    long answer = 0;

                    if (adj.get(x).containsKey(y)) {
                        answer += 2 * adj.get(x).get(y);
                    }
                    if (adj.get(x).size() > adj.get(y).size()) {
                        int temp = x;
                        x = y;
                        y = temp;
                    }
                    for (Map.Entry<Integer, Integer> e : adj.get(x).entrySet()) {
                        int v = e.getKey();
                        if (adj.get(y).containsKey(v)) {
                            answer += Math.min(adj.get(x).get(v), adj.get(y).get(v));
                        }
                    }
                    answerList.add(answer);
                    ans.get(x).put(y, answer);
                    ans.get(y).put(x, answer);
                }
            }

			pw.print(String.format("Case #%d:", t));

			for (long ppl : answerList) {
                pw.print(String.format(" %d", ppl));
            }
            pw.println();
		}
		pw.flush();
		pw.close();
		br.close();
	}
}