/*************************************************************************
 *  Compilation:  javac Driver.java
 *  Execution:    java Driver
 *  Requires: CSVParser.java, StockOracle.java, StockAnalyzer.java
 *  HighTickAnalyzer.java, LowTickAnalyzer.java, MeanAnalyzer.java
 *  About:  create the stocks tools and runs the stock analysis code
 *************************************************************************/
import java.io.IOException;

public class Driver {

    public static void main(String[] args) throws IOException {

		// create the stock tools
		String filename = "googlestocks.csv";
		CSVParser parser = new CSVParser();
		StockOracle stocks = new StockOracle(parser.parse(filename));

		StockAnalyzer highStocks = new HighTickAnalyzer();
		StockAnalyzer lowStocks = new LowTickAnalyzer();
		StockAnalyzer meanStocks = new MeanAnalyzer();

		String[] tick;

		while( (tick = stocks.getNextTick()) != null) {
			highStocks.addStock(tick);
			lowStocks.addStock(tick);
			meanStocks.addStock(tick);
		}

		highStocks.writeJSON("highStocks.json");
		lowStocks.writeJSON("lowStocks.json");
		meanStocks.writeJSON("meanStocks.json");

		return;
    }
}
