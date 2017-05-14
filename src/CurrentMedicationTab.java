import java.awt.*;
import java.io.*;
import java.sql.*;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.*;
import javax.swing.text.DefaultFormatter;

public class CurrentMedicationTab extends JPanel {
	
//	Table and table model used to show the medication
	DefaultTableModel model = new DefaultTableModel();
	JTable table = new JTable(model);
	
	public CurrentMedicationTab() {
		setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();
		
//		Set up the tables design
		table.setFont(new Font(table.getFont().getName(), Font.PLAIN, 16));
		
//		Set up the columns within the table
		model.addColumn("Pharmaceutical Company");
		model.addColumn("Medication");
		model.addColumn("Strength (mg)");
		model.addColumn("Barcode");
		model.addColumn("Tablets");
		
		gc.gridx = 0;
		gc.gridy = 0;
		gc.weightx = 1;
		gc.gridwidth = 2;
		gc.fill = GridBagConstraints.HORIZONTAL;
//		Add the table to the GUI
		add(table.getTableHeader(), gc);
		
		gc.gridy = 1;
		add(table, gc);
		
		Font font = new Font(table.getFont().getName(), Font.PLAIN, 14);
		Font fontBold = new Font(table.getFont().getName(), Font.BOLD, 14);
		gc.insets = new Insets(40,0,0,10);
		
//		Frequency calculations section
//		Selected medication label and value
		gc.gridy = 2;
		gc.weightx = 0;
		gc.gridwidth = 1;
		JLabel selectedMedicationLabel = new JLabel("Selected Medication:");
		selectedMedicationLabel.setFont(fontBold);
		add(selectedMedicationLabel, gc);
		
		gc.gridx = 1;
//		This will later show the medication the user has clicked on in the table. By default, the user has not clicked on any medication, so show "Please select..."
		JLabel resultMedicationLabel = new JLabel("Please selected a medicine");
		resultMedicationLabel.setFont(font);
		add(resultMedicationLabel, gc);
		
//		Frequency label and spinner
		gc.gridy = 3;
		gc.gridx = 0;
		gc.insets = new Insets(10,0,0,0);
		JLabel frequencyLabel = new JLabel("Frequency:");
		frequencyLabel.setFont(fontBold);
		add(frequencyLabel, gc);
		
		gc.gridx = 1;
//		Set up the spinner to start at 1 and max out at 100
		SpinnerNumberModel model = new SpinnerNumberModel(0,0,100,1); 
		JSpinner frequencySpinner = new JSpinner(model);
//		Set design preferences
		Dimension size = frequencySpinner.getPreferredSize();
		size = new Dimension(10, size.height);
		frequencySpinner.setPreferredSize(size);
		frequencySpinner.setFont(font);
//		Readjust the up and down arrows to be on the left
		JComponent editor = frequencySpinner.getEditor();
		JSpinner.DefaultEditor spinnerEditor = (JSpinner.DefaultEditor)editor;
		spinnerEditor.getTextField().setHorizontalAlignment(JTextField.LEFT);
		JFormattedTextField jtf = spinnerEditor.getTextField();
		DefaultFormatter formatter = (DefaultFormatter)jtf.getFormatter();
		formatter.setCommitsOnValidEdit(true);
		add(frequencySpinner, gc);
		
//		Dosage calculations label and value
		gc.gridy = 4;
		gc.gridx = 0;
		JLabel weeklyDosageLabel = new JLabel("Weekly Dosage:");
		weeklyDosageLabel.setFont(fontBold);
		add(weeklyDosageLabel, gc);
		
		gc.gridx = 1;
//		This will later show the calculated dosage. By default the user has not selected a medication so show "Please select..."
		JLabel resultDosageLabel = new JLabel("Please select a medicine");
		resultDosageLabel.setFont(font);
		add(resultDosageLabel, gc);
		
//		Upon making a selection in the table
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
			public void valueChanged(ListSelectionEvent event) {
//				Set the resultMedicationLabel to be the name of the medication from the table
				resultMedicationLabel.setText(table.getValueAt(table.getSelectedRow(), 1).toString());
//				Fetch the string value of the dosage
				String tempSelectedStrength = table.getValueAt(table.getSelectedRow(), 2).toString();
//				Convert it into a double
				Double selectedStrength = Double.parseDouble(tempSelectedStrength.substring(0, tempSelectedStrength.length() - 2));
//				Calculate the weekly dosage by multiplying the frequency by dosage per tablet
				double resultDosage = calculateWeeklyDosage(Integer.parseInt(frequencySpinner.getValue().toString()), selectedStrength);
//				Update the resultDosageLabel
				resultDosageLabel.setText("" + resultDosage + "mg");
			}
		});
		
//		Do the same as above, but when the frequency spinner changes value
		frequencySpinner.addChangeListener(new ChangeListener() {
			
			public void stateChanged(ChangeEvent e) {
				try {
					resultMedicationLabel.setText(table.getValueAt(table.getSelectedRow(), 1).toString());
					String tempSelectedStrength = table.getValueAt(table.getSelectedRow(), 2).toString();
					Double selectedStrength = Double.parseDouble(tempSelectedStrength.substring(0, tempSelectedStrength.length() - 2));
					double resultDosage = calculateWeeklyDosage(Integer.parseInt(frequencySpinner.getValue().toString()), selectedStrength);
					resultDosageLabel.setText("" + resultDosage + "mg");
				}
				catch(Exception except) {
					
				}
			}
		});
		
		refreshResults();
	}
	
//	Weekly dosage calculator
	public static double calculateWeeklyDosage(int frequency, double strength) {
		return 7 * frequency * strength;
	}
	
//	Fetches the users medication to be used in the table
	public ArrayList<String[]> getCurrentMedication() {
		ArrayList<String[]> result = new ArrayList<String[]>();
		
		try {
//			open medication.txt and split every buffer value of ":::"
			File file = new File("medication.txt");
			BufferedReader reader = new BufferedReader(new FileReader(file));	
			
			String text = null;
//			Each drug is separated by a new line
			while ((text = reader.readLine()) != null) {
				if (text.length() > 0) {
					result.add(text.split(":::"));
				}
			}
			
			reader.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
//	Updates the medication table
	public void refreshResults() {
		int rowCount = model.getRowCount();
		//Remove rows one by one from the end of the table
		for (int i = rowCount - 1; i >= 0; i--) {
		    model.removeRow(i);
		}
		
		ArrayList<String[]> current = getCurrentMedication();
		
//		Add each drug into the table one by one
		for(String[] x : current) {
//			Company, Name, Strength, Barcode, Tablets per package
			model.addRow(new Object[]{x[0], x[1], x[2] + "mg", x[3], x[4]});
		}
		resizeColumnWidth();
	}
	
//	Automatically resizes the column widths to fit in the largest value of each column
	public void resizeColumnWidth() {
	    final TableColumnModel columnModel = table.getColumnModel();
	    for (int column = 0; column < table.getColumnCount(); column++) {
	        int width = 15; // Min width
	        for (int row = 0; row < table.getRowCount(); row++) {
	        	table.setRowHeight(row, 30);
	            TableCellRenderer renderer = table.getCellRenderer(row, column);
	            Component comp = table.prepareRenderer(renderer, row, column);
	            width = Math.max(comp.getPreferredSize().width +1 , width);
	        }
	        
	        TableColumn tempColumn = columnModel.getColumn(column);
            TableCellRenderer headerRenderer = tempColumn.getHeaderRenderer();
            if (headerRenderer == null) {
                headerRenderer = table.getTableHeader().getDefaultRenderer();
            }
            Object headerValue = tempColumn.getHeaderValue();
            Component headerComp = headerRenderer.getTableCellRendererComponent(table, headerValue, false, false, 0, column);
            width = Math.max(width, headerComp.getPreferredSize().width);
	        
//          Sets the columns max possible width at 300
            if(width > 300)
	            width=300;
	        columnModel.getColumn(column).setPreferredWidth(width + 10);
	    }
	}
}
