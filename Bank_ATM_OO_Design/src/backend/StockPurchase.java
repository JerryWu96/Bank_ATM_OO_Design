package backend;

/**
 * Transaction: buy stock
 */
public class StockPurchase extends Transaction {
    private String stockID;
    private String savAccountID;
    private int unit;

    StockPurchase(String userID, int creationDay, String savAccountID, String stockID, int unit) {
        super(userID, creationDay, null, "StockPurchase");
        this.stockID = stockID;
        this.savAccountID = savAccountID;
    }

    /**
     * execute transaction: buy stock
     */
    public String startTransaction() {
        return BankPortal.getInstance().getBank().buyStock(getSavAccountID(), getStockID(), getUnit());
    }

    public String getStockID() {
        return this.stockID;
    }

    public String getSavAccountID() {
        return this.savAccountID;
    }

    public int getUnit() {
        return this.unit;
    }

    @Override
    public String toString() {
        return "Day " + getDay() + " customer " + getUserID() + " bought " + unit + " units of " + getStockID() + "stock";
    }
}
