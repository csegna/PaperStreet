package project02CS2114;

import java.util.Scanner;

/**
 * Console interface for PaperStreet, a paper trading game.
 *
 * @author (your name)
 * @version Oct 2026
 */
public class Main
{
    private static final double STARTING_BALANCE = 10000.00;

    private static Scanner scanner;
    private static MarketClass market;
    private static playerProfileClass player;

    /**
     * Runs the game.
     *
     * @param args unused
     */
    public static void main(String[] args)
    {
        scanner = new Scanner(System.in);

        System.out.print("Please enter your name: ");
        String name = scanner.nextLine().trim();
        if (name.isEmpty())
        {
            name = "Player";
        }

        startGame(name);
        System.out.println("Welcome to PaperStreet, " + player.getName()
            + "!");

        boolean running = true;
        while (running)
        {
            displayStatus();
            displayMenu();
            int choice = readInt("Enter your choice: ");
            running = processChoice(choice);
        }

        showFinalResults();
        scanner.close();
    }


    /**
     * Creates the market and the player.
     *
     * @param name the player's name
     */
    public static void startGame(String name)
    {
        market = new MarketClass();
        player = new playerProfileClass(name, STARTING_BALANCE);
    }


    /**
     * Shows the day counter and cash balance.
     */
    public static void displayStatus()
    {
        System.out.println();
        System.out.println("=== Day " + market.getCurrentDay() + " of "
            + market.getTotalDays() + " (" + market.getDaysRemaining()
            + " remaining) ===");
        System.out.printf("Cash: $%,.2f%n", player.getBalance());
    }


    /**
     * Prints the menu.
     */
    public static void displayMenu()
    {
        System.out.println("1. View market");
        System.out.println("2. Buy stock");
        System.out.println("3. Sell stock");
        System.out.println("4. View portfolio");
        System.out.println("5. View transaction history");
        if (market.isGameOver())
        {
            System.out.println("6. Finish game");
        }
        else
        {
            System.out.println("6. Advance to next day");
        }
        System.out.println("0. Quit and show results");
    }


    /**
     * Carries out a menu choice.
     *
     * @param choice the menu number entered
     * @return true if the game continues, false if it should end
     */
    public static boolean processChoice(int choice)
    {
        switch (choice)
        {
            case 1:
                viewMarket();
                return true;
            case 2:
                buy();
                return true;
            case 3:
                sell();
                return true;
            case 4:
                viewPortfolio();
                return true;
            case 5:
                viewHistory();
                return true;
            case 6:
                return advance();
            case 0:
                return false;
            default:
                System.out.println("Please choose a number from 0 to 6.");
                return true;
        }
    }


    /**
     * Prints every stock with its price and description.
     */
    private static void viewMarket()
    {
        System.out.println();
        for (StockInfoClass stock : market.getAllStocks())
        {
            System.out.println(stock);
            System.out.println("       " + stock.getDescription());
        }
    }


    /**
     * Handles buying shares.
     */
    private static void buy()
    {
        StockInfoClass stock = askForStock();
        if (stock == null)
        {
            return;
        }
        int shares = readInt("Shares to buy: ");
        if (shares <= 0)
        {
            System.out.println("Shares must be a positive number.");
            return;
        }
        double price = stock.getSharePrice();
        double cost = shares * price;
        if (cost > player.getBalance())
        {
            System.out.printf("Not enough cash. That costs $%,.2f.%n", cost);
            return;
        }

        if (StockTransactionsClass.buyStock(
            player, stock, shares, market.getCurrentDay()))
        {
            market.recordTrade();
            System.out.printf("Bought %d share(s) of %s at $%,.2f.%n",
                shares, stock.getTicker(), price);
        }
        else
        {
            System.out.println("Purchase failed.");
        }
    }


    /**
     * Handles selling shares.
     */
    private static void sell()
    {
        StockInfoClass stock = askForStock();
        if (stock == null)
        {
            return;
        }
        HoldingClass holding = player.findHolding(stock.getTicker());
        if (holding == null)
        {
            System.out.println("You do not own any " + stock.getTicker()
                + ".");
            return;
        }
        int owned = holding.getShares();
        int shares = readInt("Shares to sell (you own " + owned + "): ");
        if (shares <= 0 || shares > owned)
        {
            System.out.println("Enter a number from 1 to " + owned + ".");
            return;
        }

        // Read these first: the holding is removed if all shares are sold.
        double price = stock.getSharePrice();
        double avg = holding.getAveragePurchasePrice();

        if (StockTransactionsClass.sellStock(
            player, stock, shares, market.getCurrentDay()))
        {
            market.recordTrade();
            System.out.printf("Sold %d share(s) of %s at $%,.2f.%n",
                shares, stock.getTicker(), price);
            double pl = (price - avg) * shares;
            System.out.printf("%s on this sale: $%,.2f%n",
                pl >= 0 ? "Profit" : "Loss", Math.abs(pl));
        }
        else
        {
            System.out.println("Sale failed.");
        }
    }


    /**
     * Prints holdings, cash, and total value.
     */
    private static void viewPortfolio()
    {
        System.out.println();
        if (player.getHoldings().isEmpty())
        {
            System.out.println("You do not own any stocks.");
        }
        for (HoldingClass h : player.getHoldings())
        {
            StockInfoClass s = h.getStock();
            System.out.printf(
                "%-6s %5d shares | avg paid $%,.2f | now $%,.2f"
                    + " | value $%,.2f%n",
                s.getTicker(), h.getShares(), h.getAveragePurchasePrice(),
                s.getSharePrice(), h.getCurrentValue());
        }
        System.out.printf("Cash:            $%,.2f%n", player.getBalance());
        System.out.printf("Stocks value:    $%,.2f%n",
            player.getPortfolioValue());
        System.out.printf("Total portfolio: $%,.2f%n",
            player.getTotalValue());
    }


    /**
     * Prints every completed transaction, oldest first.
     */
    private static void viewHistory()
    {
        System.out.println();
        System.out.print(player.getTransactionHistory());
    }


    /**
     * Moves to the next day, or ends the game on the last day.
     *
     * @return true if the game continues, false if it is over
     */
    private static boolean advance()
    {
        if (market.isGameOver())
        {
            return false;
        }
        if (!market.advanceDay())
        {
            System.out.println("You must make a trade before advancing.");
            return true;
        }
        System.out.println("Advanced to day " + market.getCurrentDay()
            + ". Prices have changed.");
        return true;
    }


    /**
     * Prints the final value and overall gain or loss.
     */
    private static void showFinalResults()
    {
        double change = player.getProfitLoss();
        System.out.println();
        System.out.println("=== FINAL RESULTS ===");
        System.out.printf("Starting balance: $%,.2f%n",
            player.getStartingBalance());
        System.out.printf("Final portfolio:  $%,.2f%n",
            player.getTotalValue());
        System.out.printf("%s: $%,.2f (%.2f%%)%n",
            change >= 0 ? "Gain" : "Loss", Math.abs(change),
            change / player.getStartingBalance() * 100);
        System.out.println("Thanks for playing, " + player.getName() + "!");
    }


    // ---------------------------- helpers ----------------------------

    /**
     * Asks for a ticker and looks it up.
     *
     * @return the stock, or null (after printing a message) if not found
     */
    private static StockInfoClass askForStock()
    {
        System.out.print("Enter ticker: ");
        StockInfoClass stock = market.getStock(scanner.nextLine());
        if (stock == null)
        {
            System.out.println("No stock with that ticker.");
        }
        return stock;
    }


    /**
     * Prompts until the user types a whole number.
     *
     * @param prompt the text to show
     * @return the number entered
     */
    private static int readInt(String prompt)
    {
        while (true)
        {
            System.out.print(prompt);
            try
            {
                return Integer.parseInt(scanner.nextLine().trim());
            }
            catch (NumberFormatException e)
            {
                System.out.println("Please enter a whole number.");
            }
        }
    }
}