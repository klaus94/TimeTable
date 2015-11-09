package Model;

import java.util.List;

public class Modul {
	private String name;
	private List<Course> courses;
	
	public Modul(String name, List<Course> courses)
	{
		if (name == null || courses == null)
		{
			throw new NullPointerException();
		}
		if (name.equals(""))
		{
			throw new IllegalArgumentException();
		}
		
		this.name = name;
		this.courses = courses;
	}
	
	public String getName()
	{
		return name;
	}
	
	public List<Course> getCourses()
	{
		return courses;
	}
	
	public void addCourse(Course course)
	{
		if (course == null)
		{
			throw new NullPointerException();
		}
		
		courses.add(course);
	}
}
