//import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.print.DocFlavor.URL;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

public class ResultsTab extends JPanel {
	
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
	private JLabel trafficLabel;
	private JLabel informationBox;

	public ResultsTab() {
		setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();

//		Panel containing all input fields
		JPanel medicationDetailsPanel = new JPanel(new GridBagLayout());
		Border line = BorderFactory.createTitledBorder("Medication");
		((javax.swing.border.TitledBorder)line).setTitleFont(new Font(medicationDetailsPanel.getFont().getName(), Font.PLAIN, 24)); 
		Border empty = new EmptyBorder(10,10,10,10); 
		CompoundBorder border = new CompoundBorder(line, empty); 
		medicationDetailsPanel.setBorder(border);
		
		gc.gridx = 0;
		gc.gridy = 0;
		gc.weightx = 0;
		gc.insets = new Insets(10, 0, 0, 0);

		// Pharmacy Entry
		gc.gridy = 0;
		pharmacyLabel = new JLabel("Pharmaceutical Company Name:");
		pharmacyLabel.setFont(new Font(pharmacyLabel.getFont().getName(), Font.PLAIN, 16));
		pharmacyField = new JTextField(20);
		pharmacyField.setFont(new Font(pharmacyField.getFont().getName(), Font.PLAIN, 16));
		pharmacyField.setHorizontalAlignment(SwingConstants.CENTER);
		medicationDetailsPanel.add(pharmacyLabel, gc);
		gc.gridy = 1;
		medicationDetailsPanel.add(pharmacyField, gc);

		// Name Entry
		gc.gridy = 2;
		nameLabel = new JLabel("Name of Medication:");
		nameLabel.setFont(new Font(nameLabel.getFont().getName(), Font.PLAIN, 16));
		nameField = new JTextField(20);
		nameField.setFont(new Font(nameField.getFont().getName(), Font.PLAIN, 16));
		nameField.setHorizontalAlignment(SwingConstants.CENTER);
		medicationDetailsPanel.add(nameLabel, gc);
		gc.gridy = 3;
		medicationDetailsPanel.add(nameField, gc);

		// Strength Entry
		gc.gridy = 4;
		strengthLabel = new JLabel("Strength (mg):");
		strengthLabel.setFont(new Font(strengthLabel.getFont().getName(), Font.PLAIN, 16));
		strengthField = new JTextField(20);
		strengthField.setFont(new Font(strengthField.getFont().getName(), Font.PLAIN, 16));
		strengthField.setHorizontalAlignment(SwingConstants.CENTER);
		medicationDetailsPanel.add(strengthLabel, gc);
		gc.gridy = 5;
		medicationDetailsPanel.add(strengthField, gc);

		// Barcode of medication
		gc.gridy = 6;
		barcodeLabel = new JLabel("Barcode:");
		barcodeLabel.setFont(new Font(barcodeLabel.getFont().getName(), Font.PLAIN, 16));
		barcodeField = new JTextField(20);
		barcodeField.setFont(new Font(barcodeField.getFont().getName(), Font.PLAIN, 16));
		barcodeField.setHorizontalAlignment(SwingConstants.CENTER);
		medicationDetailsPanel.add(barcodeLabel, gc);
		gc.gridy = 7;
		medicationDetailsPanel.add(barcodeField, gc);

		// Tablets per package
		gc.gridy = 8;
		tabletsLabel = new JLabel("Tablets:");
		tabletsLabel.setFont(new Font(tabletsLabel.getFont().getName(), Font.PLAIN, 16));
		// tabletsField = new JTextField(20);
		tabletsField = new JSpinner();
		tabletsField.setFont(new Font(tabletsField.getFont().getName(), Font.PLAIN, 16));
		// tabletsField.setHorizontalAlignment(SwingConstants.CENTER);
		Dimension prefSize = tabletsField.getPreferredSize();
		prefSize = new Dimension(265, prefSize.height);
		tabletsField.setPreferredSize(prefSize);
		medicationDetailsPanel.add(tabletsLabel, gc);
		gc.gridy = 9;
		medicationDetailsPanel.add(tabletsField, gc);

		// Submit button
		gc.gridy = 10;
		JButton submitButton = new JButton("Submit");
		submitButton.setFont(new Font(submitButton.getFont().getName(), Font.PLAIN, 16));
		medicationDetailsPanel.add(submitButton, gc);
		
		gc.gridx = 0;
		gc.gridy = 0;
		gc.weightx = 1;
		gc.anchor = GridBagConstraints.WEST;
		gc.fill = GridBagConstraints.HORIZONTAL;
		add(medicationDetailsPanel, gc);
		
//		Set up the traffic light model
		gc.gridx = 1;
		ImageIcon image = new ImageIcon(ResultsTab.class.getResource("/resources/TrafficLightEmpty.png"));
		trafficLabel = new JLabel("", image, JLabel.CENTER); 
		add(trafficLabel, gc);
		
		gc.gridx = 0;
		gc.gridy = 1;
		gc.gridwidth = 2;
		gc.fill = GridBagConstraints.HORIZONTAL;
		gc.anchor = GridBagConstraints.CENTER;
		
//		JLabel informing the user of drug-drug interactions. By default the user has not entered any information, so ask them to "Please fill..."
		informationBox = new JLabel(); 
		line = BorderFactory.createTitledBorder("Information");
		((javax.swing.border.TitledBorder)line).setTitleFont(new Font(informationBox.getFont().getName(), Font.PLAIN, 24)); 
		empty = new EmptyBorder(10,10,10,10); 
		border = new CompoundBorder(line, empty); 
		informationBox.setBorder(border);
		informationBox.setText("Please fill in the fields above");
		informationBox.setFont(new Font(informationBox.getFont().getName(), Font.PLAIN, 18));
		
		add(informationBox, gc);
		
//		On submitButton being clicked
		submitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//				Reset all error messages
				resetLabels();
				informationBox.setText("Please fill in the fields above");
//				Reset the traffic light 
				trafficLabel.setIcon(new ImageIcon(ResultsTab.class.getResource("/resources/TrafficLightEmpty.png")));
//				Validate the users input
				validateManual();
			}
		});
	}
	
//	Resets all input labels, removing all error messages
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
	
//	Checks the validity of the users input
	public void validateManual() {
//		boolean to monitor errors
		boolean flag = true;
		
//		If pharmacyField is empty
		if (pharmacyField.getText().length() == 0) {
			pharmacyLabel.setText("Pharmaceutical Company Name: please enter a name");
			pharmacyLabel.setForeground(Color.RED);
			flag = false;
		}
//		If nameField is empty
		if (nameField.getText().length() == 0) {
			nameLabel.setText("Name of Medication: please enter a name");
			nameLabel.setForeground(Color.RED);
			flag = false;
		}
//		If strengthField is empty
		if (strengthField.getText().length() == 0) {
			strengthLabel.setText("Strength (mg): please enter a strength");
			strengthLabel.setForeground(Color.RED);
			flag = false;
		}
//		If strengthField is not a digit or integer
		else if(!(strengthField.getText().matches("\\d*\\.?\\d+"))) {
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
//		If the barcodeField is empty
		if (barcodeField.getText().length() == 0) {
			barcodeLabel.setText("Barcode: please enter a barcode");
			barcodeLabel.setForeground(Color.RED);
			flag = false;
		}
//		If the barcodeField is not numerical
		else if(!(barcodeField.getText().matches("\\d+"))) {
			barcodeLabel.setText("Barcode: invalid barcode");
			barcodeLabel.setForeground(Color.RED);
			flag = false;
		}
//		If the barcodeField is not 13 in length
		else if (barcodeField.getText().length() != 13) {
			barcodeLabel.setText("Barcode: incorrect barcode size");
			barcodeLabel.setForeground(Color.RED);
			flag = false;
		}
		
//		If no errors occur
		if (flag == true) {
//			Change the traffic light to green. It will always be green since no drug-drug interaction checking actually needs to be done in this assignment
			trafficLabel.setIcon(new ImageIcon(ResultsTab.class.getResource("/resources/TrafficLightWhole3.png")));
//			Change the informationBox to let the user know there is no problem taking the new medication
			informationBox.setText("<html>There are no drug-drug interactions between " + nameField.getText() + " and your current medication</html>");
		}
	}
}
