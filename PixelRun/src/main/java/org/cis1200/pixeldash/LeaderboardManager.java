package org.cis1200.pixeldash;

import java.io.*;
import java.util.*;

/**
 * The LeaderboardManager centralizes all leaderboard-related functionality, including:
 * Maintaining separate leaderboards for the two game modes.
 * Persisting leaderboard data across game sessions.
 * Providing methods to add new entries, retrieve leaderboards, and clear all data.
 */

public class LeaderboardManager {

    // File where leaderboard data for the "Pixel Run" mode is stored.
    private static final String PIXEL_FLY_FILE = "pixel_fly_leaderboard.txt";
    // File where leaderboard data for the "Dino Run" mode is stored.
    private static final String PIXEL_RUN_FILE = "pixel_run_leaderboard.txt";
    // Limits the maximum number of entries that can be stored in a leaderboard to 10.
    private static final int MAX_ENTRIES = 10;
    // Lists of LeaderboardEntry objects to store leaderboard data for the respective game modes
    private static List<LeaderboardEntry> pixelFlyLeaderboard = new ArrayList<>();
    private static List<LeaderboardEntry> pixelRunLeaderboard = new ArrayList<>();

    // Ensures that the leaderboards are properly loaded into memory before any operations are performed.
    private static void ensureLeaderboardsInitialized() {
        if (pixelFlyLeaderboard == null || pixelRunLeaderboard == null) {
            pixelFlyLeaderboard = loadLeaderboardFromFile(PIXEL_FLY_FILE);
            pixelRunLeaderboard = loadLeaderboardFromFile(PIXEL_RUN_FILE);
        }
    }

    // Add a leaderboard entry based on mode
    public static void addEntry(String name, int score, String mode) {
        ensureLeaderboardsInitialized();

        List<LeaderboardEntry> targetLeaderboard = "Pixel Run".equals(mode) ? pixelRunLeaderboard : pixelFlyLeaderboard;

        targetLeaderboard.add(new LeaderboardEntry(name, score));
        targetLeaderboard.sort((e1, e2) -> e2.getScore() - e1.getScore());
        if (targetLeaderboard.size() > MAX_ENTRIES) {
            targetLeaderboard.remove(MAX_ENTRIES);
        }
        saveLeaderboards();
    }

    // Get Pixel Run leaderboard
    public static List<LeaderboardEntry> getPixelFlyLeaderboard() {
        ensureLeaderboardsInitialized();
        return new ArrayList<>(pixelFlyLeaderboard); // Return a copy
    }

    // Get Pixel Run leaderboard
    public static List<LeaderboardEntry> getPixelRunLeaderboard() {
        ensureLeaderboardsInitialized();
        return new ArrayList<>(pixelRunLeaderboard); // Return a copy
    }

    // Save both leaderboards to their respective files
    private static void saveLeaderboards() {
        ensureLeaderboardsInitialized();
        saveLeaderboardToFile(PIXEL_FLY_FILE, pixelFlyLeaderboard);
        saveLeaderboardToFile(PIXEL_RUN_FILE, pixelRunLeaderboard);
    }

    // Writes the leaderboard entries to the specified file, one entry per line in the format name, score.
    private static void saveLeaderboardToFile(String fileName, List<LeaderboardEntry> leaderboard) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
            for (LeaderboardEntry entry : leaderboard) {
                bw.write(entry.getName() + "," + entry.getScore());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving leaderboard: " + e.getMessage());
        }
    }

    // Reads leaderboard data from the specified file and populates a List<LeaderboardEntry>.
    private static List<LeaderboardEntry> loadLeaderboardFromFile(String fileName) {
        List<LeaderboardEntry> leaderboard = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    leaderboard.add(new LeaderboardEntry(parts[0], Integer.parseInt(parts[1])));
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading leaderboard from " + fileName + ": " + e.getMessage());
        }
        return leaderboard;
    }
}


