package Model;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="CourseList")
@XmlAccessorType(XmlAccessType.FIELD)
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
