import java.util.*;
import java.io.*;
import java.math.BigInteger;

public class C_v2 {
    static final String bound = "35000000000000000000000000";
    public static void main(String[] args) throws IOException {
        long globalStartTime = System.currentTimeMillis();

        // TODO: Change the input file name for submission
        String filename = "lemonade_life_input.txt";
        // String filename = "C_input.txt";
        BufferedReader br = new BufferedReader(new FileReader(filename));
        PrintWriter pw = new PrintWriter(new FileWriter("output.txt"));
        int T = Integer.parseInt(br.readLine());

        for (int t = 1; t <= T; t++) {
            long startTime = System.currentTimeMillis();
            System.out.println(String.format("Case #%d", t));

            StringTokenizer st = new StringTokenizer(br.readLine());
            int N = Integer.parseInt(st.nextToken());
            int K = Integer.parseInt(st.nextToken());
            int D = Integer.parseInt(st.nextToken());
            Point[] points = new Point[N];

            for (int i = 0; i < N; i++) {
                st = new StringTokenizer(br.readLine());
                long x = Long.parseLong(st.nextToken());
                long y = Long.parseLong(st.nextToken());
                points[i] = new Point(x, y);
            }
            Point start = points[0], end = points[N - 1];

            List<Stack<Point>> verticies = monotoneChain(points); // O(N log N)

            List<Point> V = new ArrayList<>(verticies.get(0));

            while (verticies.get(1).size() > 0) {
                V.add(verticies.get(1).pop());
            }

            System.out.println(String.format("%d %d", N, V.size()));

            int startIdx = 0, endIdx = 0;

            for (int i = 0; i < V.size(); i++) {
                if (V.get(i).equals(start)) {
                    startIdx = i;
                } else if (V.get(i).equals(end)) {
                    endIdx = i;
                }
            }

            BigInteger ans = dijkstra(V, startIdx, endIdx, D, K);

            if (ans.toString().equals(bound)) {
                ans = new BigInteger("-1");
            }
            
            pw.println(String.format("Case #%d: %s", t, ans.toString()));

            long endTime = System.currentTimeMillis();
            System.out.println(String.format("Runtime: %dms", endTime - startTime));
        }
        pw.flush();
        pw.close();
        br.close();

        long globalEndTime = System.currentTimeMillis();
        System.out.println(String.format("Total runtime: %dms", globalEndTime - globalStartTime));
    }

    static BigInteger dijkstra(List<Point> V, int startIdx, int endIdx, int D, int K) {
        // O(V^2) time and O(V) space
        // Traditional implementation takes O(E) = O(V^2) ~ 10^9 space, which will
        // lead to OutOfMemoryError
        int N = V.size();
        Map<Integer, BigInteger> dist = new HashMap<>();
        Set<Integer> notVisited = new HashSet<>();
        
        final BigInteger INF = new BigInteger(bound);

        for (int i = 0; i < N; i++) {
            if (i == startIdx) {
                dist.put(i, BigInteger.ZERO);
            } else {
                dist.put(i, INF);
            }
            notVisited.add(i);
        }

        for (int i = 0; i < N; i++) {
            int p = -1;

            for (int v : notVisited) {
                if (p == -1 || (dist.get(v).compareTo(dist.get(p)) < 0)) {
                    p = v;
                }
            }

            notVisited.remove(p);

            for (int q : notVisited) {
                long d = getDist(V.get(p), V.get(q), D, K);
                if (d != -1) {
                    BigInteger newDist = dist.get(p).add(new BigInteger(Long.toString(d)));
                    if (dist.get(q).compareTo(newDist) > 0) {
                        dist.put(q, newDist);
                    }
                }
            }
        }
        // System.out.println(dist);
        return dist.get(endIdx);
    }
    static List<Stack<Point>> monotoneChain(Point[] points) {
        Arrays.sort(points); // O(N log N)
        Stack<Point> upper = new Stack<>();
        Stack<Point> lower = new Stack<>();

        for (int i = 0; i < points.length; i++) {
            while (lower.size() >= 2) {
                Point p2 = lower.pop(), p1 = lower.peek();
                // potential ordering: p1 --> p2 --> P[i]
                if (!ccw(p1, p2, points[i])) {
                    // remove p2
                    continue;
                } else {
                    // add p2 back and break out of the loop
                    lower.push(p2);
                    break;
                }
            }
            lower.add(points[i]);
        }
        for (int i = points.length - 1; i >= 0; i--) {
            while (upper.size() >= 2) {
                Point p2 = upper.pop(), p1 = upper.peek();
                // potential ordering: p1 --> p2 --> P[i]
                if (!ccw(p1, p2, points[i])) {
                    // remove p2
                    continue;
                } else {
                    // add p2 back and break out of the loop
                    upper.push(p2);
                    break;
                }
            }
            upper.add(points[i]);
        }
        lower.pop();
        upper.pop();
        List<Stack<Point>> out = new ArrayList<>();
        out.add(lower);
        out.add(upper);
        return out;
    }
    static long getDist(Point p1, Point p2, int D, int K) {
        long d = getDistSquared(p1, p2);
        if (d > (long) D * D)
            return -1;

        long distance = Math.max(d, K);
        return distance;
    }

    static long getDistSquared(Point p1, Point p2) {
        long x = p1.x - p2.x, y = p1.y - p2.y;
        return x * x + y * y;
    }

    
    static boolean ccw(Point p1, Point p2, Point p3) {
        // check if vector p1 --> p2 and p2 --> p3 are ccw
        return crossProduct(p2.x - p1.x, p2.y - p1.y, p3.x - p2.x, p3.y - p2.y) > 0;
    }

    static long crossProduct(long a, long b, long c, long d) {
        return a * d - b * c;
    }
}

class Point implements Comparable<Point> {
    long x, y;

    public Point(long x, long y) {
        this.x = x;
        this.y = y;
    }

    public int compareTo(Point p) {
        if (this.x != p.x) {
            return Long.compare(this.x, p.x);
        } else {
            return Long.compare(this.y, p.y);
        }
    }

    public boolean equals(Point p) {
        String self = String.format("%d %d", this.x, this.y);
        String other = String.format("%d %d", p.x, p.y);
        return self.equals(other);
    }

    public String toString() {
        return String.format("%d %d", this.x, this.y);
    }
}
