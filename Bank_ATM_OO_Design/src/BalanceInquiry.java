public class BalanceInquiry extends Transaction {

    BalanceInquiry(int accountID, int userID, int creationDay, Currency currency) {
        super(accountID, userID, creationDay, currency, 0);
    }

    public double getBalance(Account account) {
        return account.getBalance();
    }
}
