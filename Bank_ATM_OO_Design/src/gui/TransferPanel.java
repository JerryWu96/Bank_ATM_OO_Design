package gui;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import backend.BankPortal;
import backend.SharedConstants;

/*
Author: Ziqi Tan
*/
public class TransferPanel extends JPanel implements ActionListener {
	
	private JComboBox<String> sourceAccountList;
	private JComboBox<String> targetAccountList;
	private JComboBox<String> currencyList;
	
	private JTextField amountField;
	
	private final String selectOne = ">Select one";
	
	public TransferPanel() {
		
		int windowHeight = OperationFrame.getInstance().getHeight();
		int windowWidth = OperationFrame.getInstance().getWidth();
		
		int x = windowWidth/5;
		int y = windowHeight/8;
		int increment = 25;
		int textWidth = 200;
		setLayout(null);
		
		// Source accounts List
        JLabel sourceAccountLabel = new JLabel("Source account: ");
        sourceAccountLabel.setBounds(x, y, textWidth, 25);
        add(sourceAccountLabel);
        
        sourceAccountList = new JComboBox<String>();
        sourceAccountList.setBounds(x, y+increment, textWidth, 25);
        add(sourceAccountList);
        
        // Target accounts List
        JLabel targetAccountLabel = new JLabel("Target account: ");
        targetAccountLabel.setBounds(x, y+increment*3, textWidth, 25);
        add(targetAccountLabel);
        
        targetAccountList = new JComboBox<String>();
        targetAccountList.setBounds(x, y+increment*4, textWidth, 25);
        add(targetAccountList);
        
        // Currency List
        JLabel currencyLabel = new JLabel("Select a currency: ");
        currencyLabel.setBounds(x, y+increment*6, textWidth, 25);
        add(currencyLabel);
        
        currencyList = new JComboBox<String>();
        currencyList.setBounds(x, y+increment*7, textWidth, 25);
        add(currencyList);
        updateCurrencyListBox();
        
        // Transfer amount
        JLabel amountLabel = new JLabel("Amount");
        amountLabel.setBounds(x, y+increment*9, textWidth, 25);
        add(amountLabel);
        
        amountField = new JTextField();
        amountField.setBounds(x, y+increment*10, textWidth, 25);
        add(amountField);
        
        JButton submitButton = new JButton("Submit");
        submitButton.setBounds(x, y+increment*12, 80, 25);
        add(submitButton);
        submitButton.addActionListener(this);	
        
        JButton returnButton = new JButton("Return");
        returnButton.setBounds(x + 100, y+increment*12, 80, 25);
        add(returnButton);
        returnButton.addActionListener(this);		
	}
	
	public void updateSourceAccountListBox() {
		sourceAccountList.removeAllItems();
		sourceAccountList.addItem(selectOne);
		String userID = OperationFrame.getInstance().getUserID();
        String[] accountList = BankPortal.getInstance().getBank().getAccountList(SharedConstants.CK);
        for (String accountID : accountList) {
            if (BankPortal.getInstance().getBank().isUserAccount(userID, accountID)) {
            	sourceAccountList.addItem(accountID);
            }
        }
        accountList = BankPortal.getInstance().getBank().getAccountList(SharedConstants.SAV);
        for (String accountID : accountList) {
            if (BankPortal.getInstance().getBank().isUserAccount(userID, accountID)) {
            	sourceAccountList.addItem(accountID);
            }
        }
	}
		
	public void updateTargetAccountListBox() {
		targetAccountList.removeAllItems();
		targetAccountList.addItem(selectOne);
        String[] accountList = BankPortal.getInstance().getBank().getAccountList(SharedConstants.CK);
        for (String accountID : accountList) {
        	targetAccountList.addItem(accountID);
        }
        accountList = BankPortal.getInstance().getBank().getAccountList(SharedConstants.SAV);
        for (String accountID : accountList) {
        	targetAccountList.addItem(accountID);
        }
	}
	
	private void updateCurrencyListBox() {
		currencyList.removeAllItems();
		currencyList.addItem(selectOne);
		String[] currencies = BankPortal.getInstance().getBank().getCurrencyList();
		for( String currency: currencies ) {
			currencyList.addItem(currency);
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if( e.getActionCommand().contentEquals("Return") ) {
			setEnabled(false);
			setVisible(false);
			OperationFrame.getInstance().setTransactionPanel();
		}
		
		if( e.getActionCommand() == "Submit" ) {
			String sourceAccount = sourceAccountList.getSelectedItem().toString();
			String targetAccount = targetAccountList.getSelectedItem().toString();
			String selectedCurrency = currencyList.getSelectedItem().toString();
			String userID = OperationFrame.getInstance().getUserID();
			if( sourceAccount.equals(selectOne) || targetAccount.equals(selectOne) ) {
				JOptionPane.showMessageDialog(null, "Please select the accounts!");
			}
			else if( selectedCurrency.equals(selectOne) ) {
				JOptionPane.showMessageDialog(null, "Please select a currency!");
			}
			else {
				try {
					String inputValue = amountField.getText();
					double amount = Double.parseDouble(inputValue);
		            String result = BankPortal.getInstance().transfer(userID, sourceAccount, targetAccount, amount, selectedCurrency);
		            if (result.equals(SharedConstants.ERR_INSUFFICIENT_BALANCE)) {
		                JOptionPane.showMessageDialog(null, "Your selected account does not have enough balance.");
		            } 
		            else {
		                JOptionPane.showMessageDialog(null, "Transfer succeeded!\n You have transfered " +
		                        amount + " from your account: " + sourceAccount + " to the account: " + targetAccount);
		            }		            
				}
				catch( Exception error ) {
					System.out.println(error);
					JOptionPane.showMessageDialog(null, "Please input a number!");
				}
			}
		}
	}
}
