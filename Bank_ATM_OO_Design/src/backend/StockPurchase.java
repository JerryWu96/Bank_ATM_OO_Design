package backend;

/**
 * Transaction: buy stock
 */
public class StockPurchase extends Transaction {
    private String stockID;
    private String secAccountID;
    private String company;
    private double curStockPrice;
    private int unit;

    public StockPurchase(String userID, int creationDay, String secAccountID, String stockID, int unit, String company, double curStockPrice) {
        super(userID, creationDay, SharedConstants.USD, SharedConstants.STOCK_PURCHASE);
        this.stockID = stockID;
        this.secAccountID = secAccountID;
        this.unit = unit;
        this.company = company;
        this.curStockPrice = curStockPrice;
    }

    /**
     * execute transaction: buy stock
     */
    public String startTransaction() {
        return BankPortal.getInstance().getBank().buyStock(getSecAccountID(), getStockID(), getUnit(), getCompany(), getPrice());
    }

    public String getStockID() {
        return this.stockID;
    }

    public String getSecAccountID() {
        return this.secAccountID;
    }

    public int getUnit() {
        return this.unit;
    }

    public String getCompany() {
        return this.company;
    }

    public double getPrice() {
        return this.curStockPrice;
    }

    @Override
    public String toString() {
        return "Day " + getDay() + ": customer " + getUserID() + " bought " + unit + " units of " + getStockID() + "stock\n";
    }
}
