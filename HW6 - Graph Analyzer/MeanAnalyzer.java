/*************************************************************************
 *  Compilation:  javac Driver.java
 *  About: MeanAnalyzer saves the date and the ten point average value of
 *  the stock tick, it is derived from StockAnalyzer class.
 *************************************************************************/
import java.awt.*;
import java.util.Scanner;
import java.util.ArrayList;
import java.io.PrintWriter;
import java.io.IOException;


public class MeanAnalyzer extends StockAnalyzer {
	private int tickNum = 0;
	//keep track of the highValues
	private ArrayList<Double> highValues = new ArrayList<Double>();;

	/**
	 * Adds a 10 point average stock tick to the stock analyzer.
     * The analyzer assumes that the stock tick has the form:
	 * <p>
	 * date, open value, high value, low value, close value, volume, adj close
	 * <p>
	 * @param tick a string array that represents a single stock tick
	 * @return void
      * @see <a href=
     * "http://en.wikipedia.org/wiki/Moving_average">
     * 10 Point Average</a>
	 */
    public void addStock(String[] tick) {
    	tickNum++;
		dates.add(tick[0]);
		//add the highValues
		highValues.add(Double.valueOf(tick[2]));
		//calculating 10 point average and add to the values
        //first 9 points are zeros
		if(tickNum < 10){
			values.add(0.0);
		}else{
			double tenPointAverage = 0.0;
			for(int i = tickNum - 10; i < tickNum - 1; i++){
				tenPointAverage += highValues.get(i);
			}
			tenPointAverage /= 10.0;
			values.add(Double.valueOf(tenPointAverage));
		}
		return;
	}
    /**
     * Writes dates and data to a JSON file using PrintWriter
     * It overrides the writeJSON function in the super class, it
     * skips the first nine 0 valued tenPointAverages
     * @param filename - the name of the JSON file that writeJSON writes to
     * @return void
     */
	public void writeJSON(String filename) throws IOException {
		PrintWriter pw = new PrintWriter(filename);
		pw.println("{");
		pw.print("  \"labels\": [");
		for(int i = 9; i < dates.size()-1; i++){
			pw.print("\"" + dates.get(i) + "\",");
		}
		pw.print("\"" + dates.get(dates.size()-1) + "\"],");
		pw.println();
		pw.println("  \"datasets\": [");
		pw.println("    {");
		pw.print("      \"data\": [");
		for(int i = 9; i < values.size()-1; i++){
			pw.print(values.get(i) + ",");
		}
		pw.println(values.get(values.size()-1) + "]");
		pw.println("    }");
		pw.println("  ]");
		pw.println("}");

		pw.close();
		return;
	}

}
