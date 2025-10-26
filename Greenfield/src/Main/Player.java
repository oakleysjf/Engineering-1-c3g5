package Main;

import java.awt.*;
import GameAssets.*;

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
        if (up && y - speed > map.getTopBoundary()) {
            y -= speed;
        }
        if (down && y + speed < map.getBottomBoundary() - diameter) {
            y += speed;
        }
        if (left && x - speed > map.getLeftBoundary()) {
            x -= speed;
        }
        if (right && x + speed < map.getRightBoundary() - diameter) {
            x += speed;
        }
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
