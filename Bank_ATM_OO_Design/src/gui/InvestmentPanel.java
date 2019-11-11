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
public class InvestmentPanel extends JPanel implements ActionListener {

    private JComboBox<String> securityAccountsList;
    private JTextArea accountTextArea;
    private final String selectOne = ">Select one";

    public InvestmentPanel() {
        setLayout(null);

        int windowHeight = OperationFrame.getInstance().getHeight();
        int windowWidth = OperationFrame.getInstance().getWidth();

        int x = windowWidth / 10 * 6;
        int y = windowHeight / 15;
        int increment = 50;
        int buttonWidth = 180;
        int buttonHeight = 25;

        JButton securityButton = new JButton("Open a security account");
        securityButton.setBounds(x, y + increment * 1, buttonWidth, buttonHeight);
        add(securityButton);
        securityButton.addActionListener(this);
        
        JButton butStockButton = new JButton("Buy stock");
        butStockButton.setBounds(x, y + increment * 3, buttonWidth, buttonHeight);
        add(butStockButton);
        butStockButton.addActionListener(this);

        JButton sellStockButton = new JButton("Sell stock");
        sellStockButton.setBounds(x, y + increment * 4, buttonWidth, buttonHeight);
        add(sellStockButton);
        sellStockButton.addActionListener(this);

        JButton returnButton = new JButton("Return");
        returnButton.setBounds(x, y + increment * 6, buttonWidth, buttonHeight);
        add(returnButton);
        returnButton.addActionListener(this);

        x = windowWidth / 14;
        y = windowHeight / 10;
        increment = 25;
        int textWidth = 290;

        JLabel titleLabel = new JLabel("Security accounts: ");
        titleLabel.setBounds(x, y, textWidth, 25);
        add(titleLabel);

        securityAccountsList = new JComboBox<String>();
        securityAccountsList.addItem(selectOne);
        securityAccountsList.setBounds(x, y + increment * 1, textWidth, 25);
        add(securityAccountsList);
        updateSecurityAccountList();

        JLabel stockInfoLabel = new JLabel("Account Information:");
        stockInfoLabel.setBounds(x, y + increment * 4, textWidth, 25);
        add(stockInfoLabel);

        accountTextArea = new JTextArea();
        accountTextArea.setLineWrap(true);
        accountTextArea.setEditable(false);
        JScrollPane jsp = new JScrollPane(accountTextArea);
        jsp.setBounds(x, y + increment * 5, textWidth, 200);
        add(jsp);
        updateInfo();
    }

    private void updateSecurityAccountList() {
        securityAccountsList.removeAllItems();
        securityAccountsList.addItem(selectOne);
        String userID = OperationFrame.getInstance().getUserID();
        String[] accountList = BankPortal.getInstance().getBank().getAccountList(SharedConstants.SEC);
        for (String accountID : accountList) {
            System.out.println("value = " + accountID);
            if (BankPortal.getInstance().getBank().isUserAccount(userID, accountID)) {
                securityAccountsList.addItem(accountID);
            }
        }
    }
    
    public void updateInfo() {
		accountTextArea.setText("");
		String userID = OperationFrame.getInstance().getUserID();
		String info = BankPortal.getInstance().getUserInfo(userID);
		accountTextArea.append("\n\n");
        accountTextArea.append(info);
        accountTextArea.append("\n\n");
	}

    @Override
    public void actionPerformed(ActionEvent e) {

        if( e.getActionCommand() == "Return" ) {
            setEnabled(false);
            setVisible(false);
            OperationFrame.getInstance().setAccountsInfoPanel();
        }

        if( e.getActionCommand() == "Open a security account" ) {
            String userID = OperationFrame.getInstance().getUserID();
            String[] savingAccountList = BankPortal.getInstance().getBank().getAccountList(SharedConstants.SAV);
            // Whether there is a saving account
            if (savingAccountList.length == 0) {
                JOptionPane.showMessageDialog(null, "Please open a saving account first!");
            } else {
                // Select a saving account
                Object selectedValue = JOptionPane.showInputDialog(null, "Choose one",
                        "Input", JOptionPane.INFORMATION_MESSAGE, null, savingAccountList,
                        savingAccountList[0]);

                String selectedSavingAccount = selectedValue.toString();

                // Whether there is enough money
                double balance = BankPortal.getInstance().getBank().getSavingsAccount(selectedSavingAccount).getBalance();
                if (balance < SharedConstants.SAVINGS_AMOUNT_THRESHOLD) {
                    JOptionPane.showMessageDialog(null, "You should have at least "
                            + SharedConstants.SAVINGS_AMOUNT_THRESHOLD + " USD in this saving account!");
                } else {
                    BankPortal.getInstance().openAccount(SharedConstants.BANK_ID, userID, SharedConstants.SEC, selectedSavingAccount);
                    updateSecurityAccountList();
                    updateInfo();
                }

            }

        }
        
        if( e.getActionCommand() == "Buy stock" ) {
        	String selectedSecurityAccount = securityAccountsList.getSelectedItem().toString();
        	if( selectedSecurityAccount.contentEquals(selectOne) ) {
        		JOptionPane.showMessageDialog(null, "Please select a security account!");
        	}
        	else {        		
        		try {
        			// select a stock
            		String[] stocksList = BankPortal.getInstance().getAllStockID();
            		Object selectedValue = JOptionPane.showInputDialog(null, "Choose one",
                            "Input", JOptionPane.INFORMATION_MESSAGE, null, stocksList,
                            stocksList[0]);
        			String selectedStock = selectedValue.toString();  // NullPointerException
        			
        			// how many shares
        			int shares = 0;       			
        			String inputValue = JOptionPane.showInputDialog("How many shares would you like?");
    				shares = Integer.parseInt(inputValue);    // NumberFormatException	
    				if( shares > 0 ) {
    					String result = BankPortal.getInstance().buyStock(selectedStock, selectedSecurityAccount, shares);   
                        switch(result) {
                        	case SharedConstants.ERR_INSUFFICIENT_BALANCE:
                        		JOptionPane.showMessageDialog(null, "You do not have enough balance!");
                        		break;
                        	default:
                        		// SharedConstants.SUCCESS_TRANSACTION
                        		JOptionPane.showMessageDialog(null, "Purchased successfully!");
                            	updateInfo();
                        } 
    				}
    				else {
    					JOptionPane.showMessageDialog(null, "Please input a positive integer!");
    				}
                       		
        		}
        		catch(NullPointerException error) {
        			System.out.println(error);
        		}
        		catch(NumberFormatException error) {
        			System.out.println(error);
        			JOptionPane.showMessageDialog(null, "Please input an integer!");
        		}           
        	}       	
        }
        
        if( e.getActionCommand() == "Sell stock" ) {
        	String selectedSecurityAccount = securityAccountsList.getSelectedItem().toString();
        	if( selectedSecurityAccount.contentEquals(selectOne) ) {
        		JOptionPane.showMessageDialog(null, "Please select a security account!");
        	}
        	else {
        		try {
        			// select your stock
        			// Do you have a share of this stock?
            		String[] stocksList = BankPortal.getInstance().getAllStockID();
            		Object selectedValue = JOptionPane.showInputDialog(null, "Choose one",
                            "Input", JOptionPane.INFORMATION_MESSAGE, null, stocksList,
                            stocksList[0]);
        			String selectedStock = selectedValue.toString();  // NullPointerException
        			
        			// How many shares would you like to sell?
        			// Do you have enough shares of this stock?
        			int shares = 0;       			
        			String inputValue = JOptionPane.showInputDialog("How many shares would you like to sell?");
    				shares = Integer.parseInt(inputValue);    // NumberFormatException	
    				if( shares > 0 ) {
    					String result = BankPortal.getInstance().sellStock(selectedStock, selectedSecurityAccount, shares); 
                        switch(result) {
                        	case SharedConstants.ERR_STOCK_NOT_EXIST:
                        		JOptionPane.showMessageDialog(null, "Are you sure you have purchased this stock?");
                        		break;
                        	case SharedConstants.ERR_INSUFFICIENT_BALANCE:
                        		JOptionPane.showMessageDialog(null, "Are you sure you have enough shares of this stock?");
                        		break;
                        	case SharedConstants.ERR_INSUFFICIENT_STOCK:
                        		JOptionPane.showMessageDialog(null, "You don't have sufficient stocks.");
                        		break;
                        	default:
                        		// SharedConstants.SUCCESS_TRANSACTION
                        		JOptionPane.showMessageDialog(null, "Sold successfully!");
                            	updateInfo();
                        }    		
    				}
    				else {
    					JOptionPane.showMessageDialog(null, "Please input a positive integer!");
    				}                      			        			
        		}
        		catch(NullPointerException error) {
        			System.out.println(error);
        		}
        		catch(NumberFormatException error) {
        			System.out.println(error);
        			JOptionPane.showMessageDialog(null, "Please input an integer!");
        		}
        	}
        }
    }
}
