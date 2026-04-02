package Converter;

import Interfaces.CurrencyConverter;

import java.util.HashMap;
import java.util.Map;

public class FixedCurrencyConverter implements CurrencyConverter {

    private final Map<String, Double> fixedExchangeRate = new HashMap<>();

    public FixedCurrencyConverter() {
        fixedExchangeRate.put("USD", 1.0);
        fixedExchangeRate.put("EUR", 0.85);
        fixedExchangeRate.put("GBP", 0.73);
    }

    public double convertCurrency(double amount, String sourceCurrency, String targetCurrency) {
        final double sourceExchangeRate = fixedExchangeRate.get(sourceCurrency);
        final double targetExchangeRate = fixedExchangeRate.get(targetCurrency);

        return amount * targetExchangeRate / sourceExchangeRate;
    }
}