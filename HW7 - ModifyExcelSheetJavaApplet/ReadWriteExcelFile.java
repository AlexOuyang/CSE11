/*************************************************************************
 *  Compilation:  javac Driver.java
 *  About: Core function library that reads excel files, extracts data into
 *  arrays, creates Instrument objects from data, checks the input excel sheet
 *  for validity and outputs the Instrument objects into a new excel sheet.
 *************************************************************************/
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

// Core function library
public class ReadWriteExcelFile {
	// Those variables keep track of the fields orders so we can write the
	// new excel file fields the same order as the input excel file
	// If they are -1 then they don not exist in the input excel data sheet
	private static int descriptionOrderNum = -1;
	private static int SKUOrderNum = -1;
	private static int MAPOrderNum = -1;
	private static int stockOrderNum = -1;
	private static int priceOrderNum = -1;
	private static ArrayList<String> errorLog = new ArrayList<String>();

	/**
     * An accessor for the field errorLog
     * @return an arraylist of errors
     */
	public static ArrayList<String> getErrorLog() {
		return errorLog;
	}

	/**
     * reset the fields to -1
     * @return void
     */
	private static void resetFieldsOrder() {
		descriptionOrderNum = -1;
		SKUOrderNum = -1;
		MAPOrderNum = -1;
		stockOrderNum = -1;
		priceOrderNum = -1;
	}
	/**
	 * Check for errors in both master sheet and vendor sheet and
	 * input errors into errorLog
	 * @param a 2D array of master excel sheet cells
	 * @param a 2D array of vendor excel sheet cells
	 * @return if the input sheets are valid or not in boolean
     */
	public static boolean isErrorFree(List<List<XSSFCell>> excelData,
			List<List<XSSFCell>> excelVendorData) {
		// create a new errorLog
		errorLog = new ArrayList<String>();
		boolean isErrorFree = true;
		// Check for vendor fields
		resetFieldsOrder();
		assignExcelFieldsOrder(excelVendorData.get(0));
		if (descriptionOrderNum < 0) {
			errorLog.add("Vendor sheet has no Description..");
			isErrorFree = false;
		}
		if (SKUOrderNum < 0) {
			errorLog.add("Vendor sheet has no SKU..");
			isErrorFree = false;
		}
		if (MAPOrderNum < 0) {
			errorLog.add("Vendor sheet has no MAP..");
			isErrorFree = false;
		}

		// Check for Master sheet fields
		resetFieldsOrder();
		assignExcelFieldsOrder(excelData.get(0));
		if (descriptionOrderNum < 0) {
			errorLog.add("Master sheet has no Description..");
			isErrorFree = false;
		}
		if (SKUOrderNum < 0) {
			errorLog.add("Master sheet has no SKU..");
			isErrorFree = false;
		}
		if (MAPOrderNum < 0) {
			errorLog.add("Master sheet has no MAP..");
			isErrorFree = false;
		}
		if (stockOrderNum < 0) {
			errorLog.add("Master sheet has no SKU..");
			isErrorFree = false;
		}
		if (priceOrderNum < 0) {
			errorLog.add("Master sheet has no MAP..");
			isErrorFree = false;
		}

		return isErrorFree;
	}


	/**
	 * Update the MAP of ecah instruments in the master sheet based
	 * on the vendor sheet
	 * @param an ArrayList of master sheet instrument objects
	 * @param an ArrayList of vendor sheet instrument objects
	 * @return void
     */
	public static void updateInstrumentsMAP(ArrayList<Instrument> instruments,
			ArrayList<Instrument> vendorInstruments) {
		// Loop thorough each arrayList to match the SKU
		for (int i = 1; i < instruments.size(); i++) {
			// Used for checking instruments with duplicated MAP
			ArrayList<Instrument> instrumentsWithDuplicatedMAP =
					new ArrayList<Instrument>();
			// For each instrument in master sheet, loop through all the
			// instruments in vendor sheet to match MAP
			for (int j = 1; j < vendorInstruments.size(); j++) {
				// Check both SKU and name
				String instrumentSKU = instruments.get(i).getSKU();
				String vendorSKU = vendorInstruments.get(j).getSKU();
				// Check MAP for match, if MAP is duplicated then add to
				// instrumentsWithDuplicatedMAP
				if (instrumentSKU.equals(vendorSKU)) {
					Instrument vendorInt = vendorInstruments.get(j);
					instrumentsWithDuplicatedMAP.add(vendorInt);
				}
			}

			// If there is no duplicated MAP, update the instrument with
			// new MAP, if yes, then check for name
			if (instrumentsWithDuplicatedMAP.size() == 1) {
				String SKU = instrumentsWithDuplicatedMAP.get(0).getMAPField();
				// set the new MAP for the instrument
				instruments.get(i).setMAP(SKU);
			} else if (instrumentsWithDuplicatedMAP.size() > 1) {
				int size = instrumentsWithDuplicatedMAP.size();
				for (int k = 0; k < size; k++) {
					String description = instruments.get(i).getDescription();
					String vendorDescription = instrumentsWithDuplicatedMAP
							.get(k).getDescription();
					if (description.equals(vendorDescription)) {
						String SKU = instrumentsWithDuplicatedMAP.get(k)
								.getMAPField();
						// set the new MAP for the instrument
						instruments.get(i).setMAP(SKU);
					}
				}
			}
		}
	}

	/**
	 * Creates and writes the attributes of each instrument objects
	 * to a new Excel file
	 * @param the new fileName inString
	 * @param determin if the output file is a detailed excel sheet
	 *        or a non detailed excel sheet in boolean
	 * @return void
     */
	public static void WriteToExcel(String fileName, boolean detailed,
			ArrayList<Instrument> instruments,
			List<XSSFCell> excelDataFieldsOrder) {

		// assign the output fields order based on the fields of the excel
		// sheet
		assignExcelFieldsOrder(excelDataFieldsOrder);

		@SuppressWarnings("resource")
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("Exel sheet");

		for (int i = 0; i < instruments.size(); i++) {
			Row row = sheet.createRow(i);
			Instrument one = instruments.get(i);
			row.createCell(SKUOrderNum).setCellValue(one.getSKU());
			row.createCell(descriptionOrderNum).setCellValue(
					one.getDescription());

			// Row one contains all the fields
			if (i == 0) {
				row.createCell(stockOrderNum).setCellValue(one.getStockField());
				row.createCell(MAPOrderNum).setCellValue(one.getMAPField());
				row.createCell(priceOrderNum).setCellValue(one.getPrice());

				if (detailed)
					row.createCell(5).setCellValue("Detail");

			} else {
				row.createCell(stockOrderNum).setCellValue(one.getStock());
				row.createCell(MAPOrderNum).setCellValue(one.getMAP());
				// set formula cell
				Cell priceCell = row.createCell(priceOrderNum);
				priceCell.setCellType(XSSFCell.CELL_TYPE_FORMULA);
				String price = one.getPrice();
				priceCell.setCellFormula(price);
				if (detailed)
					row.createCell(5).setCellValue(one.getDetail());

			}

		}

		try {
			FileOutputStream out = new FileOutputStream(new File(fileName));
			workbook.write(out);
			out.close();
			if (detailed)
				System.out.println("Detailed Sheet Written Successfully..");
			if (!detailed)
				System.out.println("Master Sheet Written Successfully..");

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * A helper method for assignExcelDataToInstruments() and WriteToExcel()
	 * Assign the excel fields so we can write the new excel field
	 * fields the same order as the input excel file. By default the
	 * fields to a negative number if they do not exist in the sheet
	 * @param the first row in the excel sheet as a list of excel sheet
	 *        cell data
	 * @return void
    */
	private static void assignExcelFieldsOrder(List<XSSFCell> excelFields) {
		for (int i = 0; i < excelFields.size(); i++) {
			if (excelFields.get(i).toString().equals("SKU"))
				SKUOrderNum = i;
			if (excelFields.get(i).toString().equals("Description"))
				descriptionOrderNum = i;
			if (excelFields.get(i).toString().equals("MAP"))
				MAPOrderNum = i;
			if (excelFields.get(i).toString().equals("Stock"))
				stockOrderNum = i;
			if (excelFields.get(i).toString().equals("ListPrice"))
				priceOrderNum = i;
		}

	}

	/**
	 * Creates the instrument objects based on excel data and add the
	 * objects to the arrayList of instruments
	 * @param a 2D list of excel sheet cell data
	 * @param determin if the output file is a detailed excel sheet
	 *        or a non detailed excel sheet in boolean
	 * @return an ArrayList of instrument objects
    */
	public static ArrayList<Instrument> assignExcelDataToInstruments(
			List<List<XSSFCell>> excelData, boolean isVendor) {
		// find the order of excel file fileds
		resetFieldsOrder();
		assignExcelFieldsOrder(excelData.get(0));

		ArrayList<Instrument> instruments = new ArrayList<Instrument>();
		// Assign the fields to each instrument object based on the excel
		// fields order
		for (int i = 0; i < excelData.size(); i++) {
			List<XSSFCell> list = (List<XSSFCell>) excelData.get(i);
			// for each Column, get the data based on fields order
			String SKU = list.get(SKUOrderNum).toString();
			String description = list.get(descriptionOrderNum).toString();
			String MAP = list.get(MAPOrderNum).toString();
			if (isVendor) {
				// vendor has only three arguments
				Instrument instrument = new Instrument(SKU, description, MAP);
				instruments.add(instrument);

			} else {
				String stock = list.get(stockOrderNum).toString();
				String price = list.get(priceOrderNum).toString();

				Instrument instrument = new Instrument(SKU, description, MAP,
						stock, price);
				instruments.add(instrument);
			}
		}

		return instruments;
	}

	/**
	 * Reads the excel data to a 2D List of excel sheet cell data
	 * @param the file name of the data that needs to be read in String
	 * @return a 2D list of excel sheet cell data
    */
	public static List<List<XSSFCell>> readExcelDataToArrayList
		(String filename) throws IOException {
		// Create an ArrayList to store the data read from excel sheet.
		List<List<XSSFCell>> excelData = new ArrayList<List<XSSFCell>>();
		FileInputStream fis = null;
		try {
			// Create a FileInputStream to read the excel file.
			fis = new FileInputStream(filename);

			// Create an excel workbook from the file system.
			@SuppressWarnings("resource")
			XSSFWorkbook workbook = new XSSFWorkbook(fis);
			// Get the first sheet on the workbook since that is
			// all we care about in here.
			XSSFSheet sheet = workbook.getSheetAt(0);

			// Iterator on each of the cells in the rows of the data sheet
			// and store the data in an ArrayList
			Iterator rows = sheet.rowIterator();
			while (rows.hasNext()) {
				XSSFRow row = (XSSFRow) rows.next();
				Iterator cells = row.cellIterator();

				List<XSSFCell> rowData = new ArrayList<XSSFCell>();
				while (cells.hasNext()) {
					XSSFCell cell = (XSSFCell) cells.next();
					// System.out.println(cell);
					rowData.add(cell);
				}

				excelData.add(rowData);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fis != null) {
				fis.close();
			}
		}
		// After finish reading the dataSheet return the data arrayList
		return excelData;
	}

}
