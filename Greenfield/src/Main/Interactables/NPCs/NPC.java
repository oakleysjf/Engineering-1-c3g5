package Main.Interactables.NPCs;

import javax.swing.JComponent;
import java.awt.Graphics;

public class NPC extends JComponent {
    private String name;
    private int x, y;
    private boolean hostile;
    private boolean interactable;

    /**
     * Constructor for the NPC class.
     * @param name The name of the NPC.
     * @param x The initial x-coordinate.
     * @param y The initial y-coordinate.
     */
    public NPC(String name, int x, int y) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.hostile = false;
        this.interactable = true;
    }

    /**
     * Updates the NPC's position based on its walk cycle.
     */
    public void walkCycle() {
        // Default walk cycle does nothing; subclasses can override.
    }

    /**
     * Draws the NPC on the screen.
     * @param g The Graphics object to draw with.
     */
    public void draw(Graphics g) {
        // Default draw does nothing; subclasses can override.
    }

    // Getters and setters
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isHostile() {
        return hostile;
    }

    public void setHostile(boolean hostile) {
        this.hostile = hostile;
    }

    public boolean isInteractable() {
        return interactable;
    }

    public void setInteractable(boolean interactable) {
        this.interactable = interactable;
    }
}
