package strategy;

import java.util.ArrayList;

import Strategy_Interface.MyState;
import Strategy_Interface.SimpleStrategyI;
import data.Bar;
import data.BarMore;
import findparameter.ParameterSet;

public class StartSessionTry implements SimpleStrategyI {

	public int amount;
	public int range;
	private int max;
	private int min;
	private int bar_checked;
	private int done;
	private int openmax;
	private int openmin;

	public StartSessionTry() {
		range=30;
		this.amount = 200;
		init();
	}

	public StartSessionTry(int amount) {
		range=30;
		this.amount = amount;
		init();
	}

	@Override
	public int whatToDo(ArrayList<Bar> barlist, MyState state, int at) {
		BarMore bar = (BarMore) barlist.get(at);
		max = Math.max(max, bar.high);
		min = Math.min(min, bar.low);
		if (openmax==-1) openmax = bar.high;
		if (openmin==-1) openmin = bar.low;
		if (bar_checked++ > range) return 0;
		if (bar.close>=openmin+amount && done == 0) {
			done = 1;
			return -1 - state.marketposition;
		}
		if (bar.close <= openmax-amount && done == 0) {
			done = 1;
			return 1 - state.marketposition;
		}
		return 0;
	}

	@Override
	public void resetParameter(ParameterSet ps) {
		this.amount = (int) Math.round(ps.params.get(0));
	}

	@Override
	public void init() {
		max = 0;
		min = Integer.MAX_VALUE;
		bar_checked = 0;
		done = 0;
		openmax = -1;
		openmin = -1;
	}

}
