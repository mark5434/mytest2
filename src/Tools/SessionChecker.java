package Tools;

import java.util.ArrayList;

import data.Bar;

public class SessionChecker {
	// todo: 判断session结束中 提前bar数量
	// todo: private int _before_num;
	
	private ArrayList<Integer> _time_sep = new ArrayList<Integer>();
	
	public SessionChecker() {
		_time_sep.add(90000);
	}
	public SessionChecker(int t1, int t2, int t3) {
		_time_sep.add(t1);
		if (t2>=0) _time_sep.add(t2);
		if (t3>=0) _time_sep.add(t3);
	}

	public int is_session_first(ArrayList<Bar> barlist, int at) {
		if (at==0) return 0;
		for (int i=0;i<_time_sep.size();i++) {
			// （at跨天或者at前<时间点）并且at大于等于时间点
			if ((barlist.get(at-1).time>barlist.get(at).time||barlist.get(at-1).time<_time_sep.get(i)) 
					&& barlist.get(at).time>=_time_sep.get(i))
				return i;
		}
		return -1;
	}
	public int is_session_last(ArrayList<Bar> barlist, int at) {
		if (at==barlist.size()-1) {
			// todo: 区分哪个区域的last
			return 0;
		}
		int r = is_session_first(barlist, at+1);
		if (r>=0) return 0;
		else return -1;
	}
}