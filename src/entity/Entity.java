package entity;

import main.GamePanel;
import main.UtilityTool;
import tile_interactive.InteractiveTile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

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
    public boolean alive = true;
    public boolean dying = false;
    boolean displayHealthBar = false;
    public boolean onPath = false;
    public boolean knockedBack = false;

    // COUNTERS
    public int spriteCounter = 0;
    public int actionLockCounter = 0;
    public int invincibleTimer = 0;
    public int shotAvailableCounter = 0;
    int dyingCounter = 0;
    int healthBarCounter = 0;
    int knockBackCounter = 0;

    // INVENTORY
    public ArrayList<Entity> inventory = new ArrayList<>();
    public final int maxInventorySize = 28; // 4 * 7 inventory

    // CHARACTER ATTRIBUTES
    public String name;
    public int speed;
    public int defaultSpeed;
    public int diagonalSpeed;
    public int maxHealth;
    public int health;
    public int maxMana;
    public int mana;
    public int ammo;
    public int level;
    public int strength;
    public int dexterity;
    public int attack;
    public int defense;
    public int exp;
    public int nextLevelExp;
    public int coin;
    public Entity currentWeapon;
    public Entity currentShield;
    public Projectile projectile;

    // ITEM ATTRIBUTES
    public int value;
    public int attackValue;
    public int defenseValue;
    public String description = "";
    public int useCost;
    public int price;
    public int knockBackPower = 0;
    public boolean stackable = false;
    public int amount = 1;
    public boolean opened = false;

    // TYPE
    public int type; // 0 = player, 1 = npc, 2 = monster, ...
    public final int type_player = 0;
    public final int type_npc = 1;
    public final int type_monster = 2;
    public final int type_sword = 3;
    public final int type_axe = 4;
    public final int type_shield = 5;
    public final int type_consumable = 6;
    public final int type_pickupOnly = 7;
    public final int type_obstacle = 8;

    public Entity(GamePanel gp) {
        this.gp = gp;
    }

    public boolean use(Entity entity){return false;}

    public void setAction(){}

    public void damageReaction(){

    }

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

    public void interact(){

    }

    public int getDetected(Entity user, Entity target[][], String targetName){
        int index = 999;

        // CHECK SURROUNDING OBJECT
        int nextWorldX = user.getLeftX();
        int nextWorldY = user.getTopY();

        switch(user.direction){
            case "up":
                nextWorldY = user.getTopY() - user.speed;
                break;
            case "down":
                nextWorldY = user.getBottomY() + user.speed;
                break;
            case "left":
                nextWorldX = user.getLeftX() - user.speed;
                break;
            case "right":
                nextWorldX = user.getRightX() + user.speed;
                break;
        }

        int col = nextWorldX / gp.tileSize;
        int row = nextWorldY / gp.tileSize;

        for(int i = 0; i < target[gp.currentMap].length; i++){
            if(target[gp.currentMap][i] != null){
                if((target[gp.currentMap][i].getCol() == col) &&
                        (target[gp.currentMap][i].getRow() == row) &&
                        (target[gp.currentMap][i].name.equals(targetName))){
                    index = i;
                    break;
                }
            }
        }

        return index;
    }

    public void dyingAnimation(Graphics2D g2){
        dyingCounter++;

        // flashing death animation -- could replace with death animation textures in future
        int i = 5;
        if(dyingCounter <= i){
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0F));
        }
        if(dyingCounter > i && dyingCounter <= i * 2){
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1F));
        }
        if(dyingCounter > i * 2 && dyingCounter <= i * 3){
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0F));
        }
        if(dyingCounter > i * 3 && dyingCounter <= i * 4){
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1F));
        }
        if(dyingCounter > i * 4 && dyingCounter <= i * 5){
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0F));
        }
        if(dyingCounter > i * 5 && dyingCounter <= i * 6){
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1F));
        }
        if(dyingCounter > i * 6 && dyingCounter <= i * 7){
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0F));
        }
        if(dyingCounter > i * 7 && dyingCounter <= i * 8){
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1F));
        }
        if (dyingCounter > i * 8) {
            alive = false;
        }
    }

    public void checkDrop(){

    }

    public void dropItem(Entity droppedItem){
        for(int i = 0; i < gp.obj[gp.currentMap].length; i++){
            if(gp.obj[gp.currentMap][i] == null){
                // place dropped item where killed entity was
                gp.obj[gp.currentMap][i] = droppedItem;
                gp.obj[gp.currentMap][i].worldX = worldX;
                gp.obj[gp.currentMap][i].worldY = worldY;
                break; // prevents from going through all null slots
            }
        }
    }

    public void checkCollision(){
        // CHECK COLLISION
        collisionOn = false;
        // tile collision
        gp.collisionChecker.checkTile(this);
        gp.collisionChecker.checkEntity(this, gp.interactiveTile);

        // object collision
        gp.collisionChecker.checkObject(this, false);

        // collision with different types of entities
        gp.collisionChecker.checkEntity(this, gp.npc);
        gp.collisionChecker.checkEntity(this, gp.monster);
        boolean contactPlayer = gp.collisionChecker.checkPlayer(this);

        if(this.type == type_monster && contactPlayer){ // type == monster
            damagePlayer(attack);
        }
    }

    public void update(){
        if(knockedBack){
            checkCollision();

            if(collisionOn){
                knockBackCounter = 0;
                knockedBack = false;
                speed = defaultSpeed;
            }
            else{
                switch(gp.player.direction){
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
            knockBackCounter++;
            if(knockBackCounter > 10){
                knockBackCounter = 0;
                knockedBack = false;
                speed = defaultSpeed;
            }
        }
        else{
            setAction();
            checkCollision();

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
        }

        // sprite changer
        spriteCounter++;
        if(spriteCounter > 24){ // every 10 frames, sprite alternates
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
        if(shotAvailableCounter < 30){
            shotAvailableCounter++;
        }
    }

    public void damagePlayer(int attack){
        if(!gp.player.invincible){
            // damage player
            gp.playSoundEffect(6); // receive damage sound

            int damage = attack - gp.player.defense;
            if(damage < 0){
                damage = 0;
            }
            gp.player.health -= damage;
            gp.player.invincible = true;
            if(gp.player.health <= 0){
                gp.player.health = 0;
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

            // monster hp bar
            if(type == 2 && displayHealthBar){
                double oneHealthScale = (double)gp.tileSize / maxHealth;
                double healthBarValue = oneHealthScale * health;

                // render health bar
                int healthBarHeight = 10;
                g2.setColor(new Color(0, 0, 0));
                g2.fillRect(screenX - 2, screenY - 10, gp.tileSize + 4, healthBarHeight + 4);
                g2.setColor(new Color(255, 0, 30));
                g2.fillRect(screenX, screenY - 8, (int)healthBarValue, healthBarHeight);

                // health bar disappears after 5 seconds of not hitting monster
                healthBarCounter++;

                if(healthBarCounter > 300){
                    displayHealthBar = false;
                    healthBarCounter = 0;
                }
            }

            // lower opacity when invincible -- NOT ON INTERACTIVE TILES
            if(!(this instanceof InteractiveTile)){
                if(invincible){
                    displayHealthBar = true;
                    healthBarCounter = 0; // reset counter every time monster is hit
                    g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4F));
                }
            }

            if(dying){
                dyingAnimation(g2);
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

    // PARTICLES
    public Color getParticleColor(){
        Color color = null;
        return color;
    }

    public int getParticleSize(){
        int size = 0;
        return size;
    }

    public int getParticleSpeed(){
        int speed = 0;
        return speed;
    }

    public int getParticleMaxHealth(){
        int maxHealth = 0;
        return maxHealth;
    }

    public void generateParticle(Entity generator, Entity target){
        Color color = generator.getParticleColor();
        int size = generator.getParticleSize();
        int speed = generator.getParticleSpeed();
        int maxHealth = generator.getParticleMaxHealth();

        Particle p1 = new Particle(gp, target, color, size, speed, maxHealth, -1, -1);
        Particle p2 = new Particle(gp, target, color, size, speed, maxHealth, -2, -1);
        Particle p3 = new Particle(gp, target, color, size, speed, maxHealth, 1, -2);
        Particle p4 = new Particle(gp, target, color, size, speed, maxHealth, 0, -1);
        gp.particleList.add(p1);
        gp.particleList.add(p2);
        gp.particleList.add(p3);
        gp.particleList.add(p4);
    }

    public void searchPath(int goalCol, int goalRow, boolean followingPlayer){
        int startCol = (worldX + solidArea.x) / gp.tileSize;
        int startRow = (worldY + solidArea.y) / gp.tileSize;
        gp.pathFinder.setNode(startCol, startRow, goalCol, goalRow, this);

        if(gp.pathFinder.search()){ // found path
            // next worldX and worldY
            int nextX = gp.pathFinder.pathList.getFirst().col * gp.tileSize;
            int nextY = gp.pathFinder.pathList.getFirst().row * gp.tileSize;

            // entity's solidArea positions
            int entityLeftX = worldX + solidArea.x;
            int entityRightX = worldX + solidArea.x + solidArea.width;
            int entityTopY = worldY + solidArea.y;
            int entityBottomY = worldY + solidArea.y + solidArea.height;

            // next is above entity
            if((entityTopY > nextY) && (entityLeftX >= nextX) && (entityRightX < nextX + gp.tileSize)){
                direction = "up";
            }
            // next is below entity
            else if((entityTopY < nextY) && (entityLeftX >= nextX) && (entityRightX < nextX + gp.tileSize)){
                direction = "down";
            }
            // next is on either side of entity
            else if((entityTopY >= nextY) && (entityBottomY < nextY + gp.tileSize)){
                // left or right
                if(entityLeftX > nextX){
                    direction = "left";
                }
                else if(entityLeftX < nextX){
                    direction = "right";
                }
            }
            // next is up and left
            else if((entityTopY > nextY) && (entityLeftX > nextX)){
                // up or left
                direction = "up";
                checkCollision();
                if(collisionOn){
                    direction = "left";
                }
            }
            // next is up and right
            else if((entityTopY > nextY) && (entityLeftX < nextX)){
                // up or right
                direction = "up";
                checkCollision();
                if(collisionOn){
                    direction = "right";
                }
            }
            // next is down and left
            else if((entityTopY < nextY) && (entityLeftX > nextX)){
                // down or left
                direction = "down";
                checkCollision();
                if(collisionOn){
                    direction = "left";
                }
            }
            // next is down and right
            else if((entityTopY < nextY) && (entityLeftX < nextX)){
                // down or right
                direction = "down";
                checkCollision();
                if(collisionOn){
                    direction = "right";
                }
            }

            if(!followingPlayer){
                int nextCol = gp.pathFinder.pathList.getFirst().col;
                int nextRow = gp.pathFinder.pathList.getFirst().row;
                // if reaches goal, stops following path
                if(nextCol == goalCol && nextRow == goalRow){
                    onPath = false;
                }
            }
        }
    }

    // getter functions
    public int getLeftX(){
        return worldX + solidArea.x;
    }

    public int getRightX(){
        return worldX + solidArea.x + solidArea.width;
    }

    public int getTopY(){
        return worldY + solidArea.y;
    }
    public int getBottomY(){
        return worldY + solidArea.y + solidArea.height;
    }

    public int getCol(){
        return (worldX + solidArea.x) / gp.tileSize;
    }

    public int getRow(){
        return (worldY + solidArea.y) / gp.tileSize;
    }


    // setup with custom scale for width and height
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

    // overload setup to default scale to gp.tileSize
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
