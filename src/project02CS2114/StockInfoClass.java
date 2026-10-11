package project02CS2114;

/**
 * // -------------------------------------------------------------------------
/**
 *  a stock that can be traded:
 *  has ticker symbol, company name, short desc, and current share price
 * 
 *  @author corbin segna
 *  @version Oct 11, 2026
 */
public class StockInfoClass
{
    private final String ticker;
    private final String companyName;
    private final String description;
    private double sharePrice;

    
    /** 
     * creates a stock
     * 
     * @param ticker      the ticker symbol, e.g. "AAPL"; stored trimmed and
     *                    in upper case, so "aapl " becomes "AAPL"
     * @param companyName the company's name
     * @param description a short description of what the company does;
     *                    null is stored as an empty string
     * @param sharePrice  the starting share price; must be greater than 0
     * @throws IllegalArgumentException if ticker or companyName is null or
     *                                  blank, or sharePrice is not a
     *                                  positive number
     */
    public StockInfoClass(
        String ticker,
        String companyName,
        String description,
        double sharePrice)
    {
        if (ticker == null || ticker.trim().isEmpty())
        {
            throw new IllegalArgumentException("Ticker must not be blank");
        }
        if (companyName == null || companyName.trim().isEmpty())
        {
            throw new IllegalArgumentException(
                "Company name must not be blank");
        }
        checkPrice(sharePrice);
        this.ticker = normalizeTicker(ticker);
        this.companyName = companyName.trim();
        this.description = (description == null) ? "" : description.trim();
        this.sharePrice = sharePrice;
    }
    
    /**
     * gets the ticker symbol
     * 
     * @return the ticker, upper case
     */
    public String getTicker()
    {
        return ticker;
    }
    
    /**
     * gets the company name
     * 
     * @return the company name
     */
    public String getCompanyName()
    {
        return companyName;
    }
    
    /**
     * gets the description
     */
    public String getDescription()
    {
        return description;
    }
    
    /**
     * gets the share price
     * 
     * @return the share price
     */
    public double getSharePrice()
    {
        return sharePrice;
    }
    
    /**
     * Changes the current share price
     *
     * @param newPrice the new share price must be greater than 0
     * @throws IllegalArgumentException if newPrice is zero, negative, or
     *                            not a number; the old price is kept
     */
    public void setSharePrice(double newPrice)
    {
        checkPrice(newPrice);
        sharePrice = newPrice;
    }


    /**
     * Turns user input ticker text into the stored form: trimmed and in
     * upper case. MarketClass uses this so "aapl" finds "AAPL".\
     *
     * @param rawTicker the text the user typed
     * @return the normalized ticker, or null if rawTicker is null
     */
    public static String normalizeTicker(String rawTicker)
    {
        if (rawTicker == null)
        {
            return null;
        }
        return rawTicker.trim().toUpperCase();
    }


    /**
     * Two stocks are equal when their tickers match
     *
     * @param other the object to compare with
     * @return true if other is a StockInfoClass with the same ticker
     */
    @Override
    public boolean equals(Object other)
    {
        if (this == other)
        {
            return true;
        }
        if (!(other instanceof StockInfoClass))
        {
            return false;
        }
        return ticker.equals(((StockInfoClass)other).ticker);
    }


    /**
     * Hash code consistent with equals(): based on the ticker
     *
     * @return the hash code
     */
    @Override
    public int hashCode()
    {
        return ticker.hashCode();
    }


    /**
     * One line for the market listing, e.g.
     * "AAPL   Apple Inc.                $189.50".
     *
     * @return a readable summary of this stock
     */
    @Override
    public String toString()
    {
        return String.format("%-6s %-24s $%,10.2f", ticker, companyName,
            sharePrice);
    }

    /**
     * Rejects prices that are zero, negative, NaN or infinite
     *
     * @param price the price to check
     * @throws IllegalArgumentException if the price is not valid
     */
    private static void checkPrice(double price)
    {
        if (!(price > 0) || Double.isInfinite(price))
        {
            throw new IllegalArgumentException(
                "Share price must be a positive number, got " + price);
        }
    } 
    
}
