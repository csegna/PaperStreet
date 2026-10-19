package project02CS2114;

/**
 * Stores a player's completed transactions in the order they happened.
 *
 * @author (team)
 * @version Oct 2026
 */
public class playerTransactionHistoryChain
    extends LinkedChain<StockTransactionsClass>
{
    /**
     * Creates an empty history.
     */
    public playerTransactionHistoryChain()
    {
        super();
    }


    /**
     * Adds a transaction at the end, so the history stays oldest first.
     *
     * @param transaction
     *            the completed transaction
     * @throws IllegalArgumentException
     *             if transaction is null
     */
    public void record(StockTransactionsClass transaction)
    {
        addToEnd(transaction);
    }


    /**
     * Gets every transaction, oldest first.
     *
     * @return an array of the transactions
     */
    public StockTransactionsClass[] toArray()
    {
        return toArray(new StockTransactionsClass[size()]);
    }


    /**
     * Gets the transactions made on a given day.
     *
     * @param day
     *            the trading day
     * @return the transactions from that day, oldest first (empty if none)
     */
    public StockTransactionsClass[] getTransactionsOnDay(int day)
    {
        int count = 0;
        for (StockTransactionsClass t : toArray())
        {
            if (t.getDay() == day)
            {
                count++;
            }
        }
        StockTransactionsClass[] result = new StockTransactionsClass[count];
        int i = 0;
        for (StockTransactionsClass t : toArray())
        {
            if (t.getDay() == day)
            {
                result[i] = t;
                i++;
            }
        }
        return result;
    }


    /**
     * Lists the transactions, one per line.
     *
     * @return the listing
     */
    @Override
    public String toString()
    {
        if (isEmpty())
        {
            return "(no transactions)";
        }
        StringBuilder sb = new StringBuilder();
        for (StockTransactionsClass t : toArray())
        {
            sb.append(t).append(System.lineSeparator());
        }
        return sb.toString();
    }
}