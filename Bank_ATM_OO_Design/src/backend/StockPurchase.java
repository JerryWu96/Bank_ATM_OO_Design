package backend;

/**
 * Transaction: buy stock
 */
public class StockPurchase extends Transaction {
    private String stockID;
    private String secAccountID;
    private int unit;

    StockPurchase(String userID, int creationDay, String secAccountID, String stockID, int unit) {
        super(userID, creationDay, SharedConstants.USD, SharedConstants.STOCK_PURCHASE);
        this.stockID = stockID;
        this.secAccountID = secAccountID;
        this.unit = unit;
    }

    /**
     * execute transaction: buy stock
     */
    public String startTransaction() {
        return BankPortal.getInstance().getBank().buyStock(getSecAccountID(), getStockID(), getUnit());
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

    @Override
    public String toString() {
        return "Day " + getDay() + " customer " + getUserID() + " bought " + unit + " units of " + getStockID() + "stock";
    }
}
