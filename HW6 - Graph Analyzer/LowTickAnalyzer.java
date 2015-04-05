/*************************************************************************
 *  Compilation:  javac Driver.java
 *  About: LowTickAnalyzer saves the date and the low value of
 *  the stock tick, it is derived from StockAnalyzer class
 *************************************************************************/
import java.awt.*;
import java.util.Scanner;

public class LowTickAnalyzer extends StockAnalyzer {

	/**
	 * Adds a low stock tick to the stock analyzer. The analyzer assumes
	 * that the stock tick has the form:
	 * Date,Open,High,Low,Close,Volume,Adj Close
	 * <p>
	 * date, open value, high value, low value, close value, volume, adj close
	 * <p>
	 * @param tick a string array that represents a single stock tick
	 * @return void
	 */
    public void addStock(String[] tick) {
		dates.add(tick[0]);
		values.add(Double.valueOf(tick[3]));
		return;
	}

}
