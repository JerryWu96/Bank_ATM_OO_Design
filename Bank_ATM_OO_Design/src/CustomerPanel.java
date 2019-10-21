import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CustomerPanel {
    private JFrame frame;

    public CustomerPanel(String userID) {
        this.frame = new JFrame();
        Container contentPane = frame.getContentPane();
        JTabbedPane tabbedPane = new JTabbedPane();
        contentPane.add(tabbedPane);

        JPanel accountPanel = new JPanel();
        JPanel transactionPanel = new JPanel();
        JPanel loanPanel = new JPanel();

        accountPanel.setLayout(new BoxLayout(accountPanel, BoxLayout.Y_AXIS));

        JButton newCheckingBtn = new JButton("open Checking account");
        JButton newSavingsBtn = new JButton("open Savings account");
        JButton closeCheckingBtn = new JButton("close Checking account");
        JButton closeSavingsBtn = new JButton("close Savings account");
        JLabel userBalanceLabel = new JLabel("Your cash to deposit:");
        JLabel accountIDLabel = new JLabel("Account ID you want to close:");
        JTextField balanceField = new JTextField(5);
        balanceField.setInputVerifier(new doubleVerifier());
        JTextField accountIDField = new JTextField(5);
        JComboBox<String> currencyBox= new JComboBox<>(Bank.getInstance().getCurrencyList());


        String displayContent = getAccountInfo(userID);
        JTextArea infoArea = new JTextArea(displayContent);
        accountPanel.add(infoArea);
        accountPanel.add(userBalanceLabel);
        accountPanel.add(balanceField);
        accountPanel.add(currencyBox);
        accountPanel.add(newCheckingBtn);
        accountPanel.add(newSavingsBtn);

        accountPanel.add(accountIDLabel);
        accountPanel.add(accountIDField);
        accountPanel.add(closeCheckingBtn);
        accountPanel.add(closeSavingsBtn);
        JScrollPane scrollAccountPane = new JScrollPane(accountPanel);

        tabbedPane.addTab("Account Info", null, scrollAccountPane, "Use this tab to check/open/close your account");
        tabbedPane.addTab("Transactions", null, transactionPanel, "Use this tab to make transactions");
        tabbedPane.addTab("Loan", null, loanPanel, "Use this tab to check/place/pay off your loan");




        newCheckingBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = balanceField.getText();
                String selectedCurrency = (String) currencyBox.getSelectedItem();
                double balance = Double.parseDouble(input);
                BankPortal.getInstance().openCheckingAccount(selectedCurrency, balance, "BofF", userID);
                infoArea.setText(getAccountInfo(userID));
            }
        });

        closeCheckingBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String accountID = accountIDField.getText();
                String reply = BankPortal.getInstance().closeCheckingAccount(userID, accountID);
                if (reply.equals("Not Exist")) {
                    JOptionPane.showMessageDialog(frame, "The account ID does not exist!");
                } else {
                    JOptionPane.showMessageDialog(frame, "You have successfully closed your Checking account " + accountID + " !");
                    infoArea.setText(getAccountInfo(userID));
                }
            }
        });

        newSavingsBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = balanceField.getText();
                String selectedCurrency = (String) currencyBox.getSelectedItem();
                double balance = Double.parseDouble(input);
                BankPortal.getInstance().openSavingsAccount(selectedCurrency, balance, "BofF", userID);
                infoArea.setText(getAccountInfo(userID));
            }
        });

        closeSavingsBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String accountID = accountIDField.getText();
                String reply = BankPortal.getInstance().closeSavingsAccount(userID, accountID);
                if (reply.equals("Not Exist")) {
                    JOptionPane.showMessageDialog(frame, "The account ID does not exist!");
                } else {
                    JOptionPane.showMessageDialog(frame, "You have successfully closed your Savings account " + accountID + " !");
                    infoArea.setText(getAccountInfo(userID));
                }
            }
        });
//        accountPanel.setLayout(new GridBagLayout());
//        GridBagConstraints gbc = new GridBagConstraints();
//        gbc.gridwidth = GridBagConstraints.REMAINDER;
//        gbc.fill = GridBagConstraints.VERTICAL;


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
                displayContent += "CK AccountID: " + account.getAccountID() + " Balance: " + account.getBalance() + "\n";
            }
        }
        for (SavingsAccount account : Bank.getInstance().getSavings()) {
            if (account.getUserID().equals(userID)) {
                displayContent += "SAV AccountID: " + account.getAccountID() + " Balance: " + account.getBalance() + "\n";
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
