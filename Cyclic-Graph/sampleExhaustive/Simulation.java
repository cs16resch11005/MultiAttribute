import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

public class Simulation 
{
	//int numCores = Runtime.getRuntime().availableProcessors();
	public static Vector<Agent> population        = new Vector<Agent>();	
	@SuppressWarnings("unchecked")
	public static ArrayList<City>  kAttrsNodes[]    = new ArrayList[ConfigParameters.k_Value];
	public static int currentTime                 = 0;	
	public static int currRunId					  = 0;
	
	public static boolean isQuerySatisfied;
	public static int avgSatNodes[] = new int[ConfigParameters.k_Value];
	public static int maxLevel;
	
	QueryEvaluation qe; //= new QueryEvaluation();	
	SamplingOnKAttributeGraphs skag; // = new  SamplingOnKAttributeGraphs();
	
	Simulation(int runId)
	{
		currRunId       = runId;		
		currentTime     = 0;
		
		for(int i=0; i<ConfigParameters.k_Value; i++)
		{
			kAttrsNodes[i]      = new ArrayList<City>();			
			avgSatNodes[i] = 0;
		}
		
		isQuerySatisfied = false;
		population.clear();
		maxLevel = 2*ConfigParameters.sim_Time + 1;
		
		qe = new QueryEvaluation();
		skag = new  SamplingOnKAttributeGraphs();
	}
	
	public void preConfiguration() throws IOException 
	{
		
		for(int i=0; i<ConfigParameters.num_People; i++)
		{
			Agent agent = new Agent(i);
			agent.attributes = new HashMap<Integer, Integer>();
			population.add(agent);
		}	
		
		for(int i=0; i<ConfigParameters.k_Value;i++)
		{
			for(int j=0; j<InitialSetup.kAdjList[i].length; j++)
			{
				City city = new City(j);
				city.people = new Vector<Integer>();
				kAttrsNodes[i].add(city);
				SamplingOnKAttributeGraphs.nodeLevels[i][j] = 2*ConfigParameters.sim_Time + 1;
			}	
		}		
		
		InitializeAttributeValuesofAgents();			
		qe.readAtomicPropositions();		
		//skag.chooseSamplesOnKattributeGraphs();		
		//skag.printNodeLevels();
		//System.exit(1);
		Run();
		
	}/* End of preConfiguration() */	
	
	public void Run() throws IOException
	{
		for(int i=0; i<ConfigParameters.sim_Time; i++)
		{
			currentTime = i;
			UpdateAttributeValuesOfAgents();		
			isQuerySatisfied = qe.checkQuerySatisfied();
			
			if(isQuerySatisfied)
				break;			
		}
		
		for(int k=0; k<ConfigParameters.k_Value; k++)
		{
			avgSatNodes[k] = avgSatNodes[k]/(currentTime+1);
			//System.out.println("Avg sat Nodes " + k + "-attribute graph :" + avgSatNodes[k] + " sample Size : " + UnionNbrsList1K[k].size());
		}
		//System.exit(0);

	}/* End of Run()*/	
	

	public void postConfiguration()
	{
		for(int i=0; i<ConfigParameters.k_Value; i++)
		{
			kAttrsNodes[i].clear(); 			
			//SamplingOnKAttributeGraphs.UnionNbrsList1K[i].clear(); 
			//SamplingOnKAttributeGraphs.UnionNbrsList2K[i].clear(); 
			//SamplingOnKAttributeGraphs.samplePopulation[i] = null;
		}
	}/* End of postConfiguration() */	
	
	public void InitializeAttributeValuesofAgents() throws IOException
	{
		for(int k=0; k<ConfigParameters.k_Value; k++)
		{
			InitializeAttributeValues.initializeKthAttributeValueOfAgents(k, population);
		}
		
				// debug code
		/*for(int k=0; k<ConfigParameters.k_Value; k++)
		{
			System.out.println("\n " + k + " - Attribute graph\n");
			
			for(int j=0; j<kAttrsNodes[k].size(); j++)
			{
				;//System.out.println("Agents at node " + j+ " : " + kAttrsNodes[k].get(j).people.toString() + " size: "+ kAttrsNodes[k].get(j).people.size());
			}
		}*/
		
	}
		
	public void UpdateAttributeValuesOfAgents() 
	{
		for(int k=0; k<ConfigParameters.k_Value; k++)		
		{
		//	System.out.println("update values of "+k+"-attribute graph");			
			//System.out.println("Sample size of " + k +"-attribute graph : "+samplePopulation[k].length);
						
			//int count=0;			
			for(int i=0; i<ConfigParameters.num_Nodes; i++)
			{
				for(int j=0; j<kAttrsNodes[k].get(i).people.size(); j++)
				{   
					int agentId = kAttrsNodes[k].get(i).people.get(j);  //InitialSetup.kAdjList[k][j];										
					Agent agent = population.get(agentId);
					int currVal = agent.attributes.get(k);
					ArrayList<Integer> nbrList = InitialSetup.kAdjList[k][currVal];
					//System.out.println("Agent Id : " + agentId + " curr Val" + currVal + " " +nbrList);
					//count = count+1;
					UpdateAttributeValues.updateKthAttributeValueOfAgent(k, agent, nbrList, Math.random());	
				}			
			}
			//System.out.println("Total Agents:" + count);
			
			/*System.out.println("number of agents in cone sample :" + currentTime + " " + count);
			//debug code
			int count=0; 
			for(int i=0; i<Simulation.kAttrsNodes[k].size(); i++)
			{
				count = count + Simulation.kAttrsNodes[k].get(i).people.size();
			}
			
			if(count != ConfigParameters.num_People)
			{
				System.out.println("Error!! Total agents are not equals to the sum of agents in all the cities");
				System.exit(0);
			}*/
		}
	}
	
	
}
