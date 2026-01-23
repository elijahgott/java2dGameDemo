package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Potion_Red extends Entity {
    GamePanel gp;
    int value = 5;

    public OBJ_Potion_Red(GamePanel gp) {
        super(gp);
        this.gp = gp;

        type = type_consumable;
        name = "Red Potion";
        description = "[" + name + "]\nHeals " + value + " health.";
        down1 = setup("objects/potion_red", gp.tileSize, gp.tileSize);
    }

    public void use(Entity entity){
//        gp.gameState = gp.playState;
        gp.ui.addMessage("You drank the " + name + "!");
        entity.health += value;
        if(entity.health > entity.maxHealth){
            entity.health = entity.maxHealth;
        }
        gp.playSoundEffect(2); // power up sound effect PLACEHOLDER
    }
}
