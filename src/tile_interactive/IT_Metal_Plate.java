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

        // collision box disabled
        solidArea.x = 0;
        solidArea.y = 0;
        solidArea.width = 0;
        solidArea.height = 0;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }

    public void playSoundEffect(){
        gp.playSoundEffect(12);
    }
}
