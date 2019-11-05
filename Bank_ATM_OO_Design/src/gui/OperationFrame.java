package gui;
import javax.swing.JFrame;
/*
Author: Ziqi Tan
*/
public class OperationFrame extends JFrame {
	
	private static ATMGUI atm;
	private static LoginPanel loginPanel;
	private static AccountsInfoPanel accountsInfoPanel;
	private static TransactionPanel transactionPanel;
	private static RegisterPanel registerPanel;
	private static TransferPanel transferPanel;
	private static TransferWindow transferWindow;
	private static InvestmentPanel investmentPanel;
	private static ManagerPanel managerPanel;
	// private static Customer user;
	// private static Manager manager;
	
	public OperationFrame(ATMGUI _atm) {
				
		atm = _atm;
		// user = null;
		// manager = null;
		
		setTitle("Welcome to Bank of BBUU!");
		setSize(600,500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);         		
		setLocationRelativeTo(null);  // this will center your app
		System.out.println("ATM opeartion frame has been created.");
		
		loginPanel = new LoginPanel(this, atm);
		setLoginPanel();	
		accountsInfoPanel = new AccountsInfoPanel(this, atm);
		transactionPanel = new TransactionPanel(this, atm);
		registerPanel = new RegisterPanel(this, atm);
		transferPanel = new TransferPanel(this, atm);
		transferWindow = new TransferWindow(this, atm);
		investmentPanel = new InvestmentPanel(this, atm);
		managerPanel = new ManagerPanel(this, atm);
		
		setVisible(true);
	}
	
	/**
	 * setter() for showing which JPanel on the JFrame.
	 * */
	public void setLoginPanel() {
		add(loginPanel);
		loginPanel.setEnabled(true);
		loginPanel.setVisible(true);
	}
	
	public void setAccountsInfoPanel() {
		add(accountsInfoPanel);
		accountsInfoPanel.updateInfo();
		accountsInfoPanel.setEnabled(true);
		accountsInfoPanel.setVisible(true);
	}
	
	public void setTransactionPanel() {
		add(transactionPanel);
		transactionPanel.setEnabled(true);
		transactionPanel.setVisible(true);
	}
	
	public void setRegisterPanel() {
		add(registerPanel);
		registerPanel.setEnabled(true);
		registerPanel.setVisible(true);
	}
	
	public void setTransferPanel() {
		add(transferPanel);
		transferPanel.setEnabled(true);
		transferPanel.setVisible(true);
	}
	
	public void setTransferWindow() {
		add(transferWindow);
		transferWindow.setEnabled(true);
		transferWindow.setVisible(true);
	}
	
	public void setInvestmentPanel() {
		add(investmentPanel);
		investmentPanel.setEnabled(true);
		investmentPanel.setVisible(true);
	}
	
	public void setManagerPanel() {
		add(managerPanel);
		managerPanel.setEnabled(true);
		managerPanel.setVisible(true);
	}
	
	
	/*
	 * Method: setCustomer
	 * Function: set current customer
	 * */
	/*public void setCustomer(Customer cus) {
		user = cus;
	}*/
}
