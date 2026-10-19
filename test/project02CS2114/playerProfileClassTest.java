package project02CS2114;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
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


    // ---------------- getName ----------------
    /**
     * Good input for getName().
     */
    @Test
    public void testGetNameGood()
    {
        assertEquals("Sam", player.getName());
    }


    /**
     * The name is stored trimmed.
     */
    @Test
    public void testGetNameTrimmed()
    {
        assertEquals("Sam", new playerProfileClass("  Sam ", 500.0).getName());
    }


    /**
     * Null or blank names are rejected.
     */
    @Test
    public void testGetNameBad()
    {
        assertThrows(IllegalArgumentException.class,
            () -> new playerProfileClass(null, 500.0));
        assertThrows(IllegalArgumentException.class,
            () -> new playerProfileClass("", 500.0));
        assertThrows(IllegalArgumentException.class,
            () -> new playerProfileClass("   ", 500.0));
    }


    // ---------------- getTransactionHistory ----------------
    /**
     * Good input for getTransactionHistory().
     */
    @Test
    public void testGetTransactionHistoryGood()
    {
        assertNotNull(player.getTransactionHistory());
        assertTrue(player.getTransactionHistory().isEmpty());
    }


    /**
     * Each player gets their own history; it is not shared.
     */
    @Test
    public void testGetTransactionHistoryBad()
    {
        playerProfileClass other = new playerProfileClass("Alex", 500.0);
        assertTrue(
            player.getTransactionHistory() != other.getTransactionHistory());
    }


    // ---------------- getBalance / getStartingBalance ----------------
    /**
     * Good input for getBalance().
     */
    @Test
    public void testGetBalanceGood()
    {
        assertEquals(1000.0, player.getBalance(), DELTA);
    }


    /**
     * Zero, negative, and non-finite starting balances are rejected.
     */
    @Test
    public void testGetBalanceBad()
    {
        assertThrows(IllegalArgumentException.class,
            () -> new playerProfileClass("Broke", -50.0));
        assertThrows(IllegalArgumentException.class,
            () -> new playerProfileClass("Broke", 0.0));
        assertThrows(IllegalArgumentException.class,
            () -> new playerProfileClass("Broke", Double.NaN));
        assertThrows(IllegalArgumentException.class,
            () -> new playerProfileClass("Broke", Double.POSITIVE_INFINITY));
    }


    /**
     * The starting balance does not change when the balance does.
     */
    @Test
    public void testGetStartingBalance()
    {
        assertEquals(1000.0, player.getStartingBalance(), DELTA);
        player.setBalance(250.0);
        assertEquals(1000.0, player.getStartingBalance(), DELTA);
    }


    // ---------------- setBalance ----------------
    /**
     * Good input for setBalance().
     */
    @Test
    public void testSetBalanceGood()
    {
        player.setBalance(250.5);
        assertEquals(250.5, player.getBalance(), DELTA);
    }


    /**
     * A balance of exactly zero is allowed (all cash spent).
     */
    @Test
    public void testSetBalanceZero()
    {
        player.setBalance(0.0);
        assertEquals(0.0, player.getBalance(), DELTA);
    }


    /**
     * Bad input for setBalance().
     */
    @Test
    public void testSetBalanceBad()
    {
        assertThrows(
            IllegalArgumentException.class,
            () -> player.setBalance(-1.0));
        assertThrows(
            IllegalArgumentException.class,
            () -> player.setBalance(Double.NaN));
        assertEquals(1000.0, player.getBalance(), DELTA);
    }


    // ---------------- getHoldings / findHolding ----------------
    /**
     * Good input for getHoldings().
     */
    @Test
    public void testGetHoldingsGood()
    {
        assertNotNull(player.getHoldings());
        assertEquals(0, player.getHoldings().size());
    }


    /**
     * The returned list is the real one, so outside code can mutate it.
     */
    @Test
    public void testGetHoldingsBad()
    {
        player.getHoldings().add(new HoldingClass(stock, 2));
        assertEquals(1, player.getHoldings().size());
    }


    /**
     * findHolding ignores case and spacing.
     */
    @Test
    public void testFindHoldingGood()
    {
        HoldingClass h = new HoldingClass(stock, 2);
        player.getHoldings().add(h);
        assertSame(h, player.findHolding("AAPL"));
        assertSame(h, player.findHolding(" aapl "));
    }


    /**
     * findHolding returns null when the stock is not owned or the ticker
     * is null.
     */
    @Test
    public void testFindHoldingBad()
    {
        player.getHoldings().add(new HoldingClass(stock, 2));
        assertNull(player.findHolding("MSFT"));
        assertNull(player.findHolding(null));
        assertNull(player.findHolding(""));
    }


    // ---------------- getPortfolioValue ----------------
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


    /**
     * No holdings: value should be 0, not an exception.
     */
    @Test
    public void testGetPortfolioValueBad()
    {
        assertEquals(0.0, player.getPortfolioValue(), DELTA);
    }


    // ---------------- getTotalValue ----------------
    /**
     * Total value is cash plus stocks.
     */
    @Test
    public void testGetTotalValue()
    {
        player.setBalance(700.0);
        player.getHoldings().add(new HoldingClass(stock, 3));
        assertEquals(1000.0, player.getTotalValue(), DELTA);
    }


    /**
     * With no holdings, total value equals cash.
     */
    @Test
    public void testGetTotalValueNoHoldings()
    {
        assertEquals(1000.0, player.getTotalValue(), DELTA);
    }


    // ---------------- getProfitLoss ----------------
    /**
     * Spend 300 on 3 shares worth 100 each: no change in net worth.
     */
    @Test
    public void testGetProfitLossGood()
    {
        player.setBalance(700.0);
        player.getHoldings().add(new HoldingClass(stock, 3));
        assertEquals(0.0, player.getProfitLoss(), DELTA);
    }


    /**
     * Balance dropped with no holdings: a 400 loss.
     */
    @Test
    public void testGetProfitLossBad()
    {
        player.setBalance(600.0);
        assertEquals(-400.0, player.getProfitLoss(), DELTA);
    }


    /**
     * A price increase on held stock is a profit.
     */
    @Test
    public void testGetProfitLossPriceRise()
    {
        player.setBalance(700.0);
        player.getHoldings().add(new HoldingClass(stock, 3));
        stock.setSharePrice(200.0); // holdings now worth 600
        assertEquals(300.0, player.getProfitLoss(), DELTA);
    }
}