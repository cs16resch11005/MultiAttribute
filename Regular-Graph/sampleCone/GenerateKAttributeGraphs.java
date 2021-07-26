import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class GenerateKAttributeGraphs 
{
	public int numNodes;
	public int numEdges;	
	//public static ArrayList<Integer>[][] kAdjList;/* 2d array of list - adjacent lists of k attribute graphs*/
	public List<List<Integer> > adjList;

	/* constructor */
	GenerateKAttributeGraphs(int nodes)
	{
		numNodes = ConfigParameters.num_Nodes;
		numEdges = (int) (1.1*ConfigParameters.num_Nodes);
		//System.out.println("num Edges : " + numEdges );
				
	}		

	public void generate_Network_Topology(ArrayList<Integer>[][] kAdjList)
	{
		//System.out.println("Entered");
		for(int k=0; k<ConfigParameters.k_Value; k++)
		{
			 createRandomGraph();
			 copyRandomGraph(k, kAdjList);			
		}
	}
	
    
	public void copyRandomGraph(int k, ArrayList<Integer>[][] kAdjList) 
	{
		int count=0;
		for(int i=0; i<numNodes;i++)
		{
			Collections.sort(adjList.get(i));
			kAdjList[k][i].addAll(adjList.get(i));
			count = count + adjList.get(i).size();
		}
		
		/*System.out.println(k+"-th Random Graph; Total Degree : " + count);
		for(int i=0; i<numNodes;i++)
		{
			System.out.println(i+" :"+ kAdjList[k][i]);
		}*/
		
	}

	public void createRandomGraph()
	{		
		adjList = new ArrayList<>(numNodes);
		
        for (int i = 0; i < numNodes; i++)
        	adjList.add(new ArrayList<>());
        
        for (int i = 0; i < numNodes-1; i++)
        	 addEdge(i, i+1);
        
        Random randum = new Random();
        
        for (int i = numNodes-1; i<numEdges; ) {            
           
        	int v = randum.nextInt(numNodes);
            int w = randum.nextInt(numNodes);  
            
            // add an edge between them            
            if(!checkEdgeExist(v,w))
            {	
            	addEdge(v, w);
            	i = i+1;           
            }
        }
	}
		
	boolean checkEdgeExist(int s, int d)
	{
		if(adjList.get(s).contains(d))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	 
    void addEdge(int v, int w)
    {
    	if(v == w)
    	{
    		adjList.get(v).add(w);
    	} 
    	else
    	{    	
    		// Add w to v's adjacency list
    		adjList.get(v).add(w);  
    		// Add v to w's adjacency list
    		adjList.get(w).add(v);
    	}
    }
  
 

	
}
