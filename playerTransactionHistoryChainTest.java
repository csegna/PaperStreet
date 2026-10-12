package project02CS2114;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;

public class playerTransactionHistoryChainTest
{
    private playerTransactionHistoryChain testChain;
    private playerTransactionHistory entry1;
    private playerTransactionHistory entry2;

    @Before
    public void setUp()
    {
        testChain = new playerTransactionHistoryChain();
        entry1 = new playerTransactionHistory("1/2/2003", 1234);
        entry2 = new playerTransactionHistory("12/30/2003", 5678);
    }

    @Test
    public void testGetSpecificDayInfo()
    {
        testChain.add(entry1);
        testChain.add(entry2);

        assertSame(
            entry1,
            testChain.getSpecificDayInfo(new String("1/2/2003")));
        assertSame(
            entry2,
            testChain.getSpecificDayInfo(new String("12/30/2003")));
    }

    @Test
    public void testGetSpecificDayInfoNotFound()
    {
        testChain.add(entry1);
        testChain.add(entry2);

        assertNull(testChain.getSpecificDayInfo("5/10/2004"));
    }

    @Test
    public void testGetSpecificDayInfoEmpty()
    {
        assertNull(testChain.getSpecificDayInfo("1/2/2003"));
    }

    @Test
    public void testGetSpecificDayInfoNull()
    {
        testChain.add(entry1);

        assertNull(testChain.getSpecificDayInfo(null));
    }
}