package org.project.pixeldash;

import javax.swing.*;
import java.awt.*;

/**
 * The RunPixelDash class is the entry point for the game. It is responsible for initializing the main
 * game window (JFrame) and displaying the title screen before transitioning to the HomeScreen.
 * This class sets up the foundational structure for the graphical user interface (GUI) using Java Swing components.
 */

public class RunPixelDash implements Runnable {
    public void run() {

        // Create the main game frame
        JFrame frame = new JFrame("Pixel Dash");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLocation(300, 200);

        // Create the Title Screen
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BorderLayout());
        JLabel titleLabel = new JLabel("PIXEL DASH");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 48));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titlePanel.add(titleLabel, BorderLayout.CENTER);
        frame.add(titlePanel);

        frame.setVisible(true);

        // Switch to HOME screen after 3 seconds
        Timer titleTimer = new Timer(3000, e -> {
            frame.getContentPane().removeAll();

            String nickname = JOptionPane.showInputDialog(frame, "Enter your nickname:", "Nickname",
                    JOptionPane.PLAIN_MESSAGE);
            if (nickname == null || nickname.trim().isEmpty()) {
                nickname = "Player";
            }

            PlayerData.initialize(nickname); // Load or create new data for the username
            HomeScreen homeScreen = new HomeScreen(frame);
            frame.add(homeScreen);
            frame.revalidate();
            frame.repaint();
        });
        titleTimer.setRepeats(false);
        titleTimer.start();
    }
}