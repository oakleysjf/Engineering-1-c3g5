package Main.Interactables.NPCs;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Duck extends NPC {
    private int x, y;
    private double angle;
    private int pathSize;
    private int speed;

    private BufferedImage duckImage;

    /**
     * Constructor for the Duck class.
     * @param name The name of the duck.
     * @param startX The initial x-coordinate.
     * @param startY The initial y-coordinate.
     * @param pathSize The length of the path taken during walkCycle() method.
     */

    private final int size = 44;
    private final int diameter = size * 2;
    private int direction;


    public Duck(String name, int startX, int startY, int pathSize, int direction) {
        super(name, startX, startY);
        this.x = startX;
        this.y = startY;
        this.direction = direction;
        this.angle = 0;
        this.pathSize = pathSize;
        this.speed = 1; // Slow down the duck

        try {
            duckImage = ImageIO.read(getClass().getResource("/Tiles/duck_art.png"));
            if (duckImage == null) {
                throw new IOException("Image file not found: /Tiles/duck_art.png");
            }
        } catch (IOException e) {
            System.err.println("Failed to load duck image: " + e.getMessage());
            e.printStackTrace();
            // Create a fallback colored oval as the duck sprite
            duckImage = new BufferedImage(diameter, diameter, BufferedImage.TYPE_INT_ARGB);
            Graphics g = duckImage.getGraphics();
            g.setColor(Color.YELLOW);
            g.fillOval(0, 0, diameter, diameter);
            g.dispose();
        }
    }

    /**
     * Updates the duck's position based on an oval walk cycle.
     */
    @Override
    public void walkCycle() {
        angle += 0.01 * speed;
        if (angle >= 2 * Math.PI) {
            angle = 0;
        }
        int newY = y; // maintain vertical position
        int newX = (x + (int) (Math.sin(angle + direction) * pathSize / 2));
        setX(newX); // Use x to keep the duck in the same column
        setY(newY); // Update vertical position based on walk cycle
    }

    /**
     * Draws the duck as a black circle or the duck.png image.
     * @param g The Graphics object to draw with.
     */
    @Override
    public void draw(Graphics g) {
        if (duckImage != null) {
            g.drawImage(duckImage, getX() - size, getY() - size, diameter, diameter, null);
        } else {
            g.setColor(Color.BLACK);
            g.fillOval(getX() - size, getY() - size, diameter, diameter); // Fallback to a circle
        }
    }

    public boolean movementCheck(int x, int y) {
        // Collision detection logic
        Rectangle duckBounds = new Rectangle(getX() - size, getY() - size, diameter, diameter);
        Rectangle targetBounds = new Rectangle(x, y, diameter, diameter);
        return !duckBounds.intersects(targetBounds);
    }
}