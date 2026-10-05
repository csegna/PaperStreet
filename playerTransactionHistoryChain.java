package project02CS2114;

public class playerTransactionHistoryChain
    extends LinkedChain<playerTransactionHistory>
{
    // ~ Fields ................................................................
    private playerTransactionHistory[] entries =
        this.toArray(new playerTransactionHistory[this.size()]);

    // ~ Constructors ..........................................................
    public playerTransactionHistoryChain()
    {
        super();
    }


    public playerTransactionHistory getSpecificDayInfo(String date)
    {
        for (playerTransactionHistory i : entries)
        {
            if (i.getDate() == date)
            {
                return i;
            }
            else
            {
                continue;
            }
        }
        return null;
    }

    // ~Public Methods ........................................................

}
