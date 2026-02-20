package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Shield_Blue extends Entity {

    public static final String objName = "Blue Shield";

    public OBJ_Shield_Blue(GamePanel gp){
        super(gp);

        type = type_shield;
        name = objName;
        price = 100;
        description = "[" + name + "]\nA pristine soldier's shield.";
        down1 = setup("objects/shield_blue", gp.tileSize, gp.tileSize);

        defenseValue = 2;
    }
}
