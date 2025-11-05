package Tile;

import Main.Main;
import Main.Map;
import java.awt.Graphics2D;
import javax.imageio.ImageIO;

public class TileManager {

    Main gp;
    Tile[] tile;

    public TileManager(Main gp) {
        System.out.println("this runs cuhhh");
        this.gp = gp;

        tile = new Tile[10];
        getTileImage();
    }

    public void getTileImage() {
        try {
            tile[0] = new Tile();
            tile[0].tileImage = ImageIO.read(getClass().getResourceAsStream("/Tiles/Wood-flooring.png"));
            tile[1] = new Tile();
            tile[1].tileImage = ImageIO.read(getClass().getResourceAsStream("/Tiles/Rock-of-death.png"));
            tile[2] = new Tile();
            tile[2].tileImage = ImageIO.read(getClass().getResourceAsStream("/Tiles/wall.png"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2) {
        g2.drawImage(tile[0].tileImage, 0, 0, gp.tileSize, gp.tileSize, null);
        g2.drawImage(tile[1].tileImage, 64, 0, gp.tileSize, gp.tileSize, null);
        for (int row = 0; row < Map.tileBox.length; row++) {
            for (int col = 0; col < Map.tileBox[0].length; col++) {
                if (true) {
                    int x = col * Main.returnTileSize();
                    int y = row * Main.returnTileSize();
                    //g2.fillRect(x, y, Main.returnTileSize(), Main.returnTileSize());
                    g2.drawImage(tile[Map.tileBox[row][col]].tileImage, x, y, gp.tileSize, gp.tileSize, null);
                }
            }
        }
    }

}
