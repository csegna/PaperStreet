package project02CS2114;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

// -------------------------------------------------------------------------
/**
 * Tester for the playerProfileClass.
 * 
 * @author mlkuhl
 * @version Oct 12, 2026
 */
public class playerProfileClassTest
{
    private static final double DELTA = 0.001;
    private playerProfileClass player;
    private StockInfoClass stock;

    // Matches StockInfoClass(ticker, companyName, description, sharePrice).
    private StockInfoClass makeStock()
    {
        return new StockInfoClass("AAPL", "Apple", "Tech company", 100.0);
    }


    // ----------------------------------------------------------
    /**
     * Sets up the JUnit.
     */
    @BeforeEach
    public void setUp()
    {
        player = new playerProfileClass("Sam", 1000.0);
        stock = makeStock();
    }


    // ----------------------------------------------------------
    /**
     * Good input for getName().
     */
    @Test
    public void testGetNameGood()
    {
        assertEquals("Sam", player.getName());
    }


    // ----------------------------------------------------------
    /**
     * Bad input for getName().
     */
    @Test
    public void testGetNameBad()
    {
        // Constructor does not validate, so a null name is stored.
        playerProfileClass nullName = new playerProfileClass(null, 500.0);
        assertNull(nullName.getName());
    }


    // ----------------------------------------------------------
    /**
     * Good input for getTransactionHistory().
     */
    @Test
    public void testGetTransactionHistoryGood()
    {
        assertNotNull(player.getTransactionHistory());
    }


    // ----------------------------------------------------------
    /**
     * Bad input for getTransactionHistory.
     */
    @Test
    public void testGetTransactionHistoryBad()
    {
        // Each player gets their own history; it is not shared.
        playerProfileClass other = new playerProfileClass("Alex", 0.0);
        assertTrue(
            player.getTransactionHistory() != other.getTransactionHistory());
    }


    // ----------------------------------------------------------
    /**
     * Good input for getBalance().
     */
    @Test
    public void testGetBalanceGood()
    {
        assertEquals(1000.0, player.getBalance(), DELTA);
    }


    // ----------------------------------------------------------
    /**
     * Bad input for getBalance().
     */
    @Test
    public void testGetBalanceBad()
    {
        // Constructor does not validate, so a negative starting balance is
        // stored.
        playerProfileClass broke = new playerProfileClass("Broke", -50.0);
        assertEquals(-50.0, broke.getBalance(), DELTA);
    }


    // ----------------------------------------------------------
    /**
     * Good input for setBalance().
     */
    @Test
    public void testSetBalanceGood()
    {
        player.setBalance(250.5);
        assertEquals(250.5, player.getBalance(), DELTA);
    }


    // ----------------------------------------------------------
    /**
     * Bad input for setBalance().
     */
    @Test
    public void testSetBalanceBad()
    {
        assertThrows(
            IllegalArgumentException.class,
            () -> player.setBalance(-1.0));
        assertEquals(1000.0, player.getBalance(), DELTA);
    }


    // ----------------------------------------------------------
    /**
     * Good input for getHoldings().
     */
    @Test
    public void testGetHoldingsGood()
    {
        // Fails until holdings is initialized in the constructor.
        assertNotNull(player.getHoldings());
        assertEquals(0, player.getHoldings().size());
    }


    // ----------------------------------------------------------
    /**
     * Bad input for getHoldings().
     */
    @Test
    public void testGetHoldingsBad()
    {
        // The returned list is the real one, so outside code can mutate it.
        player.getHoldings().add(new HoldingClass(stock, 2));
        assertEquals(1, player.getHoldings().size());
    }


    // ----------------------------------------------------------
    /**
     * Good input for getPortfolioValue().
     */
    @Test
    public void testGetPortfolioValueGood()
    {
        player.getHoldings().add(new HoldingClass(stock, 3));
        player.getHoldings().add(new HoldingClass(makeStock(), 2));
        assertEquals(500.0, player.getPortfolioValue(), DELTA);
    }


    // ----------------------------------------------------------
    /**
     * Bad input for getPortfolioValue().
     */
    @Test
    public void testGetPortfolioValueBad()
    {
        // No holdings: value should be 0, not an exception.
        assertEquals(0.0, player.getPortfolioValue(), DELTA);
    }


    // ----------------------------------------------------------
    /**
     * Good input for getProfitLoss().
     */
    @Test
    public void testGetProfitLossGood()
    {
        // Spend 300 on 3 shares worth 100 each: no change in net worth.
        player.setBalance(700.0);
        player.getHoldings().add(new HoldingClass(stock, 3));
        assertEquals(0.0, player.getProfitLoss(), DELTA);
    }


    // ----------------------------------------------------------
    /**
     * Bad input for getProfitLoss().
     */
    @Test
    public void testGetProfitLossBad()
    {
        // Balance dropped with no holdings: a 400 loss.
        player.setBalance(600.0);
        assertEquals(-400.0, player.getProfitLoss(), DELTA);
    }
}
