import java.util.*;
import java.io.*;

public class D1 {
    public static void main(String[] args) throws IOException {
        // TODO: Change the input file name for submission
        String filename = "D_in.txt";
        BufferedReader br = new BufferedReader(new FileReader(filename));
        PrintWriter pw = new PrintWriter(new FileWriter("output.txt"));
        int T = Integer.parseInt(br.readLine());

        for (int t = 1; t <= T; t++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int N = Integer.parseInt(st.nextToken());
            int M = Integer.parseInt(st.nextToken());

            int[] A = new int[N];
            int[][] cnt = new int[4][N];
            List<SegTree> segTreeList = new ArrayList<>();
            List<TreeSet<Integer>> indicies = new ArrayList<>();

            for (int j = 0; j < 4; j++) {
                indicies.add(new TreeSet<>());
            }

            st = new StringTokenizer(br.readLine());
            int Q_sum = 0;
            int sum = 0;

            for (int i = 0; i < N; i++) {
                A[i] = Integer.parseInt(st.nextToken());
                sum += A[i];
                indicies.get(A[i]).add(i);
                cnt[A[i]][i] ++;
            }
            // create segmentTree - O(N)
            for (int j = 0; j <= 3; j++) {
                SegTree segTree = new SegTree(cnt[j], N);
                segTreeList.add(segTree);
            }

            for (int m = 0; m < M; m++) {
                st = new StringTokenizer(br.readLine());
                int x = Integer.parseInt(st.nextToken()) - 1;
                int y = Integer.parseInt(st.nextToken());
                int z = Integer.parseInt(st.nextToken());

                sum += (y - A[x]);
                A[x] = y;

                if (sum % 2 == 1) {
                    Q_sum = -1;
                    break;
                }
                int target = sum / 2;

                int subSum = 0;

                // O(log N)
                for (int j = 1; j <= 3; j++) {
                    subSum += segTreeList.get(j).getSum(N, 0, z - 1) * j;
                }
                int q = 0;

                if (subSum == target) {
                    continue;
                } else if (subSum < target) {
                    // need to increase subsum
                    if (target - subSum >= 2 && segTreeList.get(1).getSum(N, 0, z - 1) > 0 && segTreeList.get(3).getSum(N, z, N - 1) > 0) {
                        while ((target - subSum != 0 || target - subSum != 1) && segTreeList.get(1).getSum(N, 0, z - 1) > 0 && segTreeList.get(3).getSum(N, z, N - 1) > 0) {
                            int idx1 = indicies.get(1).pollFirst();
                            int idx3 = indicies.get(3).pollLast();
                            indicies.get(1).add(idx3);
                            indicies.get(3).add(idx1);

                            A = swap(A, idx1, idx3);
                            segTreeList.get(1).updateVal(cnt[1], N, idx1, 0);
                            segTreeList.get(1).updateVal(cnt[1], N, idx3, 1);
                            segTreeList.get(3).updateVal(cnt[3], N, idx1, 1);
                            segTreeList.get(3).updateVal(cnt[3], N, idx3, 0);
                            q++;
                        }
                        Q_sum += q;
                    }
                    if (target - subSum > 0) {
                        if (segTreeList.get(1).getSum(N, 0, z - 1) > 0 && segTreeList.get(2).getSum(N, z, N - 1) > 0) {
                            int idx1 = indicies.get(1).pollFirst();
                            int idx2 = indicies.get(2).pollLast();
                            indicies.get(1).add(idx2);
                            indicies.get(2).add(idx1);

                            A = swap(A, idx1, idx2);
                            segTreeList.get(1).updateVal(cnt[1], N, idx1, 0);
                            segTreeList.get(1).updateVal(cnt[1], N, idx2, 1);
                            segTreeList.get(2).updateVal(cnt[2], N, idx1, 1);
                            segTreeList.get(2).updateVal(cnt[2], N, idx2, 0);
        
                            q++;
                        } else {
                            int idx2 = indicies.get(2).pollFirst();
                            int idx3 = indicies.get(3).pollLast();
                            indicies.get(2).add(idx3);
                            indicies.get(3).add(idx2);
                            A = swap(A, idx3, idx2);
                            // TODO: update prefixCnt with Segment Tree - O(log n)
                            q++;
                        }
                    }
                } else {
                    if (target - subSum <= 2 && segTreeList.get(3).getSum(N, 0, z - 1) > 0 && segTreeList.get(1).getSum(N, z, N - 1) > 0) {
                        while ((target - subSum != 0 || target - subSum != 1) && segTreeList.get(3).getSum(N, 0, z - 1) > 0 && segTreeList.get(1).getSum(N, z, N - 1) > 0) {
                            int idx1 = indicies.get(1).pollLast();
                            int idx3 = indicies.get(3).pollFirst();
                            indicies.get(1).add(idx3);
                            indicies.get(3).add(idx1);

                            A = swap(A, idx1, idx3);
                            segTreeList.get(1).updateVal(cnt[1], N, idx1, 0);
                            segTreeList.get(1).updateVal(cnt[1], N, idx3, 1);
                            segTreeList.get(3).updateVal(cnt[3], N, idx1, 1);
                            segTreeList.get(3).updateVal(cnt[3], N, idx3, 0);
                            q++;
                        }
                        Q_sum += q;
                    }
                    if (target - subSum < 0) {
                        if (segTreeList.get(2).getSum(N, 0, z - 1) > 0 && segTreeList.get(1).getSum(N, z, N - 1) > 0) {
                            int idx1 = indicies.get(1).pollLast();
                            int idx2 = indicies.get(2).pollFirst();
                            indicies.get(1).add(idx2);
                            indicies.get(2).add(idx1);

                            A = swap(A, idx1, idx2);
                            segTreeList.get(1).updateVal(cnt[1], N, idx1, 0);
                            segTreeList.get(1).updateVal(cnt[1], N, idx2, 1);
                            segTreeList.get(2).updateVal(cnt[2], N, idx1, 1);
                            segTreeList.get(2).updateVal(cnt[2], N, idx2, 0);
        
                            q++;
                        } else {
                            int idx2 = indicies.get(2).pollLast();
                            int idx3 = indicies.get(3).pollFirst();
                            indicies.get(2).add(idx3);
                            indicies.get(3).add(idx2);
                            A = swap(A, idx3, idx2);
                            // TODO: update prefixCnt with Segment Tree - O(log n)
                            q++;
                        }
                    }
                }
                Q_sum += q;
            }

            pw.println(String.format("Case #%d: %d", t, Q_sum));
            System.out.println(Q_sum);

        }
        pw.flush();
        pw.close();
        br.close();
    }

    static int[] swap(int[] A, int i, int j) {
        int temp = A[i];
        A[i] = A[j];
        A[j] = temp;
        return A;
    }
}

class SegTree {
    int stArr[]; 

    SegTree(int a[], int s) {
        int h = (int) (Math.ceil(Math.log(s) / Math.log(2)));
        int maxSize = 2 * (int) Math.pow(2, h) - 1;
        stArr = new int[maxSize];
        constructST(a, 0, s - 1, 0);
    }
    int getMidIndex(int f, int l) {
        return f + (l - f) / 2;
    }
    
    int getSumUtil(int x, int y, int i, int j, int si) {
        if (i <= x && j >= y) {
            return stArr[si];
        }
        
        if (y < i || x > j) {
            return 0;
        }
        
        int midVal = getMidIndex(x, y);
        return getSumUtil(x, midVal, i, j, 2 * si + 1) +
                getSumUtil(midVal + 1, y, i, j, 2 * si + 2);
    }
    
    void updateValUtil(int x, int y, int j, int val, int si) {
        // Base Case: If the input index lies outside the range of
        // this segment
        if (j < x || j > y) {
            return;
        }
        
        stArr[si] = stArr[si] + val;
        if (y != x) {
            int midVal = getMidIndex(x, y);
            updateValUtil(x, midVal, j, val, 2 * si + 1);
            updateValUtil(midVal + 1, y, j, val, 2 * si + 2);
        }
    }
    
    void updateVal(int a[], int s, int j, int newVal) {
        // Check for erroneous input index
        if (j < 0 || j > s - 1) {
            System.out.println("Input is Invalid");
            return;
        }
        
        int diffVal = newVal - a[j];
        // Update the value in array
        stArr[j] = newVal;
        // Update the values of nodes in segment tree
        updateValUtil(0, s - 1, j, diffVal, 0);
    }
    int getSum(int s, int x, int y) {
        // Checking for the absurd input values
        if (x < 0 || x > s - 1 || x > y) {
            System.out.println("The input is invalid");
            return -1;
        }
        return getSumUtil(0, s - 1, x, y, 0);
    }
    int constructST(int a[], int x, int y, int si) {
        // If only one element is present in the array, store it
        // in the current node of the segment tree and then return
        if (x == y) {
            stArr[si] = a[x];
            return a[x];
        }
        
        int mid = getMidIndex(x, y);
        stArr[si] = constructST(a, x, mid, si * 2 + 1) +
                constructST(a, mid + 1, y, si * 2 + 2);
        return stArr[si];
    }
}