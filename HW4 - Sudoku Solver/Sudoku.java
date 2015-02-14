/*************************************************************************
  *	 ID:A12067610 
  *	 NAME: Chenxing Ouyang
  *	 LOGIN: cs11efj
  *
  *	 Compilation:  javac Sudoku.java
  *	 Execution:	   java Sudoku <table filename>
  *	 About: Sudoku solver! Given a text file with an unfinished sudoku table
  *         this program will attempt to solve it.
  *************************************************************************/
import java.io.*;
import java.io.BufferedReader;

public class Sudoku {

	// CreateTable uses a BufferedReader to load a sudoku table.
	public static int [][] CreateTable(BufferedReader reader)
		throws IOException {

		int[][] map = new int[9][9];
		String line = reader.readLine();
		String[] nums;
		int j = 0;
		
		while(line != null) {
			nums = line.split(" ");
			for(int i = 0; i < nums.length; i++) {
				map[j][i] = Integer.parseInt(nums[i]);
			}

			j++;
			line = reader.readLine();
		}

		return map;
	}

	public static boolean isFinished(int[][] board) {
		for(int i = 0; i < 9; i++) {
			for(int j = 0; j < 9; j++) {
				if(board[i][j] == 0)
					return false;
			}
		}
		return true;
	}
//returns an array that holds solutions for each element in a row
  public static int[] getPossibleSolutions(int[] array){
    int numOfRowSolutions = 0;
      // determine the length of this array
      for(int a = 0;a < 9; a++){
        if(array[a] == 0){              
          numOfRowSolutions++;
        }
      }
      //create the array with possible solutions
      int[] tempSolutions = new int[numOfRowSolutions];
      //add the possible solutions into the array
      int i = 0;
      for(int b = 1; b < 10; b++){
        if(!isInArray(b, array)){
          if(i < numOfRowSolutions){
            tempSolutions[i] = b;
            i++;
          }
        }
      }
      return tempSolutions;
    }

   //check if a number is in a given array
  public static boolean isInArray(int number, int[] array){
    for(int i = 0; i < array.length; i++){
      if(array[i] == number){
        return true;
      }
    }
    return false;
  }

  //returns an array of intersection between two arrays
  public static int[] getIntersectionSet(int[] array1, int[] array2){
    //get the length of the set first
    int length = 0;
    for(int i = 0;i<array1.length;i++){
      for(int j=0;j<array2.length;j++){
        if(array1[i] == array2[j]){
          length++;
        }
      }
    }
    //create the intersection set
    int[] intersectionSet = new int[length];
    int a = 0;
    for(int i=0;i<array1.length;i++){
      for(int j=0;j<array2.length;j++){
        if(array1[i] == array2[j]){
          intersectionSet[a] = array1[i];
          a++;
        }
      }
    }
    return intersectionSet;
  }
  
  //returns an array that holds the values of a 3x3 cubical
  public static int[] getCubical(int row, int col, int[][] board){
    int[] cubical = new int[9];
    if(row>=0 && row<3){
      row = 0;
    }else if(row>=3 && row<6){
      row = 3;
    }else if(row>=6 && row<9){
      row = 6;
    }
    if(col>=0 && col<3){
      col = 0;
    }else if(col>=3 && col<6){
      col = 3;
    }else if(col>=6 && col<9){
      col = 6;
    }
    //a is a temp var holds the iteration value
    int a = 0;
    for(int i = row; i < row+3; i++){
      for(int j = col; j < col+3; j++){
        cubical[a] = board[i][j];
        a++;
      }
    }
    return cubical;
  }


	public static void main(String[] args) {
		// check command line arguments
		if(args.length != 1) {
			System.out.println("Missing sudoku file.");
			return;
		}

		// read in the file from
		try {
			int[][] board;
			File file = new File(args[0]);
			FileReader fileReader = new FileReader(file);
			BufferedReader reader = new BufferedReader(fileReader);
			// copy the data
			board = CreateTable(reader);
			fileReader.close();

   
      // Solve the sudoku problem here.
      // a temp array holds the values of the rows of the board
      int[] rowTemp = new int[9];
     
      // a temp array holds the values of the columns of the board
      int[] colTemp = new int[9];
      
      //while not finishing the game keep inserting numbers into the board
      while(!isFinished(board)){
        for(int i = 0; i < 9; i++){
          for(int j = 0; j < 9; j++){
            if(board[i][j] == 0){
              //get the value of ith row in the board
              rowTemp = board[i];
              //create a temp array that holds the possible solutions for 
             //each element in the rows of the board
              //getPossibleSolution is a static helper method that I implemented
              int[] rowTempSolutions = getPossibleSolutions(rowTemp);
              //get the value of ith collumn in the board
              for(int k = 0; k < 9; k++){
                colTemp[k] = board[k][j];
              }
             //create a temp array that holds the possible solutinos for
              //each element in the columns of the board
              int[] colTempSolutions = getPossibleSolutions(colTemp);
              //get the interesction set
              int[] intersectionSet1 = getIntersectionSet(rowTempSolutions, 
                                                        colTempSolutions);
              //create a temp array hods the 3x3 values
              int[] array3by3 = getCubical(i, j, board);
              int[] solutions3by3 = getPossibleSolutions(array3by3);
              //find the intersection set between the intersectino set of
              //colTempSolution and rowTempsolution and array2by3 solution
              int[] ultimateSolutions = getIntersectionSet(intersectionSet1,
                                                        solutions3by3);
              //if there's only one solutino in the set of ultimateSolutions
              //then push the vlaue into the array
              if(ultimateSolutions.length == 1){
                board[i][j] = ultimateSolutions[0];
              }
            }
          }
        }
      
      }

      // print out the board 9 X 9
      for(int i = 0; i < 9; i++){
        for(int j = 0; j < 9; j++){
          System.out.print(board[i][j] + " ");
        }
          System.out.println();
      }

      //check if the sudoku is solved
      if(isFinished(board)){
         // System.out.println("Table solved.");
      }else{
         //System.out.println("Table is not solved.");
      }
		}
		catch(IOException e) {
			e.printStackTrace();
		}

		return;
	}
}
