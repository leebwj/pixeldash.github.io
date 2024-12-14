package org.cis1200.pixeldash;

import java.awt.*;

/**
 * Extends GameObj class. Provide attributes and methods for game
 * objects like position, size, movement, and boundaries. For Pixel FLy mode
 */

public class Obstacle extends GameObj {

    // Initialize new GroundObstacle object
    public Obstacle(int px, int py, int width, int height, int vx, int vy) {
        super(px, py, width, height, vx, vy, GameCourt.COURT_WIDTH, GameCourt.COURT_HEIGHT);
    }

    // Overrides GameObj move method to update obstacle’s position based on its velocity value.
    @Override
    public void draw(Graphics g) {
        // Draw obstacles as red rectangles
        g.setColor(Color.RED);
        g.fillRect(px, py, width, height);
    }

    // Overrides GameObj move method to update obstacle’s position based on its velocity value.
    @Override
    public void move() {

        super.move(); // Call the base class's move logic

        int bottomBoundary = courtHeight - height - 95;

        // Ensure the player stays within the vertical bounds of the game area
        if (py < 0) {
            py = 0; // Top boundary
        } else if (py > bottomBoundary) {
            py = bottomBoundary; // Bottom boundary
        }
    }
}