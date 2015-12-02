package Model;
import java.util.*;

import Enumerations.EPeriod;

public class TimeTable
{
	private int id;
	private List<Course> courses;

	public TimeTable(int id)
	{
		courses = new ArrayList<Course>();
		this.id = id;
	}
	
	public int getId() {
		return id;
	}

	// returns true, if course could be added successfully
	public boolean addCourse(Course course)
	{
		if(id == 145){
			System.out.println("moin");
		}
		if (course == null)
		{
			throw new NullPointerException();
		}

		boolean result = true;
		int i = 0;

		for (Course list : courses) 
		{
			i+=1;
			if (list.getTime().getDay().toInt() > course.getTime().getDay().toInt())
			{
				i-=1;
			} else if (list.getTime().getDay() == course.getTime().getDay()) 	//Tag gleich
			{	
				if (list.getTime().getTime() > course.getTime().getTime())
				{
					i-=1;
				} else if (list.getTime().getTime() == course.getTime().getTime())  		//Stunde gleich
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
			courses.add(i, course);
		}

		return result;
	}

	public List<Course> getCourseList()
	{
		return courses;
	}
	
	public void removeCourse(Course course) {
		
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

//		Course currentCourse;

		System.out.format("\nTimetable %d\n", id);

		for (Course course: courses)
		{
			System.out.format("%s %d.DS: %s in %s/%s mit %s\n", 
				course.getTime().getDay().toString(), 
				course.getTime().getTime(), 
				course.getModuleName(), 
				course.getPlace().getBuilding(),
				course.getPlace().getRoom(), 
				course.getInstructor()
			);
		}
	}



}