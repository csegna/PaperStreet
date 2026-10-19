package project02CS2114;
import com.crazzyghost.alphavantage.timeseries.response.TimeSeriesResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class makeStockMap
{
    public static Map<Double, List<String>> stockPrices =
        new HashMap<>();

    public static void setStockPrices(
        TimeSeriesResponse timeSeriesResponse)
    {
        if (timeSeriesResponse == null
            || timeSeriesResponse.getMetaData() == null
            || timeSeriesResponse.getStockUnits() == null
            || timeSeriesResponse.getStockUnits().isEmpty())
        {
            return;
        }

        String ticker =
            timeSeriesResponse.getMetaData().getSymbol();

        double price =
            timeSeriesResponse.getStockUnits().get(0).getClose();

        if (stockPrices.containsKey(price))
        {
            List<String> changeVal = stockPrices.get(price);

            if (!changeVal.contains(ticker))
            {
                changeVal.add(ticker);
            }
        }
        else
        {
            List<String> value = new ArrayList<>();
            value.add(ticker);
            stockPrices.put(price, value);
        }
    }

    public static Map<Double, List<String>> getStockPrices()
    {
        return stockPrices;
    }
}
