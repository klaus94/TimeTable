package Model;

import java.util.HashSet;
import java.util.Set;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="course")
public abstract class Course implements Cloneable
{
	private String moduleName;
	private Time time;		
	private Set<Place> place;
	private String instructor;

	public Course() {
		super();
	}
	
	public Course(String moduleName, Time time, Place place, String instructor)
	{
		if (moduleName == null || time == null || place == null || instructor == null)
		{
			throw new NullPointerException("any argument is null in Course");
		}
		if (moduleName.equals(""))
		{
			throw new IllegalArgumentException();
		}

		this.moduleName = moduleName;
		this.time = time;
		this.place = new HashSet<Place>();
		this.place.add(place);
		this.instructor = instructor;
	}
	
	public void setModuleName(String moduleName){
		this.moduleName = moduleName;
	}
	
	public void setTime(Time time) {
		this.time = time;
	}
	
	public void addPlace(Place place){
		this.place.add(place);
	}
	
	public void setInstructor(String instructor){
		this.instructor = instructor;
	}

	@XmlElement
	public String getModuleName()
	{
		return moduleName;
	}

	@XmlElement
	public Time getTime()
	{
		return time;
	}
	
	@XmlElement
	public Place getPlace()
	{
		Place place1 = null;
		for (Place place2 : place) {
			place1 = place2;
		}
		return place1;
	}

	@XmlElement
	public String getInstructor()
	{
		return instructor;
	}
	
	public String toString() {
		String placeString = "";
		for (Place place2 : place) {
			placeString = placeString + place2.toString();
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