package project02CS2114;

/**
 * A stock in PaperStreet: ticker, company name, description, and price
 * Two stocks are equal when their tickers match
 *
 * @author Corbin Segna
 * @version Oct 11 2026
 */
public class StockInfoClass
{
    private final String ticker;
    private final String companyName;
    private final String description;
    private double sharePrice;

    /**
     * Creates a stock. The ticker is stored trimmed and in upper case
     *
     * @param ticker      the ticker symbol
     * @param companyName the company name
     * @param description a short description (null becomes "")
     * @param sharePrice  the starting price; must be positive
     * @throws IllegalArgumentException if ticker or name is blank, or the
     *                                  price is not positive
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
     * Gets the ticker
     *
     * @return the ticker
     */
    public String getTicker()
    {
        return ticker;
    }

    /**
     * Gets the company name
     *
     * @return the company name
     */
    public String getCompanyName()
    {
        return companyName;
    }

    /**
     * Gets the description
     *
     * @return the description
     */
    public String getDescription()
    {
        return description;
    }

    /**
     * Gets the current share price
     *
     * @return the share price
     */
    public double getSharePrice()
    {
        return sharePrice;
    }

    /**
     * Sets a new share price
     *
     * @param newPrice the new price; must be positive
     * @throws IllegalArgumentException if the price is not positive
     */
    public void setSharePrice(double newPrice)
    {
        checkPrice(newPrice);
        sharePrice = newPrice;
    }

    /**
     * Trims a ticker and makes it upper case, so "aapl" becomes "AAPL".
     *
     * @param rawTicker the ticker as typed
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
     * @param other the object to compare
     * @return true if the tickers match
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
     * Hash code based on the ticker
     *
     * @return the hash code
     */
    @Override
    public int hashCode()
    {
        return ticker.hashCode();
    }

    /**
     * One line for the market listing
     *
     * @return ticker, name, and price
     */
    @Override
    public String toString()
    {
        return String.format("%-6s %-24s $%,10.2f", ticker, companyName,
            sharePrice);
    }

    /**
     * Throws if a price is zero, negative, or not a real number
     *
     * @param price the price to check
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
