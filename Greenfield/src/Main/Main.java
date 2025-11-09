package Main;

import Tile.TileManager;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import Main.Interactables.Items.*;
import Main.Interactables.Obstacles.*;
import Main.Interactables.NPCs.Professor;
import Main.Interactables.Exit;


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
    private final Exit exit;
    private boolean keyPickedUp = false;
    private Professor professor;
    private FreshersFlu freshersFlu;
    private EnergyDrink energyDrink;

    // Add FreshersFlu and EnergyDrink as fields, but initialize in constructor
    private boolean fluTaken = false;
    private boolean drinkTaken = false;

    // Track whether the player has escaped by reaching the exit.
    private boolean escaped = false;

    // Player score, decreases by 50 for every 30s passed.
    private int playerScore = 500;

    // Start time used to calculate score decay
    private long startTimeMillis;

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
        door = new Door(key);
        exit = new Exit();
        freshersFlu = new FreshersFlu("FreshersFlu", 128, 128);
        energyDrink = new EnergyDrink("EnergyDrink", 448, 320); 

        // Initialize the Professor in the top-right of the arena
        int professorStartX = screenWidth - 100; 
        int professorStartY = 250; 
        professor = new Professor("Professor", professorStartX, professorStartY, 200);

        // Calls the setupKeyBindings method.
        setupKeyBindings();

    // Initialize countdown and start time for score decay
    startTimeMillis = System.currentTimeMillis();
    endTimeMillis = startTimeMillis + gameTime;

        timer = new Timer(4, e -> {
            if (!timeUp && !escaped) {
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
                    professor.walkCycle(); // Update professor's position
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
     * Fresher's flu affect.
     * Checks the interactions of player with all interactables and decides the game over/game success logic.
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

        final int fluW = 16, fluH = 16;
        Rectangle fluBounds = new Rectangle(128, 128, fluW, fluH);
        if (!fluTaken && playerBounds.intersects(fluBounds)) {
            freshersFlu.interact(player);
            fluTaken = true;
        }

        final int drinkW = 16, drinkH = 16;
        Rectangle drinkBounds = new Rectangle(448, 320, drinkW, drinkH);
        if (!drinkTaken && playerBounds.intersects(drinkBounds)) {
            energyDrink.interact(player);
            drinkTaken = true;
        }

        final int doorW = 64, doorH = 64;
        // Door is at the far-right edge of the world (world coordinates)
        final int doorX = Map.tileBox[0].length * Main.returnTileSize() - doorW;
        final int doorY = (Map.tileBox.length * Main.returnTileSize()) / 2 - doorH / 2;
        Rectangle doorBounds = new Rectangle(doorX, doorY, doorW, doorH);

        if (playerBounds.intersects(doorBounds)) {
            boolean hasKey = player.getInventory().getItems().contains(key);
            if (hasKey) {
                if (door.isLocked()) {
                    door.unlock(key);
                    door.interact();
                }

                // If door is unlocked and player is intersecting, they've escaped.
                if (!door.isLocked() && !escaped) {
                    escaped = true;
                    // Stop normal updates and schedule program exit after showing success message
                    timer.stop();
                    Timer exitTimer = new Timer(3000, ev -> System.exit(0));
                    exitTimer.setRepeats(false);
                    exitTimer.start();
                    repaint();
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

        int interactionPadding = 20;
        Rectangle professorBounds = new Rectangle(
            professor.getX() - interactionPadding,
            professor.getY() - interactionPadding,
            professor.getWidth() + 2 * interactionPadding,
            professor.getHeight() + 2 * interactionPadding
        );
        if (playerBounds.intersects(professorBounds)) {
            int response = JOptionPane.showConfirmDialog(
                this,
                "Professor: Do you have your assignment?!",
                "Professor Interaction",
                JOptionPane.YES_NO_OPTION
            );

            // MESSAGE: Cannot figure out a working method for chase interaction. This is left as blank. - Oakley 
            if (response == JOptionPane.YES_OPTION || response == JOptionPane.NO_OPTION) {
                // Close the dialogue box
                
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    // Update player score based on elapsed time: -50 every 15 seconds
    long elapsed = Math.max(0L, System.currentTimeMillis() - startTimeMillis);
    long steps = elapsed / 15000L; // number of 15s intervals passed
    long decrements = steps * 50L;
    playerScore = (int) Math.max(0L, 500L - decrements);
        map.draw(g);
        tileM.draw((Graphics2D) g);
        player.draw(g);
        professor.draw(g);
        
        if (!keyPickedUp) {
            g.setColor(Color.YELLOW);
            g.fillRect(250, 250, 16, 16);
        }
        if (!fluTaken) {
            g.setColor(Color.CYAN);
            g.fillRect(128, 128, 16, 16);
        }
        if (!drinkTaken) {
            g.setColor(Color.ORANGE);
            g.fillRect(448, 320, 16, 16);
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

            // Draw a small subtitle below the timer
            String subtitle = "Escape from Uni!";
            Font subFont = originalFont.deriveFont(Font.PLAIN, 14f);
            g2.setFont(subFont);
            FontMetrics sfm = g2.getFontMetrics();
            int subW = sfm.stringWidth(subtitle);
            int subX = (screenWidth - subW) / 2;
            int subY = boxY + boxH + sfm.getAscent() + 4;
            g2.setColor(Color.WHITE);
            g2.drawString(subtitle, subX, subY);

            g2.setFont(originalFont);
        }

        int drawDoorW = 64, drawDoorH = 64;
        int drawDoorX = screenWidth - drawDoorW;
        int drawDoorY = screenHeight / 2 - drawDoorH / 2;
        g.setColor(door.isLocked() ? Color.RED : Color.GREEN);
        g.fillRect(drawDoorX, drawDoorY, drawDoorW, drawDoorH);
        // Draw the exit indicator inside the door
        exit.draw(g, drawDoorX, drawDoorY, drawDoorW, drawDoorH);

        // Draw score in top-left (on top of tiles/player)
        Graphics2D gScore = (Graphics2D) g;
        Font origFontScore = gScore.getFont();
        Font scoreFontTop = origFontScore.deriveFont(Font.BOLD, 16f);
        gScore.setFont(scoreFontTop);
        gScore.setColor(Color.WHITE);
        gScore.drawString("Score: " + playerScore, 10, 20);
        gScore.setFont(origFontScore);

        // If time runs out, show failure message centered on screen.
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

        // If player escaped, show success overlay with score beneath
        if (escaped) {
            String msg = "You escaped!";
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
            g2.fillRect(tx - 12, ty - fm.getAscent() - 8, textW + 24, textH + 16 + 24);

            g2.setColor(Color.GREEN);
            g2.drawString(msg, tx, ty);

            // draw the score below the message
            String scoreText = "Score: " + playerScore;
            Font scoreFont = originalFont.deriveFont(Font.PLAIN, 20f);
            g2.setFont(scoreFont);
            FontMetrics sfm = g2.getFontMetrics();
            int sW = sfm.stringWidth(scoreText);
            int sX = (screenWidth - sW) / 2;
            int sY = ty + sfm.getHeight() + 8;
            g2.setColor(Color.WHITE);
            g2.drawString(scoreText, sX, sY);

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
