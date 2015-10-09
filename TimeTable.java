import java.util.*;

public class TimeTable
{
	private int id;
	private List<Course> courses;

	public TimeTable(int id)
	{
		courses = new ArrayList<Course>();
		this.id = id;
	}

	// returns true, if course could be added successfully
	public boolean addCourse(Course course)
	{
		if (course == null)
		{
			throw new NullPointerException();
		}

		boolean result = true;

		for (Course list : courses) 
		{
			if (list.getTime().getDay() == course.getTime().getDay()) 	//Tag gleich
			{	
				if (list.getTime().getTime() == course.getTime().getTime())  		//Stunde gleich
				{
					result = false;
					if (((list.getTime().getPeriod() == EPeriod.ODDWEEK) && (course.getTime().getPeriod() == EPeriod.EVENWEEK)) || 
						((list.getTime().getPeriod() == EPeriod.EVENWEEK) && (course.getTime().getPeriod() == EPeriod.ODDWEEK)))
					{
						result = true;
					}
				}
			}
		}
		
		if (result == true)
		{
			courses.add(course);
		}

		return result;
	}

	public List<Course> getCourseList()
	{
		return courses;
	}

	// it's not allowed to change id
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
		if (courses.isEmpty())
		{
			return;
		}

		Course currentCourse;

		System.out.format("\nTimetable %d\n", id);

		for (Course course: courses)
		{
			System.out.format("%s %d. DS : %s in %s mit %s\n", 
				course.getTime().getDay().toString(), 
				course.getTime().getTime(), 
				course.getModuleName(), 
				course.getPlace().getRoom(), 
				course.getInstructor()
			);
		}
	}



}