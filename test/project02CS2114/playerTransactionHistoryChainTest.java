package project02CS2114;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tester for playerTransactionHistoryChain.
 */
public class playerTransactionHistoryChainTest
{
    private playerTransactionHistoryChain testChain;
    private StockTransactionsClass entry1; // day 1
    private StockTransactionsClass entry2; // day 1
    private StockTransactionsClass entry3; // day 2

    @BeforeEach
    public void setUp()
    {
        StockInfoClass stock =
            new StockInfoClass("AAPL", "Apple", "Tech company", 100.0);
        testChain = new playerTransactionHistoryChain();
        entry1 = new StockTransactionsClass(
            stock, 5, 100.0, StockTransactionsClass.BUY, 1);
        entry2 = new StockTransactionsClass(
            stock, 2, 110.0, StockTransactionsClass.SELL, 1);
        entry3 = new StockTransactionsClass(
            stock, 1, 120.0, StockTransactionsClass.BUY, 2);
    }

    @Test
    public void testRecordKeepsOldestFirst()
    {
        testChain.record(entry1);
        testChain.record(entry2);
        testChain.record(entry3);

        StockTransactionsClass[] all = testChain.toArray();
        assertEquals(3, all.length);
        assertSame(entry1, all[0]);
        assertSame(entry2, all[1]);
        assertSame(entry3, all[2]);
    }

    @Test
    public void testRecordNull()
    {
        assertThrows(IllegalArgumentException.class,
            () -> testChain.record(null));
        assertTrue(testChain.isEmpty());
    }

    @Test
    public void testToArrayEmpty()
    {
        assertEquals(0, testChain.toArray().length);
    }

    @Test
    public void testGetTransactionsOnDay()
    {
        testChain.record(entry1);
        testChain.record(entry2);
        testChain.record(entry3);

        StockTransactionsClass[] dayOne = testChain.getTransactionsOnDay(1);
        assertEquals(2, dayOne.length);
        assertSame(entry1, dayOne[0]);
        assertSame(entry2, dayOne[1]);

        StockTransactionsClass[] dayTwo = testChain.getTransactionsOnDay(2);
        assertEquals(1, dayTwo.length);
        assertSame(entry3, dayTwo[0]);
    }

    @Test
    public void testGetTransactionsOnDayNotFound()
    {
        testChain.record(entry1);
        assertEquals(0, testChain.getTransactionsOnDay(5).length);
    }

    @Test
    public void testGetTransactionsOnDayEmpty()
    {
        assertEquals(0, testChain.getTransactionsOnDay(1).length);
    }

    @Test
    public void testToStringEmpty()
    {
        assertEquals("(no transactions)", testChain.toString());
    }

    @Test
    public void testToStringOneLinePerTransaction()
    {
        testChain.record(entry1);
        testChain.record(entry2);
        String[] lines = testChain.toString().split(
            System.lineSeparator());
        assertEquals(2, lines.length);
        assertEquals(entry1.toString(), lines[0]);
        assertEquals(entry2.toString(), lines[1]);
    }
}