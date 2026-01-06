package main;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

public class UI {
    GamePanel gp;
    Graphics2D g2;
    Font UIFont, dialogueFont;
    public boolean messageOn = false;
    public String message = "";
    int messageCounter = 0;
    public boolean gameOver = false;
    public String currentDialogue;

    public UI(GamePanel gp) {
        this.gp = gp;

        try{
            InputStream is = getClass().getResourceAsStream("/font/PublicPixel.ttf");
            dialogueFont = Font.createFont(Font.TRUETYPE_FONT, is);

            is = getClass().getResourceAsStream("/font/Boxy-Bold.ttf");
            UIFont = Font.createFont(Font.TRUETYPE_FONT, is);
        }
        catch(FontFormatException e){
            e.printStackTrace();
        }
        catch(IOException e){
            e.printStackTrace();
        }
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
        // WINDOW
        int width = gp.screenWidth - (gp.tileSize * 2);
        int height = gp.tileSize * 4;
        int x = gp.tileSize;
        int y = (gp.screenHeight / 2) - (height / 2);

        drawSubWindow(x, y, width, height);

        String text = "PAUSED";

        g2.setFont(UIFont);
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 36F));

        x = getXForCenteredText(text);
        y = gp.screenHeight / 2;

        g2.drawString(text, x, y);
    }

    public void drawDialogueScreen(){
        // WINDOW
        int x = gp.tileSize;
        int y = gp.tileSize / 2;
        int width = gp.screenWidth - (gp.tileSize * 2);
        int height = gp.tileSize * 4;

        drawSubWindow(x, y, width, height);

        x += gp.tileSize / 2;
        y += gp.tileSize;

        g2.setFont(dialogueFont);
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 20F));

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
