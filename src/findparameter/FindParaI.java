package findparameter;

import java.util.ArrayList;

import Strategy_Interface.SimpleStrategyI;
import data.Bar;

public interface FindParaI {
	public ParameterSet findPara(ArrayList<Bar> barlist, SimpleStrategyI ssi, ParameterSet ps);
	public ParameterSet findPara(ArrayList<Bar> barlist, SimpleStrategyI ssi, ParameterSet ps, int st , int ed);
}
