import java.util.HashMap;

public class Agent
{	
	protected int id;	
	protected HashMap<Integer, Integer> attributes;

		
	Agent(int id)
	{
		this.id = id;
	}
	
	int self()
	{
		return id;
	}
	
	public HashMap<Integer, Integer> getAttributes()
	{
		return attributes;
	}
	
}
