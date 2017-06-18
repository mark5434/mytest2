package data;

public class Bar {
	public int day;
	public int time;
	public int high;
	public int low;

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
		return sb.toString();
	}

	static public Bar parseString(String s) {
		Bar res = new Bar();
		String[] p = s.split(" ");
		res.day = Integer.parseInt(p[0]);
		res.time = Integer.parseInt(p[1]);
		res.high = Integer.parseInt(p[2]);
		res.low = Integer.parseInt(p[3]);
		return res;
	}
}
