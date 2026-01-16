package main;

import entity.Entity;
import object.OBJ_Heart;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class UI {
    GamePanel gp;
    Graphics2D g2;
    Font UIFont, dialogueFont;
    BufferedImage heart_full, heart_half, heart_empty;
    public boolean messageOn = false;
    public String message = "";
    int messageCounter = 0;
    public boolean gameOver = false;
    public String currentDialogue;

    public int commandNumber = 0;

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

        // CREATE HUD OBJECT
        Entity heart = new OBJ_Heart(gp);
        heart_full = heart.image;
        heart_half = heart.image2;
        heart_empty = heart.image3;
    }

    public void showMessage(String text){
        message = text;
        messageOn = true;
    }

    public void draw(Graphics2D g2){
        this.g2 = g2;
        g2.setFont(UIFont);
        g2.setColor(Color.white);

        // TITLE STATE
        if(gp.gameState == gp.titleState){
            drawTitleScreen();
        }

        // PLAY STATE
        if(gp.gameState == gp.playState){
            drawPlayerHealth();
        }

        // PAUSE STATE
        if(gp.gameState == gp.pauseState){
            drawPlayerHealth();
            drawPauseScreen();
        }

        // DIALOGUE STATE
        if(gp.gameState == gp.dialogueState){
            drawPlayerHealth();
            drawDialogueScreen();
        }
    }

    public void drawTitleScreen(){
        g2.setColor(new Color(42, 0, 122));
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        // title name
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 48F));
        String text = "Da Jokah Adventure";
        int x = getXForCenteredText(text);
        int y = gp.tileSize * 2;

        // shadow -- not used because it looks bad with font
//        g2.setColor(Color.black);
//        g2.drawString(text, x + 8, y + 8);

        // main color
        g2.setColor(Color.WHITE);
        g2.drawString(text, x, y);

        // character image
        int size = gp.tileSize * 3;
        x = (gp.screenWidth / 2) - (size / 2);
        y += gp.tileSize * 2;
        g2.drawImage(gp.player.down1, x, y, size,size, null);

        // menu
        g2.setFont(dialogueFont);
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 24F));
        text = "New Game";
        x = getXForCenteredText(text);
        y += gp.tileSize * 5;
        g2.drawString(text, x, y);
        if(commandNumber == 0){
            g2.drawString(">", x - gp.tileSize, y);
            // drawImage to use image for selector
        }

        g2.setFont(dialogueFont);
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 24F));
        text = "Load Game";
        x = getXForCenteredText(text);
        y += gp.tileSize;
        g2.drawString(text, x, y);
        if(commandNumber == 1){
            g2.drawString(">", x - gp.tileSize, y);
        }

        g2.setFont(dialogueFont);
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 24F));
        text = "Quit";
        x = getXForCenteredText(text);
        y += gp.tileSize;
        g2.drawString(text, x, y);
        if(commandNumber == 2){
            g2.drawString(">", x - gp.tileSize, y);
        }
    }

    public void drawPlayerHealth(){
        int x = gp.tileSize / 2;
        int y = gp.tileSize / 2;

        int fullHearts = gp.player.health > 0 ? gp.player.health / 2 : 0;
        int halfHearts = gp.player.health > 0 ? gp.player.health % 2 : 0;
        int emptyHearts = gp.player.health > 0 ? (gp.player.maxHealth - gp.player.health) / 2 : gp.player.maxHealth / 2;

        // draw full hearts
        for(int i = 0; i < fullHearts; i++){
            g2.drawImage(heart_full, x, y,null);
            x += gp.tileSize;
        }
        // draw half hearts
        for(int i = 0; i < halfHearts; i++){
            g2.drawImage(heart_half, x, y,null);
            x += gp.tileSize;
        }

        // draw empty hearts
        for(int i = 0; i < emptyHearts; i++){
            g2.drawImage(heart_empty, x, y,null);
            x += gp.tileSize;
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
