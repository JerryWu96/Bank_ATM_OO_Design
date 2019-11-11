package backend;

public class SharedConstants {
    // Constant String names
    public static final String CUSTOMER = "Customer";
    public static final String MANAGER = "Manager";

    public static final String CK = "CK";
    public static final String SAV = "SAV";
    public static final String SEC = "SEC";
    public static final String DELIMITER = "_";

    public static final String LOAN = "Loan";
    public static final String DEPOSIT = "Deposit";
    public static final String WITHDRAW = "Withdraw";
    public static final String TRANSFER = "Transfer";
    public static final String LOAN_CREATE = "LoanCreate";
    public static final String LOAN_PAY_OFF = "LoanPayOff";
    public static final String STOCK_PURCHASE = "StockPurchase";
    public static final String STOCK_SELL = "StockSell";
    public static final String ACCOUNT_OPEN = "AccountOpen";
    public static final String ACCOUNT_CLOSE = "AccountClose";

    public static final String USD = "USD";
    public static final String CNY = "CNY";
    public static final String YEN = "YEN";

    public static final String BANK_NAME = "Bank of Fools";
    public static final String BANK_ID = "BofF";

    // Numeric values that can be modified
    public static double OPERATION_FEE = 2;   // 2 unit per currency
    public static double LOAN_INTEREST_RATE = 0.1; // 10% per day
    public static double SAVINGS_INTEREST_RATE = 0.0001; // 0.01% per day
    // SAV accounts that have more than 10000 dollars of balance is eligible to earn interest/open security accounts
    public static double SAVINGS_AMOUNT_THRESHOLD = 10000;

    // Successfully executed operations
    public static final String SUCCESS_CLOSE_ACCOUNT = "SuccessCloseAccount";
    public static final String SUCCESS_AUTHENTICATE_USER = "SuccessAuthenticateUser";
    public static final String SUCCESS_UPDATE_STOCK_PRICE = "SuccessUpdateStockPrice";
    public static final String SUCCESS_TRANSACTION = "SuccessTransaction";

    // Errors
    public static final String ERR_USER_NOT_EXIST = "ErrUserNotExist";
    public static final String ERR_ACCOUNT_NOT_EXIST = "ErrAccountNotExist";
    public static final String ERR_STOCK_NOT_EXIST = "ErrStockNotExist";
    public static final String ERR_PERMISSION_DENIED = "ErrPermissionDenied";
    public static final String ERR_INVALID_ARGUMENT = "ErrInvalidArgument";
    public static final String ERR_WRONG_PASS = "ErrWrongPass";
    public static final String ERR_INSUFFICIENT_COLLATERAL = "ErrInsufficientCollateral";
    public static final String ERR_INSUFFICIENT_BALANCE = "ErrInsufficientBalance";
    public static final String ERR_INSUFFICIENT_STOCK = "ErrInsufficientStock";
    public static final String ERR_OPEN_ACCOUNT = "ErrOpenAccount";
    public static final String ERR_CLOSE_ACCOUNT = "ErrCloseAccount";
    public static final String ERR_INVALID_DAY = "ErrInvalidDay";
    public static final String ERR_INVALID_TRANSACTION = "ErrInvalidTransaction";

}
