package org.cis1200.pixeldash;

/**
 * The LeaderboardEntry class is a fundamental building block for managing the leaderboard
 * system. It is used by the LeaderboardManager to store and retrieve high scores for the
 * game modes (Pixel Run and Pixel Fly). Each entry encapsulates a single player's performance,
 * ensuring clarity and modularity.
 */

public class LeaderboardEntry {

    // Stores the name or nickname of the player associated with the entry.
    private final String name;

    // Stores the score achieved by the player.
    private final int score;

    // Initializes the name and score fields.
    public LeaderboardEntry(String name, int score) {
        this.name = name;
        this.score = score;
    }

    // Returns the name of the player.
    public String getName() {
        return name;
    }

    // Returns the score associated with the player.
    public int getScore() {
        return score;
    }
}
