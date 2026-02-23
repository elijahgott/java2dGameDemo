package main;

import entity.NPC_Big_Rock;
import entity.NPC_Merchant;
import entity.NPC_OldMan;
import entity.monster.MON_Orc;
import entity.monster.MON_GreenSlime;
import object.*;
import tile_interactive.IT_Metal_Plate;
import tile_interactive.IT_Tree;
import tile_interactive.IT_Tree_Spruce;
import tile_interactive.IT_Wall_Destructible;

public class AssetSetter {
    GamePanel gp;

    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

    public void setObject() {
        int i = 0;
        int currentMap = 0; // main world

        gp.obj[currentMap][i] = new OBJ_Axe_Normal(gp);
        gp.obj[currentMap][i].worldX = gp.tileSize * 13;
        gp.obj[currentMap][i].worldY = gp.tileSize * 3;
        i++;

        gp.obj[currentMap][i] = new OBJ_Boots(gp);
        gp.obj[currentMap][i].worldX = gp.tileSize * 14;
        gp.obj[currentMap][i].worldY = gp.tileSize * 3;
        i++;

        gp.obj[currentMap][i] = new OBJ_Key(gp);
        gp.obj[currentMap][i].worldX = gp.tileSize * 15;
        gp.obj[currentMap][i].worldY = gp.tileSize * 3;
        i++;

        gp.obj[currentMap][i] = new OBJ_Coin(gp);
        gp.obj[currentMap][i].worldX = gp.tileSize * 16;
        gp.obj[currentMap][i].worldY = gp.tileSize * 3;
        i++;

        gp.obj[currentMap][i] = new OBJ_Coin(gp);
        gp.obj[currentMap][i].worldX = gp.tileSize * 16;
        gp.obj[currentMap][i].worldY = gp.tileSize * 4;
        i++;

        gp.obj[currentMap][i] = new OBJ_Shield_Blue(gp);
        gp.obj[currentMap][i].worldX = gp.tileSize * 17;
        gp.obj[currentMap][i].worldY = gp.tileSize * 3;
        i++;

        gp.obj[currentMap][i] = new OBJ_Potion_Red(gp);
        gp.obj[currentMap][i].worldX = gp.tileSize * 18;
        gp.obj[currentMap][i].worldY = gp.tileSize * 3;
        i++;

        gp.obj[currentMap][i] = new OBJ_Heart(gp);
        gp.obj[currentMap][i].worldX = gp.tileSize * 19;
        gp.obj[currentMap][i].worldY = gp.tileSize * 3;
        i++;

        gp.obj[currentMap][i] = new OBJ_ManaCrystal(gp);
        gp.obj[currentMap][i].worldX = gp.tileSize * 20;
        gp.obj[currentMap][i].worldY = gp.tileSize * 3;
        i++;

        // door to house
        gp.obj[currentMap][i] = new OBJ_Door(gp, 1, 12, 12);
        gp.obj[currentMap][i].worldX = gp.tileSize * 2;
        gp.obj[currentMap][i].worldY = gp.tileSize * 4;
        i++;

        gp.obj[currentMap][i] = new OBJ_Chest(gp);
        gp.obj[currentMap][i].setLoot(new OBJ_Key(gp));
        gp.obj[currentMap][i].worldX = gp.tileSize * 6;
        gp.obj[currentMap][i].worldY = gp.tileSize * 12;
        i++;

        gp.obj[currentMap][i] = new OBJ_Lantern(gp);
        gp.obj[currentMap][i].worldX = gp.tileSize * 5;
        gp.obj[currentMap][i].worldY = gp.tileSize * 12;
        i++;

        gp.obj[currentMap][i] = new OBJ_Tent(gp);
        gp.obj[currentMap][i].worldX = gp.tileSize * 5;
        gp.obj[currentMap][i].worldY = gp.tileSize * 14;
        i++;

        currentMap++; // interior map
        i = 0;

        currentMap++; // dungeon 1 map
        i = 0;

        gp.obj[currentMap][i] = new OBJ_Chest(gp);
        gp.obj[currentMap][i].setLoot(new OBJ_Pickaxe_Normal(gp));
        gp.obj[currentMap][i].worldX = gp.tileSize * 40;
        gp.obj[currentMap][i].worldY = gp.tileSize * 41;
        i++;

        gp.obj[currentMap][i] = new OBJ_Door_Iron(gp);
        gp.obj[currentMap][i].worldX = gp.tileSize * 18;
        gp.obj[currentMap][i].worldY = gp.tileSize * 23;
        i++;
    }

    public void setNPC(){
        int i = 0;
        int currentMap = 0;

        gp.npc[currentMap][i] = new NPC_OldMan(gp);
        gp.npc[currentMap][i].worldX = gp.tileSize * 20;
        gp.npc[currentMap][i].worldY = gp.tileSize * 16;
        i++;

        currentMap++; // house interior
        i = 0;

        gp.npc[currentMap][i] = new NPC_Merchant(gp);
        gp.npc[currentMap][i].worldX = gp.tileSize * 12;
        gp.npc[currentMap][i].worldY = gp.tileSize * 7;
        i++;

        currentMap++; // dungeon 1
        i = 0;

        // big rocks
        gp.npc[currentMap][i] = new NPC_Big_Rock(gp);
        gp.npc[currentMap][i].worldX = gp.tileSize * 20;
        gp.npc[currentMap][i].worldY = gp.tileSize * 25;
        i++;

        gp.npc[currentMap][i] = new NPC_Big_Rock(gp);
        gp.npc[currentMap][i].worldX = gp.tileSize * 11;
        gp.npc[currentMap][i].worldY = gp.tileSize * 18;
        i++;

        gp.npc[currentMap][i] = new NPC_Big_Rock(gp);
        gp.npc[currentMap][i].worldX = gp.tileSize * 23;
        gp.npc[currentMap][i].worldY = gp.tileSize * 14;
        i++;
    }

    public void setMonster(){
        int i = 0;
        int currentMap = 0;

        gp.monster[currentMap][i] = new MON_GreenSlime(gp);
        gp.monster[currentMap][i].worldX = gp.tileSize * 12;
        gp.monster[currentMap][i].worldY = gp.tileSize * 8;
        i++;

        gp.monster[currentMap][i] = new MON_Orc(gp);
        gp.monster[currentMap][i].worldX = gp.tileSize * 5;
        gp.monster[currentMap][i].worldY = gp.tileSize * 12;
        i++;
    }

    // manually set interactive tiles, I don't want to do it this way
    public void setInteractiveTile(){
        int currentMap = 0;
        int i = gp.interactiveTile[currentMap].length - 1;

        gp.interactiveTile[currentMap][i] = new IT_Tree_Spruce(gp, 16, 8);
        i--;

        gp.interactiveTile[currentMap][i] = new IT_Tree_Spruce(gp, 17, 8);
        i--;

        gp.interactiveTile[currentMap][i] = new IT_Tree_Spruce(gp, 18, 8);
        i--;

        currentMap++; // inside merchant house
        i = 0;

        currentMap++; // dungeon 1
        i = 0;

        // walls
        gp.interactiveTile[currentMap][i] = new IT_Wall_Destructible(gp, 18, 30); i++;
        gp.interactiveTile[currentMap][i] = new IT_Wall_Destructible(gp, 17, 31); i++;
        gp.interactiveTile[currentMap][i] = new IT_Wall_Destructible(gp, 17, 32); i++;
        gp.interactiveTile[currentMap][i] = new IT_Wall_Destructible(gp, 17, 34); i++;
        gp.interactiveTile[currentMap][i] = new IT_Wall_Destructible(gp, 18, 34); i++;
        gp.interactiveTile[currentMap][i] = new IT_Wall_Destructible(gp, 18, 34); i++;

        // metal plates
        gp.interactiveTile[currentMap][i] = new IT_Metal_Plate(gp, 20, 22); i++;
        gp.interactiveTile[currentMap][i] = new IT_Metal_Plate(gp, 8, 17); i++;
        gp.interactiveTile[currentMap][i] = new IT_Metal_Plate(gp, 39, 31); i++;
    }

    public void setInteractiveTile(int index, String type, int map, int col, int row){
            if(gp.interactiveTile[map][index] == null){ // can set interactive tile
                switch (type){
                    case "IT_tree":
                        gp.interactiveTile[map][index] = new IT_Tree(gp, col, row);
                        break;
                    case "IT_wall":
                        // placeholder for other types of interactable tiles i want to put on a tile map
                        break;
                    default:
                        System.out.println("ERROR: Unknown interactive tile type: " + type);
                }
            }
            else{

            }
    }
}
