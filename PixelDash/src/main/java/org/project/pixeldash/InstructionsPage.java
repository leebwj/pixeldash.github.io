package org.project.pixeldash;

import javax.swing.*;
import java.awt.*;

/**
 * Create and initialize instructions page. Include contents related to the instructions regarding game modes.
 */

public class InstructionsPage extends JPanel{

        // The constructor initializes the layout and content of the instructions page:
        public InstructionsPage(JFrame frame) {
            this.setLayout(new BorderLayout());

            // A JLabel with HTML formatting is used to present the game instructions in a
            // structured and visually appealing format. The instructions are categorized into sections for:
            JLabel instructions = new JLabel(
                    "<html><h1>Game Instructions</h1>" +
                            "<h2>Pixel Fly</h2>" +
                            "<ul>" +
                            "<li>Use arrow keys to move UP or DOWN.</li>" +
                            "<li>Avoid obstacles and collect coins to increase your score.</li>" +
                            "</ul>" +
                            "<h2>Pixel Run</h2>" +
                            "<ul>" +
                            "<li>Press UP to jump and DOWN to come down faster while in the air.</li>" +
                            "<li>Avoid ground and flying obstacles. Coins are placed randomly.</li>" +
                            "</ul>" +
                            "<h2>Leaderboards</h2>" +
                            "<p>The game maintains separate leaderboards for Pixel Fly and Pixel Run.</p>" +
                            "</html>");
            instructions.setHorizontalAlignment(SwingConstants.CENTER);

            // Allows the player to return to the HomeScreen by removing the current
            // content and adding a new HomeScreen instance to the frame.
            JButton backButton = new JButton("Go Back");
            backButton.addActionListener(e -> {
                frame.getContentPane().removeAll();
                frame.add(new HomeScreen(frame));
                frame.revalidate();
                frame.repaint();
            });

            this.add(instructions, BorderLayout.CENTER);
            this.add(backButton, BorderLayout.SOUTH);
    }
}
