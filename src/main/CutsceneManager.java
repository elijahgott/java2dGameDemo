package main;

import data.SaveLoad;
import entity.Entity;
import entity.PlayerDummy;
import entity.monster.MON_Skeleton_Lord;
import object.OBJ_Door_Iron;
import object.OBJ_Heart_Blue;

import java.awt.*;

public class CutsceneManager {
    GamePanel gp;
    Graphics2D g2;
    public int sceneNum;
    public int scenePhase;

    int counter = 0;
    float alpha = 0F;
    int y;

    String title;
    String endCredits;

    // Scene Numbers
    public final int NA = 0;
    public final int skeletonLord = 1;
    public final int ending = 2;

    public CutsceneManager(GamePanel gp) {
        this.gp = gp;

        title = "DA JOKAH ADVENTURE";

        endCredits =
        "Lead Programmer\n" +
        "RyiSnow\n" +
        "\n" +
        "Associate Programmer\n" +
        "Elijah Gott\n" +
        "\n\n\n" +

        "Lead Artist\n" +
        "RyiSnow\n" +
        "\n" +
        "Associate Artist\n" +
        "Elijah Gott\n" +
        "\n\n\n" +

        "Senior Musician\n" +
        "RyiSnow\n";
    }

    public void draw(Graphics2D g2) {
        this.g2 = g2;

        switch(sceneNum){
            case skeletonLord:
                skeletonLordCutscene();
                break;
            case ending:
                endingCutscene();
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

    public void endingCutscene(){
        gp.saveLoad.save();

        if(scenePhase == 0){
            gp.stopMusic();
            gp.ui.npc = new OBJ_Heart_Blue(gp);
            scenePhase++;
        }
        else if(scenePhase == 1){
            // display dialogues
            gp.ui.drawDialogueScreen();
        }
        else if(scenePhase == 2){
            // turn player to face camera
            gp.player.direction = "down";

            // play the fanfare music
            gp.playSoundEffect(4);
            scenePhase++;
        }
        else if(scenePhase == 3){
            if(counterReached(4 * 60)){
                scenePhase++;
            }
        }
        else if(scenePhase == 4){
            // fade screen to black
            alpha += 0.005F;
            if(alpha >= 1F){
                alpha = 1F;
            }
            drawBlackBackground(alpha);

            if(alpha == 1F){
                alpha = 0F;
                scenePhase++;
            }
        }
        else if(scenePhase == 5){
            // draw ending text
            drawBlackBackground(1F);
            alpha += 0.005F;
            if(alpha >= 1F){
                alpha = 1F;
            }
            String text = "The Joker beat the Skeleton Lord\n" +
                    "and obtained the Blue Heart to give himself\n" +
                    "the upper hand in his biggest battle yet...\n" +
                    "against THE BATMAN";

            drawString(alpha, 20F, 200, text, 54, "na");
            if(counterReached(10 * 60)){
                gp.playMusic(0);
                scenePhase++;
            }
        }
        else if(scenePhase == 6){
            // ending credits
            drawBlackBackground(1F);

            y = gp.screenHeight / 2;

            drawString(1F, 54F, y, "Da Jokah Adventure", 0, "title");
            drawString(1F, 24F, y + 128, endCredits, 32, "na");

            if(counterReached(2 * 60)){
                scenePhase++;
            }
        }
        else if(scenePhase == 7){
            // scroll credits

            //idk if i like the black fade out
            alpha -= 0.005F;
            if(alpha <= 0F){
                alpha = 0F;
            }
            drawBlackBackground(alpha);

            y--;
            drawString(1F, 54F, y, "Da Jokah Adventure", 0, "title");
            drawString(1F, 24F, y + 128, endCredits, 32, "na");

            if(counterReached(5 * 60)){
                sceneNum = NA;
                scenePhase = 0;

                // respawn player
                gp.gameState = gp.titleState;
                gp.player.setDefaultPositions();
                gp.player.restoreStatus();
            }
        }
    }

    public boolean counterReached(int targetNum){
        boolean counterReached = false;
        counter++;
        if(counter >= targetNum){
            counterReached = true;
            counter = 0;
        }
        return counterReached;
    }

    public void drawBlackBackground(float alpha){
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
        // reset alpha
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1F));
    }

    public void removeBlackBackground(float alpha){
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
        // reset alpha
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0F));
    }

    public void drawString(float alpha, float fontSize, int y, String text, int lineHeight, String type){
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        g2.setColor(Color.WHITE);
        if(type.equals("title")){
            g2.setFont(gp.ui.UIFont.deriveFont(fontSize));
        }
        else{
            g2.setFont(gp.ui.dialogueFont.deriveFont(fontSize));
        }

        for(String line: text.split("\n")){
            int x = gp.ui.getXForCenteredText(line);
            g2.drawString(line, x, y);
            y += lineHeight;
        }

        // reset alpha
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1F));
    }
}
