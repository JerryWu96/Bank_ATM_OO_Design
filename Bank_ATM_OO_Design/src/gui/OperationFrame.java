package gui;

import backend.BankPortal;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

public class OperationFrame extends JFrame {

    private static LoginPanel loginPanel;
    private static AccountsInfoPanel accountsInfoPanel;
    private static TransactionPanel transactionPanel;
    private static RegisterPanel registerPanel;
    private static TransferPanel transferPanel;
    private static LoanPanel loanPanel;
    private static InvestmentPanel investmentPanel;
    private static ManagerPanel managerPanel;
    private static StockManipulatorPanel stockManipulatorPanel;
    private static String userID;

    private static OperationFrame operationFrame = null;

    // Constructor
    private OperationFrame() {
        addWindowListener(
                new WindowAdapter() {
                    public void windowClosing(WindowEvent e) {
                        BankPortal.getInstance().saveStateToDB();
                    }
                }
        );
    }

    // Singleton Pattern
    public static OperationFrame getInstance() {
        if (operationFrame == null) {
            operationFrame = new OperationFrame();
        }
        return operationFrame;
    }

    public void run() {
        setTitle("Welcome to Bank of Boston");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);  // this will center your app

        loginPanel = new LoginPanel();
        setLoginPanel();
        accountsInfoPanel = new AccountsInfoPanel();
        transactionPanel = new TransactionPanel();
        registerPanel = new RegisterPanel();
        transferPanel = new TransferPanel();
        investmentPanel = new InvestmentPanel();
        loanPanel = new LoanPanel();
        managerPanel = new ManagerPanel();
        stockManipulatorPanel = new StockManipulatorPanel();

        setVisible(true);
    }

    /**
     * setter() for showing which JPanel on the JFrame.
     */
    public void setLoginPanel() {
        add(loginPanel);
        loginPanel.setEnabled(true);
        loginPanel.setVisible(true);
    }

    public void setAccountsInfoPanel() {
        add(accountsInfoPanel);
        accountsInfoPanel.updateInfo();
        accountsInfoPanel.updateAccountsListBox();
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
        transferPanel.updateSourceAccountListBox();
        transferPanel.updateTargetAccountListBox();
        transferPanel.setEnabled(true);
        transferPanel.setVisible(true);
    }

    public void setInvestmentPanel() {
        add(investmentPanel);
        investmentPanel.updateInfo();
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

    public void setStockManipulatorPanel() {
        add(stockManipulatorPanel);
        stockManipulatorPanel.setEnabled(true);
        stockManipulatorPanel.setVisible(true);
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
