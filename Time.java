public class Time
{
	private EDay day;
	private int time;
	private EPeriod period;

	public Time(EDay day, int time, EPeriod period)
	{
		if (day == null || period == null)
		{
			throw new NullPointerException();
		}
		if (time < 1 || time > 7)
		{
			throw new IllegalArgumentException();
		}
		this.day = day;
		this.time = time;
		this.period = period;
	}

	public EDay getDay()
	{
		return day;
	}

	public int getTime()
	{
		return time;
	}

	public EPeriod getPeriod()
	{
		return period;
	}
}