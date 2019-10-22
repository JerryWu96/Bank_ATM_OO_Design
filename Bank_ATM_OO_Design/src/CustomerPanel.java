import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CustomerPanel {
    private JFrame frame;
    private JComboBox<String> accountBox;
//    private List<String> accountList;

    public CustomerPanel(String userID) {
        this.frame = new JFrame();
        Container contentPane = frame.getContentPane();
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setSize(1000, 500);
        contentPane.add(tabbedPane);

        JPanel accountPanel = new JPanel();
        JPanel transactionPanel = new JPanel();
        JPanel loanPanel = new JPanel();

        accountPanel.setLayout(new BoxLayout(accountPanel, BoxLayout.Y_AXIS));

        JButton newCheckingBtn = new JButton("open Checking account");
        JButton newSavingsBtn = new JButton("open Savings account");
        JButton closeAccountBtn = new JButton("close selected account");
        JLabel userBalanceLabel = new JLabel("Your cash to deposit:");
        JLabel accountIDLabel = new JLabel("Account ID to close/deposit:");
        JTextField balanceField = new JTextField(5);
        JButton depositBtn = new JButton("deposit cash");
        balanceField.setInputVerifier(new doubleVerifier());
        JTextField accountIDField = new JTextField(5);
        JComboBox<String> currencyBox = new JComboBox<>(Bank.getInstance().getCurrencyList());
        accountBox = new JComboBox<>(new String[]{"N/A"});


        String displayContent = getAccountInfo(userID);
        JTextArea infoArea = new JTextArea(displayContent);
        accountPanel.add(infoArea);
        accountPanel.add(newCheckingBtn);
        accountPanel.add(newSavingsBtn);
        accountPanel.add(userBalanceLabel);
        accountPanel.add(balanceField);
        accountPanel.add(currencyBox);
        accountPanel.add(accountIDLabel);
        accountPanel.add(accountBox);
        accountPanel.add(depositBtn);
        accountPanel.add(closeAccountBtn);
        accountPanel.setSize(1000, 500);
        JScrollPane scrollAccountPane = new JScrollPane(accountPanel);
        scrollAccountPane.setSize(1000, 500);

        tabbedPane.addTab("Account Info", null, scrollAccountPane, "Use this tab to check/open/close your account");
        tabbedPane.addTab("Transactions", null, transactionPanel, "Use this tab to make transactions");
        tabbedPane.addTab("Loan", null, loanPanel, "Use this tab to check/place/pay off your loan");

        newCheckingBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BankPortal.getInstance().openCheckingAccount("BofF", userID);
                infoArea.setText(getAccountInfo(userID));
                updateAccountbox();
            }
        });

        newSavingsBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BankPortal.getInstance().openSavingsAccount("BofF", userID);
                infoArea.setText(getAccountInfo(userID));
                updateAccountbox();
            }
        });

        closeAccountBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String accountID = (String) accountBox.getSelectedItem();
                if (Bank.getInstance().isCheckingAccount(accountID)) {
                    String reply = BankPortal.getInstance().closeCheckingAccount(userID, accountID);
                    if (reply.equals("Not Exist")) {
                        JOptionPane.showMessageDialog(frame, "The account ID does not exist!");
                    } else {
                        infoArea.setText(getAccountInfo(userID));
                    }

                } else if (Bank.getInstance().isSavingsAccount(accountID)) {
                    String reply = BankPortal.getInstance().closeSavingsAccount(userID, accountID);
                    if (reply.equals("Not Exist")) {
                        JOptionPane.showMessageDialog(frame, "The account ID does not exist!");
                    } else {
                        infoArea.setText(getAccountInfo(userID));
                    }
                }
                updateAccountbox();
            }
        });

        depositBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String balanceStr = balanceField.getText();
                String accountID = (String) accountBox.getSelectedItem();
                double amount = Double.parseDouble(balanceStr);
                String selectedCurrency = (String) currencyBox.getSelectedItem();
                BankPortal.getInstance().deposit(userID, accountID, amount, selectedCurrency);
                infoArea.setText(getAccountInfo(userID));
            }
        });

        JButton signoutButton = new JButton("Logout");

        signoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "You have successfully signed out!");
                BankPortal.getInstance().userSignout();
                setInvisible();
            }

        });

        frame.add(signoutButton);
        frame.setTitle("Customer Panel");
        frame.setBounds(100, 500, 1400, 600);
        frame.setLayout(new FlowLayout(FlowLayout.CENTER));
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible();
    }

    private String getAccountInfo(String userID) {
        String displayContent = "";
        int CKCount = Bank.getInstance().getUserCheckingCount(userID);
        int SAVCount = Bank.getInstance().getUserSavingsCount(userID);
        displayContent += "Name: " + Bank.getInstance().getCustomerName(userID) + "\n";
        displayContent += "ID: " + userID + "\n";
        displayContent += "Total Accounts: " + (CKCount + SAVCount) + "\n";
        if (Bank.getInstance().getUserCheckingCount(userID) == 0) {
            displayContent += "You have not opened any Checking account yet!\n";
        }
        if (Bank.getInstance().getUserSavingsCount(userID) == 0) {
            displayContent += "You have not opened any Savings account yet!\n";
        }
        for (CheckingAccount account : Bank.getInstance().getCheckings()) {
            if (account.getUserID().equals(userID)) {
                displayContent += "\nCK AccountID: " + account.getAccountID();
                displayContent += "\nBalance: USD:" + account.getBalance("USD") + " CNY: " +
                        account.getBalance("CNY") + " YEN: " + account.getBalance("YEN") + "\n";
            }
        }
        displayContent += "\n";
        for (SavingsAccount account : Bank.getInstance().getSavings()) {
            if (account.getUserID().equals(userID)) {
                displayContent += "\nSAV AccountID: " + account.getAccountID();
                displayContent += "\nBalance: USD:" + account.getBalance("USD") + " CNY: " +
                        account.getBalance("CNY") + " YEN: " + account.getBalance("YEN") + "\n";
            }
        }

        return displayContent;
    }

    public void setVisible() {
        this.frame.setVisible(true);
    }

    public void setInvisible() {
        this.frame.setVisible(false);
    }

    private void updateAccountbox() {
        accountBox.removeAllItems();
        String[] accountList = Bank.getInstance().getAccountList();
        for (String accountID : accountList) {
            accountBox.addItem(accountID);
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
