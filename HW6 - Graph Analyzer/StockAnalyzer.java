/*************************************************************************
 *  Compilation:  javac Driver.java
 *  About: StockAnalyzer is a super class that can be implemented to save
 *  the date and the high value of the stock tick.
 *************************************************************************/
import java.util.ArrayList;
import java.io.IOException;
import java.io.PrintWriter;

public abstract class StockAnalyzer {
	protected ArrayList<String> dates;
	protected ArrayList<Double> values;

	/**
	 * Class constructor that initlializes protected variables
	 * <p>
	 */
	public StockAnalyzer() {
		dates = new ArrayList<String>();
		values = new ArrayList<Double>();
	}

	/**
	 * abstract method that
	 * <p>
	 * date, open value, high value, low value, close value, volume, adj close
	 * <p>
	 * @param tick a string array that represents a single stock tick
	 * @return void
	 */
    public abstract void addStock(String[] tick);

    /**
     * Writes dates and data to a JSON file using PrintWriter
     * @param filename - the name of the JSON file that writeJSON writes to
     * @return void
     */
	public void writeJSON(String filename) throws IOException {
		PrintWriter pw = new PrintWriter(filename);
		pw.println("{");
		pw.print("  \"labels\": [");
		for(int i = 0; i < dates.size()-1; i++){
			pw.print("\"" + dates.get(i) + "\",");
		}
		pw.print("\"" + dates.get(dates.size()-1) + "\"],");
		pw.println();
		pw.println("  \"datasets\": [");
		pw.println("    {");
		pw.print("      \"data\": [");
		for(int i = 0; i < values.size()-1; i++){
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
