
import java.util.*;
import java.lang.*;

public class Pair
{
 private ArrayList<Chromosome> PR_pop;
 //private ArrayList<ArrayList<Chromosome>> PR_pop_tour;
 private ArrayList<Integer> PR_pop_tour;

 public Pair(ArrayList<Chromosome> population)
    {
        PR_pop = population;
    }

 public int SimplePair() 
    {
        return (PR_pop.size() / 4);//the number of mating pairs
    }
 
 
 public void TournamentPair(ArrayList<Chromosome> PR_pop)
 	{
	 	ArrayList<Chromosome> copy = new ArrayList<Chromosome>();
	 	for(Chromosome c: PR_pop){
	 		copy.add(c);
	 	}
	 	
	 	//pairing algorithm	 	
	 	//add each chromosomes cost to new array
	 	ArrayList<Integer> costs = new ArrayList<Integer>();
	 	for(Chromosome y: copy){
	 		costs.add(y.GetCost());
	 	}
	 	
	 	//get the min and add index to index list
	 	ArrayList<Integer> indexes = new ArrayList<Integer>();
	 	for(int j = 0; j < costs.size(); j++){
	 		indexes.add(costs.indexOf(Collections.min(costs)));
	 		costs.set(costs.indexOf(Collections.min(costs)),99999999);
	 	}
	 	PR_pop_tour = indexes;
 	}
 
 
 public ArrayList<Chromosome> getPr_pop(){
	 return PR_pop;
 	}
 
 public ArrayList<Integer> getPR_pop_tour(){
	 return PR_pop_tour;
 	}
 }



