package project02CS2114;

/**
 * Stores the market's stocks in a LinkedChain. Tickers are unique
 *
 * @author Corbin Segna
 * @version Oct 11 2026
 */
public class StockInfoChain
{
    private final LinkedChain<StockInfoClass> stocks;

    /**
     * Creates an empty chain of stocks
     */
    public StockInfoChain()
    {
        stocks = new LinkedChain<StockInfoClass>();
    }

    /**
     * Adds a stock unless its ticker is already stored
     *
     * @param stock the stock to add
     * @return true if added, false if the ticker is a duplicate
     * @throws IllegalArgumentException if stock is null
     */
    public boolean add(StockInfoClass stock)
    {
        if (stock == null)
        {
            throw new IllegalArgumentException("Cannot add a null stock");
        }
        if (stocks.contains(stock))
        {
            return false;
        }
        return stocks.addToEnd(stock);
    }

    /**
     * Removes a stock
     *
     * @param stock the stock to remove
     * @return true if it was removed
     */
    public boolean remove(StockInfoClass stock)
    {
        return stocks.remove(stock);
    }

    /**
     * Checks whether a stock is stored
     *
     * @param stock the stock to look for
     * @return true if found
     */
    public boolean contains(StockInfoClass stock)
    {
        return stocks.contains(stock);
    }

    /**
     * Counts how many times a stock is stored 
     *
     * @param stock the stock to count
     * @return the count
     */
    public int count(StockInfoClass stock)
    {
        return stocks.count(stock);
    }

    /**
     * Finds a stock by ticker, ignoring case and extra spaces
     *
     * @param ticker the ticker to look for
     * @return the stock, or null if not found
     */
    public StockInfoClass getByTicker(String ticker)
    {
        String key = StockInfoClass.normalizeTicker(ticker);
        if (key == null || key.isEmpty())
        {
            return null;
        }
        for (StockInfoClass stock : toArray())
        {
            if (stock.getTicker().equals(key))
            {
                return stock;
            }
        }
        return null;
    }

    /**
     * Gets all stocks in the order they were added
     * 
     * @return an array of the stocks
     */
    public StockInfoClass[] toArray()
    {
        return stocks.toArray(new StockInfoClass[stocks.size()]);
    }

    /**
     * Gets the number of stocks
     *
     * @return the size
     */
    public int size()
    {
        return stocks.size();
    }

    /**
     * Checks whether there are no stocks
     *
     * @return true if empty
     */
    public boolean isEmpty()
    {
        return stocks.isEmpty();
    }

    /**
     * Removes all stocks
     */
    public void clear()
    {
        stocks.clear();
    }

    /**
     * Lists the stocks, one per line
     *
     * @return the listing
     */
    @Override
    public String toString()
    {
        if (isEmpty())
        {
            return "(no stocks)";
        }
        StringBuilder sb = new StringBuilder();
        for (StockInfoClass stock : toArray())
        {
            sb.append(stock).append(System.lineSeparator());
        }
        return sb.toString();
    }
}
