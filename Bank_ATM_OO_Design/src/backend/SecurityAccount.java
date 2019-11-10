package backend;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

/**
 * class for handling security account
 */
public class SecurityAccount extends Account {

    private String savingsAccountID;
    // if the account is inactive, only selling stocks is allowed.
    private boolean isActive;
    // company - Stock mapping
    private Map<String, ArrayList<Stock>> stocks;

    public SecurityAccount(String bankID, String userID, String accountType, Integer postfix, String savingsAccountID) {
        super(bankID + SharedConstants.DELIMITER + userID + SharedConstants.DELIMITER + SharedConstants.SEC + SharedConstants.DELIMITER + postfix, bankID, userID, accountType);
        this.savingsAccountID = savingsAccountID;
        this.stocks = new HashMap<>();
        this.isActive = true;
    }

    public boolean isActive() {
        return isActive;
    }

    private void setInactive() {
        this.isActive = false;
    }

    /**
     * Update stock units as well as update savings account balance
     *
     * @param stockID
     * @param curStockPrice
     * @param unit positive if we buy stock, negative if we sell stock.
     * @return
     */
    private String updateStock(String stockID, String company, double curStockPrice, int unit) {
        double balanceDiff = curStockPrice * unit;
        USD usdDiff = new USD(balanceDiff);
        // TODO: verify if the savingsAccount here is a reference.
        SavingsAccount savingsAccount = BankPortal.getInstance().getBank().getSavingsAccount(savingsAccountID);
        savingsAccount.setBalance(usdDiff);
        if (savingsAccount.lowerThanThreshold()) {
            setInactive();
            return SharedConstants.ERR_INSUFFICIENT_BALANCE;
        }
        for (Stock stock : stocks.get(company)) {
            if (stock.getID().equals(stockID)) {
                stock.setUnit(unit);
            }
        }
        return SharedConstants.SUCCESS_TRANSACTION;
    }

    /**
     * buy stock API. It calls updateStock() to update the status and balance of the savings account.
     *
     * @param stockID
     * @param unit    stock units. It must be positive when calling this api
     * @return
     */
    public String buyStock(String stockID, int unit) {
        StockMarket stockMarket = StockMarket.getInstance();
        String company = stockMarket.getStockCompany(stockID);
        Double curStockPrice = stockMarket.getStockPrice(stockID);

        // If the current user has bought the stock.
        if (stocks.containsKey(company)) {
            return updateStock(stockID, company, curStockPrice, unit);
        } else {
            Stock newStock = new Stock(stockID, company, curStockPrice, unit);
            stocks.put(company, new ArrayList<Stock>() {
                {
                    add(newStock);
                }
            });
            return SharedConstants.SUCCESS_TRANSACTION;
        }
    }


    /**
     * sell stock API. It calls updateStock() to update the status and balance of the savings account.
     *
     * @param stockID
     * @param unit    stock units. It must be positive when calling this api
     * @return
     */
    public String sellStock(String stockID, int unit) {
        StockMarket stockMarket = StockMarket.getInstance();
        String company = stockMarket.getStockCompany(stockID);
        Double curStockPrice = stockMarket.getStockPrice(stockID);

        // If the stockID is valid, update.
        if (stocks.containsKey(company)) {
            return updateStock(stockID, company, curStockPrice, -1 * unit);
        } else {
            return SharedConstants.ERR_STOCK_NOT_EXIST;
        }
    }


    public List<Stock> getStockList() {
        List<Stock> allStocksList = new ArrayList<>();
        for (ArrayList<Stock> stockList : stocks.values()) {
            allStocksList.addAll(stockList);
        }
        return allStocksList;
    }
}
