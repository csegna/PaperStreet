package project02CS2114;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;

public class studentTransactionHistoryTest
{
    private playerTransactionHistory test1;

    @Before
    public void setUp()
    {
        test1 = new playerTransactionHistory("1/2/2003", 1234);
    }

    @Test
    public void testAddNewStockInfo()
    {
        test1.addNewStockInfo("AAPL", 150);

        assertEquals(
            Integer.valueOf(150),
            test1.getSpecificStockPrice("AAPL"));
    }

    @Test
    public void testAddNewStockInfoUpdate()
    {
        test1.addNewStockInfo("AAPL", 150);
        test1.addNewStockInfo("AAPL", 200);

        assertEquals(
            Integer.valueOf(200),
            test1.getSpecificStockPrice("AAPL"));
    }

    @Test
    public void testSetNewDate()
    {
        test1.setNewDate("12/30/2003");

        assertEquals("12/30/2003", test1.getDate());
    }

    @Test
    public void testSetNewAmountDeducted()
    {
        test1.setNewAmountDeducted(12345);

        assertEquals(12345, test1.getDailyDeduction());
    }

    @Test
    public void testGetSpecificStockPrice()
    {
        test1.addNewStockInfo("SAM", 5578);
        test1.addNewStockInfo("JAM", 3456);

        assertEquals(
            Integer.valueOf(5578),
            test1.getSpecificStockPrice("SAM"));
    }

    @Test
    public void testGetSpecificStockPriceNotFound()
    {
        test1.addNewStockInfo("AAPL", 150);

        assertNull(test1.getSpecificStockPrice("ZZZZ"));
    }

    @Test
    public void testGetSpecificStockPriceEmpty()
    {
        assertNull(test1.getSpecificStockPrice("AAPL"));
    }

    @Test
    public void testGetSpecificTickerBasedPrice()
    {
        test1.addNewStockInfo("SAM", 5578);
        test1.addNewStockInfo("JAM", 3456);

        assertEquals(
            "JAM",
            test1.getSpecificTickerBasedPrice(3456));
    }

    @Test
    public void testGetSpecificTickerBasedPriceNotFound()
    {
        test1.addNewStockInfo("SAM", 5578);

        assertEquals(
            "Ticker not found",
            test1.getSpecificTickerBasedPrice(9999));
    }

    @Test
    public void testGetSpecificTickerBasedPriceEmpty()
    {
        assertEquals(
            "Ticker not found",
            test1.getSpecificTickerBasedPrice(150));
    }

    @Test
    public void testGetDate()
    {
        assertEquals("1/2/2003", test1.getDate());
    }

    @Test
    public void testGetDailyDeduction()
    {
        assertEquals(1234, test1.getDailyDeduction());
    }
}