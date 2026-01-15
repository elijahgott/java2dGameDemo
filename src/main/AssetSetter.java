package main;

import entity.NPC_OldMan;
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
        gp.obj[0] = new OBJ_Boots(gp);
        gp.obj[0].worldX = gp.tileSize * 20;
        gp.obj[0].worldY = gp.tileSize * 19;

        gp.obj[1] = new OBJ_Chest(gp);
        gp.obj[1].worldX = gp.tileSize * 21;
        gp.obj[1].worldY = gp.tileSize * 19;

        gp.obj[2] = new OBJ_Door(gp);
        gp.obj[2].worldX = gp.tileSize * 22;
        gp.obj[2].worldY = gp.tileSize * 19;

    }

    public void setNPC(){
        gp.npc[0] = new NPC_OldMan(gp);
        gp.npc[0].worldX = gp.tileSize * 20;
        gp.npc[0].worldY = gp.tileSize * 16;

        gp.npc[1] = new NPC_OldMan(gp);
        gp.npc[1].worldX = gp.tileSize * 21;
        gp.npc[1].worldY = gp.tileSize * 16;

        gp.npc[2] = new NPC_OldMan(gp);
        gp.npc[2].worldX = gp.tileSize * 22;
        gp.npc[2].worldY = gp.tileSize * 16;

    }
}
