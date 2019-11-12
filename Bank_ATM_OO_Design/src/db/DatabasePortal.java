package db;

import backend.*;

import java.security.Security;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabasePortal{

    private Connection _conn;
    private static DatabasePortal _dbp = null;

    /*
     * Constructor, sets up db connection
     * */
    public DatabasePortal(){
        try{
            _conn = DriverManager.getConnection("jdbc:sqlite:src/db/bank.db");
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    /**
     * DatabasePortal singleton
     */
    public static DatabasePortal getInstance(){
        if (_dbp == null){
            _dbp = new DatabasePortal();
        }
        return _dbp;
    }

    /*
     * Method that instantiates the database to its default form
     * */
    private void initDatabase(){
        String session = "CREATE TABLE IF NOT EXISTS session (\n" +
                "id INTEGER PRIMARY KEY NOT NULL,\n" +
                "day INTEGER);";

        String initSession = "INSERT INTO session (day) values (0);";

        String currencies = "CREATE TABLE IF NOT EXISTS currencies (\n" +
                "id INTEGER PRIMARY KEY NOT NULL,\n" +
                "name TEXT);";

        String usd = "INSERT INTO currencies (name) values (\"USD\");";
        String yen = "INSERT INTO currencies (name) values (\"YEN\");";
        String cny = "INSERT INTO currencies (name) values (\"CNY\");";

        String customers = "CREATE TABLE IF NOT EXISTS customers (\n" +
                "id INTEGER PRIMARY KEY NOT NULL,\n" +
                "username TEXT,\n" +
                "name TEXT,\n" +
                "password TEXT);";

        String defaultCustomer = "INSERT INTO customers (username, name, password \n" +
                "VALUES (\"bwk\", \"Brian\", \"/.,/.,,\");";

        String managers = "CREATE TABLE IF NOT EXISTS managers (\n" +
                "id INTEGER PRIMARY KEY NOT NULL,\n" +
                "username TEXT,\n" +
                "name TEXT,\n" +
                "password TEXT);";

        String defaultManager = "INSERT INTO managers (username, name, password) \n" +
                "VALUES (\"mgmt\", \"Manager\", \"kids\";";

        String accounts = "CREATE TABLE IF NOT EXISTS accounts (\n" +
                "id INTEGER PRIMARY KEY NOT NULL,\n" +
                "account_name TEXT,\n" +
                "customer_id INTEGER REFERENCES customers(id),\n" +
                "account_type TEXT,\n" +
                "fee DECIMAL(19,2),\n" +
                "balance DECIMAL(19,2),\n" +
                "currency INTEGER REFERENCES currencies(id),\n" +
                "interest DECIMAL(19,2),\n" +
                "active  INTEGER);";

        String security_accounts = "CREATE TABLE IF NOT EXISTS security_accounts (\n" +
                "id INTEGER PRIMARY KEY NOT NULL,\n" +
                "account_name TEXT,\n" +
                "customer_id INTEGER REFERENCES customers(id),\n" +
                "savings_account_id INTEGER REFERENCES accounts(id),\n" +
                "active INTEGER);";

        String stockinfo = "CREATE TABLE IF NOT EXISTS stock_info (\n" +
                "id INTEGER PRIMARY KEY NOT NULL,\n" +
                "symbol TEXT,\n" +
                "name TEXT,\n" +
                "price DECIMAL(19,2));";

        String aapl = "INSERT INTO stock_info (symbol, name, price) values (\"AAPL\", \"Apple Inc.\", 260.0);";
        String fb = "INSERT INTO stock_info (symbol, name, price) values (\"FB\", \"Facebook Inc\", 190.0);";
        String amzn = "INSERT INTO stock_info (symbol, name, price) values (\"AMZN\", \"Amazon.com Inc\", 1800.0);";
        String googl = "INSERT INTO stock_info (symbol, name, price) values (\"GOOGL\", \"Google.com Inc\", 1320.0);";

        String stocks = "CREATE TABLE IF NOT EXISTS stocks (\n" +
                "id INTEGER PRIMARY KEY NOT NULL,\n" +
                "account_id INTEGER REFERENCES accounts(id),\n" +
                "stock_id INTEGER REFERENCES stock_info(id),\n" +
                "stock_name TEXT,\n" +
                "purchase_price DECIMAL(19,2),\n" +
                "count INTEGER,\n" +
                "active INTEGER);";

        String transactions = "CREATE TABLE IF NOT EXISTS transactions (\n" +
                "id INTEGER PRIMARY KEY NOT NULL,\n" +
                "customer_id INTEGER REFERENCES customers(id),\n" +
                "src_account_id INTEGER REFERENCES accounts(id),\n" +
                "target_account_id INTEGER REFERENCES account(id),\n" +
                "transaction_name TEXT,\n" +
                "transaction_type TEXT,\n" +
                "timestamp INTEGER,\n" +
                "currency_type INTEGER REFERENCES currencies(id),\n" +
                "currency_moved DECIMAL(19,2),\n" +
                "stock_id INTEGER REFERENCES stock_info(id),\n" +
                "count INTEGER);";

        try{
            Statement stmt = _conn.createStatement();
            stmt.execute(session);
            stmt.execute(initSession);
            stmt.execute(currencies);
            stmt.execute(usd);
            stmt.execute(yen);
            stmt.execute(cny);
            stmt.execute(customers);
            stmt.execute(managers);
            stmt.execute(accounts);
            stmt.execute(security_accounts);
            stmt.execute(stockinfo);
            stmt.execute(aapl);
            stmt.execute(fb);
            stmt.execute(amzn);
            stmt.execute(googl);
            stmt.execute(stocks);
            stmt.execute(transactions);
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    /*
     * Returns a list of all customers stored in the db
     * */
    public List<Customer> getCustomerList(){
        ArrayList<Customer> ret = new ArrayList<Customer>();
        try{
            Statement stmt = _conn.createStatement();
            String sql = "SELECT * FROM customers;";
            stmt.execute(sql);
            ResultSet rs = stmt.getResultSet();
            while(rs.next()){
                Statement accs = _conn.createStatement();
                int id = rs.getInt("id");
                Customer c = new Customer(rs.getString("name"), rs.getString("username"), rs.getString("password"));
                String selectAccs = "SELECT a.* from customers c INNER JOIN accounts a \n" +
                        "ON c.id = a.customer_id \n " +
                        "WHERE c.id = " + id + " AND a.account_type = \"" + SharedConstants.LOAN + "\";";
                accs.execute(selectAccs);
                ResultSet loans = accs.getResultSet();
                while(loans.next()){
                    c.addLoan(loans.getDouble("balance"), loans.getDouble("interest"), loans.getString("currency"));
                }
                ret.add(c);
            }
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        return ret;
    }

    /*
    * Returns all non-loan accounts
    * */
    public List<Account> getAccountList(){
        ArrayList<Account> ret = new ArrayList<Account>();
        try {
            Statement stmt = _conn.createStatement();
            String getAccs = "SELECT * FROM accounts WHERE active = 1;";
            stmt.execute(getAccs);
            ResultSet rs = stmt.getResultSet();
            while(rs.next()){
                String accType = rs.getString("account_type");
                String sql = "SELECT * FROM customers WHERE id = " + rs.getInt("customer_id") + ";";
                Statement innerStmt = _conn.createStatement();
                innerStmt.execute(sql);
                ResultSet innerRs = innerStmt.getResultSet();
                switch(accType){
                    case SharedConstants.SAV:
                        SavingsAccount sa = new SavingsAccount(rs.getString("account_name"), "", innerRs.getString("username"), rs.getDouble("fee"), rs.getDouble("balance"));
                        ret.add(sa);
                        break;
                    case SharedConstants.CK:
                        CheckingAccount ca = new CheckingAccount(rs.getString("account_name"), "", innerRs.getString("username"), rs.getDouble("fee"), rs.getDouble("balance"));
                        ret.add(ca);
                        break;
                    default:break;
                }
            }
            String getSecAccs = "SELECT * FROM security_accounts WHERE active = 1;";
            stmt.execute(getSecAccs);
            rs = stmt.getResultSet();
            while(rs.next()){
                String getStocks = "SELECT * FROM stocks WHERE account_id = " + rs.getInt("id") + ";";
                Statement stockStmt = _conn.createStatement();
                stockStmt.execute(getStocks);
                ResultSet stockRs = stockStmt.getResultSet();

                String sql = "SELECT * FROM accounts WHERE id = " + rs.getInt("savings_account_id") + ";";
                Statement innerStmt = _conn.createStatement();
                innerStmt.execute(sql);
                ResultSet innerRs = innerStmt.getResultSet();
                int custID = innerRs.getInt("customer_id");
                String accName = innerRs.getString("account_name");
                sql = "SELECT * FROM customers WHERE id = \"" + custID + "\";";
                innerStmt = _conn.createStatement();
                innerStmt.execute(sql);
                innerRs = innerStmt.getResultSet();
                SecurityAccount seca = new SecurityAccount(rs.getString("account_name"), "", innerRs.getString("username"), accName);
                while(stockRs.next()) {
                    String stockName = stockRs.getString("stock_name");
                    String stockinfo = "SELECT * FROM stock_info WHERE id = " + stockRs.getInt("stock_id");
                    Statement infoStmt = _conn.createStatement();
                    infoStmt.execute(stockinfo);
                    ResultSet stockinfoRs = infoStmt.getResultSet();
                    Stock s = new Stock(stockinfoRs.getString("symbol"), stockinfoRs.getString("name"), stockRs.getDouble("purchase_price"), stockRs.getInt("count"));
                    seca.restoreStock(stockName, s);
                }
                ret.add(seca);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return ret;
    }

    /*
     * Returns a list of all managers in the db
     * */
    public List<Manager> getManagerList(){
        ArrayList<Manager> ret = new ArrayList<Manager>();
        try{
            Statement stmt = _conn.createStatement();
            String sql = "SELECT * FROM managers;";
            stmt.execute(sql);
            ResultSet rs = stmt.getResultSet();
            while(rs.next()){
                Manager m = new Manager(rs.getString("name"), rs.getString("username"), rs.getString("password"), "");
                ret.add(m);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return ret;
    }

    /*
     * Adds a new customer to the database with the given fields
     * */
    public void addCustomer(String name, String userID, String password){
        try {
            String sql = "INSERT INTO customers (username, name, password) VALUES (?,?,?);";
            PreparedStatement ps = _conn.prepareStatement(sql);
            ps.setString(1, userID);
            ps.setString(2, name);
            ps.setString(3, password);
            ps.executeUpdate();
        } catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    /*
     * Adds a new manager to the database with the given fields
     * */
    public void addManager(String name, String userID, String password, String bankID){
        try {
            String sql = "INSERT INTO customers (username, name, password) VALUES (?,?,?);";
            PreparedStatement ps = _conn.prepareStatement(sql);
            ps.setString(1, userID);
            ps.setString(2, name);
            ps.setString(3, password);
            ps.executeUpdate();
        } catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    /*
     * Gets a list of all active stocks
     * */
    public List<Stock> getStockListByAccount(String accountID){
        ArrayList<Stock> ret = new ArrayList<Stock>();
        try {
            String sql = "SELECT s.* from accounts a INNER JOIN stocks s \n" +
                    "ON s.account_id = a.id \n" +
                    "WHERE a.account_name = ?;";
            PreparedStatement ps = _conn.prepareStatement(sql);
            ps.setString(1, accountID);
            ps.execute();
            ResultSet rs = ps.getResultSet();
            while(rs.next()){
                int id = rs.getInt("stock_id");
                String stockQuery = "SELECT * FROM stock_info WHERE id = " + id + ";";
                Statement stmt = _conn.createStatement();
                stmt.execute(stockQuery);
                ResultSet stock = stmt.getResultSet();
                Stock s = new Stock(stock.getString("symbol"), stock.getString("name"), rs.getDouble("purchase_price"), rs.getInt("count"));
                ret.add(s);
            }
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        return ret;
    }

    /*
     * Returns list of all stocks owned by a single customer.
     * First gets a list of all of the security accounts, then calls getStockListByAccount on each account
     * */
    public List<Stock> getStockListByCustomer(String customerID){
        ArrayList<Stock> ret = new ArrayList<Stock>();
        try {
            String sql = "SELECT a.* from customers c INNER JOIN accounts a \n" +
                    "ON a.customer_id = c.id \n" +
                    "WHERE c.username = ? AND a.account_type = " + SharedConstants.SEC + ";";
            PreparedStatement ps = _conn.prepareStatement(sql);
            ps.setString(1, customerID);
            ps.execute();
            ResultSet rs = ps.getResultSet();
            while(rs.next()){
                List<Stock> accStocks = getStockListByAccount(rs.getString("account_name"));
                ret.addAll(accStocks);
            }
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        return ret;
    }

    /*
     * Returns a list of all accounts
     * */
    public List<Stock> getStockListByAll(){
        ArrayList<Stock> ret = new ArrayList<Stock>();
        try{
            String sql = "SELECT * from stocks;";
            Statement stmt = _conn.createStatement();
            stmt.execute(sql);
            ResultSet rs = stmt.getResultSet();
            while(rs.next()) {
                int id = rs.getInt("stock_id");
                String stockQuery = "SELECT * FROM stock_info WHERE id = " + id + ";";
                Statement info = _conn.createStatement();
                info.execute(stockQuery);
                ResultSet stock = info.getResultSet();
                stock.next();
                Stock s = new Stock(stock.getString("symbol"), stock.getString("name"), rs.getDouble("purchase_price"), rs.getInt("count"));
                ret.add(s);
            }
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        return ret;
    }

    /*
     * Updates the price for a stock
     * */
    public void updateStockPrice(String stockID, double price){
        try{
            String sql = "UPDATE stock_info SET price = " + price + " \n" +
                    "WHERE symbol = ?;";
            PreparedStatement ps = _conn.prepareStatement(sql);
            ps.setString(1, stockID);
            ps.execute();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /*
     * Gets a list of all transactions
     * */
    public List<Transaction> getTransactionList(int day, boolean daily){
        ArrayList<Transaction> ret = new ArrayList<Transaction>();
        try {
            String sql = "SELECT * FROM transactions";
            Statement stmt = _conn.createStatement();
            stmt.execute(sql);
            ResultSet rs = stmt.getResultSet();
            while(rs.next()) {
                Statement loopStmt = _conn.createStatement();
                String cust = "SELECT * FROM customers WHERE id = " + rs.getInt("customer_id") + ";";
                String acc = "SELECT * FROM accounts WHERE id = " + rs.getInt("src_account_id") + ";";
                loopStmt.execute(cust);
                ResultSet custResult = loopStmt.getResultSet();
                loopStmt.execute(acc);
                ResultSet accResult = loopStmt.getResultSet();
                String accountID = accResult.getString("account_name");
                String userID = custResult.getString("username");
                int ts = rs.getInt("timestamp");
                if ((daily && ts == day) || day <= ts) {
                    String extraSql;
                    Statement s = _conn.createStatement();
                    ResultSet extraResult;
                    String currency;
                    String extraString;

                    switch (rs.getString("transaction_type")) {
                        case SharedConstants.DEPOSIT:
                            extraSql = "SELECT * FROM currencies WHERE id = " + rs.getInt("currency_type") + ";";
                            s.execute(extraSql);
                            extraResult = s.getResultSet();
                            currency = extraResult.getString("name");
                            double amount = rs.getDouble("currency_moved");
                            ret.add(new Deposit(accountID, userID, ts, currency, amount));
                            break;
                        case SharedConstants.WITHDRAW:
                            extraSql = "SELECT * FROM currencies WHERE id = " + rs.getInt("currency_type") + ";";
                            s.execute(extraSql);
                            extraResult = s.getResultSet();
                            currency = extraResult.getString("name");
                            amount = rs.getDouble("currency_moved");
                            ret.add(new Withdraw(accountID, userID, ts, currency, amount));
                            break;
                        case SharedConstants.TRANSFER:
                            extraSql = "SELECT * FROM currencies WHERE id = " + rs.getInt("currency_type") + ";";
                            s.execute(extraSql);
                            extraResult = s.getResultSet();
                            currency = extraResult.getString("name");
                            String target = rs.getString("target_account_id");
                            amount = rs.getDouble("currency_moved");
                            ret.add(new Transfer(accountID, target, userID, ts, currency, amount));
                            break;
                        case SharedConstants.LOAN_CREATE:
                            extraSql = "SELECT * FROM currencies WHERE id = " + rs.getInt("currency_type") + ";";
                            s.execute(extraSql);
                            extraResult = s.getResultSet();
                            currency = extraResult.getString("name");
                            amount = rs.getDouble("currency_moved");
                            ret.add(new LoanCreate(userID, ts, currency, amount));
                            break;
                        case SharedConstants.LOAN_PAY_OFF:
                            extraSql = "SELECT * FROM currencies WHERE id = " + rs.getInt("currency_type") + ";";
                            s.execute(extraSql);
                            extraResult = s.getResultSet();
                            currency = extraResult.getString("name");
                            amount = rs.getDouble("currency_moved");
                            ret.add(new LoanPayOff(userID, ts, accountID));
                            break;
                        case SharedConstants.STOCK_PURCHASE:
                            extraSql = "SELECT * FROM stock_info WHERE id = " + rs.getInt("currency_type") + ";";
                            s.execute(extraSql);
                            extraResult = s.getResultSet();
                            currency = extraResult.getString("symbol");
                            int count = rs.getInt("count");
                            String spName = extraResult.getString("name");
                            double spPrice = extraResult.getDouble("price");
                            extraSql = "SELECT * FROM stocks WHERE id = " + rs.getInt("stock_id");
                            s.execute(extraSql);
                            extraResult = s.getResultSet();
                            extraString = extraResult.getString("stock_name");
                            ret.add(new StockPurchase(userID, ts, currency, extraString, count, spName, spPrice));
                            break;
                        case SharedConstants.STOCK_SELL:
                            extraSql = "SELECT * FROM stock_info WHERE id = " + rs.getInt("currency_type") + ";";
                            s.execute(extraSql);
                            extraResult = s.getResultSet();
                            currency = extraResult.getString("symbol");
                            int saleCount = rs.getInt("count");
                            String ssName = extraResult.getString("name");
                            double ssPrice = extraResult.getDouble("price");
                            extraSql = "SELECT * FROM stocks WHERE id = " + rs.getInt("stock_id") + ";";
                            s.execute(extraSql);
                            extraResult = s.getResultSet();
                            extraString = extraResult.getString("stock_name");
                            ret.add(new StockSell(userID, ts, currency, extraString, saleCount, ssName, ssPrice));
                            break;
                        case SharedConstants.ACCOUNT_OPEN:
                            extraSql = "SELECT * FROM accounts WHERE id = " + rs.getInt("account_id") + ";";
                            s.execute(extraSql);
                            extraResult = s.getResultSet();
                            String accOType = extraResult.getString("account_type");
                            ret.add(new AccountOpen(userID, ts, accOType));
                            break;
                        case SharedConstants.ACCOUNT_CLOSE:
                            extraSql = "SELECT * FROM accounts WHERE id = " + rs.getInt("account_id") + ";";
                            s.execute(extraSql);
                            extraResult = s.getResultSet();
                            String accCType = extraResult.getString("account_type");
                            ret.add(new AccountClose(userID, ts, accountID, accCType));
                            break;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return ret;
    }

    /*
     * Adds a transaction to the db
     * */

    /*
    * "id INTEGER PRIMARY KEY NOT NULL,\n" +
                "customer_id INTEGER REFERENCES customers(id),\n" +
                "src_account_id INTEGER REFERENCES accounts(id),\n" +
                "target_account_id INTEGER REFERENCES account(id),\n)" +
                "transaction_name TEXT,\n" +
                "transaction_type TEXT,\n" +
                "timestamp INTEGER,\n" +
                "currency_type INTEGER REFERENCES currencies(id),\n" +
                "currency_moved DECIMAL(19,2),\n" +
                "stock_id INTEGER REFERENCES stock_info(id),\n" +
                "count INTEGER);";
    * */
    public void addTransaction(Transaction t){
        try {
            String insert, update, customerFetch, accountFetch, currencyFetch, stockFetch;
            Statement stmt = _conn.createStatement();
            ResultSet rs;
            int customerID, srcAccountID, targetAccountID, currency, stock;
            double price;
            switch(t.getType()){
                case SharedConstants.DEPOSIT:
                    Deposit d = (Deposit) t;
                    customerFetch = "SELECT * FROM customers WHERE username =\" " + d.getUserID() + "\";";
                    accountFetch = "SELECT * FROM accounts WHERE account_name = \"" + d.getAccountID() + "\";";
                    currencyFetch = "SELECT * FROM currencies WHERE name = \"" + d.getSelectedCurrency() + "\";";
                    stmt.execute(customerFetch);
                    rs = stmt.getResultSet();
                    customerID = rs.getInt("id");
                    stmt.execute(accountFetch);
                    rs = stmt.getResultSet();
                    srcAccountID = rs.getInt("id");
                    stmt.execute(currencyFetch);
                    rs = stmt.getResultSet();
                    currency = rs.getInt("id");
                    insert = "INSERT INTO transactions \n" +
                            "(customer_id, src_account_id, transaction_name, transaction_type, timestamp, currency_type, currency_moved)\n" + "" +
                            "VALUES (" + customerID + "," + srcAccountID + "," + d.getTransactionID() + "\",\"" + d.getType() + "\"," + d.getDay() + "," + currency + "," + d.getDepositAmount() + ");";
                    stmt.execute(insert);
                    update = "UPDATE accounts SET balance = balance + " + d.getDepositAmount() + " WHERE id = " + srcAccountID + ";";
                    stmt.execute(update);
                    break;
                case SharedConstants.WITHDRAW:
                    Withdraw w = (Withdraw) t;
                    customerFetch = "SELECT * FROM customers WHERE username = \"" + w.getUserID() + "\";";
                    accountFetch = "SELECT * FROM accounts WHERE account_name = \"" + w.getAccountID() + "\";";
                    currencyFetch = "SELECT * FROM currencies WHERE name = \"" + w.getSelectedCurrency() + "\";";
                    stmt.execute(customerFetch);
                    rs = stmt.getResultSet();
                    customerID = rs.getInt("id");
                    stmt.execute(accountFetch);
                    rs = stmt.getResultSet();
                    srcAccountID = rs.getInt("id");
                    stmt.execute(currencyFetch);
                    rs = stmt.getResultSet();
                    currency = rs.getInt("id");
                    insert = "INSERT INTO transactions \n" +
                            "(customer_id, src_account_id, transaction_name, transaction_type, timestamp, currency_type, currency_moved)\n" + "" +
                            "VALUES (" + customerID + "," + srcAccountID + ",\"" + w.getTransactionID() + "\",\"" + w.getType() + "\"," + w.getDay() + "," + currency + "," + w.getWithdrawAmount() + ");";
                    stmt.execute(insert);
                    update = "UPDATE accounts SET balance = balance - " + w.getWithdrawAmount() + " WHERE id = " + srcAccountID + ";";
                    stmt.execute(update);
                    break;
                case SharedConstants.TRANSFER:
                    Transfer tr = (Transfer) t;
                    customerFetch = "SELECT * FROM customers WHERE username = \"" + tr.getUserID() + "\";";
                    accountFetch = "SELECT * FROM accounts WHERE account_name = \"" + tr.getSourceAccountID() + "\";";
                    currencyFetch = "SELECT * FROM currencies WHERE name = \"" + tr.getSelectedCurrency() + "\";";
                    stmt.execute(customerFetch);
                    rs = stmt.getResultSet();
                    customerID = rs.getInt("id");
                    stmt.execute(accountFetch);
                    rs = stmt.getResultSet();
                    srcAccountID = rs.getInt("id");
                    accountFetch = "SELECT * FROM accounts WHERE account_name = \"" + tr.getTargetAccountID() + "\";";
                    stmt.execute(accountFetch);
                    rs = stmt.getResultSet();
                    targetAccountID = rs.getInt("id");
                    stmt.execute(currencyFetch);
                    rs = stmt.getResultSet();
                    currency = rs.getInt("id");
                    insert = "INSERT INTO transactions \n" +
                            "(customer_id, src_account_id, target_account_id, transaction_name, transaction_type, timestamp, currency_type, currency_moved)\n" + "" +
                            "VALUES (" + customerID + "," + srcAccountID + "," + targetAccountID + ",\"" + tr.getTransactionID() + "\",\"" + tr.getType() + "\"," + tr.getDay() + "," + currency + "," + tr.getTransferAmount() + ");";
                    stmt.execute(insert);
                    update = "UPDATE accounts SET balance = balance + " + tr.getTransferAmount() + " WHERE id = " + targetAccountID + ";";
                    stmt.execute(update);
                    update = "UPDATE accounts SET balance = balance - " + tr.getTransferAmount() + " WHERE id = " + srcAccountID + ";";
                    stmt.execute(update);
                    break;
                case SharedConstants.LOAN_CREATE:
                    LoanCreate lc = (LoanCreate) t;
                    customerFetch = "SELECT * FROM customers WHERE username = \"" + lc.getUserID() + "\";";
                    accountFetch = "SELECT * FROM accounts WHERE account_name = \"" + lc.getAccountID() + "\";";
                    currencyFetch = "SELECT * FROM currencies WHERE name = \"" + lc.getSelectedCurrency() + "\";";
                    stmt.execute(customerFetch);
                    rs = stmt.getResultSet();
                    customerID = rs.getInt("id");
                    stmt.execute(accountFetch);
                    rs = stmt.getResultSet();
                    srcAccountID = rs.getInt("id");
                    stmt.execute(currencyFetch);
                    rs = stmt.getResultSet();
                    currency = rs.getInt("id");
                    insert = "INSERT INTO transactions \n" +
                            "(customer_id, src_account_id, transaction_name, transaction_type, timestamp, currency_type, currency_moved)\n" + "" +
                            "VALUES (" + customerID + "," + srcAccountID + ",\"" + lc.getTransactionID() + "\",\"" + lc.getType() + "\"," + lc.getDay() + "," + currency + "," + lc.getLoanAmount() + ");";
                    stmt.execute(insert);
                    update = "INSERT INTO accounts \n" +
                            "(account_name, customer_id, account_type, balance, currency, interest, active) \n" +
                            "VALUES (" + lc.getLoanID() + "," + customerID + "," + SharedConstants.LOAN + "," + lc.getLoanAmount() + "," + currency + "," + SharedConstants.LOAN_INTEREST_RATE + "," + 1 + ");";
                    stmt.execute(update);
                    break;
                case SharedConstants.LOAN_PAY_OFF:
                    LoanPayOff lpo = (LoanPayOff) t;
                    customerFetch = "SELECT * FROM customers WHERE username = \"" + lpo.getUserID() + "\";";
                    accountFetch = "SELECT * FROM accounts WHERE account_name = \"" + lpo.getAccountID() + "\";";
                    stmt.execute(customerFetch);
                    rs = stmt.getResultSet();
                    customerID = rs.getInt("id");
                    stmt.execute(accountFetch);
                    rs = stmt.getResultSet();
                    srcAccountID = rs.getInt("id");
                    insert = "INSERT INTO transactions \n" +
                            "(customer_id, src_account_id, transaction_name, transaction_type, timestamp)\n" + "" +
                            "VALUES (" + customerID + "," + srcAccountID + ",\"" + lpo.getTransactionID() + "\",\"" + lpo.getType() + "\"," + lpo.getDay() + ");";
                    stmt.execute(insert);
                    update = "UPDATE accounts SET active = 0 WHERE id = " + srcAccountID + ";";
                    stmt.execute(update);
                    break;
                case SharedConstants.STOCK_PURCHASE:
                    StockPurchase sp = (StockPurchase) t;
                    customerFetch = "SELECT * FROM customers WHERE username = \"" + sp.getUserID() + "\";";
                    accountFetch = "SELECT * FROM security_accounts WHERE account_name = \"" + sp.getSecAccountID() + "\";";
                    currencyFetch = "SELECT * FROM currencies WHERE name = \"" + sp.getSelectedCurrency() + "\";";
                    stockFetch = "SELECT * FROM stock_info WHERE name = \"" + sp.getStockID() + "\";";
                    stmt.execute(customerFetch);
                    rs = stmt.getResultSet();
                    customerID = rs.getInt("id");
                    stmt.execute(accountFetch);
                    rs = stmt.getResultSet();
                    srcAccountID = rs.getInt("id");
                    targetAccountID = rs.getInt("savings_account_id");
                    stmt.execute(currencyFetch);
                    rs = stmt.getResultSet();
                    currency = rs.getInt("id");
                    stmt.execute(stockFetch);
                    rs = stmt.getResultSet();
                    int spID = rs.getInt("id");
                    insert = "INSERT INTO transactions \n" +
                            "(customer_id, src_account_id, target_account_id, transaction_name, transaction_type, timestamp, currency_type, currency_moved, stock_id, count)\n" +
                            "VALUES (" + customerID + "," + srcAccountID + "," + targetAccountID + ",\"" + sp.getTransactionID() + "\",\"" + sp.getType() + "\"," + sp.getDay() + "," + currency + "," + sp.getPrice() + "," + sp.getUnit() + ");";
                    stmt.execute(insert);
                    insert = "INSERT INTO stocks \n" +
                            "(account_id, stock_id, stock_name, purchase_price, count, active) VALUES \n" +
                            "(" + srcAccountID + "," + spID + ",\"" + sp.getStockID() + "\"," + sp.getPrice() + "," + sp.getUnit() + "1);";
                    stmt.execute(insert);
                    update = "UPDATE accounts SET balance = balance - " + (sp.getPrice() * sp.getUnit()) + " WHERE id = " + targetAccountID + ";";
                    stmt.execute(update);
                    break;
                case SharedConstants.STOCK_SELL:
                    StockSell ss = (StockSell) t;
                    customerFetch = "SELECT * FROM customers WHERE username = \"" + ss.getUserID() + "\";";
                    accountFetch = "SELECT * FROM security_accounts WHERE account_name = \"" + ss.getSecAccountID() + "\";";
                    currencyFetch = "SELECT * FROM currencies WHERE name = \"" + ss.getSelectedCurrency() + "\";";
                    stockFetch = "SELECT * FROM stock_info WHERE name = \"" + ss.getStockID() + "\";";
                    stmt.execute(customerFetch);
                    rs = stmt.getResultSet();
                    customerID = rs.getInt("id");
                    stmt.execute(accountFetch);
                    rs = stmt.getResultSet();
                    srcAccountID = rs.getInt("id");
                    targetAccountID = rs.getInt("savings_account_id");
                    stmt.execute(currencyFetch);
                    rs = stmt.getResultSet();
                    currency = rs.getInt("id");
                    stmt.execute(stockFetch);
                    rs = stmt.getResultSet();
                    int ssID = rs.getInt("id");
                    insert = "INSERT INTO transactions \n" +
                            "(customer_id, src_account_id, target_account_id, transaction_name, transaction_type, timestamp, currency_type, currency_moved, stock_id, count)\n" +
                            "VALUES (" + customerID + "," + srcAccountID + "," + targetAccountID + ",\"" + ss.getTransactionID() + "\",\"" + ss.getType() + "\"," + ss.getDay() + "," + currency + "," + ss.getPrice() + "," + ss.getUnit() + ");";
                    stmt.execute(insert);
                    insert = "INSERT INTO stocks \n" +
                            "(account_id, stock_id, stock_name, purchase_price, count, active) VALUES \n" +
                            "(" + srcAccountID + "," + ssID + ",\"" + ss.getStockID() + "\"," + ss.getPrice() + "," + ss.getUnit() + "1);";
                    stmt.execute(insert);
                    update = "UPDATE accounts SET balance = balance + " + (ss.getPrice() * ss.getUnit()) + " WHERE id = " + targetAccountID + ";";
                    stmt.execute(update);
                    break;
                case SharedConstants.ACCOUNT_OPEN:
                    AccountOpen ao = (AccountOpen) t;
                    customerFetch = "SELECT * FROM customers WHERE username = \"" + ao.getUserID() + "\";";
                    accountFetch = "SELECT * FROM accounts WHERE account_name = \"" + ao.getAccountID() + "\";";
                    currencyFetch = "SELECT * FROM currencies WHERE name = \''" + ao.getSelectedCurrency() + "\";";
                    stmt.execute(customerFetch);
                    rs = stmt.getResultSet();
                    customerID = rs.getInt("id");
//                    stmt.execute(accountFetch);
//                    rs = stmt.getResultSet();
//                    srcAccountID = rs.getInt("id");
                    stmt.execute(currencyFetch);
                    rs = stmt.getResultSet();
                    currency = rs.getInt("id");

                    if(ao.getAccountType().equals(SharedConstants.SEC)) {
                        update = "INSERT INTO accounts \n" +
                                "(account_name, customer_id, account_type, balance, currency, interest, active) \n" +
                                "VALUES (\"" + ao.getAccountID() + "\"," + customerID + ",\"" + ao.getAccountType() + "\"," + 0 + "," + currency + "," + SharedConstants.SAVINGS_INTEREST_RATE + "," + 1 + ");";
                    } else {
                        String getBackSav = "SELECT * FROM accounts WHERE account_name = \"" + ao.getSavAccountID() + "\";";
                        Statement getBackStmt = _conn.createStatement();
                        getBackStmt.execute(getBackSav);
                        ResultSet getBackRs = getBackStmt.getResultSet();
                        update = "INSERT INTO security_accounts \n" +
                                "(account_name, customer_id, savings_account_id, active) VALUES \n" +
                                "(\"" + ao.getAccountID() + "\"," + customerID + "," + getBackRs.getInt("id") + "," + "1);";
                    }
                    String getSecId = "SELECT * FROM security_accounts WHERE account_name = \"" + ao.getAccountID() + "\";";
                    Statement getSecStmt = _conn.createStatement();
                    getSecStmt.execute(getSecId);
                    ResultSet getSecRs = getSecStmt.getResultSet();
                    insert = "INSERT INTO transactions \n" +
                            "(customer_id, src_account_id, transaction_name, transaction_type, timestamp, currency_type, currency_moved)\n" + "" +
                            "VALUES (" + customerID + "," + getSecRs.getInt("id") + ",\"" + ao.getTransactionID() + "\",\"" + ao.getType() + "\"," + ao.getDay() + "," + currency + ");";
                    stmt.execute(insert);
                    stmt.execute(update);
                    break;
                case SharedConstants.ACCOUNT_CLOSE:
                    AccountClose ac = (AccountClose) t;
                    customerFetch = "SELECT * FROM customers WHERE username = \"" + ac.getUserID() + "\";";
                    accountFetch = "SELECT * FROM accounts WHERE account_name = \"" + ac.getAccountID() + "\";";
                    currencyFetch = "SELECT * FROM currencies WHERE name = \"" + ac.getSelectedCurrency() + "\";";
                    stmt.execute(customerFetch);
                    rs = stmt.getResultSet();
                    customerID = rs.getInt("id");
                    stmt.execute(accountFetch);
                    rs = stmt.getResultSet();
                    srcAccountID = rs.getInt("id");
                    stmt.execute(currencyFetch);
                    rs = stmt.getResultSet();
                    currency = rs.getInt("id");
                    insert = "INSERT INTO transactions \n" +
                            "(customer_id, src_account_id, transaction_name, transaction_type, timestamp)\n" + "" +
                            "VALUES (" + customerID + "," + srcAccountID + ",\"" + ac.getTransactionID() + "\",\"" + ac.getType() + "\"," + ac.getDay() + ");";
                    stmt.execute(insert);
                    update = "UPDATE accounts SET active = 0 WHERE id = " + srcAccountID + ";";
                    stmt.execute(update);
                    break;
            }
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
//    String accounts = "CREATE TABLE IF NOT EXISTS accounts (\n" +
//            "id INTEGER PRIMARY KEY NOT NULL,\n" +
//            "account_name TEXT,\n" +
//            "customer_id INTEGER REFERENCES customers(id),\n" +
//            "account_type TEXT,\n" +
//            "fee DECIMAL(19,2),\n" +
//            "balance DECIMAL(19,2),\n" +
//            "currency INTEGER REFERENCES currencies(id),\n" +
//            "interest DECIMAL(19,2)),\n" +
//            "active  INTEGER);";
//String security_accounts = "CREATE TABLE IF NOT EXISTS security_accounts (\n" +
//        "id INTEGER PRIMARY KEY NOT NULL,\n" +
//        "account_name TEXT,\n" +
//        "customer_id INTEGER REFERENCES customers(id),\n" +
//        "savings_account_id INTEGER REFERENCES accounts(id),\n" +
//        "active INTEGER);";

    /*
     * Adds a new customer
     * */
    public void addCustomer(Customer c){
        String username = c.getUserID();
        String name = c.getName();
        String password = c.getPassword();
        String sql = "INSERT INTO customers (username, name, password) VALUES (?,?,?);";
        try {
            PreparedStatement ps = _conn.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, name);
            ps.setString(3, password);
            ps.execute();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /*
     * Adds a new manager
     * */
    public void addManager(Manager m){
        String username = m.getUserID();
        String name = m.getName();
        String password = m.getPassword();
        String sql = "INSERT INTO managers (username, name, password) VALUES (?,?,?);";
        try {
            PreparedStatement ps = _conn.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, name);
            ps.setString(3, password);
            ps.execute();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /*
     * Stores session (day)
     * */
    public void storeSession(int day){
        String sql = "UPDATE session SET day = " + day + " WHERE id=1;";
        try{
            Statement stmt = _conn.createStatement();
            stmt.execute(sql);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /*
     * Restores the session (day)
     * */
    public int restoreSession(){
        String sql = "SELECT * FROM session WHERE id=1;";
        int day = 0;
        try{
            Statement stmt = _conn.createStatement();
            stmt.execute(sql);
            ResultSet rs = stmt.getResultSet();
            day = rs.getInt("day");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return day;
    }

}