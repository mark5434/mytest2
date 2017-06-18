package backtest;

import java.util.ArrayList;

import Strategy_Interface.MyState;
import Strategy_Interface.SimpleStrategyI;
import data.Bar;

public interface SingleScoreTestI {
	public double calcScore(ArrayList<Bar> barlist, SimpleStrategyI strategy);
	public double calcScore(ArrayList<Bar> barlist, SimpleStrategyI strategy,  int st, int ed);
	public double calcScore(ArrayList<Bar> barlist, MyState startstate, SimpleStrategyI strategy, int st, int ed);
	public double calcScore(ArrayList<Bar> barlist, MyState startstate, int startr, SimpleStrategyI strategy, int st, int ed);
	public MyState getLastState();
	public int getLastR();
}
