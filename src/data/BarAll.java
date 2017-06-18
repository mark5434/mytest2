package data;

public class BarAll extends BarMore {
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

	static public BarAll parseString(String s) {
		BarAll res = new BarAll();
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
