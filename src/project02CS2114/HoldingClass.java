package project02CS2114;

// -------------------------------------------------------------------------
/**
 * A player's holding of one stock: the shares owned and the average price
 * paid per share.
 *
 * @author mlkuhl
 * @version Oct 11, 2026
 */
public class HoldingClass
{
    private final StockInfoClass stock;
    private int numShares;
    private double averagePurchasePrice;

    // ----------------------------------------------------------
    /**
     * Creates a holding bought at the stock's current price.
     *
     * @param stock
     *            the stock held; must not be null
     * @param shares
     *            the number of shares; must not be negative
     * @throws IllegalArgumentException
     *             if stock is null or shares is negative
     */
    public HoldingClass(StockInfoClass stock, int shares)
    {
        this(stock, shares, (stock == null) ? 0 : stock.getSharePrice());
    }


    // ----------------------------------------------------------
    /**
     * Creates a holding with a given average purchase price.
     *
     * @param stock
     *            the stock held; must not be null
     * @param shares
     *            the number of shares; must not be negative
     * @param averagePrice
     *            the average price paid per share; must be positive
     * @throws IllegalArgumentException
     *             if stock is null, shares is negative, or the price is not
     *             positive
     */
    public HoldingClass(StockInfoClass stock, int shares, double averagePrice)
    {
        if (stock == null)
        {
            throw new IllegalArgumentException("Stock must not be null");
        }
        if (shares < 0)
        {
            throw new IllegalArgumentException(
                "Cannot hold a negative amount of shares!");
        }
        if (!(averagePrice > 0) || Double.isInfinite(averagePrice))
        {
            throw new IllegalArgumentException(
                "Average price must be a positive number");
        }
        this.stock = stock;
        this.numShares = shares;
        this.averagePurchasePrice = averagePrice;
    }


    // ----------------------------------------------------------
    /**
     * Gets the stock associated with the HoldingClass
     *
     * @return the associated stock.
     */
    public StockInfoClass getStock()
    {
        return stock;
    }


    // ----------------------------------------------------------
    /**
     * Getter for the shares
     *
     * @return the number of shares of this stock.
     */
    public int getShares()
    {
        return numShares;
    }


    // ----------------------------------------------------------
    /**
     * Setter for numShares. Does not change the average purchase price.
     *
     * @param shares
     *            amount of shares to update with
     * @throws IllegalArgumentException
     *             if shares is negative
     */
    public void setShares(int shares)
    {
        if (shares < 0)
        {
            throw new IllegalArgumentException(
                "Cannot set to a negative amount of shares!");
        }
        this.numShares = shares;
    }


    // ----------------------------------------------------------
    /**
     * Gets the average price paid per share.
     *
     * @return the average purchase price.
     */
    public double getAveragePurchasePrice()
    {
        return averagePurchasePrice;
    }


    // ----------------------------------------------------------
    /**
     * Adds shares bought at a given price and updates the average purchase
     * price (weighted by number of shares).
     *
     * @param shares
     *            the shares bought; must be positive
     * @param price
     *            the price paid per share; must be positive
     * @throws IllegalArgumentException
     *             if shares or price is not positive
     */
    public void addShares(int shares, double price)
    {
        if (shares <= 0 || !(price > 0) || Double.isInfinite(price))
        {
            throw new IllegalArgumentException(
                "Shares and price must be positive");
        }
        averagePurchasePrice =
            (averagePurchasePrice * numShares + price * shares)
                / (numShares + shares);
        numShares += shares;
    }


    // ----------------------------------------------------------
    /**
     * Removes shares that were sold. The average purchase price does not
     * change.
     *
     * @param shares
     *            the shares sold; must be positive and at most the shares
     *            held
     * @throws IllegalArgumentException
     *             if shares is out of range
     */
    public void removeShares(int shares)
    {
        if (shares <= 0 || shares > numShares)
        {
            throw new IllegalArgumentException(
                "Cannot remove " + shares + " of " + numShares + " shares");
        }
        numShares -= shares;
    }


    // ----------------------------------------------------------
    /**
     * Gets the current value of the holding based on numShares and stock price.
     *
     * @return said current value.
     */
    public double getCurrentValue()
    {
        return numShares * stock.getSharePrice();
    }


    // ----------------------------------------------------------
    /**
     * Gets the total amount paid for the shares currently held.
     *
     * @return shares times average purchase price.
     */
    public double getCostBasis()
    {
        return numShares * averagePurchasePrice;
    }
}