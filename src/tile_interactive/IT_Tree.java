package tile_interactive;

import entity.Entity;
import main.GamePanel;

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
}
