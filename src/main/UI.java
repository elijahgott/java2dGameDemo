package main;

import entity.Entity;
import object.OBJ_Heart;
import object.OBJ_ManaCrystal;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class UI {
    GamePanel gp;
    Graphics2D g2;
    Font UIFont, dialogueFont;
    BufferedImage heart_full, heart_half, heart_empty, manaCrystal_full, manaCrystal_empty;
    public boolean messageOn = false;
//    public String message = "";
//    int messageCounter = 0;
    ArrayList<String> message = new ArrayList<>();
    ArrayList<Integer> messageCounter = new ArrayList<>();
    public boolean gameOver = false;
    public String currentDialogue;

    public int commandNumber = 0;
    public int slotCol;
    public int slotRow;

    int optionsState = 0;

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

        Entity crystal = new OBJ_ManaCrystal(gp);
        manaCrystal_full = crystal.image;
        manaCrystal_empty = crystal.image2;
    }

    public void addMessage(String text){
        message.add(text);
        messageCounter.add(0);
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
            drawPlayerMana();

            drawMessage();

            if(gp.debug){
                drawDebug();
            }
        }

        // PAUSE STATE
        if(gp.gameState == gp.pauseState){
            drawPlayerHealth();
            drawPlayerMana();

            drawPauseScreen();
        }

        // DIALOGUE STATE
        if(gp.gameState == gp.dialogueState){
            drawPlayerHealth();
            drawPlayerMana();

            drawDialogueScreen();
        }

        // INVENTORY STATE
        if(gp.gameState == gp.inventoryState){
            drawPlayerHealth();
            drawPlayerMana();

            drawInventoryScreen();
        }

        // CHARACTER STATE
        if(gp.gameState == gp.characterState){
            drawPlayerHealth();
            drawPlayerMana();

            drawCharacterScreen();
        }

        // OPTIONS STATE
        if(gp.gameState == gp.optionsState){
            drawPlayerHealth();
            drawPlayerMana();

            drawOptionsScreen();
        }

        // GAME OVER STATE
        if(gp.gameState == gp.gameOverState){
            drawGameOver();
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
        int x = gp.tileSize / 3;
        int y = gp.tileSize / 3;

        int fullHearts = gp.player.health > 0 ? gp.player.health / 2 : 0;
        int halfHearts = gp.player.health > 0 ? gp.player.health % 2 : 0;
        int emptyHearts = gp.player.health > 0 ? (gp.player.maxHealth - gp.player.health) / 2 : gp.player.maxHealth / 2;

        // draw full hearts
        for(int i = 0; i < fullHearts; i++){
            g2.drawImage(heart_full, x, y,null);
            x += ((gp.tileSize * 3) / 4) + 2;
        }
        // draw half hearts
        for(int i = 0; i < halfHearts; i++){
            g2.drawImage(heart_half, x, y,null);
            x += ((gp.tileSize * 3) / 4) + 2;
        }

        // draw empty hearts
        for(int i = 0; i < emptyHearts; i++){
            g2.drawImage(heart_empty, x, y,null);
            x += ((gp.tileSize * 3) / 4) + 2;
        }
    }

    public void drawPlayerMana(){
        int manaSize = 40;

        int x = (gp.tileSize / 3) + 3;
        int y = (gp.tileSize / 3) + gp.tileSize;

        // draw max mana
        int i = 0;
        while(i < gp.player.maxMana){
            g2.drawImage(manaCrystal_empty, x, y, manaSize, manaSize, null);
            i++;
            x += ((gp.tileSize * 3) / 4) - 4;
        }

        // draw current mana
        x = (gp.tileSize / 3) + 3;
        y = (gp.tileSize / 3) + gp.tileSize;

        i = 0;
        while(i < gp.player.mana){
            g2.drawImage(manaCrystal_full, x, y, manaSize, manaSize, null);
            i++;
            x += ((gp.tileSize * 3) / 4) - 4;
        }
    }

    public void drawMessage(){
        int messageX = gp.tileSize / 2;
        int messageY = gp.tileSize * 4;
        g2.setFont(dialogueFont.deriveFont(Font.PLAIN, 12F));

        for(int i = 0; i < message.size(); i++){
            if(message.get(i) != null){
                g2.setColor(Color.BLACK);
                g2.drawString(message.get(i), messageX + 2, messageY + 2);

                g2.setColor(Color.WHITE);
                g2.drawString(message.get(i), messageX, messageY);

                int counter = messageCounter.get(i) + 1; // basically messageCounter++
                messageCounter.set(i, counter);
                messageY += 32;

                if(messageCounter.get(i) > 180){ // 3 seconds
                    message.remove(i);
                    messageCounter.remove(i);
                }
            }
        }
    }

    public void drawDebug(){
        // general debug
        g2.setFont(new Font("Arial", Font.PLAIN, 20));

        int x = gp.tileSize / 3;
        int y = gp.tileSize * 4;
        int lineHeight = 20;

        // player coordinates
        g2.setColor(Color.black);
        g2.drawString(gp.player.worldX + ", " + gp.player.worldY, x + 2, y + 2);
        g2.setColor(Color.white);
        g2.drawString(gp.player.worldX + ", " + gp.player.worldY, x, y);
        y += lineHeight;

        // player column coords
        g2.setColor(Color.black);
        g2.drawString("Col: " + (gp.player.worldX + gp.player.solidArea.x) / gp.tileSize, x + 2, y + 2);
        g2.setColor(Color.white);
        g2.drawString("Col: " + (gp.player.worldX + gp.player.solidArea.x) / gp.tileSize, x, y);
        y += lineHeight;

        // player row coords
        g2.setColor(Color.black);
        g2.drawString("Row: " + (gp.player.worldY + gp.player.solidArea.y) / gp.tileSize, x + 2, y + 2);
        g2.setColor(Color.white);
        g2.drawString("Row: " + (gp.player.worldY + gp.player.solidArea.y) / gp.tileSize, x, y);
        y += lineHeight;
    }

    public void drawPauseScreen(){
        // sub window
        final int frameX = gp.tileSize * 2;
        final int frameY = gp.tileSize * 2;
        final int frameWidth = gp.screenWidth - (gp.tileSize * 4);
        final int frameHeight = gp.screenHeight - (gp.tileSize * 4);

        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        // TEXT
        int textX;
        int textY;

        // TITLE
        String text = "PAUSED";
        g2.setFont(UIFont);
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 36F));
        textX = getXForCenteredText(text);
        textY = frameY + gp.tileSize + 8;
        g2.drawString(text, textX, textY);

        // RESUME GAME
        text = "RESUME";
        g2.setFont(dialogueFont.deriveFont(Font.PLAIN, 20F));
        textX = getXForCenteredText(text);
        textY += gp.tileSize * 2;
        g2.drawString(text, textX, textY);
        if(commandNumber == 0){
            g2.drawString(">", textX - 24, textY);
        }

        // OPTIONS
        text = "OPTIONS";
        g2.setFont(dialogueFont.deriveFont(Font.PLAIN, 20F));
        textX = getXForCenteredText(text);
        textY += gp.tileSize + 24;
        g2.drawString(text, textX, textY);
        if(commandNumber == 1){
            g2.drawString(">", textX - 24, textY);
        }

        // QUIT
        text = "QUIT GAME";
        g2.setFont(dialogueFont.deriveFont(Font.PLAIN, 20F));
        textX = getXForCenteredText(text);
        textY += gp.tileSize + 24;
        g2.drawString("QUIT GAME", textX, textY);
        if(commandNumber == 2){
            g2.drawString(">", textX - 24, textY);
        }
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

    public void drawInventoryScreen(){
        // CREATE A FRAME
        final int frameX = gp.tileSize * 2;
        final int frameY = gp.tileSize * 2;
        final int frameWidth = gp.screenWidth - (gp.tileSize * 4);
        final int frameHeight = gp.screenHeight - (gp.tileSize * 4);

        final int slotSize = frameWidth / 9;

        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        // SLOT
        final int slotXStart = frameX + slotSize;
        final int slotYStart = frameY + 20;
        int slotX = slotXStart;
        int slotY = slotYStart;

        // DRAW INVENTORY ITEMS
        for(int i = 0; i < gp.player.inventory.size(); i++){
            if(gp.player.inventory.get(i) == gp.player.currentWeapon ||
                gp.player.inventory.get(i) == gp.player.currentShield){
                g2.setColor(Color.ORANGE);
                g2.drawRect(slotX + 4, slotY + 4, slotSize - 10, slotSize - 10);
            }
            g2.drawImage(gp.player.inventory.get(i).down1, slotX + ((slotSize - gp.tileSize) / 2), slotY + ((slotSize - gp.tileSize) / 2),null);
            slotX += slotSize;
            // next row of items
            if(i == 6 || i == 13 || i == 20 || i == 27){
                slotX = slotXStart;
                slotY += slotSize;
            }
        }

        // CURSOR
        int cursorX = slotXStart + (slotSize * slotCol);
        int cursorY = slotYStart + (slotSize * slotRow);
        int cursorWidth = slotSize - 2;
        int cursorHeight = slotSize - 2;

        // DRAW CURSOR
        g2.setColor(Color.WHITE);
        g2.setStroke(new BasicStroke(4));
        g2.drawRect(cursorX, cursorY, cursorWidth, cursorHeight);

        // DESCRIPTION
        int dFrameX = frameX + 16;
        int dFrameY = frameY + (frameHeight - gp.tileSize * 2); // under inventory
        int dFrameWidth = frameWidth - 32;
        int dFrameHeight = gp.tileSize + 32;

        g2.setColor(Color.WHITE);
        g2.drawRect(dFrameX, dFrameY, dFrameWidth, dFrameHeight);

        // DESCRIPTION TEXT
        int textX = dFrameX + 20;
        int textY = dFrameY + 20;
        g2.setFont(dialogueFont.deriveFont(Font.PLAIN, 16F));

        int itemIndex = getItemIndex();

        if(itemIndex < gp.player.inventory.size()){
            for(String line: gp.player.inventory.get(itemIndex).description.split("\n")){
                g2.drawString(line, textX, textY + 12);
                textY += 24;
            }
        }

    }

    public void drawCharacterScreen(){
        // CREATE A FRAME
        final int frameX = gp.tileSize * 2;
        final int frameY = gp.tileSize * 2;
        final int frameWidth = gp.screenWidth - (gp.tileSize * 4);
        final int frameHeight = gp.screenHeight - (gp.tileSize * 4);

        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        // TEXT
        g2.setColor(Color.WHITE);
        g2.setFont(dialogueFont.deriveFont(Font.PLAIN, 20F));

        int textX = frameX + 32;
        int textY = frameY + 40;
        final int lineHeight = 32;

        // NAMES
        g2.drawString("LVL", textX, textY);
        textY += lineHeight;

        g2.drawString("HLTH", textX, textY);
        textY += lineHeight;

        g2.drawString("MANA", textX, textY);
        textY += lineHeight;

        g2.drawString("AMMO", textX, textY);
        textY += lineHeight;

        g2.drawString("STR", textX, textY);
        textY += lineHeight;

        g2.drawString("DXT", textX, textY);
        textY += lineHeight;

        g2.drawString("ATK", textX, textY);
        textY += lineHeight;

        g2.drawString("DEF", textX, textY);
        textY += lineHeight;

        g2.drawString("XP", textX, textY);
        textY += lineHeight;

        g2.drawString("NXT LVL", textX, textY);
        textY += lineHeight;

        g2.drawString("$", textX, textY);
        textY += lineHeight;

//        g2.drawString("WPN", textX, textY);
//        textY += lineHeight;
//
//        g2.drawString("SHLD", textX, textY);

        // VALUES
        int tailX = (frameX + frameWidth) - 32;
        // reset textY
        textY = frameY + 40;
        String value;

        value = String.valueOf(gp.player.level);
        textX = getXForRightAlignedText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.health + "/" +  gp.player.maxHealth);
        textX = getXForRightAlignedText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.mana + "/" +  gp.player.maxMana);
        textX = getXForRightAlignedText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.ammo);
        textX = getXForRightAlignedText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.strength);
        textX = getXForRightAlignedText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.dexterity);
        textX = getXForRightAlignedText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.attack);
        textX = getXForRightAlignedText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.defense);
        textX = getXForRightAlignedText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.exp);
        textX = getXForRightAlignedText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.nextLevelExp);
        textX = getXForRightAlignedText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.coin);
        textX = getXForRightAlignedText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

//        value = String.valueOf(gp.player.currentWeapon.name);
//        textX = getXForRightAlignedText(value, tailX);
//        g2.drawString(value, textX, textY);
//        textY += lineHeight;
//
//        value = String.valueOf(gp.player.currentShield.name);
//        textX = getXForRightAlignedText(value, tailX);
//        g2.drawString(value, textX, textY);

//        textY += 8;

        // draw current weapon
//        g2.drawImage(gp.player.currentWeapon.down1, tailX - gp.tileSize, textY, null);
//        textY += gp.tileSize;

        // draw current shield
//        g2.drawImage(gp.player.currentShield.down1, tailX - gp.tileSize, textY, null);
    }

    public void drawOptionsScreen(){
        // sub window
        final int frameX = gp.tileSize * 2;
        final int frameY = gp.tileSize * 2;
        final int frameWidth = gp.screenWidth - (gp.tileSize * 4);
        final int frameHeight = gp.screenHeight - (gp.tileSize * 4);

        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        // text
        g2.setColor(Color.WHITE);

        switch(optionsState){
            case 0:
                optionsTop(frameX, frameY, frameWidth, frameHeight);
                break;
            case 1:
                optionsFullScreenNotification(frameX, frameY);
                break;
            case 2:
                optionsControl(frameX, frameY, frameWidth, frameHeight);
                break;
            case 3:
                optionsQuitGameConfirmation(frameX, frameY);
                break;
        }

        gp.keyHandler.enterPressed = false;

    }

    public void optionsTop(int frameX, int frameY, int frameWidth, int frameHeight){
        int textX;
        int textY;

        // TITLE
        g2.setFont(UIFont.deriveFont(Font.PLAIN, 36F));
        String text = "OPTIONS";
        textX = getXForCenteredText(text);
        textY = frameY + gp.tileSize + 8;
        g2.drawString(text, textX, textY);

        g2.setFont(dialogueFont.deriveFont(Font.PLAIN, 20F));

        // LEFT SIDE:

        // FULL SCREEN ON/OFF
        textX = frameX + gp.tileSize;
        textY += gp.tileSize + 24;
        g2.drawString("FULL SCREEN", textX, textY);
        if(commandNumber == 0){
            g2.drawString(">", textX - 24, textY);
            if(gp.keyHandler.enterPressed){
                gp.fullScreenOn = !gp.fullScreenOn;
                optionsState = 1;
            }
        }

        // VOLUME
        textY += gp.tileSize;
        g2.drawString("VOLUME", textX, textY);

        // MUSIC
        textY += gp.tileSize;
        g2.drawString("MUSIC", textX + gp.tileSize, textY);
        if(commandNumber == 1){
            g2.drawString(">", textX + 24, textY);
        }

        // SOUND EFFECTS
        textY += gp.tileSize;
        g2.drawString("SOUND EFFECTS", textX + gp.tileSize, textY);
        if(commandNumber == 2){
            g2.drawString(">", textX + 24, textY);
        }

        // CONTROLS
        textY += gp.tileSize;
        g2.drawString("CONTROLS", textX, textY);
        if(commandNumber == 3){
            g2.drawString(">", textX - 24, textY);
            if(gp.keyHandler.enterPressed){
                optionsState = 2;
                commandNumber = 0;
            }
        }

        // BACK
        text = "BACK";
        textX = getXForCenteredText(text);
        textY += gp.tileSize;
        g2.drawString(text, textX, textY);
        if(commandNumber == 4){
            g2.drawString(">", textX - 24, textY);
        }

        // RIGHT SIDE:

        // FULL SCREEN CHECK BOX
        textX = (frameX + frameWidth) - gp.tileSize;
        textY = (frameY + (gp.tileSize * 2)) + 8;
        g2.drawRect(textX, textY, gp.tileSize / 2, gp.tileSize / 2);
        if(gp.fullScreenOn){
            g2.fillRect(textX, textY, gp.tileSize / 2, gp.tileSize / 2);
        }

        // MUSIC VOLUME
        textX -= (gp.tileSize * 5) - (gp.tileSize / 2);
        textY += gp.tileSize * 2;
        g2.drawRect(textX, textY, gp.tileSize * 5, gp.tileSize / 2);
        int volumeWidth = gp.tileSize * gp.music.volumeScale;
        g2.fillRect(textX, textY, volumeWidth, gp.tileSize / 2);

        // SOUND EFFECT VOLUME
        textY += gp.tileSize;
        g2.drawRect(textX, textY, gp.tileSize * 5, gp.tileSize / 2);
        volumeWidth = gp.tileSize * gp.soundEffect.volumeScale;
        g2.fillRect(textX, textY, volumeWidth, gp.tileSize / 2);

        gp.config.saveConfig();
    }

    public void optionsFullScreenNotification(int frameX, int frameY){
        int textX = frameX + gp.tileSize;
        int textY = frameY + gp.tileSize;

        currentDialogue = "The change will take\neffect after restarting\nthe game.";
        g2.setFont(dialogueFont.deriveFont(Font.PLAIN, 20F));
        for(String line: currentDialogue.split("\n")){
            g2.drawString(line, textX, textY);
            textY += gp.tileSize;
        }

        // BACK
        textY += gp.tileSize * 2;
        g2.drawString("BACK", textX, textY);
        if(commandNumber == 0){
            g2.drawString(">", textX - 24, textY);
            if(gp.keyHandler.enterPressed){
                optionsState = 0;
            }
        }

        // RESTART
        textY += gp.tileSize;
        g2.drawString("RESTART", textX, textY);
        if(commandNumber == 1){
            g2.drawString(">", textX - 24, textY);
            if(gp.keyHandler.enterPressed){
                // RESTART GAME
            }
        }
    }

    public void optionsControl(int frameX, int frameY, int frameWidth, int frameHeight){
        int textX;
        int textY;

        // TITLE
        g2.setFont(UIFont.deriveFont(Font.PLAIN, 36F));
        String text = "CONTROLS";
        textX = getXForCenteredText(text);
        textY = frameY + gp.tileSize + 8;
        g2.drawString(text, textX, textY);

        g2.setFont(dialogueFont.deriveFont(Font.PLAIN, 20F));

        textX = frameX + gp.tileSize;
        textY += gp.tileSize;

        // LEFT SIDE
        g2.drawString("MOVE", textX, textY);
        textY += gp.tileSize;

        g2.drawString("CONFIRM / ATTACK", textX, textY);
        textY += gp.tileSize;

        g2.drawString("SHOOT / THROW", textX, textY);
        textY += gp.tileSize;

        g2.drawString("INVENTORY SCREEN", textX, textY);
        textY += gp.tileSize;

        g2.drawString("CHARACTER SCREEN", textX, textY);
        textY += gp.tileSize;

        g2.drawString("PAUSE", textX, textY);
        textY += gp.tileSize;

        text = "WASD / ↑←↓→";
        int tailX = (frameX + frameWidth) - 32;
        textX = getXForRightAlignedText(text, tailX);
        textY = frameY + (gp.tileSize * 2) + 8;
        g2.drawString(text, textX, textY);
        textY += gp.tileSize;

        text = "SPACE / ENTER";
        textX = getXForRightAlignedText(text, tailX);
        g2.drawString(text, textX, textY);
        textY += gp.tileSize;

        text = "F";
        textX = getXForRightAlignedText(text, tailX);
        g2.drawString(text, textX, textY);
        textY += gp.tileSize;

        text = "E";
        textX = getXForRightAlignedText(text, tailX);
        g2.drawString(text, textX, textY);
        textY += gp.tileSize;

        text = "C";
        textX = getXForRightAlignedText(text, tailX);
        g2.drawString(text, textX, textY);
        textY += gp.tileSize;

        text = "ESC";
        textX = getXForRightAlignedText(text, tailX);
        g2.drawString(text, textX, textY);
        textY += gp.tileSize;

        // BACK
        text = "BACK";
        textX = getXForCenteredText(text);
        g2.drawString(text, textX, textY);
        g2.drawString(">", textX - 24, textY);
        commandNumber = 0;
        if(gp.keyHandler.enterPressed){
            optionsState = 0;
            commandNumber = 3;
        }
    }

    public void optionsQuitGameConfirmation(int frameX, int frameY){
        int textX = frameX + gp.tileSize;
        int textY = frameY + gp.tileSize * 2;

        currentDialogue = "Quit game and return\nto title screen?";
        g2.setFont(dialogueFont.deriveFont(Font.PLAIN, 20F));

        for(String line: currentDialogue.split("\n")){
            g2.drawString(line, textX, textY);
            textY += gp.tileSize;
        }

        // YES
        String text = "YES";
        textX = getXForCenteredText(text);
        textY += gp.tileSize * 2;
        g2.drawString(text, textX, textY);
        if(commandNumber == 0){
            g2.drawString(">", textX - 24, textY);
            if(gp.keyHandler.enterPressed){
                // save game ???
                optionsState = 0;
                gp.gameState = gp.titleState;
            }
        }

        // NO
        text = "NO";
        textX = getXForCenteredText(text);
        textY += gp.tileSize;
        g2.drawString(text, textX, textY);
        if(commandNumber == 1){
            g2.drawString(">", textX - 24, textY);
            if(gp.keyHandler.enterPressed){
                optionsState = 0;
                gp.gameState = gp.pauseState;
                commandNumber = 2;
            }
        }
    }

    public void drawGameOver(){
        // background
        g2.setColor(new Color(0, 0, 0, 200));
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        // text
        int x;
        int y;
        String text;
        g2.setColor(Color.WHITE);
        g2.setFont(UIFont.deriveFont(Font.PLAIN, 64F));


        // game over
        text = "GAME OVER";
        x = getXForCenteredText(text);
        y = gp.tileSize * 3;
        g2.drawString(text, x, y);

        g2.setFont(dialogueFont.deriveFont(Font.PLAIN, 20F));

        // retry
        text = "RETRY";
        x = getXForCenteredText(text);
        y += gp.tileSize * 6;
        g2.drawString(text, x, y);
        if(commandNumber == 0){
            g2.drawString(">", x - 24, y);
        }

        // title screen
        text = "QUIT";
        x = getXForCenteredText(text);
        y +=  gp.tileSize;
        g2.drawString(text, x, y);
        if(commandNumber == 1){
            g2.drawString(">", x - 24, y);
        }
    }

    public int getItemIndex(){
        return slotCol + (slotRow * 5);
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

    public int getXForRightAlignedText(String text, int tailX){
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        return tailX - length;
    }
}
