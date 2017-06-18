
import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;

public class tmp_file {

	public static void main(String[] args) throws Exception {
		String in = "D:/finance/data/format/rb.10s.large";
		String out = "D:/finance/data/format/rb.10s.test";
		Scanner sc = new Scanner(new File(in));
		PrintWriter pw = new PrintWriter(out);
		int n = 0;
		while (sc.hasNext()) {
			n++;
			if (n>419532)
				break;
			String line = sc.nextLine();
			pw.println(line);
		}
		sc.close();
		pw.close();
		System.out.printf("read finish --- line number: %d \n", n);
		return;
	}
}
