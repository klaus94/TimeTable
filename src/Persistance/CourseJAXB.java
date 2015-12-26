package Persistance;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import Enumerations.EDay;
import Enumerations.EPeriod;
import Model.Course;
import Model.Lecture;
import Model.Place;
import Model.Time;

public class CourseJAXB {
	public void persist()
	{
		try {
			Course course = new Lecture("BuS", new Time(EDay.DIENSTAG, 2, EPeriod.EACHWEEK), new Place("HSZ", "0004"), "Härtig");
			JAXBContext jc = JAXBContext.newInstance(Course.class);
			Marshaller ms = jc.createMarshaller();
			ms.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			ms.marshal(course, System.out);
			ms.marshal(course, new File("test.xml"));
		} catch (Exception e)
		{
			System.out.println("es ist ein fehler aufgetreten");
			System.out.println(e.getMessage());
		}
	}
}
