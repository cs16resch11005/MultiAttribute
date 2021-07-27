import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.PrintStream;

public class testGenericSMC
{
	public static long totalSATCities[];
	public static long totalSampleCities[];
	
	/* calculate number of times simulation to be run using estimation method */
	public int calNumOfSimulations(double ci, double epsilon)
	{
		int num_sims = 0;
		epsilon = 2*epsilon*epsilon;
		ci = Math.log(2/ci);
		num_sims = (int)Math.ceil(ci/epsilon);
		return num_sims;
	}

	public static void main(String args[]) throws Exception
	{
		/*System.setOut(new PrintStream(new OutputStream() {
			public void write(int b){  NO-OP  }
		}));*/		 

		String outFile = System.getProperty("user.dir")+ "/../" +"Output/output.txt";
		String resFile = System.getProperty("user.dir")+ "/../" +"Output/results.txt";
		System.setOut(new PrintStream(new FileOutputStream(outFile)));		
		
		File fe = new File(resFile);    
		if(fe.exists())
			fe.delete();

		File configFile =  new File(System.getProperty("user.dir")+ "/../" +"Config/config.properties");
		ConfigParameters cp = new ConfigParameters(configFile);
		cp.read_configFile();		
		
		
		InitialSetup is = new InitialSetup();
		is.configure_Network_Topology();
		
		totalSATCities = new long[ConfigParameters.k_Value];
		totalSampleCities = new long[ConfigParameters.k_Value];
		
		long startTime = System.currentTimeMillis();

		testGenericSMC th = new testGenericSMC();
		int numRuns   = th.calNumOfSimulations(ConfigParameters.val_Ci, ConfigParameters.val_Epsilon);
		int numSucess  = 0;
		int numFailure = 0;		
		
		System.out.println("\nNum of Runs : " + numRuns);

		//System.exit(1);

		for(int j=0; j<numRuns; j++)
		{			
			Simulation sm = new Simulation(j);
			sm.preConfiguration();
			
			if(Simulation.isQuerySatisfied)
			{
				numSucess  = numSucess + 1;
			}
			else
			{
				numFailure = numFailure + 1;
			}
			
			FileWriter fw =  new FileWriter(resFile, true);
			//System.out.println("Run Id : " + j);	

			fw.write("\nRun Id : " + j + "  " + Simulation.isQuerySatisfied);
			for(int k=0; k<ConfigParameters.k_Value; k++)
			{
				totalSATCities[k] = totalSATCities[k] + Simulation.avgSatNodes[k];
				totalSampleCities[k] = totalSampleCities[k] + SamplingOnKAttributeGraphs.UnionNbrsList1K[k].size();
				fw.write(" " +Simulation.avgSatNodes[k] + " " +SamplingOnKAttributeGraphs.UnionNbrsList1K[k].size());
				//System.out.println(Simulation.avgSatNodes[k] + " " + SamplingOnKAttributeGraphs.UnionNbrsList1K[k].size());
			}
			fw.write(" "+Simulation.currentTime);		
			fw.close();				
			sm.postConfiguration();
		}

		//System.exit(1);
		int totalRuns = numFailure+numSucess;

		FileWriter fw =  new FileWriter(resFile, true);
		fw.write("\nnum Sucess  : " + numSucess +"\tnum Failure : " + numFailure);
		fw.write("\nProbability of given query has been satisfied : " + (double)numSucess/totalRuns+"\n");
		long endTime = System.currentTimeMillis();
		long duration = (endTime - startTime);  //Total execution time in milli seconds
		
		for(int k=0; k<ConfigParameters.k_Value; k++)
		{
			double satFraction = (double)totalSATCities[k]/(totalSampleCities[k]); //ConfigParameters.sample_Size); 
			fw.write("\nFraction of vertices having specified agents in " + k + "-attribute graph : " +satFraction+"\n");
			System.out.println("Fraction of vertices having specified agents in " + k + "-attribute graph : " +satFraction);	
		}	
		System.out.println("Execution Time testGenericSMC:" + duration);
		fw.write("\nExecution Time testGenericSMC:" + duration);
		fw.close();
		
		for(int k=0; k<ConfigParameters.k_Value; k++)
		{
			double satFraction = (double)totalSATCities[k]/(totalSampleCities[k]);  
			System.err.println("Fraction of vertices having specified agents in  " + k + "-attribute graph : " +satFraction+"\n");	
		}	
		
		System.err.println("Probability of given query has been satisfied : " + (double)numSucess/totalRuns+"\n");
		System.err.println("Execution Time testGenericSMC(in milli seconds) : " + duration);
		
		//System.out.println(duration);

	}
}
