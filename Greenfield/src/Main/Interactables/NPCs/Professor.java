package Main.Interactables.NPCs;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Professor extends NPC {
    private int x, y;
    private double angle;
    private int pathSize;
    private int speed;

    /**
     * Constructor for the Professor class.
     * @param name The name of the professor.
     * @param startX The initial x-coordinate.
     * @param startY The initial y-coordinate.
     * @param pathSize The length of the path taken during walkCycle() method.
     */
    public Professor(String name, int startX, int startY, int pathSize) {
        super(name, startX, startY);
        this.x = startX;
        this.y = startY;
        this.angle = 0;
        this.pathSize = pathSize;
        this.speed = 1; // Slow down the professor
    }

    /**
     * Updates the Professor's position based on an oval walk cycle.
     */
    @Override
    public void walkCycle() {
        angle += 0.02 * speed;
        if (angle >= 2 * Math.PI) {
            angle = 0;
        }
        int newY = y + (int) (Math.sin(angle) * pathSize / 2);
        int newX = x; // Maintain horizontal position
        setX(newX); // Use x to keep the professor in the same column
        setY(newY); // Update vertical position based on walk cycle
    }

    /**
     * Draws the Professor as a black circle.
     * @param g The Graphics object to draw with.
     */
    @Override
    public void draw(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillOval(getX() - 10, getY() - 10, 20, 20); // Draw a circle with a radius of 10
    }

    public boolean movementCheck(int x, int y) {
        // Collision detection logic
        Rectangle professorBounds = new Rectangle(getX() - 10, getY() - 10, 20, 20);
        Rectangle targetBounds = new Rectangle(x, y, 20, 20);
        return !professorBounds.intersects(targetBounds);
    }
}