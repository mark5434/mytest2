package files;

import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;

import data.Bar;

public class FormatFile {
	static public void main(String[] args) throws Exception {
		String x = "l";
		String in = "d:/finance/data/1min/" + x + ".txt";
		String out = "d:/finance/data/format.1min.20150101-20160906/" + x;
		formatFile(in, out, 5);
	}

	static public void formatFile(String in, String out, int factor) throws Exception {
		Scanner sc = new Scanner(new File(in));
		PrintWriter pw = new PrintWriter(out);
		sc.nextLine();
		int n = 0;
		while (sc.hasNext()) {
			n++;
			String line = sc.nextLine();
			Bar bar = parseLine(line, factor);
			pw.println(bar.toString());
		}
		sc.close();
		pw.close();
		System.out.printf("read finish --- line number: %d \n", n);
		return;
	}

	static public Bar parseLine(String line, int factor) {
		Bar res = new Bar();
		// day, time, open, high, low, close, volumn
		// day: 2016/1/4
		// time: 09:00:04
		String[] parts = line.split(",");
		String[] dp = parts[0].split("/");
		int y = Integer.parseInt(dp[0]);
		int m = Integer.parseInt(dp[1]);
		int d = Integer.parseInt(dp[2]);
		res.day = y * 10000 + m * 100 + d;
		String[] tp = parts[1].split(":");
		int h = Integer.parseInt(tp[0]);
		int M = Integer.parseInt(tp[1]);
		int s = Integer.parseInt(tp[2]);
		res.time = h * 10000 + M * 100 + s;
		res.high = Math.round(factor * Float.parseFloat(parts[3]));
		res.low = Math.round(factor * Float.parseFloat(parts[4]));
		res.open = Math.round(factor * Float.parseFloat(parts[2]));
		res.close = Math.round(factor * Float.parseFloat(parts[5]));
		res.volumn = Integer.parseInt(parts[6]);
		return res;
	}
}
