package org.project.pixeldash;

/**
 * Responsible for managing coin related data. Wrapper class for PlayerData to
 * simplify coin operations.
 */

public class CoinManager {

    // Store player coin data within the file, but it is unused as it was buggy.
    private static final String COIN_FILE = "coins.dat";

    //  Retrieve coins from PlayerData
    public static int getCoins() {
        return PlayerData.getInstance().getCoins(); // Retrieve coins from PlayerData
    }

    // Update player’s current coin count in PlayerData
    public static void updateCoins(int value) {
        PlayerData.getInstance().setCoins(value); // Update coins in PlayerData
    }

    // Removes all coins from player’s total by cleaning PlayerData setCoins(0)
    public static void resetCoins() {
        PlayerData.getInstance().setCoins(0); // Reset coins in PlayerData
    }
}
