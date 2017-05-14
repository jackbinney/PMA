import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.Border;

public class GUI extends JFrame {
	
	public GUI() {
//		Design details of the JFrame
		JFrame frame = this;
		setTitle("PMA System");
		setMinimumSize(new Dimension(600, 700));
//		this.setUndecorated(true);
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
	    int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
	    int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
	    frame.setLocation(x, y);
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    
//	    Sets the GUI to show the default home screen
	    defaultScreen();
	    
	    setVisible(true);
	}
	
//	Clears and resets the GUI
	public void wipeScreen() {
		getContentPane().removeAll();
		revalidate();
		repaint();
	}
	
//	The screen the user sees upon launching the software
	public void defaultScreen() {
		
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();
		
		gc.gridx = 0;
		gc.gridy = 0;
		
//		Title design
		JLabel titleLabel = new JLabel("Personal Medication Advisor");
//		titleLabel.setBackground(new Color(180,180,180));
//		titleLabel.setOpaque(true);
		titleLabel.setFont(new Font(titleLabel.getFont().getName(), Font.BOLD, 26));
		panel.add(titleLabel, gc);
		
		gc.gridy = 1;
		JLabel hintLabel = new JLabel("Please select an option below");
		panel.add(hintLabel, gc);

//		Add by barcode button
		gc.insets = new Insets(30,0,0,0);
		gc.gridy = 2;
		gc.fill = GridBagConstraints.NONE;
		JButton addBarcodeButton = new JButton("Add New Medication - Barcode");
		addBarcodeButton.setFont(new Font(addBarcodeButton.getFont().getName(), Font.PLAIN, 18));
		addBarcodeButton.setPreferredSize(new Dimension(300, 100));
		addBarcodeButton.setFocusPainted(false);
		
//		On clicking add by barcode, reset the screen, update the header, show the AddBarcodeTab instead
		addBarcodeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				wipeScreen();
				JPanel content = new JPanel(new GridBagLayout());
				GridBagConstraints gc = new GridBagConstraints();
				gc.gridx = 0;
				gc.gridy = 0;
				gc.weightx = 1;
				gc.weighty = 0.1;
				gc.fill = GridBagConstraints.HORIZONTAL;
				gc.anchor = GridBagConstraints.NORTH;
				
				Header header = new Header("Add Medication By Barcode");
				content.add(header, gc);
				header.backButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						wipeScreen();
						defaultScreen();
						revalidate();
						repaint();
					}
				});
				
				gc.fill = GridBagConstraints.BOTH;
				gc.gridy = 1;
				gc.weightx = 1;
				gc.weighty = 0.9;
				gc.anchor = GridBagConstraints.CENTER;
				content.add(new AddBarcodeTab(), gc);
				
				setContentPane(content);
				revalidate();
				repaint();
			}
		});
		
		panel.add(addBarcodeButton, gc);
		
//		Add medication manually button
		gc.gridy = 3;
		gc.insets = new Insets(15,0,0,0);
		JButton addManualButton = new JButton("Add New Medication - Manually");
		addManualButton.setFont(new Font(addManualButton.getFont().getName(), Font.PLAIN, 18));
		addManualButton.setPreferredSize(new Dimension(300, 100));
		
//		On clicking add manually button, reset the screen, update the header, show the AddManualTab instead
		addManualButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				wipeScreen();
				JPanel content = new JPanel(new GridBagLayout());
				GridBagConstraints gc = new GridBagConstraints();
				gc.gridx = 0;
				gc.gridy = 0;
				gc.weightx = 1;
				gc.weighty = 0.1;
				gc.fill = GridBagConstraints.HORIZONTAL;
				gc.anchor = GridBagConstraints.NORTH;
				
				Header header = new Header("Add Medication Manually");
				content.add(header, gc);
				header.backButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						wipeScreen();
						defaultScreen();
						revalidate();
						repaint();
					}
				});
				
				gc.gridy = 1;
				gc.weightx = 0.1;
				gc.weighty = 0.9;
				gc.anchor = GridBagConstraints.NORTH;
				content.add(new AddManualTab(), gc);
				
				setContentPane(content);
				revalidate();
				repaint();
			}
		});
		
		panel.add(addManualButton, gc);
		
//		Current medication button
		gc.gridy = 4;
		JButton viewButton = new JButton("View Current Medication");
		viewButton.setPreferredSize(new Dimension(300, 100));
		viewButton.setFont(new Font(viewButton.getFont().getName(), Font.PLAIN, 18));
		
//		On clicking the view meication button, reset the screen, update the header, show the CurrentMedicationTab instead
		viewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				wipeScreen();
				JPanel content = new JPanel(new GridBagLayout());
				GridBagConstraints gc = new GridBagConstraints();
				gc.gridx = 0;
				gc.gridy = 0;
				gc.weightx = 1;
				gc.weighty = 0.1;
				gc.fill = GridBagConstraints.HORIZONTAL;
				gc.anchor = GridBagConstraints.NORTH;
				
				Header header = new Header("Current Medications");
				content.add(header, gc);
				header.backButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						wipeScreen();
						defaultScreen();
						revalidate();
						repaint();
					}
				});
				
				gc.gridy = 1;
				gc.weightx = 0.9;
				gc.weighty = 0.9;
				gc.insets = new Insets(0,10,0,10);
				gc.anchor = GridBagConstraints.NORTH;
				content.add(new CurrentMedicationTab(), gc);
				
				setContentPane(content);
				revalidate();
				repaint();
			}
		});
		
		panel.add(viewButton, gc);
		
//		Check compatibility button
		gc.gridy = 5;
		JButton resultsButton = new JButton("Check Compatibility");
		resultsButton.setPreferredSize(new Dimension(300, 100));
		resultsButton.setFont(new Font(resultsButton.getFont().getName(), Font.PLAIN, 18));
		
//		On clicking check compatability button, reset the screen, update the header, show the ResultsTab instead
		resultsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				wipeScreen();
				JPanel content = new JPanel(new GridBagLayout());
				GridBagConstraints gc = new GridBagConstraints();
				gc.gridx = 0;
				gc.gridy = 0;
				gc.weightx = 1;
				gc.weighty = 0.1;
				gc.fill = GridBagConstraints.HORIZONTAL;
				gc.anchor = GridBagConstraints.NORTH;
				
				Header header = new Header("Check Compatability");
				content.add(header, gc);
				header.backButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						wipeScreen();
						defaultScreen();
						revalidate();
						repaint();
					}
				});
				
				gc.gridy = 1;
				gc.weightx = 0.9;
				gc.weighty = 0.9;
				gc.insets = new Insets(0,10,0,10);
				gc.anchor = GridBagConstraints.NORTH;
				content.add(new ResultsTab(), gc);
				
				setContentPane(content);
				revalidate();
				repaint();
			}
		});
		
		panel.add(resultsButton, gc);
		
//		Exit button
		gc.gridy = 6;
		gc.insets = new Insets(30,0,0,0);
		JButton exitButton = new JButton("Exit");
		exitButton.setBackground(Color.RED);
		exitButton.setForeground(Color.WHITE);
		exitButton.setPreferredSize(new Dimension(150, 50));
		exitButton.setFont(new Font(exitButton.getFont().getName(), Font.PLAIN, 14));
		
//		On click, close the program
		exitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		panel.add(exitButton, gc);
		
		getContentPane().add(panel);
	}
	
	public static void main(String[] args) {
		new GUI();
	}
}
