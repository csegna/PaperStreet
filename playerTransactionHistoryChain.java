package project02CS2114;

public class playerTransactionHistoryChain
    extends LinkedChain<playerTransactionHistory>
{
    // ~ Fields ................................................................
    
    // ~ Constructors ..........................................................
    public playerTransactionHistoryChain()
    {
        super();
    }


    public playerTransactionHistory getSpecificDayInfo(String date)
    {
        playerTransactionHistory[] entries =
            this.toArray(new playerTransactionHistory[this.size()]);

        int totalDeducted = 0;
        boolean found = false;

        for (playerTransactionHistory entry : entries)
        {
            if (entry.getDate().equals(date))
            {
                totalDeducted += entry.getDailyDeduction();
                found = true;
            }
        }

        if (found)
        {
            return new playerTransactionHistory(date, totalDeducted);
        }

        return null;
    }

    // ~Public Methods ........................................................

}
