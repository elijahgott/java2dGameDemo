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
        value = 3;

        setDialogue();

        down1 = setup("objects/potion_red", gp.tileSize, gp.tileSize);
    }

    public void setDialogue(){
        dialogues[0][0] = "You drank the " + name + "!";
    }

    public boolean use(Entity entity){
        startDialogue(this, 0);
        entity.health += value;
        if(entity.health > entity.maxHealth){
            entity.health = entity.maxHealth;
        }

        gp.playSoundEffect(2); // power up sound effect PLACEHOLDER
        return true;
    }
}
