package Interfaces;

import java.util.Date;

public interface ICurrencyConversion {

    String[] getCurrency();
    String[] getModus();
    void setDate(Date date);
    Date getDate();
    double performConversion(double amount, String sourceCurrency, String targetCurrency, String converter);
}
