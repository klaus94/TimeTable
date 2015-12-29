package Model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="CourseList")
public class CourseList {
	private List<Course> courseList;

	public CourseList(List<Course> courseList) {
		super();
		
		if (courseList == null){
			throw new NullPointerException();
		}
		this.courseList = courseList;
	}

	public CourseList() {
		super();
	}

	@XmlElement
	public List<Course> getCourse() {			// need to name the method getCourse for xml-tag-name
		return courseList;
	}

	public void setCourseList(List<Course> courseList) {
		if (courseList == null)
		{
			throw new NullPointerException();
		}
		this.courseList = courseList;
	}
	
	
}
