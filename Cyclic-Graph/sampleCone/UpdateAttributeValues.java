import java.util.ArrayList;

public class UpdateAttributeValues
{

	public static void updateKthAttributeValueOfAgent(int k, Agent agent, ArrayList<Integer> nbrList, double randum)
	{
		//int currVal = agent.attributes.get(k);
		int neighbors = nbrList.size();		
		double sum = 0.0;
        double uniformProb = 1.0/neighbors;        
         
		for(int i=0; i<neighbors; i++) 
		{
		   sum = sum + uniformProb;
		 
		   if(randum <= sum)
		   {			 		  
			  agent.attributes.replace(k, nbrList.get(i));
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
