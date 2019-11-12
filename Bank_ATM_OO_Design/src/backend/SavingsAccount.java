package backend;

import java.util.List;
import java.util.ArrayList;

/**
 * Savings account. We can add withdrawTimesLimit in the future to make it more realistic.
 */
public class SavingsAccount extends Account {
    private double operationFee;
    private USD usd;

    SavingsAccount(String bankID, String userID, String accountType, Integer postfix) {
        super(bankID + SharedConstants.DELIMITER + userID + SharedConstants.DELIMITER + SharedConstants.SAV +
                SharedConstants.DELIMITER + postfix, bankID, userID, accountType);
        this.operationFee = SharedConstants.OPERATION_FEE;
        this.usd = new USD(-operationFee);
    }

    public SavingsAccount(String accountID, String bankID, String userID, double fee, double balance) {
        super(accountID, bankID, userID, SharedConstants.SAV);
        this.operationFee = fee;
        this.usd = new USD(balance);
    }

    /**
     * check whether balance is above the threshold where interests start to be calculated
     * @return
     */
    public boolean higherThanThreshold() {
        return this.getBalance() >= SharedConstants.SAVINGS_AMOUNT_THRESHOLD;
    }

    public boolean lowerThanThreshold() {
        return this.getBalance() < SharedConstants.SAVINGS_AMOUNT_THRESHOLD;
    }

    public String setBalance(Currency currency) {
        return this.usd.addBalance(currency.convertToUSD());
    }

    public double getBalance() {
        return this.usd.getBalance();
    }

    /**
     * calculate interests and then apply
     */
    public void computeInterest() {
        if (higherThanThreshold()) {
            double currentBalance = usd.getBalance();
            usd.addBalance(currentBalance * SharedConstants.SAVINGS_INTEREST_RATE);
        }
    }
}
