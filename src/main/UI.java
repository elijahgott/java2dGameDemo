package main;

import java.awt.*;

public class UI {
    GamePanel gp;
    Graphics2D g2;
    Font UIFont, gameOverFont, dialogueFont;
    public boolean messageOn = false;
    public String message = "";
    int messageCounter = 0;
    public boolean gameOver = false;
    public String currentDialogue;

    public UI(GamePanel gp) {
        this.gp = gp;

        UIFont = new Font("Arial", Font.PLAIN, 36);
        gameOverFont = new Font("Arial", Font.BOLD, 56);
        dialogueFont = new Font("Arial", Font.PLAIN, 32);
    }

    public void showMessage(String text){
        message = text;
        messageOn = true;
    }

    public void draw(Graphics2D g2){
        this.g2 = g2;
        g2.setFont(UIFont);
        g2.setColor(Color.white);

        // PLAY STATE
        if(gp.gameState == gp.playState){
            // do playState stuff later
        }

        // PAUSE STATE
        if(gp.gameState == gp.pauseState){
            drawPauseScreen();
        }

        // DIALOGUE STATE
        if(gp.gameState == gp.dialogueState){
            drawDialogueScreen();
        }
    }

    public void drawPauseScreen(){
        String text = "PAUSED";

        int x = getXForCenteredText(text);
        int y = gp.screenHeight / 2;

        g2.drawString(text, x, y);
    }

    public void drawDialogueScreen(){
        // WINDOW
        int x = gp.tileSize;
        int y = gp.tileSize / 2;
        int width = gp.screenWidth - (gp.tileSize * 2);
        int height = gp.tileSize * 4;

        drawSubWindow(x, y, width, height);

        x += gp.tileSize;
        y += gp.tileSize;
        g2.setFont(dialogueFont);

        for(String line : currentDialogue.split("\n")){
            g2.drawString(line, x, y);
            y += 36;
        }
    }

    public void drawSubWindow(int x, int y, int width, int height){
        Color backgroundColor = new Color(0, 0, 0, 200);
        g2.setColor(backgroundColor);
        g2.fillRect(x, y, width, height);

        Color borderColor = new Color(255, 255, 255);
        g2.setColor(borderColor);
        g2.setStroke(new BasicStroke(5));
        g2.drawRect(x + 5, y + 5, width - 10, height - 10);
    }

    public int getXForCenteredText(String text){
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        return (gp.screenWidth / 2) - (length / 2);
    }
}
