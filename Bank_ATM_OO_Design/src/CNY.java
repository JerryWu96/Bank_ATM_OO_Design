public class CNY extends Currency {
    private double exchangeRate;

    CNY() {
        super("CNY", 7, 0);
        this.exchangeRate = 7;
    }

    CNY(double balance) {
        super("CNY", 7, balance);
        this.exchangeRate = 7;
    }
}
