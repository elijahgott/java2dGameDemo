package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Tent extends Entity {
    GamePanel gp;

    public static final String objName = "Tent";

    public OBJ_Tent(GamePanel gp) {
        super(gp);
        this.gp = gp;

        type = type_consumable;
        name = objName;
        description = "[" + name + "]\nSleep inside until\nnext morning.";
        price = 200;
        stackable = true;

        down1 = setup("objects/tent");
    }

    public boolean use(Entity entity){
        if(gp.environmentManager.lighting.dayState == gp.environmentManager.lighting.night){
            gp.gameState = gp.sleepState;
            gp.playSoundEffect(14); // sleep sound effect
            gp.player.health = gp.player.maxHealth;
            gp.player.mana = gp.player.maxMana;

            gp.player.getPlayerSleepImage(down1);

            return true; // makes item disappear -> false keeps item
        }
        else{
            gp.gameState = gp.playState;
            gp.ui.addMessage("Cannot sleep during the day!");

            return false;
        }

    }
}
