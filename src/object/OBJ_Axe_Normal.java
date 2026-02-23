package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Axe_Normal extends Entity {

    public static final String objName = "Woodcutter's Axe";

    public OBJ_Axe_Normal(GamePanel gp){
        super(gp);

        type = type_axe;
        name = objName;
        price = 50;
        description = "[" + name + "]\nOld and heavy axe.";

        down1 = setup("objects/axe");

        attackValue = 2;
        knockBackPower = 10;
        // axe has shorter range than sword
        attackArea.width = 28;
        attackArea.height = 28;

        motion1Duration = 20;
        motion2Duration = 40;
    }
}
