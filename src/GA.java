
import java.io.FileNotFoundException;

import java.lang.*;
import java.util.*;

public abstract class GA extends Object
{
 protected int     GA_numChromesInit;
 protected int     GA_numChromes;
 protected int     GA_numGenes;
 protected double  GA_mutFact;
 protected int     GA_numIterations;
 protected ArrayList<Chromosome> GA_pop;
 protected String GA_target;
 protected ArrayList<Integer> currCost;
 
 public GA(String ParamFile)
 {
     GetParams GP        = new GetParams(ParamFile);
     Parameters P        = GP.GetParameters();
     GA_numChromesInit   = P.GetNumChromesI();
     GA_numChromes       = P.GetNumChromes();
     GA_numGenes         = P.GetNumGenes();
     GA_mutFact          = P.GetMutFact();
     GA_numIterations    = P.GetNumIterations();
     GA_pop              = new ArrayList<Chromosome>();
     currCost 			 = new ArrayList<Integer>();
     }

 public GA(String ParamFile, String target)
    {
        GetParams GP        = new GetParams(ParamFile);
        Parameters P        = GP.GetParameters();
        GA_numChromesInit   = P.GetNumChromesI();
        GA_numChromes       = P.GetNumChromes();
        GA_numGenes         = P.GetNumGenes();
        GA_mutFact          = P.GetMutFact();
        GA_numIterations    = P.GetNumIterations();
        GA_pop              = new ArrayList<Chromosome>();
        GA_target           = target;
        }

 public void DisplayParams()
    {
        System.out.print("Initial Chromosomes:  ");
        System.out.println(GA_numChromesInit);
        System.out.print("Chromosomes: ");
        System.out.println(GA_numChromes);
        System.out.print("Genes: ");
        System.out.println(GA_numGenes);
        System.out.print("Mutation Factor: ");
        System.out.println(GA_mutFact);
        System.out.print("Iterations: ");
        System.out.println(GA_numIterations);
    }

 public void DisplayPop()
    {
        Iterator<Chromosome> itr = GA_pop.iterator();
        System.out.println("Number\tContents\t\tCost");
        
        int chromeNum = 0;
        while (itr.hasNext())
        {
            Chromosome chrome = itr.next();
            System.out.print(chromeNum);
            ++chromeNum;
            System.out.print("\t");
            DisplayChromosome(chrome);
            System.out.println();
        }
    }

 public void DisplayBest(int iterationCt)
    {
        Chromosome chrome = GA_pop.get(0);
        System.out.print("Iteration: ");
        System.out.print(iterationCt);
        System.out.print("\t");
        DisplayChromosome(chrome);
        System.out.println();
    }

 private void DisplayChromosome(Chromosome chrome)
        {
            chrome.DisplayGenes();
            System.out.print("\t\t\t");
            System.out.print(ComputeCost(chrome));
        }

 protected void SortPop()
    {
        Collections.sort(GA_pop, new CostComparator());
    }

 private class CostComparator implements Comparator <Chromosome>
    {
        int result;
        public int compare(Chromosome obj1, Chromosome obj2)
        {
        	int cost1 = ComputeCost(obj1);
        	int cost2 = ComputeCost(obj2);
        	
        	//System.out.println(cost1);
        	//System.out.println(cost2);
        	
            result = new Integer( cost1 ).compareTo(
            new Integer( cost2 ) );
            return result;
        }
    }

 protected void TidyUp()
    {
        int end = GA_numChromesInit - 1;
        while (GA_pop.size() > GA_numChromes)
        {
            GA_pop.remove(end);
            end--;
        }
    }

 protected void Mutate() 
    {
        int totalGenes  = (GA_numGenes * GA_numChromes);
        int numMutate   = (int) (totalGenes * GA_mutFact);
        Random rnum     = new Random();

        for (int i = 0; i < numMutate; i++) 
        {
            //position of chromosome to mutate--but not the first one
            //the number generated is in the range: [1..GA_numChromes)
            
            int chromMut = 1 + (rnum.nextInt(GA_numChromes - 1));
            
            // Method to ensure no duplicates:
            // Randomly choose 2 genes in the chromosome and swap them
            int geneMut1 = rnum.nextInt(GA_numGenes); //pos of first mutated gene
            int geneMut2 = rnum.nextInt(GA_numGenes); //pos of second mutated gene
            
            while(geneMut1 == geneMut2) {
            	geneMut2 = rnum.nextInt(GA_numGenes);
            }
            
            Chromosome newChromosome = GA_pop.remove(chromMut); //get chromosome
            char gene1 = newChromosome.GetGene(geneMut1);
            char gene2 = newChromosome.GetGene(geneMut2);
            
            newChromosome.SetGene(geneMut1, gene2);//mutate it by swapping genes
            newChromosome.SetGene(geneMut2, gene1);
            
            GA_pop.add(newChromosome); //add mutated chromosome at the end
        }
        
    }
 
 protected void InitPop()
    {
    
        Random rnum = new Random();
        char letter;
        
        for (int index = 0; index < GA_numChromesInit; index++)  //set to 128
        {
            Chromosome Chrom = new Chromosome(GA_numGenes);
            ArrayList<Character> curr = new ArrayList<Character>();         
            
            for (int j = 0; j < GA_numGenes; j++)
                { 
            		// need first 8 letters - a through h
            		int temp = rnum.nextInt(8) + 97;
            		if(temp < 97 || temp > 104){
            			//System.out.print("error");
            			temp = 97;
            		}
            		
            		int it = 0;
            		while(curr.contains((char)temp)){
            			temp = 97;
            			temp += it;
            			it+=1;
            		}
                    letter = (char) (temp); //97 is the value 'a' 
                    curr.add(letter);
                    Chrom.SetGene(j,letter);
                }
            Chrom.SetCost(0);
            GA_pop.add(Chrom);
        }
    }
 protected abstract void ComputeCost();

 //In earlier versions (as on ada) this is in WordGuess and is an abstract method here 
 //configured for double and tournament pairing currently
 protected void Evolve()
    {
        int iterationCt = 0;
        Pair pairs      = new Pair(GA_pop);
        pairs.TournamentPair(pairs.getPr_pop());
        ArrayList<Integer> pairs_tour = pairs.getPR_pop_tour();
        
        int numPairs    = pairs.SimplePair();
        
        boolean found   = false;

        int tempCost = 0;

        while (iterationCt < GA_numIterations)
            {
                Mate mate = new Mate(GA_pop,GA_numGenes,GA_numChromes);
                //GA_pop = mate.Crossover(GA_pop,numPairs);
                GA_pop = mate.DoubleCrossover(GA_pop,numPairs);
                //GA_pop = mate.Crossover(GA_pop,numPairs,pairs_tour);  //overload for tournament pairing
                //GA_pop = mate.DoubleCrossover(GA_pop,numPairs,pairs_tour);  //overload for tournament pairing
                Mutate();
                
                //ComputeCost();
                
                SortPop();
                
                Chromosome chrome = GA_pop.get(0); //get the best guess
                
                DisplayBest(iterationCt); //print it
                currCost.add(ComputeCost(chrome));

                //if newest is equal to the one 6 iterations ago, break
                if(currCost.size() > 11)
                {
	                if (currCost.get(currCost.size() - 11).equals(currCost.get(currCost.size()-1)))
	                {
	                    break;
	                }
                }
                ++iterationCt;
            }
    }
 
 
 
 protected int ComputeCost(Chromosome currentIt)
 {
	 	TSP tsp = null;
		try {
			tsp = new TSP();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 		int cost = 0;
	 	
         int[][] matrix = tsp.getMatrix();
         //System.out.println(currentIt.GetNumGenes());
         for (int j = 0; j < currentIt.GetNumGenes() - 1; j++) {
         	char gene1 = currentIt.GetGene(j);
         	char gene2 = currentIt.GetGene(j+1);
         	int index1 = (int)(gene1 - 'a'); // subtract to get numerical value
         	int index2 = (int)(gene2 - 'a');
         	//System.out.print(matrix.length);
         	//System.out.println(matrix[matrix.length-1].length);
         	if(index1 < 0 || index1 > 7){
         		System.out.println("i1 error");
         	}
         	if(index2 < 0 || index2 > 7){
         		System.out.println("i2 error");
         		System.out.println(gene2);
         	}
         	
         	cost += matrix[index1][index2];
         	
         }
         //System.out.println(cost);
     return cost;
 }

}

