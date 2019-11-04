public class SharedConstants {
    // Constant String names
    public static final String CUSTOMER = "Customer";
    public static final String MANAGER = "Manager";
    public static final String CK = "CK";
    public static final String SAV = "SAV";
    public static final String USD = "USD";
    public static final String CNY = "CNY";
    public static final String YEN = "YEN";
    public static final String BANK_NAME = "Bank of Fools";
    public static final String BANK_ID = "BofF";

    // Numeric constants
    public static final double OPERATION_FEE = 2;   // 2 unit per currency
    public static final double LOAN_INTEREST_RATE = 0.1; // 10% per day
    public static final double SAVINGS_INTEREST_RATE = 0.0001; // 0.01% per day
    public static final double SAVINGS_AMOUNT_THRESHOLD = 1000; // one currency with 100000 units is eligible to earn interest

    // Successfully executed operations
    public static final String SUCCESS_CLOSE_ACCOUNT = "SuccessCloseAccount";
    public static final String SUCCESS_AUTHENTICATE_USER = "SuccessAuthenticateUser";
    public static final String SUCCESS_TRANSACTION = "SuccessTransaction";
    public static final String SUCCESS_GET_LOAN = "SuccessGetLoan";

    // Errors
    public static final String ERR_USER_NOT_EXIST = "ErrUserNotExist";
    public static final String ERR_ACCOUNT_NOT_EXIST = "ErrAccountNotExist";
    public static final String ERR_INVALID_ARGUMENT = "ErrInvalidArgument";
    public static final String ERR_WRONG_PASS = "ErrWrongPass";
    public static final String ERR_INSUFFICIENT_COLLATERAL = "ErrInsufficientCollateral";


    public static final String ERR_INSUFFICIENT_BALANCE = "ErrInsufficientBalance";
    public static final String ERR_OPEN_ACCOUNT = "ErrOpenAccount";
    public static final String ERR_CLOSE_ACCOUNT = "ErrCloseAccount";
}
