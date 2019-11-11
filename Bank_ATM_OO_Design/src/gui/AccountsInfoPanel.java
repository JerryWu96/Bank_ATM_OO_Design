package gui;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import backend.BankPortal;
import backend.SharedConstants;

/*
Author: Ziqi Tan
*/
public class AccountsInfoPanel extends JPanel implements ActionListener {
	
	private JComboBox<String> accountsList;
	private JTextArea accountTextArea;
	private final String selectOne = ">Select one";
	
	public AccountsInfoPanel() {

		setLayout(null);
		
		int windowHeight = OperationFrame.getInstance().getHeight();
		int windowWidth = OperationFrame.getInstance().getWidth();
		
		int x = windowWidth/10*6;
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
               
        JButton closeButton = new JButton("Close selected account");
        closeButton.setBounds(x, y+increment*3, buttonWidth, buttonHeight);
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
           
        x = windowWidth/14;
        y = windowHeight/10;
        increment = 25;
        int textWidth = 290;
        
        JLabel titleLabel = new JLabel("Select an account: ");
        titleLabel.setBounds(x, y, textWidth, 25);
        add(titleLabel);
        
        accountsList = new JComboBox<String>();
        accountsList.addItem(selectOne);
        accountsList.setBounds(x, y+increment*1, textWidth, 25);
        add(accountsList);
        
        JLabel accountInfoLabel = new JLabel("Account Information:");
        accountInfoLabel.setBounds(x, y+increment*4,textWidth, 25);
        add(accountInfoLabel);
        
        accountTextArea = new JTextArea();
        accountTextArea.setLineWrap(true);
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
		accountTextArea.setText("");
		String userID = OperationFrame.getInstance().getUserID();
		String info = BankPortal.getInstance().getUserInfo(userID);
		accountTextArea.append("\n\n");
        accountTextArea.append(info);
        accountTextArea.append("\n\n");
	}
	
	public void updateAccountsListBox() {
		accountsList.removeAllItems();
		accountsList.addItem(selectOne);
		String userID = OperationFrame.getInstance().getUserID();
        String[] accountList = BankPortal.getInstance().getBank().getAccountList(SharedConstants.CK);
        for (String accountID : accountList) {
            if (BankPortal.getInstance().getBank().isUserAccount(userID, accountID)) {
            	accountsList.addItem(accountID);
            }
        }
        accountList = BankPortal.getInstance().getBank().getAccountList(SharedConstants.SAV);
        for (String accountID : accountList) {
            if (BankPortal.getInstance().getBank().isUserAccount(userID, accountID)) {
            	accountsList.addItem(accountID);
            }
        }
	}
	
	/**
	 * Method: actionPerformed
	 * */
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if( e.getActionCommand() == "Inquire" ) {
			updateInfo();			
		}
		
		if( e.getActionCommand() == "Open a checking account" ) {
			accountTextArea.append("Opening a checking account.");
			String userID = OperationFrame.getInstance().getUserID();
			BankPortal.getInstance().openAccount(SharedConstants.BANK_ID, userID, SharedConstants.CK);			
	        updateAccountsListBox();
	        updateInfo();
		}
		
		if( e.getActionCommand() == "Open a saving account" ) {
			accountTextArea.append("Opening a saving account.");
			String userID = OperationFrame.getInstance().getUserID();
			BankPortal.getInstance().openAccount(SharedConstants.BANK_ID, userID, SharedConstants.SAV);
			updateAccountsListBox();
	        updateInfo();
		}
		
		if( e.getActionCommand() == "Close selected account" ) {
			
			accountTextArea.append("Closing a selected account.");
			String userID = OperationFrame.getInstance().getUserID();
			String selectedAccount = accountsList.getSelectedItem().toString();
			
			if( selectedAccount.equals(selectOne) ) {
				JOptionPane.showMessageDialog(null, "Please select an account!");
			}
			else {
				// Closing checking/saving account
				if (BankPortal.getInstance().getBank().isCheckingAccount(selectedAccount)) {
                    String reply = BankPortal.getInstance().closeAccount(userID, selectedAccount, SharedConstants.CK);
                    if (reply.equals(SharedConstants.ERR_ACCOUNT_NOT_EXIST)) {
                        JOptionPane.showMessageDialog(null, "Account ID does not exist!");
                    } 
                } 
				else if (BankPortal.getInstance().getBank().isSavingsAccount(selectedAccount)) {
                    String reply = BankPortal.getInstance().closeAccount(userID, selectedAccount, SharedConstants.SAV);
                    if (reply.equals(SharedConstants.ERR_ACCOUNT_NOT_EXIST)) {
                        JOptionPane.showMessageDialog(null, "Account ID does not exist!");
                    } 
                }
				updateAccountsListBox();
		        updateInfo();				
			}
			
		}
		
		if( e.getActionCommand() == "Transaction" ) {
			setEnabled(false);
			setVisible(false);
			OperationFrame.getInstance().setTransactionPanel();
		}
		
		if( e.getActionCommand() == "Investment" ) {
			setEnabled(false);
			setVisible(false);
			OperationFrame.getInstance().setInvestmentPanel();
		}
		
		if( e.getActionCommand() == "Logout" ) {
			
			OperationFrame.getInstance().setUserID(null);
			setEnabled(false);
			setVisible(false);
			OperationFrame.getInstance().setLoginPanel();
			
			JOptionPane.showMessageDialog(null,"Logout successfully!");
		}		
	}
}
