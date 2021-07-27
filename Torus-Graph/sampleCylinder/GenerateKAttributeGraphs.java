import java.util.ArrayList;
import java.util.Collections;

public class GenerateKAttributeGraphs 
{
	public int rows;
	public int columns;	
	//public static ArrayList<Integer>[][] kAdjList;/* 2d array of list - adjacent lists of k attribute graphs*/
	 
	/* constructor */
	GenerateKAttributeGraphs(int nodes)
	{
		rows = (int) Math.sqrt(nodes);
		columns = rows;
		
		if(nodes != (rows*columns))
		{
			System.out.println("\nNumber of nodes are not perfect square");
			System.exit(1);
		}			
	}		

	public void generate_Network_Topology(ArrayList<Integer>[][] kAdjList)
	{
		//System.out.println("Entered");
		for(int k=0; k<ConfigParameters.k_Value; k++)
		{
			selfLoop2dGridGraph(k, kAdjList);
			generateGrid2dGraph(k, kAdjList);			
		}
	}
	
	/*This function generates the 2d grid graph with self loop*/
	public void selfLoop2dGridGraph(int k, ArrayList<Integer>[][] kAdjList)
	{
		for(int i=0; i<rows*columns; i++)
		{
			kAdjList[k][i].add(i);
		}
	}
	
	/*This function generates the 2d grid graph */
	public void generateGrid2dGraph(int k, ArrayList<Integer>[][] kAdjList)
	{
		int nodeId = 0;

		for(int i=0; i<rows; i++)
		{		
			if(i == 0)
			{		
				for(int j=0; j<columns; j++)
				{
					nodeId = (i*columns) + j;

					if(j == 0)
					{
						kAdjList[k][nodeId].add(nodeId+1);
						kAdjList[k][nodeId].add(nodeId+columns-1);
						kAdjList[k][nodeId].add(nodeId+columns);
						kAdjList[k][nodeId].add(nodeId+(rows-1)*columns);
					}
					else if(j == columns-1)
					{
						kAdjList[k][nodeId].add(nodeId-columns+1);
						kAdjList[k][nodeId].add(nodeId-1);
						kAdjList[k][nodeId].add(nodeId+columns);
						kAdjList[k][nodeId].add(nodeId+(rows-1)*columns);
					}
					else
					{
						kAdjList[k][nodeId].add(nodeId-1);
						kAdjList[k][nodeId].add(nodeId+1);
						kAdjList[k][nodeId].add(nodeId+columns);
						kAdjList[k][nodeId].add(nodeId+(rows-1)*columns);
					}
				}
			}
			else if(i == rows-1)
			{

				for(int j=0; j<columns; j++)
				{
					nodeId = (i*columns) + j;
					if(j == 0)
					{
						kAdjList[k][nodeId].add(nodeId-(rows-1)*columns);
						kAdjList[k][nodeId].add(nodeId-columns);
						kAdjList[k][nodeId].add(nodeId+1);
						kAdjList[k][nodeId].add(nodeId+columns-1);						
					}
					else if(j == columns-1)
					{
						kAdjList[k][nodeId].add(nodeId-(rows-1)*columns);
						kAdjList[k][nodeId].add(nodeId-columns);
						kAdjList[k][nodeId].add(nodeId-columns+1);
						kAdjList[k][nodeId].add(nodeId-1);
						
					}
					else
					{
						kAdjList[k][nodeId].add(nodeId-columns);
						kAdjList[k][nodeId].add(nodeId-1);
						kAdjList[k][nodeId].add(nodeId+1);
						kAdjList[k][nodeId].add(nodeId-(rows-1)*columns);
					}
				}
			}
			else
			{
				for(int j=0; j<columns; j++)
				{
					nodeId = (i*columns) + j;
					if(j == 0)
					{
						kAdjList[k][nodeId].add(nodeId-columns);
						kAdjList[k][nodeId].add(nodeId+1);
						kAdjList[k][nodeId].add(nodeId+columns-1);
						kAdjList[k][nodeId].add(nodeId+columns);
					}
					else if(j == columns-1)
					{
						kAdjList[k][nodeId].add(nodeId-columns);
						kAdjList[k][nodeId].add(nodeId-columns+1);
						kAdjList[k][nodeId].add(nodeId-1);
						kAdjList[k][nodeId].add(nodeId+columns);
					}
					else
					{
						kAdjList[k][nodeId].add(nodeId-columns);
						kAdjList[k][nodeId].add(nodeId-1);
						kAdjList[k][nodeId].add(nodeId+1);
						kAdjList[k][nodeId].add(nodeId+columns);
					}
				}
			}			
		}		

		for(int i=0; i<rows*columns; i++)
		{
			Collections.sort(kAdjList[k][i]);
			
			/*if(kAdjList[k][i].size() <= 5)
			{
				System.out.println( "Node Id : "+ i + " --> " + kAdjList[k][i]);
			}
			else
			{
				System.out.println( "Error!! Node has more than 4 Neighbors; Node Id : "+ i + " --> " + kAdjList[i]);
				System.exit(1);
			}*/
		}
	}/*End of the generate_grid_2d_graph() */

	
}