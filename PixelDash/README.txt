===================
=: Core Concepts :=
===================

- List the four core concepts, the features they implement, and why each feature
  is an appropriate use of the concept. Incorporate the feedback you got after
  submitting your proposal.

  1.2D Arrays: 

The game uses a 2D array to represent the grid structure of the game world. The grid eases collision detection, placement of objects, and eases the overall game logic. For example, the GameCourt class holds a boolean[][] grid for tracking object positions where the grid ensures that items like coins and obstacles remain at a certain distance from each other for fair gameplay. Such spatial representation also makes the implementation of the scrolling effect easier, as objects are updated systematically as they cross the screen. The logical structure of the grid forces consistent spacing and alignment of game elements, providing a solid foundation for the game mechanics.


  2. Collections and/or Maps: 

Collections are heavily utilized in the game to manage dynamic objects such as coins and obstacles. In the GameCourt class, the objects list stores all active GameObj instances, including coins, ground obstacles, and rockets. This list allows for efficient iteration during each game tick to update positions, check for collisions, and remove objects that move off-screen. For example, objects are spawned dynamically during gameplay, and once they leave the visible area, they are removed from the list to optimize performance. Collections also enable flexibility in managing a variable number of game elements as the difficulty increases, ensuring smooth gameplay without hardcoding specific behaviors.

The unlock skin system uses a collection, such as List or ArrayList, to keep track of the available skins in the game. Each skin is stored as an object in the list, so iteration and manipulation of them are easy. Such a collection allows for dynamic updating of the list, to say, add new skins or change their state. For example, when the player unlocks a skin, it iterates through the list to find the proper skin object and updates its unlocked status. This organization with a list structure ensures that the skin data of the game remains well-organized and accessible at any point in gameplay.



  3. Inheritance and Sub-typing: 

Inheritance is a fundamental design choice in the game. The GameObj class serves as a base class for all game entities, including Coin, Obstacle, GroundObstacle, and FlyingRocket. It encapsulates shared attributes like position, size, and movement logic, as well as methods for detecting collisions and rendering. Subclasses override or extend these functionalities to implement specific behaviors. For example, the Coin class includes logic for increasing the player's score upon collection, while the FlyingRocket class introduces unique movement patterns. This design not only reduces redundancy but also makes the game extensible, allowing for new object types to be added with minimal changes to the existing code.


  4. File I/O: 

File I/O is used extensively to persist player progress and leaderboard data across sessions. The PlayerData class saves and loads player-specific information, such as the number of coins collected and unlocked skins, by writing to and reading from files. Similarly, the LeaderboardManager class manages high scores for both game modes (Pixel Run and Pixel Fly) by storing them in separate files. These classes use BufferedReader and BufferedWriter to handle file operations, ensuring efficiency and reliability. For example, when the game ends, the player's score is saved to the appropriate leaderboard file, and the high scores are displayed on the home screen. File I/O enhances player engagement by allowing progress tracking and continuity, providing a rewarding experience as players strive to improve their performance.

The unlock skin system employs BufferedWriter to save the state of unlocked skins persistently. Whenever a skin is unlocked, the system writes the updated state to a file, ensuring that progress is retained between game sessions. Using a file format that stores each skin's name and its unlocked status, the system guarantees data consistency. Additionally, upon game startup, the system reads this file to load previously unlocked skins using BufferedReader, ensuring that players can resume where they left off. This integration of File I/O with the unlock system enhances player experience and ensures seamless continuity.


(If you login with the same username you retrieve back game information that you had before. Resetting the game will delete all files except for the currently opened file)


===============================
=: File Structure Screenshot :=
===============================
- Include a screenshot of your project's file structure. This should include
  all of the files in your project, and the folders they are in. You can
  upload this screenshot in your homework submission to gradescope, named 
  "file_structure.png".

=========================
=: Your Implementation :=
=========================

- Provide an overview of each of the classes in your code, and what their
  function is in the overall game.

Coin.java: This class represents collectible coins in the game. It extends the GameObj class and defines the behavior and rendering logic for coins. Coins are scattered across the screen, and when collected, they increase the player's score.

CoinManager.java: This class is responsible for managing the player's total coins. It provides methods for updating, retrieving, and saving the player's coin data. It integrates with file I/O to ensure the persistence of data across game sessions.

FlyingRocket.java: This subclass of Obstacle defines the behavior for flying obstacles. The rockets make the game diverse and challenging by their movements in different ways within the game space. Used for pixel run mode.

Game.java: It is the entry point to the game and creates the main frame for the game. It is responsible for switching from the title screen to the home screen, and finally to gameplay in order to manage the overall flow of the game.

GameCourt.java: This is the heart of the game, where all core logic with regard to spawning coins and obstacles, updating the game state, handling player input, and rendering all game elements takes place. Collision detection, score updates, and game-over logic are also handled here.

GameObj.java: The base class for all game objects. It defines common properties such as position, size, velocity, and movement logic that are generic in nature for the blueprint that should be more specific to subclasses.

GameStateManager.java: Stores global game states, like resetting all progress and data. It talks to PlayerData, SkinManager, and leaderboards to make sure that everything stays consistent in case the player decides to reset the game.

GroundObstacle.java: The instance of a given obstacle that appears on the ground in pixel run mode. It makes the game more difficult since the player has to jump precisely to dodge them.

HomeScreen.java: This contains the main menu, where players can go into different modes, see instructions, and look at other features the game has, like skins and leaderboards. It creates a user-friendly interface to enhance the overall experience.

InstructionsPage.java: This class displays in detail the instructions on the game's mechanics, controls, and objective. It makes sure that a player knows how to play the game.

LeaderboardEntry.java: Represents individual entries in the game's leaderboard. Each entry contains a player name and their score, both of which are used to organize high scores in an orderly manner. LeaderboardManager.java: This is a manager for the leaderboard system, handling adding new scores and saving them correctly, loading them correctly, and keeping separate leaderboards for different game modes.

Obstacle.java: An abstract class representing a parent for all obstacles within the game. It contains the base logic for an obstacle and is extended by specific types—like FlyingRocket and GroundObstacle—to implement behaviors specific to them. Used for pixel fly mode.

Player.java: This class encapsulates the player's character, including movement mechanics, collisions, and other interactions. It provides different modes of play, like running or flying.

PlayerData.java: Stores player-specific information like nickname, coins, and unlocked skins. It integrates with file I/O to store player data between sessions for personalized experiences.

RunPixelDash.java: It is responsible for setting the environment of a game and configures the main application flow. It navigates through the title screen, gameplay, and other components of the game. 

Skin.java: Represents individual skins that players can unlock and apply to their character. Each skin has attributes like color, name, and cost. 

SkinManager.java: This class manages the collection of skins. It allows players to unlock new ones and change their appearance. It also handles persistence in order to save the unlocked skins and the selected skin. 

SkinPage.java: This class contains the UI implementation for viewing, unlocking, and selecting skins. It displays the available skins in an organized manner and features buttons to interact with them. 


Overall, Each class contributes to the game, encapsulating a certain kind of functionality; this way, the game is well modular and easy to maintain. The interaction among these classes provides smooth transitions between states and cohesive gameplay.


- Were there any significant stumbling blocks while you were implementing your
  game (related to your design, or otherwise)?

One of the hardest parts in development was achieving a balance in the game mechanics. Specifically, the even distribution of coins and obstacles proved difficult. The first few implementations placed clustering at the bottom of the screen, so the spawning algorithm needed refinement to produce better randomness. Another challenge was in GameCourt: managing dynamic objects—especially preventing overlapping during spawning while maintaining smooth performance. Persistent data management, including saving and loading high scores, coins, and skins, introduced additional complexity but was crucial for creating a seamless player experience.


- Evaluate your design. Is there a good separation of functionality? How well is
  private state encapsulated? What would you refactor, if given the chance?

Overall, the design does a good job of separating functionality across classes, which makes it modular and maintainable. The private state is well-encapsulated; classes expose only the methods that are needed and hide internal details. However, spawning logic in GameCourt could be further refactored to make it clearer and less redundant. Furthermore, some overlapping responsibilities between PlayerData and SkinManager could be integrated to make the system leaner. While these are areas to improve, the game's structure supports scalability and provides a good foundation for future enhancements.

