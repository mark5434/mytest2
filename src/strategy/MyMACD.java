package strategy;

import java.util.ArrayList;
import java.util.LinkedList;

import Strategy_Interface.MyState;
import Strategy_Interface.SimpleStrategyI;
import data.Bar;
import findparameter.ParameterSet;

public class MyMACD implements SimpleStrategyI {
	public double fast_avg_decay;
	public double slow_avg_decay;
	public double diff_avg_decay;
	public int freeze_len = 100;
	
	private LinkedList<Double> diffinfolist;
	private double buf_fast_avg;
	private double buf_slow_avg;
	private double buf_diff_avg;
	private int num_seen = 0;
	
	public MyMACD() {
		diffinfolist = new LinkedList<Double>();
		init();
	}
	
	public MyMACD(double d1) {
		diffinfolist = new LinkedList<Double>();
		setPara(d1);
		init();
	}

	public MyMACD(double d1, double d2, double d3) {
		diffinfolist = new LinkedList<Double>();
		setPara(d1, d2, d3);
		init();
	}

	public void setPara(double d1, double d2, double d3) {
		this.fast_avg_decay = 1-d1;
		this.slow_avg_decay = 1-d2;
		this.diff_avg_decay = 1-d3;
	}
	
	public void setPara(double d1) {
		fast_avg_decay = 1 - d1;
		slow_avg_decay = 1 - d1*2;
		//this.diff_avg_decay = 0.8;
		diff_avg_decay = 1 - d1*3;
	}
	
	@Override
	public void init() {
		buf_diff_avg = 0;
		buf_fast_avg = -1;
		buf_slow_avg = -1;
		diffinfolist.clear();
		num_seen = 0;
	}

	@Override
	public void resetParameter(ParameterSet ps) {
		setPara(ps.params.get(0));
	}

	@Override
	public int whatToDo(ArrayList<Bar> barlist, MyState state, int at) {
		Bar bar = barlist.get(at);
		if (buf_fast_avg<0) buf_fast_avg = bar.close;
		if (buf_slow_avg<0) buf_slow_avg = bar.close;
		buf_fast_avg = buf_fast_avg * fast_avg_decay + (1-fast_avg_decay) * bar.close;
		buf_slow_avg = buf_slow_avg * slow_avg_decay + (1-slow_avg_decay) * bar.close;
		double ndiff = buf_fast_avg - buf_slow_avg;
		buf_diff_avg = buf_diff_avg * diff_avg_decay + (1-diff_avg_decay) * ndiff;
		double ndiffinfo = ndiff - buf_diff_avg;
		if (diffinfolist.size() >= freeze_len)
			diffinfolist.pollFirst();
		double thelast = -1;
		if (diffinfolist.size()>0) thelast = diffinfolist.getLast();
		diffinfolist.addLast(ndiffinfo);
//		if (ndiffinfo-thelast>0.1||ndiffinfo-thelast<-0.1) {
//			System.out.println(ndiffinfo+" "+thelast);
//			System.out.println(bar.day+" "+bar.time);
//		}
		if (num_seen++ < freeze_len)
			return 0;
		if (thelast<0 && ndiffinfo>0) {
			//System.out.println("a");
			return -1-state.marketposition;
		}
		if (thelast>0 && ndiffinfo<0) {
			//System.out.println("b");
			return 1-state.marketposition;
		}

		return 0;
	}
}
