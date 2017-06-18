package backtest;

import java.util.ArrayList;

import Strategy_Interface.MyState;
import Strategy_Interface.SimpleStrategyI;
import data.Bar;

public class SingleValueTest implements SingleScoreTestI {
	
	private MyState laststate = null;
	private int lastr = 0;
	
	@Override
	public double calcScore(ArrayList<Bar> barlist, SimpleStrategyI strategy) {
		int st = 0;
		int ed = barlist.size();
		return calcScore(barlist, strategy, st, ed);
	}
	
	@Override
	public double calcScore(ArrayList<Bar> barlist, SimpleStrategyI strategy, int st, int ed) {
		return calcScore(barlist, new MyState(), strategy, st, ed);
	}

	@Override
	public double calcScore(ArrayList<Bar> barlist, MyState startstate, int startr, SimpleStrategyI strategy, int st, int ed) {
		MyState mst = new MyState();
		mst.atprice = startstate.atprice;
		mst.marketposition = startstate.marketposition;
		SimpleResult res = new SimpleResult();
		int lastlow = 0, lasthigh = 0;
		
		if (startr != 0) {
			res.num++;
			mst.marketposition += startr;
			if (startr > 0) {
				mst.atprice = barlist.get(0).high;
			}
			if (startr < 0) {
				mst.atprice = barlist.get(0).low;
			}
			res.score -= startr * mst.atprice;
		}
		for (int i = st; i < ed - 1; i++) {
			int r = strategy.whatToDo(barlist, mst, i);
			if (r != 0) 
				res.num++;
			mst.marketposition += r;
			if (r > 0) {
				mst.atprice = barlist.get(i + 1).high;
//				System.out.println(i+"\t"+r+"\t"+mst.atprice);
			}
			if (r < 0) {
				mst.atprice = barlist.get(i + 1).low;
//				System.out.println(i+"\t"+r+"\t"+mst.atprice);
			}
			mst.when = i;
			res.score -= r * mst.atprice;
			
			lasthigh = barlist.get(i).high;
			lastlow = barlist.get(i).low;
		}
		laststate = mst;
//		System.out.println("n"+res.num);
		res.score -= startstate.marketposition * startstate.atprice;
		int mdiff = mst.marketposition - 0;
		if (mdiff < 0) {
			res.score += mdiff * lasthigh;
			laststate.atprice = lasthigh;
		}
		if (mdiff > 0) {
			res.score += mdiff * lastlow;
			laststate.atprice = lastlow;
		}
		if (ed!=0) {
			lastr = strategy.whatToDo(barlist, mst, ed-1);
		}
		return res.score;
	}
	
	@Override
	public MyState getLastState() {
		return laststate;
	}
	
	@Override
	public int getLastR() {
		return lastr;
	}

	@Override
	public double calcScore(ArrayList<Bar> barlist, MyState startstate, SimpleStrategyI strategy, int st, int ed) {
		return calcScore(barlist, startstate, 0, strategy, st, ed);
	}
}
