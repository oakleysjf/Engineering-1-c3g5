package Main;

import Tile.TileManager;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Main extends JPanel {
    private final Map map;
    private final Player player;
    private final Timer timer;
    private static final int WINDOW_SIZE = 42;

    //my screen things
    final static int originalTileSize = 32; // 32x32 tile
    final static int scale = 2;

    public static final int tileSize = originalTileSize * scale; // 64x64 tile
    final static int maxScreenCol = 16;
    final static int maxScreenRow = 10;
    final static int screenWidth = tileSize * maxScreenCol; // 1024 pixels
    final static int screenHeight = tileSize * maxScreenRow; // 640 pixels

    public final static int maxWorldCol = 25;
    public final static int maxWorldRow = 25;
    public final static int worldWidth = tileSize * maxWorldCol;
    public final static int worldHeight = tileSize * maxWorldRow;

    TileManager tileM = new TileManager(this);

    public static int returnTileSize() {
        return tileSize;
    }

    public static int returnScreenWidth() {
        return screenWidth;
    }

    public static int returnScreenHeight() {
        return screenHeight;
    }

    public static int returnWorldWidth() {
        return worldWidth;
    }
    public static int returnWorldHeight() {
        return worldHeight;
    }

    public Main() {
        setPreferredSize(new Dimension(screenWidth, screenHeight));
        setBackground(Color.BLACK);
        setFocusable(true);

        map = new Map();
        player = new Player(0, 0);

        setupKeyBindings();

        timer = new Timer(4, e -> {
            player.update(map);
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

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        //map.draw(g);
        tileM.draw((Graphics2D) g);
        player.draw(g);
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
