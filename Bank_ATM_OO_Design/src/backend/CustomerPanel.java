package backend;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

/**
 * A Swing GUI that is displayed for customers to interact with the system.
 */
public class CustomerPanel {
    private JFrame frame;
    private String userID;
    private JComboBox<String> accountInfoBox;
    private JComboBox<String> sourceAccountBox;
    private JComboBox<String> targetAccountBox;
    private JComboBox<String> loanBox;
    private JTabbedPane tabbedPane;
    private JTextArea accountInfoArea;

    public CustomerPanel(String userID) {
        this.frame = new JFrame();
        this.userID = userID;
        Container contentPane = frame.getContentPane();
        tabbedPane = new JTabbedPane();
        contentPane.add(tabbedPane);


        accountInfoArea = new JTextArea(BankPortal.getInstance().getUserInfo(userID));
        initAccountManagePanel();
        initTransactionPanel();
        initLoanPanel();
        updateBoxes();

        tabbedPane.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                updateAccountInfoArea();
            }
        });

        frame.setTitle("Customer Panel");
        frame.setBounds(100, 500, 1400, 1000);
        frame.setLayout(new FlowLayout(FlowLayout.CENTER));
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible();
    }

    private void initLoanPanel() {
        JPanel loanPanel = new JPanel();
        loanPanel.setLayout(new BoxLayout(loanPanel, BoxLayout.Y_AXIS));

        JLabel balanceLabel = new JLabel("Please input the amount you would like to get from the loan:");
        JTextField loanField = new JTextField(5);
        loanField.setInputVerifier(new doubleVerifier());
        JComboBox<String> currencyBox = new JComboBox<>(BankPortal.getInstance().getBank().getCurrencyList());
        JLabel loanLabel = new JLabel("Your current loans:");
        loanBox = new JComboBox<>(new String[]{"N/A"});
        JButton takeLoanBtn = new JButton("take loan");
        JButton payoffLoanBtn = new JButton("pay off selected loan");


        loanPanel.add(balanceLabel);
        loanPanel.add(loanField);
        loanPanel.add(currencyBox);
        loanPanel.add(loanLabel);
        loanPanel.add(loanBox);
        loanPanel.add(takeLoanBtn);
        loanPanel.add(payoffLoanBtn);

        takeLoanBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String balanceStr = loanField.getText();
                double amount = Double.parseDouble(balanceStr);
                String selectedCurrency = (String) currencyBox.getSelectedItem();
                String result = BankPortal.getInstance().takeLoan(userID, amount, selectedCurrency);
                if (result.equals("Not Eligible")) {
                    JOptionPane.showMessageDialog(frame, "You are no longer permitted to take out a loan!\n" +
                            "Please pay off loans to retrieve your collaterals");
                } else {
                    JOptionPane.showMessageDialog(frame, "New loan created! \n You have " +
                            BankPortal.getInstance().getCustomerCollateral() + " remaining collaterals.");
                }
                accountInfoArea = new JTextArea(BankPortal.getInstance().getUserInfo(userID));
                updateBoxes();
            }
        });

        payoffLoanBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedLoan = (String) loanBox.getSelectedItem();
                BankPortal.getInstance().payoffLoan(selectedLoan);
                updateBoxes();
            }
        });

        JScrollPane scrollLoanPane = new JScrollPane(loanPanel);
        tabbedPane.addTab("Loan", null, scrollLoanPane, "Use this tab to check/place/pay off your loan");

    }

    private void initTransactionPanel() {
        JPanel transactionPanel = new JPanel();
        transactionPanel.setLayout(new BoxLayout(transactionPanel, BoxLayout.Y_AXIS));

        JLabel sourceLabel = new JLabel("Your source account to transfer money:");
        JLabel targetLabel = new JLabel("Your destination account to send money:");

        JLabel balanceLabel = new JLabel("Please select the balance amount for transfer:");
        JTextField balanceField = new JTextField(5);
        balanceField.setInputVerifier(new doubleVerifier());
        sourceAccountBox = new JComboBox<>(new String[]{"N/A"});
        targetAccountBox = new JComboBox<>(new String[]{"N/A"});
        JComboBox<String> currencyBox = new JComboBox<>(BankPortal.getInstance().getBank().getCurrencyList());
        JButton confirmBtn = new JButton("confirm");

        transactionPanel.add(sourceLabel);
        transactionPanel.add(targetLabel);
        transactionPanel.add(sourceAccountBox);
        transactionPanel.add(targetAccountBox);
        transactionPanel.add(balanceLabel);
        transactionPanel.add(balanceField);
        transactionPanel.add(currencyBox);
        transactionPanel.add(confirmBtn);

        confirmBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String balanceStr = balanceField.getText();
                double amount = Double.parseDouble(balanceStr);
                String selectedCurrency = (String) currencyBox.getSelectedItem();
                String sourceAccountID = (String) sourceAccountBox.getSelectedItem();
                String targetAccountID = (String) targetAccountBox.getSelectedItem();
                String result = BankPortal.getInstance().transfer(userID, sourceAccountID, targetAccountID, amount, selectedCurrency);
                if (result.equals(SharedConstants.ERR_INSUFFICIENT_BALANCE)) {
                    JOptionPane.showMessageDialog(frame, "Your selected account does not have enough balance.");
                } else {
                    JOptionPane.showMessageDialog(frame, "Transfer succeeded!\n You have transfered " +
                            amount + " from your account: " + sourceAccountID + " to the account: " + targetAccountID);
                }
                accountInfoArea = new JTextArea(BankPortal.getInstance().getUserInfo(userID));
            }
        });
        JScrollPane scrollTransactionPane = new JScrollPane(transactionPanel);
        tabbedPane.addTab("Transactions", null, scrollTransactionPane, "Use this tab to make transactions");
    }

    private void initAccountManagePanel() {
        JPanel accountPanel = new JPanel();
        accountPanel.setLayout(new BoxLayout(accountPanel, BoxLayout.PAGE_AXIS));

        accountPanel.setPreferredSize(new Dimension(500, 600));

        JButton newCheckingBtn = new JButton("open Checking account");
        JButton newSavingsBtn = new JButton("open Savings account");
        JButton closeAccountBtn = new JButton("close selected account");
        JLabel userBalanceLabel = new JLabel("cash to deposit:");
        JLabel accountIDLabel = new JLabel("account ID to close/deposit:");
        JButton depositBtn = new JButton("deposit cash");
        JButton withdrawBtn = new JButton("withdraw cash");
        JTextField balanceField = new JTextField(5);
        balanceField.setInputVerifier(new doubleVerifier());
        JComboBox<String> currencyBox = new JComboBox<>(BankPortal.getInstance().getBank().getCurrencyList());
        accountInfoBox = new JComboBox<>(new String[]{"N/A"});
        accountInfoArea = new JTextArea(BankPortal.getInstance().getUserInfo(userID));

        accountPanel.add(accountInfoArea);
        accountPanel.add(newCheckingBtn);
        accountPanel.add(newSavingsBtn);
        accountPanel.add(userBalanceLabel);
        accountPanel.add(balanceField);
        accountPanel.add(currencyBox);
        accountPanel.add(accountIDLabel);
        accountPanel.add(accountInfoBox);
        accountPanel.add(depositBtn);
        accountPanel.add(withdrawBtn);

        accountPanel.add(closeAccountBtn);
        JScrollPane scrollAccountPane = new JScrollPane(accountPanel);
        tabbedPane.addTab("Account Info", null, scrollAccountPane, "Use this tab to check/open/close your account");


        newCheckingBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BankPortal.getInstance().openAccount(SharedConstants.BANK_ID, userID, SharedConstants.CK);
                updateAccountInfoArea();
                updateBoxes();
            }
        });

        newSavingsBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BankPortal.getInstance().openAccount(SharedConstants.BANK_ID, userID, SharedConstants.SAV);
                updateAccountInfoArea();
                updateBoxes();
            }
        });

        closeAccountBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String accountID = (String) accountInfoBox.getSelectedItem();
                if (BankPortal.getInstance().getBank().isCheckingAccount(accountID)) {
                    String reply = BankPortal.getInstance().closeAccount(userID, accountID, SharedConstants.CK);
                    if (reply.equals(SharedConstants.ERR_ACCOUNT_NOT_EXIST)) {
                        JOptionPane.showMessageDialog(frame, "Account ID does not exist!");
                    } else {
                        updateAccountInfoArea();
                    }

                } else if (BankPortal.getInstance().getBank().isSavingsAccount(accountID)) {
                    String reply = BankPortal.getInstance().closeAccount(userID, accountID, SharedConstants.SAV);
                    if (reply.equals(SharedConstants.ERR_ACCOUNT_NOT_EXIST)) {
                        JOptionPane.showMessageDialog(frame, "Account ID does not exist!");
                    } else {
                        updateAccountInfoArea();
                    }
                }
                updateBoxes();
            }
        });

        depositBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String balanceStr = balanceField.getText();
                String accountID = (String) accountInfoBox.getSelectedItem();
                double amount = Double.parseDouble(balanceStr);
                String selectedCurrency = (String) currencyBox.getSelectedItem();
                BankPortal.getInstance().deposit(userID, accountID, amount, selectedCurrency);
                updateAccountInfoArea();
            }
        });


        withdrawBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String balanceStr = balanceField.getText();
                String accountID = (String) accountInfoBox.getSelectedItem();
                double amount = Double.parseDouble(balanceStr);
                String selectedCurrency = (String) currencyBox.getSelectedItem();
                String result = BankPortal.getInstance().withdraw(userID, accountID, amount, selectedCurrency);
                if (result.equals(SharedConstants.ERR_INSUFFICIENT_BALANCE)) {
                    JOptionPane.showMessageDialog(frame, "Your selected account has insufficient balance!");
                }
                updateAccountInfoArea();
            }
        });

        JButton signoutButton = new JButton("Logout");

        signoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "You have successfully signed out!");
                setInvisible();
            }

        });
        frame.add(signoutButton);
    }

    private void setVisible() {
        this.frame.setVisible(true);
    }

    private void setInvisible() {
        this.frame.setVisible(false);
    }

    private void updateBoxes() {
        updateAccountbox(targetAccountBox);
        updateUserAccountBox(accountInfoBox);
        updateUserAccountBox(sourceAccountBox);
        updateUserLoanBox(loanBox);
    }

    private void updateAccountbox(JComboBox myBox) {
        myBox.removeAllItems();
        String[] accountList = BankPortal.getInstance().getBank().getAccountList(SharedConstants.CK);
        for (String accountID : accountList) {
            myBox.addItem(accountID);
        }
    }

    private void updateAccountInfoArea() {
        accountInfoArea.removeAll();
        String info = BankPortal.getInstance().getUserInfo(userID);
        accountInfoArea.setText(info);
    }

    private void updateUserAccountBox(JComboBox myBox) {
        myBox.removeAllItems();
        String[] accountList = BankPortal.getInstance().getBank().getAccountList(SharedConstants.CK);
        for (String accountID : accountList) {
            if (BankPortal.getInstance().getBank().isUserAccount(userID, accountID)) {
                myBox.addItem(accountID);
            }
        }
    }

    private void updateUserLoanBox(JComboBox myBox) {
        myBox.removeAllItems();
        String[] loanList = BankPortal.getInstance().getBank().getLoanList(userID);
        for (String loanID : loanList) {
            myBox.addItem(loanID);
        }
    }

    public class doubleVerifier extends InputVerifier {
        @Override
        public boolean verify(JComponent input) {
            String text = ((JTextField) input).getText();
            try {
                Double value = Double.parseDouble(text);
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        }
    }
}
