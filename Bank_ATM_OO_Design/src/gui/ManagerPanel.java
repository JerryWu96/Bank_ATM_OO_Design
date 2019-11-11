package gui;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ListIterator;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import backend.BankPortal;

/*
Author: Ziqi Tan
*/
public class ManagerPanel extends JPanel implements ActionListener {
	
	private JTextArea report;
	
	public ManagerPanel() {
		
		int windowHeight = OperationFrame.getInstance().getHeight();
        int windowWidth = OperationFrame.getInstance().getWidth();
        int x = windowWidth / 9 * 6;
        int y = windowHeight / 10;
        int increment = 50;
        int buttonWidth = 150;
        int buttonHeight = 25;
        setLayout(null);
		
		JButton getDailyReportButton = new JButton("Get Daily Report");
		getDailyReportButton.setBounds(x, y, buttonWidth, buttonHeight);
		add(getDailyReportButton);
		getDailyReportButton.addActionListener(this);
		
		JButton getReportButton = new JButton("Get Report");
		getReportButton.setBounds(x, y+increment*1, buttonWidth, buttonHeight);
		add(getReportButton);
		getReportButton.addActionListener(this);
		
		JButton nextDayButton = new JButton("Next day");
		nextDayButton.setBounds(x, y+increment*2, buttonWidth, buttonHeight);
		add(nextDayButton);
		nextDayButton.addActionListener(this);
		
		JButton stockManipultingButton = new JButton("Stock Manipultor");
		stockManipultingButton.setBounds(x, y+increment*3, buttonWidth, buttonHeight);
		add(stockManipultingButton);
		stockManipultingButton.addActionListener(this);
		
		JButton logoutButton = new JButton("Logout");
		logoutButton.setBounds(x, y+increment*4, buttonWidth, buttonHeight);
		add(logoutButton);
		logoutButton.addActionListener(this);
		
		x = windowWidth/14;
        y = windowHeight/20;
        increment = 25;
        int textWidth = 320;    
        int textHeight = 330;
        
        JLabel accountInfoLabel = new JLabel("Report");
        accountInfoLabel.setBounds(x, y,textWidth, 25);
        add(accountInfoLabel);
        
        report = new JTextArea();
        report.setLineWrap(true);
        report.setEditable(false);
        JScrollPane jsp = new JScrollPane(report);
        jsp.setBounds(x, y+increment*1, textWidth, textHeight);
        add(jsp);
		
	}
	
	private void updateReportTextArea(String text) {
		report.setText("");
		report.append(text);
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
		
		if( e.getActionCommand().equals("Get Daily Report") ) {
			
			// input which day you want to inquire
			try {
				String inputvalue = JOptionPane.showInputDialog("Input the day");
				int day = Integer.parseInt(inputvalue);	
				String text = BankPortal.getInstance().getReportByDay(day);
				updateReportTextArea(text);				
			}
			catch(NullPointerException error) {
    			System.out.println(error);
    		}
			catch(NumberFormatException error) {
    			System.out.println(error);
    			JOptionPane.showMessageDialog(null, "Please select an integer!");
    		}  						
		}
		
		if( e.getActionCommand().equals("Next day") ) {
			BankPortal.getInstance().nextDay();
		}
		
		if( e.getActionCommand().equals("Stock Manipultor") ) {
			setEnabled(false);
			setVisible(false);
			OperationFrame.getInstance().setStockManipulatorPanel();
		}
		
	}

}
