package findparameter;

import java.io.PrintStream;
import java.util.ArrayList;

import Strategy_Interface.SimpleStrategyI;
import backtest.SimpleResult;
import backtest.UpDownRatioTest;
import backtest.RevenueTest;
import data.Bar;
import files.ReadFile;
import statistics.UpDown;
import strategy.CombineStrategy;
import strategy.CombineStrategy_withsessionexit;
import strategy.FiveReference_v0;
import strategy.InsessionFollow;
import strategy.MovingStopLoss;
import strategy.MyMACD_fast;
import strategy.MyMACD_own;
import strategy.MyMACD_rev;
import strategy.NewHighLow;
import strategy.MyMACD;
import strategy.MyMACD2;
import strategy.SessionExit;
import strategy.StartSessionTry;
import strategy.StopLoss;
import strategy.UpDownCount;
import strategy.XBarExit;

public class BruteForce_test {

	static public void main(String[] args) throws Exception {
		//try1day();
		tryDouble();
		//tryInteger();
	}
	
	static public void tryDouble() throws Exception {
		String file = "d:/finance/data/format.1min.20150101-20160906/sn";
		//String file = "d:/finance/data/format/rb.10m.20160101-20160423";
		//String file = "d:/finance/data/format/rb.10s.large";
		//String file = "d:/finance/data/format/rb.10s.test";
		ArrayList<Bar> barlist = ReadFile.readFromFile(file, 3);
		//barlist = (ArrayList<Bar>) barlist.subList(barlist.size()-10000, barlist.size());
		//barlist = BarTransform.transformByVolumnTime(barlist, 10000);
		PrintStream out = new PrintStream("d:/finance/result/temp.csv");
		
		SimpleStrategyI dayexit = new SessionExit();
		SimpleStrategyI exit = new MovingStopLoss(1500);
		SimpleStrategyI exitsim = new XBarExit(3600);
		//CombineStrategy_withsessionexit isf = new CombineStrategy_withsessionexit(-1);
		//CombineStrategy_withsessionexit isf = new CombineStrategy_withsessionexit(112800, 145800, 5800);
		CombineStrategy isf = new CombineStrategy();
		
		SimpleStrategyI macd = new MyMACD2();
		//isf = new InsessionFollow(0, 90100, 101400);
		SimpleStrategyI five = new FiveReference_v0();
		
//		isf.strategyList.add(macd);
		isf.strategyList.add(five);
		isf.paranumList.add(1);
		isf.strategyList.add(exit);
		//isf.strategyList.add(exitsim);
		
//		for (double d = 0.01;d > 1e-4; d/=1.01) {
		for (double d = 0.01;d < 1;d+=0.01) {
			ParameterSet ps = new ParameterSet();
			ps.params = new ArrayList<Double>();
			ps.params.add(d);
			isf.resetParameter(ps);
			isf.init();
			SimpleResult sr = RevenueTest.test(barlist, isf, false, false);
//			SimpleResult sr = UpDownRatioTest.test(barlist, isf);
			System.out.printf("param:%.5g score:%.4g num:%d\n", d, sr.score, sr.num);
			out.printf("param:%.5g score:%.4g num:%d\n", d, sr.score, sr.num);
			//break;
			
		}
		out.close();
	}

	static public void tryInteger() throws Exception {
		String file = "d:/finance/data/format.10s/p.10s.20160101-20160529";
		//String file = "d:/finance/data/format/rb.10m.20160101-20160423";
		//String file = "d:/finance/data/format/rb.10s.test";
		ArrayList<Bar> barlist = ReadFile.readFromFile(file, 3);
		//barlist = BarTransform.transformByVolumnTime(barlist, 10000);
		
		SimpleStrategyI dayexit = new SessionExit();
		SimpleStrategyI exit = new MovingStopLoss(500);
		SimpleStrategyI exitsim = new XBarExit(3600);
		SimpleStrategyI stoploss = new StopLoss(500);
		//CombineStrategy_withsessionexit isf = new CombineStrategy_withsessionexit(22900);
		//CombineStrategy_withsessionexit isf = new CombineStrategy_withsessionexit(112900,145900,22900);
		CombineStrategy isf = new CombineStrategy();
		
		SimpleStrategyI iii;
		//iii = new InsessionFollow(0, 90100, 112900);
		// macd = new MyMACD_own(0.0014);
		// iii = new NewHighLow();
		//iii = new StartSessionTry();
		iii = new UpDownCount();
		
		isf.strategyList.add(iii);
		isf.paranumList.add(1);
		//isf.strategyList.add(stoploss);
		isf.strategyList.add(exit);
		//isf.strategyList.add(exitsim);
		isf.paranumList.add(0);
		
		for (int len = 3; len < 120; len+=1) {
			ParameterSet ps = new ParameterSet();
			ps.params = new ArrayList<Double>();
			ps.params.add((double) len);
			isf.resetParameter(ps);
			isf.init();
			SimpleResult sr = RevenueTest.test(barlist, isf, false, false);
			System.out.printf("param:%d revenue:%d avg trade:%.2f num:%d\n", len, sr.score,
					((float) sr.score) / sr.num, sr.num);
		}
	}

	static public void try1day() throws Exception {
		//String file = "d:/finance/data/pp.1s.20160101-20160408";
		String file = "d:/finance/data/format/ag.10s.20160101-20160515";
		ArrayList<Bar> barlist = ReadFile.readFromFile(file, 3);

		SimpleStrategyI dayexit = new SessionExit();
		//CombineStrategy_withsessionexit isf = new CombineStrategy_withsessionexit(22900);
		CombineStrategy_withsessionexit isf = new CombineStrategy_withsessionexit(-1,-1,-1);
		
		MyMACD_own macd;
		// isf = new InsessionFollow(0, 90100, 101400);
		macd = new MyMACD_own();
		
		isf.strategyList.add(macd);
		isf.paranumList.add(1);
		
		ArrayList<Bar> barlist_tmp = new ArrayList<Bar>();
		for (int i = 0; i < barlist.size(); i++) {
			if (barlist.get(i).day == 20160302)
				barlist_tmp.add(barlist.get(i));
		}
		for (double d = 0.01;d > 1e-5;d=d/1.01) {
			ParameterSet ps = new ParameterSet();
			ps.params = new ArrayList<Double>();
			ps.params.add(d);
			isf.resetParameter(ps);
			isf.init();
			SimpleResult sr = RevenueTest.test(barlist_tmp, isf, false, false);
			System.out.printf("param:%.5g revenue:%d avg trade:%.2f num:%d\n", d, sr.score,
					((float) sr.score) / sr.num, sr.num);
		}
	}
}
