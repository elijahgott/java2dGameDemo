package tile_interactive;

import entity.Entity;
import main.GamePanel;

import java.awt.*;

public class IT_Metal_Plate extends InteractiveTile{
    GamePanel gp;
    public static final String itName = "Metal Plate";

    public IT_Metal_Plate(GamePanel gp, int col, int row){
        super(gp, col, row);
        this.gp = gp;

        this.worldX = gp.tileSize * col;
        this.worldY = gp.tileSize * row;

        name = itName;
        health = 3;
        down1 = setup("tiles_interactive/metalplate");
        collision = false;
    }

    public void playSoundEffect(){
        gp.playSoundEffect(12);
    }
}
