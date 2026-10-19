package project02CS2114;

// -------------------------------------------------------------------------
/**
 * HoldingClas for players stocks, its shares, and averagePurchasePrice.
 * 
 * @author mlkuhl
 * @version Oct 11, 2026
 */
public class HoldingClass
{
    private StockInfoClass stock;
    private int numShares;

    // ----------------------------------------------------------
    /**
     * Create a new HoldingClass object.
     * 
     * @param stock
     * @param shares
     */
    public HoldingClass(StockInfoClass stock, int shares)
    {
        this.stock = stock;
        this.numShares = shares;
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
     * @return the amount of shares of this associated stock.
     */
    public int getShares()
    {
        return numShares;
    }


    // ----------------------------------------------------------
    /**
     * Setter for numShares
     * 
     * @param shares
     *            amount of shares to update with
     */
    public void setShares(int shares)
    {
        if (shares < 0)
        {
            throw new IllegalArgumentException(
                "Cannot set to a negaative amount of shares!");
        }
        this.numShares = shares;
    }


    // ----------------------------------------------------------
    /**
     * Gets the current value of the holding based on numShares and stock price.
     * 
     * @return said current value.
     */
    public double getCurrentValue()
    {
        return (numShares * stock.getSharePrice());
    }
}
