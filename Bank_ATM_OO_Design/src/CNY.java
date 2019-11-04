/**
 * Class that represents CNY, one of the currencies our system needs.
 */
public class CNY extends Currency {
    private double exchangeRate;

    CNY() {
        super(SharedConstants.CNY, 7, 0);
        this.exchangeRate = 7;
    }

    CNY(double balance) {
        super(SharedConstants.CNY, 7, balance);
        this.exchangeRate = 7;
    }
}
