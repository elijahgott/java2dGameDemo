package tile;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import main.UtilityTool;

public class TileManager {
    GamePanel gp;
    public Tile[] tile;
    public int mapTileNum[][][];

    UtilityTool utilityTool;

    public TileManager(GamePanel gp) {
        this.gp = gp;

        tile = new Tile[100]; // 100 different types of tiles
        mapTileNum = new int[gp.maxMap][gp.maxWorldCol][gp.maxWorldRow];

        getTileImage();
        loadMap("/maps/world01.txt", 0); // main world map, index 0
        loadMap("/maps/interior01.txt", 1); // house interior, index 1
    }

    public void getTileImage(){
        int i = 0;

        // grass tiles
        setup(i, "grass/grass_00", false); i++; // 0
        setup(i, "grass/grass_01", false); i++; // 1
        setup(i, "grass/grass_02", false); i++; // 2
        setup(i, "grass/grass_left", false); i++; // 3
        setup(i, "grass/grass_right", false); i++; // 4
        setup(i, "grass/grass_bottomLeft", false); i++; // 5
        setup(i, "grass/grass_bottom", false); i++; // 6
        setup(i, "grass/grass_bottomRight", false); i++; // 7
        setup(i, "grass/grass_topLeft", false); i++; // 8
        setup(i, "grass/grass_top", false); i++; // 9
        setup(i, "/grass/grass_topRight", false); i++; // 10
        // i may need these at some point
        // using grass_00 as placeholder for grass_inside_topLeft
        setup(i, "grass/grass_00", false); i++; // grass_inside_topLeft // 11
        setup(i, "grass/grass_00", false); i++; // grass_inside_topRight // 12
        setup(i, "grass/grass_00", false); i++; // grass_inside_bottomLeft // 13
        setup(i, "grass/grass_00", false); i++; // grass_inside_bottomRight // 14

        // grass-sand tiles
        setup(i, "grass-sand/grass_sand_left", false); i++; // 15
        setup(i, "grass-sand/grass_sand_right", false); i++; // 16
        setup(i, "grass-sand/grass_sand_bottomLeft", false); i++; // 17
        setup(i, "grass-sand/grass_sand_bottom", false); i++; // 18
        setup(i, "grass-sand/grass_sand_bottomRight", false); i++; // 19
        setup(i, "grass-sand/grass_sand_topLeft", false); i++; // 20
        setup(i, "grass-sand/grass_sand_top", false); i++; // 21
        setup(i, "grass-sand/grass_sand_topRight", false); i++; // 22
        setup(i, "grass-sand/grass_sand_inside_topLeft", false); i++; // 23
        setup(i, "grass-sand/grass_sand_inside_topRight", false); i++; // 24
        setup(i, "grass-sand/grass_sand_inside_bottomLeft", false); i++; // 25
        setup(i, "grass-sand/grass_sand_inside_bottomRight", false); i++; // 26

        // path tiles
        setup(i, "path/path_00", false); i++; // 27
        setup(i, "path/path_01", false); i++; // 28
        setup(i, "path/path_02", false); i++; // 29
        setup(i, "path/path_left", false); i++; // 30
        setup(i, "path/path_right", false); i++; // 31
        setup(i, "path/path_bottomLeft", false); i++; // 32
        setup(i, "path/path_bottom", false); i++; // 33
        setup(i, "path/path_bottomRight", false); i++; // 34
        setup(i, "path/path_topLeft", false); i++; // 35
        setup(i, "path/path_top", false); i++; // 36
        setup(i, "path/path_topRight", false); i++; // 37
        setup(i, "path/path_inside_topLeft", false); i++; // 38
        setup(i, "path/path_inside_topRight", false); i++; // 39
        setup(i, "path/path_inside_bottomLeft", false); i++; // 40
        setup(i, "path/path_inside_bottomRight", false); i++; // 41

        // sand tiles
        setup(i, "sand/sand_00", false); i++; // 42
        setup(i, "sand/sand_01", false); i++; // 43
        setup(i, "sand/sand_left", false); i++; // 44
        setup(i, "sand/sand_right", false); i++; // 45
        setup(i, "sand/sand_bottomLeft", false); i++; // 46
        setup(i, "sand/sand_bottom", false); i++; // 47
        setup(i, "sand/sand_bottomRight", false); i++; // 48
        setup(i, "sand/sand_topLeft", false); i++; // 49
        setup(i, "sand/sand_top", false); i++; // 50
        setup(i, "sand/sand_topRight", false); i++; // 51
        setup(i, "sand/sand_inside_topLeft", false); i++; // 52
        setup(i, "sand/sand_inside_topRight", false); i++; // 53
        setup(i, "sand/sand_inside_bottomLeft", false); i++; // 54
        setup(i, "sand/sand_inside_bottomRight", false); i++; // 55

        // water tiles
        setup(i, "water/water_00", true); i++; // 56
        setup(i, "water/water_01", true); i++; // 57
        setup(i, "water/water_02", true); i++; // 58
        setup(i, "water/water_left", true); i++; // 59
        setup(i, "water/water_right", true); i++; // 60
        setup(i, "water/water_bottomLeft", true); i++; // 61
        setup(i, "water/water_bottom", true); i++; // 62
        setup(i, "water/water_bottomRight", true); i++; // 63
        setup(i, "water/water_topLeft", true); i++; // 64
        setup(i, "water/water_top", true); i++; // 65
        setup(i, "water/water_topRight", true); i++; // 66
        setup(i, "water/water_inside_topLeft", true); i++; // 67
        setup(i, "water/water_inside_topRight", true); i++; // 68
        setup(i, "water/water_inside_bottomLeft", true); i++; // 69
        setup(i, "water/water_inside_bottomRight", true); i++; // 70

        // tree tiles
        setup(i, "trees/tree_01", true); i++; // 71
        setup(i, "trees/tree_02", true); i++; // 72

        // stone brick wall tiles
        setup(i, "walls/wall_middle", false); i++; // 73
        setup(i, "walls/stone_brick_wall_left", true); i++; // 74
        setup(i, "walls/stone_brick_wall_right", true); i++; // 75
        setup(i, "walls/stone_brick_wall_bottomLeft", true); i++; // 76
        setup(i, "walls/stone_brick_wall_bottom", true); i++; // 77
        setup(i, "walls/stone_brick_wall_bottomRight", true); i++; // 78
        setup(i, "walls/stone_brick_wall_topLeft", true); i++; // 79
        setup(i, "walls/stone_brick_wall_top", true); i++; // 80
        setup(i, "walls/stone_brick_wall_topRight", true); i++; // 81
    }

    public void setup(int index, String imageName, boolean collision){
        UtilityTool utilityTool = new UtilityTool();

        try{
            tile[index] = new Tile();
            tile[index].image = ImageIO.read(getClass().getResourceAsStream("/tiles/" + imageName + ".png"));
            tile[index].image = utilityTool.scaleImage(tile[index].image, gp.tileSize, gp.tileSize);
            tile[index].collision = collision;
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    public void loadMap(String filePath, int mapIndex){
        try{
            InputStream is = getClass().getResourceAsStream(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int col = 0;
            int row = 0;

            while((col < gp.maxWorldCol) && (row < gp.maxWorldRow)){
                String line = br.readLine();

                while(col < gp.maxWorldCol){
                    String[] numbers = line.split(" ");

                    int num = Integer.parseInt(numbers[col]);

                    mapTileNum[mapIndex][col][row] = num;
                    col++;
                }
                if(col == gp.maxWorldCol){
                    col = 0;
                    row++;
                }
            }
            br.close();
        }
        catch(Exception e){
            System.out.println(e);
        }
    }

    public void draw(Graphics2D g2){
        int worldCol = 0;
        int worldRow = 0;

        while((worldCol < gp.maxWorldCol) && (worldRow < gp.maxWorldRow)){
            int tileNum = mapTileNum[gp.currentMap][worldCol][worldRow];

            int worldX = worldCol * gp.tileSize;
            int worldY = worldRow * gp.tileSize;

            // coordinates where tiles will be drawn
            int screenX = worldX - gp.player.worldX + gp.player.screenX;
            int screenY = worldY - gp.player.worldY + gp.player.screenY;

            // only draw tile if it is just outside or inside the screen
            if((worldX + gp.tileSize > gp.player.worldX - gp.player.screenX) && (worldX - gp.tileSize < gp.player.worldX + gp.player.screenX)
            && (worldY + gp.tileSize > gp.player.worldY - gp.player.screenY) && (worldY - gp.tileSize < gp.player.worldY + gp.player.screenY)){
                g2.drawImage(tile[tileNum].image, screenX, screenY, null);

                if(gp.debug){ // grid coordinates for debugging
                    int x = screenX + (gp.tileSize / 4);
                    int y = screenY + (gp.tileSize / 2);
                    int lineHeight = 16;
                    g2.setColor(Color.black);
                    g2.setFont(new Font("Arial", Font.PLAIN, 12));
                    g2.setStroke(new BasicStroke(2));

                    g2.drawRect(screenX, screenY, gp.tileSize, gp.tileSize);
                    g2.drawString((worldCol) + ", " + (worldRow), x, y);
                    y += lineHeight;

                    g2.drawString(Integer.toString(tileNum), x, y);
                }
            }

            worldCol++;

            if(worldCol == gp.maxWorldCol){
                worldCol = 0;
                worldRow++;
            }
        }
    }
}
