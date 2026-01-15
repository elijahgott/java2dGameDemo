package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Key extends Entity {
    public OBJ_Key(GamePanel gp) {
        super(gp);

        name = "Key";
        down1 = setup("objects/key");

//        solidArea.x = 8; // set custom solid area
//        solidArea.y = 8; // set custom solid area

    }
}
