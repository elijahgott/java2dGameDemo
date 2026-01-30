package tile_interactive;

import entity.Entity;
import main.GamePanel;

public class IT_Trunk extends InteractiveTile{
    GamePanel gp;

    public IT_Trunk(GamePanel gp, int col, int row){
        super(gp, col, row);
        this.gp = gp;

        this.worldX = gp.tileSize * col;
        this.worldY = gp.tileSize * row;

        down1 = setup("tiles_interactive/trunk");
        destructible = true;

        // disable collision
        solidArea.x = 0;
        solidArea.y = 0;
        solidArea.width = 0;
        solidArea.height = 0;
        solidAreaDefaultX = 0;
        solidAreaDefaultY = 0;
    }

    public boolean isCorrectItem(Entity entity){
        return entity.currentWeapon.type == type_axe;
    }
}
