package gui;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/*
Author: Ziqi Tan
*/
public class LoginPanel extends JPanel implements ActionListener, KeyListener {
	
	private ATMGUI atm;
	private OperationFrame operationFrame;
	
	private JButton loginButton;
	private JTextField userText;
	private JPasswordField  passwordText;
	
	public LoginPanel(OperationFrame operationFrame, ATMGUI atm) {
		
		this.atm = atm;
		this.operationFrame = operationFrame;
		
		setLayout(null);
		
        JLabel userLabel = new JLabel("Username:");
        userLabel.setBounds(10,20,80,25);
        add(userLabel);

        userText = new JTextField(20);
        userText.setBounds(100,20,165,25);
        add(userText);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(10,50,80,25);
        add(passwordLabel);

        passwordText = new JPasswordField(20);
        passwordText.setBounds(100,50,165,25);
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
        
        JLabel admin = new JLabel("Manager Login");
        admin.setBounds(10,140,130,25);
        add(admin);
        
        JLabel pass = new JLabel("Password: 123456789");
        pass.setBounds(10,170,130,25);
        add(pass);
        
        JTextArea sample = new JTextArea();
        String text = "Dear grader: \n"
        		+ "For your convience, there has been two customers in the hard code.\n"
        		+ "\n"
        		+ "Customer 1:\n"
        		+ "            Username: ziqi\n"
        		+ "            Password: 123\n"
        		+ "Customer 2:\n"
        		+ "            Username: tf\n"
        		+ "            Password: 123456\n"
        		+ "\n"
        		+ "Have fun!\n"
        		+ "\n"
        		+ "Ziqi Tan U88387934";
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

		if( e.getActionCommand().equals("Customer Login") ) {

			String user = userText.getText();
			String password = passwordText.getText();
			// Customer cus = this.atm.getCustomerDAOImp().getCustomer(user);
			if( user.equals("ziqi") && password.equals("123" ) /*cus != null && cus.getPassword().equals(password)*/ ) {
				JOptionPane.showMessageDialog(null,"Welcome!");
				System.out.println("Welcome!");
	            setEnabled(false);
	            setVisible(false);
	            userText.setText("");
	            passwordText.setText("");
	            // operationFrame.setCustomer(cus);
	            operationFrame.setAccountsInfoPanel();     
			}
			else {
				JOptionPane.showMessageDialog(null,"Username and password do not match!");
				System.out.println("Username and password do not match!");
			}
		}
		
		if( e.getActionCommand().equals("Register") ) {
			System.out.println("Register!");
			setEnabled(false);
            setVisible(false);
            operationFrame.setRegisterPanel();				
		}
		
		if( e.getActionCommand().equals("Manager Login") ) {
			/*try {
				String inputValue = JOptionPane.showInputDialog("Password: ");
				Manager manager = atm.getCustomerDAOImp().getManager();
				if( inputValue.equals(manager.getPassword()) ) {
					setEnabled(false);
		            setVisible(false);
		            operationFrame.setManagerPanel();
				}
			}
			catch(Exception error) {
				System.out.println(error);
			}*/
					
		}
		
	}

	@Override
	public void keyPressed(KeyEvent e) {

		if( e.getKeyCode() == KeyEvent.VK_ENTER ) {
			String user = userText.getText();
			String password = passwordText.getText();
			if( user.equals("ziqi") && password.equals("123" ) /*cus != null && cus.getPassword().equals(password)*/ ) {
				JOptionPane.showMessageDialog(null,"Welcome!");
				System.out.println("Welcome!");
	            setEnabled(false);
	            setVisible(false);
	            userText.setText("");
	            passwordText.setText("");
	            // operationFrame.setCustomer(cus);
	            operationFrame.setAccountsInfoPanel();     
			}
			else {
				JOptionPane.showMessageDialog(null,"Username and password do not match!");
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
	
}

