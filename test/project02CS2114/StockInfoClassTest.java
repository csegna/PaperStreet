package project02CS2114;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for StockInfoClass: one normal case and one bad-input case per
 * public method
 *
 * @author Corbin Segna
 * @version October 2026
 */
public class StockInfoClassTest
{
    private StockInfoClass apple;

    /**
     * Creates a fresh Apple stock before each test.
     */
    @BeforeEach
    public void setUp()
    {
        apple = new StockInfoClass("AAPL", "Apple Inc.", "Makes iPhones.",
            190.00);
    }

    /** 
     * Normal: fields are stored, ticker is normalized. 
     * 
     * 
     */
    @Test
    public void testConstructorNormal()
    {
        StockInfoClass s =
            new StockInfoClass("  nvda ", "NVIDIA Corp.", "Chips.", 120.0);
        assertEquals("NVDA", s.getTicker());
        assertEquals("NVIDIA Corp.", s.getCompanyName());
        assertEquals("Chips.", s.getDescription());
        assertEquals(120.0, s.getSharePrice(), 0.001);
    }


    /** 
     * Bad input: null/blank ticker, blank name, bad prices all throw. 
     * 
     */
    @Test
    public void testConstructorBadInput()
    {
        assertThrows(IllegalArgumentException.class,
            () -> new StockInfoClass(null, "Apple", "", 10.0));
        assertThrows(IllegalArgumentException.class,
            () -> new StockInfoClass("   ", "Apple", "", 10.0));
        assertThrows(IllegalArgumentException.class,
            () -> new StockInfoClass("AAPL", "", "", 10.0));
        assertThrows(IllegalArgumentException.class,
            () -> new StockInfoClass("AAPL", "Apple", "", -1.0));
        assertThrows(IllegalArgumentException.class,
            () -> new StockInfoClass("AAPL", "Apple", "", 0.0));
        assertThrows(IllegalArgumentException.class,
            () -> new StockInfoClass("AAPL", "Apple", "", Double.NaN));
    }

    /** 
     * Normal: getters return what the constructor stored.
     * 
     */
    @Test
    public void testGettersNormal()
    {
        assertEquals("AAPL", apple.getTicker());
        assertEquals("Apple Inc.", apple.getCompanyName());
        assertEquals("Makes iPhones.", apple.getDescription());
        assertEquals(190.00, apple.getSharePrice(), 0.001);
    }


    /** 
     * 
     * Bad input: a null description is stored as "" rather than null.
     * 
     */
    @Test
    public void testGetDescriptionNullGiven()
    {
        StockInfoClass s = new StockInfoClass("KO", "Coca-Cola", null, 62.0);
        assertEquals("", s.getDescription());
    }


    /**
     *  Normal (spec test plan): 155.25 to getter returns 155.25.
     *  
     */
    @Test
    public void testSetSharePriceNormal()
    {
        apple.setSharePrice(155.25);
        assertEquals(155.25, apple.getSharePrice(), 0.001);
    }


    /** 
     * Bad input : -1 throws and the price is unchanged. 
     * 
     */
    @Test
    public void testSetSharePriceNegative()
    {
        assertThrows(IllegalArgumentException.class,
            () -> apple.setSharePrice(-1));
        assertEquals(190.00, apple.getSharePrice(), 0.001);
        assertThrows(IllegalArgumentException.class,
            () -> apple.setSharePrice(0));
        assertThrows(IllegalArgumentException.class,
            () -> apple.setSharePrice(Double.POSITIVE_INFINITY));
        assertEquals(190.00, apple.getSharePrice(), 0.001);
    }


    /** 
     * Normal: mixed case and spaces become upper case trimmed. 
     * 
     */
    @Test
    public void testNormalizeTickerNormal()
    {
        assertEquals("AAPL", StockInfoClass.normalizeTicker(" aApL "));
    }


    /**
     *  Bad input: null stays null, blank becomes empty.
     *  
     */
    @Test
    public void testNormalizeTickerBadInput()
    {
        assertNull(StockInfoClass.normalizeTicker(null));
        assertEquals("", StockInfoClass.normalizeTicker("   "));
    }

    /** 
     * Normal: same ticker means equal, even if price differs
     * 
     */
    @Test
    public void testEqualsSameTicker()
    {
        StockInfoClass other =
            new StockInfoClass("aapl", "Apple", "different", 1.0);
        assertEquals(apple, other);
        assertEquals(apple.hashCode(), other.hashCode());
        assertEquals(apple, apple);
    }


    /**
     *  Bad input: null, other types and other tickers are not equal
     *  
     */
    @Test
    public void testEqualsBadInput()
    {
        assertNotEquals(apple, null);
        assertNotEquals(apple, "AAPL");
        assertNotEquals(apple,
            new StockInfoClass("MSFT", "Microsoft", "", 420.0));
        assertFalse(apple.equals(null));
    }

    /** Normal:
     *  listing line shows ticker, name and price
     */
    @Test
    public void testToString()
    {
        String line = apple.toString();
        assertTrue(line.startsWith("AAPL"));
        assertTrue(line.contains("Apple Inc."));
        assertTrue(line.contains("$"));
        assertTrue(line.contains("190.00"));
    }
}