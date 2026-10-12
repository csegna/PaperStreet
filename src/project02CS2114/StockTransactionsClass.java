package project02CS2114;

// -------------------------------------------------------------------------
/**
 * Stores a stock transaction.
 * 
 * @author mlkuhl
 * @version Oct 11, 2026
 */
public class StockTransactionsClass
{
    private StockInfoClass stock;
    private int shares;
    private double price;
    private String type;
    private int day;

    // ----------------------------------------------------------
    /**
     * Create a new StockTransactionsClass object.
     * 
     * @param stock
     *            what stock
     * @param shares
     *            how many shares
     * @param price
     *            price of the stock
     * @param type
     *            ?
     * @param day
     *            what day it was bought on
     */
    public StockTransactionsClass(
        StockInfoClass stock,
        int shares,
        double price,
        String type,
        int day)
    {
        this.stock = stock;
        this.shares = shares;
        this.price = price;
        this.type = type;
        this.day = day;
    }


    // ----------------------------------------------------------
    /**
     * Buys the stock if the player is able to, else returns false.
     * 
     * @param player
     *            The profile of the player.
     * @param stock
     *            What stock to buy.
     * @param shares
     *            How many shares
     * @return true if successfull, else false.
     */
    public
        boolean
        buyStock(playerProfileClass player, StockInfoClass stock, int shares)
    {
        if (stock.getSharePrice() * shares > player.getBalance())
        {
            return false;
        }
        double newBalance =
            player.getBalance() - shares * stock.getSharePrice();
        player.setBalance(newBalance);

        for (int i = 0; i < player.getHoldings().size(); i++)
        {
            if (player.getHoldings().get(i).getStock().getTicker()
                .equals(stock.getTicker()))
            {
                HoldingClass thisStock = player.getHoldings().get(i);
                thisStock.setShares(thisStock.getShares() + shares);
                return true;
            }
        }

        HoldingClass newHolding = new HoldingClass(stock, shares);

        player.getHoldings().add(newHolding);
        return true;

    }


    // ----------------------------------------------------------
    /**
     * Tries and sells the players stock.
     * 
     * @param player
     *            Name of the player
     * @param stock
     *            Stock to sell
     * @param shares
     *            How many shares of said stock
     * @return true/false depending if it was a success or not.
     */
    public
        boolean
        sellStock(playerProfileClass player, StockInfoClass stock, int shares)
    {
        for (int i = 0; i < player.getHoldings().size(); i++)
        {
            HoldingClass currHolding = player.getHoldings().get(i);
            if (currHolding.getStock().getTicker().equals(stock.getTicker()))
            {
                if (currHolding.getShares() >= shares)
                {
                    currHolding.setShares(currHolding.getShares() - shares);
                    player.setBalance(
                        player.getBalance() + stock.getSharePrice() * shares);
                    return true;
                }
            }
        }

        return false;
    }


    // ----------------------------------------------------------
    /**
     * Gets the stock associated.
     * 
     * @return said stock.
     */
    public StockInfoClass getStock()
    {
        return stock;
    }


    // ----------------------------------------------------------
    /**
     * Gets the shares associated
     * 
     * @return said shares.
     */
    public int getShares()
    {
        return shares;
    }


    // ----------------------------------------------------------
    /**
     * Getter for the price
     * 
     * @return said price.
     */
    public double getPrice()
    {
        return price;
    }


    // ----------------------------------------------------------
    /**
     * Gets the type of transaction (buy/sell).
     * 
     * @return the type.
     */
    public String getType()
    {
        return type;
    }


    // ----------------------------------------------------------
    /**
     * Gets the day of the transaction.
     * 
     * @return that day...
     */
    public int getDay()
    {
        return day;
    }
}
