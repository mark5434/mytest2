package strategy;

import java.util.ArrayList;

import Strategy_Interface.MyState;
import Strategy_Interface.SimpleStrategyI;
import data.Bar;
import findparameter.ParameterSet;

public class XBarExit implements SimpleStrategyI {

	public int counter;
	public int exitnum;
	
	public XBarExit() {

	}
	
	public XBarExit(int exitnum) {
		this.exitnum = exitnum;
	}

	@Override
	public void resetParameter(ParameterSet ps) {
		this.exitnum = ps.params.get(0).intValue();
	}

	@Override
	public int whatToDo(ArrayList<Bar> barlist, MyState state, int at) {
		if (state.marketposition!=0) {
			counter++;
		}
		if (state.marketposition==0) {
			counter=0;
		}
		if (counter > exitnum) {
			counter=0;
			return 0-state.marketposition;
		}
		return 0;
	}

	@Override
	public void init() {
		counter = 0;
	}
}
