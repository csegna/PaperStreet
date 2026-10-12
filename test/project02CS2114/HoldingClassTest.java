package project02CS2114;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


// -------------------------------------------------------------------------
/**
 * Tester for the HoldingClass.
 * 
 *  @author mlkuhl
 *  @version Oct 12, 2026
 */
public class HoldingClassTest
{
    private static final double DELTA = 0.001;
    private StockInfoClass stock;
    private HoldingClass holding;
 
 
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
        stock = makeStock();
        holding = new HoldingClass(stock, 10);
    }
 
 
    
    // ----------------------------------------------------------
    /**
     * Good input for getStock()
     */
    @Test
    public void testGetStockGood()
    {
        assertSame(stock, holding.getStock());
    }
 
 
    // ----------------------------------------------------------
    /**
     * Bad input for getStock().
     */
    @Test
    public void testGetStockBad()
    {
        // A null stock is accepted by the constructor and returned as-is.
        HoldingClass nullHolding = new HoldingClass(null, 5);
        assertNull(nullHolding.getStock());
    }
 
 
    
    // ----------------------------------------------------------
    /**
     * Good input for getShares()
     */
    @Test
    public void testGetSharesGood()
    {
        assertEquals(10, holding.getShares());
    }
 
 
    // ----------------------------------------------------------
    /**
     * Bad inout for getShares()
     */
    @Test
    public void testGetSharesBad()
    {
        // The constructor does not validate, so negative shares are stored.
        HoldingClass negative = new HoldingClass(stock, -3);
        assertEquals(-3, negative.getShares());
    }
 
 
    // ----------------------------------------------------------
    /**
     * Good inout for setShares().
     */
    @Test
    public void testSetSharesGood()
    {
        holding.setShares(25);
        assertEquals(25, holding.getShares());
    }
 
 
    // ----------------------------------------------------------
    /**
     * Bad inout for setShares().
     */
    @Test
    public void testSetSharesBad()
    {
        assertThrows(IllegalArgumentException.class, () -> holding.setShares(-1));
        // The value must be unchanged after the failed call.
        assertEquals(10, holding.getShares());
    }
 
 
    // ----------------------------------------------------------
    /**
     * Good input for getCurrentValue().
     */
    @Test
    public void testGetCurrentValueGood()
    {
        assertEquals(1000.0, holding.getCurrentValue(), DELTA);
    }
 
 
    // ----------------------------------------------------------
    /**
     * Bad input for getCurrentValue().
     */
    @Test
    public void testGetCurrentValueBad()
    {
        HoldingClass nullHolding = new HoldingClass(null, 5);
        assertThrows(NullPointerException.class, () -> nullHolding.getCurrentValue());
    }

}
