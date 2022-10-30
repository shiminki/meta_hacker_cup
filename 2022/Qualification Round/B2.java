import java.util.*;
import java.io.*;

public class B2 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("B2_input.txt"));
        PrintWriter pw = new PrintWriter(new FileWriter("B2_output.txt"));
        int T = Integer.parseInt(br.readLine());

        for (int t = 1; t <= T; t++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int R = Integer.parseInt(st.nextToken());
            int C = Integer.parseInt(st.nextToken());
            char[][] field = new char[R][C];

            for (int i = 0; i < R; i++) {
                field[i] = br.readLine().toCharArray();
                for (int j = 0; j < C; j++) {
                    if (field[i][j] == '.') {
                        field[i][j] = 'T';
                    }
                }
            }
            boolean valid = true;
            for (int i = 0; i < R; i++) {
                for (int j = 0; j < C; j++) {
                    if (field[i][j] == '^') {
                        int friendCnt = getFriendCnt(field, i, j);
                        if (friendCnt < 2) {
                            valid = false;
                        }
                    } else if (field[i][j] == 'T') {
                        int friendCnt = getFriendCnt(field, i, j);
                        if (friendCnt < 2) {
                            field[i][j] = '.';
                            field = dfs(field, i, j);
                        }
                    }
                }
            }

            if (valid) {
                for (int i = 0; i < R; i++) {
                    for (int j = 0; j < C; j++) {
                        if (field[i][j] == 'T') {
                            field[i][j] = '^';
                        }
                        if (field[i][j] == '^') {
                            if (getFriendCnt(field, i, j) < 2) {
                                valid = false;
                            }
                        }
                    }
                }
            }
            if (valid) {
                pw.println(String.format("Case #%d: Possible", t));
                for (char[] row : field) {
                    for (char elem : row) {
                        pw.print(elem);
                    }
                    pw.println();
                }
            } else {
                pw.println(String.format("Case #%d: Impossible", t));
            }
        }
        pw.flush();
        pw.close();
        br.close();
    }

    static char[][] dfs(char[][] field, int i, int j) {
        int R = field.length, C = field[0].length;

        for (int neighbor : getNeighbors(i, j, R, C)) {
            int x = neighbor / C, y = neighbor % C;

            if (field[x][y] == 'T' && getFriendCnt(field, x, y) < 2) {
                field[x][y] = '.';
                field = dfs(field, x, y);
            }
        }
        return field;
    }

    static int getFriendCnt(char[][] field, int i, int j) {
        int friendCnt = 0;
        int R = field.length, C = field[0].length;

        for (int neighbor : getNeighbors(i, j, R, C)) {
            int x = neighbor / C, y = neighbor % C;
            if (field[x][y] == '^' || field[x][y] == 'T') {
                friendCnt++;
            }
        }
        return friendCnt;
    }

    static List<Integer> getNeighbors(int x, int y, int R, int C) {
        List<Integer> neighbors = new ArrayList<Integer>();

        if (x > 0) {
            neighbors.add((x - 1) * C + y);
        }
        if (x < R - 1) {
            neighbors.add((x + 1) * C + y);
        }
        if (y > 0) {
            neighbors.add(x * C + y - 1);
        }
        if (y < C - 1) {
            neighbors.add(x * C + y + 1);
        }
        return neighbors;
    }
}