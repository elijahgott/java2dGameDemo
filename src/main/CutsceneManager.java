package main;

import entity.Entity;
import entity.PlayerDummy;
import entity.monster.MON_Skeleton_Lord;
import object.OBJ_Door_Iron;

import java.awt.*;

public class CutsceneManager {
    GamePanel gp;
    Graphics2D g2;
    public int sceneNum;
    public int scenePhase;

    // Scene Numbers
    public final int NA = 0;
    public final int skeletonLord = 1;

    public CutsceneManager(GamePanel gp) {
        this.gp = gp;
    }

    public void draw(Graphics2D g2) {
        this.g2 = g2;

        switch(sceneNum){
            case skeletonLord:
                skeletonLordCutscene();
                break;
        }
    }

    public void skeletonLordCutscene(){
        // place iron door behind player
        if(scenePhase == 0){
            gp.bossBattleOn = true;

            // place iron door behind player
            for(int i = 0; i < gp.obj[1].length; i++){
                if(gp.obj[gp.currentMap][i] == null){
                    gp.obj[gp.currentMap][i] = new OBJ_Door_Iron(gp);
                    gp.obj[gp.currentMap][i].worldX = gp.tileSize * 25;
                    gp.obj[gp.currentMap][i].worldY = gp.tileSize * 28;
                    gp.obj[gp.currentMap][i].temporary = true;

                    gp.playSoundEffect(21);
                    break;
                }
            }

            // search for vacant slot for player dummy
            for(int i = 0; i < gp.npc[1].length; i++){
                if(gp.npc[gp.currentMap][i] == null){
                    gp.npc[gp.currentMap][i] = new PlayerDummy(gp);
                    gp.npc[gp.currentMap][i].worldX = gp.player.worldX;
                    gp.npc[gp.currentMap][i].worldY = gp.player.worldY;
                    gp.npc[gp.currentMap][i].direction = gp.player.direction;
                    break;
                }
            }

            gp.player.drawing = false;
            scenePhase++;
        }
        // move camera up to show boss
        else if(scenePhase == 1){
            gp.player.worldY -= 2;

            if(gp.player.worldY <= gp.tileSize * 16){
                scenePhase++;
            }
        }
        // wake up boss
        else if(scenePhase == 2){
            // search for boss
            for(int i = 0; i < gp.monster[1].length; i++){
                if(gp.monster[gp.currentMap][i] != null && gp.monster[gp.currentMap][i].name.equals(MON_Skeleton_Lord.monName)){
                    gp.monster[gp.currentMap][i].sleep = false;
                    gp.ui.npc = gp.monster[gp.currentMap][i];
                    scenePhase++;
                    break;
                }
            }
        }
        // boss speaking
        else if(scenePhase == 3){
            gp.ui.drawDialogueScreen();
        }
        // move camera back to player
        else if(scenePhase == 4){
            for(int i = 0; i < gp.npc[1].length; i++){
                if(gp.npc[gp.currentMap][i] != null && gp.npc[gp.currentMap][i].name.equals(PlayerDummy.npcName)){
                    gp.player.worldY += 2;

                    if(gp.player.worldY >= gp.npc[gp.currentMap][i].worldY){
                        gp.player.worldY = gp.npc[gp.currentMap][i].worldY;

                        gp.npc[gp.currentMap][i] = null;

                        // draw player
                        gp.player.drawing = true;

                        // reset
                        sceneNum = NA;
                        scenePhase = 0;
                        gp.gameState = gp.playState;

                        // play boss battle music
                        gp.stopMusic();
                        gp.playMusic(22);

                        break;
                    }
                }
            }
        }
    }
}
