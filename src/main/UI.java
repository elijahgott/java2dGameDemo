package main;

import entity.Entity;
import object.OBJ_Coin;
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
    public Font UIFont, dialogueFont;
    BufferedImage heart_full, heart_half, heart_empty, manaCrystal_full, manaCrystal_empty, coin;
    public boolean messageOn = false;
//    public String message = "";
//    int messageCounter = 0;
    ArrayList<String> message = new ArrayList<>();
    ArrayList<Integer> messageCounter = new ArrayList<>();
    public boolean gameOver = false;
    public String currentDialogue;

    public int commandNumber = 0;
    public int playerSlotCol;
    public int playerSlotRow;
    public int npcSlotCol;
    public int npcSlotRow;

    int subState = 0;

    int counter = 0;

    public Entity npc;

    int charIndex = 0;
    String combinedText = "";

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

        Entity coinObj = new OBJ_Coin(gp);
        coin = coinObj.down1;
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
            drawMonsterHealth();

            drawPlayerHealth();
            drawPlayerMana();

            drawMessage();

            if(gp.debug){
                drawDebug();
            }
        }

        // PAUSE STATE
        if(gp.gameState == gp.pauseState){
            drawMonsterHealth();

            drawPlayerHealth();
            drawPlayerMana();

            drawPauseScreen();
        }

        // DIALOGUE STATE
        if(gp.gameState == gp.dialogueState){
            drawMonsterHealth();

            drawPlayerHealth();
            drawPlayerMana();

            drawDialogueScreen();
        }

        // INVENTORY STATE
        if(gp.gameState == gp.inventoryState){
            drawMonsterHealth();

            drawPlayerHealth();
            drawPlayerMana();

            int x = gp.tileSize * 3;
            int y = gp.tileSize * 2;
            int width = gp.screenWidth - (gp.tileSize * 6);
            int height = gp.screenHeight - (gp.tileSize * 4);
            drawInventory(gp.player, true, x, y, width, height);
        }

        // CHARACTER STATE
        if(gp.gameState == gp.characterState){
            drawMonsterHealth();

            drawPlayerHealth();
            drawPlayerMana();

            drawCharacterScreen();
        }

        // OPTIONS STATE
        if(gp.gameState == gp.optionsState){
            drawMonsterHealth();

            drawPlayerHealth();
            drawPlayerMana();

            drawOptionsScreen();
        }

        // GAME OVER STATE
        if(gp.gameState == gp.gameOverState){
            drawGameOverScreen();
        }

        // TRANSITION STATE
        if(gp.gameState == gp.transitionState){
            drawTransition();
        }

        // TRADE STATE
        if(gp.gameState == gp.tradeState){
            drawTradeScreen();
        }

        // SLEEP STATE
        if(gp.gameState == gp.sleepState){
            drawSleepScreen();
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

        g2.setColor(Color.BLACK);
        g2.drawString(text, x + 2, y + 2);
        g2.setColor(Color.WHITE);
        g2.drawString(text, x, y);
        if(commandNumber == 0){
            g2.setColor(Color.BLACK);
            g2.drawString(">", x - gp.tileSize + 2, y + 2);
            g2.setColor(Color.WHITE);
            g2.drawString(">", x - gp.tileSize, y);
            // drawImage to use image for selector
        }

        g2.setFont(dialogueFont);
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 24F));
        text = "Load Game";
        x = getXForCenteredText(text);
        y += gp.tileSize;

        g2.setColor(Color.BLACK);
        g2.drawString(text, x + 2, y + 2);
        g2.setColor(Color.WHITE);

        g2.drawString(text, x, y);
        if(commandNumber == 1){
            g2.setColor(Color.BLACK);
            g2.drawString(">", x - gp.tileSize + 2, y + 2);
            g2.setColor(Color.WHITE);
            g2.drawString(">", x - gp.tileSize, y);
        }

        g2.setFont(dialogueFont);
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 24F));
        text = "Quit";
        x = getXForCenteredText(text);
        y += gp.tileSize;

        g2.setColor(Color.BLACK);
        g2.drawString(text, x + 2, y + 2);
        g2.setColor(Color.WHITE);
        g2.drawString(text, x, y);
        if(commandNumber == 2){
            g2.setColor(Color.BLACK);
            g2.drawString(">", x - gp.tileSize + 2, y + 2);
            g2.setColor(Color.WHITE);
            g2.drawString(">", x - gp.tileSize, y);
        }
    }

    public void drawPlayerHealth(){
        int iconSize = 32;
        int x = gp.tileSize / 3;
        int y = gp.tileSize / 3;

        int fullHearts = gp.player.health > 0 ? gp.player.health / 2 : 0;
        int halfHearts = gp.player.health > 0 ? gp.player.health % 2 : 0;
        int emptyHearts = gp.player.health > 0 ? (gp.player.maxHealth - gp.player.health) / 2 : gp.player.maxHealth / 2;

        // draw full hearts
        for(int i = 0; i < fullHearts; i++){
            g2.drawImage(heart_full, x, y, iconSize, iconSize, null);
            x += iconSize - 4;
        }
        // draw half hearts
        for(int i = 0; i < halfHearts; i++){
            g2.drawImage(heart_half, x, y, iconSize, iconSize, null);
            x += iconSize - 4;
        }

        // draw empty hearts
        for(int i = 0; i < emptyHearts; i++){
            g2.drawImage(heart_empty, x, y, iconSize, iconSize, null);
            x += iconSize - 4;
        }
    }

    public void drawPlayerMana(){
        int manaSize = 28;

        int x = (gp.tileSize / 3);
        int y = (gp.tileSize / 3) + 32;

        // draw max mana
        int i = 0;
        while(i < gp.player.maxMana){
            g2.drawImage(manaCrystal_empty, x, y, manaSize, manaSize, null);
            i++;
            x += manaSize - 4;
        }

        // draw current mana
        x = (gp.tileSize / 3);
        y = (gp.tileSize / 3) + 32;

        i = 0;
        while(i < gp.player.mana){
            g2.drawImage(manaCrystal_full, x, y, manaSize, manaSize, null);
            i++;
            x += manaSize - 4;
        }
    }

    public void drawMonsterHealth(){
        for(int i = 0; i < gp.monster[1].length; i++){
            Entity monster = gp.monster[gp.currentMap][i];
            if(monster != null && monster.inCamera()){
                // monster hp bar
                if(!monster.boss && monster.displayHealthBar){
                    int screenX = monster.getScreenX();
                    int screenY = monster.getScreenY();

                    double oneHealthScale = (double)gp.tileSize / monster.maxHealth;
                    double healthBarValue = oneHealthScale * monster.health;

                    // render health bar
                    int healthBarHeight = 10;
                    g2.setColor(new Color(0, 0, 0));
                    g2.fillRect(screenX - 2, screenY - 10, gp.tileSize + 4, healthBarHeight + 4);
                    g2.setColor(new Color(255, 0, 30));
                    g2.fillRect(screenX, screenY - 8, (int)healthBarValue, healthBarHeight);

                    // health bar disappears after 5 seconds of not hitting monster
                    monster.healthBarCounter++;

                    if(monster.healthBarCounter > 300){
                        monster.displayHealthBar = false;
                        monster.healthBarCounter = 0;
                    }
                }
                else if(gp.bossBattleOn && monster.boss){ // BOSS
                    int healthBarHeight = 24;
                    int healthBarWidth = gp.tileSize * 8;
                    int borderThickness = 4;

                    double oneHealthScale = (double)healthBarWidth / monster.maxHealth;
                    double healthBarValue = oneHealthScale * monster.health;

                    int x = (gp.screenWidth / 2) - (gp.tileSize * 4);
                    int y = gp.tileSize;

                    // render health bar
                    g2.setColor(Color.BLACK);
                    g2.fillRect(x - borderThickness, y - borderThickness, healthBarWidth + borderThickness, healthBarHeight + borderThickness);
                    g2.setColor(new Color(255, 0, 30));
                    g2.fillRect(x, y, (int)healthBarValue - borderThickness, healthBarHeight - borderThickness);

                    g2.setFont(UIFont.deriveFont(Font.PLAIN, 24F));
                    g2.setColor(Color.WHITE);
                    String text = monster.name;
                    g2.drawString(text, x, y - borderThickness);
                }
            }
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

        // player godmode status
        if(gp.keyHandler.godModeOn){
            g2.setColor(Color.black);
            g2.drawString("Godmode: ON",x + 2, y + 2);
            g2.setColor(Color.white);
            g2.drawString("Godmode: ON",x, y);
            y += lineHeight;
        }
        else{
            g2.setColor(Color.black);
            g2.drawString("Godmode: OFF",x + 2, y + 2);
            g2.setColor(Color.white);
            g2.drawString("Godmode: OFF",x, y);
            y += lineHeight;
        }

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
        int x = gp.tileSize * 2;
        int y = gp.tileSize / 2;
        int width = gp.screenWidth - (gp.tileSize * 4);
        int height = gp.tileSize * 4;

        drawSubWindow(x, y, width, height);

        x += gp.tileSize / 2;
        y += gp.tileSize;

        g2.setFont(dialogueFont);
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 20F));

        if(npc.dialogues[npc.dialogueSet][npc.dialogueIndex] != null){
//            currentDialogue = npc.dialogues[npc.dialogueSet][npc.dialogueIndex];

            char characters[] = npc.dialogues[npc.dialogueSet][npc.dialogueIndex].toCharArray();

            if(charIndex < characters.length){
                gp.playSoundEffect(17); // speak sound effect
                String s = String.valueOf(characters[charIndex]);
                combinedText = combinedText + s;
                currentDialogue = combinedText;

                charIndex++;
            }

            if(gp.keyHandler.enterPressed || gp.keyHandler.spacePressed){
                charIndex = 0;
                combinedText = "";

                if(gp.gameState == gp.dialogueState || gp.gameState == gp.cutsceneState){
                    npc.dialogueIndex++;
                    gp.keyHandler.enterPressed = false;
                    gp.keyHandler.spacePressed = false;
                }
            }
        }
        else{ // no text in array
            npc.dialogueIndex = 0;

            if(gp.gameState == gp.dialogueState){
                gp.gameState = gp.playState;
            }
            if(gp.gameState == gp.cutsceneState){
                gp.cutsceneManager.scenePhase++;
            }
        }

        for(String line : currentDialogue.split("\n")){
            g2.drawString(line, x, y);
            y += 36;
        }
    }

    public void drawInventory(Entity entity, boolean cursor, int x, int y, int width, int height){
        int slotCol;
        int slotRow;

        if(entity == gp.player){
            slotCol = playerSlotCol;
            slotRow = playerSlotRow;
        }
        else{
            slotCol = npcSlotCol;
            slotRow = npcSlotRow;
        }

        final int slotSize = width / 8;

        drawSubWindow(x, y, width, height);

        // SLOT
        final int slotXStart = x + ((width - slotSize * 7) / 2);
        final int slotYStart = y + 20;
        int slotX = slotXStart;
        int slotY = slotYStart;

        // DRAW INVENTORY ITEMS
        for(int i = 0; i < entity.inventory.size(); i++){
            if(entity.inventory.get(i) == entity.currentWeapon ||
                    entity.inventory.get(i) == entity.currentShield ||
                    entity.inventory.get(i) == entity.currentLight){
                g2.setColor(Color.ORANGE);
                g2.drawRect(slotX + 4, slotY + 4, slotSize - 10, slotSize - 10);
            }
            g2.drawImage(entity.inventory.get(i).down1, slotX + ((slotSize - gp.tileSize) / 2), slotY + ((slotSize - gp.tileSize) / 2),null);

            // DISPLAY AMOUNT OF ITEMS
            if((entity == gp.player) && (entity.inventory.get(i).amount > 1)){
                String text = "" + entity.inventory.get(i).amount;
                int amountX = getXForRightAlignedText(text, slotX + (int)(slotSize * .7));
                int amountY = slotY + (int)(slotSize * .9);

                g2.setFont(dialogueFont.deriveFont(Font.PLAIN, 16F));
                g2.setColor(Color.BLACK);
                g2.drawString(text, amountX, amountY);

                g2.setColor(Color.WHITE);
                g2.drawString(text, amountX - 2, amountY - 2);
            }

            slotX += slotSize;
            // next row of items
            if(i == 6 || i == 13 || i == 20 || i == 27){
                slotX = slotXStart;
                slotY += slotSize;
            }
        }

        // CURSOR
        if(cursor){
            int cursorX = slotXStart + (slotSize * slotCol);
            int cursorY = slotYStart + (slotSize * slotRow);
            int cursorWidth = slotSize - 2;
            int cursorHeight = slotSize - 2;

            // DRAW CURSOR
            g2.setColor(Color.WHITE);
            g2.setStroke(new BasicStroke(4));
            g2.drawRect(cursorX, cursorY, cursorWidth, cursorHeight);

            // DESCRIPTION
            int dFrameX = x + 16;
            int dFrameY = y + (height - gp.tileSize * 2); // under inventory
            int dFrameWidth = width - 32;
            int dFrameHeight = gp.tileSize + 32;

            g2.setColor(Color.WHITE);
            g2.drawRect(dFrameX, dFrameY, dFrameWidth, dFrameHeight);

            // DESCRIPTION TEXT
            int textX = dFrameX + 20;
            int textY = dFrameY + 20;
            g2.setFont(dialogueFont.deriveFont(Font.PLAIN, 16F));

            int itemIndex = getItemIndex(slotCol, slotRow);

            if(itemIndex < entity.inventory.size()){
                for(String line: entity.inventory.get(itemIndex).description.split("\n")){
                    g2.drawString(line, textX, textY + 12);
                    textY += 24;
                }
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

        switch(subState){
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
                subState = 1;
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
                subState = 2;
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
                subState = 0;
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
            subState = 0;
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
                subState = 0;
                gp.stopMusic();
                gp.gameState = gp.titleState;
                gp.resetGame(true);
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
                subState = 0;
                gp.gameState = gp.pauseState;
                commandNumber = 2;
            }
        }
    }

    public void drawGameOverScreen(){
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
        text = "TITLE SCREEN";
        x = getXForCenteredText(text);
        y +=  gp.tileSize;
        g2.drawString(text, x, y);
        if(commandNumber == 1){
            g2.drawString(">", x - 24, y);
        }
    }

    public void drawTransition(){
        // slowly darken screen
        counter++;
        g2.setColor(new Color(0, 0, 0, counter * 5));
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        if(counter >= 51){
            counter = 0;

            gp.gameState = gp.playState;
            gp.currentMap = gp.eventHandler.tempMap;
            gp.player.worldX = gp.tileSize * gp.eventHandler.tempCol;
            gp.player.worldY = gp.tileSize * gp.eventHandler.tempRow;

            gp.eventHandler.previousEventX = gp.player.worldX;
            gp.eventHandler.previousEventY = gp.player.worldY;

            gp.changeArea();
        }
    }

    public void drawTradeScreen(){
        switch(subState){
            case 0:
                trade_select();
                break;
            case 1:
                trade_buy();
                break;
            case 2:
                trade_sell();
                break;
        }

        gp.keyHandler.enterPressed = false;
    }

    public void trade_select(){
        npc.dialogueSet = 0;
        drawDialogueScreen();

        // draw window
        int x = gp.tileSize * 12;
        int y = (int)(gp.tileSize * 4.5);
        int width = gp.tileSize * 6;
        int height = (int)(gp.tileSize * 3.5);
        drawSubWindow(x, y, width, height);

        // draw texts
        x += gp.tileSize;
        y += (int)(gp.tileSize * .90);
        g2.drawString("Buy", x, y);
        if(commandNumber == 0){
            g2.drawString(">", x - 24, y);
            if(gp.keyHandler.enterPressed){
                subState = 1;
            }
        }

        y += gp.tileSize;
        g2.drawString("Sell", x, y);
        if(commandNumber == 1){
            g2.drawString(">", x - 24, y);
            if(gp.keyHandler.enterPressed){
                subState = 2;
            }
        }

        y +=  gp.tileSize;
        g2.drawString("Cancel", x, y);
        if(commandNumber == 2){
            g2.drawString(">", x - 24, y);
            if(gp.keyHandler.enterPressed){
                commandNumber = 0;
                npc.startDialogue(npc, 1);
            }
        }
    }

    public void trade_buy(){
        // draw player inventory
        int x = gp.tileSize;
        int y = gp.tileSize * 2;
        int width = gp.tileSize * 8;
        int height = gp.tileSize * 6;
        drawInventory(gp.player, false, x, y, width, height);

        // draw coin window
        y += height;
        height = gp.tileSize * 2;
        drawSubWindow(x, y, width, height);
        x += gp.tileSize * 2;
        y += (gp.tileSize / 4);
        int size = (int)(gp.tileSize * 1.5);
        g2.drawImage(coin, x, y, size, size, null);

        x += (int)(gp.tileSize * 1.5);
        y += gp.tileSize;
        g2.setFont(dialogueFont.deriveFont(Font.PLAIN, 32F));
        g2.drawString(Integer.toString(gp.player.coin), x, y);

        // draw nav hint
        String text = "[ESC] Back";
        x = getXForRightAlignedText(text, gp.screenWidth);
        y += (int)(gp.tileSize * 1.5);
        g2.setFont(dialogueFont.deriveFont(Font.PLAIN, 20F));
        g2.setColor(Color.black);
        g2.drawString(text, x, y);
        g2.setColor(Color.white);
        g2.drawString(text, x - 2, y - 2);

        // draw npc inventory
        x = gp.screenWidth - (gp.tileSize + width);
        y = gp.tileSize * 2;
        height = gp.tileSize * 6;
        drawInventory(npc, true, x, y, width, height);

        // draw price window
        y += height;
        height = gp.tileSize * 2;
        drawSubWindow(x, y, width, height);
        x += (int)(gp.tileSize * 1.5);
        y += (gp.tileSize / 4);
        g2.drawImage(coin, x, y, size, size, null);

        int itemValue = 0;
        int itemIndex = getItemIndex(npcSlotCol, npcSlotRow);
        if(itemIndex < npc.inventory.size()){
            itemValue = npc.inventory.get(itemIndex).price;
        }

        x += (int)(gp.tileSize * 1.5);
        y += gp.tileSize;
        g2.setFont(dialogueFont.deriveFont(Font.PLAIN, 32F));
        g2.drawString(Integer.toString(itemValue), x, y);

        // BUY AN ITEM
        if(gp.keyHandler.enterPressed){
            if(npc.inventory.get(itemIndex).price > gp.player.coin){
                npc.startDialogue(npc, 2);
            }
            else{
                if(gp.player.canObtainItem(npc.inventory.get(itemIndex))){
                    gp.player.coin -= npc.inventory.get(itemIndex).price;
                }
                else{
//                    subState = 0;
                    npc.startDialogue(npc, 3);
                }
            }
        }
    }

    public void trade_sell(){
        // draw player inventory
        int x = gp.tileSize;
        int y = gp.tileSize * 2;
        int width = gp.tileSize * 8;
        int height = gp.tileSize * 6;
        drawInventory(gp.player, true, x, y, width, height);

        // draw coin window
        y += height;
        height = gp.tileSize * 2;
        drawSubWindow(x, y, width, height);
        x += gp.tileSize * 2;
        y += (gp.tileSize / 4);
        int size = (int)(gp.tileSize * 1.5);
        g2.drawImage(coin, x, y, size, size, null);

        int itemValue = 0;
        int itemIndex = getItemIndex(playerSlotCol, playerSlotRow);
        if(itemIndex < gp.player.inventory.size() && gp.player.inventory.get(itemIndex) != null){
            itemValue = (int)(gp.player.inventory.get(itemIndex).price * .67);
        }

        x += (int)(gp.tileSize * 1.5);
        y += gp.tileSize;
        g2.setFont(dialogueFont.deriveFont(Font.PLAIN, 32F));
        g2.drawString(gp.player.coin + " + " + itemValue, x, y);

        // draw nav hint
        String text = "[ESC] Back";
        x = getXForRightAlignedText(text, gp.screenWidth);
        y += (int)(gp.tileSize * 1.5);
        g2.setFont(dialogueFont.deriveFont(Font.PLAIN, 20F));
        g2.setColor(Color.black);
        g2.drawString(text, x - 8, y);
        g2.setColor(Color.white);
        g2.drawString(text, x - 10, y - 2);

        // draw npc inventory
        x = gp.screenWidth - (gp.tileSize + width);
        y = gp.tileSize * 2;
        height = gp.tileSize * 6;
        drawInventory(npc, false, x, y, width, height);

        // SELL AN ITEM
        if(gp.keyHandler.enterPressed){
            if(gp.player.inventory.get(itemIndex) == gp.player.currentWeapon ||
            gp.player.inventory.get(itemIndex) == gp.player.currentShield){
//                subState = 0;
                npc.startDialogue(npc, 4);
                drawDialogueScreen();
            }
            else if(gp.player.inventory.isEmpty()){
//                subState = 0;
                npc.startDialogue(npc, 5);
            }
            else{
                // add item to npc inventory
                if(npc.canObtainItem(gp.player.inventory.get(itemIndex))){
                    gp.player.coin += itemValue;
                }
                else{
                    npc.startDialogue(npc, 6);
                }

                // remove item from players inventory
                if(gp.player.inventory.get(itemIndex).amount > 1){
                    gp.player.inventory.get(itemIndex).amount--;
                }
                else{
                    gp.player.inventory.remove(gp.player.inventory.get(itemIndex));
                }
            }
        }
    }

    public void drawSleepScreen(){
        counter++;

        // increase darkness
        if(counter < 120){
            gp.environmentManager.lighting.filterAlpha += 0.01F;
            if(gp.environmentManager.lighting.filterAlpha > 1F){
                gp.environmentManager.lighting.filterAlpha = 1F;
            }
        }

        //decrease darkness
        if(counter >= 120){
            gp.environmentManager.lighting.filterAlpha -= 0.01F;
            if(gp.environmentManager.lighting.filterAlpha < 0F){
                gp.environmentManager.lighting.filterAlpha = 0F;
                counter = 0;
                gp.environmentManager.lighting.dayState = gp.environmentManager.lighting.day;
                gp.environmentManager.lighting.dayCounter = 0;
                gp.player.getImage();
                gp.gameState = gp.playState;
            }
        }
    }

    public int getItemIndex(int slotCol, int slotRow){
        return slotCol + (slotRow * 7); // 7 = number of slots in 1 inventory row
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
