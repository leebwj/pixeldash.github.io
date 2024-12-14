# Pixel Dash


Pixel Dash is a single-player game designed with two distinct modes: Pixel Fly and Pixel Run. Players navigate the game world, collecting coins, unlocking new skins, dodging obstacles, and competing for high scores. The Game features a persistent data system, dynamic gameplay elements, and user-friendly GUIs. Program is developed in Java using Swing for GUI, and incorporates robust file management for player progress and leaderboards. Its modular structure allows for easy extension and feature additions.

---

## Warning

**This project is a Final Project submission for the CIS1200 course at the University of Pennsylvania. The code is provided solely for demonstration and execution purposes. You are not permitted to copy, redistribute, or reuse the code in any form. Violations may result in academic integrity issues. You may run the code for personal understanding but must not use it in other projects.**

---

## Table of Contents
1. [Intentions and Goals](#intentions-and-goals)
2. [How to Run the Game](#how-to-run-the-game)
3. [Game Modes](#game-modes)
4. [Features](#features)
5. [Core Concepts](#core-concepts)
6. [Class Overview](#class-overview)
7. [Known Challenges](#known-challenges)
8. [Future Improvements](#future-improvements)
9. [External Resources](#external-resources)

---

## Intentions and Goals

The primary goal of this project was to combine programming concepts learned throughout the CIS1200 course into a cohesive, interactive application. I aimed to create a game that not only demonstrates technical competence but also provides an engaging and enjoyable experience for players. Below are the specific intentions and goals for this project:

1. **Practical Application of Core Concepts**  
   - The game integrates fundamental programming concepts such as collections, inheritance, file I/O, and encapsulation. Each feature of the game was built with a clear connection to these concepts, demonstrating their practical use in real-world applications.

2. **Persistent Player Experience**  
   - A key feature of the game is its ability to save and load player progress across sessions. This includes saving coins, unlocked skins, and leaderboard entries, which provide players with a sense of continuity and accomplishment.

3. **Dynamic Gameplay and Progression**  
   - To keep the game engaging, I designed two distinct modes: **Pixel Fly** and **Pixel Run**, each offering unique mechanics. The game introduces progressively challenging obstacles, ensuring that players remain engaged while working toward unlocking new skins.

4. **Creative and Scalable Design**  
   - The modular structure of the code ensures that the game is easily extendable, with opportunities to add new obstacles, game modes, or visual features in the future. Additionally, the customizable skin system reflects creative input and rewards players for achieving milestones.

5. **User-Focused Interface**  
   - The GUI was carefully crafted to provide clear navigation between different screens, including the home screen, gameplay, instructions, and skin selection. Intuitive design choices and detailed instructions ensure a smooth user experience.

6. **Learning Through Challenges**  
   - This project helped me tackle real-world programming challenges, such as implementing efficient file I/O operations, preventing overlapping objects during spawning, and balancing difficulty levels. Each hurdle enhanced my problem-solving skills and contributed to my learning experience.


---

## How to Run the Game

1. Clone the repository from GitHub or download the source code:
    ```bash
    # Clone this repository
    $ git clone https://github.com/leebwj/pixeldash.github.io.git
    gh repo clone leebwj/pixeldash.github.io
    ```

2. Open the project in an IDE like IntelliJ IDEA or Eclipse.

3. Ensure the Maven `pom.xml` file is properly configured.

4. Run the main file:
   - `main` in the package `org.cis1200.pixeldash`.
   - OR run the `pom.xml` file directly.

---

## Game Modes

### Pixel Fly Mode
- Navigate vertically and horizontally to dodge flying obstacles while collecting coins.
- Difficulty increases as you progress with accelerating obstacle speeds.

### Pixel Run Mode
- Classic infinite runner mode.
- Jump over ground obstacles and dodge flying rockets to collect coins.
- Speed increases over time, challenging reflexes and strategy.

---

## Features

1. **Skins and Customization**
   - Players can unlock skins with coins and select their appearance in the game.
   - Skins include:
     - Default (free)
     - Green (10 coins)
     - Purple (25 coins)
     - Pink (50 coins)
     - Black (100 coins)
     - Yellow (150 coins)

2. **Leaderboard**
   - Separate leaderboards for Pixel Fly and Pixel Run modes.
   - Displays top 10 high scores for each mode.

3. **Persistent Data**
   - Saves and loads player progress, including coins, unlocked skins, and leaderboards.
   - Log in with the same username to load existing data from previous compilation.

4. **Dynamic Object Management**
   - Obstacles and coins spawn dynamically during gameplay.
   - Items are removed when they move off-screen to optimize performance.

5. **Reset Game**
   - Allows resetting all game progress, including leaderboards, skins, and coins.
   - Delete all existing save files. 


---

## Core Concepts

### 1. Collections and Maps
- **Implementation:** 
  - A `List<GameObj>` is used in `GameCourt` to store dynamic objects (e.g., coins, rockets).
  - The `SkinManager` class uses a `List<Skin>` to manage skins.
- **Purpose:** 
  - Enables efficient iteration, addition, and removal of game objects.
  - Facilitates dynamic updates like unlocking new skins or managing active objects.

### 2. Inheritance and Subtyping
- **Implementation:** 
  - `GameObj` is the base class for all game elements (e.g., `Coin`, `FlyingRocket`, `GroundObstacle`).
  - Specific behaviors are implemented in subclasses:
    - `Coin` increases score when collected.
    - `FlyingRocket` introduces challenging movement patterns.
- **Purpose:** 
  - Reduces code redundancy and ensures modularity.

### 3. File I/O
- **Implementation:** 
  - `PlayerData` saves player progress (coins, unlocked skins).
  - `LeaderboardManager` saves and loads leaderboards for both game modes.
  - Skins are saved persistently in files named `skins_<username>.txt`.
- **Purpose:** 
  - Ensures player progress is maintained across sessions.
  - Enables easy updates to game state.
 
### 4. JUnit Testing 
- **Implementation:** 
  - Validates player movement, collision detection, and game-over logic.
  - Ensures file I/O for coins, leaderboards, and skins works correctly.
  - Tests the dynamic spawning and removal of game objects.
- **Purpose:**
  - Ensures core game mechanics behave as expected.
  - Verifies persistence systems load and save data reliably.
  - Catches edge cases to prevent unexpected crashes or errors during gameplay. 

---

## Class Overview

### Key Classes and Their Roles:

1. **Coin.java**
   - Represents collectible coins that increase the player's score when collected.

2. **CoinManager.java**
   - Manages the player's total coins and updates them during gameplay.

3. **FlyingRocket.java**
   - A flying obstacle in Pixel Run mode that adds challenge with unpredictable movement.

4. **GameCourt.java**
   - Core game logic, including spawning, collision detection, score updates, and rendering.

5. **GameObj.java**
   - Base class for all game objects. Provides shared attributes (e.g., position, size) and methods.

6. **Player.java**
   - Represents the player's character and handles movement, collisions, and skin customization.

7. **PlayerData.java**
   - Stores and retrieves player-specific data (nickname, coins, unlocked skins).

8. **LeaderboardManager.java**
   - Handles saving, loading, and displaying high scores for each game mode.

9. **Skin.java**
   - Represents individual skins with attributes like color, name, and cost.

10. **SkinManager.java**
    - Manages the collection of skins, including unlocking, saving, and selecting skins.

11. **HomeScreen.java**
    - The main menu interface with buttons for different game modes, instructions, and settings.

12. **InstructionsPage.java**
    - Displays gameplay instructions for both game modes.

13. **RunPixelDash.java**
    - Entry point for the game. Handles the title screen and transitions to the home screen.

---

## Challenges Faced

1. **Spawning Logic**
   - Initial implementations caused clustering of obstacles and coins at the screen's bottom.
   - Refined algorithms improved randomness and fairness.

2. **Collision Detection**
   - Ensuring precision in collision detection without sacrificing performance was challenging.

3. **File Management**
   - Persistent data handling for skins, coins, and leaderboards required careful design to avoid data corruption.

---

## Future Improvements

1. **Enhanced Gameplay**
   - Add power-ups, levels, and additional challenges to increase replayability.

2. **Multiplayer Mode**
   - Introduce a competitive or cooperative multiplayer mode.

3. **Improved UI**
   - Add animations and visual effects to enhance the overall user experience.

4. **Mobile Compatibility**
   - Adapt the game for mobile devices with touch-based controls.

---

## External Resources

1. [Java Swing Documentation](https://docs.oracle.com/javase/tutorial/uiswing/)
2. [BufferedReader and BufferedWriter](https://www.w3schools.com/java/)
3. [Maven Project Setup](https://maven.apache.org/guides/)
4. [YouTube Tutorials](https://www.youtube.com/) for Swing and File I/O concepts.

---
