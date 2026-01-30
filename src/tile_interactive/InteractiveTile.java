package tile_interactive;

import entity.Entity;
import main.GamePanel;

public class InteractiveTile extends Entity {
    GamePanel gp;
    public boolean destructible = false;

    public InteractiveTile(GamePanel gp, int col, int row) {
        super(gp);
        this.gp = gp;
    }

    public boolean isCorrectItem(Entity entity){
        boolean isCorrectItem = false;

        return isCorrectItem;
    }

    public void playSoundEffect(){}

    public InteractiveTile getDestroyedForm(){
        InteractiveTile tile = null;
        return tile;
    }

    public void update(){
        // invincibility timer after being hit
        if(invincible){
            invincibleTimer++;
            if(invincibleTimer > 30){
                invincible = false;
                invincibleTimer = 0;
            }
        }
    }
}
