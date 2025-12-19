package main;

import object.OBJ_Key;
import object.OBJ_Door;
import object.OBJ_Chest;
import object.OBJ_Boots;

public class AssetSetter {
    GamePanel gp;

    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

    public void setObject() {
        // key by water
        gp.obj[0] = new OBJ_Key();
        gp.obj[0].worldX = 40 * gp.tileSize;
        gp.obj[0].worldY = 3 * gp.tileSize;

        // key in woods
        gp.obj[1] = new OBJ_Key();
        gp.obj[1].worldX = 25 * gp.tileSize;
        gp.obj[1].worldY = 26 * gp.tileSize;

        // door 1 to woods
        gp.obj[2] = new OBJ_Door();
        gp.obj[2].worldX = 31 * gp.tileSize;
        gp.obj[2].worldY = 25 * gp.tileSize;

        // door 2 to woods
        gp.obj[3] = new OBJ_Door();
        gp.obj[3].worldX = 13 * gp.tileSize;
        gp.obj[3].worldY = 28 * gp.tileSize;

        // chest by house
        gp.obj[4] = new OBJ_Chest();
        gp.obj[4].worldX = 4 * gp.tileSize;
        gp.obj[4].worldY = 4 * gp.tileSize;

        // boots by house
        gp.obj[5] = new OBJ_Boots();
        gp.obj[5].worldX = 6 * gp.tileSize;
        gp.obj[5].worldY = 4 * gp.tileSize;
    }
}
