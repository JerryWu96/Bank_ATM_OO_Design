/**
 * Base class of a currency. It is designed for a more flexible extension of different currencies.
 */
import java.text.DecimalFormat;

public class Currency {
    private String name;
    private double conversionRate;
    private double balance;

    Currency(String name, double conversionRate, double balance) {
        this.name = name;
        this.conversionRate = conversionRate;
        this.balance = balance;
    }

    public double getBalance() {
        DecimalFormat formatter = new DecimalFormat("#.##");
        return Double.parseDouble(formatter.format(this.balance));
    }

    public void addBalance(double amount) {
        this.balance += amount ;
    }

    public double convertToUSD() { // to USD in default
        return this.balance / getExchangeRate();
    }

    public String getName() {
        return this.name;
    }

    public double getExchangeRate() {
        return this.conversionRate;
    }
}
