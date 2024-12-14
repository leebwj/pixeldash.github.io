package org.cis1200.pixeldash;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This class holds the primary game logic for how different objects interact
 * with one another. Take time to understand how the timer interacts with the
 * different methods and how it repaints the GUI on every tick().
 */

public class GameCourt extends JPanel {

    private Player player; // Represents the player
    private List<GameObj> objects; // Obstacles and coins
    private Timer timer; // Timer for game loop
    private JLabel status; // Displays game status or score
    private int score; // Tracks the player's score
    private boolean playing; // Indicates if the game is active
    private int speed; // Initial speed of obstacles and coins
    private int ticks; // Counter for the number of game ticks
    private JFrame frame; // Reference to main frame
    private String mode; // Represents mode of the game
    private int obstacleCooldown = 0; // Cooldown tracker for obstacles
    private int coinCooldown = 0; // Cooldown tracker for coins

    public static final int COURT_WIDTH = 800;
    public static final int COURT_HEIGHT = 600;
    public static final int GROUND_HEIGHT = 50;

    // Initialize new GameCourt with initial values
    public GameCourt(JLabel status, String mode) {
        this.status = status;
        this.mode = mode;
        this.speed = -5; // Initial obstacle speed
        this.ticks = 0; // Tick counter

        // Enable key input for player movement
        this.setFocusable(true);
        this.addKeyListener(new KeyAdapter() {

            @Override
            public void keyPressed(KeyEvent e) {
                handleKeyPress(e);
            }

            @Override
            public void keyReleased(KeyEvent e) {
                handleKeyRelease(e);
            }
        });
        timer = new Timer(35, e -> tick());
    }

    // Resets game state.
    // Creates new Player Object. Resets score, speed, list of objects.
    // Starts the game loop
    public void reset() {
        this.requestFocusInWindow();

        if ("Pixel Fly".equals(mode)) {
            player = new Player(100, 300 - 70, 50, 50, 0, 0,
                    COURT_WIDTH, COURT_HEIGHT - 90, mode);
        } else if ("Pixel Run".equals(mode)) {
            player = new Player(100, COURT_HEIGHT - 200, 50, 50, 0, 0,
                    COURT_WIDTH, COURT_HEIGHT, mode);
        }

        player.setColor(SkinManager.getSelectedSkin().getColor());

        objects = new ArrayList<>();
        score = 0;
        speed = -5;
        playing = true;
        status.setText("Score: " + score + " | Speed: " + Math.abs(speed));
        timer.start();
    }

    // Called periodically by Timer to update game state
    // Move player and objects, remove off-screen objects, spawn new objects, check for collision
    // Gradually increase speed
    private void tick() {
        if (!playing) {
            timer.stop();
            return;
        }

        // Move player
        player.move();

        // move all objects (obstacles and coins) and remove off-screen objects
        for (GameObj obj : objects) {
            obj.move();
        }
        objects.removeIf(obj -> obj.getPx() < 0);

        // Increment tick counter
        ticks++;

        // Gradually increase speed every 200 ticks
        if (ticks % 200 == 0 && "Pixel Fly".equals(mode)) {
            speed = (int) (speed * 1.2); // Exponentially increase speed
        } else if (ticks % 150 == 0 && "Pixel Run".equals(mode)) {
            speed = (int) (speed * 1.25);
        }

        // Spawn objects based on the game mode
        if ("Pixel Fly".equals(mode)) {
            spawnPixelFlyObjects();
        } else if ("Pixel Run".equals(mode)) {
            spawnPixelRunObjects();
        }

        // Check for collisions
        checkCollisions();
        // Update the Display
        repaint();
    }

    // Spawns coins and obstacles at random vertical positions with specified cooldown intervals.
    private void spawnPixelFlyObjects() {
        obstacleCooldown--;
        coinCooldown--;

        if (obstacleCooldown <= 0) {
            int obstacleY = randomY();
            boolean collisionFree = true;
            for (GameObj obj : objects) {
                if (Math.abs(obj.getPy() - obstacleY) < 40) {
                    collisionFree = false;
                    break;
                }
            }
            if (collisionFree) {
                addGameObj(new Obstacle(COURT_WIDTH, obstacleY, 30, 30, speed, 0));
                obstacleCooldown = Math.max(10, 30 - Math.abs(speed * 2));
            }
        }

        if (coinCooldown <= 0) {
            int coinY = randomY();
            boolean collisionFree = true;
            for (GameObj obj : objects) {
                if (Math.abs(obj.getPy() - coinY) < 40) {
                    collisionFree = false;
                    break;
                }
            }
            if (collisionFree) {
                addGameObj(new Coin(COURT_WIDTH, coinY, 20, 20, speed, 0));
                coinCooldown = Math.max(10, 20 - Math.abs(speed * 2));
            }
        }
    }

    // Spawns GroundObstacle or FlyingRocket objects with a 50% probability each.
    // Spawns coins at fixed vertical levels to align with ground and airborne paths.
    private void spawnPixelRunObjects() {
        obstacleCooldown--;
        coinCooldown--;

        if (obstacleCooldown <= 0) {
            if (Math.random() < 0.5) {
                addGameObj(new GroundObstacle(
                        COURT_WIDTH,
                        COURT_HEIGHT - GROUND_HEIGHT - 70,
                        30,
                        30,
                        speed,
                        COURT_WIDTH,
                        COURT_HEIGHT
                ));
            } else {
                addGameObj(new FlyingRocket(
                        COURT_WIDTH,
                        COURT_HEIGHT - GROUND_HEIGHT - 100,
                        40,
                        20,
                        speed,
                        COURT_WIDTH,
                        COURT_HEIGHT
                ));
            }
            obstacleCooldown = Math.max(60, 180 - Math.abs(speed * 9));
        }

        if (coinCooldown <= 0) {
            int coinY;
            if (Math.random() < 0.5) {
                coinY = COURT_HEIGHT - GROUND_HEIGHT - 30;
            } else {
                coinY = COURT_HEIGHT - GROUND_HEIGHT - 80;
            }
            boolean collisionFree = true;
            for (GameObj obj : objects) {
                if (Math.abs(obj.getPy() - coinY) < 40) {
                    collisionFree = false;
                    break;
                }
            }
            if (collisionFree) {
                addGameObj(new Coin(COURT_WIDTH, coinY, 20, 20, speed, 0));
                coinCooldown = Math.max(40, 120 - Math.abs(speed * 7));
            }
        }
    }

    // Adds a new object to the game only if it does not overlap with existing objects.
    private void addGameObj(GameObj obj) {
        for (GameObj existing : objects) {
            if (existing.intersects(obj)) {
                return; // Avoid adding overlapping objects
            }
        }
        objects.add(obj);
    }

    // Checks if the player intersects any object in the game.
    private void checkCollisions() {
        for (GameObj obj : objects) {
            if (player.intersects(obj)) {
                if (obj instanceof Coin) {
                    score++;
                    CoinManager.updateCoins(CoinManager.getCoins() + 1);
                    status.setText("Score: " + score + " | Speed: " + Math.abs(speed));
                    objects.remove(obj);
                    break;
                } else if (obj instanceof Obstacle || obj instanceof GroundObstacle
                        || obj instanceof FlyingRocket) {
                    playing = false;
                    status.setText("Game Over! Final Score: " + score);
                    notifyHighScore(); // Check and notify if this is a high score
                    LeaderboardManager.addEntry(PlayerData.getInstance().getUsername(), score, mode);
                    return;
                }
            }
        }
    }

    // Respond to keyboard input for moving the player (Pixel Fly mode) or jumping/sliding (Pixel Run mode).
    private void handleKeyPress(KeyEvent e) {
        if ("Pixel Fly".equals(mode)) {
            // Movement logic for Pixel Run
            if (e.getKeyCode() == KeyEvent.VK_UP) {
                player.setVy(-5);
            } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                player.setVy(5);
            } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                player.setVx(-5);
            } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                player.setVx(5);
            }
        } else if ("Pixel Run".equals(mode)) {
            // Movement logic for Dino Run
            if (e.getKeyCode() == KeyEvent.VK_UP) {
                player.jump();
            } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                player.slide();
            }
            if (e.getKeyCode() == KeyEvent.VK_DOWN && player.isJumping()) {
                player.forceDescend();
            }
        }
    }

    // Respond to keyboard release for moving the player (Pixel Fly mode) or jumping/sliding (Pixel Run mode)
    private void handleKeyRelease(KeyEvent e) {
        if ("Pixel Fly".equals(mode)) {
            if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_DOWN) {
                player.setVy(0);
            } else if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_RIGHT) {
                player.setVx(0);
            }
        } else if ("Pixel Run".equals(mode)) {
            if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                player.stopSliding();
            }
        }
    }

    // Generates random vertical positions for objects within the game bounds.
    private int randomY() {
        // Generate a random y coordinate within bounds
        return (int) (Math.random() * (COURT_HEIGHT));
    }

    // Checks if the player's score qualifies as a high score and notifies them via a dialog box.
    private void notifyHighScore() {
        List<LeaderboardEntry> leaderboard;
        if (mode.equals("Pixel Run")) {
            leaderboard = LeaderboardManager.getPixelRunLeaderboard();
        } else {
            leaderboard = LeaderboardManager.getPixelFlyLeaderboard();
        }

        if (leaderboard.isEmpty() || score > leaderboard.get(0).getScore()) {
            JOptionPane.showMessageDialog(
                    frame,
                    "Congratulations! You reached a new high score: " + score,
                    "High Score Achieved!",
                    JOptionPane.INFORMATION_MESSAGE
            );
        }
    }

    // Draw objects and player
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw the player
        player.draw(g);

        // Draw all game objects
        for (GameObj obj : objects) {
            obj.draw(g);
        }
    }

    // Set dimension for game area
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(COURT_WIDTH, COURT_HEIGHT); // Ensure consistent game area size
    }
}