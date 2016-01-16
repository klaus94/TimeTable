package Model;

import java.util.HashSet;
import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import Enumerations.ECourseType;

@XmlRootElement(name="course")
@XmlAccessorType (XmlAccessType.FIELD)
public class Course implements Cloneable
{
	private String moduleName;
	private Time time;		
	private Set<Place> place;
	private String instructor;
	private ECourseType courseType;

	public Course() {
		super();
	}
	
	public Course(ECourseType courseType, String moduleName, Time time, Place place, String instructor)
	{
		if (moduleName == null || time == null || place == null || instructor == null || courseType == null)
		{
			throw new NullPointerException("an argument is null in Course");
		}
		if (moduleName.equals(""))
		{
			throw new IllegalArgumentException();
		}

		this.courseType = courseType;
		this.moduleName = moduleName;
		this.time = time;
		this.place = new HashSet<Place>();
		this.place.add(place);
		this.instructor = instructor;
	}
	
	public void setCourseType(ECourseType courseType)
	{
		if (courseType == null)
			throw new NullPointerException("CourseType is null");
	
		this.courseType = courseType;
	}
	
	public void setModuleName(String moduleName){
		if (moduleName == null)
			throw new NullPointerException("Modul-name is null");
		
		this.moduleName = moduleName;
	}
	
	public void setTime(Time time) {
		if (time == null)
			throw new NullPointerException("time is null");
		
		this.time = time;
	}
	
	public void addPlace(Place place){
		if (place == null)
			throw new NullPointerException("place is null");
		
		this.place.add(place);
	}
	
	public void setInstructor(String instructor){
		if (instructor == null)
			throw new NullPointerException("instructor is null");
		
		this.instructor = instructor;
	}

	
	public ECourseType getCourseType()
	{
		return courseType;
	}
	
	
	public String getModuleName()
	{
		return moduleName;
	}

	
	public Time getTime()
	{
		return time;
	}
	
	
	public Place getPlace()
	{
		Place place1 = null;
		if (place != null)
		{
			for (Place place2 : place) {
				place1 = place2;
			}
		}
		
		return place1;
	}

	
	public String getInstructor()
	{
		return instructor;
	}
	
	public String toString() {
		String placeString = "";
		if (place != null)
		{
			for (Place place2 : place) {
				placeString = placeString + place2.toString();
			}
		}
		
		String str = moduleName + " " +
				time.toString() + " " +
				placeString + " " +
				instructor;
		return str;
	}
	
	public Object clone() {
		Object cloneCourse = null;
	    try {
	    	cloneCourse = super.clone();
	    }
	    catch (CloneNotSupportedException e) {
	    }
	    return cloneCourse;
	}
}