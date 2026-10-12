# PaperStreet
paper trading game project

Reflection (Megha Dabbeeru): 

- I completed the playerTransactionHistory, playerTransactionHistoryChain, and Main class for our project. In the creation of these classes, there were a few things that were changed. One of these include my realization throughout the process of the creation of the playerTransactionHistoryChain class that I didn't have the need to actually write explicit add(), contains(), etc. methods due to the methods within the LinkedChain class being sufficient. In addition, for the PlayerTransactionHistory class, I changed the structure from being something akin to a list to being a Map/Hashmap, which I determined would work far easier for our purposes and would be much more organized. In accordance with this change, I changed the methods that were created, from being more linked chain/array based to being more related to the Map data structure.
- Nothing changed with the LinkedChain in my implementation of the classes I worked on.
- We didn't quite have many, if any, unknowns in our scope of the project, as such, I cannot speak on whether or not any unknowns were found out or not.
- For my part of this project, what is left is to test is the actual combining of all the components of this project as the three of us worked on our parts very separately.


Next Steps :

- The most glaring parts of our MVP that still need completion are the actual background running components of the application, including the day counter and the end of game information presentation. These will most likely be done in the main method and need all the components of this project to be working and together to be run, as such, we will first merge all parts of the project together before actually writing this code to essentially analyze the stats and decisions of the player.
- We will most likely be implementing the budgeting systems so that this can be a more accurate representation of how an application like this can be more used as a financial planning tool rather than a simple stock trading simulation. We also might include AlphaVantage integration to this project as we would more easily receive real-time information about the companies we are using these stocks with. We probably won't do a feature that would let the user view real-time news, the feature of an ai-like system, and a feature that predicts how the value of various stocks may increase with time.
- 
- f



Attribution: 

Megha- PlayerTransactionHistory
Megha-PlayerTransactionHistoryChain
Megha-PlayerTransactionHistoryChainTest (Used GenAI to help figure out edge cases and what exactly to test)
Megha-studentTransactionHistoryTest (Used GenAI to help figure out edge cases and what exactly to test)
Megha-Main (Used GenAI to help figure out how the structure of the code should function, in terms of when the gameStart function should be called and what each method should do in conjunction).




Reflection (Corbin Segna):

- I completed StockInfoClass, StockInfoChain, and MarketClass. Changes from our spec: StockInfoChain holds a LinkedChain as a field instead of extending it, since the LinkedChain says to use it that way and it keeps all app logic out of the chain. add() now returns boolean so it can refuse duplicate tickers, and advanceDay() returns boolean so Main knows if the day actually advanced. I added getByTicker(), recordTrade(), getDaysRemaining(), and isGameOver(), and an equals() on StockInfoClass (by ticker) so contains/count/remove work. Validation for prices lives in StockInfoClass, and the 2-day no-trading rule lives in MarketClass.advanceDay() like the spec said The "count of 2 AAPL" test from our plan was dropped since tickers are unique. (Changes came from my own review while building, with Claudes help.)
- I did not change LinkedChain, my classes only use its public methods.
- Unknown #2 (how to change prices) is solved: each day every price moves by a random -5% to +5%, rounded to the cent and never below $0.01. Unknowns #1 (real-time API) and #3 (saving/loading a game) are still open.
- My classes and their 47 tests all pass. Nothing in my part is failing, it still needs to be connected to Main.

Next Steps (Corbin):

- Connect MarketClass to Main: show the market, day counter, and days remaining, and call market.recordTrade() after every successful buy/sell
- New tests: MarketClass with the full game loop (trade, advance, game over), and the buy/sell flow with an unknown ticker and a lowercase ticker
- Stretch goals: per stock volatility and random "news events" for prices

Attribution:

Corbin-StockInfoClass 
Corbin-StockInfoChain 
Corbin-MarketClass (Price modeling and market built with claude asssistance)
Corbin-StockInfoClassTest (test reviewed by claude)
Corbin-StockInfoChainTest (test review by claude)
Corbin-MarketClassTest (Claude assisted)
Corbin-Project setup (src/test layout, merged Megha's files, converted her tests to JUnit 5)
