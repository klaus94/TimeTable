import java.util.*;

public class Controller
{
	TimeTable basicTimeTable;								// contains all lectures

	private List<TimeTable> allTimeTables;					// list of all generated timeTables
	private List<Course> possibleCoursesList;				// list of all courses available
	private Map<String, List<Course>> exercisesModulMap;	// mappes a module to all possible exercises for this module

	public Controller(List<Course> possibleCoursesList)
	{
		if (possibleCoursesList == null)
		{
			throw new NullPointerException();
		}

		this.possibleCoursesList = possibleCoursesList;
		allTimeTables = new ArrayList<TimeTable>();
		exercisesModulMap = new HashMap<String, List<Course>>();
		basicTimeTable = new TimeTable(0);
		basicTimeTable = buildBasicTimeTable();
		basicTimeTable.showTimeTable();
	}

	public List<TimeTable> getAllTimeTables()
	{
		return allTimeTables;
	}

	public void generateTimeTables()
	{
		// generate the exercise-Map
		// e.g the exercisesModulMap could look like this: ( {"Mathe-1", [course_a, course_b, course_c]},  {"TGI", [course_d]} )
		List<Course> currentExerciseList;
		for (Course course: possibleCoursesList)
		{
			// be sure, that course-associated list exists
			currentExerciseList = exercisesModulMap.get( course.getModuleName() );
			if (currentExerciseList == null)
			{
				currentExerciseList =  new ArrayList<Course>();
				exercisesModulMap.put(course.getModuleName(), currentExerciseList);
			}

			// add current course to this list
			currentExerciseList.add(course);
		}

		generateNewTimeTable(basicTimeTable, exercisesModulMap);
	}

	// add all lectures to the basic-timetable
	// and removes all lectures from the possibleCoursesList
	private TimeTable buildBasicTimeTable()
	{
		TimeTable basicTimeTable = new TimeTable(-1);
		List<Course> coursesToRemove = new ArrayList<Course>();
		for (Course course: possibleCoursesList)
		{
			if (course instanceof Lecture)
			{
				basicTimeTable.addCourse(course);
				coursesToRemove.add(course);		
			}
		}

		for (Course course: coursesToRemove)
		{
			possibleCoursesList.remove(course);
		}

		return basicTimeTable;
	}


	//TODO -oTilo: need to change that							modulName, list
	private void generateNewTimeTable(TimeTable oldTimeTable, Map<String, List<Course>> restCourses)
	{
		if (oldTimeTable == null || restCourses == null)
		{
			throw new NullPointerException();
		}

		// break case:
		if (restCourses.keySet().isEmpty())
		{
			allTimeTables.add(oldTimeTable);
			return;
		}

		String nextModule = (String)restCourses.keySet().toArray()[0];		// pick first module in map
		List<Course> courseList = restCourses.get(nextModule);		// temporary saves the current courseList
		restCourses.remove(nextModule);								// remove this module from the restCourse-list
		

		for (Course course: courseList )
		{
		    TimeTable newTimeTable = copyTimeTable(oldTimeTable);
			
			if (newTimeTable.addCourse(course))						// if course could successfully be added to the timetable
			{
				generateNewTimeTable(newTimeTable, restCourses);
			} else 
			{
				//free(newTimeTable);
			}
		}

		restCourses.put(nextModule, courseList);
	}

	private TimeTable copyTimeTable(TimeTable toCopy)
	{
		TimeTable timeTable = new TimeTable(Id.getInstance().getNext());
		for (Course course: toCopy.getCourseList()) 
		{
			timeTable.addCourse(course);
		}

		return timeTable;
	}

	//used to show the TimeTables
	public void showTimeTables(List<TimeTable> timeTables)
	{
		int i=0;
		for (TimeTable timeTable: timeTables)
		{
			i+=1;
			timeTable.showTimeTable();
		}
		System.out.println(i);
	}

	//used to filter, when the lessons should start at earliest hour
	public List<TimeTable> filterByMorningtime(List<TimeTable> timeTables, List<EDay> days, int frueh)
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
	public List<TimeTable> filterByAfternoontime(List<TimeTable> timeTables, List<EDay> days, int spaet)
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
	public List<TimeTable> filterByMinNumber(List<TimeTable> timeTables, int min)
	{
		List<TimeTable> result = new ArrayList<TimeTable>();
		Map<EDay, Integer> filterMap = new HashMap<EDay, Integer>();
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
	public List<TimeTable> filterByMaxNumber(List<TimeTable> timeTables, int max)
	{
		List<TimeTable> result = new ArrayList<TimeTable>();
		Map<EDay, Integer> filterMap = new HashMap<EDay, Integer>();
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
	/*public List<TimeTable> filterByMaxInRow(List<TimeTable> timeTables, int maxRow)
	{

	} */
}