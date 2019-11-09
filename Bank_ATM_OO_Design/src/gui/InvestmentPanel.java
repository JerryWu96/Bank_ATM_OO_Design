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
		
		int x = windowWidth/9*5;
		int y = windowHeight/15;
		int increment = 50;
		int buttonWidth = 180;
		int buttonHeight = 25;
				
		JButton butStockButton = new JButton("Buy stock");
		butStockButton.setBounds(x, y+increment, buttonWidth, buttonHeight);
        add(butStockButton);
        butStockButton.addActionListener(this);
        
        JButton sellStockButton = new JButton("Sell stock");
        sellStockButton.setBounds(x, y+increment*2, buttonWidth, buttonHeight);
        add(sellStockButton);
        sellStockButton.addActionListener(this);
        
        JButton securityButton = new JButton("Open a security account");
        securityButton.setBounds(x, y+increment*3, buttonWidth, buttonHeight);
        add(securityButton);
        securityButton.addActionListener(this);
        
        JButton returnButton = new JButton("Return");
        returnButton.setBounds(x, y+increment*4, buttonWidth, buttonHeight);
        add(returnButton);
        returnButton.addActionListener(this);
        
        x = windowWidth/9;
        y = windowHeight/10;
        increment = 25;
        int textWidth = 220;
        
        JLabel titleLabel = new JLabel("Security accounts: ");
        titleLabel.setBounds(x, y, textWidth, 25);
        add(titleLabel);
        
        securityAccountsList = new JComboBox<String>();
        securityAccountsList.addItem(selectOne);
        securityAccountsList.setBounds(x, y+increment*1, textWidth, 25);
        add(securityAccountsList);
        updateSecurityAccountList();
        
        JButton inquiryButton = new JButton("Inquire");
        inquiryButton.setBounds(x+140, y+increment*2+5, 80, 25);
        add(inquiryButton);
        inquiryButton.addActionListener(this);
        
        JLabel stockInfoLabel = new JLabel("Stock Information:");
        stockInfoLabel.setBounds(x, y+increment*4,textWidth, 25);
        add(stockInfoLabel);
        
        accountTextArea = new JTextArea();
        accountTextArea.setText("Account:\nAccount:\nAccount:\nAccount:\nAccount:\n");
        accountTextArea.setEditable(false);
        JScrollPane jsp = new JScrollPane(accountTextArea);
        jsp.setBounds(x, y+increment*5, textWidth, 200);
        add(jsp);
	}
	
	private void updateSecurityAccountList() {
		securityAccountsList.removeAllItems();
		securityAccountsList.addItem(selectOne);
		String userID = OperationFrame.getInstance().getUserID();
        String[] accountList = BankPortal.getInstance().getBank().getAccountList(SharedConstants.SEC);
        for (String accountID : accountList) {
            if (BankPortal.getInstance().getBank().isUserAccount(userID, accountID)) {
            	securityAccountsList.addItem(accountID);
            }
        }
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
			if( savingAccountList.length == 0 ) {
				JOptionPane.showMessageDialog(null, "Please open a saving account first!");
			}			
			else {
				// Select a saving account
				Object selectedValue = JOptionPane.showInputDialog(null, "Choose one",
				"Input", JOptionPane.INFORMATION_MESSAGE, null, savingAccountList,
				savingAccountList[0]);
				
				String selectedSavingAccount = selectedValue.toString();
				
				// Whether there is enough money
				double balance = BankPortal.getInstance().getBank().getSavingsAccount(selectedSavingAccount).getBalance();
				if( balance < SharedConstants.SAVINGS_AMOUNT_THRESHOLD ) {
					JOptionPane.showMessageDialog(null, "You should have at least " 
							+ SharedConstants.SAVINGS_AMOUNT_THRESHOLD + " USD in this saving account!");
				}
				else {
					BankPortal.getInstance().openAccount(SharedConstants.BANK_ID, userID, SharedConstants.SEC, selectedSavingAccount);
					updateSecurityAccountList();
				}
				
			}
			
		}
		
	}
}
