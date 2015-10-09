import java.util.*;

public class Main
{
	public static void main(String[] args) 
	{
		Course courseMath1 = new Lecture("BuS", new Time(EDay.DIENSTAG, 2, EPeriod.ODDWEEK), new Place("HSZ", "0004"), "Härtig");
		Course courseMath2 = new Lecture("BuS", new Time(EDay.FREITAG, 2, EPeriod.EACHWEEK), new Place("HSZ", "0003"), "Härtig");
		Course courseMath3 = new Lecture("FS", new Time(EDay.MONTAG, 3, EPeriod.EACHWEEK), new Place("HSZ", "0002"), "Hölldobler");
		Course courseMath4 = new Lecture("FS", new Time(EDay.DONNERSTAG, 4, EPeriod.ODDWEEK), new Place("HSZ", "0003"), "Hölldobler");
		Course courseMath5 = new Lecture("Mathe", new Time(EDay.DIENSTAG, 3, EPeriod.ODDWEEK), new Place("HSZ", "0002"), "Baumann");
		Course courseMath6 = new Lecture("Mathe", new Time(EDay.DONNERSTAG, 3, EPeriod.EACHWEEK), new Place("HSZ", "0003"), "Baumann");

		Course courseMath10 = new ExerciseCourse("BuS", new Time(EDay.MONTAG, 2, EPeriod.EACHWEEK), new Place("WIL", "C073"), "1");
		Course courseMath11 = new ExerciseCourse("BuS", new Time(EDay.MONTAG, 3, EPeriod.EACHWEEK), new Place("WIL", "C013"), "2");
		Course courseMath12 = new ExerciseCourse("BuS", new Time(EDay.MONTAG, 3, EPeriod.EVENWEEK), new Place("WIL", "A923"), "3");
		Course courseMath13 = new ExerciseCourse("BuS", new Time(EDay.DIENSTAG, 1, EPeriod.EACHWEEK), new Place("WIL", "B234"), "4");
		Course courseMath14 = new ExerciseCourse("BuS", new Time(EDay.DIENSTAG, 6, EPeriod.ODDWEEK), new Place("WIL", "B234"), "5");
		Course courseMath15 = new ExerciseCourse("BuS", new Time(EDay.MITTWOCH, 6, EPeriod.EACHWEEK), new Place("WIL", "C073"), "1");
		Course courseMath16 = new ExerciseCourse("BuS", new Time(EDay.DONNERSTAG, 1, EPeriod.EACHWEEK), new Place("WIL", "C013"), "2");
		Course courseMath17 = new ExerciseCourse("BuS", new Time(EDay.DONNERSTAG, 1, EPeriod.EVENWEEK), new Place("WIL", "A923"), "3");
		Course courseMath18 = new ExerciseCourse("BuS", new Time(EDay.DONNERSTAG, 3, EPeriod.EACHWEEK), new Place("WIL", "B234"), "4");
		Course courseMath19 = new ExerciseCourse("BuS", new Time(EDay.FREITAG, 1, EPeriod.ODDWEEK), new Place("WIL", "B234"), "5");
		Course courseMath20 = new ExerciseCourse("BuS", new Time(EDay.FREITAG, 5, EPeriod.EACHWEEK), new Place("WIL", "C073"), "1");
		Course courseMath21 = new ExerciseCourse("FS", new Time(EDay.MONTAG, 1, EPeriod.EACHWEEK), new Place("WIL", "C013"), "2");
		Course courseMath22 = new ExerciseCourse("FS", new Time(EDay.MONTAG, 6, EPeriod.EVENWEEK), new Place("WIL", "A923"), "3");
		Course courseMath23 = new ExerciseCourse("FS", new Time(EDay.DIENSTAG, 1, EPeriod.EACHWEEK), new Place("WIL", "B234"), "4");
		Course courseMath24 = new ExerciseCourse("FS", new Time(EDay.DIENSTAG, 2, EPeriod.ODDWEEK), new Place("WIL", "B234"), "5");
		Course courseMath25 = new ExerciseCourse("FS", new Time(EDay.MITTWOCH, 1, EPeriod.EACHWEEK), new Place("WIL", "C073"), "1");
		Course courseMath26 = new ExerciseCourse("FS", new Time(EDay.MITTWOCH, 6, EPeriod.EACHWEEK), new Place("WIL", "C013"), "2");
		Course courseMath27 = new ExerciseCourse("FS", new Time(EDay.DONNERSTAG, 1, EPeriod.EVENWEEK), new Place("WIL", "A923"), "3");
		Course courseMath28 = new ExerciseCourse("FS", new Time(EDay.FREITAG, 1, EPeriod.EACHWEEK), new Place("WIL", "B234"), "4");
		Course courseMath29 = new ExerciseCourse("FS", new Time(EDay.FREITAG, 2, EPeriod.ODDWEEK), new Place("WIL", "B234"), "5");
		Course courseMath30 = new ExerciseCourse("FS", new Time(EDay.FREITAG, 3, EPeriod.EACHWEEK), new Place("WIL", "C073"), "1");
		Course courseMath31 = new ExerciseCourse("FS", new Time(EDay.FREITAG, 3, EPeriod.EACHWEEK), new Place("WIL", "C013"), "2");
		Course courseMath32 = new ExerciseCourse("FS", new Time(EDay.FREITAG, 5, EPeriod.EVENWEEK), new Place("WIL", "A923"), "3");
		Course courseMath33 = new ExerciseCourse("Mathe", new Time(EDay.MONTAG, 2, EPeriod.EACHWEEK), new Place("WIL", "B234"), "4");
		Course courseMath34 = new ExerciseCourse("Mathe", new Time(EDay.MONTAG, 4, EPeriod.ODDWEEK), new Place("WIL", "B234"), "5");
		Course courseMath35 = new ExerciseCourse("Mathe", new Time(EDay.MONTAG, 4, EPeriod.EACHWEEK), new Place("WIL", "C073"), "1");
		Course courseMath36 = new ExerciseCourse("Mathe", new Time(EDay.MONTAG, 5, EPeriod.EACHWEEK), new Place("WIL", "C013"), "2");
		Course courseMath37 = new ExerciseCourse("Mathe", new Time(EDay.DIENSTAG, 4, EPeriod.EVENWEEK), new Place("WIL", "A923"), "3");
		Course courseMath38 = new ExerciseCourse("Mathe", new Time(EDay.MITTWOCH, 2, EPeriod.EACHWEEK), new Place("WIL", "B234"), "4");
		Course courseMath39 = new ExerciseCourse("Mathe", new Time(EDay.MITTWOCH, 2, EPeriod.ODDWEEK), new Place("WIL", "B234"), "5");
		Course courseMath40 = new ExerciseCourse("Mathe", new Time(EDay.MITTWOCH, 3, EPeriod.EACHWEEK), new Place("WIL", "C073"), "1");
		Course courseMath41 = new ExerciseCourse("Mathe", new Time(EDay.MITTWOCH, 4, EPeriod.EACHWEEK), new Place("WIL", "C013"), "2");
		Course courseMath42 = new ExerciseCourse("Mathe", new Time(EDay.MITTWOCH, 4, EPeriod.EVENWEEK), new Place("WIL", "A923"), "3");
		Course courseMath43 = new ExerciseCourse("Mathe", new Time(EDay.DONNERSTAG, 6, EPeriod.EACHWEEK), new Place("WIL", "B234"), "4");
		Course courseMath44 = new ExerciseCourse("Mathe", new Time(EDay.FREITAG, 5, EPeriod.ODDWEEK), new Place("WIL", "B234"), "5");







		List<Course> courseList = new ArrayList<Course>();
		courseList.add(courseMath1);
		courseList.add(courseMath2);
		courseList.add(courseMath3);
		courseList.add(courseMath4);
		courseList.add(courseMath5);
		courseList.add(courseMath6);

		courseList.add(courseMath10);
		courseList.add(courseMath11);
		courseList.add(courseMath12);
		courseList.add(courseMath13);
		courseList.add(courseMath14);
		courseList.add(courseMath15);
		courseList.add(courseMath16);
		courseList.add(courseMath17);
		courseList.add(courseMath18);
		courseList.add(courseMath19);
		courseList.add(courseMath20);
		courseList.add(courseMath21);
		courseList.add(courseMath22);
		courseList.add(courseMath23);
		courseList.add(courseMath24);
		courseList.add(courseMath25);
		courseList.add(courseMath26);
		courseList.add(courseMath27);
		courseList.add(courseMath28);
		courseList.add(courseMath29);
		courseList.add(courseMath30);
		courseList.add(courseMath31);
		courseList.add(courseMath32);
		courseList.add(courseMath33);
		courseList.add(courseMath34);
		courseList.add(courseMath35);
		courseList.add(courseMath36);
		courseList.add(courseMath37);
		courseList.add(courseMath38);
		courseList.add(courseMath39);
		courseList.add(courseMath40);
		courseList.add(courseMath41);
		courseList.add(courseMath42);
		courseList.add(courseMath43);
		courseList.add(courseMath44);
		

		Controller myController = new Controller(courseList);
		myController.generateTimeTables();
		List<TimeTable> allTimeTables = myController.getAllTimeTables();

		//myController.showAllTimeTables();
		myController.filter(2,5);
	}
}