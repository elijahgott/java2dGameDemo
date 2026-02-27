package entity.monster;

import data.Progress;
import entity.Entity;
import main.GamePanel;
import object.OBJ_Door_Iron;
import object.OBJ_Heart;
import object.OBJ_ManaCrystal;
import object.OBJ_Rock;

import java.util.Random;

public class MON_Skeleton_Lord extends Entity {
    GamePanel gp;

    public static final String monName = "Skeleton Lord";

    public MON_Skeleton_Lord(GamePanel gp) {
        super(gp);
        this.gp = gp;

        name = monName;
        type = type_monster;
        boss = true;
        sleep = true;

        defaultSpeed = 1;
        speed = defaultSpeed;

        maxHealth = 50;
        health = maxHealth;

        attack = 12;
        knockBackPower = 5;
        motion1Duration = 25;
        motion2Duration = 50;

        defense = 2;
        exp = 50;

        int size = gp.tileSize * 5;
        solidArea.x = gp.tileSize;
        solidArea.y = gp.tileSize;
        solidArea.width = size - (gp.tileSize * 2);
        solidArea.height = size - gp.tileSize;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        attackArea.width = 170;
        attackArea.height = 170;

        getImage();
        getAttackImage();

        setDialogue();
    }

    public void getImage() {
        int i = 5;

        if(!enraged){
            up1 = setup("monster/skeletonBoss/phase1/walk/skeletonlord_up_1", gp.tileSize * i, gp.tileSize * i);
            up2 = setup("monster/skeletonBoss/phase1/walk/skeletonlord_up_2", gp.tileSize * i, gp.tileSize * i);
            down1 = setup("monster/skeletonBoss/phase1/walk/skeletonlord_down_1", gp.tileSize * i, gp.tileSize * i);
            down2 = setup("monster/skeletonBoss/phase1/walk/skeletonlord_down_2", gp.tileSize * i, gp.tileSize * i);
            left1 = setup("monster/skeletonBoss/phase1/walk/skeletonlord_left_1", gp.tileSize * i, gp.tileSize * i);
            left2 = setup("monster/skeletonBoss/phase1/walk/skeletonlord_left_2", gp.tileSize * i, gp.tileSize * i);
            right1 = setup("monster/skeletonBoss/phase1/walk/skeletonlord_right_1", gp.tileSize * i, gp.tileSize * i);
            right2 = setup("monster/skeletonBoss/phase1/walk/skeletonlord_right_2", gp.tileSize * i, gp.tileSize * i);
        }
        else{
            up1 = setup("monster/skeletonBoss/phase2/walk/skeletonlord_phase2_up_1", gp.tileSize * i, gp.tileSize * i);
            up2 = setup("monster/skeletonBoss/phase2/walk/skeletonlord_phase2_up_2", gp.tileSize * i, gp.tileSize * i);
            down1 = setup("monster/skeletonBoss/phase2/walk/skeletonlord_phase2_down_1", gp.tileSize * i, gp.tileSize * i);
            down2 = setup("monster/skeletonBoss/phase2/walk/skeletonlord_phase2_down_2", gp.tileSize * i, gp.tileSize * i);
            left1 = setup("monster/skeletonBoss/phase2/walk/skeletonlord_phase2_left_1", gp.tileSize * i, gp.tileSize * i);
            left2 = setup("monster/skeletonBoss/phase2/walk/skeletonlord_phase2_left_2", gp.tileSize * i, gp.tileSize * i);
            right1 = setup("monster/skeletonBoss/phase2/walk/skeletonlord_phase2_right_1", gp.tileSize * i, gp.tileSize * i);
            right2 = setup("monster/skeletonBoss/phase2/walk/skeletonlord_phase2_right_2", gp.tileSize * i, gp.tileSize * i);
        }

    }

    public void getAttackImage(){
        int i = 5;

        if(!enraged){
            attackUp1 = setup("monster/skeletonBoss/phase1/attack/skeletonlord_attack_up_1", gp.tileSize * i, gp.tileSize * i * 2);
            attackUp2 = setup("monster/skeletonBoss/phase1/attack/skeletonlord_attack_up_2", gp.tileSize * i, gp.tileSize * i * 2);
            attackDown1 = setup("monster/skeletonBoss/phase1/attack/skeletonlord_attack_down_1", gp.tileSize * i, gp.tileSize * i * 2);
            attackDown2 = setup("monster/skeletonBoss/phase1/attack/skeletonlord_attack_down_2", gp.tileSize * i, gp.tileSize * i * 2);
            attackLeft1 = setup("monster/skeletonBoss/phase1/attack/skeletonlord_attack_left_1", gp.tileSize * i * 2, gp.tileSize * i);
            attackLeft2 = setup("monster/skeletonBoss/phase1/attack/skeletonlord_attack_left_2", gp.tileSize * i * 2, gp.tileSize * i);
            attackRight1 = setup("monster/skeletonBoss/phase1/attack/skeletonlord_attack_right_1", gp.tileSize * i * 2, gp.tileSize * i);
            attackRight2 = setup("monster/skeletonBoss/phase1/attack/skeletonlord_attack_right_2", gp.tileSize * i * 2, gp.tileSize * i);
        }
        else{
            attackUp1 = setup("monster/skeletonBoss/phase2/attack/skeletonlord_phase2_attack_up_1", gp.tileSize * i, gp.tileSize * i * 2);
            attackUp2 = setup("monster/skeletonBoss/phase2/attack/skeletonlord_phase2_attack_up_2", gp.tileSize * i, gp.tileSize * i * 2);
            attackDown1 = setup("monster/skeletonBoss/phase2/attack/skeletonlord_phase2_attack_down_1", gp.tileSize * i, gp.tileSize * i * 2);
            attackDown2 = setup("monster/skeletonBoss/phase2/attack/skeletonlord_phase2_attack_down_2", gp.tileSize * i, gp.tileSize * i * 2);
            attackLeft1 = setup("monster/skeletonBoss/phase2/attack/skeletonlord_phase2_attack_left_1", gp.tileSize * i * 2, gp.tileSize * i);
            attackLeft2 = setup("monster/skeletonBoss/phase2/attack/skeletonlord_phase2_attack_left_2", gp.tileSize * i * 2, gp.tileSize * i);
            attackRight1 = setup("monster/skeletonBoss/phase2/attack/skeletonlord_phase2_attack_right_1", gp.tileSize * i * 2, gp.tileSize * i);
            attackRight2 = setup("monster/skeletonBoss/phase2/attack/skeletonlord_phase2_attack_right_2", gp.tileSize * i * 2, gp.tileSize * i);
        }

    }

    public void setAction() {
        if(!enraged && (health < maxHealth / 2)){
            enraged = true;
            getImage();
            getAttackImage();
            defaultSpeed += 2;
            speed = defaultSpeed;

            attack += 4;
        }
        if(getTileDistance(gp.player) < 10){
            moveTowardPlayer(60);
        }
        else{
            getRandomDirection(150);
        }

        // check if it attacks
        if(!attacking){
            checkAttack(60, gp.tileSize * 7, gp.tileSize * 5);
        }
    }

    public void damageReaction(){
        actionLockCounter = 0;
    }

    public void checkDrop(){
        gp.bossBattleOn = false;
        Progress.skeletonLordDefeated = true;

        // restore previous music
        gp.stopMusic();
        gp.playMusic(19);

        // remove iron doors
        for(int i = 0; i < gp.obj[1].length; i++){
            if(gp.obj[gp.currentMap][i] != null && gp.obj[gp.currentMap][i].name.equals(OBJ_Door_Iron.objName)){
                gp.playSoundEffect(21); // door open
                gp.obj[gp.currentMap][i] = null;
            }
        }

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

    public void setDialogue(){
        dialogues[0][0] = "GTFO!!!";
        dialogues[0][1] = "I will murder you...";
        dialogues[0][2] = "and your entire family!";
    }
}
