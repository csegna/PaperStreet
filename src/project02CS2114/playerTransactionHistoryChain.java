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

        for (playerTransactionHistory entry : entries)
        {
            if (entry.getDate().equals(date))
            {
                return entry;
            }
        }
        return null;
    }

    // ~Public Methods ........................................................

}
