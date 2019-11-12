package gui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import backend.BankPortal;
import backend.SharedConstants;

/*
Author: Ziqi Tan
*/
public class LoginPanel extends JPanel implements ActionListener, KeyListener {

    private JButton loginButton;
    private JTextField userText;
    private JPasswordField passwordText;

    public LoginPanel() {

        setLayout(null);

        JLabel userLabel = new JLabel("Username:");
        userLabel.setBounds(10, 20, 80, 25);
        add(userLabel);

        userText = new JTextField(20);
        userText.setBounds(100, 20, 165, 25);
        add(userText);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(10, 50, 80, 25);
        add(passwordLabel);

        passwordText = new JPasswordField(20);
        passwordText.setBounds(100, 50, 165, 25);
        add(passwordText);
        passwordText.addKeyListener(this);

        loginButton = new JButton("Customer Login");
        loginButton.setBounds(10, 80, 125, 25);
        add(loginButton);
        loginButton.addActionListener(this);

        JButton registerButton = new JButton("Register");
        registerButton.setBounds(140, 80, 100, 25);
        add(registerButton);
        registerButton.addActionListener(this);

        JButton rootButton = new JButton("Manager Login");
        rootButton.setBounds(250, 80, 130, 25);
        add(rootButton);
        rootButton.addActionListener(this);

        JLabel admin = new JLabel("Manager Username: m");
        admin.setBounds(10, 140, 130, 25);
        add(admin);

        JLabel pass = new JLabel("Password: m");
        pass.setBounds(10, 170, 130, 25);
        add(pass);

        JTextArea sample = new JTextArea();
        String text = "Customer Username: a"
        		+ "\n"
        		+ "Password: a"
        		+ "\n"
        		+ "\n"
        		+ "Have fun!"
        		+ "\n";
        sample.setLineWrap(true);
        sample.setText(text);
        sample.setFont(new Font("", Font.BOLD, 13));
        sample.setEditable(false);
        sample.setBounds(10, 200, 440, 250);
        add(sample);

    }

    /*
     * Method: actionPerformed
     * Function: Action listener for "Login" button, "Register" button and "Manager Login" button.
     * */
    @Override
    public void actionPerformed(ActionEvent e) {

        // Customer Login
        if (e.getActionCommand().equals("Customer Login")) {
            String user = userText.getText();
            String password = passwordText.getText();
            boolean loginFlag = loginAuth(user, password, SharedConstants.CUSTOMER);
            if (loginFlag) {
                JOptionPane.showMessageDialog(null, "Welcome!");
                setEnabled(false);
                setVisible(false);
                userText.setText("");
                passwordText.setText("");
                OperationFrame.getInstance().setUserID(user);
                OperationFrame.getInstance().setAccountsInfoPanel();
            } else {
                // JOptionPane.showMessageDialog(null,"Username and password do not match!");
                System.out.println("Username and password do not match!");
            }
        }

        // Register
        if (e.getActionCommand().equals("Register")) {
            System.out.println("Register!");
            setEnabled(false);
            setVisible(false);
            OperationFrame.getInstance().setRegisterPanel();
        }

        // Manager Login
        if (e.getActionCommand().equals("Manager Login")) {
            String user = userText.getText();
            String password = passwordText.getText();
            boolean loginFlag = loginAuth(user, password, SharedConstants.MANAGER);
            if (loginFlag) {
                JOptionPane.showMessageDialog(null, "Welcome!");
                System.out.println("Welcome!");
                setEnabled(false);
                setVisible(false);
                userText.setText("");
                passwordText.setText("");
                OperationFrame.getInstance().setUserID(user);
                OperationFrame.getInstance().setManagerPanel();
            } else {
                // JOptionPane.showMessageDialog(null,"Username and password do not match!");
                System.out.println("Username and password do not match!");
            }

        }

    }

    /**
     * Methods from KeyListener
     */
    @Override
    public void keyPressed(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            String user = userText.getText();
            String password = passwordText.getText();
            boolean loginFlag = loginAuth(user, password, SharedConstants.CUSTOMER);
            if (loginFlag) {
                JOptionPane.showMessageDialog(null, "Welcome!");
                System.out.println("Welcome!");
                setEnabled(false);
                setVisible(false);
                userText.setText("");
                passwordText.setText("");
                OperationFrame.getInstance().setUserID(user);
                OperationFrame.getInstance().setAccountsInfoPanel();
            } else {
                // JOptionPane.showMessageDialog(null,"Username and password do not match!");
                System.out.println("Username and password do not match!");
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub

    }

    /**
     * Method: loginAuth
     * Function: check whether user name matches the password.
     */
    private boolean loginAuth(String userID, String password, String identity) {

        String response = BankPortal.getInstance().getBank().authenticateUser(userID, password, identity);

        switch (response) {
            case SharedConstants.ERR_USER_NOT_EXIST:
                JOptionPane.showMessageDialog(null, "User does not exist!");
                return false;
            case SharedConstants.ERR_WRONG_PASS:
                JOptionPane.showMessageDialog(null, "Wrong password!");
                return false;
            default:
                return true;
        }
    }
}

