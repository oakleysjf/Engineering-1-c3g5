import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


// Very basic, barebones frame of the game. Dot within a box that cannot move past the boundary.

public class MainGame extends JPanel{

    // Size of the game.
    private static final int WINDOW_WIDTH = 420;
    private static final int BOX_SIZE = 40;
    private static final int BOX_THICKNESS = 4;

    // Size of the player.
    private final int PLAYER_SIZE = 8;
    private final int PLAYER_DIAMETER = PLAYER_SIZE * 2;
    private final int PLAYER_SPEED = 3;

    // Position of the player.
    private int player_x, player_y;

    // Movements of the player.
    private boolean up, down, left, right;

    // Timer to track actions.
    private Timer timer;

    public MainGame(){
        setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_WIDTH));
        setBackground(Color.BLACK);
        setFocusable(true);
        setupKeyBindings();

        timer = new Timer(16, e ->{
            updatePosition();
            repaint();
        });
        timer.start();
    }

    /**
     * main() method draws and shows the game's panel.
     */
    public static void main(String[] args) {
        JFrame frame = new JFrame("Greenfield");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        MainGame panel = new MainGame();
        frame.setContentPane(panel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    

    @Override
    public void addNotify() {
        super.addNotify();
        
        int boxLeft = BOX_SIZE + BOX_THICKNESS/2;
        int boxTop = BOX_SIZE + BOX_THICKNESS/2;
        int boxWidth = getWidth() - (BOX_SIZE * 2) - BOX_THICKNESS;
        int boxHeight = getHeight() - (BOX_SIZE * 2) - BOX_THICKNESS;
        player_x = boxLeft + boxWidth / 2;
        player_y = boxTop + boxHeight / 2;
    }

    
    /**
     * Method to set up and map the standard W (UP), A (LEFT), S (DOWN) and D (RIGHT) controls.
     */
    private void setupKeyBindings() {
        InputMap im = getInputMap(WHEN_IN_FOCUSED_WINDOW);
        ActionMap am = getActionMap();

        // Mapping key stroke -> action name
        im.put(KeyStroke.getKeyStroke('W'), "upPressed");
        im.put(KeyStroke.getKeyStroke('w'), "upPressed");
        im.put(KeyStroke.getKeyStroke("UP"), "upPressed");

        im.put(KeyStroke.getKeyStroke('S'), "downPressed");
        im.put(KeyStroke.getKeyStroke('s'), "downPressed");
        im.put(KeyStroke.getKeyStroke("DOWN"), "downPressed");

        im.put(KeyStroke.getKeyStroke('A'), "leftPressed");
        im.put(KeyStroke.getKeyStroke('a'), "leftPressed");
        im.put(KeyStroke.getKeyStroke("LEFT"), "leftPressed");

        im.put(KeyStroke.getKeyStroke('D'), "rightPressed");
        im.put(KeyStroke.getKeyStroke('d'), "rightPressed");
        im.put(KeyStroke.getKeyStroke("RIGHT"), "rightPressed");

        // Released bindings
        im.put(KeyStroke.getKeyStroke("released W"), "upReleased");
        im.put(KeyStroke.getKeyStroke("released w"), "upReleased");
        im.put(KeyStroke.getKeyStroke("released UP"), "upReleased");

        im.put(KeyStroke.getKeyStroke("released S"), "downReleased");
        im.put(KeyStroke.getKeyStroke("released s"), "downReleased");
        im.put(KeyStroke.getKeyStroke("released DOWN"), "downReleased");

        im.put(KeyStroke.getKeyStroke("released A"), "leftReleased");
        im.put(KeyStroke.getKeyStroke("released a"), "leftReleased");
        im.put(KeyStroke.getKeyStroke("released LEFT"), "leftReleased");

        im.put(KeyStroke.getKeyStroke("released D"), "rightReleased");
        im.put(KeyStroke.getKeyStroke("released d"), "rightReleased");
        im.put(KeyStroke.getKeyStroke("released RIGHT"), "rightReleased");

        // Sets boolean variables appropriately.
        am.put("upPressed", new AbstractAction() { public void actionPerformed(ActionEvent e) { up = true; }});
        am.put("upReleased", new AbstractAction() { public void actionPerformed(ActionEvent e) { up = false; }});

        am.put("downPressed", new AbstractAction() { public void actionPerformed(ActionEvent e) { down = true; }});
        am.put("downReleased", new AbstractAction() { public void actionPerformed(ActionEvent e) { down = false; }});

        am.put("leftPressed", new AbstractAction() { public void actionPerformed(ActionEvent e) { left = true; }});
        am.put("leftReleased", new AbstractAction() { public void actionPerformed(ActionEvent e) { left = false; }});

        am.put("rightPressed", new AbstractAction() { public void actionPerformed(ActionEvent e) { right = true; }});
        am.put("rightReleased", new AbstractAction() { public void actionPerformed(ActionEvent e) { right = false; }});
    }

    
    /**
     * 
     */
    private void updatePosition() {
        int dx = 0, dy = 0;
        if (up) dy -= PLAYER_SPEED;
        if (down) dy += PLAYER_SPEED;
        if (left) dx -= PLAYER_SPEED;
        if (right) dx += PLAYER_SPEED;

        if (dx != 0 && dy != 0) {
            dx = (int) Math.round(dx / Math.sqrt(2));
            dy = (int) Math.round(dy / Math.sqrt(2));
        }

        player_x += dx;
        player_y += dy;

        clampDotToBox();
    }

    

    private void clampDotToBox() {
        int boxLeft = BOX_SIZE + BOX_THICKNESS/2 + PLAYER_SIZE;
        int boxTop = BOX_SIZE + BOX_THICKNESS/2 + PLAYER_SIZE;
        int boxRight = getWidth() - BOX_SIZE - BOX_THICKNESS/2 - PLAYER_SIZE;
        int boxBottom = getHeight() - BOX_SIZE - BOX_THICKNESS/2 - PLAYER_SIZE;

        if (player_x < boxLeft) player_x = boxLeft;
        if (player_y < boxTop) player_y = boxTop;
        if (player_x > boxRight) player_x = boxRight;
        if (player_y > boxBottom) player_y = boxBottom;
    }

    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int boxX = BOX_SIZE;
        int boxY = BOX_SIZE;
        int boxW = getWidth() - BOX_SIZE * 2;
        int boxH = getHeight() - BOX_SIZE * 2;

        g2.setColor(new Color(30, 30, 30));
        g2.fillRect(boxX, boxY, boxW, boxH);

        g2.setStroke(new BasicStroke(BOX_THICKNESS));
        g2.setColor(Color.GRAY);
        g2.drawRect(boxX, boxY, boxW, boxH);

        g2.setColor(Color.WHITE);
        int drawX = player_x - PLAYER_SIZE;
        int drawY = player_y - PLAYER_SIZE;
        g2.fillOval(drawX, drawY, PLAYER_DIAMETER, PLAYER_DIAMETER);

        g2.setColor(Color.LIGHT_GRAY);
        g2.dispose();
    }

}
