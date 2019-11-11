package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import backend.BankPortal;

/*
Author: Ziqi Tan
*/
public class LoanPanel extends JPanel implements ActionListener {
	
	private JComboBox<String> currencyList;
	private JComboBox<String> loanList;
	private final String selectOne = ">Select one";
	
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
        currencyList.setBounds(x, y+increment*1, textWidth, 25);
        add(currencyList);
        updateCurrencyListBox();
        
        JLabel loanListLabel = new JLabel("Your current loans: ");
        loanListLabel.setBounds(x, y+increment*3, textWidth, 25);
        add(loanListLabel);
        
        loanList = new JComboBox<String>();
        loanList.setBounds(x, y+increment*4, textWidth, 25);
        add(loanList);
        updateLoanListBox();
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
	
	private void updateLoanListBox() {
	    
        loanList.removeAllItems();
        loanList.addItem(selectOne);
        String userID = OperationFrame.getInstance().getUserID();
        String[] list = BankPortal.getInstance().getBank().getLoanList(userID);
        for (String loanID : list) {
            loanList.addItem(loanID);
        }	    
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if( e.getActionCommand() == "Return" ) {
			setEnabled(false);
			setVisible(false);
			OperationFrame.getInstance().setTransactionPanel();
		}
		
		if( e.getActionCommand() == "Take loan" ) {
			try {
				String inputValue = JOptionPane.showInputDialog("Take loan:");
				double amount = Double.parseDouble(inputValue);
				String userID = OperationFrame.getInstance().getUserID();					            
				String selectedCurrency = currencyList.getSelectedItem().toString();				
	            String result = BankPortal.getInstance().takeLoan(userID, amount, selectedCurrency);
	            if (result.equals("Not Eligible")) {
	                JOptionPane.showMessageDialog(null, "You are no longer permitted to take out a loan!\n" +
	                        "Please pay off loans to retrieve your collaterals");
	            } 
	            else {
	                JOptionPane.showMessageDialog(null, "New loan created! \n You have " +
	                        BankPortal.getInstance().getCustomerCollateral() + " remaining collaterals.");
	            }
	            updateLoanListBox();	            
			}
			catch(Exception error) {
				System.out.println(error);
				JOptionPane.showMessageDialog(null, "Please input a number.");
			}			
		}
		
		if( e.getActionCommand() == "Pay loan" ) {
			String selectedLoan = loanList.getSelectedItem().toString();
            BankPortal.getInstance().payoffLoan(selectedLoan);
            updateLoanListBox();
		}
	}

}
