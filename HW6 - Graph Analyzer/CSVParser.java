/*************************************************************************
 *  Requires: Driver.java
 *  About: CSV Parser loads a csv file and stores each comma separated value
 *  as an index in the String[][]. Each row of the String[][] should
 *  represent one line in the CSV file.
 *************************************************************************/
import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.*;


public class CSVParser {

	/**
	 * Loads a csv file and return the information stored in an array of
	 * an array of strings.
	 * @param filename - the name and directory of the csv file
	 * @return a 2D array of strings that contains the csv data
	 */
	public String[][] parse(String filename) throws IOException {
		ArrayList<String[]> tempData = new ArrayList<String[]>();
		//read the file
		File file = new File(filename);
		FileReader fileReader = new FileReader(file);
		BufferedReader reader = new BufferedReader(fileReader);
		String line = reader.readLine();

		//store and parse each line
		for(int index = 0; line != null; index++){
			String[] info = line.split(",");
			//skip the firt line
			if(index != 0){
				tempData.add(info);
			}
			line = reader.readLine();
    	}
    	//convert the arrayList to a 2d array
		String[][] data = tempData.toArray(new String[tempData.size()][]);
		return data;
	}
}
