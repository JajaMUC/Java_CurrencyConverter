import Converter.FixedCurrencyConverter;
import Converter.HistoricalCurrencyConverter;
import Converter.LatestCurrencyConverter;
import Interfaces.ICurrencyConversion;
import Interfaces.CurrencyConverter;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CurrencyConversionHandler implements ICurrencyConversion {
    private final String[] currency = new String[] {"USD", "EUR", "GBP"};
    private final Map<String, CurrencyConverter> modusMap = new HashMap<>();
    private Date selectedDate;

    public CurrencyConversionHandler() {
        modusMap.put("Fix", new FixedCurrencyConverter());
        modusMap.put("Historisch",new HistoricalCurrencyConverter(this));
        modusMap.put("Echtzeit", new LatestCurrencyConverter());
    }

    @Override
    public String[] getCurrency() {
        return currency;
    }

    @Override
    public String[] getModus() {
      return modusMap.keySet().toArray(new String[0]);
    }

    @Override
    public void setDate(Date date) {
        selectedDate = date;
    }

    @Override
    public Date getDate() {
        return this.selectedDate;
    }

    @Override
    public double performConversion(double amount, String sourceCurrency, String targetCurrency, String converter) {
        final CurrencyConverter currencyConverter = modusMap.get(converter);
        return currencyConverter.convertCurrency(amount, sourceCurrency, targetCurrency);
    }
}