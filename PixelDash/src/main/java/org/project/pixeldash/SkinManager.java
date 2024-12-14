package org.project.pixeldash;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The SkinManager class handles all operations related to managing skins in the game, including initialization,
 * selection, unlocking, and persistence. It acts as a centralized controller for skin-related functionality.
 */

public class SkinManager {

    // Specifies the default filename for saving skin data.
    private static final String SKIN_FILE = "skins.txt";
    // A list of Skin objects representing all available skins.
    private static Skin selectedSkin = new Skin("Default", Color.BLUE, 0, true);
    // Tracks the currently selected skin.
    private static List<Skin> skins = new ArrayList<>();

    // Initializes the list of available skins with predefined attributes (e.g., name, color, cost, and unlock status).
    public static void initializeSkins() {
        if (skins.isEmpty()) {
            skins.add(new Skin("Default", Color.BLUE, 0, true));
            skins.add(new Skin("Green", Color.GREEN, 10, false));
            skins.add(new Skin("Purple", Color.MAGENTA, 25, false));
            skins.add(new Skin("Pink", Color.PINK, 50, false));
            skins.add(new Skin("Black", Color.BLACK, 100, false));
            skins.add(new Skin("Yellow", Color.YELLOW, 150, false));

            loadSkins();
        }
    }

    // Check if the skin is initialized
    private static void ensureSkinsInitialized() {
        if (skins.isEmpty()) {
            initializeSkins();
        }
    }

    // Returns the currently selected skin after ensuring skins are initialized.
    public static Skin getSelectedSkin() {
        ensureSkinsInitialized();
        return selectedSkin;
    }

    // Sets the currently selected skin and saves the state.
    public static void setSelectedSkin(Skin skin) {
        ensureSkinsInitialized();
        selectedSkin = skin;
        saveSkins();
    }

    // Return initialized skins
    public static List<Skin> getSkins() {
        ensureSkinsInitialized();
        return skins;
    }

    // Unlocks a specified skin by name. Updates both the in-memory state and the player's save file through PlayerData.
    public static void unlockSkin(String skinName) {
        ensureSkinsInitialized();
        for (Skin skin : skins) {
            if (skin.getName().equals(skinName)) {
                skin.unlock();
                PlayerData.getInstance().unlockSkin(skinName); // Update PlayerData
                saveSkins();
                break;
            }
        }
    }

    // Checks if a skin is unlocked by querying the player's save file via PlayerData.
    public static boolean isSkinUnlocked(String skinName) {
        ensureSkinsInitialized();
        return PlayerData.getInstance().isSkinUnlocked(skinName);
    }

    // Writes the current state of all skins and the selected skin to a file. This ensures persistence
    // across game sessions.
    public static void saveSkins() {
        ensureSkinsInitialized();
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(getSkinFile()))) {
            for (Skin skin : skins) {
                bw.write(skin.getName() + "," + skin.isUnlocked());
                bw.newLine();
            }
            bw.write("Selected Skin," + selectedSkin.getName());
        } catch (IOException e) {
            System.out.println("Error saving skins: " + e.getMessage());
        }
    }

    // Reads the saved skin state from the file, updating the in-memory list and selected skin.
    public static void loadSkins() {
        ensureSkinsInitialized();
        File skinFile = getSkinFile();
        if (!skinFile.exists()) {
            return; // Skip loading if no file exists
        }

        try (BufferedReader br = new BufferedReader(new FileReader(skinFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[0].equals("Selected Skin")) {
                    for (Skin skin : skins) {
                        if (skin.getName().equals(parts[1])) {
                            selectedSkin = skin;
                        }
                    }
                } else {
                    for (Skin skin : skins) {
                        if (skin.getName().equals(parts[0])) {
                            skin.unlock(Boolean.parseBoolean(parts[1]));
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading skins: " + e.getMessage());
        }
    }

    // Resets all skins to their initial state, unlocking only the default skin and saving the changes.
    public static void resetSkins() {
        ensureSkinsInitialized();
        for (Skin skin : skins) {
            skin.unlock(skin.getCost() == 0); // Only the default skin is unlocked
        }
        selectedSkin = skins.get(0); // Reset to the default skin
        saveSkins();
    }

    // Get skin data for current player
    private static File getSkinFile() {
        String username = PlayerData.getInstance().getUsername();
        return new File("skins_" + username + ".txt");
    }
}
