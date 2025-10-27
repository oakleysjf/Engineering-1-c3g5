package Main;

import GameAssets.*;
import java.awt.*;

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
    }

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
}
