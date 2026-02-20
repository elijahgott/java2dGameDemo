package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Boots extends Entity{

    public static final String objName = "Jordan 1's";

    public OBJ_Boots(GamePanel gp) {
        super(gp);

        name = objName;
        price = 250;
        description = "[" + name + "]" + "\nYou feel more agile in these.";

        down1 = setup("objects/boots", gp.tileSize, gp.tileSize);
    }
}
