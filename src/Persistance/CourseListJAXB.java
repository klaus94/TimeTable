package Persistance;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import Model.CourseList;

// USAGE:
// ------
// 1) WRITE:
//	List<Course> myList;							// create a list of courses, that should be saved
// 	myList...										// write data into the list
// 	CourseList cList = new CourseList(myList);		// create a CourseList from your list (CourseList is the datastructure, the xml-marshaller understands)
//	CourseListJAXB c = new CourseListJAXB();		// get instance of CourseListJAXB
//	c.save(cList);									// save the list to the file: src/data/courses.xml

// 2) READ:
//	CourseListJAXB c = new CourseListJAXB();		// get instance of CourseListJAXB
//	CourseList cList = c.load();					// loads all courses from file: src/data/courses.xml
//	List<Course> myList = cList.getCourse();		// i know, the name is bad. but xml-takes this method-name to write the xml-tags...
//	myList...										// do something with the list of courses


public class CourseListJAXB {
	private String fileName = "src" + File.separator + "data" + File.separator + "courses.xml";
	
	public void save(CourseList courseList)
	{
		try {
			if (courseList == null)
			{
				throw new NullPointerException();
			}
			
			JAXBContext jc = JAXBContext.newInstance(CourseList.class);
			Marshaller ms = jc.createMarshaller();
			ms.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			ms.marshal(courseList, System.out);
			ms.marshal(courseList, new File(fileName));
		} catch (Exception e)
		{
			System.out.println("es ist ein fehler aufgetreten");
			System.out.println(e.getMessage());
		}
	}
	
	public CourseList load()
	{
		CourseList courseList = null;
		JAXBContext jc;
		try {
			jc = JAXBContext.newInstance(CourseList.class);
			Unmarshaller ums = jc.createUnmarshaller();
			courseList = (CourseList)ums.unmarshal(new File(fileName));
		} catch (JAXBException e) {
			System.out.println("FEHLER");
			e.printStackTrace();
		}
		
		return courseList;
	}
	
}
