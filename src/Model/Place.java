package Model;
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

	public String getBuilding()
	{
		return building;
	}

	public String getRoom()
	{
		return room;
	}
	
	public String toString() {
		return building + " " + room;
	}
}