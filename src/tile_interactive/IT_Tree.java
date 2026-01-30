package tile_interactive;

import entity.Entity;
import main.GamePanel;

import java.awt.Color;

public class IT_Tree extends InteractiveTile{
    GamePanel gp;

    public IT_Tree(GamePanel gp, int col, int row){
        super(gp, col, row);
        this.gp = gp;

        this.worldX = gp.tileSize * col;
        this.worldY = gp.tileSize * row;

        health = 4;
        down1 = setup("tiles_interactive/drytree");
        destructible = true;
    }

    public boolean isCorrectItem(Entity entity){
        return entity.currentWeapon.type == type_axe;
    }

    public void playSoundEffect(){
        gp.playSoundEffect(12);
    }

    public InteractiveTile getDestroyedForm(){
        InteractiveTile tile = new IT_Trunk(gp, worldX / gp.tileSize, worldY / gp.tileSize);
        return tile;
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
