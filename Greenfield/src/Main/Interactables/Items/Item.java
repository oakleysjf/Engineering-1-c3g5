package Main.Interactables.Items;

import javax.swing.JComponent;
import java.util.ArrayList;
import java.util.Arrays;

import Main.Player;

public  class Item extends JComponent{
    private int x, y;
    private ArrayList<String> possibleNameList = new ArrayList<>(Arrays.asList("Key", "Assignment", "Book", "FreshersFlu", "EnergyDrink"));
    private String name;

    public Item(String name, int x, int y) {
    this.possibleNameList = new ArrayList<>(Arrays.asList("Key", "Book", "Assignment", "FreshersFlu", "EnergyDrink"));

        if(!possibleNameList.contains(name)){
            throw new IllegalArgumentException();
        }
        this.name = name;
        this.x = x;
        this.y = y;
    }

    public Item(String name) {
        this.name = name;
    }

    public void removeFromMap(){
        
    }

    public void draw(){}

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getName() {
        return name;
    }

    public void interact(Player player) {
        // Default implementation does nothing. Subclasses can override.
    }
}
