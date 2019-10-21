import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ManagerPanel {
    private JFrame frame;

    public ManagerPanel(String userID) {
        this.frame = new JFrame();
        Container contentPane = frame.getContentPane();
        JTabbedPane tabbedPane = new JTabbedPane();
        contentPane.add(tabbedPane);
        contentPane.setLayout(null);

        JPanel bankPanel = new JPanel();
        JPanel reportPanel = new JPanel();
        JLabel nameLabel = new JLabel();
        nameLabel.setText(Bank.getInstance().getManagerName(userID));

        tabbedPane.addTab("Your Bank", null, bankPanel, "Use this tab to check your bank metrics");
        tabbedPane.addTab("Bank Report", null, reportPanel, "Use this tab to check reports of your bank");

        JButton signoutButton = new JButton("Logout");

        signoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "You have successfully signed out!");
                BankPortal.getInstance().userSignout();
                setInvisible();
            }
        });


        JTextField userLoginIDField = new JTextField(8);
        JTextField userSignupIDField = new JTextField(8);
        JLabel userSignupNameLabel = new JLabel("Name:");
        JTextField userSignupNameField = new JTextField(10);

        JLabel userLoginIDLabel = new JLabel("ID:");
        JLabel userSignupIDLabel = new JLabel("ID:");
        JLabel userSignupPhoneLabel = new JLabel("Phone:");
        JTextField userSignupPhoneField = new JTextField(10);
        JPasswordField userLoginPassField = new JPasswordField(12);
        JPasswordField userSignupPassField = new JPasswordField(12);

        JLabel userLoginPassLabel = new JLabel("Password:");
        JLabel userSignupPassLabel = new JLabel("Password:");

        userLoginIDField.setHorizontalAlignment(JTextField.LEFT);
        userLoginPassField.setHorizontalAlignment(JPasswordField.LEFT);

        JButton registerButton = new JButton("Signup");



        bankPanel.add(userSignupIDLabel);
        bankPanel.add(userSignupIDField);
        bankPanel.add(userSignupPassLabel);
        bankPanel.add(userSignupPassField);
        bankPanel.add(userSignupNameLabel);
        bankPanel.add(userSignupNameField);
        bankPanel.add(userSignupPhoneLabel);
        bankPanel.add(userSignupPhoneField);
        bankPanel.add(registerButton);
        tabbedPane.addTab("Your Bank", null, bankPanel, "Use this tab to check your bank metrics");

        frame.add(signoutButton);
        frame.setTitle("Manager Panel");
        frame.setBounds(100, 500, 1400, 600);
        frame.setLayout(new FlowLayout(FlowLayout.CENTER));
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible();
    }

    public void setVisible() {
        this.frame.setVisible(true);
    }

    public void setInvisible() {
        this.frame.setVisible(false);
    }
}
