/*************************************************************************
 *  Compilation:  javac Driver.java
 *  About: The driver class that is used to execute the program GUI and run
 *  the program
 *************************************************************************/
import java.awt.Dimension;
import java.awt.Insets;

import javax.swing.JFrame;

public class Driver {

	/**
     * Executes the program by setting up the GUI
     * @return void
     */
	public static void main(String[] args) {
		// Creates the GUI and run the java app
		ReadWriteExcelFrame myFrame = new ReadWriteExcelFrame();

		myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		myFrame.pack();
		Insets insets = myFrame.getInsets();
		myFrame.setSize(new Dimension(insets.left + insets.right + 500,
				insets.top + insets.bottom + 400));
		myFrame.setVisible(true);
		myFrame.setResizable(false);

		return;
	}
}
