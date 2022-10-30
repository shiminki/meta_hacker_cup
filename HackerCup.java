import java.util.*;
import java.io.*;

public class HackerCup {
	public static void main(String[] args) throws IOException {
		// TODO: Change the input file name for submission
		String filename = "input.txt";
		BufferedReader br = new BufferedReader(new FileReader(filename));
		PrintWriter pw = new PrintWriter(new FileWriter("output.txt"));
		int T = Integer.parseInt(br.readLine());

		for (int t = 1; t <= T; t++) {
			StringTokenizer st = new StringTokenizer(br.readLine());

			pw.println(String.format("Case #%d:", t));
			
		}
		pw.flush();
		pw.close();
		br.close();
	}
}