package org.project.pixeldash;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * JUnit tests for SkinManager and SkinPage functionality.
 */
public class PixelDashTest {

    private SkinManager skinManager;

    @BeforeEach
    public void setUp() {
        SkinManager.initializeSkins();
    }

    @Test
    public void testDefaultSkinIsUnlocked() {
        List<Skin> skins = SkinManager.getSkins();
        assertNotNull(skins);
        assertEquals("Default", skins.get(0).getName());
        assertTrue(skins.get(0).isUnlocked());
    }

    @Test
    public void testUnlockingSkinWithSufficientCoins() {
        // Simulate having enough coins
        CoinManager.updateCoins(50);

        assertFalse(SkinManager.isSkinUnlocked("Green")); // Initially locked
        SkinManager.unlockSkin("Green");

        assertTrue(SkinManager.isSkinUnlocked("Green"));
        assertEquals(40, CoinManager.getCoins()); // Coins deducted
    }

    @Test
    public void testUnlockingSkinWithoutSufficientCoins() {
        // Simulate insufficient coins
        CoinManager.updateCoins(5);

        assertFalse(SkinManager.isSkinUnlocked("Green")); // Initially locked
        SkinManager.unlockSkin("Green");

        // Skin remains locked
        assertFalse(SkinManager.isSkinUnlocked("Green"));
        assertEquals(5, CoinManager.getCoins()); // Coins unchanged
    }

    @Test
    public void testSelectingUnlockedSkin() {
        SkinManager.unlockSkin("Purple");
        SkinManager.setSelectedSkin(SkinManager.getSkins().get(2)); // Assuming Purple is index 2
        assertEquals("Purple", SkinManager.getSelectedSkin().getName());
    }

    @Test
    public void testCannotSelectLockedSkin() {
        assertFalse(SkinManager.isSkinUnlocked("Black")); // Initially locked
        SkinManager.setSelectedSkin(SkinManager.getSkins().get(4)); // Assuming Black is index 4

        // Default skin remains selected since Black is locked
        assertEquals("Default", SkinManager.getSelectedSkin().getName());
    }

    @Test
    public void testCoinsAreDeductedProperly() {
        CoinManager.updateCoins(150);

        SkinManager.unlockSkin("Yellow");
        assertTrue(SkinManager.isSkinUnlocked("Yellow"));

        assertEquals(0, CoinManager.getCoins());
    }

    @Test
    public void testSkinPageUIComponents() {
        JFrame frame = new JFrame();
        SkinPage skinPage = new SkinPage(frame);

        // Check if the title label exists
        Component[] components = skinPage.getComponents();
        boolean foundTitle = false;
        for (Component component : components) {
            if (component instanceof JLabel && ((JLabel) component).getText().equals("Select Your Skin")) {
                foundTitle = true;
                break;
            }
        }
        assertTrue(foundTitle, "Title label not found in SkinPage.");
    }

    @Test
    public void testSkinPageButtons() {
        JFrame frame = new JFrame();
        SkinPage skinPage = new SkinPage(frame);

        // Check if all action buttons exist for skins
        List<Skin> skins = SkinManager.getSkins();
        for (Skin skin : skins) {
            boolean buttonExists = false;
            for (Component component : skinPage.getComponents()) {
                if (component instanceof JButton) {
                    JButton button = (JButton) component;
                    if (button.getText().equals(skin.isUnlocked() ? "Select" : "Unlock")) {
                        buttonExists = true;
                        break;
                    }
                }
            }
            assertTrue(buttonExists, "Button for skin '" + skin.getName() + "' not found.");
        }
    }

    @Test
    public void testGoBackButtonUI() {
        JFrame frame = new JFrame();
        SkinPage skinPage = new SkinPage(frame);

        // Check if Go Back button exists
        boolean foundGoBack = false;
        for (Component component : skinPage.getComponents()) {
            if (component instanceof JButton && ((JButton) component).getText().equals("Go Back")) {
                foundGoBack = true;
                break;
            }
        }
        assertTrue(foundGoBack, "Go Back button not found in SkinPage.");
    }
}
