package project02CS2114;

import java.util.Random;
import java.util.Map;
import java.util.HashMap;

/**
 * The simulated market: the stocks, the trading day, and daily price changes.
 * Refuses to advance after MAX_IDLE_DAYS days without a trade
 *
 * @author Corbin Segna
 * @version Oct 11 2026
 */
public class MarketClass
{
    /**
     * Game length when none is given
     */
    public static final int DEFAULT_TOTAL_DAYS = 30;

    /**
     * Days in a row a player may go without trading.
     */
    public static final int MAX_IDLE_DAYS = 2;

    /**
     * Largest daily price move (0.05 = 5%)
     */
    public static final double MAX_DAILY_CHANGE = 0.05;

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
     * Creates a market with a fixed random seed, so prices are the same every
     * run. Used for testing
     *
     * @param totalDays
     *            number of trading days; at least 1
     * @param seed
     *            the random seed
     * @throws IllegalArgumentException
     *             if totalDays is less than 1
     */
    public MarketClass(int totalDays, long seed)
    {
        this(totalDays, new Random(seed));
    }


    /**
     * Shared constructor
     *
     * @param totalDays
     *            number of trading days
     * @param random
     *            the random number source
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
     * Gets the current trading day starts at 1
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
     * @param ticker
     *            the ticker typed by the player
     * @return the stock, or null if not in the market
     */
    public StockInfoClass getStock(String ticker)
    {
        return stocks.getByTicker(ticker);
    }


    /**
     * Checks whether a ticker is in the market
     *
     * @param ticker
     *            the ticker typed by the player
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
     * Moves every price up or down every day by the alphavantage imports...only
     * one time a day or such
     */
    public void updatePrices()
    {
        applicationAlphaVantage.initializeAlphaVantage();
        Map<String, Double> updatedStocks = stockUI.fetchStockData();

        for (StockInfoClass stock : stocks.toArray())
        {
            Double newPrice = updatedStocks.get(stock.getTicker());

            if (newPrice != null
                && Double.isFinite(newPrice)
                && newPrice > 0)
            {
                newPrice = Math.round(newPrice * 100) / 100.0;
                stock.setSharePrice(Math.max(MIN_PRICE, newPrice));
            }
        }
    }

    /**
     * Records that the player traded today. Call after every successful buy or
     * sell
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
        return currentDay - lastTradeDay >= MAX_IDLE_DAYS;
    }


    /**
     * Moves to the next day and updates prices, if allowed
     *
     * @return true if the day advanced; false if the game is over or the player
     *             must trade first
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
        stocks.add(
            new StockInfoClass(
                "AAPL",
                "Apple Inc.",
                "Makes the iPhone, Mac and iPad.",
                190.00));
        stocks.add(
            new StockInfoClass(
                "MSFT",
                "Microsoft Corp.",
                "Makes Windows, Office and the Azure cloud.",
                420.00));
        stocks.add(
            new StockInfoClass(
                "NVDA",
                "NVIDIA Corp.",
                "Designs graphics and AI chips.",
                120.00));
        stocks.add(
            new StockInfoClass(
                "AMZN",
                "Amazon.com Inc.",
                "Online store and AWS cloud services.",
                180.00));
        stocks.add(
            new StockInfoClass(
                "TSLA",
                "Tesla Inc.",
                "Makes electric cars and batteries.",
                250.00));
        stocks.add(
            new StockInfoClass(
                "DIS",
                "Walt Disney Co.",
                "Movies, theme parks and streaming.",
                95.00));
        stocks.add(
            new StockInfoClass(
                "KO",
                "Coca-Cola Co.",
                "Soft drinks and other beverages.",
                62.00));
        stocks.add(
            new StockInfoClass(
                "F",
                "Ford Motor Co.",
                "Makes cars and trucks.",
                11.00));
    }
}
