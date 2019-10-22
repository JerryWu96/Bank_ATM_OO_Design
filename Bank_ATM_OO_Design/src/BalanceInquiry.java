public class BalanceInquiry extends Transaction {

    BalanceInquiry(String accountID, String userID, int creationDay, String currency) {
        super(accountID, userID, creationDay, currency, 0);
    }

    public void startTransaction() {

    }

//    public double getBalance(Account account) {
//        return account.getBalance();
//    }
}
