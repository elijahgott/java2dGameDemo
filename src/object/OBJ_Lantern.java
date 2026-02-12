package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Lantern extends Entity {
    public OBJ_Lantern(GamePanel gp) {
        super(gp);

        type = type_light;
        name = "Lantern";
        description = "[" + name + "]\nLights your path when dark.";

        down1 = setup("objects/lantern");
        price = 100;

        lightRadius = 350;
    }
}
