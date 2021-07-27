import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ReadKAttributeGraphs 
{
	
	ReadKAttributeGraphs()
	{
		
	}	
	
	@SuppressWarnings("unchecked")
	public void read_Network_Topology(int k, ArrayList<Integer>[][] kAdjList) throws IOException
	{
		String fileName = k+".txt";		
		File fe = new File(fileName);
		FileReader fr = new FileReader(fe);		
		BufferedReader br = new BufferedReader(fr);		
		String readline  = br.readLine();
		int nodes = Integer.parseInt(readline);		
		//System.out.println("Nodes : " + nodes);
		
		kAdjList[k] = new ArrayList[nodes];
		
		int i = 0;		
		while((readline = br.readLine()) != null)
		{
			parseAdjacentListOfNode(k, i, readline, kAdjList);
			i=i+1;
		}		
		
		if(nodes != i)
		{
			System.out.print("\n Error !! number of adjacent lists are not equals to nodes in " + k +"-attribute graph");
			System.exit(1);
		}
		fr.close();			
	}
	
	
	public void parseAdjacentListOfNode(int k, int i, String readline, ArrayList<Integer>[][] kAdjList)
	{
		String[] token = readline.split(" ");
		int count = 0;
		//System.out.print("\n");
		for(String str: token)
		{
			int pos = Integer.parseInt(str);
			kAdjList[k][i].add(pos);
			//System.out.print(kAdjList[k][i].get(count) + " ");
			count = count +1;
		}	
	}/* End of parsePositionOfPeople()*/	
	
}
