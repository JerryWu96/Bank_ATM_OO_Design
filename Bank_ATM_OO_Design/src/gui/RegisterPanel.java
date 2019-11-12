package gui;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import backend.BankPortal;
import backend.SharedConstants;

/*
Author: Ziqi Tan
*/
public class RegisterPanel extends JPanel implements ActionListener {
	
	private JTextField nameText;
	private JTextField idText;
	private JTextField phoneText;
	private JComboBox<String> roleBox;
	private JPasswordField password;
	private JPasswordField passwordConfirm;
		
	public RegisterPanel() {

		setLayout(null);
		
		int windowHeight = OperationFrame.getInstance().getHeight();
		int windowWidth = OperationFrame.getInstance().getWidth();
		
		int x = windowWidth/5;
		int y = windowHeight/10;
		int increment = 40;
		
		// String name, String ssn, String phone, String address, String password
		JLabel nameLabel = new JLabel("Name");
		nameLabel.setBounds(x, y, 80, 25);
		add(nameLabel);
		nameText = new JTextField(20);
		nameText.setBounds(x+80, y, 165, 25);
		add(nameText);	
		
		JLabel idLabel = new JLabel("ID");
		idLabel.setBounds(x, y+increment*1, 80, 25);
		add(idLabel);
		idText = new JTextField(20);
		idText.setBounds(x+80, y+increment*1, 165, 25);
		add(idText);
		
		JLabel phoneLabel = new JLabel("Phone");
		phoneLabel.setBounds(x, y+increment*2, 80, 25);
		add(phoneLabel);
		phoneText = new JTextField(20);
		phoneText.setBounds(x+80, y+increment*2, 165, 25);
		add(phoneText);
		
		JLabel roleLabel = new JLabel("Role");
		roleLabel.setBounds(x, y+increment*3, 80, 25);
		add(roleLabel);
		
		roleBox = new JComboBox<String>();
		roleBox.addItem("Customer");
		roleBox.addItem("Manager");
		roleBox.setBounds(x+80, y+increment*3, 165, 25);
		add(roleBox);

		JLabel passwordLabel = new JLabel("Password");
		passwordLabel.setBounds(x, y+increment*4, 80, 25);
		add(passwordLabel);		
		password = new JPasswordField(20);
		password.setBounds(x+80, y+increment*4, 165, 25);
		add(password);
		
		JLabel passwordConfirmLabel = new JLabel("Confirm");
		passwordConfirmLabel.setBounds(x, y+increment*5, 80, 25);
		add(passwordConfirmLabel);
		
		passwordConfirm = new JPasswordField(20);
		passwordConfirm.setBounds(x+80, y+increment*5, 165, 25);
		add(passwordConfirm);
		
		JButton submitButton = new JButton("Submit");
		submitButton.setBounds(x+15, y+increment*6, 100, 25);
        add(submitButton);
        submitButton.addActionListener(this);
        
        JButton returnButton = new JButton("Return");
        returnButton.setBounds(x+135, y+increment*6, 100, 25);
        add(returnButton);
        returnButton.addActionListener(this);
				
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		// Submit your information
		if( e.getActionCommand().equals("Submit") ) {
			
			String userName = nameText.getText();
			String userID = idText.getText();
			String phone = phoneText.getText();
			String pw = password.getText();
			String pwc = passwordConfirm.getText();
					
			if( userName.equals("") || userID.equals("") || 
					phone.equals("") || pw.equals("") || pwc.equals("") ) {
				JOptionPane.showMessageDialog(null,"Please fill all boxes!");
				System.out.println("Please fill all boxes!");
			}
			else {
				// Password confirm
				if( !pw.equals(pwc) ) {
					JOptionPane.showMessageDialog(null,"Please confirm your password!");
					System.out.println("Please confirm your password!");
				}
				else {
					// Registration success
					
					// get text from JComboBox
					String role = roleBox.getSelectedItem().toString();
					System.out.println(role);
					switch(role) {
						case SharedConstants.CUSTOMER:
							BankPortal.getInstance().getBank().addUser(userID, pw, userName, role);
							JOptionPane.showMessageDialog(null,"Registration success!");
							nameText.setText("");
							idText.setText("");
							phoneText.setText("");
							password.setText("");
							passwordConfirm.setText("");
							setEnabled(false);
							setVisible(false);
							OperationFrame.getInstance().setLoginPanel();
							break;
						case SharedConstants.MANAGER:
							BankPortal.getInstance().getBank().addUser(userID, pw, userName, role);
							JOptionPane.showMessageDialog(null,"Registration success!");
							nameText.setText("");
							idText.setText("");
							phoneText.setText("");
							password.setText("");
							passwordConfirm.setText("");
							setEnabled(false);
							setVisible(false);
							OperationFrame.getInstance().setLoginPanel();
							break;
						default:
							System.out.println("Fail to register.");
					}	
				}							
			}
		} // Submit event
		
		// Return to login panel
		if( e.getActionCommand().equals("Return") ) {
			nameText.setText("");
			idText.setText("");
			phoneText.setText("");
			password.setText("");
			passwordConfirm.setText("");
			setEnabled(false);
			setVisible(false);
			OperationFrame.getInstance().setLoginPanel();	
		}		
	}
}
