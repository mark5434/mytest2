package strategy;

import java.util.ArrayList;

import Strategy_Interface.MyState;
import Strategy_Interface.SimpleStrategyI;
import data.Bar;
import findparameter.ParameterSet;

public class InsessionFollow_dynamic implements SimpleStrategyI {

	// strategy state
	public int seenlow;
	public int seenhigh;
	public int dynamicamount;

	// strategy parameter
	public int amount;
	public int timest;
	public int timeed;

	public InsessionFollow_dynamic(int amount, int timest, int timeed) {
		init();
		this.amount = amount;
		this.timest = timest;
		this.timeed = timeed;
	}

	@Override
	public void init() {
		seenlow = Integer.MAX_VALUE;
		seenhigh = -1;
		dynamicamount = amount;
	}

	private boolean is_insession(int time) {
		if (timest < timeed) {
			if (time >= timest && time < timeed)
				return true;
		} else {
			if (time >= timest || time < timeed)
				return true;
		}
		return false;
	}

	private boolean is_first(ArrayList<Bar> barList, int at) {
		if (at == 0)
			return true;
		if (is_insession(barList.get(at).time) && !is_insession(barList.get(at - 1).time)) {
			return true;
		}
		return false;
	}

	private boolean is_last(ArrayList<Bar> barList, int at) {
		if (at == barList.size() - 1)
			return true;
		if (is_insession(barList.get(at).time) && !is_insession(barList.get(at + 1).time)) {
			return true;
		}
		return false;
	}

	@Override
	public int whatToDo(ArrayList<Bar> barlist, MyState state, int at) {
		if (is_first(barlist, at)) {
			init();
		}
		if (is_last(barlist, at)) {
			// out of session: get back
			init();
			return 0 - state.marketposition;
		}
		int nhigh = barlist.get(at).high;
		int nlow = barlist.get(at).low;
		int ntime = barlist.get(at).time;

		if (is_insession(ntime) && state.marketposition == 0) {
			// update high low
			seenhigh = Integer.max(seenhigh, nhigh);
			seenlow = Integer.min(seenlow, nlow);
			// check and do
			if (seenhigh - nlow > dynamicamount) {
				// start down, reset seenlow
				seenlow = nlow;
				return -1;
			}
			if (nhigh - seenlow > dynamicamount) {
				// start up, reset seenhigh
				seenhigh = nhigh;
				return 1;
			}
			return 0;
		}
		if (is_insession(ntime) && state.marketposition == 1) {
			// update high low
			if (nhigh > seenhigh) {
				//dynamicamount += (nhigh - seenhigh) / 2;
				seenhigh = nhigh;
			}
			if (seenhigh - nlow > dynamicamount) {
				seenlow = nlow;
				dynamicamount = amount;
				return -2;
			}
			return 0;
		}
		if (is_insession(ntime) && state.marketposition == -1) {
			// update high low
			if (nlow < seenlow) {
				//dynamicamount += (seenlow - nlow) / 2;
				seenlow = nlow;
			}
			if (nhigh - seenlow > dynamicamount) {
				seenhigh = nhigh;
				dynamicamount = amount;
				return 2;
			}
			return 0;
		}
		return 0;
	}

	@Override
	public void resetParameter(ParameterSet ps) {
		// TODO Auto-generated method stub

	}
}
