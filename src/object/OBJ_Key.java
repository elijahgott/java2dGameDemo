package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Key extends Entity {
    GamePanel gp;

    public static final String objName = "Key";

    public OBJ_Key(GamePanel gp) {
        super(gp);
        this.gp = gp;

        name = objName;
        description = "[" + name + "]" + "\nKeys open doors.";
        type = type_consumable;
        stackable = true;

        down1 = setup("objects/key");

        setDialogue();

//        solidArea.x = 8; // set custom solid area
//        solidArea.y = 8; // set custom solid area

    }

    public void setDialogue(){
        dialogues[0][0] = "Can't use that here.";

        dialogues[1][0] = "You opened the door!";
    }

    public boolean use(Entity entity){
        int objIndex = getDetected(entity, gp.obj, "Door");

        if(objIndex != 999){
            startDialogue(this, 1);
            gp.playSoundEffect(3);
            gp.obj[gp.currentMap][objIndex].opened = true;
            gp.obj[gp.currentMap][objIndex].down1 = gp.obj[gp.currentMap][objIndex].image2;
            return true;
        }
        else{
            startDialogue(this, 0);
            return false;
        }
    }
}
