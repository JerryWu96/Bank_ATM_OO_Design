package backend;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

public class StockMarket {
    private List<Stock> stockList;
    private Map<String, String> stockIDtoCompany;
    private Map<String, Double> stockIDtoPrice;

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
        return stockIDtoCompany.getOrDefault(stockID, SharedConstants.ERR_STOCK_NOT_EXIST);
    }

    public Double getStockPrice(String stockID) {
        return stockIDtoPrice.getOrDefault(stockID, -1.0);
    }

    // TODO: We should add a SQL connection here to initialize stocks
    public void initStock() {
        this.stockList = new ArrayList<Stock>() {{
            add(new Stock("AAPL", "Apple Inc.", 260.0, 1));
            add(new Stock("FB", "Facebook Inc", 190.0, 1));
            add(new Stock("AMZN", "Amazon.com Inc", 1800.0, 1));
            add(new Stock("GOOGL", "Google.com Inc", 1320.0, 1));
        }};

        this.stockIDtoCompany = new HashMap<String, String>() {{
            put("AAPL", "Apple Inc.");
            put("FB", "Facebook Inc");
            put("AMZN", "Amazon.com Inc");
            put("GOOGL", "Google.com Inc");
        }};
        this.stockIDtoPrice = new HashMap<String, Double>() {{
            put("AAPL", 260.0);
            put("FB", 190.0);
            put("AMZN", 1800.0);
            put("GOOGL", 1320.0);
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
        if (stockIDtoPrice.containsKey(stockID)) {
            stockIDtoPrice.put(stockID, newPrice);
            return SharedConstants.SUCCESS_UPDATE_STOCK_PRICE;
        } else {
            return SharedConstants.ERR_STOCK_NOT_EXIST;
        }
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
}
