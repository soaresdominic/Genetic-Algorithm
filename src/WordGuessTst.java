/* 
 * CPSC 427 02
 * Assignment #11: TSP/Genetic Algorithm
 * Team Leader: Trevor Farthing (tfarthing)
 * Developer 1: Dominic Soares
 * Developer 2: Kevin Do
 
 Usage: java WordGuess <paramFile> <targetWord>
 Example: java WordGuess param.dat genetic
    Using the parameter file, param.dat, try to generate the word "genetic."
*/

import java.lang.*;

public class WordGuessTst
{

 public static void main(String args[])
    {

        //WordGuess WG1 = new WordGuess(args[0],args[1]);
	 	WordGuess WG1 = new WordGuess(args[0]);

        System.out.println();
        //WG1.DisplayParams(); Uncomment to display the contents of the parameter file
        //WG1.DisplayPop(); Uncomment to display the population before evolution
        WG1.Evolve();
        //WG1.DisplayPop(); Uncomment to display the population after evolution
        System.out.println();
    }
}

