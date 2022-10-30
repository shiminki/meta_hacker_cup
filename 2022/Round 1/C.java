import java.util.*;
import java.io.*;

public class C {
    public static void main(String[] args) throws IOException {
        // TODO: Change the input file name for submission
        String filename = "lemonade_life_input.txt";
        // String filename = "C_input.txt";
        BufferedReader br = new BufferedReader(new FileReader(filename));
        PrintWriter pw = new PrintWriter(new FileWriter("output.txt"));
        int T = Integer.parseInt(br.readLine());

        for (int t = 1; t <= T; t++) {
            System.out.println(String.format("Case # %d", t));

            StringTokenizer st = new StringTokenizer(br.readLine());
            int N = Integer.parseInt(st.nextToken());
            int K = Integer.parseInt(st.nextToken());
            long D = Long.parseLong(st.nextToken());
            Point[] points = new Point[N];

            for (int i = 0; i < N; i++) {
                st = new StringTokenizer(br.readLine());
                long x = Long.parseLong(st.nextToken());
                long y = Long.parseLong(st.nextToken());
                points[i] = new Point(x, y);
            }
            Point start = points[0], end = points[N - 1];

            List<Stack<Point>> verticies = monotoneChain(points); // O(N log N)

            List<Map<Integer, Integer>> adj = new ArrayList<>();
            Set<Point> Vset = new HashSet<>();

            while (verticies.get(0).size() > 0) {
                Vset.add(verticies.get(0).pop());
            }
            while (verticies.get(1).size() > 0) {
                Vset.add(verticies.get(1).pop());
            }
            List<Point> V = new ArrayList<>();
            int startIdx = 0, endIdx = 0;

            for (Point p : Vset) {
                if (p.equals(start)) {
                    startIdx = V.size();
                } else if (p.equals(end)) {
                    endIdx = V.size();
                }
                V.add(p);
            }
            System.out.println(String.format("%d %d", N, V.size()));

            for (int i = 0; i < V.size(); i++) {
                adj.add(new HashMap<>());
                for (int j = 0; j < V.size(); j++) {
                    if (i == j)
                        continue;
                    long d = getDistSquared(V.get(i), V.get(j));
                    if (d > (long) D * D)
                        continue;

                    int distance = (int) Math.max(d, K);
                    adj.get(i).put(j, distance);
                }
            }
            System.out.println();

            // System.out.println(start);
            // System.out.println(end);
            // System.out.println(V);
            // System.out.println(adj);

            long ans = dijkstra(adj, startIdx, endIdx);
            if (ans == Long.MAX_VALUE) {
                ans = -1;
            }
            pw.println(String.format("Case #%d: %d", t, ans));

        }
        pw.flush();
        pw.close();
        br.close();
    }

    static long getDistSquared(Point p1, Point p2) {
        long x = p1.x - p2.x, y = p1.y - p2.y;
        return x * x + y * y;
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

    static boolean ccw(Point p1, Point p2, Point p3) {
        // check if vector p1 --> p2 and p2 --> p3 are ccw
        return crossProduct(p2.x - p1.x, p2.y - p1.y, p3.x - p2.x, p3.y - p2.y) > 0;
    }

    static long crossProduct(long a, long b, long c, long d) {
        return a * d - b * c;
    }

    static long dijkstra(List<Map<Integer, Integer>> adj, int startIdx, int endIdx) {
        int N = adj.size();
        Map<Integer, Long> dist = new HashMap<>();
        Set<Integer> visited = new HashSet<>();

        for (int i = 0; i < N; i++) {
            if (i == startIdx) {
                dist.put(i, (long) 0);
            } else {
                dist.put(i, Long.MAX_VALUE);
            }
        }

        PriorityQueue<Vertex> pq = new PriorityQueue<>();
        pq.add(new Vertex(startIdx, 0));

        for (int i = 0; i < N; i++) {
            if (pq.size() == 0) {
                return (long) -1;
            }
            Vertex vertex = pq.poll();

            while (visited.contains(vertex.idx)) {
                if (pq.size() == 0) {
                    return (long) -1;
                }
                vertex = pq.poll();
            }
            int p = vertex.idx;
            dist.put(p, vertex.d);
            visited.add(p);

            for (Map.Entry<Integer, Integer> entry : adj.get(p).entrySet()) {
                int q = entry.getKey();

                if (dist.get(q) > dist.get(p) + adj.get(p).get(q)) {
                    dist.put(q, dist.get(p) + adj.get(p).get(q));
                    pq.add(new Vertex(q, dist.get(q)));
                }
            }
        }
        // System.out.println(dist);
        return dist.get(endIdx);
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

class Vertex implements Comparable<Vertex> {
    int idx;
    long d;

    public Vertex(int idx, long d) {
        this.idx = idx;
        this.d = d;
    }

    public int compareTo(Vertex v) {
        return Long.compare(d, v.d);
    }

    public String toString() {
        return String.format("Vertex %d, dist: %d", idx, d);
    }
}