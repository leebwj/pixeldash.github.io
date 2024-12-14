package org.cis1200.pixeldash;

import java.awt.*;

/**
 * Extends GameObj class. Provide attributes and methods for game
 * objects like position, size, movement, and boundaries. For Pixel Run mode
 */

public class GroundObstacle extends GameObj {

    // Initialize new GroundObstacle object
    public GroundObstacle(int px, int py, int width, int height, int vx, int courtWidth, int courtHeight) {
        super(px, py, width, height, vx, 0, courtWidth, courtHeight);
    }

    // Overrides GameObj move method to update obstacle’s position based on its velocity value.
    @Override
    public void draw(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(px, py, width, height); // Represent ground obstacle as a black rectangle
    }
}