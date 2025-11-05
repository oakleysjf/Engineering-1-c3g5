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
     * @param name
     * @param x
     * @param y
     */
    public NPC(String name, int x, int y){}

    public void walkCycle(){};

    public void draw(){};
}
