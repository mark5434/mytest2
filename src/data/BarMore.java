package data;

public class BarMore extends Bar {
	public int open;
	public int close;

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
		return sb.toString();
	}

	static public BarMore parseString(String s) {
		BarMore res = new BarMore();
		String[] p = s.split(" ");
		res.day = Integer.parseInt(p[0]);
		res.time = Integer.parseInt(p[1]);
		res.high = Integer.parseInt(p[2]);
		res.low = Integer.parseInt(p[3]);
		res.open = Integer.parseInt(p[4]);
		res.close = Integer.parseInt(p[5]);
		return res;
	}
}
