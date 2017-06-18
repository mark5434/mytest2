package findparameter;

import java.util.ArrayList;

import Strategy_Interface.SimpleStrategyI;
import backtest.SingleScoreTestI;
import data.Bar;

public class FindNearPara implements FindParaI{

	private ParameterSet nowps;
	// near para的范围写死在代码中
	// 目前只支持对第一个para做
	private void setNowPara(ParameterSet ps) {
		nowps = ps;
//		for (int i=0;i<nowps.params.size();i++) {
//			nowps.params_st = new ArrayList<Double>();
//			nowps.params_st.add(nowps.params.get(i) * 0.8);
//			nowps.params_ed = new ArrayList<Double>();
//			nowps.params_ed.add(nowps.params.get(i) * 1.2);
//		}
		nowps.params_st = new ArrayList<Double>();
		nowps.params_st.add(nowps.params.get(0) * 0.9);
		nowps.params_ed = new ArrayList<Double>();
		nowps.params_ed.add(nowps.params.get(0) * 1.1);
	}
	
	private SingleScoreTestI sst;
	public FindNearPara(SingleScoreTestI t) {
		sst = t;
	}
	
	// for now only one para
	public ParameterSet findPara(ArrayList<Bar> barlist, SimpleStrategyI ssi, ParameterSet ps, int st, int ed) {
		double maxscore = 0;
		this.setNowPara(ps);
		ParameterSet temppara = new ParameterSet();
		temppara.params = new ArrayList<Double>(nowps.params);
		ParameterSet bestpara = new ParameterSet();
		bestpara.params = new ArrayList<Double>(nowps.params);
		double pst = nowps.params_st.get(0);
		double ped = nowps.params_ed.get(0);
		double step = (ped-pst)/21;
		for (double para = pst;para < ped;para+=step) {
			temppara.params.set(0, para);
			ssi.resetParameter(temppara);
			double this_score = sst.calcScore(barlist, ssi, st, ed);
			if (this_score > maxscore) {
				maxscore = this_score;
				bestpara.params.set(0, para);
			}
		}
		return bestpara;
	}
	
	public ParameterSet findPara(ArrayList<Bar> barlist, SimpleStrategyI ssi, ParameterSet ps) {
		int st = 0;
		int ed = barlist.size();
		return findPara(barlist, ssi, ps, st, ed);
	}
}
