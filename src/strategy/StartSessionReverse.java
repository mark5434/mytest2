package strategy;

import java.util.ArrayList;

import Strategy_Interface.MyState;
import Strategy_Interface.SimpleStrategyI;
import data.Bar;
import findparameter.ParameterSet;

public class StartSessionReverse implements SimpleStrategyI {

	public int TIME_BOUND[] = {112900, 145900, 22900};
	
	public StartSessionReverse() {

	}

	@Override
	public void resetParameter(ParameterSet ps) {
	}

	@Override
	public int whatToDo(ArrayList<Bar> barlist, MyState state, int at) {
		if (at==0) return 0;
		if (state.marketposition==0) return 0;
		if (at == barlist.size()-2) return 0-state.marketposition;
		int otime = barlist.get(at-1).time;
		int attime = barlist.get(at).time;
		for (int i=0;i<TIME_BOUND.length;i++) {
			if (otime < TIME_BOUND[i] && attime >= TIME_BOUND[i]) {
				return 0-state.marketposition;
			}
		}
		return 0;
	}

	@Override
	public void init() {
	}
}
