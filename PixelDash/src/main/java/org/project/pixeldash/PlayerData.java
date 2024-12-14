package org.project.pixeldash;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

/**
 * The PlayerData class centralizes the management of player-related data, such as coins and unlocked skins,
 * ensuring consistency across the game's components.It supports the game's progression system by
 * persisting data between sessions, allowing players to continue from where they left off.
 * The class is integrated with other components like the CoinManager (to update coin totals) and
 * SkinManager (to manage skin unlocking and selection).
 */

public class PlayerData {

    private static final String DATA_DIR = "player_data";
    private String username;
    private int totalCoins;
    private List<String> unlockedSkins;
    private static PlayerData instance; // Singleton instance

    /**
     * Constructor for a new player.
     *
     * @param username The username of the player.
     */
    public PlayerData(String username) {
        this.username = username;
        this.totalCoins = 0;
        this.unlockedSkins = new ArrayList<>();
        this.unlockedSkins.add("Default"); // Default skin is always unlocked
    }

    // Returns the current instance of PlayerData, ensuring that the class follows the singleton pattern.
    public static PlayerData getInstance() {
        if (instance == null) {
            throw new IllegalStateException("PlayerData has not been initialized.");
        }
        return instance;
    }

    // Loads the player's data from the file (if it exists) or creates a new PlayerData instance
    // if the file does not exist.
    public static void initialize(String username) {
        instance = load(username);
    }

    // Retrieve and update the player's coin total, saving the updated data to the file.
    public String getUsername() {
        return username;
    }

    // Adds coins to the player's total and saves the updated data.
    public int getCoins() {
        return totalCoins;
    }

    // Set current total coins player has
    public void setCoins(int coins) {
        this.totalCoins = coins;
        save();
    }

    // Checks if a specific skin is unlocked.
    public boolean isSkinUnlocked(String skinName) {
        return unlockedSkins.contains(skinName);
    }

    // Unlocks a new skin and saves the updated data.
    public void unlockSkin(String skinName) {
        if (!unlockedSkins.contains(skinName)) {
            unlockedSkins.add(skinName);
            save();
        }
    }

    // Writes the current state of the player's data (coins and unlocked skins) to a file in the player_data directory.
    public void save() {
        File dir = new File(DATA_DIR);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        File file = new File(dir, username + ".dat");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write("coins=" + totalCoins + "\n");
            writer.write("skins=" + String.join(",", unlockedSkins) + "\n");
        } catch (IOException e) {
            System.out.println("Error saving player data: " + e.getMessage());
        }
    }

    // Reads the player's data from the file. If the file does not exist, a new PlayerData instance is
    // created with default values.
    public static PlayerData load(String username) {
        File file = new File(DATA_DIR, username + ".dat");
        PlayerData playerData = new PlayerData(username);

        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (line.startsWith("coins=")) {
                        playerData.totalCoins = Integer.parseInt(line.substring(6));
                    } else if (line.startsWith("skins=")) {
                        String[] skins = line.substring(6).split(",");
                        for (String skin : skins) {
                            playerData.unlockedSkins.add(skin);
                        }
                    }
                }
            } catch (IOException | NumberFormatException e) {
                System.out.println("Error loading player data: " + e.getMessage());
            }
        }
        return playerData;
    }
}