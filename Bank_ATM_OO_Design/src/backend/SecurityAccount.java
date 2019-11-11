package backend;

import java.util.Map;
import java.util.HashMap;
import java.util.concurrent.*;
import java.util.List;
import java.util.ArrayList;

public class SecurityAccount extends Account {

    private String savingsAccountID;
    // if the account is inactive, only selling stocks is allowed.
    private boolean isActive;
    // company - Stock mapping
    private Map<String, ArrayList<Stock>> stocks;

    public SecurityAccount(String bankID, String userID, String accountType, Integer postfix, String savingsAccountID) {
        super(bankID + SharedConstants.DELIMITER + userID + SharedConstants.DELIMITER + SharedConstants.SEC + SharedConstants.DELIMITER + postfix, bankID, userID, accountType);
        this.savingsAccountID = savingsAccountID;
        this.stocks = new ConcurrentHashMap<>();
        this.isActive = true;
    }

    public boolean isActive() {
        return isActive;
    }

    /**
     * Toggle the status of the current sec account. If it was active, set inactive, vice versa
     */
    private void toggleStatus() {
        if (!this.isActive) {
            this.isActive = true;
        } else {
            this.isActive = false;
        }
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
        // TODO: verify if the savingsAccount here is a reference.
        SavingsAccount savingsAccount = BankPortal.getInstance().getBank().getSavingsAccount(savingsAccountID);

        // update savings account balance
        if (tradeType.equals(SharedConstants.STOCK_PURCHASE)) {
            double balanceDiff = -1 * targetStockPrice * unit;
            USD usdDiff = new USD(balanceDiff);
            savingsAccount.setBalance(usdDiff);
        } else {
            double balanceDiff = targetStockPrice * unit;
            USD usdDiff = new USD(balanceDiff);
            savingsAccount.setBalance(usdDiff);
        }

        if (savingsAccount.lowerThanThreshold()) {
            System.out.println("Status toggled to inactive in updateStock");
            // if the balance is no longer sufficient, deactivate security account.
            toggleStatus();
        } else if (!isActive) {
            // if balance is enough (for example, deposit some money) and it was inactive, then activate the account
            System.out.println("Status toggled to active in updateStock");
            toggleStatus();
        }
        // update stock units
        for (Stock stock : stocks.get(company)) {
            if (stock.getID().equals(stockID)) {
                switch (tradeType) {
                    case SharedConstants.STOCK_PURCHASE:
                        stock.setUnit(unit);
                        System.out.println("stock unit now = " + stock.getUnit());
                    case SharedConstants.STOCK_SELL:
                        int curUnit = stock.getUnit();
                        if (curUnit < unit) {
                            System.out.println("InSufficient stock");
                            return SharedConstants.ERR_INSUFFICIENT_STOCK;
                        }
                        stock.setUnit(-unit);
                        if (stock.getUnit() == 0) {
                            System.out.println("Remove stock");
                            stocks.get(company).remove(stock);
                        }
                }
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
    public String buyStock(String stockID, int unit, String company, double curStockPrice) {
        SavingsAccount savingsAccount = BankPortal.getInstance().getBank().getSavingsAccount(savingsAccountID);
        if (savingsAccount.lowerThanThreshold()) {
            return SharedConstants.ERR_INSUFFICIENT_BALANCE;
        } else if (!isActive) {
            System.out.println("Status toggled to active in buystock");
            // with enough balance and was set inactive, reactivate the account
            toggleStatus();
        }

        // If the current user has bought the stock.
        if (!stocks.containsKey(company)) {
            System.out.println("stock not found!");
            Stock newStock = new Stock(stockID, company, curStockPrice, unit);
            stocks.put(company, new ArrayList<Stock>() {
                {
                    add(newStock);
                }
            });
            System.out.println("new stock added!");
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
        for (ArrayList<Stock> stockList : stocks.values()) {
            allStocksList.addAll(stockList);
        }
        return allStocksList;
    }
}
