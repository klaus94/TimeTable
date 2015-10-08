import java.util.*;

public class Main
{
	public static void main(String[] args) 
	{
		Course courseMath1 = new ExerciseCourse("Ma-1", new Time(EDay.FREITAG, 1, EPeriod.EACHWEEK), new Place("WIL", "C073"), "Dr. Noack");
		Course courseMath2 = new ExerciseCourse("Ma-1", new Time(EDay.DIENSTAG, 2, EPeriod.EACHWEEK), new Place("WIL", "C013"), "Marx");
		Course courseMath3 = new ExerciseCourse("Ma-1", new Time(EDay.FREITAG, 4, EPeriod.EACHWEEK), new Place("WIL", "A923"), "Martinovic");
		Course courseMath4 = new ExerciseCourse("Ma-2", new Time(EDay.MITTWOCH, 2, EPeriod.EACHWEEK), new Place("WIL", "B234"), "Dr. Noack");
		Course courseMath5 = new Lecture("Ma-1", new Time(EDay.DIENSTAG, 1, EPeriod.ODDWEEK), new Place("WIL", "C324"), "Baumann");
		Course courseMath6 = new Lecture("Ma-1", new Time(EDay.MONTAG, 2, EPeriod.EACHWEEK), new Place("WIL", "C232"), "Baumann");

		List<Course> courseList = new ArrayList<Course>();
		courseList.add(courseMath1);
		courseList.add(courseMath2);
		courseList.add(courseMath3);
		courseList.add(courseMath4);
		courseList.add(courseMath5);
		courseList.add(courseMath6);

		Controller myController = new Controller(courseList);
		myController.generateTimeTables();
		List<TimeTable> allTimeTables = myController.getAllTimeTables();

		myController.showAllTimeTables();
	}
}