package project02CS2114;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


// -------------------------------------------------------------------------
/**
 * Tester for the StockTransactionsClass.
 * 
 *  @author mlkuhl
 *  @version Oct 12, 2026
 */
public class StockTransactionsClassTest
{
    private static final double DELTA = 0.001;
    private StockInfoClass stock;
    private playerProfileClass player;
    private StockTransactionsClass transaction;


    // Matches StockInfoClass(ticker, companyName, description, sharePrice).
    private StockInfoClass makeStock()
    {
        return new StockInfoClass("AAPL", "Apple", "Tech company", 100.0);
    }


    @BeforeEach
    public void setUp()
    {
        stock = makeStock();
        player = new playerProfileClass("Sam", 1000.0);
        transaction = new StockTransactionsClass(stock, 5, 100.0, "buy", 1);
    }


    // ---------------- buyStock ----------------
    @Test
    public void testBuyStockGood()
    {
        assertTrue(transaction.buyStock(player, stock, 5));
        assertEquals(500.0, player.getBalance(), DELTA);
        assertEquals(1, player.getHoldings().size());
        assertEquals(5, player.getHoldings().get(0).getShares());
    }


    @Test
    public void testBuyStockBad()
    {
        // 11 shares cost 1100, but the player only has 1000.
        assertFalse(transaction.buyStock(player, stock, 11));
        assertEquals(1000.0, player.getBalance(), DELTA);
        assertEquals(0, player.getHoldings().size());
    }


    // ---------------- sellStock ----------------
    @Test
    public void testSellStockGood()
    {
        transaction.buyStock(player, stock, 5);
        assertTrue(transaction.sellStock(player, stock, 3));
        assertEquals(800.0, player.getBalance(), DELTA);
        assertEquals(2, player.getHoldings().get(0).getShares());
    }


    @Test
    public void testSellStockBad()
    {
        // The player owns nothing, so the sale must fail with no side effects.
        assertFalse(transaction.sellStock(player, stock, 1));
        assertEquals(1000.0, player.getBalance(), DELTA);
    }


    // ---------------- getStock ----------------
    @Test
    public void testGetStockGood()
    {
        assertSame(stock, transaction.getStock());
    }


    @Test
    public void testGetStockBad()
    {
        StockTransactionsClass nullStock =
            new StockTransactionsClass(null, 1, 1.0, "buy", 1);
        assertNull(nullStock.getStock());
    }


    // ---------------- getShares ----------------
    @Test
    public void testGetSharesGood()
    {
        assertEquals(5, transaction.getShares());
    }


    @Test
    public void testGetSharesBad()
    {
        // Constructor does not validate, so negative shares are stored.
        StockTransactionsClass negative =
            new StockTransactionsClass(stock, -5, 100.0, "buy", 1);
        assertEquals(-5, negative.getShares());
    }


    // ---------------- getPrice ----------------
    @Test
    public void testGetPriceGood()
    {
        assertEquals(100.0, transaction.getPrice(), DELTA);
    }


    @Test
    public void testGetPriceBad()
    {
        StockTransactionsClass negative =
            new StockTransactionsClass(stock, 5, -100.0, "buy", 1);
        assertEquals(-100.0, negative.getPrice(), DELTA);
    }


    // ---------------- getType ----------------
    @Test
    public void testGetTypeGood()
    {
        assertEquals("buy", transaction.getType());
    }


    @Test
    public void testGetTypeBad()
    {
        StockTransactionsClass nullType =
            new StockTransactionsClass(stock, 5, 100.0, null, 1);
        assertNull(nullType.getType());
    }


    // ---------------- getDay ----------------
    @Test
    public void testGetDayGood()
    {
        assertEquals(1, transaction.getDay());
    }


    @Test
    public void testGetDayBad()
    {
        StockTransactionsClass negative =
            new StockTransactionsClass(stock, 5, 100.0, "buy", -1);
        assertEquals(-1, negative.getDay());
    }
}