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
     *            the name for the player
     * @param startingBalance
     *            the starting balance for the player.
     */
    public playerProfileClass(String name, double startingBalance)
    {
        this.playerName = name;
        this.balance = startingBalance;
        transactionHistory = new playerTransactionHistoryChain();
        this.startingBalance = startingBalance;
        this.holdings = new ArrayList<HoldingClass>();
    }


    // ----------------------------------------------------------
    /**
     * Getter for the players name.
     * 
     * @return the players name,
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
     * @return the players current balance.
     */
    public double getBalance()
    {
        return this.balance;
    }


    // ----------------------------------------------------------
    /**
     * Updates the players balance
     * 
     * @param newBalance
     *            the new balance to update balance to
     */
    public void setBalance(double newBalance)
    {
        if (newBalance < 0)
        {
            throw new IllegalArgumentException(
                "Cannot enter a new balance that is negative!");
        }
        this.balance = newBalance;
    }


    // ----------------------------------------------------------
    /**
     * Returns the holding array of the players current holdings.
     * 
     * @return the array of the players current holdings.
     */
    public ArrayList<HoldingClass> getHoldings()
    {
        return this.holdings;
    }


    // ----------------------------------------------------------
    /**
     * Calculates the sum of each individual stock holding value, then returns
     * it.
     * 
     * @return the sum.
     */
    public double getPortfolioValue()
    {
        double sumStockVals = 0;

        for (HoldingClass h : holdings)
        {
            sumStockVals += (h.getShares() * h.getStock().getSharePrice());
        }

        return sumStockVals;
    }


    // ----------------------------------------------------------
    /**
     * Calculates the players overall profit/loss relative to their starting
     * balance.
     * 
     * @return the profit/loss you currently have.
     */
    public double getProfitLoss()
    {
        double portvalue = getPortfolioValue();
        return (portvalue + balance - startingBalance);
    }

}
