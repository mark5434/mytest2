package strategy;

import java.util.ArrayList;

import Strategy_Interface.MyState;
import Strategy_Interface.SimpleStrategyI;
import data.Bar;
import findparameter.ParameterSet;

public class NewHighLow implements SimpleStrategyI {

	public int lookback;
	private int max;
	private int min;
	private int barchecked;
	private int done;

	public NewHighLow() {
		this.lookback = 100;
		init();
	}

	public NewHighLow(int amount) {
		this.lookback = amount;
		init();
	}

	@Override
	public int whatToDo(ArrayList<Bar> barlist, MyState state, int at) {
		Bar bar = barlist.get(at);
		max = Math.max(max, bar.high);
		min = Math.min(min, bar.low);
		if (barchecked++ < lookback) return 0;
		if (bar.close >= max && done == 0) {
			done = 1;
			return -1 - state.marketposition;
		}
		if (bar.close <= min && done == 0) {
			done = 1;
			return 1 - state.marketposition;
		}
		return 0;
	}

	@Override
	public void resetParameter(ParameterSet ps) {
		this.lookback = (int) Math.round(ps.params.get(0));
	}

	@Override
	public void init() {
		max = 0;
		min = Integer.MAX_VALUE;
		barchecked = 0;
		done = 0;
	}

}
