package strategy;

import java.util.ArrayList;
import java.util.LinkedList;

import Strategy_Interface.MyState;
import Strategy_Interface.SimpleStrategyI;
import data.Bar;
import findparameter.ParameterSet;

public class MyMACD_own implements SimpleStrategyI {
	public double fast_avg_decay;
	public double slow_avg_decay;
	public double diff_avg_decay;
	public double diff_info_decay = 0.01;
	public int freeze_len = 40;
	
	private LinkedList<Double> diffinfolist;
	private double buf_fast_avg;
	private double buf_slow_avg;
	private double buf_diff_avg;
	
	private double buf_old_diff_info;
	private double buf_new_diff_info;
	private int num_seen = 0;
	
	public MyMACD_own() {
		diffinfolist = new LinkedList<Double>();
		init();
	}
	
	public MyMACD_own(double d1) {
		diffinfolist = new LinkedList<Double>();
		setPara(d1);
		init();
	}

	public MyMACD_own(double d1, double d2, double d3) {
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
		fast_avg_decay = 1 - d1*2;
		slow_avg_decay = 1 - d1;
		//this.diff_avg_decay = 0.9999;
		diff_avg_decay = 1 - d1*3;
	}
	
	@Override
	public void init() {
		buf_diff_avg = 0;
		buf_fast_avg = -1;
		buf_slow_avg = -1;
		diffinfolist.clear();
		num_seen = 0;
		
		buf_old_diff_info = 0;
		buf_new_diff_info = 0;
	}

	@Override
	public void resetParameter(ParameterSet ps) {
		setPara(ps.params.get(0));
		//this.freeze_len = ps.params.get(0).intValue();
	}

	@Override
	public int whatToDo(ArrayList<Bar> barlist, MyState state, int at) {
		// System.out.println(fl+" "+sl);
		Bar bar = barlist.get(at);
		if (buf_fast_avg<0) buf_fast_avg = bar.close;
		if (buf_slow_avg<0) buf_slow_avg = bar.close;
		buf_fast_avg = buf_fast_avg * fast_avg_decay + (1-fast_avg_decay) * bar.close;
		buf_slow_avg = buf_slow_avg * slow_avg_decay + (1-slow_avg_decay) * bar.close;
		double ndiff = buf_fast_avg - buf_slow_avg;
		buf_diff_avg = buf_diff_avg * diff_avg_decay + (1-diff_avg_decay) * ndiff;
		//System.out.println(buf_diff_avg);
		
		double ndiffinfo = ndiff - buf_diff_avg;
		
		double lastdiffinfo = 0;
		if (diffinfolist.size() != 0)
			lastdiffinfo = diffinfolist.getFirst();
		if (diffinfolist.size() >= freeze_len)
			diffinfolist.pollFirst();
		diffinfolist.addLast(ndiffinfo);
		
		buf_old_diff_info = buf_old_diff_info * diff_info_decay + (1-diff_info_decay) * lastdiffinfo;
		buf_new_diff_info = buf_new_diff_info * diff_info_decay + (1-diff_info_decay) * ndiffinfo;
		
		if (num_seen++ < freeze_len)
			return 0;
		if (diffinfolist.size() < freeze_len)
			return 0;
		// System.out.println(barlist.get(at).day+" "+barlist.get(at).time+"
		// "+favg+" "+savg+" "+ndiff+" "+diffavg+" "+lastdiffinfo);
		//if (state.marketposition!=0) return 0;
		if (ndiffinfo > 0 && buf_new_diff_info > buf_old_diff_info && buf_old_diff_info<0) {
			return 1 - state.marketposition;
		}
		if (ndiffinfo < 0 && buf_new_diff_info < buf_old_diff_info && buf_old_diff_info>0) {
			return -1 - state.marketposition;
		}
		return 0;
	}
}
