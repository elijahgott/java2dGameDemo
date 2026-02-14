package entity.monster;

import entity.Entity;
import main.GamePanel;
import object.OBJ_Heart;
import object.OBJ_ManaCrystal;
import object.OBJ_Rock;

import java.util.Random;

public class MON_Orc extends Entity {
    GamePanel gp;

    public MON_Orc(GamePanel gp) {
        super(gp);
        this.gp = gp;

        name = "Orc";
        type = type_monster;

        defaultSpeed = 1;
        speed = defaultSpeed;

        maxHealth = 10;
        health = maxHealth;

        attack = 8;
        knockBackPower = 5;
        motion1Duration = 40;
        motion2Duration = 85;

        defense = 2;
        exp = 5;

        solidArea.x = 4;
        solidArea.y = 4;
        solidArea.width = 40;
        solidArea.height = 40;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        attackArea.width = 48;
        attackArea.height = 48;

        getImage();
        getAttackImage();
    }

    public void getImage() {
        up1 = setup("monster/orc/walk/orc_up_1");
        up2 = setup("monster/orc/walk/orc_up_2");
        down1 = setup("monster/orc/walk/orc_down_1");
        down2 = setup("monster/orc/walk/orc_down_2");
        left1 = setup("monster/orc/walk/orc_left_1");
        left2 = setup("monster/orc/walk/orc_left_2");
        right1 = setup("monster/orc/walk/orc_right_1");
        right2 = setup("monster/orc/walk/orc_right_2");
    }

    public void getAttackImage(){
        attackUp1 = setup("monster/orc/attack/orc_attack_up_1", gp.tileSize, gp.tileSize * 2);
        attackUp2 = setup("monster/orc/attack/orc_attack_up_2", gp.tileSize, gp.tileSize * 2);
        attackDown1 = setup("monster/orc/attack/orc_attack_down_1", gp.tileSize, gp.tileSize * 2);
        attackDown2 = setup("monster/orc/attack/orc_attack_down_2", gp.tileSize, gp.tileSize * 2);
        attackLeft1 = setup("monster/orc/attack/orc_attack_left_1", gp.tileSize * 2, gp.tileSize);
        attackLeft2 = setup("monster/orc/attack/orc_attack_left_2", gp.tileSize * 2, gp.tileSize);
        attackRight1 = setup("monster/orc/attack/orc_attack_right_1", gp.tileSize * 2, gp.tileSize);
        attackRight2 = setup("monster/orc/attack/orc_attack_right_2", gp.tileSize * 2, gp.tileSize);
    }

    public void setAction() {
        int tileDistance = getTileDistance(gp.player);

        if(onPath){ // monster is following player
            // follow player
            searchPath(getGoalCol(gp.player), getGoalRow(gp.player), true); // goes to home at spawn

            // stop following player
            checkStopChasing(gp.player, 12, 100); // 1% chance
        }
        else{ // check if monster starts chasing player
            checkStartChasing(gp.player, 6, 100);

            // change slime direction randomly
            getRandomDirection();
        }

        // check if it attacks
        if(!attacking){
            checkAttack(90, gp.tileSize * 4, gp.tileSize);
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
