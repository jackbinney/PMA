import java.awt.*;
import java.awt.event.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.*;

import com.mysql.jdbc.Statement;

public class AddManualTab extends JPanel{
	
	private JLabel pharmacyLabel;
	private JTextField pharmacyField;
	private JLabel nameLabel;
	private JTextField nameField;
	private JLabel strengthLabel;
	private JTextField strengthField;
	private JLabel barcodeLabel;
	private JTextField barcodeField;
	private JLabel tabletsLabel;
	private JSpinner tabletsField;

	public AddManualTab() {
		defaultTab();
	}
	
	// What the user first sees
	public void defaultTab() {
		setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();
		
		gc.gridx = 0;
		gc.gridy = 0;
		gc.anchor = GridBagConstraints.CENTER;
		gc.insets = new Insets(10, 0, 0, 0);
		
		// Pharmacy Entry
		gc.gridy = 0;
		pharmacyLabel = new JLabel("Pharmaceutical Company Name:");
		pharmacyLabel.setFont(new Font(pharmacyLabel.getFont().getName(), Font.PLAIN, 18));
		pharmacyField = new JTextField(20);
		pharmacyField.setFont(new Font(pharmacyField.getFont().getName(), Font.PLAIN, 18));
		pharmacyField.setHorizontalAlignment(SwingConstants.CENTER);
		add(pharmacyLabel, gc);
		gc.gridy = 1;
		add(pharmacyField, gc);
		
		// Name Entry
		gc.gridy = 2;
		nameLabel = new JLabel("Name of Medication:");
		nameLabel.setFont(new Font(nameLabel.getFont().getName(), Font.PLAIN, 18));
		nameField = new JTextField(20);
		nameField.setFont(new Font(nameField.getFont().getName(), Font.PLAIN, 18));
		nameField.setHorizontalAlignment(SwingConstants.CENTER);
		add(nameLabel, gc);
		gc.gridy = 3;
		add(nameField, gc);
		
		// Strength Entry
		gc.gridy = 4;
		strengthLabel = new JLabel("Strength (mg):");
		strengthLabel.setFont(new Font(strengthLabel.getFont().getName(), Font.PLAIN, 18));
		strengthField = new JTextField(20);
		strengthField.setFont(new Font(strengthField.getFont().getName(), Font.PLAIN, 18));
		strengthField.setHorizontalAlignment(SwingConstants.CENTER);
		add(strengthLabel, gc);
		gc.gridy = 5;
		add(strengthField, gc);
		
		// Barcode of medication
		gc.gridy = 6;
		barcodeLabel = new JLabel("Barcode:");
		barcodeLabel.setFont(new Font(barcodeLabel.getFont().getName(), Font.PLAIN, 18));
		barcodeField = new JTextField(20);
		barcodeField.setFont(new Font(barcodeField.getFont().getName(), Font.PLAIN, 18));
		barcodeField.setHorizontalAlignment(SwingConstants.CENTER);
		add(barcodeLabel, gc);
		gc.gridy = 7;
		add(barcodeField, gc);
		
		// Tablets per package
		gc.gridy = 8;
		tabletsLabel = new JLabel("Tablets:");
		tabletsLabel.setFont(new Font(tabletsLabel.getFont().getName(), Font.PLAIN, 18));
//		tabletsField = new JTextField(20);
		tabletsField = new JSpinner();
		tabletsField.setFont(new Font(tabletsField.getFont().getName(), Font.PLAIN, 18));
//		tabletsField.setHorizontalAlignment(SwingConstants.CENTER);
	    Dimension prefSize = tabletsField.getPreferredSize();
	    prefSize = new Dimension(285, prefSize.height);
	    tabletsField.setPreferredSize(prefSize);
		add(tabletsLabel, gc);
		gc.gridy = 9;
		add(tabletsField, gc);
		
		// Submit button
		gc.gridy = 10;
		JButton submitButton = new JButton("Submit");
		submitButton.setFont(new Font(submitButton.getFont().getName(), Font.PLAIN, 18));
		submitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//				Resets any error messages
				resetLabels();
				validateManual();
			}
		});
		add(submitButton, gc);
	}
	
//	Resets any error messages
	public void resetLabels() {
		pharmacyLabel.setText("Pharmaceutical Company Name:");
		pharmacyLabel.setForeground(Color.BLACK);
		nameLabel.setText("Name of Medication:");
		nameLabel.setForeground(Color.BLACK);
		strengthLabel.setText("Strength (mg):");
		strengthLabel.setForeground(Color.BLACK);
		tabletsLabel.setText("Tablets:");
		tabletsLabel.setForeground(Color.BLACK);
		barcodeLabel.setText("Barcode:");
		barcodeLabel.setForeground(Color.BLACK);
	}
	
//	Clears all fields
	public void resetFields() {
		pharmacyField.setText("");
		nameField.setText("");
		strengthField.setText("");
//		tabletsField.setText("");
		tabletsField.setValue(0);
		barcodeField.setText("");
	}
	
//	Checks if the user has filled in all information correctly
	public void validateManual() {
//		Boolean variable to monitor if an error has occurred
		boolean flag = true;
		
//		If pharmacyField is empty
		if (pharmacyField.getText().length() == 0) {
//			Set error message
			pharmacyLabel.setText("Pharmaceutical Company Name: please enter a name");
			pharmacyLabel.setForeground(Color.RED);
			flag = false;
		}
//		If nameField is empty
		if (nameField.getText().length() == 0) {
//			Set error message
			nameLabel.setText("Name of Medication: please enter a name");
			nameLabel.setForeground(Color.RED);
			flag = false;
		}
//		If strengthField is empty
		if (strengthField.getText().length() == 0) {
//			Set error message
			strengthLabel.setText("Strength (mg): please enter a strength");
			strengthLabel.setForeground(Color.RED);
			flag = false;
		}
//		Check strengthField is not a decimal or integer
		else if(!(strengthField.getText().matches("\\d*\\.?\\d+"))) {
//			Set error message
			strengthLabel.setText("Strength (mg): Invalid strength");
			strengthLabel.setForeground(Color.RED);
			flag = false;
		}
//		If tabletsField is empty
		if (tabletsField.getValue().toString().equals("0")) {
			tabletsLabel.setText("Tablets: please enter an amount");
			tabletsLabel.setForeground(Color.RED);
			flag = false;
		}
//		If barcodeField is empty
		if (barcodeField.getText().length() == 0) {
			barcodeLabel.setText("Barcode: please enter a barcode");
			barcodeLabel.setForeground(Color.RED);
			flag = false;
		}
//		If barcodeField is not numerical
		else if(!(barcodeField.getText().matches("\\d+"))) {
			barcodeLabel.setText("Barcode: invalid barcode");
			barcodeLabel.setForeground(Color.RED);
			flag = false;
		}
//		If barcodeField is not 13 in length
		else if (barcodeField.getText().length() != 13) {
			barcodeLabel.setText("Barcode: incorrect barcode size");
			barcodeLabel.setForeground(Color.RED);
			flag = false;
		}
		
//		If no errors have occurred
		if (flag == true) {
			try {				
//				Add the medication to the users record
				addMedicationRecord2(pharmacyField.getText(), nameField.getText(), strengthField.getText(), barcodeField.getText(), Integer.parseInt(tabletsField.getValue().toString()));
//				Move to the result screen
				resultTab(true);
			}
			catch(Exception e) {
//				Move to the result screen
				resultTab(false);
			}
		}
	}
	
//	Informs the user if they have successfully added their new medication
	public void resultTab(Boolean success) {
//		Resets the GUI
		removeAll();
		revalidate();
		repaint();
		JLabel message = new JLabel();
		message.setFont(new Font(message.getFont().getName(), Font.PLAIN, 18));
//		Medication added successfully
		if (success == true) {
			message.setText("Medication successfully added!");
		}
//		Medication not added
		else {
			message.setText("Oops! Something went wrong!");
		}
		add(message);
	}
	
//	This was my preferable attempt to storing user's records, utilising SQL, however the university wanted us to use text files for some weird reason...
//	Therefore this method is never used
	public void addMedicationRecord(String company, String medication, String strength, String barcode, Integer tablets) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
//			Variables removed for data protection
			Connection conn = DriverManager.getConnection(DB_URL, user, password);
//			Statement statement = conn.createStatement();
			
			String query = "INSERT INTO medication (company, medication, strength, barcode, tablets)" + " values (?, ?, ?, ?, ?)";
			PreparedStatement preparedStatement = conn.prepareStatement(query);
			preparedStatement.setString(1, company);
		    preparedStatement.setString(2, medication);
		    preparedStatement.setString(3, strength);
		    preparedStatement.setString(4, barcode);
		    preparedStatement.setInt(5, tablets);
		    preparedStatement.execute();
		    conn.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
//	Adds the new medication to the text file
	public void addMedicationRecord2(String company, String medication, String strength, String barcode, Integer tablets) {
		try {
//			Opens medication.txt and writes in the new medication
			FileWriter fstream = new FileWriter("medication.txt", true);
			BufferedWriter out = new BufferedWriter(fstream);
			out.write("\n" + company + ":::" + medication + ":::" + strength + ":::" + barcode + ":::" + tablets);
			out.close();
			resetFields();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
