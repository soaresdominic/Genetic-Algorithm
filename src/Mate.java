import java.util.*;
import java.lang.*;

public class Mate
{
 private    Chromosome MT_father, MT_mother, MT_child1, MT_child2;
 private    int MT_posChild1, MT_posChild2, MT_posLastChild,MT_posFather, MT_posMother,
             MT_numGenes, MT_numChromes;

 public Mate(ArrayList<Chromosome> population, int numGenes, int numChromes)
    {
        MT_numGenes     = numGenes;
        MT_numChromes   = numChromes;
        
        MT_posChild1    = population.size()/2;
        MT_posChild2    = MT_posChild1 + 1;
        MT_posLastChild= population.size() - 1;
        
        for (int i = MT_posLastChild; i >= MT_posChild1; i--)
            population.remove(i);
        
        MT_posFather = 0;
        MT_posMother = 1;
    }
 //Simple Top-Down Pairing
 public ArrayList<Chromosome> Crossover(ArrayList<Chromosome> population, int numPairs)
    {
        for (int j = 0; j < numPairs; j++)
        {
            MT_father       =  population.get(MT_posFather);
            MT_mother       =  population.get(MT_posMother);
            MT_child1       = new Chromosome(MT_numGenes);
            MT_child2       = new Chromosome(MT_numGenes);
            Random rnum     = new Random();
            int crossPoint  = rnum.nextInt(MT_numGenes);

            //left side
            for (int i = 0; i < crossPoint; i++)
                {
                    MT_child1.SetGene(i,MT_father.GetGene(i));
                    MT_child2.SetGene(i,MT_mother.GetGene(i));
                }
    
            //right side 
            for (int i = crossPoint; i < MT_numGenes; i++)
                {
                    MT_child1.SetGene(i, MT_mother.GetGene(i));
                    MT_child2.SetGene(i, MT_father.GetGene(i));
                }
                
            ArrayList<Chromosome> newPopulation = PMX(population);
            population = newPopulation;
            
            MT_posChild1    = MT_posChild1 + 2;
            MT_posChild2    = MT_posChild2 + 2;
            MT_posFather    = MT_posFather + 2;
            MT_posMother    = MT_posMother + 2;
        }
        return population;
    }
 
 // Partially Matched Crossover Algorithm - ensures no duplicates
 public ArrayList<Chromosome> PMX(ArrayList<Chromosome> population)
 {
	 // Generate random crosspoints
	 Random rnum     = new Random();
     int crossPoint  = rnum.nextInt(MT_numGenes);
     int crossPoint2  = rnum.nextInt(MT_numGenes);

     //switch so 1 is < 2
     if(crossPoint > crossPoint2){
     	int temp = crossPoint2;
     	crossPoint2 = crossPoint;
     	crossPoint = temp;
     }
     
     // First switch parent genes between the crosspoints
     for(int i = crossPoint; i <= crossPoint2; i++) {
    	 MT_child1.SetGene(i, MT_mother.GetGene(i));
    	 MT_child2.SetGene(i, MT_father.GetGene(i));
     }
     
     // Arrays with values matching up to the number of instances of each gene
     int[] duplicates1 = new int[MT_numGenes];
     int[] duplicates2 = new int[MT_numGenes];
     Arrays.fill(duplicates1, 0);
     Arrays.fill(duplicates2, 0);
     for(int i = 0; i < MT_numGenes; i++) {
    	 duplicates1[(int)(MT_child1.GetGene(i) - 'a')] += 1;
    	 duplicates2[(int)(MT_child2.GetGene(i) - 'a')] += 1;
     }
     
     for(int i = 0; i < MT_numGenes; i++) {
    	 // Leaves values in between crosspoints as is
    	 if (i < crossPoint || i > crossPoint2) {
    		 char gene1 = MT_child1.GetGene(i);
    		 if(duplicates1[(int)(gene1 - 'a')] > 1) {
    			 // Gene needs to be swapped with child2
    			 for(int j = 0; j < MT_numGenes; j++) {
    				 if (j < crossPoint || j > crossPoint2) {
    					 char gene2 = MT_child2.GetGene(j);
    					 if(duplicates2[(int)(gene2 - 'a')] > 1) {
    						 // Swap the genes
    						 MT_child1.SetGene(i, gene2);
    						 MT_child2.SetGene(j, gene1);
    						 // Update duplicate arrays
    						 duplicates1[(int)(gene1 - 'a')] -= 1;
    						 duplicates2[(int)(gene2 - 'a')] -= 1;
    						 break;
    					 }
    				 }
    			 }	 
    		 }
    	 }
     }
     population.add(MT_posChild1,MT_child1);
     population.add(MT_posChild2,MT_child2); 
	 
	 return population;
 }
 
 
 //tournament Pairing
 public ArrayList<Chromosome> Crossover(ArrayList<Chromosome> population, int numPairs, ArrayList<Integer> pairs_tour)
    {
        for (int j = 0; j < numPairs; j++)
        {
            MT_father       =  population.get(pairs_tour.get(MT_posFather)); //get top of tournament pairs
            MT_mother       =  population.get(pairs_tour.get(MT_posMother));
            
            MT_child1       = new Chromosome(MT_numGenes);
            MT_child2       = new Chromosome(MT_numGenes);
            Random rnum     = new Random();
            int crossPoint  = rnum.nextInt(MT_numGenes);

            //left side
            for (int i = 0; i < crossPoint; i++)
                {
                    MT_child1.SetGene(i,MT_father.GetGene(i));
                    MT_child2.SetGene(i,MT_mother.GetGene(i));
                }
    
            //right side 
            for (int i = crossPoint; i < MT_numGenes; i++)
                {
                    MT_child1.SetGene(i, MT_mother.GetGene(i));
                    MT_child2.SetGene(i, MT_father.GetGene(i));
                }
                
            ArrayList<Chromosome> newPopulation = PMX(population);
            population = newPopulation;
            
            MT_posChild1    = MT_posChild1 + 2;
            MT_posChild2    = MT_posChild2 + 2;
            MT_posFather    = MT_posFather + 2;
            MT_posMother    = MT_posMother + 2;
        }
        return population;
    }
 
 
 //Simple Top-Down Pairing
 public ArrayList<Chromosome> DoubleCrossover(ArrayList<Chromosome> population, int numPairs)
    {
        for (int j = 0; j < numPairs; j++)
        {
            MT_father       =  population.get(MT_posFather); //where top-down pairing is happening
            MT_mother       =  population.get(MT_posMother);
            
            MT_child1       = new Chromosome(MT_numGenes);
            MT_child2       = new Chromosome(MT_numGenes);
            Random rnum     = new Random();
            int crossPoint  = rnum.nextInt(MT_numGenes);
            int crossPoint2  = rnum.nextInt(MT_numGenes);

            //switch so 1 is < 2
            if(crossPoint > crossPoint2){
            	int temp = crossPoint2;
            	crossPoint2 = crossPoint;
            	crossPoint = temp;
            }
            
            //red father, blue mother
            //left side
            for (int i = 0; i < crossPoint; i++)
                {
                    MT_child1.SetGene(i,MT_father.GetGene(i));
                    MT_child2.SetGene(i,MT_mother.GetGene(i));
                }
    
            //middle
            for (int i = crossPoint; i < crossPoint2; i++)
                {
                    MT_child1.SetGene(i, MT_mother.GetGene(i));
                    MT_child2.SetGene(i, MT_father.GetGene(i));
                }
            
            //right side
            for (int i = crossPoint2; i < MT_numGenes; i++)
                {
                    MT_child1.SetGene(i, MT_father.GetGene(i));
                    MT_child2.SetGene(i, MT_mother.GetGene(i));
                }
                
            ArrayList<Chromosome> newPopulation = PMX(population);
            population = newPopulation;
            
            MT_posChild1    = MT_posChild1 + 2;
            MT_posChild2    = MT_posChild2 + 2;
            MT_posFather    = MT_posFather + 2;
            MT_posMother    = MT_posMother + 2;
        }
        return population;
    }
 
 //tournament Pairing
 public ArrayList<Chromosome> DoubleCrossover(ArrayList<Chromosome> population, int numPairs, ArrayList<Integer> pairs_tour)
 {
     for (int j = 0; j < numPairs; j++)
     {
         MT_father       =  population.get(pairs_tour.get(MT_posFather)); //get top of tournament pairs
         MT_mother       =  population.get(pairs_tour.get(MT_posMother));
         
         MT_child1       = new Chromosome(MT_numGenes);
         MT_child2       = new Chromosome(MT_numGenes);
         Random rnum     = new Random();
         int crossPoint  = rnum.nextInt(MT_numGenes);
         int crossPoint2  = rnum.nextInt(MT_numGenes);

         //switch so 1 is < 2
         if(crossPoint > crossPoint2){
         	int temp = crossPoint2;
         	crossPoint2 = crossPoint;
         	crossPoint = temp;
         }
         
         //red father, blue mother
         //left side
         for (int i = 0; i < crossPoint; i++)
             {
                 MT_child1.SetGene(i,MT_father.GetGene(i));
                 MT_child2.SetGene(i,MT_mother.GetGene(i));
             }
 
         //middle
         for (int i = crossPoint; i < crossPoint2; i++)
             {
                 MT_child1.SetGene(i, MT_mother.GetGene(i));
                 MT_child2.SetGene(i, MT_father.GetGene(i));
             }
         
         //right side
         for (int i = crossPoint2; i < MT_numGenes; i++)
             {
                 MT_child1.SetGene(i, MT_father.GetGene(i));
                 MT_child2.SetGene(i, MT_mother.GetGene(i));
             }
             
         ArrayList<Chromosome> newPopulation = PMX(population);
         population = newPopulation;
         
         MT_posChild1    = MT_posChild1 + 2;
         MT_posChild2    = MT_posChild2 + 2;
         MT_posFather    = MT_posFather + 2;
         MT_posMother    = MT_posMother + 2;
     }
     return population;
 }
 }
