package backend;

public class StockSell extends Transaction{
    private String stockID;
    private String savAccountID;
    private int unit;

    StockSell(String userID, int creationDay, String savAccountID, String stockID, int unit) {
        super(userID, creationDay, null, "StockSell");
        this.stockID = stockID;
        this.savAccountID = savAccountID;
        this.unit = unit;
    }

    /**
     * execute transaction: sell stock
     */
    public String startTransaction() {
        return BankPortal.getInstance().getBank().sellStock(getSavAccountID(), getStockID(), getUnit());
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
        return "Day " + getDay() + " customer " + getUserID() + " sold " + unit + " units of " + getStockID() + "stock";
    }
}
