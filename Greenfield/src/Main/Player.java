package Main;

import GameAssets.*;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import Main.Interactables.Items.Item;

public class Player {
    private int x, y;
    private final int size = 8;
    private final int diameter = size * 2;
    private final int speed = 3;

    private boolean up, down, left, right;
    private Inventory playerInventory;

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

        public void add(Item item) {
            if (item != null) items.add(item);
        }

        public boolean remove(Item item) {
            return items.remove(item);
        }

        public Item get(int index) {
            return items.get(index);
        }

        public List<Item> getItems() {
            return Collections.unmodifiableList(items);
        }

        public int size() {
            return items.size();
        }

        public void clear() {
            items.clear();
        }
    }

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

    // Movement controls
    public void setUp(boolean state) { up = state; }
    public void setDown(boolean state) { down = state; }
    public void setLeft(boolean state) { left = state; }
    public void setRight(boolean state) { right = state; }

    public int getX() { return x; }
    public int getY() { return y; }

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
    
    public void useItem(Item item){
        switch(item.get){}
    }
}
