/*************************************************************************
 *  Compilation:  javac Driver.java
 *  About:  This class will store the String[][] created by the CSVParser
 *  and then will simulate streaming stock data.
 *************************************************************************/
import java.awt.*;
import java.util.Scanner;

public class StockOracle {
	private String[][] ticks;
	//used for keeping track of the stock tick number
	private int tickNum;

	/**
	 * class constructor, initialize private class variables
	 * <p>
	 * @param inputTicks expects an array of an array of Strings
	 */
	public StockOracle(String[][] inputTicks) {
		this.ticks = inputTicks;
		tickNum = -1;
	}


	/**
	 * Returns the next stock tick. This function returns null if
	 * there are no more stock ticks available.
	 * <p>
	 * @return String[] a string array of stock information.
	 *
	 */
	public String[] getNextTick() {
		tickNum++;
		if(tickNum < ticks.length){
			return ticks[tickNum];
		}
		return null;
	}

}
