package gui;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/*
Author: Ziqi Tan
*/
public class InvestmentPanel extends JPanel implements ActionListener {
	
	private ATMGUI atm;
	private OperationFrame operationFrame;
	
	private JComboBox<String> securityAccountsList;
	private JTextArea accountTextArea;
	
	public InvestmentPanel(OperationFrame operationFrame, ATMGUI atm) {
		this.atm = atm;
		this.operationFrame = operationFrame;
		setLayout(null);
		
		int windowHeight = operationFrame.getHeight();
		int windowWidth = operationFrame.getWidth();
		
		int x = windowWidth/9*5;
		int y = windowHeight/15;
		int increment = 50;
		int buttonWidth = 180;
		int buttonHeight = 25;
				
		JButton butStockButton = new JButton("Buy stock");
		butStockButton.setBounds(x, y+increment, buttonWidth, buttonHeight);
        add(butStockButton);
        butStockButton.addActionListener(this);
        
        JButton sellStockButton = new JButton("Sell stock");
        sellStockButton.setBounds(x, y+increment*2, buttonWidth, buttonHeight);
        add(sellStockButton);
        sellStockButton.addActionListener(this);
        
        JButton returnButton = new JButton("Return");
        returnButton.setBounds(x, y+increment*3, buttonWidth, buttonHeight);
        add(returnButton);
        returnButton.addActionListener(this);
        
        x = windowWidth/9;
        y = windowHeight/10;
        increment = 25;
        int textWidth = 220;
        
        JLabel titleLabel = new JLabel("Security accounts: ");
        titleLabel.setBounds(x, y, textWidth, 25);
        add(titleLabel);
        
        securityAccountsList = new JComboBox<String>();
        securityAccountsList.addItem(">Select one");
        securityAccountsList.setBounds(x, y+increment*1, textWidth, 25);
        add(securityAccountsList);
        
        JButton inquiryButton = new JButton("Inquire");
        inquiryButton.setBounds(x+140, y+increment*2, 80, 25);
        add(inquiryButton);
        inquiryButton.addActionListener(this);
        
        JLabel stockInfoLabel = new JLabel("Stock Information:");
        stockInfoLabel.setBounds(x, y+increment*4,textWidth, 25);
        add(stockInfoLabel);
        
        accountTextArea = new JTextArea();
        accountTextArea.setText("Account:\nAccount:\nAccount:\nAccount:\nAccount:\n");
        accountTextArea.setEditable(false);
        JScrollPane jsp = new JScrollPane(accountTextArea);
        jsp.setBounds(x, y+increment*5, textWidth, 200);
        add(jsp);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if( e.getActionCommand() == "Return" ) {
			setEnabled(false);
			setVisible(false);
			operationFrame.setAccountsInfoPanel();
		}	
	}
}
