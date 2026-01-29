package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Coin extends Entity {
    GamePanel gp;
    public OBJ_Coin(GamePanel gp) {
        super(gp);
        this.gp = gp;

        name = "Coin";
        type = type_pickupOnly;
        value = 1;
        down1 = setup("objects/coin");
    }

    public void use(Entity entity){
        gp.playSoundEffect(1); // coin sound effect
        if(value == 1){
            gp.ui.addMessage("+1 Coin");
        }
        else{
            gp.ui.addMessage("+" + value + " Coins");
        }
        gp.player.coin += value;
    }
}
