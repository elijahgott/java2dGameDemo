package monster;

import entity.Entity;
import main.GamePanel;
import object.OBJ_Coin;
import object.OBJ_Heart;
import object.OBJ_ManaCrystal;
import object.OBJ_Rock;

import java.util.Random;

public class MON_GreenSlime extends Entity {

    GamePanel gp;
    public MON_GreenSlime(GamePanel gp) {
        super(gp);
        this.gp = gp;

        name = "Green Slime";
        type = type_monster;
        speed = 1;
        maxHealth = 4;
        health = maxHealth;
        attack = 5;
        defense = 0;
        exp = 2;
        projectile = new OBJ_Rock(gp);

        solidArea.x = 4;
        solidArea.y = 18;
        solidArea.width = 40;
        solidArea.height = 30;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        getImage();
    }

    public void getImage() {
        up1 = setup("monster/slime/greenslime_down_1");
        up2 = setup("monster/slime/greenslime_down_2");
        down1 = setup("monster/slime/greenslime_down_1");
        down2 = setup("monster/slime/greenslime_down_2");
        left1 = setup("monster/slime/greenslime_down_1");
        left2 = setup("monster/slime/greenslime_down_2");
        right1 = setup("monster/slime/greenslime_down_1");
        right2 = setup("monster/slime/greenslime_down_2");
    }

    public void update(){
        super.update(); // Entity class's update

        int xDistance = Math.abs(worldX - gp.player.worldX);
        int yDistance = Math.abs(worldY - gp.player.worldY);
        int tileDistance = (xDistance + yDistance) / gp.tileSize;

        if(!onPath && tileDistance < 5){
            int i = new Random().nextInt(100) + 1;
            if(i > 25){ // 75% of the time, it gets aggro
                onPath = true;
            }
        }
        if(onPath && tileDistance > 16){
            onPath = false;
        }
    }

    public void setAction() {
        if(onPath){
            // follow player
            int goalCol = (gp.player.worldX + gp.player.solidArea.x) / gp.tileSize;
            int goalRow = (gp.player.worldY + gp.player.solidArea.y) / gp.tileSize;

            searchPath(goalCol, goalRow, true); // goes to home at spawn

            // shoot rocks
            int i = new Random().nextInt(100) + 1;
            if(i > 98 && !projectile.alive && shotAvailableCounter == 30){
                projectile.set(worldX, worldY, direction, true, this);
                gp.projectileList.add(projectile);
                shotAvailableCounter = 0;
            }
        }
        else{
            actionLockCounter++;

            if(actionLockCounter == 120) {
                Random random = new Random();
                int i = random.nextInt(100) + 1; // random number from 1 - 100

                if (i <= 25) {
                    direction = "up";
                } else if (i <= 50) {
                    direction = "down";
                } else if (i <= 75) {
                    direction = "left";
                } else {
                    direction = "right";
                }

                actionLockCounter = 0;
            }
        }
    }

    public void damageReaction(){
        actionLockCounter = 0;

//        direction = gp.player.direction; // slime flees when hit
        onPath = true; // slime follows player
        speed = 1; // 2 too fast
    }

    public void checkDrop(){
        int i  = new Random().nextInt(100) + 1; // random from 1-100

        // SET DROPS
        // 75% chance of rock
        if(i < 75){
            dropItem(new OBJ_Rock(gp));
        }
        // 15% chance of heart
        else if(i < 90){
            dropItem(new OBJ_Heart(gp));
        }
        // 10% chance of mana
        else{
            dropItem(new OBJ_ManaCrystal(gp));
        }
    }

}
