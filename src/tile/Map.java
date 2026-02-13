package tile;

import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Map extends TileManager{
    GamePanel gp;
    BufferedImage worldMap[];
    public boolean minimapOn = false;

    public Map(GamePanel gp){
        super(gp);
        this.gp = gp;

        createWorldMap();
    }

    public void createWorldMap(){
        worldMap = new BufferedImage[gp.maxMap];
        int worldMapWidth = gp.tileSize * gp.maxWorldCol;
        int worldMapHeight = gp.tileSize * gp.maxWorldRow;

        for(int i = 0; i < gp.maxMap; i++){
            worldMap[i] = new BufferedImage(worldMapWidth, worldMapHeight, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = (Graphics2D)worldMap[i].createGraphics();

            int col = 0;
            int row = 0;

            while(col < gp.maxWorldCol && row < gp.maxWorldRow){
                int tileNum = mapTileNum[i][col][row];
                int x = gp.tileSize * col;
                int y = gp.tileSize * row;

                // replace with tree texture
                if(tileNum == 71 ||  tileNum == 72){
                    tileNum = 82;
                }

                g2.drawImage(tile[tileNum].image, x, y, null);
                col++;

                if(col == gp.maxWorldCol){
                    col = 0;
                    row++;
                }
            }
            g2.dispose();
        }
    }

    public void drawFullMapScreen(Graphics2D g2){
        // background color
        g2.setColor(new Color(65, 146, 195));
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        // draw map
        int width = 500;
        int height = 500;
        int x = (gp.screenWidth - width) / 2;
        int y = (gp.screenHeight - height) / 2;
        g2.drawImage(worldMap[gp.currentMap], x, y, width, height, null);

        // draw player
        double scale = (double)(gp.tileSize * gp.maxWorldCol) / width;
        int playerX = (int)(x + gp.player.worldX / scale);
        int playerY = (int)(y + gp.player.worldY / scale);
        int playerSize = (int)(gp.tileSize / 3);
        g2.drawImage(gp.player.down1, playerX, playerY, playerSize, playerSize, null);

        // hint message
        g2.setFont(gp.ui.dialogueFont.deriveFont(Font.PLAIN, 16F));
        g2.setColor(Color.BLACK);
        g2.drawString("[ESC] or [M] to close", (x + width) - 120, (y + height) + 28);
        g2.setColor(Color.WHITE);
        g2.drawString("[ESC] or [M] to close", (x + width) - 120 - 2, (y + height) + 28 - 2);
    }

    public void drawMiniMap(Graphics2D g2){
        if(minimapOn){
            // draw map
            int width = 150;
            int height = 150;
            int x = (gp.screenWidth - width) - (gp.tileSize / 2);
            int y = (gp.tileSize / 2);
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f));
            g2.drawImage(worldMap[gp.currentMap], x, y, width, height, null);

            // draw player
            double scale = (double)(gp.tileSize * gp.maxWorldCol) / width;
            int playerX = (int)(x + gp.player.worldX / scale) - 6;
            int playerY = (int)(y + gp.player.worldY / scale) - 6;
            int playerSize = (int)(gp.tileSize / 3);
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
            g2.drawImage(gp.player.down1, playerX, playerY, playerSize, playerSize, null);

            // border around map
            g2.setColor(Color.BLACK);
            g2.setStroke(new BasicStroke(5));
            g2.drawRect(x - 3, y - 3, width + 5, height + 5);
        }
    }
}
