import java.util.*;
import java.io.*;

public class C2 {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader("C2_input.txt"));
		PrintWriter pw = new PrintWriter(new FileWriter("C2_output.txt"));
		int T = Integer.parseInt(br.readLine());

		for (int t = 1; t <= T; t++) {
			int N = Integer.parseInt(br.readLine());
			String C1 = br.readLine();

			Set<String> C = getPowerSet();

			if (C1.length() < 10) {
				int l = C1.length();
				List<String> remove = new ArrayList<>();
				for (String c : C) {
					if (C1.equals(c.substring(0, l))) {
						remove.add(c);
					}
				}
				for (String c : remove) {
					C.remove(c);
				}
			} else {
				C.remove(C1.substring(0, 10));
			}

			List<String> answer = new ArrayList<>();

			for (String c : C) {
				if (answer.size() == N - 1) {
					break;
				}
				answer.add(c);
			}

			pw.println(String.format("Case #%d:", t));

			for (String c : answer) {
				pw.println(c);
			}
		}
		pw.flush();
		pw.close();
		br.close();
	}

	static Set<String> getPowerSet() {
		Set<String> set = new HashSet<>();

		for (int i = 0; i < 1024; i++) {
			set.add(getString(i));
		}
		return set;
	}

	static String getString(int x) {
		char[] str = new char[10];

		for (int i = 0; i < 10; i++) {
			int r = x % 2;
			x /= 2;

			if (r == 1) {
				str[i] = '-';
			} else {
				str[i] = '.';
			}
		}
		return new String(str);
	}
}