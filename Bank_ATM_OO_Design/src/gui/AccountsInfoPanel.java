package gui;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/*
Author: Ziqi Tan
*/
public class AccountsInfoPanel extends JPanel implements ActionListener {

	private OperationFrame operationFrame;
	private ATMGUI atm;
	
	private JComboBox<String> accountsList;
	private JTextArea accountTextArea;
	
	public AccountsInfoPanel(OperationFrame operationFrame, ATMGUI atm) {
		
		this.operationFrame = operationFrame;
		this.atm = atm;
		setLayout(null);
		
		int windowHeight = operationFrame.getHeight();
		int windowWidth = operationFrame.getWidth();
		
		int x = windowWidth/9*5;
		int y = windowHeight/15;
		int increment = 50;
		int buttonWidth = 180;
		int buttonHeight = 25;
				
		JButton checkingButton = new JButton("Open a checking account");
		checkingButton.setBounds(x, y+increment, buttonWidth, buttonHeight);
        add(checkingButton);
        checkingButton.addActionListener(this);
        
        JButton savingButton = new JButton("Open a saving account");
        savingButton.setBounds(x, y+increment*2, buttonWidth, buttonHeight);
        add(savingButton);
        savingButton.addActionListener(this);
        
        JButton securityButton = new JButton("Open a security account");
        securityButton.setBounds(x, y+increment*3, buttonWidth, buttonHeight);
        add(securityButton);
        securityButton.addActionListener(this);
        
        JButton closeButton = new JButton("Close selected account");
        closeButton.setBounds(x, y+increment*4, buttonWidth, buttonHeight);
        add(closeButton);
        closeButton.addActionListener(this);
        
        JButton transactionButton = new JButton("Transaction");
        transactionButton.setBounds(x, y+increment*5, buttonWidth, buttonHeight);
        add(transactionButton);
        transactionButton.addActionListener(this);
        
        JButton investButton = new JButton("Investment");
        investButton.setBounds(x, y+increment*6, buttonWidth, buttonHeight);
        add(investButton);
        investButton.addActionListener(this);
        
        JButton logoutButton = new JButton("Logout");
        logoutButton.setBounds(x, y+increment*7, buttonWidth, buttonHeight);
        add(logoutButton);
        logoutButton.addActionListener(this);
           
        x = windowWidth/9;
        y = windowHeight/10;
        increment = 25;
        int textWidth = 220;
        
        JLabel titleLabel = new JLabel("Select an account to inquire: ");
        titleLabel.setBounds(x, y, textWidth, 25);
        add(titleLabel);
        
        accountsList = new JComboBox<String>();
        accountsList.addItem(">Select one");
        accountsList.setBounds(x, y+increment*1, textWidth, 25);
        add(accountsList);
        
        JButton inquiryButton = new JButton("Inquire");
        inquiryButton.setBounds(x+140, y+increment*2, 80, 25);
        add(inquiryButton);
        inquiryButton.addActionListener(this);
        
        JLabel accountInfoLabel = new JLabel("Account Information:");
        accountInfoLabel.setBounds(x, y+increment*4,textWidth, 25);
        add(accountInfoLabel);
        
        accountTextArea = new JTextArea();
        accountTextArea.setText("Account:\nAccount:\nAccount:\nAccount:\nAccount:\n");
        accountTextArea.setEditable(false);
        JScrollPane jsp = new JScrollPane(accountTextArea);
        jsp.setBounds(x, y+increment*5, textWidth, 200);
        add(jsp);
            
	}
	
	/*
	 * Method: updateInfo
	 * Function: update the information on the customer panel, 
	 * 			including balance in all accounts.
	 * */
	public void updateInfo() {
		
		/*Customer cus = operationFrame.getCustomer();
		DecimalFormat df = new DecimalFormat(".00");*/
		
		/*double checkingBalance = cus.getCheckingAccount().getBalance();
		checking.setText("Checking Account: " + df.format(checkingBalance) + " USD");
		
		double savingBalance = cus.getSavingAccount().getBalance();
		saving.setText("Saving Account: " + df.format(savingBalance) + " USD");
		
		double euroBalance = cus.getEuroAccount().getBalance();
		euro.setText("Euro Account: " + df.format(euroBalance) + " EUR");
		
		double cnyBalance = cus.getCNYAccount().getBalance();
		cny.setText("CNY Account: " + df.format(cnyBalance) + " CNY");*/
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if( e.getActionCommand() == "Inquire" ) {
			
			// accountTextArea.setText("");
			accountTextArea.append("Account : \n");
			
		}
		
		if( e.getActionCommand() == "Open a checking account" ) {
			accountTextArea.setText("");
			accountTextArea.append("Opening a checking account.");
		}
		
		if( e.getActionCommand() == "Open a saving account" ) {
			
		}
		
		if( e.getActionCommand() == "Open a security account" ) {
			
		}
		
		if( e.getActionCommand() == "Close selected account" ) {
			
		}
		
		if( e.getActionCommand() == "Transaction" ) {
			setEnabled(false);
			setVisible(false);
			operationFrame.setTransactionPanel();
		}
		
		if( e.getActionCommand() == "Investment" ) {
			setEnabled(false);
			setVisible(false);
			operationFrame.setInvestmentPanel();
		}
		
		if( e.getActionCommand() == "Logout" ) {
			/*operationFrame.setCustomer(null);*/
			
			setEnabled(false);
			setVisible(false);
			operationFrame.setLoginPanel();
			
			JOptionPane.showMessageDialog(null,"Logout successfully!");
		}
		
	}

}
