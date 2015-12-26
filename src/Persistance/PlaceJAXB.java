package Persistance;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import Model.Place;

public class PlaceJAXB {
	public void persist()
	{
		try {
			Place p = new Place("WIL", "C222");
			JAXBContext jc = JAXBContext.newInstance(Place.class);
			Marshaller ms = jc.createMarshaller();
			ms.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			ms.marshal(p, System.out);
			ms.marshal(p, new File("test.xml"));
		} catch (Exception e)
		{
			System.out.println("es ist ein fehler aufgetreten");
			e.printStackTrace();
		}
	}
}