package main;

import java.awt.*;

public class EventHandler{
    GamePanel gp;
    Rectangle eventRect;
    int eventRectDefaultX, eventRectDefaultY;

    public EventHandler(GamePanel gp){
        this.gp = gp;

        eventRect = new Rectangle();
        eventRect.x = 23;
        eventRect.y = 23;
        eventRect.width = 2;
        eventRect.height = 2;
        eventRectDefaultX = eventRect.x;
        eventRectDefaultY = eventRect.y;
    }

    public void checkEvent() {
        // damage pit
        if(hit(7, 5, "up")){
            damagePit(gp.dialogueState);
        }
        // healing pool
        if(hit(7, 4, "right")){
            healingPool(gp.dialogueState);
        }

        // teleport pad
        if(hit(6, 10, "any")){
            teleport(gp.dialogueState);
        }
    }

    public boolean hit(int eventCol, int eventRow, String requiredDirection){
        boolean hit = false;

        gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
        gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;
        eventRect.x = (eventCol * gp.tileSize) + eventRect.x;
        eventRect.y = (eventRow * gp.tileSize) + eventRect.y;

        if(gp.player.solidArea.intersects(eventRect)){
            if(gp.player.direction.equals(requiredDirection) || requiredDirection.equals("any")){
                hit = true;
            }
        }

        // reset player and event positions
        gp.player.solidArea.x = gp.player.solidAreaDefaultX;
        gp.player.solidArea.y = gp.player.solidAreaDefaultY;
        eventRect.x = eventRectDefaultX;
        eventRect.y = eventRectDefaultY;

        return hit;
    }

    public void damagePit(int gameState){
        gp.gameState = gameState;
        gp.ui.currentDialogue = "You fall into a pit of death\nand despair!";
        gp.player.health--;
    }

    public void healingPool(int gameState){
        if(gp.keyHandler.enterPressed && (gp.player.health < gp.player.maxHealth)){
            gp.gameState = gameState;
            gp.ui.currentDialogue = "You drink the pond water.\nYour health is now replenished!";
            gp.player.health++;
        }
    }

    public void teleport(int gameState){
        gp.gameState = gameState;
        gp.ui.currentDialogue = "Teleporting to...?";
        gp.player.worldX = gp.tileSize * 26;
        gp.player.worldY = gp.tileSize * 35;
    }
}
