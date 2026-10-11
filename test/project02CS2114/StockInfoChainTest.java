package project02CS2114;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for StockInfoChain.
 *
 * @author Corbin Segna
 * @version Oct 11 2026
 */
public class StockInfoChainTest
{
    private StockInfoChain chain;
    private StockInfoClass apple;
    private StockInfoClass nvidia;
    private StockInfoClass xyzb;

    /**
     * Sets up an empty chain and sample stocks
     */
    @BeforeEach
    public void setUp()
    {
        chain = new StockInfoChain();
        apple = new StockInfoClass("AAPL", "Apple Inc.", "", 190.0);
        nvidia = new StockInfoClass("NVDA", "NVIDIA Corp.", "", 120.0);
        xyzb = new StockInfoClass("XYZB", "Not Listed Co.", "", 5.0);
    }

    /**
     * Tests every method on an empty chain
     */
    @Test
    public void testEmptyChain()
    {
        assertTrue(chain.isEmpty());
        assertEquals(0, chain.size());
        assertEquals(0, chain.toArray().length);
        assertNull(chain.getByTicker("AAPL"));
        assertFalse(chain.contains(apple));
        assertEquals(0, chain.count(apple));
        assertFalse(chain.remove(apple));
        assertEquals("(no stocks)", chain.toString());
    }

    /**
     * Tests adding a stock
     */
    @Test
    public void testAddNormal()
    {
        assertTrue(chain.add(nvidia));
        assertEquals(1, chain.size());
        assertTrue(chain.contains(nvidia));
    }

    /**
     * Tests adding null and a duplicate ticker
     */
    @Test
    public void testAddBadInput()
    {
        assertThrows(IllegalArgumentException.class, () -> chain.add(null));
        assertTrue(chain.add(apple));
        StockInfoClass appleAgain =
            new StockInfoClass("aapl", "Apple copy", "", 1.0);
        assertFalse(chain.add(appleAgain));
        assertEquals(1, chain.size());
    }

    /**
     * Tests removing a stock
     */
    @Test
    public void testRemoveNormal()
    {
        chain.add(apple);
        chain.add(nvidia);
        assertTrue(chain.remove(nvidia));
        assertEquals(1, chain.size());
        assertFalse(chain.contains(nvidia));
        assertTrue(chain.contains(apple));
    }

    /**
     * Tests removing a stock that isn't there, and null
     */
    @Test
    public void testRemoveNotThere()
    {
        chain.add(apple);
        assertFalse(chain.remove(xyzb));
        assertFalse(chain.remove(null));
        assertEquals(1, chain.size());
    }

    /**
     * Tests contains with a matching ticker
     */
    @Test
    public void testContainsNormal()
    {
        chain.add(apple);
        assertTrue(chain.contains(
            new StockInfoClass("AAPL", "Apple", "", 1.0)));
    }

    /**
     * Tests contains with null and a missing stock
     */
    @Test
    public void testContainsBadInput()
    {
        chain.add(apple);
        assertFalse(chain.contains(null));
        assertFalse(chain.contains(xyzb));
    }

    /**
     * Tests count; a duplicate add is refused so the count stays 1
     */
    @Test
    public void testCountNormal()
    {
        chain.add(apple);
        chain.add(nvidia);
        chain.add(new StockInfoClass("AAPL", "dup", "", 1.0));
        assertEquals(1, chain.count(apple));
    }

    /**
     * Tests count with null and a missing stock
     */
    @Test
    public void testCountBadInput()
    {
        chain.add(apple);
        assertEquals(0, chain.count(null));
        assertEquals(0, chain.count(xyzb));
    }

    /**
     * Tests finding stocks by ticker, including mixed case
     */
    @Test
    public void testGetByTickerNormal()
    {
        chain.add(apple);
        chain.add(nvidia);
        assertSame(nvidia, chain.getByTicker("NVDA"));
        assertSame(apple, chain.getByTicker(" aApl "));
    }

    /**
     * Tests finding with null, blank, and unknown tickers
     */
    @Test
    public void testGetByTickerBadInput()
    {
        chain.add(apple);
        assertNull(chain.getByTicker(null));
        assertNull(chain.getByTicker("   "));
        assertNull(chain.getByTicker("ZZZZ"));
    }

    /**
     * Tests that toArray keeps the order stocks were added
     */
    @Test
    public void testToArrayOrder()
    {
        chain.add(apple);
        chain.add(nvidia);
        assertArrayEquals(new StockInfoClass[] { apple, nvidia },
            chain.toArray());
    }

    /**
     * Tests that changing the returned array doesn't change the chain
     */
    @Test
    public void testToArrayIsCopy()
    {
        chain.add(apple);
        StockInfoClass[] copy = chain.toArray();
        copy[0] = null;
        assertSame(apple, chain.toArray()[0]);
    }

    /**
     * Tests size and isEmpty after adding
     */
    @Test
    public void testSizeAndIsEmpty()
    {
        chain.add(apple);
        chain.add(nvidia);
        assertEquals(2, chain.size());
        assertFalse(chain.isEmpty());
    }

    /**
     * Tests clearing a chain with stocks
     */
    @Test
    public void testClearNormal()
    {
        chain.add(apple);
        chain.add(nvidia);
        chain.clear();
        assertTrue(chain.isEmpty());
        assertEquals(0, chain.size());
    }

    /**
     * Tests clearing an already empty chain
     */
    @Test
    public void testClearEmpty()
    {
        chain.clear();
        assertTrue(chain.isEmpty());
    }

    /**
     * Tests the listing shows each stock in order
     */
    @Test
    public void testToString()
    {
        chain.add(apple);
        chain.add(nvidia);
        String text = chain.toString();
        assertTrue(text.contains("AAPL"));
        assertTrue(text.contains("NVDA"));
        assertTrue(text.indexOf("AAPL") < text.indexOf("NVDA"));
    }
}
