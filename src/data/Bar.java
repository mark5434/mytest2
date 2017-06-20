package data;

public class Bar {
	public int day;
	public int time;
	public int high;
	public int low;
	public int open;
	public int close;
	public int volumn;

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(day);
		sb.append(' ');
		sb.append(time);
		sb.append(' ');
		sb.append(high);
		sb.append(' ');
		sb.append(low);
		sb.append(' ');
		sb.append(open);
		sb.append(' ');
		sb.append(close);
		sb.append(' ');
		sb.append(volumn);
		return sb.toString();
	}

	static public Bar parseString(String s) {
		Bar res = new Bar();
		String[] p = s.split(" ");
		res.day = Integer.parseInt(p[0]);
		res.time = Integer.parseInt(p[1]);
		res.high = Integer.parseInt(p[2]);
		res.low = Integer.parseInt(p[3]);
		res.open = Integer.parseInt(p[4]);
		res.close = Integer.parseInt(p[5]);
		res.volumn = Integer.parseInt(p[6]);
		return res;
	}
}
