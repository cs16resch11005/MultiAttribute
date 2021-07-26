import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class QueryEvaluation
{
	public  int apCvalues[][] = new int[ConfigParameters.k_Value][2];
	public  double apDvalues[][] = new double[ConfigParameters.k_Value][2];
	public  boolean isAtomicPropTrue[] = new boolean[ConfigParameters.k_Value];

	QueryEvaluation()
	{
		for(int k=0; k<ConfigParameters.k_Value; k++)
			isAtomicPropTrue[k] = false;
	}
	
	public void readAtomicPropositions() throws IOException
	{
		String fileName = System.getProperty("user.dir")+ "/../" +"Input/AP/atomic_prop.txt";
		File fe = new File(fileName);
		FileReader fr = new FileReader(fe);		
		BufferedReader br = new BufferedReader(fr);		
		String readline = null;
		
		int i = 0;		
		while((readline = br.readLine()) != null)
		{
			parseAtomicPropositions(i, readline);
			i=i+1;
		}		
		
		if(i < ConfigParameters.k_Value)
		{
			System.out.print("\n Error !! number of atomic propositions are less than k-attribute graphs");
			System.exit(1);
		}		
		fr.close();	
	}
		
	public void parseAtomicPropositions(int k, String readline)
	{

		if(k >= ConfigParameters.k_Value)
			return;

		String[] token = readline.split(" ");
		int i = 0;
		
		//System.out.print("\n");
		for(String str: token)
		{
			if(i < 2)
			{
				int c = Integer.parseInt(str);
				apCvalues[k][i] = c;
				
				if(c > ConfigParameters.num_People)
				{
					System.out.println("\nError!! apCvalues value is exceeding total agents");
					System.exit(1);
				}
				//System.out.print( " c" +i+ " :" + apCvalues[k][i]);
			}
			else
			{
				double d = Double.parseDouble(str);
				apDvalues[k][i-2] = d;
				
				if(d > 1)
				{
					System.out.println("\nError!! apDvalues value should be between [0, 1]");
					System.exit(1);
				}
				//System.out.print( " d" +(i-2)+ " :" + apDvalues[k][i-2]);
			}			
			i=i+1;
		}			
	}
	
	
	public boolean checkQuerySatisfied() 
	{
		boolean isQuerySatisfied = true;
		
		evaluateAtomicPropositions();	
		
		for(int k=0; k<ConfigParameters.k_Value; k++)
		{
			if(isAtomicPropTrue[k] == false)
			{
				isQuerySatisfied = false;
				break;
			}
		}		
		return isQuerySatisfied;
	}
	
	public void evaluateAtomicPropositions()
	{		
		for(int k=0; k<ConfigParameters.k_Value; k++)
		{
			isAtomicPropTrue[k] = checkAtomicPropositionSatisfied(k, apCvalues[k], apDvalues[k], SamplingOnKAttributeGraphs.samplePopulation[k]);
		}		
	}
	
	/*function to check the fraction of vertices with in random sample contains the given threshold of agents */
	public boolean checkAtomicPropositionSatisfied(int k, int [] cValues, double [] dValues, int samplePopulation[])
	{
				
		int count[] = new int[(ConfigParameters.num_Nodes)];
		
		for(int i=0; i<ConfigParameters.num_Nodes; i++)
			count[i] = 0;		
		
		for(int i=0; i<SamplingOnKAttributeGraphs.UnionNbrsList2K[k].size(); i++)
		{								
			int p = SamplingOnKAttributeGraphs.UnionNbrsList2K[k].get(i);
			int numAgents = Simulation.kAttrsNodes[k].get(p).people.size();
			//System.out.print("\nCity Id : " + p + " node level : " + SamplingOnKAttributeGraphs.nodeLevels[k][p] + " agent list: ") ;
			for(int j=0; j<numAgents; j++)
			{   
					int agentId = Simulation.kAttrsNodes[k].get(p).people.get(j);  //InitialSetup.kAdjList[k][j];					
					Agent agent = Simulation.population.get(agentId);
					int currVal = agent.attributes.get(k);
					count[currVal] = count[currVal] + 1;								
			}				
		}		
		
		boolean isAPTrue = false;
		int satNodes = 0;
			
		//System.out.println("Total Cities :" + SamplingOnKAttributeGraphs.UnionNbrsList1K[k].size());
			
		for(int i=0; i<SamplingOnKAttributeGraphs.UnionNbrsList1K[k].size(); i++)
		{
			int cityId = SamplingOnKAttributeGraphs.UnionNbrsList1K[k].get(i);			
			
			if((count[cityId] >= cValues[0])&&(count[cityId] <= cValues[1]))
			{
				satNodes = satNodes+1;
			}			
			//System.out.println("City Id " + cityId + " :" + count[cityId]);
		}
		
		//System.out.println("Number of Cities Satisfied : " + satNodes);
		
		double fraction = (double)satNodes/SamplingOnKAttributeGraphs.UnionNbrsList1K[k].size();;
		//System.out.println("Fraction : " + fraction);
		//System.exit(1);
		if((fraction >= dValues[0])&&(fraction <= dValues[1]))
		{
			isAPTrue = true;
		}
		Simulation.avgSatNodes[k] = Simulation.avgSatNodes[k] + satNodes;		
		return isAPTrue;
		
	}/* End of checkQuerySatisfied() */		
	
}
