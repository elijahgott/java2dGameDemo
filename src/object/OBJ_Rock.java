package object;

import entity.Entity;
import entity.Projectile;
import main.GamePanel;

public class OBJ_Rock extends Projectile {
    GamePanel gp;
    public OBJ_Rock(GamePanel gp) {
        super(gp);
        this.gp = gp;

        name = "Rock";
        type = type_pickupOnly;
        speed = 8;
        maxHealth = 80;
        health = maxHealth;
        attack = 2;
        useCost = 1;
        alive = false;
        value = 1;

        getImage();
    }

    public boolean hasResource(Entity user){
        boolean hasResource = false;

        if(user.ammo >= useCost){
            hasResource = true;
        }

        return hasResource;
    }

    public void useResource(Entity user){
        user.ammo -= useCost;
    }

    public void use(Entity entity){
        gp.playSoundEffect(1); // coin sound effect
        if(value == 1){
            gp.ui.addMessage("+1 Rock");
        }
        else{
            gp.ui.addMessage("+" + value + " Rocks");
        }
        gp.player.ammo += value;
    }

    public void getImage(){
        up1 = setup("projectiles/rock_down_1");
        up2 = setup("projectiles/rock_down_1");
        down1 = setup("projectiles/rock_down_1");
        down2 = setup("projectiles/rock_down_1");
        left1 = setup("projectiles/rock_down_1");
        left2 = setup("projectiles/rock_down_1");
        right1 = setup("projectiles/rock_down_1");
        right2 = setup("projectiles/rock_down_1");
    }
}
