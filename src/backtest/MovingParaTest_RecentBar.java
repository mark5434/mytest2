package backtest;

import java.util.ArrayList;

import Strategy_Interface.MyState;
import Strategy_Interface.SimpleStrategyI;
import data.Bar;
import data.BarAll;
import data.Trade;
import findparameter.FindNearPara;
import findparameter.FindParaI;
import findparameter.ParameterSet;

public class MovingParaTest_RecentBar {
	static public int num_barbucket = 10000;
	static public double test(ArrayList<Bar> barlist, SimpleStrategyI strategy, ParameterSet ps) {
		SingleScoreTestI sst;
		sst = new SingleValueTest();
		FindParaI fpi;
		fpi = new FindNearPara(sst);
		
		ParameterSet orips = new ParameterSet();
		orips.params = new ArrayList<Double>(ps.params);
		
		ParameterSet nowps = ps;
		int lastst = 0;
		int lastmon = -1;
		int mon =0;
		double final_testing_score = 0.0;
		double final_training_score = 0.0;
		for (int i=0;i<barlist.size();i++) {
			BarAll bar = (BarAll) barlist.get(i);
			int bucket_id = i /10000;
			
			// 1. update para every bar
			// 2. update para every num_barbucket
			// 3. update para every trade
			// 4. update para when recent trades are going down
			
			if (lastmon != -1 && mon != lastmon) {
				strategy.resetParameter(nowps);
				double olds = sst.calcScore(barlist, strategy, lastst, i);
				if (olds>0) {
					lastst = i;
					lastmon = mon;
					final_testing_score += olds;
					final_training_score += olds;
					continue;
					
				}
				nowps = fpi.findPara(barlist, strategy, nowps, lastst, i);
				strategy.resetParameter(nowps);
				double news = sst.calcScore(barlist, strategy, lastst, i);
				final_testing_score += olds;
				final_training_score += news;
				lastst = i;
				System.out.println(lastmon);
				System.out.println(olds);
				System.out.println(news);
				System.out.println(nowps.params.get(0));
				System.out.println("------------------------");
				//return 0;
			}
			lastmon = mon;
		}
		System.out.println(final_testing_score);
		System.out.println(final_training_score);
		return final_testing_score;
	}
}
