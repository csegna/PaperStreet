package project02CS2114;

import java.util.HashMap;
import java.util.Map;

public class playerTransactionHistory
{
    // ~ Fields ................................................................
    private Map<String, Integer> stocksBoughtInformation;
    private String date;
    private int amountDeducted;

    public playerTransactionHistory(String dateUE, int amountDeductedUE)
    {
        stocksBoughtInformation = new HashMap<>();
        date = dateUE;
        amountDeducted = amountDeductedUE;
    }


    public void addNewStockInfo(String stockTicker, Integer stockPrice)
    {
        stocksBoughtInformation.put(stockTicker, stockPrice);

    }


    public void setNewDate(String date)
    {
        this.date = date;
    }


    public void setNewAmountDeducted(int amountDeducted)
    {
        this.amountDeducted = amountDeducted;
    }


    public Integer getSpecificStockPrice(String ticker)
    {
        return stocksBoughtInformation.get(ticker);
    }


    public String getSpecificTickerBasedPrice(Integer price)
    {
        for (String i : stocksBoughtInformation.keySet())
        {
            if (stocksBoughtInformation.get(i) == price)
            {
                return i;
            }
            else
            {
                continue;
            }

        }
        return "Ticker not found";
    }


    public String getDate()
    {
        return this.date;
    }


    public int getDailyDeduction()
    {
        return this.amountDeducted;
    }

    // ~Public Methods ........................................................

}
