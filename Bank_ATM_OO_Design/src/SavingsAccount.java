import java.util.ArrayList;
import java.util.List;
/**
 * Savings account. We can add withdrawTimesLimit in the future to make it more realistic.
 */
public class SavingsAccount extends Account {
    private double operationFee;
    private List<Currency> currencies;

    SavingsAccount(String bankID, String userID, String accountType, Integer postfix) {
        super(bankID + "_" + userID + "_SAV_" + postfix, bankID, userID, accountType);

        this.operationFee = SharedConstants.OPERATION_FEE;

        currencies = new ArrayList<>();
        currencies.add(new USD());
        currencies.add(new CNY());
        currencies.add(new YEN());

        this.setBalance(-operationFee, SharedConstants.USD);
        this.setBalance(-operationFee, SharedConstants.CNY);
        this.setBalance(-operationFee, SharedConstants.YEN);
    }

    private boolean doesHitThreshold(Currency currency) {
        return this.getBalance(currency.getName()) >= SharedConstants.SAVINGS_AMOUNT_THRESHOLD;
    }

    public void setBalance(double amount, String currencyName) {
        for (Currency currency : currencies) {
            if (currency.getName().equals(currencyName)) {
                currency.addBalance(amount);
            }
        }
    }

    public double getBalance(String currencyName) {
        for (Currency currency : currencies) {
            if (currency.getName().equals(currencyName)) {
                return currency.getBalance();
            }
        }
        return -1;
    }

    public void computeInterest() {
        System.out.println("Compute!");
        for (Currency currency : currencies) {
            System.out.println("Currency:" + currency.getName() + " Balance: " + getBalance(currency.getName()));
            if (doesHitThreshold(currency)) {
                double currentBalance = currency.getBalance();
                System.out.println("current balance:" + currentBalance);
                currency.addBalance(currentBalance * SharedConstants.SAVINGS_INTEREST_RATE);
            }
        }
    }
}
