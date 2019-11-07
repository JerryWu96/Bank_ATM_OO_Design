package gui;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/*
Author: Ziqi Tan
*/
public class TransferPanel extends JPanel implements ActionListener {
	
	public TransferPanel() {
		
		int windowHeight = OperationFrame.getInstance().getHeight();
		int windowWidth = OperationFrame.getInstance().getWidth();
		
		int x = windowWidth/5;
		int y = windowHeight/4;
		int increment = 50;
		
		setLayout(null);
		
		JButton transferBetweenYourAccounts = new JButton("Transfer between your accounts");
		transferBetweenYourAccounts.setBounds(x, y+increment, 300, 25);
        add(transferBetweenYourAccounts);
        transferBetweenYourAccounts.addActionListener(this);
        
        JButton transferToOthers = new JButton("Transfer to others");
        transferToOthers.setBounds(x, y+increment*2, 300, 25);
        add(transferToOthers);
        transferToOthers.addActionListener(this);
        
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
		
		if( e.getActionCommand().contentEquals("Transfer between your accounts") ) {
						
		}	
		
		if( e.getActionCommand().contentEquals("Transfer to others") ) {
			setEnabled(false);
			setVisible(false);
			OperationFrame.getInstance().setTransferWindow();
		}
	}
}
