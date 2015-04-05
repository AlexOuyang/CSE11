/*************************************************************************
 *  Compilation:  javac Driver.java
 *  About: HighTickAnalyzer saves the date and the high value of
 *  the stock tick, it is derived from StockAnalyzer class
 *************************************************************************/
import java.awt.*;
import java.util.Scanner;

public class HighTickAnalyzer extends StockAnalyzer {

	/**
	 * Adds a shigh tock tick to the stock analyzer. The analyzer assumes
	 * that the stock tick has the form:
	 * Date,Open,High,Low,Close,Volume,AdjClose
	 * <p>
	 * date, open value, high value, low value, close value, volume, adj close
	 * <p>
	 * @param tick a string array that represents a single stock tick
	 * @return void
	 */
    public void addStock(String[] tick) {
		dates.add(tick[0]);
		values.add(Double.valueOf(tick[2]));
		return;
	}

}
