package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Door_Iron extends Entity {
    GamePanel gp;
    int toMap, toCol, toRow;

    public static final String objName = "Iron Door";

    public OBJ_Door_Iron(GamePanel gp) {
        super(gp);
        this.gp = gp;

        name = objName;
        type = type_obstacle;
        image = setup("objects/door_iron");
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
//        this.toMap = toMap;
//        this.toCol = toCol;
//        this.toRow = toRow;

        setDialogue();
    }

    public void setDialogue(){
        dialogues[0][0] = "It won't budge...";
    }

    public void interact(){
        if(!opened){
            startDialogue(this, 0);
        }
        else{
//            gp.eventHandler.teleport(toMap, toCol, toRow, gp.inside);
        }
    }
}
