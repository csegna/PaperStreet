package project02CS2114;

import com.crazzyghost.alphavantage.AlphaVantage;
import com.crazzyghost.alphavantage.Config;

public class applicationAlphaVantage
{
    protected static void initializeAlphaVantage()
    {
        Config cfg =
            Config.builder().key("U9UI2M0BY8PGKSZ9").timeOut(10).build();
        AlphaVantage.api().init(cfg);
    }

}
