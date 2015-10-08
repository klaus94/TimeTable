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
		if ( building.equals("") || room.equals("") )
		{
			throw new IllegalArgumentException();
		}

		this.building = building;
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
}