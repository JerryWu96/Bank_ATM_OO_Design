package gui;
import javax.swing.JFrame;
/*
Author: Ziqi Tan
*/
public class OperationFrame extends JFrame {
	
	private static LoginPanel loginPanel;
	private static AccountsInfoPanel accountsInfoPanel;
	private static TransactionPanel transactionPanel;
	private static RegisterPanel registerPanel;
	private static TransferPanel transferPanel;
	private static TransferWindow transferWindow;
	private static LoanPanel loanPanel;
	private static InvestmentPanel investmentPanel;
	private static ManagerPanel managerPanel;
	private static String userID;
	// private static Manager manager;
	
	private static OperationFrame operationFrame = null;
	
	// Constructor
	private OperationFrame() {				
		// user = null;
		// manager = null;	
	}
	
	// Singleton Pattern
	public static OperationFrame getInstance() {
		if( operationFrame == null ) {
			operationFrame = new OperationFrame();
		}
		return operationFrame;
	}
	
	public void run() {
		setTitle("Welcome to Bank of BBUU!");
		setSize(600,500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);         		
		setLocationRelativeTo(null);  // this will center your app
		System.out.println("ATM opeartion frame has been created.");
		
		loginPanel = new LoginPanel();
		setLoginPanel();
		accountsInfoPanel = new AccountsInfoPanel();
		transactionPanel = new TransactionPanel();
		registerPanel = new RegisterPanel();
		transferPanel = new TransferPanel();
		transferWindow = new TransferWindow();
		investmentPanel = new InvestmentPanel();
		loanPanel = new LoanPanel();
		managerPanel = new ManagerPanel();
		
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
		transactionPanel.updateAccountsListBox();
		transactionPanel.updateCurrencyListBox();
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
	
	public void setLoanPanel() {
		add(loanPanel);
		loanPanel.setEnabled(true);
		loanPanel.setVisible(true);
	}
	
	public void setManagerPanel() {
		add(managerPanel);
		managerPanel.setEnabled(true);
		managerPanel.setVisible(true);
	}
	
	/*
	 * getter()
	 * */
	public String getUserID() {
		return this.userID;
	}
		
	/*
	 * Method: setCustomer
	 * Function: set current customer
	 * */
	public void setUserID(String userID) {
		this.userID = userID;
	}
}
