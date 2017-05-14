import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.*;

import com.mysql.jdbc.Statement;

public class AddBarcodeTab extends JPanel{
	
	private JLabel barcodeLabel;
	private JTextField barcodeField;
	
	public AddBarcodeTab() {
		setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();
		
		// Barcode Entry
		JPanel content = new JPanel(new GridBagLayout());
		
		gc.gridx = 0;
		gc.gridy = 0;
		gc.insets = new Insets(10, 0, 0, 0);
		
		// Appearance of the barcode label and field
		barcodeLabel = new JLabel("Barcode:");
		barcodeLabel.setFont(new Font(barcodeLabel.getFont().getName(), Font.PLAIN, 18));
		barcodeField = new JTextField(20);
		barcodeField.setHorizontalAlignment(SwingConstants.CENTER);
		barcodeField.setFont(new Font(barcodeField.getFont().getName(), Font.PLAIN, 18));
		content.add(barcodeLabel, gc);
		
		gc.gridy = 1;
		content.add(barcodeField, gc);

		gc.gridy = 2;
		JButton submitButton = new JButton("Submit");
		submitButton.setFont(new Font(submitButton.getFont().getName(), Font.PLAIN, 18));
		submitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// On clicking the submit button, validate the barcode
				validateBarcode();
			}
		});
		content.add(submitButton, gc);
		
		gc.gridx = 0;
		gc.gridy = 1;
		gc.insets = new Insets(0,0,0,0);
		add(content, gc);
	}
	
	public void validateBarcode() {
		// Resets the error message
		barcodeLabel.setText("Barcode:");
		barcodeLabel.setForeground(Color.BLACK);
		// A boolean that changes to false if an error has occurred
		boolean flag = true;
		
		// Checks if no barcode has been entered
		if (barcodeField.getText().length() == 0) {
			// Set error message
			barcodeLabel.setText("Barcode: please enter a barcode");
			barcodeLabel.setForeground(Color.RED);
			flag = false;
		}
		// Checks if the barcode field is entirely numerical
		else if(!(barcodeField.getText().matches("\\d+"))) {
			// Set error message
			barcodeLabel.setText("Barcode: invalid barcode");
			barcodeLabel.setForeground(Color.RED);
			flag = false;
		}
		// Checks if the barcode is 13 in length
		else if(barcodeField.getText().length() != 13) {
			// Set error message
			barcodeLabel.setText("Barcode: invalid size");
			barcodeLabel.setForeground(Color.RED);
			flag = false;
		}
		
		if (flag == true) {
			// Move to result tab if no errors found
			resultTab(true);
		}
	}
	
	// Feature not required in assignment
	public void resultTab(Boolean success) {
		removeAll();
		revalidate();
		repaint();
		JLabel message = new JLabel();
		message.setFont(new Font(message.getFont().getName(), Font.PLAIN, 18));
		if (success == true) {
			message.setText("This feature is yet to be added");
		}
		else {
			message.setText("Oops! Something went wrong!");
		}
		add(message);
	}
}
