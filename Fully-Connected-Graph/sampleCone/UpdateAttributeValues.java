import java.util.ArrayList;
import java.util.Random;

public class UpdateAttributeValues
{

	public static void updateKthAttributeValueOfAgent(int k, Agent agent, ArrayList<Integer> nbrList, double randum)
	{
		//int currVal = agent.attributes.get(k);
		//int neighbors = nbrList.size();
		int neighbors = ConfigParameters.num_Nodes;
		double sum = 0.0;
        	double uniformProb = 1.0/neighbors;        
         
	       // Random randumc = new Random();
		//int v = randumc.nextInt(ConfigParameters.num_Nodes);
		//agent.attributes.replace(k, v);

		for(int i=0; i<neighbors; i++) 
		{
		   sum = sum + uniformProb;
		 
		   if(randum <= sum)
		   {			 		  
			  agent.attributes.replace(k, i);
			  break;
		   }		   
		}
	//	System.out.println("Agent " + agent.self() +" CurrVal : " + currVal + " Random : " + randum + " Sum : " + sum + "  NextVal : " + agent.attributes.get(k) + " neighbors:" + nbrList );
		
		if(sum > 1.000005)
		{
			System.out.println("Error!! Cummulative Probability is Greater Than ONE : " + sum);
			System.exit(0);
		}		
	}/* End of updationRulesForAgentSalary() */	
}
