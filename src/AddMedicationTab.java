import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.*;

import com.mysql.jdbc.Statement;

public class AddMedicationTab extends JPanel{
	
	private JButton barcodeButton;
	private JButton manualButton;
	private JTextField barcodeField;
	private JTextField pharmacyField;
	private JTextField nameField;
	private JTextField strengthField;
	private JTextField frequencyField;
	
	public AddMedicationTab() {
		defaultTab();
	}
	
	public void resetTab() {
		removeAll();
		revalidate();
		repaint();
	}
	
	public void defaultTab() {
		this.setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();
		
		gc.gridx = 0;
		gc.gridy = 0;
		gc.gridwidth = 2;
		JLabel prompt = new JLabel("Please select a method of entry");
		add(prompt, gc);
		
		gc.insets = new Insets(10,0,0,0);
		gc.gridwidth = 1;
		gc.gridy = 1;
		barcodeButton = new JButton("Scan Barcode");
		add(barcodeButton, gc);
		
		gc.gridx = 1;
		manualButton = new JButton("Manually");
		add(manualButton, gc);
		
		barcodeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Barcode");
				resetTab();
				addByBarcode();
			}
		});
		
		manualButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Manually");
				resetTab();
				addManually();
			}
		});
	}
	
	public void test() {
		System.out.println("TESTING");
	}
	
	public void addByBarcode() {
		setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();
		
		gc.gridx = 0;
		gc.gridy = 0;
		gc.weightx = 0.1;
		gc.weighty = 0.1;
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		JButton backButton = new JButton("Back");
		
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resetTab();
				defaultTab();
			}
		});
		
		add(backButton, gc);
		
		// Barcode Entry
		JPanel content = new JPanel(new GridBagLayout());
		
		gc.gridx = 0;
		gc.gridy = 0;
		gc.anchor = GridBagConstraints.CENTER;
		gc.insets = new Insets(10, 0, 0, 0);
		
		JLabel barcodeLabel = new JLabel("Barcode:");
		barcodeField = new JTextField(20);
		barcodeField.setHorizontalAlignment(SwingConstants.CENTER);
		content.add(barcodeLabel, gc);
		
		gc.gridy = 1;
		content.add(barcodeField, gc);

		gc.gridy = 2;
		JButton submitButton = new JButton("Submit");
		submitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				validateBarcode();
			}
		});
		content.add(submitButton, gc);
		
		gc.gridx = 0;
		gc.gridy = 1;
		gc.weightx = 0.9;
		gc.weighty = 0.9;
		gc.insets = new Insets(0,0,0,0);
		add(content, gc);
	}
	
	public void validateBarcode() {
		boolean flag = false;
		String errorMessage = "";
		
		if(barcodeField.getText().length() != 13) {
			errorMessage = errorMessage + "Barcode not right size\n";
		}
		if(!(barcodeField.getText().matches("\\d+"))) {
			errorMessage = errorMessage + "Barcode is not numeric";
		}
		
		if (errorMessage.length() == 0){
			flag = true;
		}
		else {
			System.out.println(errorMessage);
		}
		
		if (flag == true) {
			try {
				Class.forName("com.mysql.jdbc.Driver");
				Connection conn = DriverManager.getConnection(DB_URL, user, password);
				
				String query = "SELECT * FROM medication WHERE barcode=?";
				PreparedStatement preparedStatement = conn.prepareStatement(query);
				preparedStatement.setString(1, barcodeField.getText());
				ResultSet rs = preparedStatement.executeQuery();
				
				int size = 0;
				if (rs.last()) {
					size = rs.getRow();
					rs.beforeFirst();
				}
				
				if (size == 0) {
					System.out.println("NO RECORD FOUND");
				}
				else {
					System.out.println("RECORD FOUND");
				}
				
				conn.close();
			}
			catch(Exception e) {
				
			}
		}
	}
	
	public void addManually() {
		setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();
		
		gc.gridx = 0;
		gc.gridy = 0;
		gc.weightx = 0.1;
		gc.weighty = 0.1;
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		JButton backButton = new JButton("Back");
		
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resetTab();
				defaultTab();
			}
		});
		
		add(backButton, gc);
		
		JPanel content = new JPanel(new GridBagLayout());
		
		gc.gridx = 0;
		gc.gridy = 0;
		gc.anchor = GridBagConstraints.CENTER;
		gc.insets = new Insets(10, 0, 0, 0);
		
		// Pharmacy Entry
		gc.gridy = 0;
		JLabel pharmacyLabel = new JLabel("Pharmaceutical Company Name:");
		pharmacyField = new JTextField(20);
		pharmacyField.setHorizontalAlignment(SwingConstants.CENTER);
		content.add(pharmacyLabel, gc);
		gc.gridy = 1;
		content.add(pharmacyField, gc);
		
		// Name Entry
		gc.gridy = 2;
		JLabel nameLabel = new JLabel("Name of Medication:");
		nameField = new JTextField(20);
		nameField.setHorizontalAlignment(SwingConstants.CENTER);
		content.add(nameLabel, gc);
		gc.gridy = 3;
		content.add(nameField, gc);
		
		// Strength Entry
		gc.gridy = 4;
		JLabel strengthLabel = new JLabel("Strength (mg):");
		strengthField = new JTextField(20);
		strengthField.setHorizontalAlignment(SwingConstants.CENTER);
		content.add(strengthLabel, gc);
		gc.gridy = 5;
		content.add(strengthField, gc);
		
		// Frequency of medication
		gc.gridy = 6;
		JLabel frequencyLabel = new JLabel("Frequency:");
		frequencyField = new JTextField(20);
		frequencyField.setHorizontalAlignment(SwingConstants.CENTER);
		content.add(frequencyLabel, gc);
		gc.gridy = 7;
		content.add(frequencyField, gc);
		
		// Barcode of medication
		gc.gridy = 8;
		JLabel barcodeLabel = new JLabel("Barcode:");
		barcodeField = new JTextField(20);
		barcodeField.setHorizontalAlignment(SwingConstants.CENTER);
		content.add(barcodeLabel, gc);
		gc.gridy = 9;
		content.add(barcodeField, gc);
		
		// Submit button
		gc.gridy = 10;
		JButton submitButton = new JButton("Submit");
		submitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				validateManual();
			}
		});
		content.add(submitButton, gc);
		
		gc.gridx = 0;
		gc.gridy = 1;
		gc.weightx = 0.9;
		gc.weighty = 0.9;
		gc.insets = new Insets(0,0,0,0);
		add(content, gc);
	}
	
	public void validateManual() {
		boolean flag = false;
		String errorMessage = "";
		
		if (pharmacyField.getText().length() == 0) {
			errorMessage = errorMessage + "Please fill in the name of the pharmaceutical company\n"; 
		}
		if (nameField.getText().length() == 0) {
			errorMessage = errorMessage + "Please fill in the name of the medication\n";
		}
		if (strengthField.getText().length() == 0) {
			errorMessage = errorMessage + "Please fill in the strength of the medication\n";
		}
		if (frequencyField.getText().length() == 0) {
			errorMessage = errorMessage + "Please fill in the frequency of which you take the medication\n";
		}
		if (errorMessage.length() == 0) {
			flag = true;
		}
		else {
			System.out.println(errorMessage);
		}
		
		if (flag == true) {
			try {
				Class.forName("com.mysql.jdbc.Driver");
				Connection conn = DriverManager.getConnection(DB_URL, user, password);
				
				String query = "INSERT INTO medication (company, medication, strength, barcode, tablets)" + " values (?, ?, ?, ?, ?)";
				PreparedStatement preparedStatement = conn.prepareStatement(query);
				preparedStatement.setString(1, pharmacyField.getText());
			    preparedStatement.setString(2, nameField.getText());
			    preparedStatement.setString(3, strengthField.getText());
			    preparedStatement.setString(4, barcodeField.getText());
			    preparedStatement.setInt(5, Integer.parseInt(frequencyField.getText()));
			    preparedStatement.execute();
			    
			    conn.close();
			    
			    System.out.println("Medication successfully added");
			    resetTab();
			    defaultTab();
			}
			catch(Exception e) {
				
			}
		}
	}
	
	public void addMedicationRecord(String company, String medication, String strength, String barcode, Integer tablets) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
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
			System.out.println(e);
		}
	}
}
