package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Heart extends Entity{
    GamePanel gp;
    public OBJ_Heart(GamePanel gp) {
        super(gp);
        this.gp = gp;

        name = "Heart";
        type = type_pickupOnly;
        value = 2;

        down1 = setup("objects/heart_full");
        image = setup("objects/heart_full");
        image2 = setup("objects/heart_half");
        image3 = setup("objects/heart_empty");

//        solidArea.x = 8; // set custom solid area
//        solidArea.y = 8; // set custom solid area

    }

    public void use(Entity entity){
        gp.playSoundEffect(1); // coin sound effect
        gp.player.health += value;
    }
}
