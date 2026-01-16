package entity;

import main.GamePanel;
import main.KeyHandler;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends Entity{
    KeyHandler keyHandler;
    public final int screenX;
    public final int screenY;
    int standCounter = 0;

    public Player(GamePanel gp, KeyHandler keyHandler) {
        super(gp); // call constructor up Entity class and pass in gp
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
        worldX = gp.tileSize * 2;
        worldY = gp.tileSize * 5;

        // health
        maxHealth = 6;
        health = maxHealth;

        speed = 4;
        diagonalSpeed = Math.toIntExact(Math.round(speed * (1 / Math.sqrt(2))));
    }

    public void getPlayerImage(){
        up1 = setup("player/walk/boy_up_1");
        up2 = setup("player/walk/boy_up_2");
        down1 = setup("player/walk/boy_down_1");
        down2 = setup("player/walk/boy_down_2");
        left1 = setup("player/walk/boy_left_1");
        left2 = setup("player/walk/boy_left_2");
        right1 = setup("player/walk/boy_right_1");
        right2 = setup("player/walk/boy_right_2");
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

            // CHECK NPC COLLISION
            int npcIndex = gp.collisionChecker.checkEntity(this, gp.npc);
            interactNPC(npcIndex);

            // CHECK MONSTER COLLISION
            int monsterIndex = gp.collisionChecker.checkEntity(this, gp.monster);
            interactMonster(monsterIndex);

            // CHECK EVENT
            gp.eventHandler.checkEvent();
            gp.keyHandler.enterPressed = false;

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
                    // diagonals
                    case "up-right":
                        worldX += diagonalSpeed;
                        worldY -= diagonalSpeed;
                        break;
                    case "up-left":
                        worldX -= diagonalSpeed;
                        worldY -= diagonalSpeed;
                        break;
                    case "down-right":
                        worldX += diagonalSpeed;
                        worldY += diagonalSpeed;
                        break;
                    case "down-left":
                        worldX -= diagonalSpeed;
                        worldY += diagonalSpeed;
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
        // invincibility timer after being hit
        if(invincible){
            invincibleTimer++;
            if(invincibleTimer > 60){
                invincible = false;
                invincibleTimer = 0;
            }
        }
    }

    public void pickupObject(int index) {
        if(index != 999){

        }
    }

    public void interactNPC(int index){
        if(index != 999){
            if(gp.keyHandler.enterPressed) {
                gp.gameState = gp.dialogueState;
                gp.npc[index].speak();
            }
        }
    }

    public void interactMonster(int index){
        if(index != 999){
            if(!invincible){
                health -= 1;
                invincible = true;
                if(health <= 0){ // prevents player health from going negative
                    health = 0;
                }
            }
        }
    }

    public void draw(Graphics2D g){
        BufferedImage image = null;

        switch(direction){
            case "up" :
                if(spriteNumber == 1){
                    image = up1;
                }
                if(spriteNumber == 2){
                    image = up2;
                }
                break;
            case "down":
                if(spriteNumber == 1){
                    image = down1;
                }
                if(spriteNumber == 2){
                    image = down2;
                }
                break;
            case "left", "up-left", "down-left":
                if(spriteNumber == 1){
                    image = left1;
                }
                if(spriteNumber == 2){
                    image = left2;
                }
                break;
            case "right", "up-right", "down-right":
                if(spriteNumber == 1){
                    image = right1;
                }
                if(spriteNumber == 2){
                    image = right2;
                }
                break;
        }

        // lower opacity when invincible
        if(invincible){
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3F));
        }

        g.drawImage(image, screenX, screenY, null);

        // reset opacity
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1F));

        // show collision box
//        g.setColor(Color.RED);
//        g.drawRect(screenX + solidArea.x, screenY + solidArea.y, solidArea.width, solidArea.height);
    }
}
