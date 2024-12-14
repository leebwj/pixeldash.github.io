package org.project.pixeldash;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * The SkinPage class represents the GUI page where players can view, select, and unlock skins.
 * It provides an intuitive interface for interacting with the game's skin customization system.
 */

public class SkinPage extends JPanel {

    private final JFrame frame;

    // Constructor for SkinPage
    public SkinPage(JFrame frame) {
        this.frame = frame;
        this.setLayout(new BorderLayout());

        // Title section. Adds a JLabel ("Select Your Skin") in the top section using FlowLayout.
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel title = new JLabel("Select Your Skin");
        title.setFont(new Font("Arial", Font.BOLD, 24));
        titlePanel.add(title);
        this.add(titlePanel, BorderLayout.NORTH);

        // Skins section
        JPanel skinsPanel = new JPanel(new GridLayout(2, 3, 30, 30)); // 3 columns, 2 rows, spacing
        skinsPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50)); // Padding around grid

        List<Skin> skins = SkinManager.getSkins();
        for (Skin skin : skins) {
            JPanel skinPanel = new JPanel(new BorderLayout());

            // Skin color preview
            JPanel colorPreview = new JPanel();
            colorPreview.setBackground(skin.getColor());
            colorPreview.setPreferredSize(new Dimension(100, 100));
            skinPanel.add(colorPreview, BorderLayout.CENTER);

            // Skin info and button
            JLabel nameLabel = new JLabel(skin.getName(), JLabel.CENTER);
            nameLabel.setFont(new Font("Arial", Font.PLAIN, 16));

            JLabel costLabel;
            if (skin.isUnlocked()) {
                costLabel = new JLabel("Unlocked", JLabel.CENTER);
            } else {
                costLabel = new JLabel("Locked (Cost: " + skin.getCost() + " coins)", JLabel.CENTER);
            }
            costLabel.setFont(new Font("Arial", Font.PLAIN, 12));

            JButton actionButton;
            if (skin.isUnlocked()) {
                actionButton = new JButton("Select");
                actionButton.addActionListener(e -> {
                    SkinManager.setSelectedSkin(skin);
                    JOptionPane.showMessageDialog(
                            frame,
                            "Skin \"" + skin.getName() + "\" selected!",
                            "Skin Selected",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                });
            } else {
                actionButton = new JButton("Unlock");
                actionButton.addActionListener(e -> {
                    int coins = CoinManager.getCoins();
                    if (coins >= skin.getCost()) {
                        CoinManager.updateCoins(coins - skin.getCost());
                        SkinManager.unlockSkin(skin.getName());
                        JOptionPane.showMessageDialog(
                                frame,
                                "Skin \"" + skin.getName() + "\" unlocked!",
                                "Skin Unlocked",
                                JOptionPane.INFORMATION_MESSAGE
                        );
                        refreshSkinPage(); // Refresh the UI
                    } else {
                        JOptionPane.showMessageDialog(
                                frame,
                                "Not enough coins to unlock this skin!",
                                "Unlock Failed",
                                JOptionPane.ERROR_MESSAGE
                        );
                    }
                });
            }

            // Add components to skinPanel
            JPanel infoPanel = new JPanel(new GridLayout(2, 1));
            infoPanel.add(nameLabel);
            infoPanel.add(costLabel);

            skinPanel.add(infoPanel, BorderLayout.NORTH);
            skinPanel.add(actionButton, BorderLayout.SOUTH);

            skinsPanel.add(skinPanel);
        }

        this.add(skinsPanel, BorderLayout.CENTER);

        // Back button section
        JPanel backPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton backButton = new JButton("Go Back");
        backButton.addActionListener(e -> {
            frame.getContentPane().removeAll();
            frame.add(new HomeScreen(frame));
            frame.revalidate();
            frame.repaint();
        });
        backPanel.add(backButton);
        this.add(backPanel, BorderLayout.SOUTH);
    }

    private void refreshSkinPage() {
        frame.getContentPane().removeAll();
        frame.add(new SkinPage(frame));
        frame.revalidate();
        frame.repaint();
    }
}