
import java.util.ArrayList;

import Strategy_Interface.SimpleStrategyI;
import backtest.SimpleResult;
import backtest.RevenueTest;
import data.Bar;
import files.ReadFile;
import strategy.InsessionFollow;

public class test {

	public static void main(String[] args) throws Exception {
		String file = "d:/finance/data/format/au.1s.20160101-20160408";
		ArrayList<Bar> barlist = ReadFile.readFromFile(file);
		SimpleStrategyI isf = new InsessionFollow(480, 90030, 102900);
		SimpleResult sr = RevenueTest.test(barlist, isf, true, true);
		System.out.println(sr.score);
		System.out.println(sr.num);
		// for (int i=0;i<sr.tradelist.size();i++) {
		// System.out.println(sr.tradelist.get(i).toString());
		// }
		for (int i = 0; i < sr.daylist.size(); i++) {
			System.out.println(sr.daylist.get(i));
		}
	}
}
