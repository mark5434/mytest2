package Strategy_Interface;

import java.util.ArrayList;

import data.Bar;
import findparameter.ParameterSet;

public interface SimpleStrategyI {
	public int whatToDo(ArrayList<Bar> barlist, MyState state, int at);
	
	public void resetParameter(ParameterSet ps);
	public void init();
}
