/*************************************************************************
 *  Compilation:  javac Driver.java
 *  About: a test class to test the core functions from Instrument.java,
 *  ReadWriteExcelFiles.java and ReadWriteExcelFrame.java
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

public class Tester {
	public static void main(String[] args) throws Exception {

		// Filename includes excel file full path name
		String filename = "/Users/chenxingouyang/Desktop/cs11e/"
				+ "cs11efj/HW7/EXAMPLES1/master1.xlsx";
		String vendorFileName = "/Users/chenxingouyang/Desktop/"
				+ "cs11e/cs11efj/HW7/EXAMPLES1/vendor1.xlsx";

		// Create an arrayList of data from the excel input sheets
		List<List<XSSFCell>> excelData = ReadWriteExcelFile
				.readExcelDataToArrayList(filename);
		List<List<XSSFCell>> excelVendorData = ReadWriteExcelFile
				.readExcelDataToArrayList(vendorFileName);

		// Check for exception before writing the output excel files
		if (ReadWriteExcelFile.isErrorFree(excelData, excelVendorData)) {

			// create instruments based on the excel data
			ArrayList<Instrument> instruments = ReadWriteExcelFile
					.assignExcelDataToInstruments(excelData, false);
			ArrayList<Instrument> vendorInstruments = ReadWriteExcelFile
					.assignExcelDataToInstruments(excelVendorData, true);

			// Update the instruments MAP based on the vendor sheet
			ReadWriteExcelFile.updateInstrumentsMAP(instruments,
					vendorInstruments);

			// Fields Order is the first row in the excel sheet
			List<XSSFCell> excelDataFieldsOrder = excelData.get(0);

			String masterFileOut = "/Users/chenxingouyang/Desktop/cs11e/"
					+ "cs11efj/HW7/EXAMPLES1/Master.xlsx";
			String masterFileDetailedOut = "/Users/chenxingouyang/Desktop/"
					+ "cs11e/cs11efj/HW7/EXAMPLES1/Master - Detailed.xlsx";
			ReadWriteExcelFile.WriteToExcel(masterFileOut, false, instruments,
					excelDataFieldsOrder);
			ReadWriteExcelFile.WriteToExcel(masterFileDetailedOut, true,
					instruments, excelDataFieldsOrder);

			System.out.println("Excel Sheet Generated Successfully");

		}

	}

}
