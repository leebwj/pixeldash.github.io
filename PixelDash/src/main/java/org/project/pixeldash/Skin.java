package org.project.pixeldash;

import java.awt.*;

/**
 * The Skin class is responsible for representing a player's customizable skin in the game.
 * It encapsulates attributes such as the skin's name, color, cost, and unlock status, providing
 * a modular approach to managing skins.
 */

public class Skin {

    // Represents the name of the skin, used for display purposes and identification.
    private final String name;
    // Specifies the visual appearance of the skin using the Color class.
    private final Color color;
    // Indicates the number of coins required to unlock the skin.
    private final int cost;
    // A boolean flag that tracks whether the skin is available for use. It ensures the game respects unlock conditions.
    private boolean unlocked;

    // Initializes all attributes, allowing for flexible creation of skins with specific names,
    // colors, costs, and unlock states.
    public Skin(String name, Color color, int cost, boolean unlocked) {
        this.name = name;
        this.color = color;
        this.cost = cost;
        this.unlocked = unlocked;
    }

    // Return name value
    public String getName() {
        return name;
    }

    // Return color value
    public Color getColor() {
        return color;
    }

    // Get cost value data
    public int getCost() {
        return cost;
    }

    // Return boolean state whether skin is unlocked
    public boolean isUnlocked() {
        return unlocked;
    }

    // Provides a method to set the unlock status explicitly, supporting flexible update logic.
    public void unlock(boolean b) {
        this.unlocked = b;
    }

    // A convenience method that sets the unlock status to true, simplifying cases where a skin is
    // unlocked unconditionally.
    public void unlock() {
        this.unlocked = true;
    }


}
