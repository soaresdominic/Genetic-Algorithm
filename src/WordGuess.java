import java.util.*;
import java.io.FileNotFoundException;
import java.lang.*;


public class WordGuess extends GA
{
 private String WG_target;

 public WordGuess(String fileName, String target)
    {
        super(fileName,target);
        WG_target = new String(target);
        GA_numGenes = WG_target.length();
            if (WG_target.length() != GA_numGenes)
            {
                System.out.println("Error: Target size differs from number of genes");
                DisplayParams();
                System.exit(1);
            }

        InitPop();
    }


 public void InitPop()
    {
        super.InitPop();
        ComputeCost();
        SortPop();
        TidyUp();
    }

 public void DisplayParams()
    {
        System.out.print("Target: ");
        System.out.println(WG_target);
        super.DisplayParams();
    }

 protected void ComputeCost()
    {
	 	TSP tsp = null;
	 	
	 	// Create a new TSP to access matrix of costs
		try {
			tsp = new TSP();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	 	
        for (int i = 0; i < GA_pop.size(); i++)
        {
            int cost = 0;
            Chromosome chrom = GA_pop.remove(i);
            int[][] matrix = tsp.getMatrix();
            for (int j = 0; j < chrom.GetNumGenes() - 1; j++) {
            	char gene1 = chrom.GetGene(j);
            	char gene2 = chrom.GetGene(j+1);
            	int index1 = (int)(gene1 - 'a'); // subtract to get numerical value
            	int index2 = (int)(gene2 - 'a');
            	cost += matrix[index1][index2];
            	
            }
            chrom.SetCost(cost);
            GA_pop.add(i,chrom);
        }
    }
 //in earlier versions (as on ada) Evolve() from GA is here
 }

