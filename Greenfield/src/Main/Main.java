package Main;

import Tile.TileManager;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import Main.Interactables.Items.*;
import Main.Interactables.Obstacles.*;


// Main class to be compiled into .jar.
public class Main extends JPanel {

    // Initialise essential 3 - map, player and game timer.
    // The timer tracks the game time as well as allowing for game frames to be redrawn in sync.
    private final Map map;
    private final Player player;
    private Timer timer;

    
    // Initialise items and interactables.
    private final Key key;
    private final Door door;
    private boolean keyPickedUp = false;

    // Logic for the game time - 5 minutes, if the timer runs out, terminate and issue message.
    private final long gameTime = 5 * 60 * 1000L; 
    private long endTimeMillis;
    private boolean timeUp = false;

    //my screen things
    final int originalTileSize = 32; // 32x32 tile
    final int scale = 2;

    public final int tileSize = originalTileSize * scale; // 64x64 tile
    final int maxScreenCol = 16;
    final int maxScreenRow = 10;
    final int screenWidth = tileSize * maxScreenCol; // 1024 pixels
    final int screenHeight = tileSize * maxScreenRow; // 640 pixels

    TileManager tileM = new TileManager(this);

    public static int returnTileSize() { return 64; }

    /**
     * Constructor for the main executable class. 
     */
    public Main() {
        // Set the dimensions of the game to correspond with the game's arena.
        setPreferredSize(new Dimension(screenWidth, screenHeight));
        setBackground(Color.BLACK);
        setFocusable(true);

        // Initialise map and player.
        map = new Map();
        player = new Player(0, 0);

        // Initialise items and interactables.
        key = new Key("Golden Key");
        door = new Door(new Key("Golden Key"));

        // Calls the setupKeyBindings method.
        setupKeyBindings();

        // Initialize countdown
        endTimeMillis = System.currentTimeMillis() + gameTime;

        timer = new Timer(4, e -> {
            if (!timeUp) {
                long remaining = endTimeMillis - System.currentTimeMillis();
                if (remaining <= 0) {
                    // Time is up: stop game updates and schedule exit after showing message
                    timeUp = true;
                    timer.stop();
                    Timer exitTimer = new Timer(3000, ev -> System.exit(0));
                    exitTimer.setRepeats(false);
                    exitTimer.start();
                } else {
                    player.update(map);
                    checkInteractions();
                }
            }
            // Repaints all components actively.
            repaint();
        });
        timer.start();
    }

    /**
     * Sets up keyboard input mappings for player movement controls using WASD keys.
     * This method configures both key press and release events for each direction:
     * - W: Up movement
     * - S: Down movement
     * - A: Left movement  
     * - D: Right movement
     * 
     * The method uses InputMap and ActionMap to bind keyboard events to player movement actions.
     * When a key is pressed, the corresponding direction is set to true in the player object.
     * When a key is released, the corresponding direction is set to false.
     */
    private void setupKeyBindings() {
        getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("W"), "upPressed");
        getActionMap().put("upPressed", new AbstractAction() {
            public void actionPerformed(ActionEvent e) { player.setUp(true); }
        });

        getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("released W"), "upReleased");
        getActionMap().put("upReleased", new AbstractAction() {
            public void actionPerformed(ActionEvent e) { player.setUp(false); }
        });

        getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("S"), "downPressed");
        getActionMap().put("downPressed", new AbstractAction() {
            public void actionPerformed(ActionEvent e) { player.setDown(true); }
        });

        getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("released S"), "downReleased");
        getActionMap().put("downReleased", new AbstractAction() {
            public void actionPerformed(ActionEvent e) { player.setDown(false); }
        });

        getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("A"), "leftPressed");
        getActionMap().put("leftPressed", new AbstractAction() {
            public void actionPerformed(ActionEvent e) { player.setLeft(true); }
        });

        getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("released A"), "leftReleased");
        getActionMap().put("leftReleased", new AbstractAction() {
            public void actionPerformed(ActionEvent e) { player.setLeft(false); }
        });

        getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("D"), "rightPressed");
        getActionMap().put("rightPressed", new AbstractAction() {
            public void actionPerformed(ActionEvent e) { player.setRight(true); }
        });

        getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("released D"), "rightReleased");
        getActionMap().put("rightReleased", new AbstractAction() {
            public void actionPerformed(ActionEvent e) { player.setRight(false); }
        });
    }

    
    /**
     * TODO: NPC interactions, Fresher's flu affect.
     * Checks the interactions of player with key and door.
     * If player intersects key location, delete from map and add to inventory.
     */
    private void checkInteractions() {
        int pd = player.getDiameter();
        Rectangle playerBounds = new Rectangle(player.getX(), player.getY(), pd, pd);

        // Key position
        final int keyW = 16, keyH = 16;
        final int keyX = 250, keyY = 250;
        Rectangle keyBounds = new Rectangle(keyX, keyY, keyW, keyH);

        if (!keyPickedUp && playerBounds.intersects(keyBounds)) {
            player.getInventory().add(key);
            keyPickedUp = true;
        }

        final int doorW = 64, doorH = 64;
        final int doorX = screenWidth - doorW; 
        final int doorY = screenHeight / 2 - doorH / 2;
        Rectangle doorBounds = new Rectangle(doorX, doorY, doorW, doorH);

        if (playerBounds.intersects(doorBounds)) {
            boolean hasKey = player.getInventory().getItems().contains(key);
            if (hasKey) {
                if (door.isLocked()) {
                    door.unlock(key);
                    door.interact();
                }
            } else {
                int px = player.getX();
                int py = player.getY();
                int pCenterX = px + pd / 2;
                int pCenterY = py + pd / 2;

                if (pCenterX < doorX) {
                    player.setPosition(doorX - pd - 1, py);
                } else if (pCenterX > doorX + doorW) {
                    player.setPosition(doorX + doorW + 1, py);
                } else if (pCenterY < doorY) {
                    player.setPosition(px, doorY - pd - 1);
                } else {
                    player.setPosition(px, doorY + doorH + 1);
                }
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        map.draw(g);
        tileM.draw((Graphics2D) g);
        player.draw(g);
        
        if (!keyPickedUp) {
            g.setColor(Color.YELLOW);
            g.fillRect(250, 250, 16, 16);
        }

        // Draw countdown timer at the top of the game.
        if (!timeUp) {
            long remaining = Math.max(0L, endTimeMillis - System.currentTimeMillis());
            long seconds = remaining / 1000L;
            long mins = seconds / 60L;
            long secs = seconds % 60L;
            String timeText = String.format("%02d:%02d", mins, secs);

            Graphics2D g2 = (Graphics2D) g;
            Font originalFont = g2.getFont();
            Font timerFont = originalFont.deriveFont(Font.BOLD, 18f);
            g2.setFont(timerFont);
            FontMetrics fm = g2.getFontMetrics();
            int textW = fm.stringWidth(timeText);
            int textH = fm.getHeight();

            int boxW = textW + 20;
            int boxH = textH + 8;
            int boxX = (screenWidth - boxW) / 2;
            int boxY = 10;

            
            g2.setColor(new Color(0, 0, 0, 160));
            g2.fillRoundRect(boxX, boxY, boxW, boxH, 8, 8);

            g2.setColor(Color.WHITE);
            int tx = boxX + (boxW - textW) / 2;
            int ty = boxY + (boxH + fm.getAscent()) / 2 - 2;
            g2.drawString(timeText, tx, ty);

            g2.setFont(originalFont);
        }

        int drawDoorW = 64, drawDoorH = 64;
        int drawDoorX = screenWidth - drawDoorW;
        int drawDoorY = screenHeight / 2 - drawDoorH / 2;
        g.setColor(door.isLocked() ? Color.RED : Color.GREEN);
        g.fillRect(drawDoorX, drawDoorY, drawDoorW, drawDoorH);

        // If time ran out, show failure message centered on screen.
        if (timeUp) {
            String msg = "You failed to escape!";
            Graphics2D g2 = (Graphics2D) g;
            Font originalFont = g2.getFont();
            Font msgFont = originalFont.deriveFont(Font.BOLD, 36f);
            g2.setFont(msgFont);
            FontMetrics fm = g2.getFontMetrics();
            int textW = fm.stringWidth(msg);
            int textH = fm.getHeight();
            int tx = (screenWidth - textW) / 2;
            int ty = (screenHeight - textH) / 2 + fm.getAscent();

            // background box
            g2.setColor(new Color(0, 0, 0, 200));
            g2.fillRect(tx - 12, ty - fm.getAscent() - 8, textW + 24, textH + 16);

            g2.setColor(Color.RED);
            g2.drawString(msg, tx, ty);

            g2.setFont(originalFont);
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Greenfield");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Main panel = new Main();
        frame.setContentPane(panel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
