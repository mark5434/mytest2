package backtest;

import java.util.ArrayList;

import Strategy_Interface.MyState;
import Strategy_Interface.SimpleStrategyI;
import data.Bar;
import data.Trade;

public class RevenueTest {
	static public SimpleResult test(ArrayList<Bar> barlist, SimpleStrategyI strategy, boolean is_needtradelist,
			boolean is_needday) {
		MyState st = new MyState();
		SimpleResult res = new SimpleResult();
		if (is_needday)
			res.daylist = new ArrayList<String>();
		if (is_needtradelist)
			res.tradelist = new ArrayList<Trade>();
		int lastday = -1;
		int lastr = 0;
		int lastlow = 0, lasthigh = 0;
		for (int i = 0; i < barlist.size() - 1; i++) {
			if (lastday != -1 && lastday != barlist.get(i).day && is_needday) {
				int deltar = (int) (res.score - lastr);
				int f = 0;
				if (st.marketposition < 0)
					f += st.marketposition * lasthigh;
				if (st.marketposition > 0)
					f += st.marketposition * lastlow;
				res.daylist.add(Integer.toString(lastday) + " " + (deltar + f));
				lastr = (int) (res.score + f);
			}
			int r = strategy.whatToDo(barlist, st, i);
			if (r != 0)
				res.num++;
			st.marketposition += r;
			if (r > 0)
				st.atprice = barlist.get(i + 1).high;
			else
				st.atprice = barlist.get(i + 1).low;
			st.when = i;
			res.score -= r * st.atprice;
			if (r != 0 && is_needtradelist) {
				Trade t = new Trade();
				t.day = barlist.get(i).day;
				t.time = barlist.get(i).time;
				t.amount = r;
				t.atprice = st.atprice;
				res.tradelist.add(t);
			}
			lastday = barlist.get(i).day;
			lasthigh = barlist.get(i).high;
			lastlow = barlist.get(i).low;
		}
		if (st.marketposition < 0) {
			res.score += st.marketposition * lasthigh;
			//System.out.println("final not 0 1");
		}
		if (st.marketposition > 0) {
			res.score += st.marketposition * lastlow;
			//System.out.println("final not 0 2");
		}
		return res;
	}

	static public SimpleResult test(ArrayList<Bar> barlist, SimpleStrategyI strategy) {
		return test(barlist, strategy, false, false);
	}
}
