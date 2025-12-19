package entity;

import main.GamePanel;
import main.KeyHandler;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends Entity{
    GamePanel gp;
    KeyHandler keyHandler;

    public final int screenX;
    public final int screenY;

    public int hasKey = 0;
    int standCounter = 0;

    public Player(GamePanel gp, KeyHandler keyHandler) {
        this.gp = gp;
        this.keyHandler = keyHandler;

        screenX = (gp.screenWidth / 2) - (gp.tileSize / 2);
        screenY = (gp.screenHeight / 2) - (gp.tileSize / 2);

        solidArea = new Rectangle(8, 16, 32, 32); // collision box -> starts at 8,16 and is 32x32px
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        setDefaultValues();
        getPlayerImage();
        direction = "down";
    }

    public void setDefaultValues(){
        // set default player location
        worldX = gp.tileSize * 6;
        worldY = gp.tileSize * 3;

        speed = 4;
    }

    public void getPlayerImage(){
        up1 = setup("boy_up_1");
        up2 = setup("boy_up_2");
        down1 = setup("boy_down_1");
        down2 = setup("boy_down_2");
        left1 = setup("boy_left_1");
        left2 = setup("boy_left_2");
        right1 = setup("boy_right_1");
        right2 = setup("boy_right_2");
    }

    public BufferedImage setup(String imageName){
        UtilityTool utilityTool = new UtilityTool();
        BufferedImage image = null;

        try{
            image = ImageIO.read(getClass().getClassLoader().getResourceAsStream("player/walk/" + imageName + ".png"));
            image = utilityTool.scaleImage(image, gp.tileSize, gp.tileSize);
        }
        catch(IOException e){
            e.printStackTrace();
        }

        return image;
    }

    public void update(){

        if(keyHandler.upPressed || keyHandler.downPressed ||
           keyHandler.leftPressed || keyHandler.rightPressed) {
            if(keyHandler.upPressed){
                direction = "up";
            }
            if(keyHandler.downPressed){
                direction = "down";
            }
            if(keyHandler.leftPressed){
                direction = "left";
            }
            if(keyHandler.rightPressed) {
                direction = "right";
            }
            if(keyHandler.upPressed && keyHandler.rightPressed){
                direction = "up-right";
            }
            if(keyHandler.upPressed && keyHandler.leftPressed){
                direction = "up-left";
            }
            if(keyHandler.downPressed && keyHandler.rightPressed){
                direction = "down-right";
            }
            if(keyHandler.downPressed && keyHandler.leftPressed){
                direction = "down-left";
            }

            // CHECK TILE COLLISION
            collisionOn = false;
            gp.collisionChecker.checkTile(this);

            // CHECK OBJECT COLLISION
            int objIndex = gp.collisionChecker.checkObject(this, true);
            pickupObject(objIndex);

            // IF COLLISION IS FALSE, PLAYER CAN MOVE
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
                    case "up-right":
                        worldX += speed;
                        worldY -= speed;
                        break;
                    case "up-left":
                        worldX -= speed;
                        worldY -= speed;
                        break;
                    case "down-right":
                        worldX += speed;
                        worldY += speed;
                        break;
                    case "down-left":
                        worldX -= speed;
                        worldY += speed;
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
        else{ // player not moving
            standCounter++;
            if(standCounter == 20){ // 20 frame buffer before switching back to default stance
                spriteNumber = 1;
                standCounter = 0;
            }
        }
    }

    public void pickupObject(int index) {
        if(index != 999){
            String objectName = gp.obj[index].name;

            switch(objectName){
                case "Key":
                    gp.playSoundEffect(1); // coin sound effect
                    hasKey++;
                    gp.obj[index] = null;
                    gp.ui.showMessage("You picked up a key!");
                    break;
                case "Door":
                    if(hasKey > 0){
                        gp.playSoundEffect(3); // unlock sound effect
                        gp.obj[index] = null;
                        hasKey--;
                        gp.ui.showMessage("You opened a door!");
                    }
                    else{
                        gp.ui.showMessage("You don't have a key...");
                    }
                    break;
                case "Boots":
                    gp.playSoundEffect(2); // power up sound effect
                    speed += 2;
                    gp.obj[index] = null;
                    gp.ui.showMessage("You feel faster!");
                    break;
                case "Chest":
                    gp.ui.gameOver = true;
                    gp.stopMusic();
                    gp.playSoundEffect(4);
                    break;
            }
        }
    }

    public void draw(Graphics g){
        BufferedImage image = null;

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

        g.drawImage(image, screenX, screenY, null);
        // show collision box
//        g.setColor(Color.RED);
//        g.drawRect(screenX + solidArea.x, screenY + solidArea.y, solidArea.width, solidArea.height);
    }
}
