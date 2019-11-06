package backend;
/**
 * Class that represents USD, one of the currencies our system needs.
 */
public class YEN extends Currency {

    YEN() {
        super(SharedConstants.YEN, 100, 0);
    }

    YEN(double balance) {
        super(SharedConstants.YEN, 100, balance);
    }
}
