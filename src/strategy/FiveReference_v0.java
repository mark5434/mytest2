// a1: high[-1] a2: low[-1] a3: open[-1] a4: close[-1] a5: open[0]
// version1: fix a3=0 a4=0 a5=1
package strategy;

import java.util.ArrayList;

import Strategy_Interface.MyState;
import Strategy_Interface.SimpleStrategyI;
import Tools.SessionChecker;
import data.Bar;
import data.BarMore;
import findparameter.ParameterSet;

public class FiveReference_v0 implements SimpleStrategyI {
	
	public SessionChecker sc = new SessionChecker();
	public double para = 0.5;
	
	public FiveReference_v0() {
		init();
	}
	
	public FiveReference_v0(double d1) {
		setPara(d1);
		init();
	}
	
	public void setPara(double d1) {
		para = d1;
		init();
	}
	
	private void updatePoint() {
		_buy_pnt = calcBuyPoint();
		_sell_pnt = calcSellPoint();
	}
	
	private int calcBuyPoint() {
		int up = _l_close - _l_open;
		int down = _l_high - _l_low + 1;
		if (1.0 * up/down > para) return Integer.MAX_VALUE;
		return -1;
	}
	
	private int calcSellPoint() {
		int up = _l_close - _l_open;
		int down = _l_high - _l_low + 1;
		if (1.0 * up/down < -para) return 1;
		return Integer.MAX_VALUE;
	}
	
	@Override
	public void init() {
		_sell_pnt = Integer.MAX_VALUE;
		_buy_pnt = 0;
	}

	@Override
	public void resetParameter(ParameterSet ps) {
		setPara(ps.params.get(0));
	}
	
	private int _l_high;
	private int _l_low;
	private int _l_open;
	private int _l_close =-1;
	private int _n_open;
	
	private int _sell_pnt;
	private int _buy_pnt;

	private String info() {
//		return String.format("sell pnt: %d\t buy pnt: %d", _sell_pnt, _buy_pnt);
//		return String.format("%d %d %d %d %d", _l_high, _l_low, _l_open, _l_close, _n_open);
		return String.format("%d %d %d %d %d %d %d", _l_high, _l_low, _l_open, _l_close, _n_open, 
				_sell_pnt, _buy_pnt);
	}
	
	@Override
	public int whatToDo(ArrayList<Bar> barlist, MyState state, int at) {
//		System.out.println(at+":"+info());
		BarMore bar = (BarMore) barlist.get(at);
		if (sc.is_session_first(barlist, at)>=0) {
			_l_open = _n_open;
			_n_open = bar.open;
			if (_l_close == -1) return 0;
			updatePoint();
//			System.out.println(info());
			_l_high = bar.high;
			_l_low = bar.low;
			return 0;
		}
		_l_high = Math.max(_l_high, bar.high);
		_l_low = Math.min(_l_low, bar.low);
		if (sc.is_session_last(barlist, at)>=0) {
			_l_close = bar.close;
//			return 0;
			return 0;
		}
		if (sc.is_session_last(barlist, at+1)>=0) {
			_l_close = bar.close;
//			return 0-state.marketposition;
			return 0;
		}
		
		if (barlist.get(at).high<_buy_pnt) {
			_buy_pnt = 0;
			return 1-state.marketposition;
		}
		if (barlist.get(at).low>_sell_pnt) {
			_sell_pnt = Integer.MAX_VALUE;
			return -1-state.marketposition;
		}
		return 0;
	}
}
