package Model;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import Enumerations.EDay;
import Enumerations.EPeriod;

@XmlRootElement(name="time")
public class Time
{
	private EDay day;
	private int time;
	private EPeriod period;

	public Time() {
		super();
	}

	public Time(EDay day, int time, EPeriod period)
	{
		if (day == null || period == null)
		{
			throw new NullPointerException();
		}
		if (time < 0 || time > 7)
		{
			throw new IllegalArgumentException();
		}
		this.day = day;
		this.time = time;
		this.period = period;
	}

	@XmlElement
	public EDay getDay()
	{
		return day;
	}

	@XmlElement
	public int getTime()
	{
		return time;
	}

	@XmlElement
	public EPeriod getPeriod()
	{
		return period;
	}
	
	@Override
	public String toString()
	{
		// Return Format: "Mo 2.DS"
		String result = "";
		
		result += day.toString();
		result += " ";
		result += time;
		result += ".DS";
		
		return result;
	}
}