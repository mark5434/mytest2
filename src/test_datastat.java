
import java.util.ArrayList;

import Strategy_Interface.SimpleStrategyI;
import backtest.SimpleResult;
import backtest.RevenueTest;
import data.Bar;
import files.ReadFile;
import strategy.InsessionFollow;

public class test_datastat {

	public static void main(String[] args) throws Exception {
		String file = "d:/finance/data/format/pp.1s.20160101-20160408";
		ArrayList<Bar> barlist = ReadFile.readFromFile(file);
		int lastday = -1;
		int high = 0;
		int low = Integer.MAX_VALUE;
		int st = 0;
		for (int i = 0; i < barlist.size(); i++) {
			int nday = barlist.get(i).day;
			int ntime = barlist.get(i).time;
			int s = 0;
			if (ntime > 210000)
				s = 3;
			else if (ntime < 40000) {
				s = 3;
				nday--;
			} else if (ntime > 113000)
				s = 2;
			else if (ntime > 90000)
				s = 1;
			int xday = nday * 100 + s;
			high = Integer.max(high, barlist.get(i).high);
			low = Integer.min(low, barlist.get(i).low);
			if (lastday == -1)
				st = barlist.get(i).high;
			if (lastday != -1 && lastday != xday) {
				if (s == 2)
					System.out.println(lastday + "\t" + (high - st) + "\t" + (st - low));
				high = barlist.get(i).high;
				low = barlist.get(i).low;
				st = barlist.get(i).high;
			}
			lastday = xday;
		}
	}
}
