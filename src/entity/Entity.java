package entity;

import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.awt.Graphics2D;

public class Entity {
    GamePanel gp;
    public int worldX, worldY;
//    public int screenX, screenY;
    public int speed;

    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    public String direction;

    public int spriteCounter = 0;
    public int spriteNumber = 1;

    public Rectangle solidArea = new Rectangle(0, 0, 48, 48); // default solid area
    public int solidAreaDefaultX, solidAreaDefaultY;
    public boolean collisionOn = false;

    public int actionLockCounter = 0;

    public Entity(GamePanel gp) {
        this.gp = gp;
    }

    public void setAction(){}

    public void update(){
        setAction();

        collisionOn = false;
        gp.collisionChecker.checkTile(this);
        gp.collisionChecker.checkObject(this, false);
        gp.collisionChecker.checkPlayer(this);

        if(!collisionOn){
            switch(direction){
                case "up":
                    worldY -= speed;
                    break;
                case "down":
                    worldY += speed;
                    break;
                case "left":
                    worldX -= speed;
                    break;
                case "right":
                    worldX += speed;
                    break;
            }
        }
        // sprite changer
        spriteCounter++;
        if(spriteCounter > 12){ // every 10 frames, sprite alternates
            if(spriteNumber == 1){
                spriteNumber = 2;
            }
            else if(spriteNumber == 2){
                spriteNumber = 1;
            }
            spriteCounter = 0;
        }
    }

    public void draw(Graphics2D g2){
        BufferedImage image = null;

        // coordinates where objects will be drawn
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        // only draw object if it is just outside or inside the screen
        if((worldX + gp.tileSize > gp.player.worldX - gp.player.screenX) && (worldX - gp.tileSize < gp.player.worldX + gp.player.screenX)
                && (worldY + gp.tileSize > gp.player.worldY - gp.player.screenY) && (worldY - gp.tileSize < gp.player.worldY + gp.player.screenY)){

            switch(direction){
                case "up", "up-right", "up-left" :
                    if(spriteNumber == 1){
                        image = up1;
                    }
                    if(spriteNumber == 2){
                        image = up2;
                    }
                    break;
                case "down", "down-right", "down-left" :
                    if(spriteNumber == 1){
                        image = down1;
                    }
                    if(spriteNumber == 2){
                        image = down2;
                    }
                    break;
                case "left":
                    if(spriteNumber == 1){
                        image = left1;
                    }
                    if(spriteNumber == 2){
                        image = left2;
                    }
                    break;
                case "right":
                    if(spriteNumber == 1){
                        image = right1;
                    }
                    if(spriteNumber == 2){
                        image = right2;
                    }
                    break;
            }
            g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
        }
    }

    public BufferedImage setup(String imagePath){
        UtilityTool utilityTool = new UtilityTool();
        BufferedImage image = null;

        try{
            image = ImageIO.read(getClass().getClassLoader().getResourceAsStream(imagePath + ".png"));
            image = utilityTool.scaleImage(image, gp.tileSize, gp.tileSize);
        }
        catch(IOException e){
            e.printStackTrace();
        }

        return image;
    }
}
