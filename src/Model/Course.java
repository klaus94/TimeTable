package Model;
public abstract class Course
{
	private String moduleName;
	private Time time;		
	private Place place;
	private String instructor;

	public Course(String moduleName, Time time, Place place, String instructor)
	{
		if (moduleName == null || time == null || place == null || instructor == null)
		{
			throw new NullPointerException("any argument is null in Course");
		}
		if (moduleName.equals(""))
		{
			throw new IllegalArgumentException();
		}

		this.moduleName = moduleName;
		this.time = time;
		this.place = place;
		this.instructor = instructor;
	}

	public String getModuleName()
	{
		return moduleName;
	}

	public Time getTime()
	{
		return time;
	}

	public Place getPlace()
	{
		return place;
	}

	public String getInstructor()
	{
		return instructor;
	}
	
	public String toString() {
		String str = moduleName + " " +
				time.toString() + " " +
				place.toString() + " " +
				instructor;
		return str;
	}
}