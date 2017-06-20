package strategy;

import java.util.ArrayList;

import Strategy_Interface.MyState;
import Strategy_Interface.SimpleStrategyI;
import data.Bar;
import findparameter.ParameterSet;

public class MovingStopLoss implements SimpleStrategyI {

	public int amount;
	private int max;
	private int min;

	public MovingStopLoss() {
		this.amount = 500;
		init();
	}

	public MovingStopLoss(int amount) {
		this.amount = amount;
		init();
	}

	@Override
	public int whatToDo(ArrayList<Bar> barlist, MyState state, int at) {
		Bar bar = barlist.get(at);
		if (state.marketposition == 1) {
			max = Math.max(max, bar.high);
			min = Integer.MAX_VALUE;
			if (bar.close <= max - amount) {
				max = 0;
				return -1;
			}
		}
		if (state.marketposition == -1) {
			min = Math.min(min, bar.low);
			max = 0;
			if (bar.close >= min + amount) {
				min = Integer.MAX_VALUE;
				return 1;
			}
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
	}

}
