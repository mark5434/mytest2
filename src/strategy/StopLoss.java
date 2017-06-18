package strategy;

import java.util.ArrayList;

import Strategy_Interface.MyState;
import Strategy_Interface.SimpleStrategyI;
import data.Bar;
import data.BarMore;
import findparameter.ParameterSet;

public class StopLoss implements SimpleStrategyI {

	public int amount;

	public StopLoss() {
		this.amount = 500;
		init();
	}

	public StopLoss(int amount) {
		this.amount = amount;
		init();
	}

	@Override
	public int whatToDo(ArrayList<Bar> barlist, MyState state, int at) {
		BarMore bar = (BarMore) barlist.get(at);
		if (state.marketposition == 1) {
			if (bar.close <= state.atprice - amount) {
				return -1;
			}
		}
		if (state.marketposition == -1) {
			if (bar.close >= state.atprice + amount) {
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
	}

}
