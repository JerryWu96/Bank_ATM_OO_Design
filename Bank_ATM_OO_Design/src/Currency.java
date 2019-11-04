/**
 * Base class of a currency. It is designed for a more flexible extension of differenct curreincies.
 */
import java.text.DecimalFormat;

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
        DecimalFormat formatter = new DecimalFormat("#.##");
        return Double.parseDouble(formatter.format(this.balance));
    }

    public void addBalance(double amount) {
        this.balance += amount;
    }

    public String getName() {
        return this.name;
    }

    public double getExchangeRate() {
        return this.exchangeRate;
    }

}
