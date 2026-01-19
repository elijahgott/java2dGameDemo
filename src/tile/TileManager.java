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
    public int mapTileNum[][];

    UtilityTool utilityTool;

    public TileManager(GamePanel gp) {
        this.gp = gp;

        tile = new Tile[100]; // 100 different types of tiles
        mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];

        getTileImage();
        loadMap("/maps/world01.txt");
    }

    public void getTileImage(){
        // grass tiles
        setup(0, "grass/grass_00", false);
        setup(1, "grass/grass_01", false);
        setup(2, "grass/grass_02", false);

        setup(3, "grass/grass_left", false);
        setup(4, "grass/grass_right", false);

        setup(5, "grass/grass_bottomLeft", false);
        setup(6, "grass/grass_bottom", false);
        setup(7, "grass/grass_bottomRight", false);

        setup(8, "grass/grass_topLeft", false);
        setup(9, "grass/grass_top", false);
        setup(10, "grass/grass_topRight", false);

        // i may need these at some point
        // using grass_00 as placeholder for grass_inside_topLeft
        // error here when on laptop (why?)
        setup(11, "grass/grass_00", false); // grass_inside_topLeft
        setup(12, "grass/grass_00", false); // grass_inside_topRight
        setup(13, "grass/grass_00", false); // grass_inside_bottomLeft
        setup(14, "grass/grass_00", false); // grass_inside_bottomRight

        // grass-sand tiles
        setup(15, "grass-sand/grass_sand_left", false);
        setup(16, "grass-sand/grass_sand_right", false);

        setup(17, "grass-sand/grass_sand_bottomLeft", false);
        setup(18, "grass-sand/grass_sand_bottom", false);
        setup(19, "grass-sand/grass_sand_bottomRight", false);

        setup(20, "grass-sand/grass_sand_topLeft", false);
        setup(21, "grass-sand/grass_sand_top", false);
        setup(22, "grass-sand/grass_sand_topRight", false);

        setup(23, "grass-sand/grass_sand_inside_topLeft", false);
        setup(24, "grass-sand/grass_sand_inside_topRight", false);
        setup(25, "grass-sand/grass_sand_inside_bottomLeft", false);
        setup(26, "grass-sand/grass_sand_inside_bottomRight", false);

        // path tiles
        setup(27, "path/path_00", false);
        setup(28, "path/path_01", false);
        setup(29, "path/path_02", false);

        setup(30, "path/path_left", false);
        setup(31, "path/path_right", false);

        setup(32, "path/path_bottomLeft", false);
        setup(33, "path/path_bottom", false);
        setup(34, "path/path_bottomRight", false);

        setup(35, "path/path_topLeft", false);
        setup(36, "path/path_top", false);
        setup(37, "path/path_topRight", false);

        setup(38, "path/path_inside_topLeft", false);
        setup(39, "path/path_inside_topRight", false);
        setup(40, "path/path_inside_bottomLeft", false);
        setup(41, "path/path_inside_bottomRight", false);

        // sand tiles
        setup(42, "sand/sand_00", false);
        setup(43, "sand/sand_01", false);

        setup(44, "sand/sand_left", false);
        setup(45, "sand/sand_right", false);

        setup(46, "sand/sand_bottomLeft", false);
        setup(47, "sand/sand_bottom", false);
        setup(48, "sand/sand_bottomRight", false);

        setup(49, "sand/sand_topLeft", false);
        setup(50, "sand/sand_top", false);
        setup(51, "sand/sand_topRight", false);

        setup(52, "sand/sand_inside_topLeft", false);
        setup(53, "sand/sand_inside_topRight", false);
        setup(54, "sand/sand_inside_bottomLeft", false);
        setup(55, "sand/sand_inside_bottomRight", false);

        // water tiles
        setup(56, "water/water_00", true);
        setup(57, "water/water_01", true);
        setup(58, "water/water_02", true);

        setup(59, "water/water_left", true);
        setup(60, "water/water_right", true);

        setup(61, "water/water_bottomLeft", true);
        setup(62, "water/water_bottom", true);
        setup(63, "water/water_bottomRight", true);

        setup(64, "water/water_topLeft", true);
        setup(65, "water/water_top", true);
        setup(66, "water/water_topRight", true);

        // do i need inside waters??
        // using water_00 as placeholder for insides
        setup(67, "water/water_00", false); // water_inside_topLeft
        setup(68, "water/water_00", false); // water_inside_topRight
        setup(69, "water/water_00", false); // water_inside_bottomLeft
        setup(70, "water/water_00", false); // water_inside_bottomRight

        // tree tiles
        setup(71, "trees/tree_01", true);
        setup(72, "trees/tree_02", true);

        // stone brick wall tiles
        setup(73, "walls/wall_middle", true);

        setup(74, "walls/stone_brick_wall_left", true);
        setup(75, "walls/stone_brick_wall_right", true);

        setup(76, "walls/stone_brick_wall_bottomLeft", true);
        setup(77, "walls/stone_brick_wall_bottom", true);
        setup(78, "walls/stone_brick_wall_bottomRight", true);

        setup(79, "walls/stone_brick_wall_topLeft", true);
        setup(80, "walls/stone_brick_wall_top", true);
        setup(81, "walls/stone_brick_wall_topRight", true);
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

    public void loadMap(String filePath){
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

                    mapTileNum[col][row] = num;
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
            int tileNum = mapTileNum[worldCol][worldRow];

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
                    g2.setColor(Color.black);
                    g2.drawRect(screenX, screenY, gp.tileSize, gp.tileSize);
                    g2.drawString((worldCol) + ", " + (worldRow), screenX + (gp.tileSize / 4), screenY + (gp.tileSize / 2));
                    g2.drawString(Integer.toString(tileNum), screenX + (gp.tileSize / 4), screenY + (gp.tileSize - (gp.tileSize / 4)));
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
