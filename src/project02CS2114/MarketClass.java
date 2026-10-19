package project02CS2114;

import java.util.Random;

/**
 * The simulated market: the stocks, the trading day, and daily price
 * changes. Refuses to advance after MAX_IDLE_DAYS days without a trade
 *
 * @author Corbin Segna
 * @version Oct 11 2026
 */
public class MarketClass
{
    /** 
     * Game length when none is given
     */
    public static final int DEFAULT_TOTAL_DAYS = 15;
    
    /**
     *  Days in a row a player may go without trading. 
     */
    public static final int MAX_IDLE_DAYS = 2;
    
    /**
     *  Largest daily price move (0.05 = 5%)
     */
    private static final double MARKET_VOLATILITY = 0.01;
    private static final double MARKET_CARRYOVER = 0.3;
    private static final double DAILY_DRIFT = 0.0003;
    private static final double JUMP_CHANCE = 0.03;
    public static final double MAX_DAILY_MOVE = 0.20;

    private double lastMarketReturn = 0.0;
    
    /** 
     * Lowest possible price. 
     */
    public static final double MIN_PRICE = 0.01;

    private final StockInfoChain stocks;
    private final Random random;
    private final int totalDays;
    private int currentDay;
    private int lastTradeDay;

    /**
     * Creates a 30-day market with the default stocks.
     */
    public MarketClass()
    {
        this(DEFAULT_TOTAL_DAYS, new Random());
    }

    /**
     * Creates a market with a fixed random seed, so prices are the same
     * every run. Used for testing
     *
     * @param totalDays number of trading days; at least 1
     * @param seed the random seed
     * @throws IllegalArgumentException if totalDays is less than 1
     */
    public MarketClass(int totalDays, long seed)
    {
        this(totalDays, new Random(seed));
    }

    /**
     * Shared constructor
     *
     * @param totalDays number of trading days
     * @param random the random number source
     */
    private MarketClass(int totalDays, Random random)
    {
        if (totalDays < 1)
        {
            throw new IllegalArgumentException(
                "A game needs at least 1 trading day, got " + totalDays);
        }
        this.totalDays = totalDays;
        this.random = random;
        this.stocks = new StockInfoChain();
        this.currentDay = 1;
        this.lastTradeDay = 0;
        loadDefaultStocks();
    }

    /**
     * Gets the current trading day
     * starts at 1
     *
     * @return the current day
     */
    public int getCurrentDay()
    {
        return currentDay;
    }

    /**
     * Gets the total number of trading days
     *
     * @return the total days
     */
    public int getTotalDays()
    {
        return totalDays;
    }

    /**
     * Gets how many days are left after today
     *
     * @return the days remaining
     */
    public int getDaysRemaining()
    {
        return totalDays - currentDay;
    }

    /**
     * Checks whether this is the last trading day
     *
     * @return true if the game is over
     */
    public boolean isGameOver()
    {
        return currentDay >= totalDays;
    }

    /**
     * Finds a stock by ticker, ignoring case and extra spaces
     *
     * @param ticker the ticker typed by the player
     * @return the stock, or null if not in the market
     */
    public StockInfoClass getStock(String ticker)
    {
        return stocks.getByTicker(ticker);
    }

    /**
     * Checks whether a ticker is in the market
     *
     * @param ticker the ticker typed by the player
     * @return true if found
     */
    public boolean hasStock(String ticker)
    {
        return getStock(ticker) != null;
    }

    /**
     * Gets every stock in the market
     *
     * @return an array of the stocks
     */
    public StockInfoClass[] getAllStocks()
    {
        return stocks.toArray();
    }

    /**
     * Gets the number of stocks in the market
     *
     * @return the number of stocks
     */
    public int getMarketSize()
    {
        return stocks.size();
    }

    /**
     * Moves every price using a shared market move, each stock's own
     * beta and volatility, and an occasional news-style shock.
     */
    public void updatePrices()
    {
        double marketReturn = MARKET_CARRYOVER * lastMarketReturn
            + random.nextGaussian() * MARKET_VOLATILITY;
        lastMarketReturn = marketReturn;

        for (StockInfoClass stock : stocks.toArray())
        {
            String ticker = stock.getTicker();
            double change = DAILY_DRIFT
                + getBeta(ticker) * marketReturn
                + getVolatility(ticker) * random.nextGaussian();

            if (random.nextDouble() < JUMP_CHANCE)
            {
                double size = 0.05 + random.nextDouble() * 0.07;
                change += random.nextBoolean() ? size : -size;
            }

            change = Math.max(-MAX_DAILY_MOVE,
                Math.min(MAX_DAILY_MOVE, change));

            double newPrice = stock.getSharePrice() * Math.exp(change);
            newPrice = Math.round(newPrice * 100) / 100.0;
            stock.setSharePrice(Math.max(MIN_PRICE, newPrice));
        }
    }


    /**
     * How strongly a stock follows the overall market.
     *
     * @param ticker the stock's ticker
     * @return the stock's beta
     */
    private double getBeta(String ticker)
    {
        switch (ticker)
        {
            case "KO":   return 0.5;
            case "DIS":  return 1.0;
            case "MSFT": return 1.1;
            case "AAPL": return 1.1;
            case "F":    return 1.1;
            case "AMZN": return 1.2;
            case "NVDA": return 1.6;
            case "TSLA": return 1.8;
            default:     return 1.0;
        }
    }


    /**
     * A stock's own daily volatility, separate from the market.
     *
     * @param ticker the stock's ticker
     * @return the standard deviation of its daily noise
     */
    private double getVolatility(String ticker)
    {
        switch (ticker)
        {
            case "KO":   return 0.007;
            case "MSFT": return 0.010;
            case "AAPL": return 0.012;
            case "DIS":  return 0.014;
            case "AMZN": return 0.015;
            case "F":    return 0.017;
            case "NVDA": return 0.022;
            case "TSLA": return 0.026;
            default:     return 0.015;
        }
    }

    /**
     * Records that the player traded today. Call after every successful
     * buy or sell
     */
    public void recordTrade()
    {
        lastTradeDay = currentDay;
    }

    /**
     * Checks whether the player must trade before advancing
     *
     * @return true if MAX_IDLE_DAYS have passed without a trade
     */
    public boolean mustTradeBeforeAdvancing()
    {
        return currentDay - lastTradeDay > MAX_IDLE_DAYS;
    }

    /**
     * Moves to the next day and updates prices, if allowed
     *
     * @return true if the day advanced; false if the game is over or the
     *         player must trade first
     */
    public boolean advanceDay()
    {
        if (isGameOver() || mustTradeBeforeAdvancing())
        {
            return false;
        }
        currentDay++;
        updatePrices();
        return true;
    }

    /**
     * The market listing, one stock per line
     *
     * @return the listing
     */
    @Override
    public String toString()
    {
        return stocks.toString();
    }

    /**
     * Adds the starting stocks
     */
    private void loadDefaultStocks()
    {
        stocks.add(new StockInfoClass("AAPL", "Apple Inc.",
            "Makes the iPhone, Mac and iPad.", 190.00));
        stocks.add(new StockInfoClass("MSFT", "Microsoft Corp.",
            "Makes Windows, Office and the Azure cloud.", 420.00));
        stocks.add(new StockInfoClass("NVDA", "NVIDIA Corp.",
            "Designs graphics and AI chips.", 120.00));
        stocks.add(new StockInfoClass("AMZN", "Amazon.com Inc.",
            "Online store and AWS cloud services.", 180.00));
        stocks.add(new StockInfoClass("TSLA", "Tesla Inc.",
            "Makes electric cars and batteries.", 250.00));
        stocks.add(new StockInfoClass("DIS", "Walt Disney Co.",
            "Movies, theme parks and streaming.", 95.00));
        stocks.add(new StockInfoClass("KO", "Coca-Cola Co.",
            "Soft drinks and other beverages.", 62.00));
        stocks.add(new StockInfoClass("F", "Ford Motor Co.",
            "Makes cars and trucks.", 11.00));
    }
}