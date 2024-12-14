package org.project.pixeldash;

import java.awt.*;

/**
 * A basic game object starting in the upper left corner of the game court. It
 * is displayed as a square of a specified color.
 */

public class Player extends GameObj {

    private Color color; // Add a color field
    private boolean isJumping; // Check if player is jumping
    private boolean isSliding; // Check if player is sliding
    private int originalHeight; // Set original height of player
    private int groundLevel; // Set ground level boundary
    private int score; // Set player's score
    private String mode; // Set which mode player selected to play


    // Initializes Player on the game screen, depending on the game mode selected
    public Player(int px, int py, int width, int height, int vx, int vy, int courtWidth, int courtHeight, String mode) {
        super(px, py, width, height, vx, vy, courtWidth, courtHeight);

        if ("Pixel Run".equals(mode)) {
            this.groundLevel = courtHeight - height - 90; // Dino Run
        } else {
            this.groundLevel = courtHeight; // Pixel Run (default free movement)
        }

        this.originalHeight = height;
        this.score = 0;
        this.mode = mode;
    }

    // Overrides the abstract method in GameObj to render the player on the screen as a
    // rectangle with the current color.
    @Override
    public void draw(Graphics g) {
        g.setColor(color);
        g.fillRect(px, py, width, height);
    }

    // Implements game-specific movement logic
    @Override
    public void move() {

        // Pixel run: Handles jumping and sliding mechanics, including gravity simulation and ground-level restrictions.
        if ("Pixel Run".equals(mode)) {
            if (isJumping) {
                vy += 1; // Simulate gravity
                py += vy;

                if (py >= groundLevel) {
                    py = groundLevel;
                    vy = 0;
                    isJumping = false;
                }
            } else if (isSliding) {
                height = originalHeight / 2;
                py = groundLevel + originalHeight / 2;
            } else {
                height = originalHeight;
                py = groundLevel;
            }
            // Pixel Fly: Allows free movement within the screen boundaries.
        } else if ("Pixel Fly".equals(mode)) {
            px += vx; // Update horizontal position
            py += vy; // Update vertical position

            // Ensure the player doesn't move off the screen horizontally
            if (px < 0) {
                px = 0;
            } else if (px + width > courtWidth) {
                px = courtWidth - width;
            }

            // Ensure the player doesn't move off the screen vertically
            if (py < 0) { // Top boundary with a slight margin
                py = 0;
            } else if (py + height > courtHeight) { // Bottom boundary
                py = courtHeight - height;
            }
        }
    }

    // Initiates a jump if the player is neither already jumping nor sliding.
    public void jump() {
        if (!isJumping && !isSliding) {
            vy = -15;
            isJumping = true;
        }
    }

    // Enables sliding by reducing the player's height and adjusting the position (py).
    public void slide() {
        if (!isJumping) {
            isSliding = true;
        }
    }

    // Stops sliding and restores the player's original height and position.
    public void stopSliding() {
        isSliding = false;
    }

    // Updates the vertical velocity (vy), used for both "Pixel Run" and "Pixel Fly" modes.
    public void setVy(int vy) {
        this.vy = vy;
    }

    // Sets the player's color, typically based on the selected skin.
    public void setColor(Color color) {
        this.color = color;
    }

    // Accelerates the player's descent during a jump (used when the player presses the "down" key in mid-air).
    public void forceDescend() {
        if (isJumping) {
            vy = Math.max(vy, 10);
        }
    }

    // Returns the current state of the isJumping flag.
    public boolean isJumping() {
        return isJumping;
    }
}
