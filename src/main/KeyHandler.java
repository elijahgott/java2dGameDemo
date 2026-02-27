package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import tile.TileManager;

public class KeyHandler implements KeyListener {
    public boolean upPressed, downPressed, leftPressed, rightPressed, enterPressed, spacePressed, shiftPressed, shotKeyPressed;

    GamePanel gp;

    public boolean godModeOn = false;

    public KeyHandler(GamePanel gp) {
        this.gp = gp;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // not used
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        // TITLE STATE
        if(gp.gameState == gp.titleState){
            titleState(code);
        }

        // PLAY STATE
        else if(gp.gameState == gp.playState){
            playState(code);
        }

        // PAUSE STATE
        else if(gp.gameState == gp.pauseState){
            pauseState(code);
        }

        // DIALOGUE STATE OR DIALOGUE STATE
        else if(gp.gameState == gp.dialogueState || gp.gameState == gp.cutsceneState){
            dialogueState(code);
        }

        // INVENTORY STATE
        else if(gp.gameState == gp.inventoryState){
            inventoryState(code);
        }

        // CHARACTER STATE
        else if(gp.gameState == gp.characterState){
            characterState(code);
        }

        // OPTIONS STATE
        else if(gp.gameState == gp.optionsState){
            optionsState(code);
        }

        // GAME OVER STATE
        else if(gp.gameState == gp.gameOverState){
            gameOverState(code);
        }

        // TRADE STATE
        else if(gp.gameState == gp.tradeState){
            tradeState(code);
        }

        // MAP STATE
        else if(gp.gameState == gp.mapState){
            mapState(code);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        if(code == KeyEvent.VK_W){
            upPressed = false;
        }
        if(code == KeyEvent.VK_S){
            downPressed = false;
        }
        if(code == KeyEvent.VK_A){
            leftPressed = false;
        }
        if(code == KeyEvent.VK_D){
            rightPressed = false;
        }
        if(code == KeyEvent.VK_F){
            shotKeyPressed = false;
        }
        if(code == KeyEvent.VK_SHIFT){
            shiftPressed = false;
        }
        if(code == KeyEvent.VK_SPACE){
            spacePressed = false;
        }
        if(code == KeyEvent.VK_ENTER){
            enterPressed = false;
        }
    }

    public void titleState(int code){
        int maxCommandNumber = 2;
        if(code == KeyEvent.VK_W || code == KeyEvent.VK_UP){
            gp.ui.commandNumber--;
            gp.playSoundEffect(10);
            if(gp.ui.commandNumber < 0){
                gp.ui.commandNumber = maxCommandNumber;
            }
        }
        if(code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN){
            gp.ui.commandNumber++;
            gp.playSoundEffect(10);
            if(gp.ui.commandNumber > maxCommandNumber){
                gp.ui.commandNumber = 0;
            }
        }
        if(code == KeyEvent.VK_ENTER){
            if(gp.ui.commandNumber == 0){
                // NEW GAME
                gp.gameState = gp.playState;
                gp.playMusic(0); // play song when game loads

                // video #17 shows how to add character class selection in a new game
            }
            else if(gp.ui.commandNumber == 1){
                // LOAD GAME
                gp.saveLoad.load();
                gp.gameState = gp.playState;
                gp.playMusic(0); // play song when game loads
            }
            else{
                // QUIT GAME
                System.exit(0);
            }
        }
    }

    public void playState(int code){
        if(code == KeyEvent.VK_W){
            upPressed = true;
        }
        if(code == KeyEvent.VK_S){
            downPressed = true;
        }
        if(code == KeyEvent.VK_A){
            leftPressed = true;
        }
        if(code == KeyEvent.VK_D){
            rightPressed = true;
        }
        if(code == KeyEvent.VK_ENTER){
            enterPressed = true;
        }
        if(code == KeyEvent.VK_SHIFT){
            shiftPressed = true;
        }
        if(code == KeyEvent.VK_SPACE){
            spacePressed = true;
        }
        if(code == KeyEvent.VK_F){
            shotKeyPressed = true;
        }
        if(code == KeyEvent.VK_ESCAPE) {
            gp.gameState = gp.pauseState;
        }
        if(code == KeyEvent.VK_C){
            gp.gameState = gp.characterState;
        }
        if(code == KeyEvent.VK_E){
            gp.gameState = gp.inventoryState;
        }
        if(code == KeyEvent.VK_M){
            gp.gameState = gp.mapState;
        }
        if(code == KeyEvent.VK_N){
            gp.map.minimapOn = !gp.map.minimapOn;
        }

        // DEBUG
        if(code == KeyEvent.VK_F3){
            gp.debug = !gp.debug;
        }
        if(code == KeyEvent.VK_R){
            // i dont think this works with intellij
            switch(gp.currentMap){
                case 0:
                    gp.tileManager.loadMap("/maps/world01.txt", 0);
                    break;
                case 1:
                    gp.tileManager.loadMap("/maps/interior01.txt", 1);
                    break;
            }
        }
        if(code == KeyEvent.VK_0){
            godModeOn = !godModeOn;
        }
    }

    public void pauseState(int code){
        if(code == KeyEvent.VK_ESCAPE){
            gp.gameState = gp.playState;
        }
        int maxCommandNumber = 2;
        if(code == KeyEvent.VK_W || code == KeyEvent.VK_UP){
            gp.ui.commandNumber--;
            gp.playSoundEffect(10);
            if(gp.ui.commandNumber < 0){
                gp.ui.commandNumber = maxCommandNumber;
            }
        }
        if(code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN){
            gp.ui.commandNumber++;
            gp.playSoundEffect(10);

            if(gp.ui.commandNumber > maxCommandNumber){
                gp.ui.commandNumber = 0;
            }
        }
        if(code == KeyEvent.VK_ENTER){
            if(gp.ui.commandNumber == 0){
                // resume
                gp.gameState = gp.playState;
            }
            else if(gp.ui.commandNumber == 1){
                // show options
                gp.ui.commandNumber = 0;
                gp.gameState = gp.optionsState;
            }
            else{
                // quit game
                gp.gameState = gp.optionsState;
                gp.ui.subState = 3;
                gp.ui.commandNumber = 0;
            }
        }
    }

    public void dialogueState(int code){
        if(code == KeyEvent.VK_SPACE || code == KeyEvent.VK_ENTER){
            enterPressed = true;
        }
    }

    public void inventoryState(int code){
        if(code == KeyEvent.VK_E || code == KeyEvent.VK_ESCAPE){
            gp.gameState = gp.playState;
        }
        if(code == KeyEvent.VK_C){
            gp.gameState = gp.characterState;
        }
        // handles WASD
        playerInventory(code);

        if(code == KeyEvent.VK_SPACE || code == KeyEvent.VK_ENTER){
            gp.player.selectItem();
        }
    }

    public void characterState(int code){
        if(code == KeyEvent.VK_C || code == KeyEvent.VK_ESCAPE){
            gp.gameState = gp.playState;
        }
        if(code == KeyEvent.VK_E){
            gp.gameState = gp.inventoryState;
        }
    }

    public void optionsState(int code){
        if(code == KeyEvent.VK_ESCAPE){
            gp.gameState = gp.pauseState;
            gp.ui.commandNumber = 1;
        }
        if(code == KeyEvent.VK_ENTER){
            enterPressed = true;
        }

        int maxCommandNumber = 4;
        if(code == KeyEvent.VK_W || code == KeyEvent.VK_UP){
            gp.ui.commandNumber--;
            gp.playSoundEffect(10);
            if(gp.ui.commandNumber < 0){
                gp.ui.commandNumber = maxCommandNumber;
            }
        }
        if(code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN){
            gp.ui.commandNumber++;
            gp.playSoundEffect(10);
            if(gp.ui.commandNumber > maxCommandNumber){
                gp.ui.commandNumber = 0;
            }
        }
        if(code == KeyEvent.VK_ENTER){
            if(gp.ui.commandNumber == 0){
                // full screen
            }
            else if(gp.ui.commandNumber == 1){
                // music volume
            }
            else if(gp.ui.commandNumber == 2){
                // sound effect volume
            }
            else if(gp.ui.commandNumber == 3){
                // controls
            }
            else{
                // back
                gp.gameState = gp.pauseState;
                gp.ui.commandNumber = 1;
            }
        }
        if(code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT){
            if(gp.ui.subState == 0){
                if(gp.ui.commandNumber == 1 && gp.music.volumeScale > 0){
                    gp.music.volumeScale--;
                    gp.music.checkVolume();
                    gp.playSoundEffect(10);
                }
                if(gp.ui.commandNumber == 2 && gp.soundEffect.volumeScale > 0){
                    gp.soundEffect.volumeScale--;
                    gp.playSoundEffect(10);
                }
            }
        }
        if(code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT){
            if(gp.ui.subState == 0){
                if(gp.ui.commandNumber == 1 && gp.music.volumeScale < 5){
                    gp.music.volumeScale++;
                    gp.music.checkVolume();
                    gp.playSoundEffect(10);
                }
                if(gp.ui.commandNumber == 2 && gp.soundEffect.volumeScale < 5){
                    gp.soundEffect.volumeScale++;
                    gp.playSoundEffect(10);
                }
            }
        }
    }

    public void gameOverState(int code){
        int maxCommandNumber = 1;
        if(code == KeyEvent.VK_W || code == KeyEvent.VK_UP){
            gp.ui.commandNumber--;
            gp.playSoundEffect(10);
            if(gp.ui.commandNumber < 0){
                gp.ui.commandNumber = maxCommandNumber;
            }
        }
        if(code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN){
            gp.ui.commandNumber++;
            gp.playSoundEffect(10);
            if(gp.ui.commandNumber > maxCommandNumber){
                gp.ui.commandNumber = 0;
            }
        }

        if(code == KeyEvent.VK_ENTER){
            if(gp.ui.commandNumber == 0){
                // retry
                gp.resetGame(false);
                gp.playMusic(0);
                gp.gameState = gp.playState;
            }
            else if(gp.ui.commandNumber == 1){
                gp.resetGame(true);
                gp.gameState = gp.titleState;
            }
        }
    }

    public void tradeState(int code){
        if(code == KeyEvent.VK_ESCAPE){
            if(gp.ui.subState ==0){
                gp.gameState = gp.playState;
            }
            else{
                gp.ui.subState--;
            }
        }
        if(code == KeyEvent.VK_ENTER || code == KeyEvent.VK_SPACE){
            enterPressed = true;
        }
        if(gp.ui.subState == 0){
            int maxCommandNumber = 2;
            if(code == KeyEvent.VK_W || code == KeyEvent.VK_UP){
                gp.ui.commandNumber--;
                gp.playSoundEffect(10); // cursor
                if(gp.ui.commandNumber < 0){
                    gp.ui.commandNumber = maxCommandNumber;
                }
            }
            if(code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN){
                gp.ui.commandNumber++;
                gp.playSoundEffect(10); // cursor
                if(gp.ui.commandNumber > maxCommandNumber){
                    gp.ui.commandNumber = 0;
                }
            }
        }
        else if(gp.ui.subState == 1){
            npcInventory(code);
            if(code == KeyEvent.VK_ESCAPE){
                gp.ui.subState = 0;
            }
        }
        else if(gp.ui.subState == 2){
            playerInventory(code);
            if(code == KeyEvent.VK_ESCAPE){
                gp.ui.subState = 0;
            }
        }
    }

    public void playerInventory(int code){
        int maxRow = 2; // 3rd row = index 2
        int maxCol = 6; // 7th col = index 6
        if(code == KeyEvent.VK_W || code == KeyEvent.VK_UP){
            // do not allow row outside of range (5 rows)
            if(gp.ui.playerSlotRow != 0) {
                gp.ui.playerSlotRow--;
            }
            else{
                gp.ui.playerSlotRow = maxRow;
            }
            gp.playSoundEffect(10);
        }
        // do not allow col outside of range (7 cols)
        if(code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT){
            if(gp.ui.playerSlotCol != 0) {
                gp.ui.playerSlotCol--;
            }
            else{
                gp.ui.playerSlotCol = maxCol;
            }
            gp.playSoundEffect(10);
        }
        if(code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN){
            if(gp.ui.playerSlotRow != maxRow) {
                gp.ui.playerSlotRow++;
            }
            else{
                gp.ui.playerSlotRow = 0;
            }
            gp.playSoundEffect(10);
        }
        if(code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT){
            if(gp.ui.playerSlotCol != maxCol) {
                gp.ui.playerSlotCol++;
            }
            else{
                gp.ui.playerSlotCol = 0;
            }
            gp.playSoundEffect(10);
        }
    }

    public void npcInventory(int code){
        if(code == KeyEvent.VK_W || code == KeyEvent.VK_UP){
            // do not allow row outside of range (5 rows)
            if(gp.ui.npcSlotRow != 0) {
                gp.ui.npcSlotRow--;
            }
            else{
                gp.ui.npcSlotRow = 3;
            }
            gp.playSoundEffect(10);
        }
        // do not allow col outside of range (9 cols)
        if(code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT){
            if(gp.ui.npcSlotCol != 0) {
                gp.ui.npcSlotCol--;
            }
            else{
                gp.ui.npcSlotCol = 6;
            }
            gp.playSoundEffect(10);
        }
        if(code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN){
            if(gp.ui.npcSlotRow != 3) {
                gp.ui.npcSlotRow++;
            }
            else{
                gp.ui.npcSlotRow = 0;
            }
            gp.playSoundEffect(10);
        }
        if(code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT){
            if(gp.ui.npcSlotCol != 6) {
                gp.ui.npcSlotCol++;
            }
            else{
                gp.ui.npcSlotCol = 0;
            }
            gp.playSoundEffect(10);
        }
    }

    public void mapState(int code){
        if(code == KeyEvent.VK_M ||  code == KeyEvent.VK_ESCAPE){
            gp.gameState = gp.playState;
        }
    }
}
