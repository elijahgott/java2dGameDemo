package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import tile.TileManager;

public class KeyHandler implements KeyListener {
    public boolean upPressed, downPressed, leftPressed, rightPressed, enterPressed, spacePressed;
    public boolean checkDrawTime = false;

    GamePanel gp;

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

        // DIALOGUE STATE
        else if(gp.gameState == gp.dialogueState){
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
    }

    public void titleState(int code){
        if(code == KeyEvent.VK_W || code == KeyEvent.VK_UP){
            gp.ui.commandNumber--;
            if(gp.ui.commandNumber < 0){
                gp.ui.commandNumber = 2;
            }
        }
        if(code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN){
            gp.ui.commandNumber++;
            if(gp.ui.commandNumber > 2){
                gp.ui.commandNumber = 0;
            }
        }
        if(code == KeyEvent.VK_ENTER){
            if(gp.ui.commandNumber == 0){
                // NEW GAME
                gp.gameState = gp.playState;
//                gp.playMusic(0);

                // video #17 shows how to add character class selection in a new game
            }
            else if(gp.ui.commandNumber == 1){
                // LOAD GAME
                // add later
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
        if(code == KeyEvent.VK_SPACE){
            spacePressed = true;
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
        // DEBUG
        if(code == KeyEvent.VK_F3){
            gp.debug = !gp.debug;
        }
        // DEBUG TOO
        if(code == KeyEvent.VK_T){
            checkDrawTime = !checkDrawTime;
        }
        if(code == KeyEvent.VK_R){
            gp.tileManager.loadMap("/maps/world01.txt");
        }
    }

    public void pauseState(int code){
        if(code == KeyEvent.VK_ESCAPE){
            gp.gameState = gp.playState;
        }
    }

    public void dialogueState(int code){
        if(code == KeyEvent.VK_SPACE || code == KeyEvent.VK_ENTER || code == KeyEvent.VK_ESCAPE){
            gp.gameState = gp.playState;
        }
    }

    public void inventoryState(int code){
        if(code == KeyEvent.VK_E || code == KeyEvent.VK_ESCAPE){
            gp.gameState = gp.playState;
        }
        if(code == KeyEvent.VK_C){
            gp.gameState = gp.characterState;
        }
        if(code == KeyEvent.VK_W || code == KeyEvent.VK_UP){
            // do not allow row outside of range (5 rows)
            if(gp.ui.slotRow != 0) {
                gp.ui.slotRow--;
            }
            else{
                gp.ui.slotRow = 3;
            }
            gp.playSoundEffect(10);
        }
        // do not allow col outside of range (9 cols)
        if(code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT){
            if(gp.ui.slotCol != 0) {
                gp.ui.slotCol--;
            }
            else{
                gp.ui.slotCol = 6;
            }
            gp.playSoundEffect(10);
        }
        if(code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN){
            if(gp.ui.slotRow != 3) {
                gp.ui.slotRow++;
            }
            else{
                gp.ui.slotRow = 0;
            }
            gp.playSoundEffect(10);
        }
        if(code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT){
            if(gp.ui.slotCol != 6) {
                gp.ui.slotCol++;
            }
            else{
                gp.ui.slotCol = 0;
            }
            gp.playSoundEffect(10);
        }
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
}
