package labeling;

import java.util.ArrayList;

import data.Bar;
import data.BarMore;

public class InsessionLabel {
	static public ArrayList<Double> tagLabel(ArrayList<Bar> barlist, int futureBars) {
		ArrayList<Double> res = new ArrayList<Double>();
		for (int i = 0; i < barlist.size(); i++) {
			if (i >= barlist.size() - 2) {
				res.add(0.0);
				continue;
			}
			double label = 0;
			int high = 0;
			int low = Integer.MAX_VALUE;
			int lasttime = barlist.get(i).time;
			int timeexitp = 0;
			for (int j = 1; j < futureBars; j++) {
				if (i + j == barlist.size())
					break;
				BarMore jbar = (BarMore) barlist.get(i + j);
				if (jbar.time - lasttime > 3600 || jbar.time - lasttime < -3600) {
					break;
				}
				high = Math.max(high, jbar.high);
				low = Math.min(low, jbar.low);
				lasttime = jbar.time;
				timeexitp = jbar.low;
			}
			int now = ((BarMore) barlist.get(i)).close;
			// BarMore next = (BarMore) barlist.get(i+1);
			// now = next.high;
			if (now - low >= 10) {
				label = -10;
			} else {
				if (high - now >= 40)
					label = 40;
				else
					label = timeexitp - now;
			}
			res.add(label);
		}
		return res;
	}

	static public ArrayList<Double> tagLabel(ArrayList<Bar> barlist) {
		return tagLabel(barlist, 600);
	}
}
