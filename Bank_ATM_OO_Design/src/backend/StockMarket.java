package backend;

import java.util.List;
import java.util.ArrayList;

/**
 * class for handling stock market
 */
public class StockMarket {
    private List<Stock> stockList;

    private static StockMarket stockMarket = null;// Singleton design pattern

    /**
     * Singleton design pattern is applied
     * BankPortal serves as a unified interface between backend logic and frontend GUI
     *
     * @return instance of BankPortal
     */
    public static StockMarket getInstance() {
        if (stockMarket == null) {
            stockMarket = new StockMarket();
        }
        return stockMarket;
    }

    public StockMarket() {
        initStock();
    }

    public String getStockCompany(String stockID) {
        for (Stock stock : stockList) {
            if (stock.getID().equals(stockID)) {
                return stock.getCompany();
            }
        }
        return SharedConstants.ERR_STOCK_NOT_EXIST;
    }

    public Double getStockPrice(String stockID) {
        for (Stock stock : stockList) {
            if (stock.getID().equals(stockID)) {
                return stock.getPrice();
            }
        }
        return null;
    }

    // TODO: We should add a SQL connection here to initialize stocks
    public void initStock() {
        this.stockList = new ArrayList<Stock>() {{
            add(new Stock("AAPL", "Apple Inc.", 260.0, 1));
            add(new Stock("FB", "Facebook Inc", 190.0, 1));
            add(new Stock("AMZN", "Amazon.com Inc", 1800.0, 1));
            add(new Stock("GOOGL", "Google.com Inc", 1320.0, 1));
        }};
    }
    // TODO: We should add a SQL connection here to update a single stock price and save it

    /**
     * update a single stock with a new price
     *
     * @param stockID
     * @param newPrice
     */
    public String updateStockPrice(String stockID, Double newPrice) {
        for (Stock stock : stockList) {
            if (stock.getID().equals(stockID)) {
                stock.setPrice(newPrice);
                return SharedConstants.SUCCESS_UPDATE_STOCK_PRICE;
            }
        }
        return SharedConstants.ERR_STOCK_NOT_EXIST;
    }

    /**
     * Get all stock IDs
     * @return String array of IDs
     */
    public String[] getAllStockID() {
        List<String> stockIDList = new ArrayList<>();
        for (Stock stock : stockList) {
           stockIDList.add(stock.getID());
        }
        return stockIDList.toArray(new String[0]);
    }

    /**
     * Get all stock objects.
     * @return A list of stocks
     */
    public List<Stock> getAllStocks() {
        return this.stockList;
    }
}