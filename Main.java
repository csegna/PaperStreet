package project02CS2114;

import java.util.*;

public class Main
{
    private static playerTransactionHistoryChain history;
    private static Scanner scanner;
    // ~ Fields ................................................................
    public static void main(String[] args)
    {
         scanner = new Scanner(System.in);

        System.out.println("Please enter your name: ");
        String User_Name = scanner.nextLine();
        System.out.println("Please enter your assigned username: ");
        String username = scanner.nextLine();
        System.out.println("Welcome to PaperStreet " + User_Name+"!");
        StartGame();
        boolean running = true;
        while(running) {
        displayMenu();
        try {
        int choice = Integer.parseInt(scanner.nextLine());
        if(choice == 0) {
            running = false;
        }
        else {
            processChoice(choice);
        }
        }
        catch(NumberFormatException  exception){
            System.out.println("Please pring out a whole number!");
        }
        }
        System.out.println("Goodbye!!");
        scanner.close();
        
        

    }


    public static void StartGame()
    {
        history = new playerTransactionHistoryChain();
        
    }
    
    public static void displayMenu() {
        System.out.println("\n1. Add a transaction record");
        System.out.println("2. View a transaction by date");
        System.out.println("0. Exit");
        System.out.println("Enter your choice:");    }
    
    public static void processChoice(int choice) {
        if (choice == 1)
        {
            System.out.println("Enter the date (YYYY-MM-DD):");
            String date = scanner.nextLine().trim();

            if (date.equals(""))
            {
                System.out.println("Please enter a date!");
                return;
            }

            System.out.println("Enter the amount deducted:");
            int amount = Integer.parseInt(scanner.nextLine().trim());

            if (amount < 0)
            {
                System.out.println("Please enter a positive value!!");
                return;
            }

            playerTransactionHistory transaction =
                new playerTransactionHistory(date, amount);

            history.add(transaction);
            System.out.println("Transaction added.");
        }
        else if (choice == 2)
        {
            System.out.println("Enter date please:");
            String searchDate = scanner.nextLine().trim();

            playerTransactionHistory result =
                history.getSpecificDayInfo(searchDate);

            if (result == null)
            {
                System.out.println("No transaction found for that date.");
            }
            else
            {
                System.out.println("Date: " + result.getDate());
                System.out.println(
                    "Amount deducted: " + result.getDailyDeduction());
            }
        }
        else
        {
            System.out.println("Please choose 0, 1, or 2.");
        }
    }
    // ~ Constructors ..........................................................

    // ~Public Methods ........................................................

}
