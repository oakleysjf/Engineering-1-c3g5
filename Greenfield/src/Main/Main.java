package Main;

import Tile.TileManager;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import Main.Interactables.Items.Key;
import Main.Interactables.Obstacles.Door;

public class Main extends JPanel {
    private final Map map;
    private final Player player;
    private final Timer timer;

    private final Key key;
    private final Door door;
    private boolean keyPickedUp = false;

    //my screen things
    final int originalTileSize = 32; // 32x32 tile
    final int scale = 2;

    public final int tileSize = originalTileSize * scale; // 64x64 tile
    final int maxScreenCol = 16;
    final int maxScreenRow = 10;
    final int screenWidth = tileSize * maxScreenCol; // 1024 pixels
    final int screenHeight = tileSize * maxScreenRow; // 640 pixels

    TileManager tileM = new TileManager(this);

    public static int returnTileSize() {
        return 64;
    }

    public Main() {
        setPreferredSize(new Dimension(screenWidth, screenHeight));
        setBackground(Color.BLACK);
        setFocusable(true);

        map = new Map();
        player = new Player(0, 0);

        key = new Key("Golden Key");
        door = new Door(new Key("Golden Key"));

        setupKeyBindings();

        timer = new Timer(4, e -> {
            player.update(map);
            checkInteractions();
            repaint();
        });
        timer.start();
    }

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

        // Terminate when player reaches right edge of the map
        if (player.getX() + pd >= screenWidth) {
            System.out.println("Player reached end of map. Exiting.");
            System.exit(0);
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

        int drawDoorW = 64, drawDoorH = 64;
        int drawDoorX = screenWidth - drawDoorW;
        int drawDoorY = screenHeight / 2 - drawDoorH / 2;
        g.setColor(door.isLocked() ? Color.RED : Color.GREEN);
        g.fillRect(drawDoorX, drawDoorY, drawDoorW, drawDoorH);
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
