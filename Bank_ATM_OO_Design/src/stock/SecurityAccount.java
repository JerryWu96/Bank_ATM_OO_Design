package stock;

import java.util.ArrayList;

public class SecurityAccount {
	
	private int securityAccID;
	private int savAccID;
	
	private ArrayList<Stock> stocks;
	
	private boolean isActive;
	
	public void open(int id) {
		stocks = new ArrayList<Stock>();
		isActive = true;
		securityAccID = id;
	}
	
	public int getId() {
		return this.securityAccID;
	}
	
	public boolean isActive() {
		return isActive;
	}
	
	public void linkSavingsAccount(int id) {
		savAccID = id;
	}
	
	public int getSavingsAccountId() {
		return this.savAccID;
	}
	
	public void purchaseStock(Stock stock) {
		//before calling this method,
		//you need to alter the money inside
		//the linked savings account
		int i = -1;
		int j;
		for(j = 0; j < stocks.size(); j++) {
			Stock s = stocks.get(j);
			if(s.equals(stock) && s.getPrice() == stock.getPrice()) {
				i = j;
				break;
			}
		}
		if(i == -1) {
			stocks.add(stock);
		}
		else {
			Stock s = stocks.get(i);
			s.setUnit(s.getUnit() + stock.getUnit());
		}
	}
	
	public void sellStock(String id, int unit) {
		int i = -1;
		Stock s = new Stock(id);
		for(int j = 0; j < stocks.size(); j++) {
			if(stocks.get(j).equals(s)) {
				i = j;
				break;
			}
		}
		if(i != -1) {
			s = stocks.get(i);
			if(s.getUnit() > unit) {
				s.setUnit(s.getUnit() - unit);
			}
		}
		//after calling this method,
		//you need to get savings account and
		//alter the money inside it
	}
	
	public ArrayList<Stock> getStockList(){
		return stocks;
	}

}
