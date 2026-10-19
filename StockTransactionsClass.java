package project02CS2114;

// -------------------------------------------------------------------------
/**
 * Stores one stock transaction (a buy or a sell) and provides the logic to
 * carry out buys and sells for a player.
 *
 * @author mlkuhl
 * @version Oct 11, 2026
 */
public class StockTransactionsClass
{
    /** Transaction type for a purchase. */
    public static final String BUY = "BUY";

    /** Transaction type for a sale. */
    public static final String SELL = "SELL";

    private final StockInfoClass stock;
    private final int shares;
    private final double price;
    private final String type;
    private final int day;

    // ----------------------------------------------------------
    /**
     * Create a new StockTransactionsClass object.
     *
     * @param stock
     *            what stock; must not be null
     * @param shares
     *            how many shares; must be positive
     * @param price
     *            price per share; must be positive
     * @param type
     *            BUY or SELL
     * @param day
     *            the trading day it happened on; at least 1
     * @throws IllegalArgumentException
     *             if any argument is invalid
     */
    public StockTransactionsClass(
        StockInfoClass stock,
        int shares,
        double price,
        String type,
        int day)
    {
        if (stock == null)
        {
            throw new IllegalArgumentException("Stock must not be null");
        }
        if (shares <= 0)
        {
            throw new IllegalArgumentException("Shares must be positive");
        }
        if (!(price > 0) || Double.isInfinite(price))
        {
            throw new IllegalArgumentException("Price must be positive");
        }
        if (!BUY.equals(type) && !SELL.equals(type))
        {
            throw new IllegalArgumentException(
                "Type must be BUY or SELL, got " + type);
        }
        if (day < 1)
        {
            throw new IllegalArgumentException("Day must be at least 1");
        }
        this.stock = stock;
        this.shares = shares;
        this.price = price;
        this.type = type;
        this.day = day;
    }


    // ----------------------------------------------------------
    /**
     * Buys shares at the stock's current price if the player can afford
     * them. On success, the cash is deducted, the holding is created or
     * updated (including its average purchase price), and the transaction
     * is added to the player's history. On failure nothing changes.
     *
     * @param player
     *            the player buying
     * @param stock
     *            what stock to buy
     * @param shares
     *            how many shares
     * @param day
     *            the current trading day
     * @return true if successful, else false.
     */
    public static boolean buyStock(
        playerProfileClass player,
        StockInfoClass stock,
        int shares,
        int day)
    {
        if (player == null || stock == null || shares <= 0 || day < 1)
        {
            return false;
        }
        double price = stock.getSharePrice();
        double cost = price * shares;
        if (cost > player.getBalance())
        {
            return false;
        }
        player.setBalance(player.getBalance() - cost);

        HoldingClass holding = player.findHolding(stock.getTicker());
        if (holding == null)
        {
            player.getHoldings().add(new HoldingClass(stock, shares, price));
        }
        else
        {
            holding.addShares(shares, price);
        }
        player.getTransactionHistory().record(
            new StockTransactionsClass(stock, shares, price, BUY, day));
        return true;
    }


    // ----------------------------------------------------------
    /**
     * Sells shares at the stock's current price if the player owns enough.
     * On success, the cash is added, the holding is reduced (and removed
     * when it reaches zero shares), and the transaction is added to the
     * player's history. On failure nothing changes.
     *
     * @param player
     *            the player selling
     * @param stock
     *            the stock to sell
     * @param shares
     *            how many shares
     * @param day
     *            the current trading day
     * @return true if successful, else false.
     */
    public static boolean sellStock(
        playerProfileClass player,
        StockInfoClass stock,
        int shares,
        int day)
    {
        if (player == null || stock == null || shares <= 0 || day < 1)
        {
            return false;
        }
        HoldingClass holding = player.findHolding(stock.getTicker());
        if (holding == null || holding.getShares() < shares)
        {
            return false;
        }
        double price = stock.getSharePrice();
        holding.removeShares(shares);
        if (holding.getShares() == 0)
        {
            player.getHoldings().remove(holding);
        }
        player.setBalance(player.getBalance() + price * shares);
        player.getTransactionHistory().record(
            new StockTransactionsClass(stock, shares, price, SELL, day));
        return true;
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
     * Gets the number of shares traded.
     *
     * @return the shares.
     */
    public int getShares()
    {
        return shares;
    }


    // ----------------------------------------------------------
    /**
     * Gets the price per share at the time of the transaction.
     *
     * @return the price.
     */
    public double getPrice()
    {
        return price;
    }


    // ----------------------------------------------------------
    /**
     * Gets the type of transaction.
     *
     * @return BUY or SELL.
     */
    public String getType()
    {
        return type;
    }


    // ----------------------------------------------------------
    /**
     * Gets the day of the transaction.
     *
     * @return the trading day.
     */
    public int getDay()
    {
        return day;
    }


    // ----------------------------------------------------------
    /**
     * One line describing the transaction.
     *
     * @return day, type, ticker, shares, price, and total.
     */
    @Override
    public String toString()
    {
        return String.format("Day %2d  %-4s %-6s %5d @ $%,.2f = $%,.2f",
            day, type, stock.getTicker(), shares, price, shares * price);
    }
}