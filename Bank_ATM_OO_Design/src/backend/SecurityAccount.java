package backend;

import java.util.Map;
import java.util.concurrent.*;
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
    private Map<String, Stock> stocks;

    public SecurityAccount(String bankID, String userID, String accountType, Integer postfix, String savingsAccountID) {
        super(bankID + SharedConstants.DELIMITER + userID + SharedConstants.DELIMITER + SharedConstants.SEC +
                SharedConstants.DELIMITER + postfix, bankID, userID, accountType);
        this.savingsAccountID = savingsAccountID;
        this.stocks = new ConcurrentHashMap<>();
        this.isActive = true;
    }

    public SecurityAccount(String accountID, String bankID, String userID, String savingsAccountID) {
        super(accountID, bankID, userID, SharedConstants.SAV);
        this.savingsAccountID = savingsAccountID;
        this.isActive = true;
    }

    public void restoreStock(String stockID, Stock s){
        stocks.put(stockID, s);
    }

    public boolean isActive() {
        return isActive;
    }

    /**
     * buy stock API. It calls updateStock() to update the status and balance of the savings account.
     *
     * @param stockID
     * @param unit    stock units. It must be positive when calling this api
     * @return
     */
    public String buyStock(String stockID, int unit, String company, double curStockPrice) {
        SavingsAccount savingsAccount = BankPortal.getInstance().getBank().getSavingsAccount(savingsAccountID);
        if (savingsAccount.lowerThanThreshold()) {
            return SharedConstants.ERR_INSUFFICIENT_BALANCE;
        } else if (!isActive()) {
            // with enough balance and was set inactive, reactivate the account
            toggleStatus();
        }

        // If the current user has bought the stock.
        if (!stocks.containsKey(company)) {
            Stock newStock = new Stock(stockID, company, curStockPrice, unit);
            stocks.put(company, newStock);
            updateSavBalance(savingsAccount, curStockPrice, -unit);
            return SharedConstants.SUCCESS_TRANSACTION;
        }
        // else, update the stock unit
        return updateStock(stockID, company, curStockPrice, unit, SharedConstants.STOCK_PURCHASE);
    }


    /**
     * sell stock API. It calls updateStock() to update the status and balance of the savings account.
     *
     * @param stockID
     * @param unit    stock units. It must be positive when calling this api
     * @return
     */
    public String sellStock(String stockID, int unit, String company, double targetStockPrice) {
        if (!stocks.containsKey(company)) {
            return SharedConstants.ERR_STOCK_NOT_EXIST;
            // If there is corresponding stock, then we should update its unit.
        }
        return updateStock(stockID, company, targetStockPrice, unit, SharedConstants.STOCK_SELL);
    }


    public List<Stock> getStockList() {
        List<Stock> allStocksList = new ArrayList<>();
        allStocksList.addAll(stocks.values());
        return allStocksList;
    }

    /**
     * Toggle the status of the current sec account. If it was active, set inactive, vice versa
     */
    private void toggleStatus() {
        if (!this.isActive()) {
            this.isActive = true;
        } else {
            this.isActive = false;
        }
    }

    /**
     * update savings balance given a stock price and units
     *
     * @param targetStockPrice
     * @param unit
     * @return
     */
    private String updateSavBalance(SavingsAccount savingsAccount, double targetStockPrice, int unit) {
        double balanceDiff = targetStockPrice * unit;
        USD usdDiff = new USD(balanceDiff);
        return savingsAccount.setBalance(usdDiff);
    }

    /**
     * Update stock units as well as update savings account balance
     *
     * @param stockID
     * @param targetStockPrice
     * @param unit             negative if we buy stock, positive if we sell stock.
     * @return
     */
    private String updateStock(String stockID, String company, double targetStockPrice, int unit, String tradeType) {
        SavingsAccount savingsAccount = BankPortal.getInstance().getBank().getSavingsAccount(savingsAccountID);
        Stock stock = stocks.get(company);

        // update savings account balance
        if (tradeType.equals(SharedConstants.STOCK_PURCHASE)) {
            String result = updateSavBalance(savingsAccount, targetStockPrice, -unit);
            if (result.equals(SharedConstants.ERR_INSUFFICIENT_BALANCE)) {
                return SharedConstants.ERR_INSUFFICIENT_BALANCE;
            }
            stock.setUnit(unit);
        } else {
            int curUnit = stock.getUnit();
            if (curUnit < unit) {
                return SharedConstants.ERR_INSUFFICIENT_STOCK;
            }
            stock.setUnit(-unit);
            if (stock.getUnit() == 0) {
                stocks.remove(company);
            }
            updateSavBalance(savingsAccount, targetStockPrice, unit);
        }

        if (savingsAccount.lowerThanThreshold()) {
            // if the balance is no longer sufficient, deactivate security account.
            toggleStatus();
        } else if (!isActive()) {
            // if balance is enough (for example, deposit some money) and it was inactive, then activate the account
            toggleStatus();
        }
        return SharedConstants.SUCCESS_TRANSACTION;
    }

}
