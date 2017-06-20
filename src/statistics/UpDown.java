package statistics;

import java.util.ArrayList;

import data.Bar;
import files.ReadFile;

public class UpDown {
	static public void main(String args[]) throws Exception {
		String file = "d:/finance/data/format/jm.1s.20160101-20160408";
		ArrayList<Bar> barlist = ReadFile.readFromFile(file);
		calc(barlist);
	}
	static public void sessionsartupdown(ArrayList<Bar> barlist) {
				
	}
	static public void calc(ArrayList<Bar> barlist) {
		int lastday = -1;
		int amount = 0;
		Bar lastbar = null;
		for (int i = 0; i < barlist.size(); i++) {
			Bar bar = barlist.get(i);
			int nowday = bar.day;
			if (bar.time < 80000)
				nowday = lastday;
			if (lastday != -1 && lastday != nowday) {
				System.out.println(lastday + ":" + amount + "\t" + 1.0 * amount / bar.open);
				amount = 0;
				lastday = nowday;
				lastbar = null;
			}
			if (lastbar != null)
				amount += Math.abs(bar.open - lastbar.close);
			amount += bar.high - bar.low;
			lastday = nowday;
		}
	}
}
