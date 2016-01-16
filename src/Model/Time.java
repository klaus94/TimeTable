package Model;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import Enumerations.EDay;
import Enumerations.EPeriod;

@XmlRootElement(name="time")
@XmlAccessorType(XmlAccessType.FIELD)
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