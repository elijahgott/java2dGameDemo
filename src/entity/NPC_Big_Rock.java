package entity;

import main.GamePanel;

import java.awt.*;
import java.util.Random;

public class NPC_Big_Rock extends Entity{

    public final static String npcName = "Big Rock";

    public NPC_Big_Rock(GamePanel gp) {
        super(gp);

        name = npcName;
        type = type_npc;
        direction = "down";
        speed = 4;
        solidArea = new Rectangle(2, 6, 44, 40); // not used because default entity solid area is used

        getImage();
        setDialogue();
    }

    public void getImage(){
        up1 = setup("npc/bigRock/bigrock");
        up2 = setup("npc/bigRock/bigrock");
        down1 = setup("npc/bigRock/bigrock");
        down2 = setup("npc/bigRock/bigrock");
        left1 = setup("npc/bigRock/bigrock");
        left2 = setup("npc/bigRock/bigrock");
        right1 = setup("npc/bigRock/bigrock");
        right2 = setup("npc/bigRock/bigrock");
    }

    public void move(String direction){
        this.direction = direction;

        checkCollision();

        if(!collisionOn){
            switch(direction){
                case "up": worldY -= speed; break;
                case "down": worldY += speed; break;
                case "left": worldX -= speed; break;
                case "right": worldX += speed; break;
            }
        }
    }

    public void setAction(){

    }

    public void update(){}

    public void setDialogue() {
        dialogues[0][0] = "It's not just a boulder...";
    }

    public void speak(){
        facePlayer();
        startDialogue(this, dialogueSet);

        dialogueSet++;

        if(dialogues[dialogueSet][0] == null){
//            dialogueSet = 0; // loops around back to beginning
            dialogueSet--; // goes back to the previous one
        }
    }
}
