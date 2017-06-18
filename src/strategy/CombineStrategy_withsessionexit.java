package strategy;

import java.util.ArrayList;

import Strategy_Interface.MyState;
import Strategy_Interface.SimpleStrategyI;
import data.Bar;
import findparameter.ParameterSet;

public class CombineStrategy_withsessionexit implements SimpleStrategyI {

	public ArrayList<Integer> paranumList;
	public ArrayList<SimpleStrategyI> strategyList;
	
	public int TIME_BOUND[] = {112900, 145900, 22900};
	private int lock = 0;

	public CombineStrategy_withsessionexit() {
		paranumList = new ArrayList<Integer>();
		strategyList = new ArrayList<SimpleStrategyI>();
	}
	
	public CombineStrategy_withsessionexit(int c) {
		paranumList = new ArrayList<Integer>();
		strategyList = new ArrayList<SimpleStrategyI>();
		
		this.TIME_BOUND[2] = c;
	}
	
	public CombineStrategy_withsessionexit(int a, int b, int c) {
		paranumList = new ArrayList<Integer>();
		strategyList = new ArrayList<SimpleStrategyI>();
		
		this.TIME_BOUND[0] = a;
		this.TIME_BOUND[1] = b;
		this.TIME_BOUND[2] = c;
	}

	@Override
	public int whatToDo(ArrayList<Bar> barlist, MyState state, int at) {
		if (at==0) return 0;
		if (at == barlist.size()-2) return 0-state.marketposition;
		
		int otime = barlist.get(at-1).time;
		int attime = barlist.get(at).time;
		for (int i=0;i<TIME_BOUND.length;i++) {
			if (otime < TIME_BOUND[i] && attime >= TIME_BOUND[i]) {
				lock = 1;
				return 0-state.marketposition;
			}
		}
		
		if (lock==1 && (attime-otime > 10000 || attime-otime < -10000)) {
			lock = 0;
			init();
		}
		if (lock==1) return 0;
		
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
