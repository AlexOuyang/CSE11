/*************************************************************************
  *	 ID: 
  *	 NAME: 
  *	 LOGIN: cs11eXX
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

		}
		catch(IOException e) {
			e.printStackTrace();
		}

		return;
	}
}
