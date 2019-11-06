package backend;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
/**
 * The signup/login Swing GUI class.
 */
public class WelcomePanel {
    private JFrame frame;
    private CustomerPanel customerPanel;
    private ManagerPanel managerPanel;
    private BufferedImage logoImage;

    private boolean loginAuth(String userID, String password, String identity) {
        String response = BankPortal.getInstance().getBank().authenticateUser(userID, password, identity);
        switch (response) {
            case "NotExist":
                JOptionPane.showMessageDialog(frame, "User does not exist!");
                return false;
            case "WrongPass":
                JOptionPane.showMessageDialog(frame, "Wrong password!");
                return false;
            case "Error":
                JOptionPane.showMessageDialog(frame, "Unexpected Error!");
            default:
                return true;
        }
    }

    public WelcomePanel() {
        this.frame = new JFrame();
        Container contentPane = frame.getContentPane();
        JTabbedPane tabbedPane = new JTabbedPane();
        contentPane.add(tabbedPane);
        contentPane.setLayout(null);

        try {
            logoImage = ImageIO.read(new File("./misc/Logo.png"));
        } catch (IOException ex) {
            System.out.println(ex);
        }

        JLabel logoLabel = new JLabel(new ImageIcon(logoImage));
        JPanel loginPanel = new JPanel();
        JPanel signupPanel = new JPanel();

        frame.setTitle(BankPortal.getInstance().getBank().getBankName() + " Welcome Panel");
        frame.setBounds(100, 500, 1400, 600);
        frame.setLayout(new FlowLayout(FlowLayout.CENTER));
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JRadioButton loginRadioBtn_manager = new JRadioButton("Manager");
        JRadioButton loginRadioBtn_customer = new JRadioButton("Customer");
        JRadioButton signupRadioBtn_manager = new JRadioButton("Manager");
        JRadioButton signupRadioBtn_customer = new JRadioButton("Customer");

        ButtonGroup loginGroup = new ButtonGroup();
        loginGroup.add(loginRadioBtn_manager);
        loginGroup.add(loginRadioBtn_customer);
        ButtonGroup signupGroup = new ButtonGroup();
        signupGroup.add(signupRadioBtn_manager);
        signupGroup.add(signupRadioBtn_customer);

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

        JButton loginButton = new JButton("Login");
        JButton registerButton = new JButton("Signup");

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userID = userLoginIDField.getText();
                String password = new String(userLoginPassField.getPassword());
                if (userID.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Please fill in your ID.");
                } else if (password.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Please fill in your password.");
                } else if (loginRadioBtn_manager.isSelected()) {
                    if (loginAuth(userID, password, loginRadioBtn_manager.getText())) {
                        BankPortal.getInstance().userLogin(userID);
                        JOptionPane.showMessageDialog(frame, "Welcome " + loginRadioBtn_manager.getText() + " " + userID + "!");
                        managerPanel = new ManagerPanel(userID);
                    }
                } else if (loginRadioBtn_customer.isSelected()) {
                    if (loginAuth(userID, password, loginRadioBtn_customer.getText())) {
                        BankPortal.getInstance().userLogin(userID);
                        JOptionPane.showMessageDialog(frame, "Welcome " + loginRadioBtn_customer.getText() + " " + userID + "!");
                        customerPanel = new CustomerPanel(userID);
                    }
                } else if (!loginRadioBtn_manager.isSelected() && !loginRadioBtn_customer.isSelected()) {
                    JOptionPane.showMessageDialog(frame, "Please select your identity. Manager/Customer?");
                }
            }
        });

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userID = userSignupIDField.getText();
                String password = new String(userSignupPassField.getPassword());
                String userName = userSignupNameField.getText();
                String userPhone = userSignupPhoneField.getText();
                if (userID.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Please fill in your ID.");
                } else if (password.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Please fill in your password.");
                } else if (userPhone.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Please fill in your phone.");
                } else if (userName.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Please fill in your name.");
                } else if (signupRadioBtn_manager.isSelected()) {
                    JOptionPane.showMessageDialog(frame, "Manager " + userID + ", you have successfully signed up!");
                    BankPortal.getInstance().getBank().addUser(userID, password, userName, signupRadioBtn_manager.getText());
                } else if (signupRadioBtn_customer.isSelected()) {
                    JOptionPane.showMessageDialog(frame, "Customer " + userID + ", you have successfully signed up!");
                    BankPortal.getInstance().getBank().addUser(userID, password, userName, signupRadioBtn_customer.getText());
                } else {
                    JOptionPane.showMessageDialog(frame, "Please select your identity. Manager/Customer?");
                }
            }
        });

        signupPanel.add(userSignupIDLabel);
        signupPanel.add(userSignupIDField);
        signupPanel.add(userSignupPassLabel);
        signupPanel.add(userSignupPassField);
        signupPanel.add(userSignupNameLabel);
        signupPanel.add(userSignupNameField);
        signupPanel.add(userSignupPhoneLabel);
        signupPanel.add(userSignupPhoneField);
        signupPanel.add(signupRadioBtn_manager);
        signupPanel.add(signupRadioBtn_customer);
        signupPanel.add(registerButton);
        tabbedPane.addTab("Signup", null, signupPanel, "Use this tab for signup");

        loginPanel.add(userLoginIDLabel);
        loginPanel.add(userLoginIDField);
        loginPanel.add(userLoginPassLabel);
        loginPanel.add(userLoginPassField);
        loginPanel.add(loginRadioBtn_manager);
        loginPanel.add(loginRadioBtn_customer);
        loginPanel.add(loginButton);
        tabbedPane.addTab("Login", null, loginPanel, "Use this tab for login");

        frame.add(logoLabel);
//        frame.pack();
        setVisible();
    }

    public void setVisible() {
        this.frame.setVisible(true);
    }

    public void setInvisible() {
        this.frame.setVisible(false);
    }


}
