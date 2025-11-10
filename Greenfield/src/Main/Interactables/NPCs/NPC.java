package Main.Interactables.NPCs;

import Main.Main;
import Main.Player;
import java.awt.Graphics;
import javax.swing.JComponent;

public class NPC extends JComponent {
    private String name;
    private int x, y;
    int offsetX;
    int offsetY;
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
        offsetX = Player.returnPlayerX();

        if (offsetX < 0) {
            offsetX = 0;
        }
        else if (offsetX > Main.worldWidth - Main.returnScreenWidth()) {
            offsetX = Main.worldWidth - Main.returnScreenWidth();
        }
        this.x = x - offsetX;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        offsetY = Player.returnPlayerY();

        if (offsetY < 0) {
            offsetY = 0;
        }
        else if (offsetY > Main.worldHeight - Main.returnScreenHeight()) {
            offsetY = Main.worldHeight - Main.returnScreenHeight();
        }
        this.y = y - offsetY;
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
