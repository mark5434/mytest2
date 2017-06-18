package backtest;

import java.util.ArrayList;

import Strategy_Interface.MyState;
import Strategy_Interface.SimpleStrategyI;
import data.Bar;
import data.Trade;

public class UpDownRatioTest {
	static public SimpleResult test(ArrayList<Bar> barlist, SimpleStrategyI strategy) {
		MyState st = new MyState();
		int lastlow = Integer.MAX_VALUE, lasthigh = 0;
		int num =0;
		int lastr = 0;
		double res_ratio =1.0; 
		for (int i = 0; i < barlist.size() - 1; i++) {
			int r = strategy.whatToDo(barlist, st, i);
			lastlow = Math.min(lastlow, barlist.get(i).low);
			lasthigh = Math.max(lasthigh, barlist.get(i).high);
			if (r != 0 && lastr != 0) {
				double this_ratio;
				if (lastr > 0)
					this_ratio = 1.0 * lasthigh / st.atprice * Math.pow(1.0 * lastlow  / st.atprice, 2);
				else
					this_ratio = 1.0 * (2*st.atprice - lastlow) / st.atprice * Math.pow(1.0 * (2*st.atprice - lasthigh) / st.atprice, 2);
//				System.out.println("lastr"+lastr);
//				System.out.println("l"+lastlow);
//				System.out.println("h"+lasthigh);
//				System.out.println("a"+st.atprice);
//				System.out.println("ratio"+this_ratio);
				res_ratio *= Math.pow(this_ratio, Math.abs(lastr));
				lastlow = Integer.MAX_VALUE;
				lasthigh = 0;
				num++;
			}
			if (r > 0) st.atprice = barlist.get(i + 1).high;
			if (r < 0) st.atprice = barlist.get(i + 1).low;
			if (r != 0) lastr = r;
			st.marketposition += r;
		}
		if (lastr != 0) {

			double this_ratio;
			if (lastr > 0)
				this_ratio = 1.0 * lasthigh / st.atprice * Math.pow(1.0 * lastlow  / st.atprice, 2);
			else
				this_ratio = 1.0 * (2*st.atprice - lastlow) / st.atprice * Math.pow(1.0 * (2*st.atprice - lasthigh) / st.atprice, 2);
//			System.out.println("lastr"+lastr);
//			System.out.println("l"+lastlow);
//			System.out.println("h"+lasthigh);
//			System.out.println("a"+st.atprice);
//			System.out.println("ratio"+this_ratio);
			res_ratio *= Math.pow(this_ratio, Math.abs(lastr));
			num++;
		}
		SimpleResult sr = new SimpleResult();
		sr.score = res_ratio;
		sr.num = num;
		return sr;
	}
}
