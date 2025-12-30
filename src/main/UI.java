package main;

import java.awt.Font;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

import object.OBJ_Key;

public class UI {
    GamePanel gp;
    Graphics2D g2;
    Font UIFont, gameOverFont;
    public boolean messageOn = false;
    public String message = "";
    int messageCounter = 0;
    public boolean gameOver = false;

    double playTime;
    DecimalFormat dFormat = new DecimalFormat("#0.00");

    public UI(GamePanel gp) {
        this.gp = gp;

        UIFont = new Font("Arial", Font.BOLD, 36);
        gameOverFont = new Font("Arial", Font.BOLD, 56);
    }

    public void showMessage(String text){
        message = text;
        messageOn = true;
    }

    public void draw(Graphics2D g2){
        this.g2 = g2;
        g2.setFont(UIFont);
        g2.setColor(Color.white);

        if(gp.gameState == gp.playState){
            // do playState stuff later
        }
        if(gp.gameState == gp.pauseState){
            drawPauseScreen();
        }
    }

    public void drawPauseScreen(){
        String text = "PAUSED";

        int x = getXForCenteredText(text);
        int y = gp.screenHeight / 2;

        g2.drawString(text, x, y);
    }

    public int getXForCenteredText(String text){
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        return (gp.screenWidth / 2) - (length / 2);
    }
}
