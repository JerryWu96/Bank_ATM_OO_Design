package gui;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import backend.BankPortal;
import backend.SharedConstants;

/*
Author: Ziqi Tan
*/
public class TransactionPanel extends JPanel implements ActionListener {
	
	private JComboBox<String> accountsList;
	private JComboBox<String> currencyList;
	private final String selectOne = ">Select one";
	
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
        
        JButton logoutButton = new JButton("Return");
        logoutButton.setBounds(x, y+increment*5, 100, 25);
        add(logoutButton);
        logoutButton.addActionListener(this);
        
        x = windowWidth/9;
        y = windowHeight/10;
        increment = 25;
        int textWidth = 220;
        
        // Accounts List
        JLabel titleLabel = new JLabel("Select an account: ");
        titleLabel.setBounds(x, y, textWidth, 25);
        add(titleLabel);
        
        accountsList = new JComboBox<String>();
        accountsList.setBounds(x, y+increment*1, textWidth, 25);
        add(accountsList);
        updateAccountsListBox();
        
        // Currency List
        JLabel currencyLabel = new JLabel("Select a currency: ");
        currencyLabel.setBounds(x, y+increment*3, textWidth, 25);
        add(currencyLabel);
        
        currencyList = new JComboBox<String>();
        currencyList.setBounds(x, y+increment*4, textWidth, 25);
        add(currencyList);
        updateCurrencyListBox();
       
	}
	
	/*
	 * Method: updateAccountsListBox
	 * Function: update the account list in JComboBox
	 * */
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
	
	/*
	 * Method: updateCurrencyListBox
	 * Function: update currency list in JComboBox
	 * */
	public void updateCurrencyListBox() {
		currencyList.removeAllItems();
		currencyList.addItem(selectOne);
		String[] currencies = BankPortal.getInstance().getBank().getCurrencyList();
		for( String currency: currencies ) {
			currencyList.addItem(currency);
		}
	}
	
	/*
	 * Method: isAccountSelected
	 * Function: check whether an account has been selected.
	 * */
	private boolean isAccountSelected() {
		String selectedAccount = accountsList.getSelectedItem().toString();
		if( selectedAccount.equals(selectOne) ) {
			return false;
		}
		return true;
	}
	
	/*
	 * Method: isCurrencySelected
	 * Function: check whether an currency has been selected.
	 * */
	private boolean isCurrencySelected() {
		String selectedCurrency = currencyList.getSelectedItem().toString();
		if( selectedCurrency.equals(selectOne) ) {
			return false;
		}
		return true;
	}
		
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if( e.getActionCommand() == "Return" ) {
			setEnabled(false);
			setVisible(false);
			OperationFrame.getInstance().setAccountsInfoPanel();
		}
		
		if( e.getActionCommand() == "Withdraw" ) {
			if( isAccountSelected() && isCurrencySelected() ) {
				try {
					String inputValue = JOptionPane.showInputDialog("Withdraw:");
					double amount = Double.parseDouble(inputValue);
					String userID = OperationFrame.getInstance().getUserID();
					String accountID = accountsList.getSelectedItem().toString();					
	                String selectedCurrency = currencyList.getSelectedItem().toString();
	                String result = BankPortal.getInstance().withdraw(userID, accountID, amount, selectedCurrency);
	                if (result.equals(SharedConstants.ERR_INSUFFICIENT_BALANCE)) {
	                    JOptionPane.showMessageDialog(null, "Your selected account has insufficient balance!");
	                }
	                else {
	                	JOptionPane.showMessageDialog(null, "Withdraw successfully!");
	                }
	                
				}
				catch(Exception error) {
					System.out.println(error);
					JOptionPane.showMessageDialog(null, "Please input a number!");
				}
			}
			else {
				JOptionPane.showMessageDialog(null, "Please select an account and a currency!");
			}
		}
		
		if( e.getActionCommand() == "Deposit" ) {
			if( isAccountSelected() && isCurrencySelected() ) {
				try {
					String inputValue = JOptionPane.showInputDialog("Deposit:");
					double amount = Double.parseDouble(inputValue);
					String userID = OperationFrame.getInstance().getUserID();
					String accountID = accountsList.getSelectedItem().toString();					
	                String selectedCurrency = currencyList.getSelectedItem().toString();
	                BankPortal.getInstance().deposit(userID, accountID, amount, selectedCurrency);
	                JOptionPane.showMessageDialog(null, "Deposit successfully!");
				}
				catch(Exception error) {
					System.out.println(error);
					JOptionPane.showMessageDialog(null, "Please input a number!");
				}				
			}
			else {
				JOptionPane.showMessageDialog(null, "Please select an account and a currency!");
			}
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
		
	}	
}
