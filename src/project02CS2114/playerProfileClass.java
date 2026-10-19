package project02CS2114;

import java.util.ArrayList;

// -------------------------------------------------------------------------
/**
 * Stores data for the players profile.
 *
 * @author mlkuhl
 * @version Oct 11, 2026
 */
public class playerProfileClass
{
    private String playerName;
    private playerTransactionHistoryChain transactionHistory;
    private double balance;
    private double startingBalance;
    private ArrayList<HoldingClass> holdings;

    // ----------------------------------------------------------
    /**
     * Create a new playerProfileClass object.
     *
     * @param name
     *            the name for the player; must not be blank
     * @param startingBalance
     *            the starting balance for the player; must be positive
     * @throws IllegalArgumentException
     *             if name is blank or startingBalance is not positive
     */
    public playerProfileClass(String name, double startingBalance)
    {
        if (name == null || name.trim().isEmpty())
        {
            throw new IllegalArgumentException("Name must not be blank");
        }
        if (!(startingBalance > 0) || Double.isInfinite(startingBalance))
        {
            throw new IllegalArgumentException(
                "Starting balance must be a positive number");
        }
        this.playerName = name.trim();
        this.balance = startingBalance;
        this.startingBalance = startingBalance;
        this.transactionHistory = new playerTransactionHistoryChain();
        this.holdings = new ArrayList<HoldingClass>();
    }


    // ----------------------------------------------------------
    /**
     * Getter for the players name.
     *
     * @return the players name.
     */
    public String getName()
    {
        return this.playerName;
    }


    // ----------------------------------------------------------
    /**
     * Returns the transaction history.
     *
     * @return transaction history chain.
     */
    public playerTransactionHistoryChain getTransactionHistory()
    {
        return this.transactionHistory;
    }


    // ----------------------------------------------------------
    /**
     * Getter for the players balance.
     *
     * @return the players current cash balance.
     */
    public double getBalance()
    {
        return this.balance;
    }


    // ----------------------------------------------------------
    /**
     * Gets the balance the player started with.
     *
     * @return the starting balance.
     */
    public double getStartingBalance()
    {
        return this.startingBalance;
    }


    // ----------------------------------------------------------
    /**
     * Updates the players balance
     *
     * @param newBalance
     *            the new balance to update balance to
     * @throws IllegalArgumentException
     *             if newBalance is negative or not a number
     */
    public void setBalance(double newBalance)
    {
        if (!(newBalance >= 0))
        {
            throw new IllegalArgumentException(
                "Cannot enter a new balance that is negative!");
        }
        this.balance = newBalance;
    }


    // ----------------------------------------------------------
    /**
     * Returns the list of the players current holdings.
     *
     * @return the list of the players current holdings.
     */
    public ArrayList<HoldingClass> getHoldings()
    {
        return this.holdings;
    }


    // ----------------------------------------------------------
    /**
     * Finds the holding for a ticker.
     *
     * @param ticker
     *            the ticker to look for (case and spacing ignored)
     * @return the holding, or null if the player does not own that stock
     */
    public HoldingClass findHolding(String ticker)
    {
        String key = StockInfoClass.normalizeTicker(ticker);
        if (key == null)
        {
            return null;
        }
        for (HoldingClass h : holdings)
        {
            if (h.getStock().getTicker().equals(key))
            {
                return h;
            }
        }
        return null;
    }


    // ----------------------------------------------------------
    /**
     * Calculates the sum of each individual stock holding value, not
     * counting cash.
     *
     * @return the value of the stocks owned.
     */
    public double getPortfolioValue()
    {
        double sumStockVals = 0;
        for (HoldingClass h : holdings)
        {
            sumStockVals += h.getCurrentValue();
        }
        return sumStockVals;
    }


    // ----------------------------------------------------------
    /**
     * Calculates cash plus the value of all stocks owned.
     *
     * @return the total value of the player's account.
     */
    public double getTotalValue()
    {
        return balance + getPortfolioValue();
    }


    // ----------------------------------------------------------
    /**
     * Calculates the players overall profit/loss relative to their starting
     * balance.
     *
     * @return the profit (positive) or loss (negative).
     */
    public double getProfitLoss()
    {
        return getTotalValue() - startingBalance;
    }
}