package org.cis1200.pixeldash;

import java.awt.*;

/**
 * Coin class extends GameObj that represents coins in the game. Coin class provides functionality for
 * rendering coins as yellow circles on the screen and handling their movement within the game area.
 */

public class Coin extends GameObj {

    // Initialize new Coin object
    public Coin (int px, int py, int width, int height, int vx, int vy) {
        super(px, py, width, height, vx, vy, GameCourt.COURT_WIDTH, GameCourt.COURT_HEIGHT);
    }

    // Override draw method from GameObj. Uses Java GUI library to create graphical elements of coin object
    @Override
    public void draw(Graphics g) {
        //Draw coins as yellow circles
        g.setColor(Color.YELLOW);
        g.fillOval(px, py, width, height);
    }

    // Overrides GameObj move method to update coin’s position based on its velocity value.
    @Override
    public void move() {

        super.move(); // Call the base class's move logic

        int bottomBoundary = courtHeight - height - 95; // Subtract scoreboard height (e.g., 30 pixels)

        // Ensure the player stays within the vertical bounds of the game area
        if (py < 0) {
            py = 0; // Top boundary
        } else if (py > bottomBoundary) {
            py = bottomBoundary; // Bottom boundary
        }
    }
}
