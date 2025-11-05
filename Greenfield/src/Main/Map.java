package Main;

import java.awt.*;

public class Map {
    private final int windowSize = 420;
    private final int borderWidth = 40;
    private final int boxThickness = 4;

    // boundary boxes for the map
    static int[][] hitbox = {
    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
    {0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0},
    {0, 1, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0},
    {0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0},
    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
    {0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0},
    {0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0},
    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},};

public static int[][] tileBox = {
    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
    {0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0},
    {0, 1, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0},
    {0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0},
    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
    {0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0},
    {0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0},
    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},};

    public static boolean isColliding(int playerX, int playerY) {
        try {
        // Future implementation for collision detection
            if (hitbox[playerY / Main.returnTileSize()][playerX / Main.returnTileSize()] == 1) {
            return true;
            }
        } catch (Exception e) {
            return true;
        }

        return false;
    }

    public void draw(Graphics g) {
        g.setColor(Color.magenta);
        //g.fillRect(borderWidth, borderWidth, windowSize - borderWidth * 2, boxThickness); // top
        //g.fillRect(borderWidth, windowSize - borderWidth - boxThickness, windowSize - borderWidth * 2, boxThickness); // bottom
        //g.fillRect(borderWidth, borderWidth, boxThickness, windowSize - borderWidth * 2); // left
        //g.fillRect(windowSize - borderWidth - boxThickness, borderWidth, boxThickness, windowSize - borderWidth * 2); // right

        g.setColor(Color.white);

        for (int row = 0; row < hitbox.length; row++) {
            for (int col = 0; col < hitbox[0].length; col++) {
                if (hitbox[row][col] == 1) {
                    int x = col * Main.returnTileSize();
                    int y = row * Main.returnTileSize();
                    g.fillRect(x, y, Main.returnTileSize(), Main.returnTileSize());
                }
            }
        }
    }

    public int getLeftBoundary() { return borderWidth + boxThickness / 2; }
    public int getRightBoundary() { return windowSize - borderWidth - boxThickness / 2; }
    public int getTopBoundary() { return borderWidth + boxThickness / 2; }
    public int getBottomBoundary() { return windowSize - borderWidth - boxThickness / 2; }

    public int getCenterX() { return getLeftBoundary() + (getRightBoundary() - getLeftBoundary()) / 2; }
    public int getCenterY() { return getTopBoundary() + (getBottomBoundary() - getTopBoundary()) / 2; }
}
