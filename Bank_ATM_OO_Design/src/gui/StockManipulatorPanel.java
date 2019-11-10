package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/*
Author: Ziqi Tan
*/
public class StockManipulatorPanel extends JPanel implements ActionListener {
	
	private JComboBox<String> stockListBox;
	private JTextField newStockPriceField;
	private JTextArea stockInfo;
	
	public StockManipulatorPanel() {
				
		setLayout(null);
		
		int windowHeight = OperationFrame.getInstance().getHeight();
		int windowWidth = OperationFrame.getInstance().getWidth();
		
		int x = windowWidth/10*6;
		int y = windowHeight/15;
		int increment = 25;
		int buttonWidth = 150;
		int buttonHeight = 25;
				
		// Select stock id
		JLabel selectStockIdLabel = new JLabel("Select stock ID:");
		selectStockIdLabel.setBounds(x, y+increment*0, buttonWidth, buttonHeight);
		add(selectStockIdLabel);
		
		stockListBox = new JComboBox<String>();
		stockListBox.setBounds(x, y+increment*1, buttonWidth, 25);
        add(stockListBox);
		
		/*JButton checkingButton = new JButton("Open a checking account");
		checkingButton.setBounds(x, y+increment, buttonWidth, buttonHeight);
        add(checkingButton);
        checkingButton.addActionListener(this);*/
        
        JLabel newPriceLabel = new JLabel("Input a new price:");
        newPriceLabel.setBounds(x, y+increment*3, buttonWidth, buttonHeight);
		add(newPriceLabel);
      	
		// JTextField to input stock price
        newStockPriceField = new JTextField();
        newStockPriceField.setBounds(x, y+increment*4, buttonWidth, 25);
        add(newStockPriceField);
		
		// JButton to alter price
        JButton changePriceButton = new JButton("Change price");
        changePriceButton.setBounds(x, y+increment*6, buttonWidth, buttonHeight);
        add(changePriceButton);
        changePriceButton.addActionListener(this);
        
		// JButton to return
        JButton returnButton = new JButton("Return");
        returnButton.setBounds(x, y+increment*9, buttonWidth, buttonHeight);
        add(returnButton);
        returnButton.addActionListener(this);
        
        // JTextArea shows stock information
        x = windowWidth/14;
        y = windowHeight/20;
        increment = 25;
        int textWidth = 290;    
        int textHeight = 330;
        
        JLabel accountInfoLabel = new JLabel("Stock Market:");
        accountInfoLabel.setBounds(x, y,textWidth, 25);
        add(accountInfoLabel);
        
        stockInfo = new JTextArea();
        stockInfo.setLineWrap(true);
        stockInfo.setEditable(false);
        JScrollPane jsp = new JScrollPane(stockInfo);
        jsp.setBounds(x, y+increment*1, textWidth, textHeight);
        add(jsp);
		
	}
	
	private void updateStockListBox() {
		
	}
	
	private void updateStockInfo() {
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if( e.getActionCommand().equals("Return") ) {
			setEnabled(false);
			setVisible(false);
			OperationFrame.getInstance().setManagerPanel();
		}
		
	}

}
