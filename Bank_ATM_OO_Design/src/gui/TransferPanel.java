package gui;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/*
Author: Ziqi Tan
*/
public class TransferPanel extends JPanel implements ActionListener {
	
	private JComboBox<String> sourceAccountList;
	private JComboBox<String> targetAccountList;
	
	public TransferPanel() {
		
		int windowHeight = OperationFrame.getInstance().getHeight();
		int windowWidth = OperationFrame.getInstance().getWidth();
		
		int x = windowWidth/5;
		int y = windowHeight/4;
		int increment = 50;
		
		setLayout(null);
		
		// Accounts List
        /*JLabel titleLabel = new JLabel("Select an account: ");
        titleLabel.setBounds(x, y, textWidth, 25);
        add(titleLabel);
        
        accountsList = new JComboBox<String>();
        accountsList.setBounds(x, y+increment*1, textWidth, 25);
        add(accountsList);
        updateAccountsListBox();*/
		
        
        JButton returnButton = new JButton("Return");
        returnButton.setBounds(x, y+increment*3, 300, 25);
        add(returnButton);
        returnButton.addActionListener(this);		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if( e.getActionCommand().contentEquals("Return") ) {
			setEnabled(false);
			setVisible(false);
			OperationFrame.getInstance().setTransactionPanel();
		}
		

	}
}
