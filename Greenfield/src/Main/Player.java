package Main;

import java.awt.*;
import java.util.List;

import Main.Interactables.Items.Item;

import java.util.ArrayList;
import java.util.Collections;


// Player class to be controlled by the user.
public class Player {
    
    // Player position, size and speed.
    private int x, y;
    private final int size = 8;
    private final int diameter = size * 2;
    private final int speed = 2;

    // Movement booleans.
    private boolean up, down, left, right;
    
    // Inventory.
    private Inventory playerInventory;


    // Constructor.
    public Player(int startX, int startY) {
        this.x = startX;
        this.y = startY;
        this.playerInventory = new Inventory();
    }

    /**
     * Simple nested Inventory class owned by the Player.
     * Stores items in an ArrayList and exposes basic operations.
     */
    public class Inventory {
        private final List<Item> items = new ArrayList<>();

        /**
         * Adds item to player inventory.
         * @param item - item to be added into player inventory.
         */
        public void add(Item item) {
            if (item != null) items.add(item);
        }

        /**
         * Removes an item from the player's inventory.
         * @param item - item to be removed from inventory.
         * @return - true if successful, false otherwise.
         */
        public boolean remove(Item item) {
            return items.remove(item);
        }

        /**
         * Retrieves an item.
         * @param index - index of where the item is.
         * @return - item at the given index.
         */
        public Item get(int index) {
            return items.get(index);
        }

        /**
         * @return more than one item.
         */
        public List<Item> getItems() {
            return Collections.unmodifiableList(items);
        }

        /**
         * @return size of the inventory. (number of elements)
         */
        public int size() {
            return items.size();
        }

        /**
         * Empties inventory.
         */
        public void clear() {
            items.clear();
        }
    }

    /**
     * Retrieves the player inventory.
     * @return - inventory.
     */
    public Inventory getInventory() { return playerInventory; }

    public void update(Map map) {
        if (up && movementCheck(0, -speed)) {
            y -= speed;
        }
        if (down && movementCheck(0, speed + diameter)) {
            y += speed;
        }
        if (left && movementCheck(-speed, 0)) {
            x -= speed;
        }
        if (right && movementCheck(speed + diameter, 0)) {
            x += speed;
        }
    }

    public boolean movementCheck(int x, int y) {
        // Future implementation for collision detection
        if (Map.isColliding(this.x + x, this.y + y)) {
            return false;
        }

        return true;
    }

    public void draw(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillOval(x, y, diameter, diameter);
    }

    // Setter methods for movement booleans. (See Main class for use.)
    
    public void setUp(boolean state) { up = state; }
    public void setDown(boolean state) { down = state; }
    public void setLeft(boolean state) { left = state; }
    public void setRight(boolean state) { right = state; }


    // Getter methods for the player coordinates.

    public int getX() { return x; }
    public int getY() { return y; }
    
    /**
     * Set the player's position directly.
     */
    public void setPosition(int newX, int newY) {
        this.x = newX;
        this.y = newY;
    }

    /**
     * Return the diameter (width/height) of the player for collision calculations.
     */
    public int getDiameter() { return diameter; }

    /**
     * Adds an item to the playerInventory variable.
     * @param item - item to be added to the inventory.
     */
    public void addItem(Item item){
        if(item.getX() == this.x + 5 || item.getY() == this.size + 5){
            playerInventory.add(item);
            item.removeFromMap();
        }
    }
    
    // Deprecated method, instead of live activation the items will be used upon first interaction (i.e. key is added to inventory then used on door.)
    // public void useItem(Item item){
    // }
}
