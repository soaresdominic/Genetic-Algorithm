import java.io.File;
import java.io.FileNotFoundException;
import java.lang.*;
import java.util.Scanner;

public class TSP {
	
    int[][] matrix = new int[8][8];
	
	//construtor
	public TSP() throws FileNotFoundException{
		matrix = createMatrix();
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
	
	public int[][] getMatrix(){
		return matrix;
	}
	
	public void setMatrix(){
		
	}
}
