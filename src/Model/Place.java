package Model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "place")
@XmlAccessorType(XmlAccessType.NONE)
public class Place
{
	private String building;
	private String room;

	public Place(String building, String room)
	{
		if (building == null || room == null)
		{
			throw new NullPointerException();
		}

		this.building = building;
		this.room = room;
	}
	
	public void setBuilding(String building){
		this.building = building;
	}
	
	public void setRoom(String room){
		this.room = room;
	}
	
	@XmlElement
	public String getBuilding()
	{
		return building;
	}

	@XmlElement
	public String getRoom()
	{
		return room;
	}
	
	public String toString() {
		return building + " " + room;
	}
}