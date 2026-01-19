package entity;

import main.GamePanel;
import main.KeyHandler;
import object.OBJ_Shield_Wood;
import object.OBJ_Sword_Normal;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Player extends Entity{
    KeyHandler keyHandler;
    public final int screenX;
    public final int screenY;
    int standCounter = 0;
    public boolean attackCanceled = false;

    public Player(GamePanel gp, KeyHandler keyHandler) {
        super(gp); // call constructor up Entity class and pass in gp
        this.keyHandler = keyHandler;

        screenX = (gp.screenWidth / 2) - (gp.tileSize / 2);
        screenY = (gp.screenHeight / 2) - (gp.tileSize / 2);

        solidArea = new Rectangle(8, 16, 32, 32); // collision box -> starts at 8,16 and is 32x32px
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        attackArea.width = 36;
        attackArea.height = 36;


        setDefaultValues();
        getPlayerImage();
        getPlayerAttackImage();
    }

    public void setDefaultValues(){
        // set default player location
        worldX = gp.tileSize * 2;
        worldY = gp.tileSize * 5;
        direction = "down";

        speed = 4;
        diagonalSpeed = Math.toIntExact(Math.round(speed * (1 / Math.sqrt(2))));

        // health
        maxHealth = 6;
        health = maxHealth;

        // default player stats
        level = 1;
        strength = 1; // more strength = more damage given
        dexterity = 1; // more dexterity = less damage received
        exp = 0;
        nextLevelExp = 5;
        coin = 0;

        // default loadout
        currentWeapon = new OBJ_Sword_Normal(gp);
        currentShield = new OBJ_Shield_Wood(gp);
        attack = getAttack();
        defense = getDefense();
    }

    public int getAttack(){
        return attack = strength * currentWeapon.attackValue;
    }

    public int getDefense(){
        return defense = dexterity * currentShield.defenseValue;
    }

    public void getPlayerImage(){
        up1 = setup("player/walk/boy_up_1", gp.tileSize, gp.tileSize);
        up2 = setup("player/walk/boy_up_2", gp.tileSize, gp.tileSize);
        down1 = setup("player/walk/boy_down_1", gp.tileSize, gp.tileSize);
        down2 = setup("player/walk/boy_down_2", gp.tileSize, gp.tileSize);
        left1 = setup("player/walk/boy_left_1", gp.tileSize, gp.tileSize);
        left2 = setup("player/walk/boy_left_2", gp.tileSize, gp.tileSize);
        right1 = setup("player/walk/boy_right_1", gp.tileSize, gp.tileSize);
        right2 = setup("player/walk/boy_right_2", gp.tileSize, gp.tileSize);
    }

    public void getPlayerAttackImage(){
        attackUp1 = setup("player/attack/boy_attack_up_1", gp.tileSize, gp.tileSize * 2);
        attackUp2 = setup("player/attack/boy_attack_up_2", gp.tileSize, gp.tileSize * 2);
        attackDown1 = setup("player/attack/boy_attack_down_1", gp.tileSize, gp.tileSize * 2);
        attackDown2 = setup("player/attack/boy_attack_down_2", gp.tileSize, gp.tileSize * 2);
        attackLeft1 = setup("player/attack/boy_attack_left_1", gp.tileSize * 2, gp.tileSize);
        attackLeft2 = setup("player/attack/boy_attack_left_2", gp.tileSize * 2, gp.tileSize);
        attackRight1 = setup("player/attack/boy_attack_right_1", gp.tileSize * 2, gp.tileSize);
        attackRight2 = setup("player/attack/boy_attack_right_2", gp.tileSize * 2, gp.tileSize);
    }

    public void update(){
        if(attacking){
            attack();
        }
        else if(keyHandler.upPressed || keyHandler.downPressed ||
           keyHandler.leftPressed || keyHandler.rightPressed || keyHandler.enterPressed || keyHandler.spacePressed) {
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

            // IF COLLISION IS FALSE, PLAYER CAN MOVE
            if(!collisionOn && (!keyHandler.enterPressed && !keyHandler.spacePressed)){
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

            if((keyHandler.enterPressed || keyHandler.spacePressed) && !attackCanceled){
                Random random = new Random();
                int i = random.nextInt(100) + 1; // random number from 1 - 100
                // 33% chance to play low, med, and high melee sounds
                if(i <= 33){
                    gp.playSoundEffect(7); // melee low
                }
                else if(i <= 66){
                    gp.playSoundEffect(8); // melee med
                }
                else{
                    gp.playSoundEffect(9); // melee high
                }
                attacking = true;
                spriteCounter = 0;
            }
            attackCanceled = false;

            gp.keyHandler.enterPressed = false;
            gp.keyHandler.spacePressed = false;

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

    public void attack(){
        spriteCounter++;
        if(spriteCounter <= 5){
            spriteNumber = 1;
        }
        if(spriteCounter > 5 && spriteCounter <= 25){
            spriteNumber = 2;

            // save current worldX, worldY, and solidArea
            int currentWorldX = worldX;
            int currentWorldY = worldY;
            int solidAreaWidth = solidArea.width;
            int solidAreaHeight = solidArea.height;

            // adjust player's worldX and worldY for the attackArea
            switch(direction){
                case "up":
                    worldY -= attackArea.height;
                    break;
                case "down":
                    worldY += attackArea.height;
                    break;
                case "left":
                    worldX -= attackArea.width;
                    break;
                case "right":
                    worldX += attackArea.width;
                    break;
            }

            // attackArea becomes solidArea
            solidArea.width = attackArea.width;
            solidArea.height = attackArea.height;

            // check monster collision with updated position
            int monsterIndex = gp.collisionChecker.checkEntity(this, gp.monster);
            damageMonster(monsterIndex);

            // restore position and solidArea
            worldX = currentWorldX;
            worldY = currentWorldY;
            solidArea.width = solidAreaWidth;
            solidArea.height = solidAreaHeight;
        }
        if(spriteCounter > 25){
            spriteNumber = 1;
            spriteCounter = 0;
            attacking = false;
        }
    }

    public void pickupObject(int index) {
        if(index != 999){

        }
    }

    public void interactNPC(int index){
        if(gp.keyHandler.enterPressed || gp.keyHandler.spacePressed){
            if(index != 999){
                attackCanceled = true;
                gp.gameState = gp.dialogueState;
                gp.npc[index].speak();
            }
        }

    }

    public void interactMonster(int index){
        if(index != 999){
            if(!invincible){
                gp.playSoundEffect(6); // received damage sound
                health -= 1;
                invincible = true;
                if(health <= 0){ // prevents player health from going negative
                    health = 0;
                }
            }
        }
    }

    public void damageMonster(int index){
        if(index != 999){
            // monster has been hit
            if(!gp.monster[index].invincible){
                gp.playSoundEffect(5); // hit monster sound
                gp.monster[index].health -= 1;
                gp.monster[index].invincible = true;
                gp.monster[index].damageReaction();

                // kill monster
                if(gp.monster[index].health <= 0){
                    gp.monster[index].dying = true;
                }
            }
        }
    }

    public void draw(Graphics2D g){
        BufferedImage image = null;
        int tempScreenX = screenX;
        int tempScreenY = screenY;

        switch(direction){
            case "up" :
                if(!attacking){
                    if(spriteNumber == 1){
                        image = up1;
                    }
                    if(spriteNumber == 2){
                        image = up2;
                    }
                }
                if(attacking){
                    tempScreenY = screenY - gp.tileSize;
                    if(spriteNumber == 1){
                        image = attackUp1;
                    }
                    if(spriteNumber == 2){
                        image = attackUp2;
                    }
                }
                break;
            case "down":
                if(!attacking){
                    if(spriteNumber == 1){
                        image = down1;
                    }
                    if(spriteNumber == 2){
                        image = down2;
                    }
                }
                if(attacking){
                    if(spriteNumber == 1){
                        image = attackDown1;
                    }
                    if(spriteNumber == 2){
                        image = attackDown2;
                    }
                }
                break;
            case "left", "up-left", "down-left":
                if(!attacking){
                    if(spriteNumber == 1){
                        image = left1;
                    }
                    if(spriteNumber == 2){
                        image = left2;
                    }
                }
                if(attacking){
                    tempScreenX = screenX - gp.tileSize;
                    if(spriteNumber == 1){
                        image = attackLeft1;
                    }
                    if(spriteNumber == 2){
                        image = attackLeft2;
                    }
                }
                break;
            case "right", "up-right", "down-right":
                if(!attacking){
                    if(spriteNumber == 1){
                        image = right1;
                    }
                    if(spriteNumber == 2){
                        image = right2;
                    }
                }
                if(attacking){
                    if(spriteNumber == 1){
                        image = attackRight1;
                    }
                    if(spriteNumber == 2){
                        image = attackRight2;
                    }
                }
                break;
        }

        // lower opacity when invincible
        if(invincible){
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.6F));
        }

        g.drawImage(image, tempScreenX, tempScreenY, null);

        // reset opacity
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1F));

        // show collision box
        if(gp.debug){
            g.setColor(new Color(255, 0, 0, 100));
            g.fillRect(screenX + solidArea.x, screenY + solidArea.y, solidArea.width, solidArea.height);
        }
    }
}
