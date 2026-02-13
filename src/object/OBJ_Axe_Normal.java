package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Axe_Normal extends Entity {
    public OBJ_Axe_Normal(GamePanel gp){
        super(gp);

        type = type_axe;
        name = "Woodcutter's Axe";
        price = 50;
        description = "[" + name + "]\nOld and heavy axe.";

        down1 = setup("objects/axe", gp.tileSize, gp.tileSize);

        attackValue = 2;
        knockBackPower = 10;
        // axe has shorter range than sword
        attackArea.width = 28;
        attackArea.height = 28;

        motion1Duration = 20;
        motion2Duration = 40;
    }
}
