package main;

import entity.NPC_OldMan;
import monster.MON_GreenSlime;
import object.*;

import java.security.Key;

public class AssetSetter {
    GamePanel gp;

    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

    public void setObject() {
        int i = 0;

        gp.obj[i] = new OBJ_Axe_Normal(gp);
        gp.obj[i].worldX = gp.tileSize * 13;
        gp.obj[i].worldY = gp.tileSize * 3;
        i++;

        gp.obj[i] = new OBJ_Boots(gp);
        gp.obj[i].worldX = gp.tileSize * 14;
        gp.obj[i].worldY = gp.tileSize * 3;
        i++;

        gp.obj[i] = new OBJ_Key(gp);
        gp.obj[i].worldX = gp.tileSize * 15;
        gp.obj[i].worldY = gp.tileSize * 3;
        i++;

        gp.obj[i] = new OBJ_Key(gp);
        gp.obj[i].worldX = gp.tileSize * 16;
        gp.obj[i].worldY = gp.tileSize * 3;
        i++;

        gp.obj[i] = new OBJ_Shield_Blue(gp);
        gp.obj[i].worldX = gp.tileSize * 17;
        gp.obj[i].worldY = gp.tileSize * 3;
        i++;

        gp.obj[i] = new OBJ_Potion_Red(gp);
        gp.obj[i].worldX = gp.tileSize * 18;
        gp.obj[i].worldY = gp.tileSize * 3;
        i++;
    }

    public void setNPC(){
        int i = 0;

        gp.npc[i] = new NPC_OldMan(gp);
        gp.npc[i].worldX = gp.tileSize * 20;
        gp.npc[i].worldY = gp.tileSize * 16;
        i++;
    }

    public void setMonster(){
        int i = 0;

        gp.monster[i] = new MON_GreenSlime(gp);
        gp.monster[i].worldX = gp.tileSize * 12;
        gp.monster[i].worldY = gp.tileSize * 8;
        i++;

        gp.monster[i] = new MON_GreenSlime(gp);
        gp.monster[i].worldX = gp.tileSize * 13;
        gp.monster[i].worldY = gp.tileSize * 10;
        i++;

        gp.monster[i] = new MON_GreenSlime(gp);
        gp.monster[i].worldX = gp.tileSize * 10;
        gp.monster[i].worldY = gp.tileSize * 6;
        i++;

        gp.monster[i] = new MON_GreenSlime(gp);
        gp.monster[i].worldX = gp.tileSize * 6;
        gp.monster[i].worldY = gp.tileSize * 10;
        i++;
    }
}
