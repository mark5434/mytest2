package feature;

import java.util.ArrayList;

import data.Bar;

public class InsessionOnebarDelta {
	static public ArrayList<Double> generateFeature(ArrayList<Bar> barlist) {
		ArrayList<Double> res = new ArrayList<Double>();
		int last = -1;
		int lasttime = -1;
		for (int i = 0; i < barlist.size(); i++) {
			Bar bar = barlist.get(i);
			if (bar.time - lasttime > 3600 || bar.time - lasttime < -3600) {
				last = -1;
			}
			lasttime = bar.time;
			if (last == -1)
				last = bar.close;
			int ires = bar.close - last;
			last = bar.close;
			res.add((double) ires);
		}
		return res;
	}
}
