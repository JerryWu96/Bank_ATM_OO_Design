public class Currency {
    private String name;
    private double exchangeRate;

    Currency(String name, double exchangeRate) {
        this.name = name;
        this.exchangeRate = exchangeRate;
    }

    public String getName() {
        return this.name;
    }

    public double getExchangeRate() {
        return this.exchangeRate;
    }

}
