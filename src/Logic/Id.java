package Logic;
public class Id
{
	private int id;
	private static Id instance;

	private Id()
	{
		id = 0;
	}

	public static Id getInstance()
	{
		if (instance == null)
		{
			instance = new Id();
		}
		return instance;
	}

	public int getNext()
	{
		id += 1;
		return id;
	}
}