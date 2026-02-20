package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Lantern extends Entity {

    public static final String objName = "Lantern";

    public OBJ_Lantern(GamePanel gp) {
        super(gp);

        type = type_light;
        name = objName;
        description = "[" + name + "]\nLights your path when dark.";

        down1 = setup("objects/lantern");
        price = 100;

        lightRadius = 350;
    }
}
