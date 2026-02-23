package entity;

import main.GamePanel;
import object.OBJ_Door_Iron;
import tile_interactive.IT_Metal_Plate;
import tile_interactive.InteractiveTile;

import java.awt.*;
import java.util.ArrayList;
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
        detectPlate();
    }

    public void detectPlate(){
        ArrayList<InteractiveTile> plateList = new ArrayList<>();
        ArrayList<Entity> rockList = new ArrayList<>();

        // create plate list
        for(int i = 0; i < gp.interactiveTile[1].length; i++){
            if(gp.interactiveTile[gp.currentMap][i] != null && gp.interactiveTile[gp.currentMap][i].name != null &&
            gp.interactiveTile[gp.currentMap][i].name.equals(IT_Metal_Plate.itName)){
                plateList.add(gp.interactiveTile[gp.currentMap][i]);
            }
        }

        // create rock list
        for(int i = 0; i < gp.npc[1].length; i++){
            if(gp.npc[gp.currentMap][i] != null &&
                    gp.npc[gp.currentMap][i].name.equals(NPC_Big_Rock.npcName)){
                rockList.add(gp.npc[gp.currentMap][i]);
            }
        }

        int count = 0;

        // scan plate list
        for(int i = 0; i < plateList.size(); i++){
            int xDistance = Math.abs(worldX - plateList.get(i).worldX);
            int yDistance = Math.abs(worldY - plateList.get(i).worldY);
            int distance = Math.max(xDistance, yDistance);

            // if within 8 pixels, consider rock on metal plate
            if(distance < 8){
                if(linkedEntity == null){ // only when they first touch
                    linkedEntity = plateList.get(i);
                    gp.playSoundEffect(3); // unlock sound
                }
            }
            else{
                if(linkedEntity == plateList.get(i)){
                    linkedEntity = null;
                }
            }
        }

        // scan rock list
        for(int i = 0; i < rockList.size(); i++){
            // count rocks on plate
            if(rockList.get(i).linkedEntity != null){
                count++;
            }
        }

        // if all rocks are on the plates, iron door opens
        if(count == rockList.size()){
            for(int i = 0; i < gp.obj[1].length; i++){
                if(gp.obj[gp.currentMap][i] != null &&
                gp.obj[gp.currentMap][i].name.equals(OBJ_Door_Iron.objName)){
                    gp.obj[gp.currentMap][i] = null;
//                    gp.obj[gp.currentMap][i].opened = true;
                    gp.playSoundEffect(21); // door open sound effect
                }
            }
        }
    }

    public void setAction(){
        // No AI
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
