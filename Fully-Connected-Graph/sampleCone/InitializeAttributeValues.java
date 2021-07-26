import java.io.IOException;
import java.util.Vector;
import java.util.Random;

public class InitializeAttributeValues
{
	InitializeAttributeValues()
	{
		
	}
	
	public static void initializeKthAttributeValueOfAgents(int k, Vector<Agent> population) throws IOException
	{
		if(ConfigParameters.num_People%ConfigParameters.num_Nodes != 0)
		{
			System.out.println("Error!! Numpber of people must be multiple of Nodes(Cities)");
			System.exit(1);
		}	
		
		Random randum = new Random();	
		for(int i=0; i<ConfigParameters.num_People; i++)
		{
			int attr = randum.nextInt(ConfigParameters.num_Nodes);			
			population.get(i).attributes.put(k, attr);	
			Simulation.kAttrsNodes[k].get(attr).people.add(i);
			//System.out.println("Agent Id :" + i +" value:" + population.get(i).attributes.get(k));
		}	
		
		/* debug code
		int count=0;
		for(int i=0; i<Simulation.kAttrsNodes[k].size(); i++)
		{
			count = count + Simulation.kAttrsNodes[k].get(i).people.size();
		}
		
		if(count != ConfigParameters.num_People)
		{
			System.out.println("Error!! Total agents are not equals to the sum of agents in all the cities");
			System.exit(1);
		}
		*/
		
	}/* End of the function initialPositionOfAgents() */
	
	

}
