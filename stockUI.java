package project02CS2114;
import com.crazzyghost.alphavantage.AlphaVantage;
import com.crazzyghost.alphavantage.parameters.OutputSize;
import com.crazzyghost.alphavantage.timeseries.response.TimeSeriesResponse;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class stockUI
{
    private static final List<String> stockTickers =
        Arrays.asList("AAPL", "MSFT", "NVDA", "AMZN", "TSLA");

    public static Map<String, Double> fetchStockData()
    {
        for (String ticker : stockTickers)
        {
//            System.out.println("Fetching: " + ticker);

            try
            {
                TimeSeriesResponse response = AlphaVantage.api()
                    .timeSeries()
                    .daily()
                    .forSymbol(ticker)
                    .outputSize(OutputSize.COMPACT)
                    .fetchSync();

                if (response == null
                    || response.getStockUnits() == null
                    || response.getStockUnits().isEmpty())
                {
                    System.out.println(
                        "No stock data returned for " + ticker);
                    continue;
                }

                makeStockMap.setStockPrices(response);
//                System.out.println("Success: " + ticker);
            }
            catch (Exception error)
            {
                System.out.println(
                    "Error for " + ticker + ": " + error.getMessage());
            }

            try
            {
                Thread.sleep(2000);
            }
            catch (InterruptedException error)
            {
                Thread.currentThread().interrupt();
                return null;
            }
        }

        return printStockPrices();
    }

    private static Map<String, Double> printStockPrices()
    {
        Map<String, Double> stockTickersAndPrices = new HashMap<>();
        Map<Double, List<String>> currStockPrices =
            makeStockMap.getStockPrices();

        if (currStockPrices == null || currStockPrices.isEmpty())
        {
            System.out.println("No stock prices available.");
            return null;
        }

        int count = 0;

        for (Map.Entry<Double, List<String>> entry
            : currStockPrices.entrySet())
        {
            List<String> tickers = entry.getValue();

            if (tickers == null)
            {
                continue;
            }
            
            for (String ticker : tickers)
            {
                if (count >= 6)
                {
                    return null;
                }

                stockTickersAndPrices.put(ticker, entry.getKey());

                count++;
            }
        }
        return stockTickersAndPrices;
    }
}

