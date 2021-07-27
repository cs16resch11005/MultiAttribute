import java.io.IOException;
import java.util.ArrayList;

public class InitialSetup
{
  public static ArrayList<Integer>[][] kAdjList;/* 2d array of list - adjacent lists of k attribute graphs*/

  @SuppressWarnings("unchecked")
  InitialSetup()
  {
	  kAdjList = new ArrayList[ConfigParameters.k_Value][];
	// kAdjList = new ArrayList[ConfigParameters.k_Value][ConfigParameters.num_Nodes];
  }
  
  @SuppressWarnings("unchecked")
  public void configure_Network_Topology() throws IOException
  {
	  if(ConfigParameters.is_Random_Graphs == 1)
	  {
		   // memory allocation  for adjacent list of k-attribute graphs
		  for(int k=0; k<ConfigParameters.k_Value; k++)
		  {
			    kAdjList[k] = new ArrayList[ConfigParameters.num_Nodes];			    
				for (int i=0; i<ConfigParameters.num_Nodes; i++)
				{ 
				  kAdjList[k][i] = new ArrayList<Integer>();			
				}		
	      }			  
		  GenerateKAttributeGraphs gkag = new GenerateKAttributeGraphs(ConfigParameters.num_Nodes);
		  gkag.generate_Network_Topology(kAdjList);
	  }
	  else
	  {
		  ReadKAttributeGraphs rkag = new ReadKAttributeGraphs();		  
		  for(int k=0; k<ConfigParameters.k_Value; k++)
		  {
			  rkag.read_Network_Topology(k, kAdjList);
		  }
	  }	  
  }

}
