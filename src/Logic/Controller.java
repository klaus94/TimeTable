package Logic;
import java.util.*;

import Enumerations.ECourseType;
import Model.Course;
import Model.TimeTable;

public class Controller
{
	TimeTable basicTimeTable;								// contains all lectures

	private List<TimeTable> allTimeTables;					// list of all generated timeTables
	private List<Course> possibleCoursesList;				// list of all courses available
	private Map<String, List<Course>> exercisesModulMap;	// mappes a module to all possible exercises for this module
	
	public Controller(Map<String, List<Course>> courseMap)
	{
		if (courseMap == null)
		{
			throw new NullPointerException();
		}
		
		possibleCoursesList = new ArrayList<Course>();
		for (String module : courseMap.keySet()) {
			possibleCoursesList.addAll(courseMap.get(module));
		}
		
		
		allTimeTables = new ArrayList<TimeTable>();
		exercisesModulMap = courseMap;
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
		generateTimeTables(basicTimeTable, exercisesModulMap);
	}

	// add all lectures to the basic-timetable
	// and removes all lectures from the possibleCoursesList
	private TimeTable buildBasicTimeTable()
	{
		TimeTable basicTimeTable = new TimeTable(-1);
		List<Course> coursesToRemove = new ArrayList<Course>();
		
		//go through courseMap and get all Lectures
		for (String module : exercisesModulMap.keySet()) {
			coursesToRemove.clear();
			for (Course course : exercisesModulMap.get(module)) {
				if (course.getCourseType() == ECourseType.LECTURE) {
					coursesToRemove.add(course);
					basicTimeTable.addCourse(course);
				}
			}
		
			for (Course course: coursesToRemove)
			{
				exercisesModulMap.get(module).remove(course);
			}
		}
	
		

		return basicTimeTable;
	}


	private void generateTimeTables(TimeTable oldTimeTable, Map<String, List<Course>> restCourses)
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
				generateTimeTables(newTimeTable, restCourses);
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

}