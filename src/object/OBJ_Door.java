package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Door extends Entity{
    GamePanel gp;
    int toMap, toCol, toRow;

    public static final String objName = "Door";

    public OBJ_Door(GamePanel gp, int toMap, int toCol, int toRow) {
        super(gp);
        this.gp = gp;

        name = objName;
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

        setDialogue();
    }

    public void setDialogue(){
        dialogues[0][0] = "You need a key to open the door.";
    }

    public void interact(){
        if(!opened){
            startDialogue(this, 0);
        }
        else{
            gp.eventHandler.teleport(toMap, toCol, toRow);
        }
    }
}
