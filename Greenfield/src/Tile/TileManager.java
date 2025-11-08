package Tile;

import Main.Main;
import Main.Player;
import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.FileReader;
import javax.imageio.ImageIO;


public class TileManager {

    Main gp;
    Tile[] tile;
    int mapTileNum[][];
    public static int mapBoundaryNum[][];

    int offsetX;
    int offsetY;

    public TileManager(Main gp) {
        System.out.println("this runs cuhhh");
        this.gp = gp;
        mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];
        mapBoundaryNum = new int[gp.maxWorldCol][gp.maxWorldRow];

        tile = new Tile[10];
        getTileImage();
        loadMap("Greenfield/src/GameAssets/WorldMapInfo/World-Map.txt");
        loadBoundary("Greenfield/src/GameAssets/WorldMapInfo/World-Boundaries.txt");
    }

    public static boolean isColliding(int playerX, int playerY) {
        try {
        // Future implementation for collision detection
            if (mapBoundaryNum[playerY / Main.returnTileSize()][playerX / Main.returnTileSize()] == 1) {
            return true;
            }
        } catch (Exception e) {
            return false;
        }

        return false;
    }

    public void loadMap(String filePath) {
        //InputStream is = getClass().getResourceAsStream(filePath);
        //BufferedReader br = new BufferedReader(new InputStreamReader(is));

        int col = 0;
        int row = 0;
        System.out.println(System.getProperty("user.dir"));
        try(BufferedReader br2 = new BufferedReader(new FileReader(filePath))) {
            String line;
            while((line = br2.readLine()) != null) {
                System.out.println(line);
                String numbers[] = line.split(" ");
                for (col = 0; col < gp.maxWorldCol; col++) {
                    int num = Integer.parseInt(numbers[col]);
                    System.out.println("Setting mapTileNum[" + row + "][" + col + "] = " + num);
                    mapTileNum[row][col] = num;
                }
                row++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        /* while (col < gp.maxWorldCol && row < gp.maxWorldRow) {

            try {
                String line = br.readLine();            
                System.out.println(line);

                while (col < gp.maxWorldCol) {
                    String numbers[] = line.split(" ");
                    int num = Integer.parseInt(numbers[col]);

                    mapTileNum[col][row] = num;
                    col++;
                }
                if (col == gp.maxWorldCol) {
                    col = 0;
                    row++;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } */
    }

    public void loadBoundary(String filePath) {
        int col = 0;
        int row = 0;
        System.out.println(System.getProperty("user.dir"));
        try(BufferedReader br2 = new BufferedReader(new FileReader(filePath))) {
            String line;
            while((line = br2.readLine()) != null) {
                System.out.println(line);
                String numbers[] = line.split(" ");
                for (col = 0; col < gp.maxWorldCol; col++) {
                    int num = Integer.parseInt(numbers[col]);
                    System.out.println("Setting mapBoundaryNum[" + row + "][" + col + "] = " + num);
                    mapBoundaryNum[row][col] = num;
                }
                row++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getTileImage() {
        try {
            tile[0] = new Tile();
            tile[0].tileImage = ImageIO.read(getClass().getResourceAsStream("/GameAssets/TilesPieces/Wood-flooring.png"));
            tile[1] = new Tile();
            tile[1].tileImage = ImageIO.read(getClass().getResourceAsStream("/GameAssets/TilesPieces/Rock-of-death.png"));
            tile[2] = new Tile();
            tile[2].tileImage = ImageIO.read(getClass().getResourceAsStream("/GameAssets/TilesPieces/wall.png"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2) {

        offsetX = Player.returnPlayerX();
        offsetY = Player.returnPlayerY();
        if (offsetX < 0) {
            offsetX = 0;
        }
        else if (offsetX > Main.worldWidth - Main.returnScreenWidth()) {
            offsetX = Main.worldWidth - Main.returnScreenWidth();
        }
        if (offsetY < 0) {
            offsetY = 0;
        }
        else if (offsetY > Main.worldHeight - Main.returnScreenHeight()) {
            offsetY = Main.worldHeight - Main.returnScreenHeight();
        }

        for (int row = 0; row < gp.maxWorldCol; row++) {
            for (int col = 0; col < gp.maxWorldRow; col++) {
                if (true) {
                    int x = col * Main.returnTileSize();
                    int y = row * Main.returnTileSize();
                    //g2.fillRect(x, y, Main.returnTileSize(), Main.returnTileSize());
                    try {
                        //System.out.println(mapTileNum[row][col]);

                        g2.drawImage(tile[mapTileNum[row][col]].tileImage, x - offsetX, y - offsetY, gp.tileSize, gp.tileSize, null);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }
        }
    }

}
