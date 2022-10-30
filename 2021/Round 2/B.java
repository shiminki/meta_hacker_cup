import java.util.*;
import java.io.*;

public class B {
    static int[] depth, first, eulerTour;
    static int idx, ans;

    public static void main(String[] args) throws IOException {
        // TODO: Change the input file name for submission
        // String filename = "B_input.txt";
        String filename = "chainblock_input.txt";
        BufferedReader br = new BufferedReader(new FileReader(filename));
        PrintWriter pw = new PrintWriter(new FileWriter("output.txt"));
        int T = Integer.parseInt(br.readLine());

        for (int t = 1; t <= T; t++) {
            System.out.println(String.format("Case #%d", t));
            int N = Integer.parseInt(br.readLine());
            List<Set<Integer>> adj = new ArrayList<>();

            for (int i = 0; i < N; i++) {
                adj.add(new HashSet<>());
            }

            for (int i = 0; i < N - 1; i++) {
                StringTokenizer st = new StringTokenizer(br.readLine());
                int A = Integer.parseInt(st.nextToken()) - 1;
                int B = Integer.parseInt(st.nextToken()) - 1;
                adj.get(A).add(B);
                adj.get(B).add(A);
            }
            // full dfs - O(N)
            eulerTour = new int[2 * N - 1];
            first = new int[N];
            depth = new int[N];
            idx = 0;

            dfs(adj, 0, 0, 1);

            // reads in frequency & calculate L[f]

            int[] F = new int[N];
            Map<Integer, List<Integer>> fSet = new HashMap<>();
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int i = 0; i < N; i++) {
                F[i] = Integer.parseInt(st.nextToken());
                if (!fSet.containsKey(F[i])) {
                    fSet.put(F[i], new ArrayList<>());
                }
                fSet.get(F[i]).add(i);
            }

            int pow2 = 1;

            while (pow2 < 2 * N - 1) {
                pow2 *= 2;
            }
            // pow2 ~= 2*N
            int[] tree = new int[2 * pow2]; // 1 indexed

            for (int i = 0; i < 2 * N - 1; i++) {
                tree[pow2 + i] = eulerTour[i];
            }
            for (int i = 2 * N - 1; i < pow2; i++) {
                tree[pow2 + i] = -1;
            }

            for (int i = pow2 - 1; i >= 0; i--) {
                if (tree[2 * i] == -1) {
                    tree[i] = -1;
                } else if (tree[2 * i + 1] == -1) {
                    tree[i] = tree[2 * i];
                } else {
                    tree[i] = depth[tree[2 * i]] < depth[tree[2 * i + 1]] ? tree[2 * i] : tree[2 * i + 1];
                }
            }
            // L[f] = lowest common ancestor for all verticies
            // with frequency f
            Map<Integer, Integer> L = new HashMap<>();

            for (Map.Entry<Integer, List<Integer>> entry : fSet.entrySet()) {
                int f = entry.getKey();
                List<Integer> S = entry.getValue();
                if (S.size() == 1) {
                    L.put(f, S.get(0));
                } else {
                    // a and b are indicies of tree
                    // lca is the vertex
                    int a = first[S.get(0)];
                    int b = first[S.get(1)];
                    int lca = rmq(tree, a, b);

                    for (int i = 2; i < S.size(); i++) {
                        a = first[lca];
                        b = first[S.get(i)];
                        lca = rmq(tree, a, b);
                    }
                    L.put(f, lca);
                }
            }
            // Z[i] = min height of L[f] where f is all the frequencies
            // under the substree of i
            int[] Z = new int[N];

            for (int i = 0; i < N; i++) {
                Z[i] = depth[L.get(F[i])];
            }

            Z = dfs_z(adj, Z, 0, 0);

            // System.out.println(Arrays.toString(depth));
            // System.out.println(Arrays.toString(eulerTour));
            // System.out.println(Arrays.toString(first));
            // System.out.println(Arrays.toString(tree));
            // System.out.println(L);
            // System.out.println(Arrays.toString(Z));

            ans = 0;
            dfs_cnt(adj, Z, 0, 0);

            pw.println(String.format("Case #%d: %d", t, ans));

        }
        pw.flush();
        pw.close();
        br.close();
    }

    static void dfs_cnt(List<Set<Integer>> adj, int[] Z, int v, int p) {
        if (Z[v] == depth[v] && v != p) {
            ans++;
        }

        for (int w : adj.get(v)) {
            if (w != p) {
                dfs_cnt(adj, Z, w, v);
            }
        }
        return;
    }

    static int[] dfs_z(List<Set<Integer>> adj, int[] Z, int v, int p) {
        for (int w : adj.get(v)) {
            if (w != p) {
                Z = dfs_z(adj, Z, w, v);
            }
        }
        Z[p] = Math.min(Z[p], Z[v]);
        return Z;
    }

    static int rmq(int[] tree, int a, int b) {
        // returns range minimum of depth[arr[a~b]]
        int n = tree.length / 2;

        a += n;
        b += n;
        int min = tree[a];

        while (a <= b) {
            if (a % 2 == 1) {
                min = depth[tree[a]] < depth[min] ? tree[a] : min;
                a++;
            }
            if (b % 2 == 0) {
                min = depth[tree[b]] < depth[min] ? tree[b] : min;
                b--;
            }
            a /= 2;
            b /= 2;
        }
        return min;
    }

    static void dfs(List<Set<Integer>> adj, int v, int p, int d) {
        depth[v] = d;
        eulerTour[idx] = v;
        first[v] = idx++;

        for (int w : adj.get(v)) {
            if (w != p) {
                dfs(adj, w, v, d + 1); // visit w
                eulerTour[idx] = v; // now at vertex v
                idx++;
            }
        }
        return;
    }
}