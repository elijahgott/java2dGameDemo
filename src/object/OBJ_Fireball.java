package object;

import entity.Entity;
import entity.Projectile;
import main.GamePanel;

import java.awt.*;

public class OBJ_Fireball extends Projectile {
    GamePanel gp;

    public static final String objName = "Fireball";

    public OBJ_Fireball(GamePanel gp) {
        super(gp);
        this.gp = gp;

        name = objName;
        speed = 2;
        maxHealth = 80;
        health = maxHealth;
        attack = 2;
        useCost = 1;
        alive = false;
        knockBackPower = 5;

        getImage();
    }

    public boolean hasResource(Entity user){
        boolean hasResource = false;

        if(user.mana >= useCost){
            hasResource = true;
        }

        return hasResource;
    }

    public void useResource(Entity user){
        user.mana -= useCost;
    }

    // PARTICLES
    public Color getParticleColor(){
        Color color = new Color(255,90,0);
        return color;
    }

    public int getParticleSize(){
        int size = 8;
        return size;
    }

    public int getParticleSpeed(){
        int speed = 1;
        return speed;
    }

    public int getParticleMaxHealth(){
        int maxHealth = 20;
        return maxHealth;
    }

    public void getImage(){
        up1 = setup("projectiles/fireball_up_1");
        up2 = setup("projectiles/fireball_up_2");
        down1 = setup("projectiles/fireball_down_1");
        down2 = setup("projectiles/fireball_down_2");
        left1 = setup("projectiles/fireball_left_1");
        left2 = setup("projectiles/fireball_left_2");
        right1 = setup("projectiles/fireball_right_1");
        right2 = setup("projectiles/fireball_right_2");
    }
}
