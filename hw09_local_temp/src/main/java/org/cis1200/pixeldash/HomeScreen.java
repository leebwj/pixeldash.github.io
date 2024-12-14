package org.cis1200.pixeldash;

import javax.swing.*;
import java.awt.*;

/**
 *  The HomeScreen acts as the navigation hub for the game. It is the first interface
 *  players interact with after launching the game and provides easy access to all key
 *  features. Its design ensures a clean separation between the main menu and the in-game content.
 */

public class HomeScreen extends JPanel {

    // A reference to the main JFrame of the application. This is used to update
    // the content displayed on the screen dynamically.
    private JFrame frame;

    // A JLabel that displays the player's username and the total number of coins they have collected.
    private JLabel coinStatus;

    // Holds an instance of PlayerData, which stores the player's progress and profile information.
    private PlayerData playerData;

    // Initializes the layout of the home screen using a combination of BorderLayout, FlowLayout, and GridLayout.
    public HomeScreen(JFrame frame) {
        this.frame = frame;
        this.playerData = PlayerData.getInstance();

        this.setLayout(new BorderLayout());

        // Title Panel at the top
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel title = new JLabel("PIXEL DASH");
        title.setFont(new Font("Arial", Font.BOLD, 32));
        titlePanel.add(title);
        this.add(titlePanel, BorderLayout.NORTH);

        // Center Panel for buttons and information
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(0, 1, 10, 10)); // Single column with spacing

        // Coin Status Panel
        JPanel coinStatusPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        String nickname = PlayerData.getInstance().getUsername();
        coinStatus = new JLabel("Username: " + nickname + "  ||  Total Coins: " + CoinManager.getCoins());
        coinStatus.setFont(new Font("Arial", Font.PLAIN, 16));
        coinStatusPanel.add(coinStatus);
        centerPanel.add(coinStatusPanel, BorderLayout.NORTH);

        // Game Modes Button
        JButton pixelRunButton = new JButton("Play Pixel Fly");
        JButton dinoRunButton = new JButton("Play Pixel Run");
        centerPanel.add(pixelRunButton);
        centerPanel.add(dinoRunButton);

        pixelRunButton.addActionListener(e -> startGame("Pixel Fly"));
        dinoRunButton.addActionListener(e -> startGame("Pixel Run"));

        // Additional Buttons for Skin Page, Instructions, Leaderboard, Quit, Reset
        JButton instructionsButton = new JButton("Instructions");
        JButton leaderboardButton = new JButton("Leaderboard");
        JButton skinButton = new JButton("Select Skin");
        JButton resetGameButton = new JButton("Reset Game");
        JButton quitButton = new JButton("Quit");

        centerPanel.add(instructionsButton);
        centerPanel.add(leaderboardButton);
        centerPanel.add(skinButton);
        centerPanel.add(resetGameButton);
        centerPanel.add(quitButton);

        instructionsButton.addActionListener(e -> showInstructions());
        leaderboardButton.addActionListener(e -> showLeaderboard());
        skinButton.addActionListener(e -> showSkinPage());
        resetGameButton.addActionListener(e -> resetGame());
        quitButton.addActionListener(e -> System.exit(0));

//        // FOR DEBUG Add 500 Coins Button
//        JButton addCoinsButton = new JButton("Add 500 Coins");
//        addCoinsButton.addActionListener(e -> {
//            CoinManager.updateCoins(CoinManager.getCoins() + 500);
//            refreshCoinStatus();
//        });
//        centerPanel.add(addCoinsButton);

        // Add the center panel
        this.add(centerPanel, BorderLayout.CENTER);
    }

    // Clears the current content in the frame and sets up the game interface for
    // the chosen mode (Pixel Fly or Pixel Run).
    private void startGame(String mode) {
        frame.getContentPane().removeAll();

        JPanel statusPanel = new JPanel();
        statusPanel.setBackground(Color.LIGHT_GRAY); // Distinct color for the bottom UI
        JLabel scoreLabel = new JLabel("Score: 0");
        statusPanel.add(scoreLabel);
        frame.add(statusPanel, BorderLayout.SOUTH);

        GameCourt gameCourt = new GameCourt(scoreLabel, mode);
        frame.add(gameCourt, BorderLayout.CENTER);

        JPanel controlPanel = new JPanel();
        controlPanel.setBackground(Color.LIGHT_GRAY); // Distinct color for the top UI
        JButton resetButton = new JButton("Reset");
        resetButton.addActionListener(e -> {
            gameCourt.reset();
            gameCourt.requestFocusInWindow();
        });
        controlPanel.add(resetButton);

        JButton backButton = new JButton("Go Back");
        backButton.addActionListener(e -> {
            frame.getContentPane().removeAll();
            HomeScreen home = new HomeScreen(frame);
            frame.add(home);
            frame.revalidate();
            frame.repaint();
        });
        controlPanel.add(backButton);

        frame.add(controlPanel, BorderLayout.NORTH);
        frame.revalidate();
        frame.repaint();

        gameCourt.reset();
        gameCourt.requestFocusInWindow();
    }

    // Navigates to the InstructionsPage, replacing the current content in the frame.
    private void showInstructions() {
        frame.getContentPane().removeAll();
        frame.add(new InstructionsPage(frame));
        frame.revalidate();
        frame.repaint();
    }

    // Navigates to the SkinPage, where players can select or unlock new skins.
    private void showSkinPage() {
        frame.getContentPane().removeAll();
        frame.add(new SkinPage(frame));
        frame.revalidate();
        frame.repaint();
    }

    // Updates the coinStatus label to reflect the current username and total coins,
    // ensuring the displayed information stays up-to-date.
    public void refreshCoinStatus() {
        String nickname = PlayerData.getInstance().getUsername();
        int coins = PlayerData.getInstance().getCoins();
        coinStatus.setText("Username: " + nickname + "  ||  Total Coins: " + coins);
    }

    // Displays the leaderboard for both game modes (Pixel Run and Pixel Fly) in a dialog box.
    private void showLeaderboard() {
        String pixelRunText = "<html><h1>Pixel Run Leaderboard</h1><ol>";
        for (LeaderboardEntry entry : LeaderboardManager.getPixelFlyLeaderboard()) {
            pixelRunText += "<li>" + entry.getName() + ": " + entry.getScore() + "</li>";
        }
        pixelRunText += "</ol><br><h1>Dino Run Leaderboard</h1><ol>";

        for (LeaderboardEntry entry : LeaderboardManager.getPixelRunLeaderboard()) {
            pixelRunText += "<li>" + entry.getName() + ": " + entry.getScore() + "</li>";
        }
        pixelRunText += "</ol></html>";

        JOptionPane.showMessageDialog(frame, new JLabel(pixelRunText), "Leaderboard",
                JOptionPane.INFORMATION_MESSAGE);
    }

    // Prompts the player to confirm resetting the game. If confirmed, it invokes
    // GameStateManager.resetAllGameData() to clear all player progress, leaderboard
    // data, and skin files.
    private void resetGame() {
        int confirmation = JOptionPane.showConfirmDialog(
                frame,
                "Are you sure you want to reset the entire game? This will delete all save files and reset all data.",
                "Reset Game Confirmation",
                JOptionPane.YES_NO_OPTION
        );
        if (confirmation == JOptionPane.YES_OPTION) {
            GameStateManager gameStateManager = new GameStateManager("PlayerName");
            gameStateManager.resetAllGameData();
            JOptionPane.showMessageDialog(frame, "Game has been reset. All data and files cleared.");
            refreshCoinStatus();
        }
    }
}
