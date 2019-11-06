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
public class TransactionPanel extends JPanel implements ActionListener {
	
	private JComboBox<String> accountsList;
	private JComboBox<String> currencyList;
	
	public TransactionPanel() {

		setLayout(null);
		
		int windowHeight = OperationFrame.getInstance().getHeight();
		int windowWidth = OperationFrame.getInstance().getWidth();
		
		int x = windowWidth/3*2;
		int y = windowHeight/7;
		int increment = 50;
				
		JButton withdrawButton = new JButton("Withdraw");
		withdrawButton.setBounds(x, y+increment, 100, 25);
        add(withdrawButton);
        withdrawButton.addActionListener(this);
        
        JButton depositButton = new JButton("Deposit");
        depositButton.setBounds(x, y+increment*2, 100, 25);
        add(depositButton);
        depositButton.addActionListener(this);
        
        JButton transferButton = new JButton("Transfer");
        transferButton.setBounds(x, y+increment*3, 100, 25);
        add(transferButton);
        transferButton.addActionListener(this);
        
        JButton loanButton = new JButton("Loan");
        loanButton.setBounds(x, y+increment*4, 100, 25);
        add(loanButton);
        loanButton.addActionListener(this);
        
        JButton historyButton = new JButton("History");
        historyButton.setBounds(x, y+increment*5, 100, 25);
        add(historyButton);
        historyButton.addActionListener(this);
        
        JButton logoutButton = new JButton("Return");
        logoutButton.setBounds(x, y+increment*6, 100, 25);
        add(logoutButton);
        logoutButton.addActionListener(this);
        
        x = windowWidth/9;
        y = windowHeight/10;
        increment = 25;
        int textWidth = 220;
        
        JLabel titleLabel = new JLabel("Select an account: ");
        titleLabel.setBounds(x, y, textWidth, 25);
        add(titleLabel);
        
        accountsList = new JComboBox<String>();
        accountsList.addItem(">Select one");
        accountsList.setBounds(x, y+increment*1, textWidth, 25);
        add(accountsList);
        
        JLabel currencyLabel = new JLabel("Select a currency: ");
        currencyLabel.setBounds(x, y+increment*3, textWidth, 25);
        add(currencyLabel);
        
        currencyList = new JComboBox<String>();
        currencyList.addItem(">Select one");
        currencyList.setBounds(x, y+increment*4, textWidth, 25);
        add(currencyList);
        
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if( e.getActionCommand() == "Return" ) {
			setEnabled(false);
			setVisible(false);
			OperationFrame.getInstance().setAccountsInfoPanel();
		}
		
		if( e.getActionCommand() == "Withdraw" ) {

		}
		
		if( e.getActionCommand() == "Deposit" ) {

		}
		
		if( e.getActionCommand() == "Transfer" ) {
			setEnabled(false);
			setVisible(false);
			OperationFrame.getInstance().setTransferPanel();
		}
		
		if( e.getActionCommand() == "Loan" ) {
			setEnabled(false);
			setVisible(false);
			OperationFrame.getInstance().setLoanPanel();
		}
		
		if( e.getActionCommand() == "History" ) {

		}
		
	}
	
}
