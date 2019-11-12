package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import backend.BankPortal;
import backend.SharedConstants;

/*
Author: Ziqi Tan
*/
public class StockManipulatorPanel extends JPanel implements ActionListener {
	
	private JComboBox<String> stockListBox;
	private JTextField newStockPriceField;
	private JTextArea stockInfo;
	
	private final String selectOne = ">Select one";
	
	public StockManipulatorPanel() {
				
		setLayout(null);
		
		int windowHeight = OperationFrame.getInstance().getHeight();
		int windowWidth = OperationFrame.getInstance().getWidth();
		
		int x = windowWidth/13*9;
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
        updateStockListBox();
		
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
        x = windowWidth/16;
        y = windowHeight/20;
        increment = 25;
        int textWidth = 350;  
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
        updateStockInfo();
		
	}
	
	private void updateStockListBox() {
		stockListBox.removeAllItems();
		stockListBox.addItem(selectOne);
		String[] stocksList = BankPortal.getInstance().getAllStockID();
		for( String stock: stocksList ) {
			stockListBox.addItem(stock);
		}
		
	}
	
	private void updateStockInfo() {
		String text = BankPortal.getInstance().getStocks().toString();
		stockInfo.setText("");
		stockInfo.append(text);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if( e.getActionCommand().equals("Return") ) {
			setEnabled(false);
			setVisible(false);
			OperationFrame.getInstance().setManagerPanel();
		}
		
		if( e.getActionCommand().equals("Change price") ) {
			String selectedStock = stockListBox.getSelectedItem().toString();
			if( selectedStock.equals(selectOne) ) {
				JOptionPane.showMessageDialog(null, "Please select a stock!");
			}
			else {
				try {
					String userID = OperationFrame.getInstance().getUserID();
					String inputValue = newStockPriceField.getText();
    				double newPrice = Double.parseDouble(inputValue);    // NumberFormatException	
    				// Change price
    				String result = BankPortal.getInstance().updateStockPrice(userID, selectedStock, newPrice);
    				switch(result) {
    					case SharedConstants.ERR_PERMISSION_DENIED:
    						JOptionPane.showMessageDialog(null, "Permission denied!");
    						break;
    					default:
    						// SharedConstants.SUCCESS_UPDATE_STOCK_PRICE
    						JOptionPane.showMessageDialog(null, "Update successfully!");
    						updateStockInfo();  						
    				}   				
				}
				catch(NumberFormatException error) {
        			System.out.println(error);
        			JOptionPane.showMessageDialog(null, "Please select an integer!");
        		}
			}
		}
				
	}

}
