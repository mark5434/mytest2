package Tools;

import java.util.ArrayList;

import data.Bar;

public class BarTransform {
	static public ArrayList<Bar> transformByVolumn(ArrayList<Bar> barlist, int volbin) {
		ArrayList<Bar> res = new ArrayList<Bar>();
		int volcount = 0;
		int topen = -1;
		int thigh = 0;
		int tlow = Integer.MAX_VALUE;
		int daybars = 0;
		for (int i = 0; i < barlist.size(); i++) {
			Bar bar = barlist.get(i);
			volcount += bar.volumn;
			for (int j = 0; j < volcount / volbin; j++) {
				Bar nbar = new Bar();
				nbar.close = bar.close;
				nbar.open = topen;
				nbar.day = bar.day;
				nbar.time = bar.time;
				nbar.high = thigh;
				nbar.low = tlow;
				nbar.volumn = volbin;
				res.add(nbar);
				topen = bar.close;
				thigh = bar.high;
				tlow = bar.low;
			}

			volcount %= volbin;
			thigh = Math.max(thigh, bar.high);
			tlow = Math.min(tlow, bar.low);
		}
		System.out.println("input bars: " + barlist.size());
		System.out.println("output bars: " + res.size());
		return res;
	}

	static public ArrayList<Bar> transformByVolumnTime(ArrayList<Bar> barlist, int volbin) {
		ArrayList<Bar> res = new ArrayList<Bar>();
		int volcount = 0;
		int topen = -1;
		int thigh = 0;
		int tlow = Integer.MAX_VALUE;
		int daybars = 0;
		for (int i = 0; i < barlist.size(); i++) {
			Bar bar = barlist.get(i);
			volcount += bar.volumn;
			if (volcount > volbin) {
				Bar nbar = new Bar();
				nbar.close = bar.close;
				nbar.open = topen;
				nbar.day = bar.day;
				nbar.time = bar.time;
				nbar.high = thigh;
				nbar.low = tlow;
				nbar.volumn = volbin;
				res.add(nbar);
				topen = bar.close;
				thigh = bar.high;
				tlow = bar.low;
				volcount = 0;
			}

			volcount %= volbin;
			thigh = Math.max(thigh, bar.high);
			tlow = Math.min(tlow, bar.low);
		}
		System.out.println("input bars: " + barlist.size());
		System.out.println("output bars: " + res.size());
		return res;
	}
}
