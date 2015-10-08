import java.util.*;

public class TimeTable
{
	private int id;
	private Map<Time, Course> timeTable;

	public TimeTable(int id)
	{
		timeTable = new HashMap<Time, Course>();
		this.id = id;
	}

	// returns true, if course could be added successfully
	public boolean addCourse(Course course)
	{
		if (course == null)
		{
			throw new NullPointerException();
		}

		if (timeTable.get(course.getTime()) == null)		
		{
			timeTable.put(course.getTime(), course);
			return true;
		}
		
		return false;
	}

	public Map<Time, Course> getCourseMap()
	{
		return timeTable;
	}

	public void setId(int id)
	{
		if (id < 0)
		{
			throw new IllegalArgumentException();
		}

		this.id = id;
	}

	public void showTimeTable()
	{
		if (timeTable.isEmpty())
		{
			return;
		}

		Course currentCourse;

		System.out.format("Timetable %d\n", id);

		for (Time time: timeTable.keySet())
		{
			currentCourse = timeTable.get(time);
			System.out.format("%s %d. DS : %s in %s mit %s\n", 
				time.getDay().toString(), 
				time.getTime(), 
				currentCourse.getModuleName(), 
				currentCourse.getPlace().getRoom(), 
				currentCourse.getInstructor()
			);
		}
	}



}