package entity;

import main.GamePanel;

import java.awt.*;
import java.util.Random;


public class NPC_OldMan extends Entity {
    public NPC_OldMan(GamePanel gp) {
        super(gp);

        type = type_npc;
        direction = "down";
        speed = 2;
        solidArea = new Rectangle(2, 2, 44, 44); // not used because default entity solid area is used

        getImage();
        setDialogue();
    }

    public void getImage(){
        up1 = setup("npc/oldMan/oldman_up_1", gp.tileSize, gp.tileSize);
        up2 = setup("npc/oldMan/oldman_up_2", gp.tileSize, gp.tileSize);
        down1 = setup("npc/oldMan/oldman_down_1", gp.tileSize, gp.tileSize);
        down2 = setup("npc/oldMan/oldman_down_2", gp.tileSize, gp.tileSize);
        left1 = setup("npc/oldMan/oldman_left_1", gp.tileSize, gp.tileSize);
        left2 = setup("npc/oldMan/oldman_left_2", gp.tileSize, gp.tileSize);
        right1 = setup("npc/oldMan/oldman_right_1", gp.tileSize, gp.tileSize);
        right2 = setup("npc/oldMan/oldman_right_2", gp.tileSize, gp.tileSize);
    }

    public void setAction(){
        if(onPath){
            // send NPC to 2, 5
//            int goalCol = 2;
//            int goalRow = 5;

            // follow player
            int goalCol = (gp.player.worldX + gp.player.solidArea.x) / gp.tileSize;
            int goalRow = (gp.player.worldY + gp.player.solidArea.y) / gp.tileSize;

            searchPath(goalCol, goalRow, true); // goes to home at spawn
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

    public void setDialogue(){
        dialogues[0] = "Crazy? I was crazy once. They\nput me in a room, a room with\nrats. Rats make me crazy.";
        dialogues[1] = "What.";
        dialogues[2] = "I am NOT old.";
        dialogues[3] = "JK I'm lowkey pretty old.";
        dialogues[4] = "Got any cute sisters?";
    }

    public void speak(){
        super.speak();

        // can add character specific stuff
        onPath = true;
    }
}
