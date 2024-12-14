package org.project.pixeldash;

import java.io.File;

/**
 * The class encapsulates all game-resetting logic, providing a single method (resetAllGameData)
 * that handles all operations. This abstraction shields other parts of the game from the
 * complexities of file handling and resetting.
 */

public class GameStateManager {

    // Holds the username of the player whose game data is being managed.
    private String currentUsername;

    // Initializes the manager with the specified username.
    public GameStateManager(String username) {
        this.currentUsername = username;
    }

    // This method is the core functionality of the class. It completely resets all stored
    // game data, including skins, coins, leaderboards, and player files.
    public void resetAllGameData() {

        SkinManager.resetSkins();

        // Close any open references to the skin file
        SkinManager.saveSkins();

        // Delete player data directory
        File playerDataDir = new File("player_data");
        if (playerDataDir.exists() && playerDataDir.isDirectory()) {
            File[] files = playerDataDir.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (!file.delete()) {
                        System.out.println("Failed to delete file: " + file.getName());
                    }
                }
            }
        }

        // Delete leaderboard files
        String[] otherFiles = {"coins.dat", "pixel_run_leaderboard.txt", "dino_run_leaderboard.txt"};
        for (String filename : otherFiles) {
            File file = new File(filename);
            if (file.exists()) {
                if (!file.delete()) {
                    System.out.println("Failed to delete file: " + filename);
                }
            }
        }

        // Delete all skins-related files (e.g., skins_<nickname>.txt)
        File currentDir = new File("."); // Current working directory
        File[] skinFiles = currentDir.listFiles((dir, name) -> name.startsWith("skins_") && name.endsWith(".txt"));
        if (skinFiles != null) {
            for (File skinFile : skinFiles) {
                if (!skinFile.delete()) {
                    System.out.println("Failed to delete file: " + skinFile.getName());
                }
            }
        }

        // Reset in-memory state
        CoinManager.resetCoins();
        SkinManager.resetSkins();
    }
}
