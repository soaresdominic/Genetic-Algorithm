# Genetic-Algorithm

### Description
Java implementation of a genetic algorithm to find an optimized solution to the traveling salesman problem for 8 points. 
The distances between the points a->h are located in data.txt as a matrix.

The algorithm uses top-down and tournament pairing matched with single-point and	double-point crossover mating.

### Youtube Video Demo
[![Genetic Algorithm](https://img.youtube.com/vi/tP_hzORpP_U/maxresdefault.jpg)](https://youtu.be/tP_hzORpP_U "Genetic Algorithm")

### How-To Run
To Compile (either Windows or Unix):
1. Copy all eight files to the same directory
2. Be sure that JDK 7 is installed and accessible from the directory where the Genetic-Algorithm files are found
3. Type: ```javac *.java```

To create the parameter file.
1. Decide upon a parameter set, for example:

   --128 intitial population members
   
   --64 actual population members 
   
   --5 size of the word to be guessed
   
   --.1 mutation factor
   
   --1000 maximum number of generations

   
2. Type: ```java SetParams params.dat 128 64 8 .1 1000```

To run WordGuess:
1. Type: ```java Run [parameter_file]```
    For example, java Run params.dat
