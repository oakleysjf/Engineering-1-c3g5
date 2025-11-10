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
        this.gp = gp;

        mapTileNum = new int[gp.maxWorldRow][gp.maxWorldCol];
        mapBoundaryNum = new int[gp.maxWorldRow][gp.maxWorldCol];



        tile = new Tile[10];
        loadMap("Greenfield/src/GameAssets/WorldMapInfo/World-Map.txt");
        loadBoundary("Greenfield/src/GameAssets/WorldMapInfo/World-Boundaries.txt");
        getTileImage();
    }

    public void getTileImage() {
        try {
            tile[0] = new Tile();
            tile[0].tileImage = ImageIO.read(getClass().getResourceAsStream("/GameAssets/TilesPieces/Wood-flooringD.png"));
            tile[1] = new Tile();
            tile[1].tileImage = ImageIO.read(getClass().getResourceAsStream("/GameAssets/TilesPieces/Empty.png"));
            tile[2] = new Tile();
            tile[2].tileImage = ImageIO.read(getClass().getResourceAsStream("/GameAssets/TilesPieces/Wall-End.png"));
            tile[3] = new Tile();
            tile[3].tileImage = ImageIO.read(getClass().getResourceAsStream("/GameAssets/TilesPieces/Wall-Floor.png"));
            tile[4] = new Tile();
            tile[4].tileImage = ImageIO.read(getClass().getResourceAsStream("/GameAssets/TilesPieces/Wall-Middle-V.png"));
            tile[4] = new Tile();
            tile[4].tileImage = ImageIO.read(getClass().getResourceAsStream("/GameAssets/TilesPieces/Wall-Middle-V.png"));
            tile[5] = new Tile();
            tile[5].tileImage = ImageIO.read(getClass().getResourceAsStream("/GameAssets/TilesPieces/Wall-Middle-H.png"));
            tile[6] = new Tile();
            tile[6].tileImage = ImageIO.read(getClass().getResourceAsStream("/GameAssets/TilesPieces/Wall-N.png"));
            tile[7] = new Tile();
            tile[7].tileImage = ImageIO.read(getClass().getResourceAsStream("/GameAssets/TilesPieces/Wall-S.png"));
            tile[8] = new Tile();
            tile[8].tileImage = ImageIO.read(getClass().getResourceAsStream("/GameAssets/TilesPieces/Wall-W.png"));
            tile[9] = new Tile();
            tile[9].tileImage = ImageIO.read(getClass().getResourceAsStream("/GameAssets/TilesPieces/Wall-E.png"));

        } catch (Exception e) {
            e.printStackTrace();
        }
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
            //e.printStackTrace();
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

        for (int col = 0; col < gp.maxWorldCol; col++) {
            for (int row = 0; row < gp.maxWorldRow; row++) {
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
