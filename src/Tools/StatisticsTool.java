package Tools;

import java.util.ArrayList;

import data.Bar;
import data.BarAll;

public class StatisticsTool {
	static public double avgClose(ArrayList<Bar> barlist, int at, int len) {
		double r = 0.0;
		for (int i = 0; i < len; i++) {
			BarAll bar = (BarAll) barlist.get(at - i);
			r += 1.0 * bar.close / len;
		}
		return r;
	}

	static public double decayedAverage(ArrayList<Bar> barlist, int at, int len) {
		double r = 0.0;
		for (int i = len; i >= 0; i--) {
			BarAll bar = (BarAll) barlist.get(at - i);
			if (i == len)
				r = bar.close;
			else
				r = 2.0 * (bar.close - r) / (len + 1) + r;
		}
		return r;
	}
	
	static public double decayedAverage(double bufvalue, ArrayList<Bar> barlist, int at, int len) {
		double r = bufvalue;
		BarAll bar = (BarAll) barlist.get(at);
		if (at==0) {
			r = bar.close;
		}
		else {
			r = 2.0 * (bar.close - r) / (len + 1) + r;
		}
		return r;
	}
}
