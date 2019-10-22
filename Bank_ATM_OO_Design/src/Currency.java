/**
 * Base class of a currency. It is designed for a more flexible extension of differenct curreincies.
 */
public class Currency {
    private String name;
    private double exchangeRate;
    private double balance;

    Currency(String name, double exchangeRate, double balance) {
        this.name = name;
        this.exchangeRate = exchangeRate;
        this.balance = balance;
    }

    public double getBalance() {
        return this.balance;
    }

    public void setAmount(double amount) {
        this.balance += amount;
    }

    public String getName() {
        return this.name;
    }

    public double getExchangeRate() {
        return this.exchangeRate;
    }

}
