package Main;

import java.awt.*;

public class Map {
    private final int windowSize = 420;
    private final int boxSize = 40;
    private final int boxThickness = 4;

    public void draw(Graphics g) {
        g.setColor(Color.GREEN);
        g.fillRect(boxSize, boxSize, windowSize - boxSize * 2, boxThickness); // top
        g.fillRect(boxSize, windowSize - boxSize - boxThickness, windowSize - boxSize * 2, boxThickness); // bottom
        g.fillRect(boxSize, boxSize, boxThickness, windowSize - boxSize * 2); // left
        g.fillRect(windowSize - boxSize - boxThickness, boxSize, boxThickness, windowSize - boxSize * 2); // right
    }

    public int getLeftBoundary() { return boxSize + boxThickness / 2; }
    public int getRightBoundary() { return windowSize - boxSize - boxThickness / 2; }
    public int getTopBoundary() { return boxSize + boxThickness / 2; }
    public int getBottomBoundary() { return windowSize - boxSize - boxThickness / 2; }

    public int getCenterX() { return getLeftBoundary() + (getRightBoundary() - getLeftBoundary()) / 2; }
    public int getCenterY() { return getTopBoundary() + (getBottomBoundary() - getTopBoundary()) / 2; }
}
