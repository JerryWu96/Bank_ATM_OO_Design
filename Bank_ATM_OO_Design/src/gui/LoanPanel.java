package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

/*
Author: Ziqi Tan
*/
public class LoanPanel extends JPanel implements ActionListener {
	
	private JComboBox<String> currencyList;
	private JComboBox<String> loanList;
	
	public LoanPanel() {
		
		setLayout(null);
		
		int windowHeight = OperationFrame.getInstance().getHeight();
		int windowWidth = OperationFrame.getInstance().getWidth();
		
		int x = windowWidth/3*2;
		int y = windowHeight/7;
		int increment = 50;
		
		JButton takeLoanButton = new JButton("Take loan");
		takeLoanButton.setBounds(x, y+increment*1, 100, 25);
        add(takeLoanButton);
        takeLoanButton.addActionListener(this);
        
        JButton payLoanButton = new JButton("Pay loan");
        payLoanButton.setBounds(x, y+increment*2, 100, 25);
        add(payLoanButton);
        payLoanButton.addActionListener(this);
		
		JButton returnButton = new JButton("Return");
        returnButton.setBounds(x, y+increment*6, 100, 25);
        add(returnButton);
        returnButton.addActionListener(this);
		
		x = windowWidth/9;
        y = windowHeight/10;
        increment = 25;
        int textWidth = 220;
        
        JLabel titleLabel = new JLabel("Select currency: ");
        titleLabel.setBounds(x, y, textWidth, 25);
        add(titleLabel);
        
        currencyList = new JComboBox<String>();
        currencyList.addItem(">Select one");
        currencyList.setBounds(x, y+increment*1, textWidth, 25);
        add(currencyList);
        
        JLabel loanListLabel = new JLabel("Your current loans: ");
        loanListLabel.setBounds(x, y+increment*3, textWidth, 25);
        add(loanListLabel);
        
        loanList = new JComboBox<String>();
        loanList.addItem(">Select one");
        loanList.setBounds(x, y+increment*4, textWidth, 25);
        add(loanList);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if( e.getActionCommand() == "Return" ) {
			setEnabled(false);
			setVisible(false);
			OperationFrame.getInstance().setTransactionPanel();
		}
		
		if( e.getActionCommand() == "Take loan" ) {
			
		}
		
		if( e.getActionCommand() == "Pay loan" ) {
			
		}
		
	}

}
