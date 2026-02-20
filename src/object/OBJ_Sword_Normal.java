package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Sword_Normal extends Entity {

    public static final String objName = "Normal Sword";

    public OBJ_Sword_Normal(GamePanel gp) {
        super(gp);

        type = type_sword;
        name = objName;
        price = 10;
        description = "[" + name + "]" + "\nThis sword is old and dull.";

        down1 = setup("objects/sword_normal", gp.tileSize, gp.tileSize);

        attackValue = 1;
        knockBackPower = 2;
        attackArea.width = 36;
        attackArea.height = 36;

        motion1Duration = 5;
        motion2Duration = 25;
    }
}
