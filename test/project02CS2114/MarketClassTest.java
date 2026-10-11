package project02CS2114;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for MarketClass. A fixed seed makes price changes repeatable
 *
 * @author Corbin Segna
 * @version Oct 11 2026
 */
public class MarketClassTest
{
    private MarketClass market;

    /**
     * Sets up a 5-day market with a fixed seed
     */
    @BeforeEach
    public void setUp()
    {
        market = new MarketClass(5, 42L);
    }

    /**
     * Tests both constructors start on day 1 with 8 stocks
     */
    @Test
    public void testConstructorNormal()
    {
        assertEquals(1, market.getCurrentDay());
        assertEquals(5, market.getTotalDays());
        assertEquals(8, market.getMarketSize());

        MarketClass standard = new MarketClass();
        assertEquals(MarketClass.DEFAULT_TOTAL_DAYS,
            standard.getTotalDays());
        assertEquals(8, standard.getMarketSize());
    }

    /**
     * Tests a game with 0 or negative days throws
     */
    @Test
    public void testConstructorBadInput()
    {
        assertThrows(IllegalArgumentException.class,
            () -> new MarketClass(0, 1L));
        assertThrows(IllegalArgumentException.class,
            () -> new MarketClass(-3, 1L));
    }

    /**
     * Tests "AAPL" returns the Apple stock
     */
    @Test
    public void testGetStockNormal()
    {
        StockInfoClass s = market.getStock("AAPL");
        assertNotNull(s);
        assertEquals("Apple Inc.", s.getCompanyName());
    }

    /**
     * Tests mixed-case tickers like "Aapl" still find AAPL
     */
    @Test
    public void testGetStockMixedCase()
    {
        assertEquals("AAPL", market.getStock("Aapl").getTicker());
        assertEquals("AAPL", market.getStock("  aapl  ").getTicker());
    }

    /**
     * Tests unknown, null, and blank tickers return null
     */
    @Test
    public void testGetStockUnknown()
    {
        assertNull(market.getStock("ZZZZ"));
        assertNull(market.getStock(null));
        assertNull(market.getStock(""));
    }

    /**
     * Tests a listed ticker is found
     */
    @Test
    public void testHasStockNormal()
    {
        assertTrue(market.hasStock("nvda"));
    }

    /**
     * Tests unlisted, null, and blank tickers are not found
     */
    @Test
    public void testHasStockBadInput()
    {
        assertFalse(market.hasStock("XYZB"));
        assertFalse(market.hasStock(null));
        assertFalse(market.hasStock(" "));
    }

    /**
     * Tests every stock is returned, starting with AAPL
     */
    @Test
    public void testGetAllStocksNormal()
    {
        StockInfoClass[] all = market.getAllStocks();
        assertEquals(market.getMarketSize(), all.length);
        assertEquals("AAPL", all[0].getTicker());
    }

    /**
     * Tests changing the returned array doesn't change the market
     */
    @Test
    public void testGetAllStocksIsCopy()
    {
        StockInfoClass[] all = market.getAllStocks();
        all[0] = null;
        assertNotNull(market.getAllStocks()[0]);
        assertEquals(8, market.getMarketSize());
    }

    /**
     * Tests days remaining counts down
     */
    @Test
    public void testDaysRemaining()
    {
        assertEquals(4, market.getDaysRemaining());
        market.recordTrade();
        market.advanceDay();
        assertEquals(3, market.getDaysRemaining());
    }

    /**
     * Tests the game is over only on the last day
     */
    @Test
    public void testIsGameOver()
    {
        assertFalse(market.isGameOver());
        for (int i = 0; i < 4; i++)
        {
            market.recordTrade();
            assertTrue(market.advanceDay());
        }
        assertEquals(5, market.getCurrentDay());
        assertTrue(market.isGameOver());
        assertEquals(0, market.getDaysRemaining());
    }

    /**
     * Tests prices change by at most 5% and are rounded to the cent
     */
    @Test
    public void testUpdatePricesNormal()
    {
        StockInfoClass[] before = market.getAllStocks();
        double[] oldPrices = new double[before.length];
        for (int i = 0; i < before.length; i++)
        {
            oldPrices[i] = before[i].getSharePrice();
        }
        market.updatePrices();
        boolean anyChanged = false;
        StockInfoClass[] after = market.getAllStocks();
        for (int i = 0; i < after.length; i++)
        {
            double p = after[i].getSharePrice();
            assertTrue(Math.abs(p - oldPrices[i])
                <= oldPrices[i] * MarketClass.MAX_DAILY_CHANGE + 0.01);
            assertEquals(Math.round(p * 100) / 100.0, p, 1e-9);
            if (p != oldPrices[i])
            {
                anyChanged = true;
            }
        }
        assertTrue(anyChanged);
    }

    /**
     * Tests prices never fall below the minimum after many days
     */
    @Test
    public void testUpdatePricesStaysPositive()
    {
        for (int i = 0; i < 2000; i++)
        {
            market.updatePrices();
        }
        for (StockInfoClass s : market.getAllStocks())
        {
            assertTrue(s.getSharePrice() >= MarketClass.MIN_PRICE);
        }
    }

    /**
     * Tests advancing moves to day 2 and changes prices
     */
    @Test
    public void testAdvanceDayNormal()
    {
        double oldApple = market.getStock("AAPL").getSharePrice();
        double oldMsft = market.getStock("MSFT").getSharePrice();
        assertTrue(market.advanceDay());
        assertEquals(2, market.getCurrentDay());
        assertTrue(oldApple != market.getStock("AAPL").getSharePrice()
            || oldMsft != market.getStock("MSFT").getSharePrice());
    }

    /**
     * Tests two days without trading blocks advancing until a trade
     */
    @Test
    public void testAdvanceDayIdleRule()
    {
        assertFalse(market.mustTradeBeforeAdvancing());
        assertTrue(market.advanceDay());
        assertTrue(market.mustTradeBeforeAdvancing());
        assertFalse(market.advanceDay());
        assertEquals(2, market.getCurrentDay());

        market.recordTrade();
        assertFalse(market.mustTradeBeforeAdvancing());
        assertTrue(market.advanceDay());
        assertEquals(3, market.getCurrentDay());
    }

    /**
     * Tests advancing past the last day is refused
     */
    @Test
    public void testAdvanceDayPastEnd()
    {
        MarketClass oneDay = new MarketClass(1, 7L);
        assertTrue(oneDay.isGameOver());
        assertFalse(oneDay.advanceDay());
        assertEquals(1, oneDay.getCurrentDay());
    }

    /**
     * Tests trading every day never blocks advancing
     */
    @Test
    public void testRecordTradeNormal()
    {
        for (int i = 0; i < 4; i++)
        {
            market.recordTrade();
            assertFalse(market.mustTradeBeforeAdvancing());
            assertTrue(market.advanceDay());
        }
    }

    /**
     * Tests recording two trades in one day is harmless
     */
    @Test
    public void testRecordTradeTwice()
    {
        market.recordTrade();
        market.recordTrade();
        assertTrue(market.advanceDay());
        assertEquals(2, market.getCurrentDay());
    }

    /**
     * Tests the listing includes every ticker
     */
    @Test
    public void testToString()
    {
        String text = market.toString();
        for (StockInfoClass s : market.getAllStocks())
        {
            assertTrue(text.contains(s.getTicker()));
        }
    }
}