import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

// Shows the current tab to the user at the top of the screen, also contains a home button
public class Header extends JPanel {
	
	public JButton backButton;
	private String titleString;
	
	public Header(String titleString) {
//		titleString is the name of the current tab the viewer is on
		this.titleString = titleString;
		this.setLayout(new GridBagLayout());
		setBackground(new Color(180, 180, 180));
		GridBagConstraints gc = new GridBagConstraints();
		gc.gridx = 0;
		gc.gridy = 0;
		gc.weightx = 0;
		gc.ipady = 10;
		gc.ipadx = 10;
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		
//		Icon made by Dave Gandy from www.flaticon.com
//		http://www.flaticon.com/free-icon/home_25694
//		Allow the user to return to the home screen by clicking this button
		ImageIcon image = new ImageIcon(Header.class.getResource("/resources/home1.png"));
		backButton = new JButton(image);
		backButton.setContentAreaFilled(false);
		backButton.setBorder(BorderFactory.createEmptyBorder());
		
		add(backButton, gc);
		
		gc.gridx = 1;
		gc.weightx = 1;
		gc.anchor = GridBagConstraints.CENTER;
		System.out.println(backButton.getWidth());
		gc.insets = new Insets(0,-68,0,0);
		JLabel title = new JLabel(titleString);
		title.setFont(new Font(title.getFont().getName(), Font.BOLD, 24));
		add(title, gc);
	}
}