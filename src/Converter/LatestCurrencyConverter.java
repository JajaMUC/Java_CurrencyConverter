package Converter;
import Interfaces.CurrencyConverter;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class LatestCurrencyConverter implements CurrencyConverter {

    @Override
    public double convertCurrency(double amount, String sourceCurrency, String targetCurrency) {

        try {
            final String apiURL = "https://api.freecurrencyapi.com/v1/latest", apiKey = "fca_live_ebkqvlEl0ySQ8CW2JWgT15fwLbvNCRAeuSSahmDE";
            final String urlString = String.format("%s?apikey=%s&base_currency=%s&currencies=%s", apiURL, apiKey, sourceCurrency, targetCurrency);
            System.out.println(urlString);

            final URL url = new URL(urlString);
            final HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            final int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                final BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String response = "";
                String line = "";
                while ((line = reader.readLine()) != null) {
                    response += line;
                }
                reader.close();

                final double exchangeRate = extractExchangeRate(response, targetCurrency);
                return amount * exchangeRate;

            } else {
                System.out.println("API request failed with response code: " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    private double extractExchangeRate(String data, String targetCurrency){

        final String currencyString = "\"" + targetCurrency + "\":";
        final int index = data.indexOf(currencyString) + targetCurrency.length() + 3;
        final String number = data.substring(index, data.length()-2);

        return Double.parseDouble(number);
    }
}
