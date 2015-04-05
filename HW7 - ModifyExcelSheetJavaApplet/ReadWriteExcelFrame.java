/*************************************************************************
 *  Compilation:  javac Driver.java
 *  About: GUI that enables the users to interact with the program graphically.
 *  The users can select a file and where to save the output file and run
 *  the program graphically. The GUI frame also outputs the errors and notifies
 *  the users once the output files are generated successfully
 *************************************************************************/
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.poi.xssf.usermodel.XSSFCell;

@SuppressWarnings("serial")
public class ReadWriteExcelFrame extends JFrame implements ActionListener {
	private JTextArea debugLogArea;
	private JButton generateExcelButton;
	private JButton clearDebugButton;
	private JButton openMasterButton;
	private JButton openVendorButton;
	private JButton chooseOutputLocation;
	private JLabel masterSheetStatusBar = new JLabel(
			"Output of your selection will go here");
	private JLabel vendorSheetStatusBar = new JLabel(
			"Output of your selection will go here");
	private JLabel outputSheetStatusBar = new JLabel(
			"Output of your selection will go here");
	private final static String APP_TITLE =
			"Musical Instrument Vendor Sheet Generator";
	private final static String FILE_TITLE =
			"All Microsoft EXCEL files (*.xlsx, *.xls)";
	public final static String[] FILE_TYPES = new String[] { "xlsx" };

	// Filename includes excel file full path name
	private String filename = "";
	private String vendorFileName = "";
	private String savingLocation = "";

	/**
	 * Constructor creates GUI components and adds GUI components using a
	 * GridBagLayout.
	 */
	ReadWriteExcelFrame() {
		GridBagConstraints layoutConst = null; // Used to specify GUI component
												// layout
		JScrollPane scrollPane = null; // Container that adds a scroll bar
		JLabel debugLogLabel = null; // Label for yearly savings

		// Set the title of the frame
		setTitle(APP_TITLE);

		/**
		 * Create button for opening master excel file and set up
		 * the action listener
		 */
		openMasterButton = new JButton("Open Master Excel Sheet");
		openMasterButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				JFileChooser chooser = new JFileChooser();
				chooser.setMultiSelectionEnabled(false);
				chooser.setAcceptAllFileFilterUsed(false);
				chooser.setFileFilter(new FileNameExtensionFilter(FILE_TITLE,
						FILE_TYPES));
				int returnVal = chooser
						.showOpenDialog(ReadWriteExcelFrame.this);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					String masterFilePath = chooser.getSelectedFile()
							.getAbsolutePath();
					filename = masterFilePath;
					masterSheetStatusBar.setText("You choose: "
							+ chooser.getSelectedFile().getName());
					debugLogArea.append("You choose input Master sheet: "
							+ chooser.getSelectedFile().getAbsolutePath());
					debugLogArea.append("\n");
				} else {
					filename = "";
					masterSheetStatusBar.setText("You canceled.");
					debugLogArea
							.append("You cancled Master Excel Sheet Input.");
					debugLogArea.append("\n");
				}
			}
		});

		/**
		 * Create button for opening vendor excel file and set up
		 * the action listener
		 */
		openVendorButton = new JButton("Open Vendor Excel Sheet");
		openVendorButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				JFileChooser chooser = new JFileChooser();
				chooser.setMultiSelectionEnabled(false);
				chooser.setAcceptAllFileFilterUsed(false);
				// set up the file filter for choosing xlsx files only
				chooser.setFileFilter(new FileNameExtensionFilter(FILE_TITLE,
						FILE_TYPES));
				int returnVal = chooser
						.showOpenDialog(ReadWriteExcelFrame.this);
				// if the file path is correct, feed it to the master
				// sheet file name;
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					String vendorFilePath = chooser.getSelectedFile()
							.getAbsolutePath();
					vendorFileName = vendorFilePath;
					vendorSheetStatusBar.setText("You choose: "
							+ chooser.getSelectedFile().getName());
					debugLogArea.append("You choose input Vendor sheet: "
							+ chooser.getSelectedFile().getAbsolutePath());
					debugLogArea.append("\n");
				} else {
					filename = "";
					vendorSheetStatusBar.setText("You canceled.");
					debugLogArea
							.append("You cancled Vendor Excel Sheet Input.");
					debugLogArea.append("\n");
				}
			}
		});

		/**
		 * Create button for selecting excel output location and set up
		 * the action listener
		 */
		chooseOutputLocation = new JButton("Choose Saving Location");
		chooseOutputLocation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				JFileChooser chooser = new JFileChooser();
				chooser = new JFileChooser();
				chooser.setCurrentDirectory(new java.io.File("."));
				chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				chooser.setAcceptAllFileFilterUsed(false);
				int returnVal = chooser
						.showOpenDialog(ReadWriteExcelFrame.this);
				// if the file path is correct, feed it to the output excel
				// sheets saving location;
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					savingLocation = chooser.getSelectedFile()
							.getAbsolutePath();
					outputSheetStatusBar.setText("You choose: "
							+ chooser.getSelectedFile().getName());
					debugLogArea
							.append("Your choice of directory for "
									+ "output files is: "
									+ chooser.getSelectedFile()
											.getAbsolutePath());
					debugLogArea.append("\n");
				} else {
					outputSheetStatusBar.setText("You Caneled.");
					savingLocation = "";
					debugLogArea
							.append("Your selection for storing "
									+ "directory is cancled");
					debugLogArea.append("\n");
				}
			}
		});

		// Create the button that clears the debug area
		clearDebugButton = new JButton("Clear Debug Log");
		clearDebugButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				debugLogArea.setText("");
			}
		});

		// Create excel file generation button
		generateExcelButton = new JButton("Generate Excel Sheet");
		generateExcelButton.addActionListener(this);

		// Create Debug Label
		debugLogLabel = new JLabel("Debug/Details:");

		// Create debug area and add it to scroll pane
		debugLogArea = new JTextArea(10, 15);
		scrollPane = new JScrollPane(debugLogArea);
		debugLogArea.setEditable(false);

		// Use a GridBagLayout
		setLayout(new GridBagLayout());

		// Select Master sheet here
		layoutConst = new GridBagConstraints();
		layoutConst.insets = new Insets(10, 10, 5, 1);
		layoutConst.anchor = GridBagConstraints.LINE_END;
		layoutConst.gridx = 0;
		layoutConst.gridy = 0;
		add(openMasterButton, layoutConst);

		layoutConst = new GridBagConstraints();
		layoutConst.insets = new Insets(10, 1, 5, 10);
		layoutConst.fill = GridBagConstraints.HORIZONTAL;
		layoutConst.gridx = 1;
		layoutConst.gridy = 0;

		add(masterSheetStatusBar, layoutConst);

		// Select Vendor sheet here
		layoutConst = new GridBagConstraints();
		layoutConst.insets = new Insets(10, 10, 5, 1);
		layoutConst.anchor = GridBagConstraints.LINE_END;
		layoutConst.gridx = 0;
		layoutConst.gridy = 1;
		add(openVendorButton, layoutConst);

		layoutConst = new GridBagConstraints();
		layoutConst.insets = new Insets(5, 1, 5, 10);
		layoutConst.fill = GridBagConstraints.HORIZONTAL;
		layoutConst.gridx = 1;
		layoutConst.gridy = 1;

		add(vendorSheetStatusBar, layoutConst);

		// Select output location
		layoutConst = new GridBagConstraints();
		layoutConst.insets = new Insets(10, 10, 5, 1);
		layoutConst.anchor = GridBagConstraints.LINE_END;
		layoutConst.gridx = 0;
		layoutConst.gridy = 2;
		add(chooseOutputLocation, layoutConst);

		layoutConst = new GridBagConstraints();
		layoutConst.insets = new Insets(5, 1, 10, 10);
		layoutConst.fill = GridBagConstraints.HORIZONTAL;
		layoutConst.gridx = 1;
		layoutConst.gridy = 2;

		add(outputSheetStatusBar, layoutConst);

		// generate output sheet button
		layoutConst = new GridBagConstraints();
		layoutConst.insets = new Insets(10, 10, 5, 1);
		layoutConst.fill = GridBagConstraints.BOTH;
		layoutConst.gridx = 0;
		layoutConst.gridy = 3;
		add(generateExcelButton, layoutConst);

		// clear Debug button
		layoutConst = new GridBagConstraints();
		layoutConst.insets = new Insets(10, 10, 5, 1);
		layoutConst.fill = GridBagConstraints.HORIZONTAL;
		layoutConst.gridx = 1;
		layoutConst.gridy = 3;

		add(clearDebugButton, layoutConst);

		// Debug log Label
		layoutConst = new GridBagConstraints();
		layoutConst.insets = new Insets(10, 10, 1, 10);
		layoutConst.fill = GridBagConstraints.HORIZONTAL;
		layoutConst.gridx = 0;
		layoutConst.gridy = 4;
		add(debugLogLabel, layoutConst);

		// Grid area for debug Log
		layoutConst = new GridBagConstraints();
		layoutConst.insets = new Insets(1, 10, 10, 10);
		layoutConst.fill = GridBagConstraints.HORIZONTAL;
		layoutConst.gridx = 0;
		layoutConst.gridy = 5;
		layoutConst.gridwidth = 3; // 3 cells wide
		add(scrollPane, layoutConst);
	}

	/**
     * When GenerateExcelSheet button is clicked, it uses the core functions
     * from ReadWriteExcelFile to read, write the excel files and output the
     * errors if there is any
     */
	@Override
	public void actionPerformed(ActionEvent event) {
		// debugLogArea.setText("");

		// Check for empty paths first
		if (filename.equals("") || vendorFileName.equals("")) {
			debugLogArea.append("Please select the correct Excel files.");
			debugLogArea.append("\n");
			return;
		} else if (savingLocation.equals("")) {
			debugLogArea.append("Please select the correct saving locations.");
			debugLogArea.append("\n");
			return;
		}

		// Create an arrayList of data from the excel input sheets
		List<List<XSSFCell>> excelData;
		List<List<XSSFCell>> excelVendorData;
		try {
			excelData = ReadWriteExcelFile.readExcelDataToArrayList(filename);
			excelVendorData = ReadWriteExcelFile
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

				String masterFileOut = savingLocation + "/Master.xlsx";
				String masterFileDetailedOut = savingLocation
						+ "/Master - Detailed.xlsx";
				System.out
						.println(masterFileOut + "\n" + masterFileDetailedOut);
				ReadWriteExcelFile.WriteToExcel(masterFileOut, false,
						instruments, excelDataFieldsOrder);
				ReadWriteExcelFile.WriteToExcel(masterFileDetailedOut, true,
						instruments, excelDataFieldsOrder);

				// log out success
				debugLogArea.append("\n");
				debugLogArea.append("Detailed Sheet Written Successfully..");
				debugLogArea.append("\n");
				debugLogArea.append("Master Sheet Written Successfully..");
				debugLogArea.append("\n");
				debugLogArea.append("Your Files Are Saved At: "
						+ savingLocation);
				debugLogArea.append("\n");

			} else {
				ArrayList<String> errorLog = ReadWriteExcelFile.getErrorLog();
				debugLogArea.append("\n");
				debugLogArea.append("Errors: ");
				for (int i = 0; i < errorLog.size(); i++) {
					debugLogArea.append("\n");
					debugLogArea.append("    " + errorLog.get(i));
				}
				debugLogArea.append("\n");
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return;
	}

}
