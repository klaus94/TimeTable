import java.util.*;

public class Filter
{
	// Filter should not be instantiated
	private Filter()
	{

	}

	//used to filter, when the lessons should start at earliest hour
	public static List<TimeTable> filterByMorningtime(List<TimeTable> timeTables, List<EDay> days, int frueh)
	{
		List<TimeTable> result = new ArrayList<TimeTable>();
		
		for (TimeTable timeTable : timeTables) 
		{
			boolean available = true;
			for (Course course : timeTable.getCourseList())
			{
				if ((days.contains(course.getTime().getDay())) && (course.getTime().getTime() < frueh))
				{
					available = false;
				} 
			}
			if (available) 
			{
			
				result.add(timeTable);	
			}
		}
		return result;
	}

	//used to filter, when the lessons should end latestly
	public static List<TimeTable> filterByAfternoontime(List<TimeTable> timeTables, List<EDay> days, int spaet)
	{
		List<TimeTable> result = new ArrayList<TimeTable>();

		for (TimeTable timeTable : timeTables) 
		{
			boolean available = true;
			for (Course course : timeTable.getCourseList()) 
			{
				if ((days.contains(course.getTime().getDay())) && (course.getTime().getTime() > spaet))
				{
					available = false;
				} 
			}
			if (available) 
			{
				result.add(timeTable);	
			}
		}
		return result;
	}

	//used to filter, how many lessons you want at minimum on a day
	public static List<TimeTable> filterByMinNumber(List<TimeTable> timeTables, int min)
	{
		List<TimeTable> result = new ArrayList<TimeTable>();
		for (TimeTable timeTable : timeTables) 
		{
			EDay current = null;
			int i = 0;
			boolean available = true;
			for (Course course : timeTable.getCourseList()) 
			{
				if (current == null)
				{
					current = course.getTime().getDay();
				}

				if (current == course.getTime().getDay())
				{
					i += 1;
				} else {
					if (i < min)
					{
						available = false;
					}
					i = 1;
					current = course.getTime().getDay();
				}
			}
			if (i < min) available = false;

			if (available) {
				result.add(timeTable);
			}
		}

		return result;
	}

	//used to filter, how many lessons at maximum
	public static List<TimeTable> filterByMaxNumber(List<TimeTable> timeTables, int max)
	{
		List<TimeTable> result = new ArrayList<TimeTable>();
		for (TimeTable timeTable : timeTables) 
		{
			EDay current = null;
			int i = 0;
			boolean available = true;
			for (Course course : timeTable.getCourseList()) 
			{
				if (current == null)
				{
					current = course.getTime().getDay();
				}

				if (current == course.getTime().getDay())
				{
					i += 1;
				} else {
					if (i > max)
					{
						available = false;
					}
					i = 1;
					current = course.getTime().getDay();
				}
			}
			if (i > max) available = false;

			if (available) {
				result.add(timeTable);
			}
		}

		return result;
	}

	//used to filter how many lessons at maxiumum in a row
	public static List<TimeTable> filterByMaxInRow(List<TimeTable> timeTables, int maxRow)
	{
		List<TimeTable> result = new ArrayList<TimeTable>();
		boolean possibleTimeTable;
		EDay currentDay;
		int coursesInRow = 0;
		int lastLessonCurrentDay = -1;

		for (TimeTable timeTable: timeTables)
		{
			currentDay = null;
			possibleTimeTable = true;

			for ( Course course: timeTable.getCourseList() )
			{
				if ( currentDay == null )
				{
					// first day, first course: initialize params
					currentDay = course.getTime().getDay();
					coursesInRow = 1;
					lastLessonCurrentDay = course.getTime().getTime();
				}

				if ( ( course.getTime().getDay() == currentDay ) && ( lastLessonCurrentDay+1 == course.getTime().getTime() ) )
				{
					// same day and course directly behind another course
					coursesInRow += 1;
				}
				else if ( course.getTime().getDay() == currentDay )
				{
					// another course at the same day but not directly behind another course
					coursesInRow = 1;
				}
				else
				{
					// course of the next day
					// 1) check coursesInRow of last day
					if (coursesInRow > maxRow)
					{
						possibleTimeTable = false;
						break;
					}

					// 2) prepare next day
					currentDay = course.getTime().getDay();
					coursesInRow = 1;
				}

				// refresh last lesson on that day
				lastLessonCurrentDay = course.getTime().getTime();

			}// end for (Courses)

			if (possibleTimeTable)
			{
				result.add(timeTable);
			}

		}// end for (Timetables)

		return result;
	}
}