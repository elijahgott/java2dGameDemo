package main;

import entity.NPC_OldMan;
import monster.MON_GreenSlime;
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

    }

    public void setNPC(){
        gp.npc[0] = new NPC_OldMan(gp);
        gp.npc[0].worldX = gp.tileSize * 20;
        gp.npc[0].worldY = gp.tileSize * 16;
    }

    public void setMonster(){
        gp.monster[0] = new MON_GreenSlime(gp);
        gp.monster[0].worldX = gp.tileSize * 18;
        gp.monster[0].worldY = gp.tileSize * 16;

        gp.monster[1] = new MON_GreenSlime(gp);
        gp.monster[1].worldX = gp.tileSize * 15;
        gp.monster[1].worldY = gp.tileSize * 17;
    }
}
