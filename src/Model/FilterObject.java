package Model;

import java.util.List;

import Enumerations.EDay;
import Enumerations.EFilter;

public class FilterObject {
	//TODO parameter and fixedCourse as one parameter object of type Object (casten)
	private EFilter type;
	private int parameter;
	private List<EDay> days;
	private Course fixedCourse;
	
	public FilterObject(EFilter type, int parameter, List<EDay> days, Course fixedCourse) {
		if (type == null) {
			throw new NullPointerException("type should not be null");
		}
		
		if (type.needDays()) {
			if (days == null) {
				throw new NullPointerException("days should not be null");
			}
		}
		
		if(type.needCourse()) {
			if(fixedCourse == null) {
				throw new NullPointerException("fixedCourse should not be null");
			}
		}
		
		
		this.type = type;
		this.parameter = parameter;
		this.days = days;
	}
	
	public EFilter getType(){
		return type;
	}
	
	public void setType(EFilter type){
		if (type == null) {
			throw new NullPointerException("type should not be null");
		}
		
		this.type = type;
	}
	
	public Course getCourse() {
		return fixedCourse;
	}
	
	public void setCourse(Course fixedCourse){
		this.fixedCourse = fixedCourse;
	}
	
	public int getParameter(){
		return parameter;
	}
	
	public void setParameter(int parameter){
		this.parameter = parameter;
	}
	
	public List<EDay> getDays() {
		return days;
	}
	
	public void setDays(List<EDay> days) {
		if (days == null) {
			throw new NullPointerException("days should not be null");
		}
		
		this.days = days;
	}
	
	public String toString() {
		String daysstring = "";
		
		for (EDay eDay : days) {
			daysstring = daysstring + eDay.toString() + " ";
		}
		
		return type.toString() + " " + Integer.toString(parameter) + " " + daysstring;
	}
}
