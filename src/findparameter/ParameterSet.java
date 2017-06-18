package findparameter;

import java.util.ArrayList;

public class ParameterSet {
	public ArrayList<Double> params;
	public ArrayList<Double> params_st;
	public ArrayList<Double> params_ed;

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < params.size(); i++) {
			if (i != 0)
				sb.append(' ');
			sb.append(params.get(i));
		}
		return sb.toString();
	}
}
