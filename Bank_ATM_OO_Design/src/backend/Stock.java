package backend;

public class Stock {

    private String id;
    private String company;
    private int unit;
    private double price;

    public Stock(String id) {
        this.id = id;
    }

    public Stock(String id, String company, int unit, double price) {
        this.id = id;
        this.company = company;
        this.unit = unit;
        this.price = price;
    }

    public String getId() {
        return id;
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

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String company) {
        this.company = company;
    }

    public void setUnit(int unit) {
        this.unit = unit;
    }

    public void addUnit(int unit) {
        this.unit += unit;
    }

    public void setPrice(int price) {
        this.price = price;
    }


    public boolean equals(Stock stock) {
        return this.id.equals(stock.getId());
    }

    @Override
    public String toString() {
        return "ID:" + id + " NAME:" + company + " UNIT:" + unit + " PRICE:" + price + System.lineSeparator();
    }
}