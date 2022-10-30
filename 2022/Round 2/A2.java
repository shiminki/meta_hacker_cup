import java.util.*;
import java.io.*;
import java.math.BigInteger;

public class A2 {
	static final long P = Long.parseLong("985412103810300293");
	static BigInteger a, b;

	public static void main(String[] args) throws IOException {
		// TODO: Change the input file name for submission
		String filename = "perfectly_balanced_chapter_2_input.txt";
		BufferedReader br = new BufferedReader(new FileReader(filename));
		PrintWriter pw = new PrintWriter(new FileWriter("output.txt"));
		int T = Integer.parseInt(br.readLine());

		for (int t = 1; t <= T; t++) {
			// O(N + Q log N) time
			System.out.println(String.format("Case #%d", t));
			int N = Integer.parseInt(br.readLine());
			StringTokenizer st = new StringTokenizer(br.readLine());
			a = new BigInteger(String.valueOf((long)(Math.random() * (P - 1)) + 1));
			b = new BigInteger(String.valueOf((long)(Math.random() * P)));
			long[] hashed = new long[N];
			Map<Long, Integer> hashcnt = new HashMap<>();

			for (int i = 0; i < N; i++) {
				hashed[i] = h(Integer.parseInt(st.nextToken()));
				if (! hashcnt.containsKey(hashed[i])) {
					hashcnt.put(hashed[i], 0);
				}
				int cnt = hashcnt.get(hashed[i]);
				hashcnt.put(hashed[i], cnt + 1);
			}

			BigSegmentTree segtree = new BigSegmentTree(hashed); // O(N)

			int Q = Integer.parseInt(br.readLine());
			int num = 0;

			// O(Q log N)
			for (int q = 0; q < Q; q++) {
				st = new StringTokenizer(br.readLine());
				int type = Integer.parseInt(st.nextToken());
				int x = Integer.parseInt(st.nextToken()) - 1;
				int y = Integer.parseInt(st.nextToken());

				if (type == 1) {
					// remove previous value
					int cnt = hashcnt.get(hashed[x]);
					if (cnt > 1)
						hashcnt.put(hashed[x], cnt - 1);
					else 
						hashcnt.remove(hashed[x]);

					// update query
					hashed[x] = h(y);
					segtree.updateQuery(x, hashed[x]); // O(log N)

					if (! hashcnt.containsKey(hashed[x])) {
						hashcnt.put(hashed[x], 0);
					}
					cnt = hashcnt.get(hashed[x]);
					hashcnt.put(hashed[x], cnt + 1);
				} else {
					int mid = (x + y) / 2;
					// O(log N)
					BigInteger leftSum = segtree.sumQuery(x, mid);
					BigInteger rightSum = segtree.sumQuery(mid + 1, y);

					long diff1 = (leftSum.subtract(rightSum).longValue() + hashed[mid] + P) % P;
					long diff2 = (rightSum.subtract(leftSum).longValue() + hashed[mid] + P) % P;

					// int diff1 = (int)((leftSum + hashed[mid] - rightSum + P) % P);
					// int diff2 = (int)((rightSum + hashed[mid] - leftSum + P) % P);

					// System.out.println(diff1 + " " + diff2);
					

					if (hashcnt.containsKey(diff1)) {
						num++;
					} else if (hashcnt.containsKey(diff2)) {
						num++;
					}
					
				}
			}

			pw.println(String.format("Case #%d: %d", t, num));

		}
		pw.flush();
		pw.close();
		br.close();
	}
	static long h(int x) {
		BigInteger X = new BigInteger(String.valueOf(x));

		BigInteger shift = X.multiply(a).add(b);

		return shift.remainder(new BigInteger(String.valueOf(P))).longValue();
	}
}
class BigSegmentTree {
	List<BigInteger> tree;

	public BigSegmentTree(long[] A) {
		// O(n)
		int n = A.length;
		int x = (int) (Math.ceil(Math.log(n) / Math.log(2)));
 
        //Maximum size of segment tree
        int max_size = 2 * (int) power(2, x) - 1;

		tree = new ArrayList<>();

		// initialize tree
		for (int i =0; i < max_size; i++) {
			tree.add(BigInteger.ZERO);
		}

		for (int i = 0; i < n; i++) {
			tree.set(treeIdx(i), new BigInteger(String.valueOf(A[i])));
		}
		for (int i = (tree.size() - 1) / 2 - 1; i >= 0; i--) {
			int left = 2 * i + 1, right = 2 * i + 2;
			BigInteger sum = BigInteger.ZERO;
			if (right < tree.size()) {
				sum = tree.get(left).add(tree.get(right));
			} else if (left < tree.size()) {
				sum = tree.get(left).add(BigInteger.ZERO);
			}
			tree.set(i, sum);
		}
	}
	int power(int a, int b) {
		// return a ^ b
		if (b == 0) {
			return 1;
		} else {
			int s = power(a, b / 2);

			if (b % 2 == 0) {
				return s * s;
			} else {
				return s * s * a;
			}
		}
	}

	int treeIdx(int idx) {
		// returns index at the tree
		// Hence A[idx] corresponds to tree[treeIdx(idx)]
		return (tree.size() - 1) / 2 + idx;
	}
	public BigInteger sumQuery(int i, int j) {
		return sumQueryUtil(treeIdx(i), treeIdx(j), 0, treeIdx(0), tree.size());
	}
	BigInteger sumQueryUtil(int i, int j, int node, int start, int end) {
		if (i <= start && j >= end) {
			// if the range of the node is a 
			// subset of the given query range
			return tree.get(node);
		} else if (j <= start || i >= end) {
			// if the range has no intersection
			// with the query range
			return BigInteger.ZERO;
		} else {
			int left = 2 * node + 1, right = 2 * node + 2;
			int mid = (start + end) / 2;
			BigInteger leftSum = sumQueryUtil(i, j, left, start, mid);
			BigInteger rightSum = sumQueryUtil(i, j, right, mid, end);
			return leftSum.add(rightSum);
		}
	}
	public void updateQuery(int idx, long val) {
		tree.set(treeIdx(idx), new BigInteger(String.valueOf(val)));

		int currentIdx = treeIdx(idx);

		while (currentIdx > 0) {
			currentIdx = (currentIdx - 1) / 2;
			int left = 2 * currentIdx + 1, right = 2 * currentIdx + 2;

			if (right < tree.size()) {
				tree.set(currentIdx, tree.get(left).add(tree.get(right)));
			} else if (left < tree.size()) {
				tree.set(currentIdx, tree.get(left).add(BigInteger.ZERO));
			}
		}
	}
}

class SegmentTree {
	long[] tree;

	public SegmentTree(int[] A) {
		// O(n)
		int n = A.length;
		int x = (int) (Math.ceil(Math.log(n) / Math.log(2)));
 
        //Maximum size of segment tree
        int max_size = 2 * (int) power(2, x) - 1;
 
		tree = new long[max_size];

		for (int i = 0; i < n; i++) {
			tree[treeIdx(i)] = A[i];
		}
		for (int i = (tree.length - 1) / 2 - 1; i >= 0; i--) {
			int left = 2 * i + 1, right = 2 * i + 2;
			if (right < tree.length) {
				tree[i] = tree[left] + tree[right];
			} else if (left < tree.length) {
				tree[i] = tree[left];
			}
		}
	}
	int power(int a, int b) {
		// return a ^ b
		if (b == 0) {
			return 1;
		} else {
			int s = power(a, b / 2);

			if (b % 2 == 0) {
				return s * s;
			} else {
				return s * s * a;
			}
		}
	}
	int treeIdx(int idx) {
		// returns index at the tree
		// Hence A[idx] corresponds to tree[treeIdx(idx)]
		return (tree.length - 1) / 2 + idx;
	}

	public long sumQuery(int i, int j) {
		return sumQueryUtil(treeIdx(i), treeIdx(j), 0, treeIdx(0), tree.length);
	}
	long sumQueryUtil(int i, int j, int node, int start, int end) {
		if (i <= start && j >= end) {
			// if the range of the node is a 
			// subset of the given query range
			return tree[node];
		} else if (j <= start || i >= end) {
			// if the range has no intersection
			// with the query range
			return 0;
		} else {
			int left = 2 * node + 1, right = 2 * node + 2;
			int mid = (start + end) / 2;
			long leftSum = sumQueryUtil(i, j, left, start, mid);
			long rightSum = sumQueryUtil(i, j, right, mid, end);
			return leftSum + rightSum;
		}
	}
	public void updateQuery(int idx, int val) {
		tree[treeIdx(idx)] = val;

		int currentIdx = treeIdx(idx);

		while (currentIdx > 0) {
			currentIdx = (currentIdx - 1) / 2;
			int left = 2 * currentIdx + 1, right = 2 * currentIdx + 2;

			if (right < tree.length) {
				tree[currentIdx] = tree[left] + tree[right];
			} else if (left < tree.length) {
				tree[currentIdx] = tree[left];
			}
		}
	}
}
