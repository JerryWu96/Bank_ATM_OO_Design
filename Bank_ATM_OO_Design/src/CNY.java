/**
 * Class that represents CNY, one of the currencies our system needs.
 */
public class CNY extends Currency {

    CNY() {
        super(SharedConstants.CNY, 7, 0);
    }

    CNY(double balance) {
        super(SharedConstants.CNY, 7, balance);
    }
}
