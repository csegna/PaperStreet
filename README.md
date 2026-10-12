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
