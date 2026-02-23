package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Pickaxe_Normal extends Entity {

    public static final String objName = "Pickaxe";

    public OBJ_Pickaxe_Normal(GamePanel gp){
        super(gp);

        type = type_pickaxe;
        name = objName;
        price = 75;
        description = "[" + name + "]\nMinecraft reference?";

        down1 = setup("objects/pickaxe");

        attackValue = 1;
        knockBackPower = 2;
        // pickaxe has shorter range than sword
        attackArea.width = 28;
        attackArea.height = 28;

        motion1Duration = 20;
        motion2Duration = 30;
    }
}
