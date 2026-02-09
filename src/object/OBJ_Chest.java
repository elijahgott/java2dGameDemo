package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Chest extends Entity{
    GamePanel gp;
    Entity loot;

    public OBJ_Chest(GamePanel gp, Entity loot) {
        super(gp);
        this.gp = gp;
        this.loot = loot;

        name = "Chest";
        type = type_obstacle;

        image = setup("objects/chest", gp.tileSize, gp.tileSize);
        image2 = setup("objects/chest_open", gp.tileSize, gp.tileSize);
        // default image
        down1 = image;
        collision = true;

        solidArea.x = 4;
        solidArea.y = 24;
        solidArea.width = 40;
        solidArea.height = 24;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }

    public void interact(){
        gp.gameState = gp.dialogueState;

        if(!opened){
            gp.playSoundEffect(3);

            StringBuilder sb = new StringBuilder();
            sb.append("You open the chest and find a ").append(loot.name).append("!");

            if(gp.player.canObtainItem(loot)){
                // put item in inventory
                sb.append("\nYou look around and put\nit in your pocket!");
                opened = true;
                down1 = image2;
            }
            else{
                // inventory full
                sb.append("\n...but you cannot hold any more items!");
            }
            gp.ui.currentDialogue = sb.toString();
        }
        else{
            // chest already opened
            gp.ui.currentDialogue = "It's empty...";
        }
    }
}
