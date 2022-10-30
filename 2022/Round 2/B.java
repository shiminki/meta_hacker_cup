import java.util.*;
import java.io.*;

public class B {
    public static void main(String[] args) throws IOException {
        // TODO: Change the input file name for submission
        String filename = "balance_sheet_validation_input.txt";
        BufferedReader br = new BufferedReader(new FileReader(filename));
        PrintWriter pw = new PrintWriter(new FileWriter("output.txt"));
        int T = Integer.parseInt(br.readLine());
        final long NUM = 1000000007;

        for (int t = 1; t <= T; t++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int N = Integer.parseInt(st.nextToken());
            int K = Integer.parseInt(st.nextToken());

            Map<Integer, List<Integer>> buy = new HashMap<>();
            Map<Integer, List<Integer>> sell = new HashMap<>();

            for (int i = 0; i < N; i++) {
                st = new StringTokenizer(br.readLine());
                int A = Integer.parseInt(st.nextToken());
                int B = Integer.parseInt(st.nextToken());
                int X = Integer.parseInt(st.nextToken());
                int Y = Integer.parseInt(st.nextToken());

                if (!buy.containsKey(A)) {
                    buy.put(A, new ArrayList<>());
                }
                if (!sell.containsKey(B)) {
                    sell.put(B, new ArrayList<>());
                }
                buy.get(A).add(X);
                sell.get(B).add(Y);
            }
            TreeSet<Long> profit = new TreeSet<>();

            for (Map.Entry<Integer, List<Integer>> e : sell.entrySet()) {
                int day = e.getKey();
                List<Integer> sellPrices = e.getValue();

                if (buy.containsKey(day)) {
                    for (int sellPrice : sellPrices) {
                        for (int buyPrice : buy.get(day)) {
                            if (sellPrice < buyPrice) {
                                profit.add((long)  buyPrice - sellPrice);

                                if (profit.size() > K) {
                                    profit.pollFirst();
                                }
                            }
                        }
                    }
                }
            }

           
            long ans = 0;

            Iterator value = profit.iterator();

            while (value.hasNext()) {
                ans += (long) value.next();
                ans %= NUM;
            }

            pw.println(String.format("Case #%d: %d", t, ans));

        }
        pw.flush();
        pw.close();
        br.close();
    }
}


// if (!buy.get(A).containsKey(X)) {
//     buy.get(A).put(X, 0);
// }
// int buyCnt = buy.get(A).get(X);
// buy.get(A).put(X, buyCnt + 1);

// if (!sell.containsKey(B)) {
//     sell.put(B, new HashMap<>());
// }
// if (!sell.get(B).containsKey(Y)) {
//     sell.get(B).put(Y, 0);
// }
// int sellCnt = sell.get(B).get(Y);
// sell.get(B).put(Y, sellCnt + 1);
