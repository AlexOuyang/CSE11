/*************************************************************************
 *  Compilation:  javac Driver.java
 *  About: Create instrument objects from each row in the input master and
 *  vendor excel sheets.
 *************************************************************************/
public class Instrument {
	private String SKU;
	private String description;
	private String MAP;
	private String stock;
	private String price;

	private String details;

	/**
     * Create an instrument based on the vendor excel sheet, which has
     * 3 fields
     */
	public Instrument(String SKU, String description, String MAP) {
		this.SKU = SKU;
		this.description = description;
		this.MAP = MAP;
	}

	/**
     * Create an instrument based on the master excel sheet, which has
     * 5 fields
     */
	public Instrument(String SKU, String description, String MAP, String stock,
			String price) {
		this.SKU = SKU;
		this.description = description;
		this.MAP = MAP;
		this.stock = stock;
		this.price = price;
		this.details = "Not Found";
	}

	/**
	 * An accessor for the price field
	 * @return the price of the instrument in String
	 */
	public String getPrice() {
		return price;
	}

	/**
	 * A mutator for the details field
	 * @param the new MAP of the instrument in String
	 * @return void
	 */
	public void setDetails(String newMAP) {
		double newM = Double.parseDouble(newMAP);
		double M = Double.parseDouble(this.MAP);
		int compare = Double.compare(newM, M);
		if (compare > 0) {
			this.details = "MVP Increased";
		} else if (compare < 0) {
			this.details = "MVP Decreased";
		} else {
			this.details = "MVP Unchanged";
		}
	}

	/**
	 * An accessor for the details field.
	 * @return the details of the instrument in String
	 */
	public String getDetail() {
		return details;
	}

	/**
	 * A mutator for the MAP field.
	 * @param the new MAP of the field
	 * @return void
	 */
	public void setMAP(String newMAP) {
		setDetails(newMAP);
		MAP = newMAP;
	}

	/**
	 * An accessor for the MAP field.
	 * @return the MAP of the instrument in String
	 */
	public String getMAPField() {
		return MAP;
	}

	/**
	 * An accessor for the MAP field
	 * @return the MAP of the instrument in Double
	 */
	public double getMAP() {
		return Double.parseDouble(MAP);
	}

	/**
	 * An accessor for the SKU field
	 * @return the SKU of the instrument in String
	 */
	public String getSKU() {
		return SKU;
	}

	/**
	 * An accessor for the description field
	 * @return the description of the instrument in String
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * An accessor for the stock field
	 * @return the stock of the instrument in String
	 */
	public String getStockField() {
		return stock;
	}

	/**
	 * An accessor for the stock field
	 * @return the stock of the instrument in Double
	 */
	public double getStock() {
		return Double.parseDouble(stock);
	}

}
