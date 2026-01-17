package entity;

import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Entity {
    GamePanel gp;

    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    public BufferedImage attackUp1, attackUp2, attackDown1, attackDown2, attackLeft1, attackLeft2, attackRight1, attackRight2;
    public BufferedImage image, image2, image3;
    public Rectangle solidArea = new Rectangle(0, 0, 48, 48); // default solid area
    public Rectangle attackArea =  new Rectangle(0, 0, 0, 0);
    public int solidAreaDefaultX, solidAreaDefaultY;
    public boolean collision = false;
    String dialogues[] = new String[20];

    // STATE
    public int worldX, worldY;
    public String direction = "down";
    public int spriteNumber = 1;
    int dialogueIndex = 0;
    public boolean collisionOn = false;
    public boolean invincible = false;
    boolean attacking = false;

    // COUNTERS
    public int spriteCounter = 0;
    public int actionLockCounter = 0;
    public int invincibleTimer = 0;

    // CHARACTER ATTRIBUTES
    public int type; // 0 = player, 1 = npc, 2 = monster, ...
    public String name;
    public int speed;
    public int diagonalSpeed;
    public int maxHealth;
    public int health;

    public Entity(GamePanel gp) {
        this.gp = gp;
    }

    public void setAction(){}

    public void speak(){
        if(dialogues[dialogueIndex] == null){
            dialogueIndex = 0;
        }
        gp.ui.currentDialogue = dialogues[dialogueIndex];
        dialogueIndex++;

        switch(gp.player.direction){
            case "up":
                direction = "down";
                break;
            case "down":
                direction = "up";
                break;
            case "left":
                direction = "right";
                break;
            case "right":
                direction = "left";
                break;
        }
    }

    public void update(){
        setAction();

        collisionOn = false;
        gp.collisionChecker.checkTile(this);
        gp.collisionChecker.checkObject(this, false);
        gp.collisionChecker.checkEntity(this, gp.npc);
        gp.collisionChecker.checkEntity(this, gp.monster);
        boolean contactPlayer = gp.collisionChecker.checkPlayer(this);

        if(this.type == 2 && contactPlayer){ // type == monster
            if(!gp.player.invincible){
                // damage player
                gp.player.health -= 1;
                gp.player.invincible = true;
                if(gp.player.health <= 0){
                    gp.player.health = 0;
                }
            }
        }

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

        // invincibility timer after being hit
        if(invincible){
            invincibleTimer++;
            if(invincibleTimer > 60){
                invincible = false;
                invincibleTimer = 0;
            }
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
            // lower opacity when invincible
            if(invincible){
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4F));
            }

            g2.drawImage(image, screenX, screenY, null);

            // reset opacity
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1F));

            if(gp.debug){
                g2.setColor(new Color(255, 0, 0, 100));
                g2.fillRect(screenX + solidArea.x, screenY + solidArea.y, solidArea.width, solidArea.height); // only shows tileSize * tileSize box, need to update to reflect actual solidAreas
            }
        }
    }

    public BufferedImage setup(String imagePath, int width, int height){
        UtilityTool utilityTool = new UtilityTool();
        BufferedImage image = null;

        try{
            image = ImageIO.read(getClass().getClassLoader().getResourceAsStream(imagePath + ".png"));
            image = utilityTool.scaleImage(image, width, height);
        }
        catch(IOException e){
            e.printStackTrace();
        }

        return image;
    }
}
