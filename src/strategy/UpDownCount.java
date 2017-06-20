package strategy;

import java.util.ArrayList;
import java.util.LinkedList;

import Strategy_Interface.MyState;
import Strategy_Interface.SimpleStrategyI;
import data.Bar;
import findparameter.ParameterSet;

public class UpDownCount implements SimpleStrategyI {

	// strategy parameter
	//public int num;
	public double per;
	
	private int range;
	// strategy state
	private int upnum;
	private int downnum;
	private LinkedList<Integer> udlist;
	
	public UpDownCount(double per) {
		init();
		//this.num = num;
		this.per = per;
		this.range = 10;
	}

	public UpDownCount() {
		init();
		this.per = 0.05;
		this.range = 10;
	}

	public UpDownCount(ParameterSet ps) {
		init();
		resetParameter(ps);
	}

	@Override
	public void resetParameter(ParameterSet ps) {
		if (ps.params.size() != 1) {
			System.out.println("error");
			return;
		}
		this.range = (int) Math.round(ps.params.get(0));
		//this.range=this.num;
		//this.timest = (int) Math.round(ps.params.get(1));
		//this.timeed = (int) Math.round(ps.params.get(2));
	}

	@Override
	public void init() {
		upnum = 0;
		downnum = 0;
		udlist = new LinkedList<Integer>();
	}

	@Override
	public int whatToDo(ArrayList<Bar> barlist, MyState state, int at) {
		if (at==0) return 0;
		Bar bar = barlist.get(at);
		Bar barl = barlist.get(at-1);
		int tag = 0;
		if (bar.close>barl.close) {
			tag = 1;
		}
		if (bar.close<barl.close) {
			tag = -1;
		}
		udlist.addLast(tag);
		if (tag==1) upnum++;
		else downnum++;
		if (udlist.size()>range) {
			int tagr = udlist.removeFirst();
			if (tagr==1) upnum--;
			else downnum--;
		}
		if (udlist.size()!=this.range) return 0;
		if (1.0*downnum/range < per) {
			return 1-state.marketposition;
		}
		if (1.0*upnum/range < per) {
			return -1 -state.marketposition;
		}
		
		return 0;
	}
}
