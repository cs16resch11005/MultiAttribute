import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.Set;

public class SamplingOnKAttributeGraphs 
{
	@SuppressWarnings("unchecked")
	public  static ArrayList<Integer> UnionNbrsList1K[] = new ArrayList[ConfigParameters.k_Value];
    @SuppressWarnings("unchecked")
	public static ArrayList<Integer> UnionNbrsList2K[] = new ArrayList[ConfigParameters.k_Value];

    public static int nodeLevels[][] = new int[ConfigParameters.k_Value][ConfigParameters.num_Nodes];	
    public static int samplePopulation[][] = new int[ConfigParameters.k_Value][];	
    public  int sampleNodes[][] = new int[ConfigParameters.k_Value][ConfigParameters.sample_Size];
    
	SamplingOnKAttributeGraphs()
	{
		
	}

	public void chooseSamplesOnKattributeGraphs()
	{
		for(int k=0; k<ConfigParameters.k_Value; k++)
		{
			generateSampleOnKthAttribteGraph(k);						
		}
	}
	
	public void printNodeLevels()
	{
		for(int k=0; k<ConfigParameters.k_Value; k++)
		{
			findAgentsInKthAttributeSample(k);
			System.out.println("nodeId --> Level");		
			for(int i=0; i<UnionNbrsList2K[k].size(); i++)
			{			
				int cityid = UnionNbrsList2K[k].get(i);
				System.out.println(cityid + " " + nodeLevels[k][cityid]);
			}
			System.out.println("Total nodes in sample "+k+" : " + UnionNbrsList2K[k].size()+ " agents :" +samplePopulation[k].length +"\n");			
			
		}		
	}/*End of the printNodeLevels()*/
	
	public int calculateAgentsInKthAttributeSample(int k)
	{
		int sum=0;		
		for(int i=0; i<UnionNbrsList2K[k].size(); i++)
		{
		    int cityId = UnionNbrsList2K[k].get(i);			
		    int strength = Simulation.kAttrsNodes[k].get(cityId).people.size();
			sum = sum + strength;
		}		
	 	return sum;	
	}
	
	public void findAgentsInKthAttributeSample(int k)
	{
		int count = 0;		
	
		//System.out.println("Total People : " + unionNbrsList2K.size()*avgPopCity);

		int maxSampleSize = calculateAgentsInKthAttributeSample(k); //(int)Math.round(unionNbrsList2K.size()*avgPopCity*1.1);
		
	//	System.out.println("Expected People  in Location: " + maxSampleSize);

		samplePopulation[k] = new int[maxSampleSize];	
		
		//System.out.print("\n Union list size : " + UnionNbrsList2K[k].size() + "\n");		
		
		for(int i=0; i<UnionNbrsList2K[k].size(); i++)
		{
			int cityId = UnionNbrsList2K[k].get(i);			
		  // 	System.out.print("\n");
			int strength = Simulation.kAttrsNodes[k].get(cityId).people.size();
			
			for(int j=0; j<strength; j++)
			{				
				samplePopulation[k][count] = Simulation.kAttrsNodes[k].get(cityId).people.get(j);				
				//System.out.print(samplePopulation[k][count] + " ");				
				count = count + 1;
			}					
			//System.out.print("\n");			
		}		

	}/*End of samplePopulation() */
	
	
	@SuppressWarnings("unchecked")
	public void generateSampleOnKthAttribteGraph(int k)
	{
		ArrayList<Integer>[] nbrList = new ArrayList[(ConfigParameters.sample_Size)];		
		//System.out.println("Generate Sample of Nodes");
		//System.out.println();
		for(int i=0; i<ConfigParameters.sample_Size; i++)
		{
			Random randum = new Random();
			int source = randum.nextInt(ConfigParameters.num_Nodes);
			sampleNodes[k][i] = source;			
			//System.out.print(source + " ");
			nbrList[i] = findAllNodesAtDistanceK(k, source, ConfigParameters.sim_Time);
		}
		//System.out.println();

		Set<Integer> set = new HashSet<Integer>();		
		for(int i=0; i<ConfigParameters.sample_Size; i++)
		{
			set.addAll(nbrList[i]);
		}   
		
		UnionNbrsList1K[k] = new ArrayList<Integer>(set);
		Collections.sort(UnionNbrsList1K[k]);
		//System.out.println("Union of Neighbors List Size : " + unionNbrsList1K.size());
		//System.out.println("Union of 1KNeighbors List : " + unionNbrsList1K +" size : "+ unionNbrsList1K.size());
		generateSampleOnKthAttribteGraph2(k);
	}
	
	@SuppressWarnings("unchecked")
	public void generateSampleOnKthAttribteGraph2(int k)
	{
		ArrayList<Integer>[] nbrList = new ArrayList[(ConfigParameters.sample_Size)];		
		//System.out.println("Generate Sample of Nodes");
		//System.out.println();
		for(int i=0; i<ConfigParameters.sample_Size; i++)
		{
			//nbrList[i] = new ArrayList<Integer>();
			//Random randum = new Random();
			int source = sampleNodes[k][i] ; //randum.nextInt(ConfigParameters.num_Nodes);
			//System.out.print(source + " ");
			nbrList[i] = findAllNodesAtDistance2K(k, source, 2*ConfigParameters.sim_Time, nodeLevels);
			
		}
		//System.out.println();
		Set<Integer> set = new HashSet<Integer>();		
		for(int i=0; i<ConfigParameters.sample_Size; i++)
		{
			set.addAll(nbrList[i]);
		}   		
		UnionNbrsList2K[k] = new ArrayList<Integer>(set);
		Collections.sort(UnionNbrsList2K[k]);
		//System.out.println("Union of Neighbors List Size : " + unionNbrsList2K.size());
		//System.out.println("Union of 2KNeighbors List : " + unionNbrsList2K +" size : "+ unionNbrsList2K.size());		
	}
	
	
	/* This function finds all the nodes reachable from source node with in distance K */
	public ArrayList<Integer> findAllNodesAtDistance2K(int k, int source, int distance, int [][] nodeLevels)
	{
		// array to store level of each node  
		int level[]  = new int[(InitialSetup.kAdjList[k].length)];  
		boolean marked[] = new boolean[(InitialSetup.kAdjList[k].length)];	    
		//int root = source;
		
		ArrayList<Integer> nbrList  = new ArrayList<Integer>();		
		Queue<Integer> queue = new LinkedList<Integer>(); 
		queue.add(source);		
		level[source] = 0;  // initialize level of source node to 0
		nodeLevels[k][source] = 0;
		marked[source] = true;	// marked it as visited  
		//System.out.println("\n Node Id --> Level\n");  
  
		while (queue.size() > 0)  
		{  
			source = queue.peek();
			nbrList.add(source);
			//System.out.println("  " +source +" --> " + level[source] );  
			queue.remove();  
			
			for (int i = 0; i < InitialSetup.kAdjList[k][source].size(); i++)  
			{  
				int b = InitialSetup.kAdjList[k][source].get(i); // b is neighbor of node source
				if((!marked[b]) && (level[source]<distance)) // if b is not marked already
				{  
					queue.add(b); // enqueue b in queue    
					level[b] = level[source] + 1; // level of b is level of x + 1  
					if(nodeLevels[k][b] > level[b])
					{
						//if(cityLevels[b] != (2*ConfigParameters.sim_Time))
						//{	System.out.println("Enter Node Id :" + b + " Current level :" + cityLevels[b] + " Next level :" + level[b]);}
						
						nodeLevels[k][b]=level[b];
					}						
					marked[b] = true; // mark b 
				}  
			}  
		}	  
		//System.out.println("Node : "+ root + " total neighbors :" + nbrList.size());
		return nbrList;
	}/* End of the findAllNodesAtDistanceK() */
	
	
	/* This function finds all the nodes reachable from source node with in distance K */
	public ArrayList<Integer> findAllNodesAtDistanceK(int k, int source, int distance)
	{
		// array to store level of each node  
		int level[]  = new int[(InitialSetup.kAdjList[k].length)];  
		boolean marked[] = new boolean[((InitialSetup.kAdjList[k].length))];	    
		//int root = source;
		
		ArrayList<Integer> nbrList  = new ArrayList<Integer>();		
		Queue<Integer> queue = new LinkedList<Integer>(); 
		queue.add(source);		
		level[source] = 0;  // initialize level of source node to 0  
		marked[source] = true;	// marked it as visited  
		//System.out.println("\n Node Id --> Level\n");  
  
		while (queue.size() > 0)  
		{  
			source = queue.peek();
			nbrList.add(source);
			//System.out.println("  " +source +" --> " + level[source] );  
			queue.remove();  
			
			for (int i = 0; i < InitialSetup.kAdjList[k][source].size(); i++)  
			{  
				int b = InitialSetup.kAdjList[k][source].get(i); // b is neighbor of node source
				if((!marked[b]) && (level[source]<distance)) // if b is not marked already
				{  
					queue.add(b); // enqueue b in queue    
					level[b] = level[source] + 1; // level of b is level of x + 1    
					marked[b] = true; // mark b 
				}  
			}  
		}	  
		//System.out.println("Node : "+ root + " total neighbors :" + nbrList.size());
		return nbrList;
	}/* End of the findAllNodesAtDistanceK() */
	
	
}
