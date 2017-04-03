/*  SetParams creates the parameter file. args[0] is its name.
    Here is a reasonable set of command line arguments:
    java SetParams params.dat 128 64 5 .1 1000
    where   128  = the size of the initial population
            64   = the size of the working population
            5    = the number of genes.  In this case, the number of letters in the word to be guessed
            .1   = the mutation factor
            1000 = the number of generations; stop after this number of generations if the word has 
                    not been guessed.
*/

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.*;
import java.util.Scanner;

public class SetParams
{
	public static void main(String args[]) throws FileNotFoundException
	    {
	        int     numChromesI = Integer.parseInt(args[1]);
	        int     numChromes   = Integer.parseInt(args[2]);
	        int     numGenes    = Integer.parseInt(args[3]);
	        double  mutFact     = Double.parseDouble(args[4]);
	        int     numIters    = Integer.parseInt(args[5]);
	        int[][] matrix 		= createMatrix();
	        GetParams GP        = new GetParams(args[0],numChromesI,numChromes,numGenes,mutFact,numIters,matrix);
	        //args[0] is the name of the parameter file
	    }
	
	public static int[][] createMatrix() throws FileNotFoundException {
		int[][] matrix = new int[8][8];
		Scanner scanner = new Scanner(new File("data.txt"));
		
		int x = 0;
		while(scanner.hasNextLine()){
			String s = scanner.nextLine();
			String[] eachNum = s.split(",");
			for(int i = 0; i < eachNum.length; i++){
				matrix[x][i] = Integer.parseInt(eachNum[i]);
			}
			x+=1;
		}
		scanner.close();
		return matrix;
	}
}

