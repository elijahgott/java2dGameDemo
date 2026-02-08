package tile_interactive;

import entity.Entity;
import main.GamePanel;

import java.awt.*;

public class IT_Trunk extends InteractiveTile{
    GamePanel gp;

    public IT_Trunk(GamePanel gp, int col, int row){
        super(gp, col, row);
        this.gp = gp;

        this.worldX = gp.tileSize * col;
        this.worldY = gp.tileSize * row;

        health = 3;
        down1 = setup("tiles_interactive/trunk");
        destructible = true;

        // collision box
        solidArea.x = 18;
        solidArea.y = 24;
        solidArea.width = 12;
        solidArea.height = 12;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }

    public boolean isCorrectItem(Entity entity){
        return entity.currentWeapon.type == type_axe;
    }

    public void playSoundEffect(){
        gp.playSoundEffect(12);
    }

    public InteractiveTile getDestroyedForm(){
        return null;
    }

    // PARTICLES
    public Color getParticleColor(){
        Color color = new Color(121,65,0);
        return color;
    }

    public int getParticleSize(){
        int size = 6; // 6 pixels
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
}
