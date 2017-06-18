package strategy;

import java.util.ArrayList;

import Strategy_Interface.MyState;
import Strategy_Interface.SimpleStrategyI;
import data.Bar;
import findparameter.ParameterSet;

public class CombineStrategy implements SimpleStrategyI {

	public ArrayList<Integer> paranumList;
	public ArrayList<SimpleStrategyI> strategyList;

	public CombineStrategy() {
		paranumList = new ArrayList<Integer>();
		strategyList = new ArrayList<SimpleStrategyI>();
	}

	@Override
	public int whatToDo(ArrayList<Bar> barlist, MyState state, int at) {
		if (at==barlist.size()-1) {
			return 0-state.marketposition;
		}
		int sum = 0;
		for (int i = 0; i < strategyList.size(); i++) {
			sum += strategyList.get(i).whatToDo(barlist, state, at);
		}
		if (sum > 2)
			sum = 2;
		if (sum < -2)
			sum = -2;
		return sum;
	}

	@Override
	public void resetParameter(ParameterSet ps) {
		int pos = 0;
		for (int i = 0; i < paranumList.size(); i++) {
			if (paranumList.get(i)==0) continue;
			ParameterSet subps = new ParameterSet();
			subps.params = new ArrayList<Double>();
			for (int j=0;j<paranumList.get(i);j++) {
				subps.params.add(ps.params.get(pos+j));
			}
			strategyList.get(i).resetParameter(subps);
			pos += paranumList.get(i);
		}
	}

	@Override
	public void init() {
		for (SimpleStrategyI ssi : strategyList) {
			ssi.init();
		}
	}

}
