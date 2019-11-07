package stock;

public class Stock {
	
	private String id;
	private String currency;
	private String name;
	private int unit;
	private double price;
	
	public Stock() {
	}
	
	public Stock(String id) {
		this.id = id;
	}
	
	public Stock(String id, String currency, String name, int unit, double price) {
		this.id = id;
		this.currency = currency;
		this.name = name;
		this.unit = unit;
		this.price = price;
	}
	
	public String getId() {
		return id;
	}
	
	public String getCurrency() {
		return currency;
	}
	
	public String getName() {
		return name;
	}
	
	public int getUnit() {
		return unit;
	}
	
	public double getPrice() {
		return price;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setUnit(int unit) {
		this.unit = unit;
	}
	
	public void setPrice(int price) {
		this.price = price;
	}
	
	public boolean equals(Stock stock) {
		return this.id.equals(stock.getId());
	}
	
	@Override
	public String toString() {
		return "ID:"+id+" CURRENCY:"+currency+" NAME:"+name+" UNIT:"+unit+" PRICE:"+price+System.lineSeparator();
	}
}