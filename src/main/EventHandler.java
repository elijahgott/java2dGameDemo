package main;

import entity.Entity;

import java.awt.*;

public class EventHandler{
    GamePanel gp;
    EventRect eventRect[][][];

    int previousEventX, previousEventY;
    boolean canTouchEvent = true;
    int tempMap, tempCol, tempRow;

    public EventHandler(GamePanel gp){
        this.gp = gp;

        eventRect = new EventRect[gp.maxMap][gp.maxWorldCol][gp.maxWorldRow];
        int map = 0;
        int col = 0;
        int row = 0;
        while(map < gp.maxMap && col < gp.maxWorldCol && row < gp.maxWorldRow){
            eventRect[map][col][row] = new EventRect();
            eventRect[map][col][row].x = 23;
            eventRect[map][col][row].y = 23;
            eventRect[map][col][row].width = 2;
            eventRect[map][col][row].height = 2;
            eventRect[map][col][row].eventRectDefaultX = eventRect[map][col][row].x;
            eventRect[map][col][row].eventRectDefaultY = eventRect[map][col][row].y;

            col++;
            if(col == gp.maxWorldCol){
                col = 0;
                row++;

                if(row == gp.maxWorldRow){
                    row = 0;
                    map++;
                }
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
            int currentMap = 0;

            // damage pit
            if(hit(currentMap, 7, 5, "any")){
                damagePit(gp.dialogueState);
            }
            // healing pool
            else if(hit(currentMap, 7, 4, "right")){ // needs right key to be actively pressed to work
                healingPool(gp.dialogueState);
            }
//            // teleport into house
//            else if(hit(currentMap, 2, 5, "any")){
//                teleport(1, 12, 13);
//            }
            // teleport out of house
            else if(hit(1, 12, 13, "any")){
                teleport(0, 2, 5);
            }

            currentMap++; // interior map
            // talk to goblin guy in house
            if(hit(currentMap, 12, 9, "up")){
                speak(gp.npc[1][0]);
            }
        }
    }

    public boolean hit(int map, int eventCol, int eventRow, String requiredDirection){
        boolean hit = false;

        if(map == gp.currentMap){
            gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
            gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;
            eventRect[map][eventCol][eventRow].x = (eventCol * gp.tileSize) + eventRect[map][eventCol][eventRow].x;
            eventRect[map][eventCol][eventRow].y = (eventRow * gp.tileSize) + eventRect[map][eventCol][eventRow].y;


            if(gp.player.solidArea.intersects(eventRect[map][eventCol][eventRow]) && !eventRect[map][eventCol][eventRow].eventDone){
                if(gp.player.direction.equals(requiredDirection) || requiredDirection.equals("any")){
                    hit = true;

                    previousEventX = gp.player.worldX;
                    previousEventY = gp.player.worldY;
                }
            }

            // reset player and event positions
            gp.player.solidArea.x = gp.player.solidAreaDefaultX;
            gp.player.solidArea.y = gp.player.solidAreaDefaultY;
            eventRect[map][eventCol][eventRow].x = eventRect[map][eventCol][eventRow].eventRectDefaultX;
            eventRect[map][eventCol][eventRow].y = eventRect[map][eventCol][eventRow].eventRectDefaultY;
        }

        return hit;
    }

    public void damagePit(int gameState){
        gp.gameState = gameState;
        gp.ui.currentDialogue = "You fall into a pit of death\nand despair!";
        // play damage sound effect
        gp.player.health--;
//        eventRect[col][row].eventDone = true; // one time event
        canTouchEvent = false;
    }

    public void healingPool(int gameState){
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

    public void teleport(int map, int col, int row){
        gp.gameState = gp.transitionState;

        tempMap = map;
        tempCol = col;
        tempRow = row;

        canTouchEvent = false;
        gp.playSoundEffect(12); // cut tree PLACEHOLDER
    }

    public void speak(Entity entity){
        if(gp.keyHandler.enterPressed || gp.keyHandler.spacePressed){
            gp.gameState = gp.dialogueState;
            gp.player.attackCanceled = true;
            entity.speak();
        }
    }
}
