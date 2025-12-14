package entity;

import main.GamePanel;
import main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends Entity{
    GamePanel gp;
    KeyHandler keyHandler;

    public final int screenX;
    public final int screenY;

    public Player(GamePanel gp, KeyHandler keyHandler) {
        this.gp = gp;
        this.keyHandler = keyHandler;

        screenX = (gp.screenWidth / 2) - (gp.tileSize / 2);
        screenY = (gp.screenHeight / 2) - (gp.tileSize / 2);

        solidArea = new Rectangle(8, 16, 32, 32); // collision box -> starts at 8,16 and is 32x32px

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
        try{
            up1 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("player/walk/boy_up_1.png"));
            up2 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("player/walk/boy_up_2.png"));
            down1 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("player/walk/boy_down_1.png"));
            down2 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("player/walk/boy_down_2.png"));
            left1 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("player/walk/boy_left_1.png"));
            left2 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("player/walk/boy_left_2.png"));
            right1 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("player/walk/boy_right_1.png"));
            right2 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("player/walk/boy_right_2.png"));

        }
        catch(IOException e){
            e.printStackTrace();
        }
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

        g.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
    }
}
