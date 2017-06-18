package strategy;

import java.util.ArrayList;
import java.util.LinkedList;

import Strategy_Interface.MyState;
import Strategy_Interface.SimpleStrategyI;
import Tools.StatisticsTool;
import data.Bar;
import findparameter.ParameterSet;

public class MyMACD_fast implements SimpleStrategyI {
	public int mymacdlen;
	public int fast_avg_len, slow_avg_len;
	private LinkedList<Double> diffinfolist;
	public double buf_fast_avg;
	public double buf_slow_avg;
	public double buf_diff_avg;

	public MyMACD_fast() {
		setPara(9);
		diffinfolist = new LinkedList<Double>();
	}

	public MyMACD_fast(int len) {
		setPara(len);
		diffinfolist = new LinkedList<Double>();
	}

	public void setPara(double len) {
		this.mymacdlen = (int) len;
		fast_avg_len = (int) (len * 1.3334);
		slow_avg_len = (int) (len * 2.8889);
//		System.out.println(2.0/(1+mymacdlen));
//		System.out.println(2.0/(1+fast_avg_len));
//		System.out.println(2.0/(1+slow_avg_len));
	}

	@Override
	public void resetParameter(ParameterSet ps) {
		setPara(ps.params.get(0));
	}

	@Override
	public int whatToDo(ArrayList<Bar> barlist, MyState state, int at) {
		// System.out.println(fl+" "+sl);

		double favg = StatisticsTool.decayedAverage(buf_fast_avg, barlist, at, fast_avg_len);
		double savg = StatisticsTool.decayedAverage(buf_slow_avg, barlist, at, slow_avg_len);
		buf_fast_avg = favg;
		buf_slow_avg = savg;
		double ndiff = favg - savg;
		double diff_decay_factor = 2.0 / (mymacdlen + 1);
		buf_diff_avg = buf_diff_avg * (1-diff_decay_factor) + diff_decay_factor * ndiff;
		if (at < slow_avg_len)
			return 0;
		if (at < slow_avg_len + mymacdlen)
			return 0;
		double ndiffinfo = ndiff - buf_diff_avg;
		double lastdiffinfo = 0;
		if (diffinfolist.size() != 0)
			lastdiffinfo = diffinfolist.peekFirst();
		if (diffinfolist.size() == mymacdlen)
			diffinfolist.pollFirst();
		diffinfolist.addLast(ndiffinfo);
		if (diffinfolist.size() < 2)
			return 0;
		// System.out.println(barlist.get(at).day+" "+barlist.get(at).time+"
		// "+favg+" "+savg+" "+ndiff+" "+diffavg+" "+lastdiffinfo);
		if (buf_diff_avg > 0 && lastdiffinfo < 0 && ndiffinfo > 0) {
			return 1 - state.marketposition;
		}
		if (buf_diff_avg < 0 && lastdiffinfo > 0 && ndiffinfo < 0) {
			return -1 - state.marketposition;
		}
		return 0;
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}
}
