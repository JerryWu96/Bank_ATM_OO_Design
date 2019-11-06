package backend;
/**
 * Class that represents USD, one of the currencies our system needs.
 */
public class USD extends Currency {
    private double exchangeRate;

    USD() {
        super(SharedConstants.USD, 1, 0);
        this.exchangeRate = 1;
    }

    USD(double balance) {
        super(SharedConstants.USD, 1, balance);
        this.exchangeRate = 1;
    }
}
