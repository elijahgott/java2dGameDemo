package tile_interactive;

import entity.Entity;
import main.GamePanel;
import object.OBJ_Heart;
import object.OBJ_ManaCrystal;
import object.OBJ_Rock;

import java.awt.*;
import java.util.Random;

public class IT_Wall_Destructible extends InteractiveTile {
    GamePanel gp;

    public IT_Wall_Destructible(GamePanel gp, int col, int row){
        super(gp, col, row);
        this.gp = gp;

        this.worldX = gp.tileSize * col;
        this.worldY = gp.tileSize * row;

        health = 3;
        height = 1;
        down1 = setup("tiles_interactive/destructiblewall");
        destructible = true;

        // collision box
        solidArea.x = 0;
        solidArea.y = 0;
        solidArea.width = gp.tileSize;
        solidArea.height = gp.tileSize;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }

    public boolean isCorrectItem(Entity entity){
        return entity.currentWeapon.type == type_pickaxe;
    }

    public void playSoundEffect(){
        gp.playSoundEffect(12);
    }

    public InteractiveTile getDestroyedForm(){
        return null;
    }

    public void checkDrop(){
        int i  = new Random().nextInt(100) + 1; // random from 1-100

        // SET DROPS
        // 75% chance of rock
        if(i < 75){
            dropItem(new OBJ_Rock(gp));
        }
        else{
            // probably better way to drop multiple of same itemw
            dropItem(new OBJ_Rock(gp));
            dropItem(new OBJ_Rock(gp));
        }
    }

    // PARTICLES
    public Color getParticleColor(){
        Color color = new Color(50,50,50);
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
