package backend;

public class StockSell extends Transaction {
    private String stockID;
    private String secAccountID;
    private String company;
    private int unit;
    private double targetPrice;

    public StockSell(String userID, int creationDay, String secAccountID, String stockID, int unit, String company, double targetPrice) {
        super(userID, creationDay, SharedConstants.USD, SharedConstants.STOCK_SELL);
        this.stockID = stockID;
        this.secAccountID = secAccountID;
        this.unit = unit;
        this.company = company;
        this.targetPrice = targetPrice;
    }

    /**
     * execute transaction: sell stock
     */
    public String startTransaction() {
        return BankPortal.getInstance().getBank().sellStock(getSecAccountID(), getStockID(), getUnit(), getCompany(), getPrice());
    }

    public String getStockID() {
        return this.stockID;
    }

    public String getSecAccountID() {
        return this.secAccountID;
    }

    public String getCompany() {
        return this.company;
    }

    public int getUnit() {
        return this.unit;
    }

    public double getPrice() {
        return this.targetPrice;
    }

    @Override
    public String toString() {
        return "Day " + getDay() + ": customer " + getUserID() + " sold " + unit + " units of " + getStockID() + "stock\n";
    }
}
