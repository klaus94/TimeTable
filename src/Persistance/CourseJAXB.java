package Persistance;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import Enumerations.EDay;
import Enumerations.EPeriod;
import Model.Course;
import Model.CourseList;
import Model.Lecture;
import Model.Place;
import Model.Time;

public class CourseJAXB {
	public void save(Course course)
	{
		if (course == null)
		{
			throw new NullPointerException();
		}
		try {
			JAXBContext jc = JAXBContext.newInstance(Course.class);
			Marshaller ms = jc.createMarshaller();
			ms.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			ms.marshal(course, System.out);
			ms.marshal(course, new File("src" + File.separator + "data" + File.separator + "courses.xml"));
		} catch (Exception e)
		{
			System.out.println("es ist ein fehler aufgetreten");
			System.out.println(e.getMessage());
		}
	}
	
	public Course load()
	{
		Course course = null;
		JAXBContext jc;
		try {
			jc = JAXBContext.newInstance(Course.class);
			Unmarshaller ums = jc.createUnmarshaller();
			Object o = ums.unmarshal(new File("src" + File.separator + "data" + File.separator + "courses.xml"));
//			course = (Course)ums.unmarshal(new File("src" + File.separator + "data" + File.separator + "courses.xml"));
		} catch (JAXBException e) {
			System.out.println("FEHLER");
			e.printStackTrace();
		}
		
		return course;
	}
	
}
