package Main;

import Main.Interactables.Items.Item;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.imageio.ImageIO;

// Player class to be controlled by the user.
public class Player {
    
    // Player position, size and speed.
    private static int x, y;
    private static int offsetX, offsetY;

    public  int screenX;
    public  int screenY;

    private final int size = 22;
    private final int diameter = size * 2;
    private int speed = 2;

    // Movement booleans.
    private boolean up, down, left, right;
    
    // Inventory.
    private Inventory playerInventory;

    private boolean stunned = false;
    private long stunEndTime = 0;

    private BufferedImage playerImage;

    public static int returnPlayerX() {
        return x;
    }
    public static int returnPlayerY() {
        return y;
    }

    // Constructor.
    public Player(int startX, int startY) {
        this.x = startX;
        this.y = startY;
        this.playerInventory = new Inventory(); // Initialize the inventory

        screenX = Main.returnTileSize() * 8 - size;
        screenY = Main.returnTileSize() * 5 - size;

        try {
            playerImage = ImageIO.read(getClass().getResource("/Tiles/student_art.png"));
            if (playerImage == null) {
                throw new IOException("Image file not found: /Tiles/student_art.png");
            }
        } catch (IOException e) {
            System.err.println("Failed to load player image: " + e.getMessage());
            e.printStackTrace();
        }
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
        if (stunned && System.currentTimeMillis() >= stunEndTime) {
            stunned = false;
        }
        if (!stunned) {
            if (up && (movementCheck(0, -speed) && movementCheck(diameter, -speed))) {
                y -= speed;
            }
            if (down && (movementCheck(0, speed + diameter) && movementCheck(diameter, speed + diameter))) {
                y += speed;
            }
            if (left && (movementCheck(-speed, 0) && movementCheck(-speed, diameter))) {
                x -= speed;
            }
            if (right && (movementCheck(speed + diameter, 0) && movementCheck(speed + diameter, diameter))) {
                x += speed;
            }
        }
    }

    public boolean movementCheck(int x, int y) {
        // Future implementation for collision detection
        if (Map.isColliding(this.x + x + screenX, this.y + y + screenY)) {
            return false;
        }

        return true;
    }

    public void draw(Graphics g) {

        if (x < 0) {
            offsetX = x;
        }
        else if (x > Main.returnWorldWidth() - Main.returnScreenWidth()) {
            offsetX = x - (Main.returnWorldWidth() - Main.returnScreenWidth());
        }
        else {
            offsetX = 0;
        }

        if (y < 0) {
            offsetY = y;
        }
        else if (y > Main.returnWorldHeight() - Main.returnScreenHeight()) {
            offsetY = y - (Main.returnWorldHeight() - Main.returnScreenHeight());
        }
        else {
            offsetY = 0;
        }   

        if (playerImage != null) {
            g.drawImage(playerImage, screenX + offsetX, screenY + offsetY, diameter, diameter, null);
        } else {
            g.setColor(Color.BLUE);
            g.fillOval(screenX + offsetX, screenY + offsetY, diameter, diameter); // Fallback to a circle
        }
    }

    // Setter methods for movement booleans. (See Main class for use.)
    
    public void setUp(boolean state) { up = state; }
    public void setDown(boolean state) { down = state; }
    public void setLeft(boolean state) { left = state; }
    public void setRight(boolean state) { right = state; }


    // Getter methods for the player coordinates.

    public int getX() { return screenX + offsetX; }
    public int getY() { return screenY + offsetY; }

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
    
    /**
     * Stuns the player for the specified duration in milliseconds.
     */
    public void stun(int durationMillis) {
        stunned = true;
        stunEndTime = System.currentTimeMillis() + durationMillis;
    }

    public void slowDown(int durationMillis) {
    // Example implementation: reduce speed for the given duration
    final int originalSpeed = this.speed;
    this.speed = Math.max(1, this.speed / 2); // Slow down (halve speed, minimum 1)
    new java.util.Timer().schedule(new java.util.TimerTask() {
        @Override
        public void run() {
            speed = originalSpeed; // Restore speed after duration
        }
    }, durationMillis);
    }

    public boolean isStunned() {
        return stunned;
    }
}
