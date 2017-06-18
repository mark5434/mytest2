package data;

public class Trade {
	public int day;
	public int time;
	public int amount;
	public int atprice;

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(day);
		sb.append(' ');
		sb.append(time);
		sb.append(' ');
		sb.append(amount);
		sb.append(' ');
		sb.append(atprice);
		return sb.toString();
	}
}
