package tile_interactive;

import entity.Entity;
import main.GamePanel;

import java.awt.*;

public class IT_Tree_Spruce extends InteractiveTile {
    GamePanel gp;

    public IT_Tree_Spruce(GamePanel gp, int col, int row){
        super(gp, col, row);
        this.gp = gp;

        this.worldX = gp.tileSize * col;
        this.worldY = gp.tileSize * row;

        health = 4;
        height = 2;
        down1 = setup("tiles_interactive/tree_spruce", gp.tileSize,  gp.tileSize * 2);
        destructible = true;

        // collision box
        solidArea.x = 14;
        solidArea.y = 32;
        solidArea.width = 20;
        solidArea.height = gp.tileSize - solidArea.y;
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
        return new IT_Trunk(gp, worldX / gp.tileSize, worldY / gp.tileSize); // place trunk one tile down
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
