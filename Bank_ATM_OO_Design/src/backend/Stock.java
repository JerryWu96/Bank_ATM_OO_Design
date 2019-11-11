package backend;

/**
 * class for a single stock entry
 */
public class Stock implements Comparable<Stock>{

    private String stockID;
    private String company;
    private int unit;
    private double price;

    public Stock(String stockID) {
        this.stockID = stockID;
    }

    public Stock(String stockID, String company, double price, int unit) {
        this.stockID = stockID;
        this.company = company;
        this.unit = unit;
        this.price = price;
    }

    public String getID() {
        return stockID;
    }

    public String getName() {
        return company;
    }

    public int getUnit() {
        return unit;
    }

    public double getPrice() {
        return price;
    }

    public void setId(String stockID) {
        this.stockID = stockID;
    }

    public void setName(String company) {
        this.company = company;
    }

    public String getCompany() {
        return this.company;
    }

    public void setUnit(int unit) {
        this.unit += unit;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean equals(Stock stock) {
        return this.stockID.equals(stock.getID());
    }

    @Override
    public int compareTo(Stock s) {
        return this.getID().compareTo(s.getID());
    }

    @Override
    public String toString() {
        return "StockID:" + stockID + " Company:" + company + " Unit:" + unit + " Price:" + price + System.lineSeparator();
    }
}