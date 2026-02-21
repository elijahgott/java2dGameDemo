package entity.monster;

import entity.Entity;
import main.GamePanel;
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

        defaultSpeed = 1;
        speed = defaultSpeed;

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
        up1 = setup("monster/slime/greenslime_up_1");
        up2 = setup("monster/slime/greenslime_up_2");
        down1 = setup("monster/slime/greenslime_down_1");
        down2 = setup("monster/slime/greenslime_down_2");
        left1 = setup("monster/slime/greenslime_down_1");
        left2 = setup("monster/slime/greenslime_down_2");
        right1 = setup("monster/slime/greenslime_down_1");
        right2 = setup("monster/slime/greenslime_down_2");
    }

    public void setAction() {
        int tileDistance = getTileDistance(gp.player);

        if(onPath){ // monster is following player
            // follow player
            searchPath(getGoalCol(gp.player), getGoalRow(gp.player), true); // goes to home at spawn

            // shoot rocks
            checkShoot(200, 10);

            // stop following player
            checkStopChasing(gp.player, 16, 100); // 1% chance
        }
        else{ // check if monster starts chasing player
            checkStartChasing(gp.player, 8, 100);

            // change slime direction randomly
            getRandomDirection();
        }
    }

    public void damageReaction(){
        actionLockCounter = 0;

        onPath = true; // slime follows player
    }

    public void checkDrop(){
        int i  = new Random().nextInt(100) + 1; // random from 1-100

        // SET DROPS
        // 75% chance of rock
        if(i < 75){
            dropItem(new OBJ_Rock(gp));
        }
        // 5% chance of heart
        else if(i < 80){
            dropItem(new OBJ_Heart(gp));
        }
        // 20% chance of mana
        else{
            dropItem(new OBJ_ManaCrystal(gp));
        }
    }

}
