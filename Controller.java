import java.util.*;

public class Controller
{
	private int counter = 0;								// counter for timeTable id
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
		basicTimeTable = new TimeTable(-1);
		buildBasicTimeTable();
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
	private void buildBasicTimeTable()
	{
		for (Course course: possibleCoursesList)
		{
			if (course instanceof Lecture)
			{
				basicTimeTable.addCourse(course);
				possibleCoursesList.remove(course);		// could crash here (deleting items in list, while iterating through list)
			}
		}
	}

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
		
		TimeTable newTimeTable = new TimeTable(counter);
		counter += 1;

		for (Course course: courseList )
		{
			if (newTimeTable.addCourse(course))						// if course could successfully be added to the timetable
			{
				generateNewTimeTable(newTimeTable, restCourses);
			}
		}
	}

	public void showAllTimeTables()
	{
		for (TimeTable timeTable: allTimeTables)
		{
			timeTable.showTimeTable();
		}
	}
}