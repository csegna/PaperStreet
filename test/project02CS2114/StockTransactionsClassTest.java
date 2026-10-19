package project02CS2114;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

// -------------------------------------------------------------------------
/**
 * Tester for the StockTransactionsClass.
 *
 * @author mlkuhl
 * @version Oct 12, 2026
 */
public class StockTransactionsClassTest
{
    private static final double DELTA = 0.001;
    private StockInfoClass stock;
    private playerProfileClass player;
    private StockTransactionsClass transaction;


    @BeforeEach
    public void setUp()
    {
        stock = new StockInfoClass("AAPL", "Apple", "Tech company", 100.0);
        player = new playerProfileClass("Sam", 1000.0);
        transaction = new StockTransactionsClass(
            stock, 5, 100.0, StockTransactionsClass.BUY, 1);
    }


    // ---------------- constructor ----------------
    @Test
    public void testConstructorNullStock()
    {
        assertThrows(IllegalArgumentException.class,
            () -> new StockTransactionsClass(
                null, 1, 1.0, StockTransactionsClass.BUY, 1));
    }


    @Test
    public void testConstructorBadShares()
    {
        assertThrows(IllegalArgumentException.class,
            () -> new StockTransactionsClass(
                stock, 0, 100.0, StockTransactionsClass.BUY, 1));
        assertThrows(IllegalArgumentException.class,
            () -> new StockTransactionsClass(
                stock, -5, 100.0, StockTransactionsClass.BUY, 1));
    }


    @Test
    public void testConstructorBadPrice()
    {
        assertThrows(IllegalArgumentException.class,
            () -> new StockTransactionsClass(
                stock, 5, 0.0, StockTransactionsClass.BUY, 1));
        assertThrows(IllegalArgumentException.class,
            () -> new StockTransactionsClass(
                stock, 5, -100.0, StockTransactionsClass.BUY, 1));
    }


    @Test
    public void testConstructorBadType()
    {
        assertThrows(IllegalArgumentException.class,
            () -> new StockTransactionsClass(stock, 5, 100.0, null, 1));
        assertThrows(IllegalArgumentException.class,
            () -> new StockTransactionsClass(stock, 5, 100.0, "buy", 1));
    }


    @Test
    public void testConstructorBadDay()
    {
        assertThrows(IllegalArgumentException.class,
            () -> new StockTransactionsClass(
                stock, 5, 100.0, StockTransactionsClass.BUY, 0));
    }


    // ---------------- buyStock ----------------
    @Test
    public void testBuyStockGood()
    {
        assertTrue(StockTransactionsClass.buyStock(player, stock, 5, 1));
        assertEquals(500.0, player.getBalance(), DELTA);
        assertEquals(1, player.getHoldings().size());
        assertEquals(5, player.getHoldings().get(0).getShares());
        assertEquals(100.0,
            player.getHoldings().get(0).getAveragePurchasePrice(), DELTA);
    }


    @Test
    public void testBuyStockRecordsTransaction()
    {
        StockTransactionsClass.buyStock(player, stock, 5, 3);
        assertEquals(1, player.getTransactionHistory().size());
        StockTransactionsClass recorded =
            player.getTransactionHistory().get(0);
        assertEquals(StockTransactionsClass.BUY, recorded.getType());
        assertEquals(5, recorded.getShares());
        assertEquals(100.0, recorded.getPrice(), DELTA);
        assertEquals(3, recorded.getDay());
    }


    @Test
    public void testBuyStockBad()
    {
        // 11 shares cost 1100, but the player only has 1000.
        assertFalse(StockTransactionsClass.buyStock(player, stock, 11, 1));
        assertEquals(1000.0, player.getBalance(), DELTA);
        assertEquals(0, player.getHoldings().size());
        assertTrue(player.getTransactionHistory().isEmpty());
    }


    @Test
    public void testBuyStockInvalidArguments()
    {
        assertFalse(StockTransactionsClass.buyStock(player, stock, 0, 1));
        assertFalse(StockTransactionsClass.buyStock(player, stock, -1, 1));
        assertFalse(StockTransactionsClass.buyStock(player, stock, 1, 0));
        assertFalse(StockTransactionsClass.buyStock(null, stock, 1, 1));
        assertFalse(StockTransactionsClass.buyStock(player, null, 1, 1));
        assertEquals(1000.0, player.getBalance(), DELTA);
    }


    @Test
    public void testBuyStockAveragePrice()
    {
        playerProfileClass rich = new playerProfileClass("Rich", 2000.0);
        StockTransactionsClass.buyStock(rich, stock, 5, 1); // 5 @ 100
        stock.setSharePrice(200.0);
        StockTransactionsClass.buyStock(rich, stock, 5, 2); // 5 @ 200
        assertEquals(1, rich.getHoldings().size());
        assertEquals(10, rich.getHoldings().get(0).getShares());
        assertEquals(150.0,
            rich.getHoldings().get(0).getAveragePurchasePrice(), DELTA);
        assertEquals(500.0, rich.getBalance(), DELTA);
    }


    // ---------------- sellStock ----------------
    @Test
    public void testSellStockGood()
    {
        StockTransactionsClass.buyStock(player, stock, 5, 1);
        assertTrue(StockTransactionsClass.sellStock(player, stock, 3, 1));
        assertEquals(800.0, player.getBalance(), DELTA);
        assertEquals(2, player.getHoldings().get(0).getShares());
        assertEquals(2, player.getTransactionHistory().size());
        assertEquals(StockTransactionsClass.SELL,
            player.getTransactionHistory().get(1).getType());
    }


    @Test
    public void testSellStockAllRemovesHolding()
    {
        StockTransactionsClass.buyStock(player, stock, 5, 1);
        assertTrue(StockTransactionsClass.sellStock(player, stock, 5, 1));
        assertEquals(1000.0, player.getBalance(), DELTA);
        assertEquals(0, player.getHoldings().size());
    }


    @Test
    public void testSellStockBad()
    {
        // The player owns nothing, so the sale must fail with no side effects.
        assertFalse(StockTransactionsClass.sellStock(player, stock, 1, 1));
        assertEquals(1000.0, player.getBalance(), DELTA);
        assertTrue(player.getTransactionHistory().isEmpty());
    }


    @Test
    public void testSellStockTooMany()
    {
        StockTransactionsClass.buyStock(player, stock, 5, 1);
        assertFalse(StockTransactionsClass.sellStock(player, stock, 6, 1));
        assertEquals(5, player.getHoldings().get(0).getShares());
        assertEquals(500.0, player.getBalance(), DELTA);
        assertEquals(1, player.getTransactionHistory().size());
    }


    @Test
    public void testSellStockInvalidArguments()
    {
        StockTransactionsClass.buyStock(player, stock, 5, 1);
        assertFalse(StockTransactionsClass.sellStock(player, stock, 0, 1));
        assertFalse(StockTransactionsClass.sellStock(player, stock, 1, 0));
        assertFalse(StockTransactionsClass.sellStock(null, stock, 1, 1));
        assertFalse(StockTransactionsClass.sellStock(player, null, 1, 1));
        assertEquals(5, player.getHoldings().get(0).getShares());
    }


    @Test
    public void testSellDoesNotChangeAveragePrice()
    {
        StockTransactionsClass.buyStock(player, stock, 5, 1);
        stock.setSharePrice(150.0);
        StockTransactionsClass.sellStock(player, stock, 2, 2);
        assertEquals(100.0,
            player.getHoldings().get(0).getAveragePurchasePrice(), DELTA);
    }


    // ---------------- getStock ----------------
    @Test
    public void testGetStockGood()
    {
        assertSame(stock, transaction.getStock());
    }


    // ---------------- getShares ----------------
    @Test
    public void testGetSharesGood()
    {
        assertEquals(5, transaction.getShares());
    }


    // ---------------- getPrice ----------------
    @Test
    public void testGetPriceGood()
    {
        assertEquals(100.0, transaction.getPrice(), DELTA);
    }


    // ---------------- getType ----------------
    @Test
    public void testGetTypeGood()
    {
        assertEquals(StockTransactionsClass.BUY, transaction.getType());
    }


    // ---------------- getDay ----------------
    @Test
    public void testGetDayGood()
    {
        assertEquals(1, transaction.getDay());
    }
}