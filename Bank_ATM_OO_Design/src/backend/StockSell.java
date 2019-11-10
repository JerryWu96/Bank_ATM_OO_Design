package backend;

public class StockSell extends Transaction{
    private String stockID;
    private String secAccountID;
    private int unit;

    StockSell(String userID, int creationDay, String secAccountID, String stockID, int unit) {
        super(userID, creationDay, SharedConstants.USD, SharedConstants.STOCK_SELL);
        this.stockID = stockID;
        this.secAccountID = secAccountID;
        this.unit = unit;
    }

    /**
     * execute transaction: sell stock
     */
    public String startTransaction() {
        return BankPortal.getInstance().getBank().sellStock(getSecAccountID(), getStockID(), getUnit());
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
        return "Day " + getDay() + " customer " + getUserID() + " sold " + unit + " units of " + getStockID() + "stock";
    }
}
