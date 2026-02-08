package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Key extends Entity {
    GamePanel gp;

    public OBJ_Key(GamePanel gp) {
        super(gp);
        this.gp = gp;

        name = "Key";
        description = "[" + name + "]" + "\nKeys open doors.";
        type = type_consumable;

        down1 = setup("objects/key", gp.tileSize, gp.tileSize);

//        solidArea.x = 8; // set custom solid area
//        solidArea.y = 8; // set custom solid area

    }

    public boolean use(Entity entity){
        gp.gameState = gp.dialogueState;

        int objIndex = getDetected(entity, gp.obj, "Door");

        if(objIndex != 999){
            gp.ui.currentDialogue = "You used the " + name + "!";
            gp.playSoundEffect(3);
            gp.obj[gp.currentMap][objIndex].opened = true;
            gp.obj[gp.currentMap][objIndex].down1 = gp.obj[gp.currentMap][objIndex].image2;
            return true;
        }
        else{
            gp.ui.currentDialogue = "Hell naw";
            return false;
        }
    }
}
