package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_ManaCrystal extends Entity {
    GamePanel gp;

    public OBJ_ManaCrystal(GamePanel gp) {
        super(gp);
        this.gp = gp;

        name = "Mana Crystal";
        price = 25;
        type = type_pickupOnly;
        value = 1;

        down1 = setup("objects/manacrystal_full");
        image = setup("objects/manacrystal_full");
        image2 = setup("objects/manacrystal_blank");
    }

    public boolean use(Entity entity){
        gp.playSoundEffect(1); // coin sound effect
        gp.player.mana += value;
        return true;
    }
}
