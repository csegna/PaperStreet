package project02CS2114;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

// -------------------------------------------------------------------------
/**
 * Tester for the HoldingClass.
 *
 * @author mlkuhl
 * @version Oct 12, 2026
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


    // ---------------- constructors ----------------
    /**
     * A null stock is rejected.
     */
    @Test
    public void testConstructorNullStock()
    {
        assertThrows(IllegalArgumentException.class,
            () -> new HoldingClass(null, 5));
        assertThrows(IllegalArgumentException.class,
            () -> new HoldingClass(null, 5, 100.0));
    }


    /**
     * Negative shares are rejected.
     */
    @Test
    public void testConstructorNegativeShares()
    {
        assertThrows(IllegalArgumentException.class,
            () -> new HoldingClass(stock, -3));
    }


    /**
     * A non-positive average price is rejected.
     */
    @Test
    public void testConstructorBadAveragePrice()
    {
        assertThrows(IllegalArgumentException.class,
            () -> new HoldingClass(stock, 5, 0.0));
        assertThrows(IllegalArgumentException.class,
            () -> new HoldingClass(stock, 5, -10.0));
    }


    /**
     * The two-argument constructor uses the stock's current price as the
     * average purchase price.
     */
    @Test
    public void testConstructorDefaultAveragePrice()
    {
        assertEquals(100.0, holding.getAveragePurchasePrice(), DELTA);
    }


    // ---------------- getStock ----------------
    /**
     * Good input for getStock()
     */
    @Test
    public void testGetStockGood()
    {
        assertSame(stock, holding.getStock());
    }


    // ---------------- getShares ----------------
    /**
     * Good input for getShares()
     */
    @Test
    public void testGetSharesGood()
    {
        assertEquals(10, holding.getShares());
    }


    /**
     * Zero shares is allowed.
     */
    @Test
    public void testGetSharesZero()
    {
        assertEquals(0, new HoldingClass(stock, 0).getShares());
    }


    // ---------------- setShares ----------------
    /**
     * Good input for setShares().
     */
    @Test
    public void testSetSharesGood()
    {
        holding.setShares(25);
        assertEquals(25, holding.getShares());
        assertEquals(100.0, holding.getAveragePurchasePrice(), DELTA);
    }


    /**
     * Bad input for setShares().
     */
    @Test
    public void testSetSharesBad()
    {
        assertThrows(IllegalArgumentException.class,
            () -> holding.setShares(-1));
        // The value must be unchanged after the failed call.
        assertEquals(10, holding.getShares());
    }


    // ---------------- addShares ----------------
    /**
     * Buying more at a higher price raises the weighted average.
     */
    @Test
    public void testAddSharesGood()
    {
        holding.addShares(10, 200.0); // 10 @ 100 + 10 @ 200
        assertEquals(20, holding.getShares());
        assertEquals(150.0, holding.getAveragePurchasePrice(), DELTA);
    }


    /**
     * Bad input for addShares() throws and changes nothing.
     */
    @Test
    public void testAddSharesBad()
    {
        assertThrows(IllegalArgumentException.class,
            () -> holding.addShares(0, 100.0));
        assertThrows(IllegalArgumentException.class,
            () -> holding.addShares(-5, 100.0));
        assertThrows(IllegalArgumentException.class,
            () -> holding.addShares(5, 0.0));
        assertThrows(IllegalArgumentException.class,
            () -> holding.addShares(5, -1.0));
        assertEquals(10, holding.getShares());
        assertEquals(100.0, holding.getAveragePurchasePrice(), DELTA);
    }


    // ---------------- removeShares ----------------
    /**
     * Selling shares does not change the average purchase price.
     */
    @Test
    public void testRemoveSharesGood()
    {
        holding.removeShares(4);
        assertEquals(6, holding.getShares());
        assertEquals(100.0, holding.getAveragePurchasePrice(), DELTA);
    }


    /**
     * Removing every share is allowed.
     */
    @Test
    public void testRemoveSharesAll()
    {
        holding.removeShares(10);
        assertEquals(0, holding.getShares());
    }


    /**
     * Bad input for removeShares() throws and changes nothing.
     */
    @Test
    public void testRemoveSharesBad()
    {
        assertThrows(IllegalArgumentException.class,
            () -> holding.removeShares(11));
        assertThrows(IllegalArgumentException.class,
            () -> holding.removeShares(0));
        assertThrows(IllegalArgumentException.class,
            () -> holding.removeShares(-1));
        assertEquals(10, holding.getShares());
    }


    // ---------------- getCurrentValue ----------------
    /**
     * Good input for getCurrentValue().
     */
    @Test
    public void testGetCurrentValueGood()
    {
        assertEquals(1000.0, holding.getCurrentValue(), DELTA);
    }


    /**
     * Zero shares are worth nothing.
     */
    @Test
    public void testGetCurrentValueZeroShares()
    {
        assertEquals(0.0, new HoldingClass(stock, 0).getCurrentValue(),
            DELTA);
    }


    /**
     * The value follows the stock's price, but the average does not.
     */
    @Test
    public void testGetCurrentValuePriceChange()
    {
        stock.setSharePrice(150.0);
        assertEquals(1500.0, holding.getCurrentValue(), DELTA);
        assertEquals(100.0, holding.getAveragePurchasePrice(), DELTA);
    }


    // ---------------- getCostBasis ----------------
    /**
     * Cost basis is shares times average price and ignores the market.
     */
    @Test
    public void testGetCostBasis()
    {
        assertEquals(1000.0, holding.getCostBasis(), DELTA);
        stock.setSharePrice(150.0);
        assertEquals(1000.0, holding.getCostBasis(), DELTA);
    }
}