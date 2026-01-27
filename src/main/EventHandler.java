package main;

import java.awt.*;

public class EventHandler{
    GamePanel gp;
    EventRect eventRect[][];

    int previousEventX, previousEventY;
    boolean canTouchEvent = true;

    public EventHandler(GamePanel gp){
        this.gp = gp;

        eventRect = new EventRect[gp.maxWorldCol][gp.maxWorldRow];
        int col = 0;
        int row = 0;
        while(col < gp.maxWorldCol && row < gp.maxWorldRow){
            eventRect[col][row] = new EventRect();
            eventRect[col][row].x = 23;
            eventRect[col][row].y = 23;
            eventRect[col][row].width = 2;
            eventRect[col][row].height = 2;
            eventRect[col][row].eventRectDefaultX = eventRect[col][row].x;
            eventRect[col][row].eventRectDefaultY = eventRect[col][row].y;

            col++;
            if(col == gp.maxWorldCol){
                col = 0;
                row++;
            }
        }
    }

    public void checkEvent() {
        // check if player is more than 1 tile away from last event
        int xDistance = Math.abs(gp.player.worldX - previousEventX);
        int yDistance = Math.abs(gp.player.worldY - previousEventY);
        int distance = Math.max(xDistance, yDistance);

        if(distance > gp.tileSize){
            canTouchEvent = true;
        }

        if(canTouchEvent){
            // damage pit
            if(hit(7, 5, "any")){
                damagePit(7, 5, gp.dialogueState);
            }
            // healing pool
            if(hit(7, 4, "right")){ // needs right key to be actively pressed to work
                healingPool(7, 4, gp.dialogueState);
            }
            // teleport pad
            if(hit(6, 10, "any")){
                teleport(6, 10, gp.dialogueState);
            }
        }
    }

    public boolean hit(int eventCol, int eventRow, String requiredDirection){
        boolean hit = false;

        gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
        gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;
        eventRect[eventCol][eventRow].x = (eventCol * gp.tileSize) + eventRect[eventCol][eventRow].x;
        eventRect[eventCol][eventRow].y = (eventRow * gp.tileSize) + eventRect[eventCol][eventRow].y;


        if(gp.player.solidArea.intersects(eventRect[eventCol][eventRow]) && !eventRect[eventCol][eventRow].eventDone){
            if(gp.player.direction.equals(requiredDirection) || requiredDirection.equals("any")){
                hit = true;

                previousEventX = gp.player.worldX;
                previousEventY = gp.player.worldY;
            }
        }

        // reset player and event positions
        gp.player.solidArea.x = gp.player.solidAreaDefaultX;
        gp.player.solidArea.y = gp.player.solidAreaDefaultY;
        eventRect[eventCol][eventRow].x = eventRect[eventCol][eventRow].eventRectDefaultX;
        eventRect[eventCol][eventRow].y = eventRect[eventCol][eventRow].eventRectDefaultY;

        return hit;
    }

    public void damagePit(int col, int row, int gameState){
        gp.gameState = gameState;
        gp.ui.currentDialogue = "You fall into a pit of death\nand despair!";
        // play damage sound effect
        gp.player.health--;
//        eventRect[col][row].eventDone = true; // one time event
        canTouchEvent = false;
    }

    public void healingPool(int col, int row, int gameState){
        if(((gp.player.health < gp.player.maxHealth) || (gp.player.mana < gp.player.maxMana)) && (gp.keyHandler.enterPressed || gp.keyHandler.spacePressed)){
            gp.player.attackCanceled = true;
            gp.gameState = gameState;
            // play drinking water sound effect
            gp.ui.currentDialogue = "You drink the pond water.\nYour health is now replenished!";
            gp.player.health++;
            gp.player.mana++;

            if(gp.player.health ==  gp.player.maxHealth){
                // respawn monsters when at full health
                gp.assetSetter.setMonster();
            }
        }
    }

    public void teleport(int col, int row, int gameState){
        gp.gameState = gameState;
        gp.ui.currentDialogue = "Teleporting to...?";
        // play teleport sound effect
        // teleport player to 26, 35
        gp.player.worldX = gp.tileSize * 26;
        gp.player.worldY = gp.tileSize * 35;
    }
}
