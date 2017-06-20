
import java.util.ArrayList;

import Strategy_Interface.SimpleStrategyI;
import backtest.RevenueTest;
import backtest.SimpleResult;
import data.Bar;
import files.ReadFile;
import findparameter.ParameterSet;
import strategy.CombineStrategy;
import strategy.FiveReference_v0;
import strategy.MovingStopLoss;
import strategy.MyMACD;
import strategy.MyMACD2;
import strategy.NewHighLow;
import strategy.StopLoss;

public class debug_strategy {

	public static void main(String[] args) throws Exception {
		String file = "D:/work/java/finance/test_data/test.txt";
		//String file = "d:/finance/data/format/rb.10s.large";
		ArrayList<Bar> barlist = ReadFile.readFromFile(file);
		//barlist = BarTransform.transformByVolumnTime(barlist, 10000);
		// SimpleStrategyI isf = new InsessionFollow(480, 90030, 102900);
		SimpleStrategyI isf1 = new MyMACD(80);
		SimpleStrategyI isf2 = new MovingStopLoss(450);
		SimpleStrategyI iii = new NewHighLow(100);
		SimpleStrategyI stoploss = new StopLoss(500);
		//SimpleStrategyI dayexit = new SessionExit();
		//SimpleStrategyI macdf = new MyMACD_fast(999);
		SimpleStrategyI macd = new MyMACD2(0.002);
		SimpleStrategyI five = new FiveReference_v0();
		//CombineStrategy_withsessionexit isf = new CombineStrategy_withsessionexit(112900,145900,22900);
		CombineStrategy isf = new CombineStrategy();
		//isf.strategyList.add(macd);
		//isf.strategyList.add(isf1);
		//isf.strategyList.add(isf2);
		//isf.strategyList.add(macd);
		isf.strategyList.add(five);
		//isf.strategyList.add(stoploss);
		//isf.strategyList.add(dayexit);
		
		// parameter setting
		isf.paranumList.add(1);
		isf.paranumList.add(0);
		ParameterSet ps = new ParameterSet();
		ps.params = new ArrayList<Double>();
		ps.params.add(0.002);
		ps.params_st = new ArrayList<Double>();
		ps.params_ed = new ArrayList<Double>();
		
//		MovingParaTest_bymonth.test(barlist, isf, ps);
		
		
//		SimpleResult sr = UpDownRatioTest.test(barlist, isf);
		SimpleResult sr = RevenueTest.test(barlist, isf, true, true);
		
//		SingleValueTest svt = new SingleValueTest();
//		double r1 = svt.calcScore(barlist, isf, 0, 2000);
//		System.out.println(r1);
//		MyState jjj= svt.getLastState();
//		double r2 = svt.calcScore(barlist, jjj, isf, 2000, 4000);
//		System.out.println(r1+r2);
		
		
		for (int i = 0; i < sr.tradelist.size(); i++) {
			System.out.println(sr.tradelist.get(i).toString());
		}
//		for (int i = 0; i < sr.daylist.size(); i++) {
//			System.out.println(sr.daylist.get(i));
//		}
	}
}
