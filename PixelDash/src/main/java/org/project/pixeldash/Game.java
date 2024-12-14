package org.project.pixeldash;

import javax.swing.*;

/**
 * Main method run to start and run the game. Initializes the runnable game
 * class of your choosing and runs it. IMPORTANT: Do NOT delete! You MUST
 * include a main method in your final submission.
 */

public class Game {

    public static void main(String[] args) {
        // Set the game you want to run here
        Runnable game = new RunPixelDash();
        SwingUtilities.invokeLater(game);
    }
}
