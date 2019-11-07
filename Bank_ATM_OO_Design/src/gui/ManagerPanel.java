package gui;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ListIterator;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/*
Author: Ziqi Tan
*/
public class ManagerPanel extends JPanel implements ActionListener {
	
	private JTextArea report;
	private JScrollPane jsp;
	
	public ManagerPanel() {
		
		JButton submit = new JButton("Get Report");
		add(submit);
		submit.addActionListener(this);
		
		JButton logoutButton = new JButton("Logout");
		add(logoutButton);
		logoutButton.addActionListener(this);
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {

		if( e.getActionCommand().equals("Logout") ) {
			setEnabled(false);
			setVisible(false);
			OperationFrame.getInstance().setLoginPanel();			
			JOptionPane.showMessageDialog(null,"Logout successfully!");			
		}
		
		if( e.getActionCommand().equals("Get Report") ) {
			
		}
		
	}

}