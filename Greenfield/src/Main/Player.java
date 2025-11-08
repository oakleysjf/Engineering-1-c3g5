package Main;

import GameAssets.*;
import java.awt.*;

public class Player {
    private static int x, y;
    private static int offsetX, offsetY;

    public  int screenX;
    public  int screenY;

    private final int size = 20;
    private final int diameter = size * 2;
    private final int speed = 1;

    private boolean up, down, left, right;
    private Inventory playerInventory;

    public static int returnPlayerX() {
        return x;
    }
    public static int returnPlayerY() {
        return y;
    }

    public Player(int startX, int startY) {
        this.x = startX;
        this.y = startY;

        screenX = Main.returnTileSize() * 8 - size;
        screenY = Main.returnTileSize() * 5 - size;
    }

    public void update(Map map) {
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

    public boolean movementCheck(int x, int y) {
        
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
        System.out.println("Player X: " + x + " Player Y: " + y);

        g.setColor(Color.WHITE);
        g.fillOval(screenX + offsetX, screenY + offsetY, diameter, diameter);
    }

    // Movement controls
    public void setUp(boolean state) { up = state; }
    public void setDown(boolean state) { down = state; }
    public void setLeft(boolean state) { left = state; }
    public void setRight(boolean state) { right = state; }

    public int getX() { return x; }
    public int getY() { return y; }
}
