package main;

import java.awt.Font;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

import object.OBJ_Key;

public class UI {
    GamePanel gp;
    Font UIFont, gameOverFont;
    BufferedImage keyImage;
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
        OBJ_Key key = new OBJ_Key();
        keyImage = key.image;
    }

    public void showMessage(String text){
        message = text;
        messageOn = true;
    }

    public void draw(Graphics2D g2){
        if(gameOver){
            String text;
            int textLength;
            int x;
            int y;

            // playtime text
            g2.setFont(UIFont);
            g2.setColor(Color.white);
            text = "Total Playtime: " + dFormat.format(playTime);
            textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
            x = (gp.screenWidth / 2) - (textLength / 2);
            y = (gp.screenHeight / 2) - (gp.tileSize * 5);
            g2.drawString(text, x, y);

            // congrats text
            g2.setFont(gameOverFont);
            g2.setColor(Color.orange);
            text = "Congratulations!";
            textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
            x = (gp.screenWidth / 2) - (textLength / 2);
            y = (gp.screenHeight / 2) - (gp.tileSize * 3);
            g2.drawString(text, x, y);


            // treasure text
            g2.setFont(UIFont);
            g2.setColor(Color.white);
            text = "You found the treasure!";
            textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
            x = (gp.screenWidth / 2) - (textLength / 2);
            y = (gp.screenHeight / 2) - (gp.tileSize * 2);
            g2.drawString(text, x, y);

            gp.gameThread = null; // stops game
        }
        else{
            g2.setFont(UIFont);
            g2.setColor(Color.white);
            // display number of keys
            g2.drawImage(keyImage, gp.tileSize / 2, gp.tileSize / 2, gp.tileSize, gp.tileSize, null);
            g2.drawString("x " + gp.player.hasKey, 74, (gp.tileSize / 3) + gp.tileSize);

            // PLAYTIME
            playTime += (double)1/60;
            g2.drawString("Playtime: " + dFormat.format(playTime), (gp.tileSize * 10) + (gp.tileSize / 3), (gp.tileSize / 3) + gp.tileSize);

            // MESSAGE
            if(messageOn){
                g2.setFont(g2.getFont().deriveFont(24F));
                g2.drawString(message, gp.tileSize / 2, gp.tileSize * 5);
                messageCounter++;

                if(messageCounter == 120){ // stops after 120 frames ~ 2 secs
                    messageOn = false;
                    messageCounter = 0;
                }
            }
        }
    }
}
