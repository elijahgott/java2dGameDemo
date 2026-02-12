package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Door extends Entity{
    GamePanel gp;
    int toMap, toCol, toRow;

    public OBJ_Door(GamePanel gp, int toMap, int toCol, int toRow) {
        super(gp);
        this.gp = gp;

        name = "Door";
        type = type_obstacle;
        image = setup("objects/door");
        image2 = setup("objects/door_open");
        down1 = image;

        collision = true;

        // hitbox
        solidArea.x = 0;
        solidArea.y = 24;
        solidArea.width = 48;
        solidArea.height = 24;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        // teleport to map and coords
        this.toMap = toMap;
        this.toCol = toCol;
        this.toRow = toRow;
    }

    public void interact(){
        if(!opened){
            gp.gameState = gp.dialogueState;
            gp.ui.currentDialogue = "You need a key to open the door.";
        }
        else{
            gp.eventHandler.teleport(toMap, toCol, toRow);
        }
    }
}
