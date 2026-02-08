package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Potion_Red extends Entity {
    GamePanel gp;

    public OBJ_Potion_Red(GamePanel gp) {
        super(gp);
        this.gp = gp;

        type = type_consumable;
        name = "Red Potion";
        price = 45;
        description = "[" + name + "]\nHeals " + value + " health.";
        value = 5;

        down1 = setup("objects/potion_red", gp.tileSize, gp.tileSize);
    }

    public boolean use(Entity entity){
        gp.ui.addMessage("You drank the " + name + "!");
        entity.health += value;

        gp.playSoundEffect(2); // power up sound effect PLACEHOLDER
        return true;
    }
}
