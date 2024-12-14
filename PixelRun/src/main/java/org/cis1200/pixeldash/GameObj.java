package org.cis1200.pixeldash;

import java.awt.*;

/**
 * An object in the game.
 *
 * Game objects exist in the game court. They have a position, velocity, size
 * and bounds. Their velocity controls how they move; their position should
 * always be within their bounds.
 */

public abstract class GameObj {

    protected int px, py; // Position
    protected int width, height; // Size
    protected int vx, vy; // Velocity
    protected int courtWidth, courtHeight; // Game area bounds

    // Initializes all fields to set up the object with its position, size, velocity, and game boundaries.
    public GameObj(int px, int py, int width, int height, int vx, int vy, int courtWidth, int courtHeight) {
        this.px = px;
        this.py = py;
        this.width = width;
        this.height = height;
        this.vx = vx;
        this.vy = vy;
        this.courtWidth = courtWidth;
        this.courtHeight = courtHeight;
    }

    // Updates the position of the object (px and py) based on its velocity (vx and vy).
    public void move() {
        px += vx; // update horizontal position
        py += vy; // Update vertical position
    }

    // Checks for collisions between this object and another GameObj.
    public boolean intersects(GameObj other) {
        // Check for collision with another object
        return px < other.px + other.width && px + width > other.px &&
                py < other.py + other.height && py + height > other.py;
    }

    // Abstract method for rendering objects
    public abstract void draw(Graphics g);

    // Getters and Setters:
    public int getPx() {
        return px;
    }

    public int getPy() {
        return py;
    }

    public void setVx(int i) {
        this.vx = vx;
    }
}