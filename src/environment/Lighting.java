package environment;

import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Lighting {
    GamePanel gp;
    BufferedImage darknessFilter;

    int dayCounter;
    float filterAlpha = 0F;
    final int day = 0;
    final int dusk = 1;
    final int night = 2;
    final int dawn = 3;
    int dayState = day;
//    int dayLength = 60 * 60 * 10; // 10 mins
    int dayLength = 60 * 60 * 1; // 10 mins


    public Lighting(GamePanel gp) {
        this.gp = gp;

        setLightSource();
    }

    public void setLightSource(){
        // Create a buffered image
        darknessFilter = new BufferedImage(gp.screenWidth, gp.screenHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = (Graphics2D)darknessFilter.getGraphics();

        // Get the center x and y of the light circle
        int centerX = gp.player.screenX + (gp.tileSize)/2;
        int centerY = gp.player.screenY + (gp.tileSize)/2;

        // Create a gradation effect
        Color color[] = new Color[12];
        float fraction[] = new float[12];

        fraction[0] = 0f;
        fraction[1] = 0.4f;
        fraction[2] = 0.5f;
        fraction[3] = 0.6f;
        fraction[4] = 0.65f;
        fraction[5] = 0.7f;
        fraction[6] = 0.75f;
        fraction[7] = 0.8f;
        fraction[8] = 0.85f;
        fraction[9] = 0.9f;
        fraction[10] = 0.95f;
        fraction[11] = 1f;

        float blueValue = 0.1F;

        // check if player has light
        if(gp.player.currentLight == null){
            color[0] = new Color(0,0,blueValue,0.5f);
            color[1] = new Color(0,0,blueValue,0.55f);
            color[2] = new Color(0,0,blueValue,0.60f);
            color[3] = new Color(0,0,blueValue,0.65f);
            color[4] = new Color(0,0,blueValue,0.70f);
            color[5] = new Color(0,0,blueValue,0.75f);
            color[6] = new Color(0,0,blueValue,0.80f);
            color[7] = new Color(0,0,blueValue,0.85f);
            color[8] = new Color(0,0,blueValue,0.90f);
            color[9] = new Color(0,0,blueValue,0.95f);
            color[10] = new Color(0,0,blueValue,0.96f);
            color[11] = new Color(0,0,blueValue,0.98f);

            // Create a gradation paint settings
            RadialGradientPaint gPaint = new RadialGradientPaint(centerX, centerY, 50, fraction, color); // player light radius without light source

            // Set the gradient data on g2
            g2.setPaint(gPaint);
        }
        else{
            color[0] = new Color(0,0,blueValue,0.1f);
            color[1] = new Color(0,0,blueValue,0.42f);
            color[2] = new Color(0,0,blueValue,0.52f);
            color[3] = new Color(0,0,blueValue,0.61f);
            color[4] = new Color(0,0,blueValue,0.69f);
            color[5] = new Color(0,0,blueValue,0.76f);
            color[6] = new Color(0,0,blueValue,0.82f);
            color[7] = new Color(0,0,blueValue,0.87f);
            color[8] = new Color(0,0,blueValue,0.91f);
            color[9] = new Color(0,0,blueValue,0.94f);
            color[10] = new Color(0,0,blueValue,0.96f);
            color[11] = new Color(0,0,blueValue,0.98f);

            // Create a gradation paint settings
            RadialGradientPaint gPaint = new RadialGradientPaint(centerX, centerY, gp.player.currentLight.lightRadius, fraction, color);

            // Set the gradient data on g2
            g2.setPaint(gPaint);
        }

        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        g2.dispose();
    }

    public void update(){
        if(gp.player.lightUpdated){
            setLightSource();
            gp.player.lightUpdated = false;
        }

        // check state of day
        if(dayState == day){
            dayCounter++;
            if(dayCounter > dayLength){
                dayState = dusk;
                dayCounter = 0;
            }
        }

        if(dayState == dusk){
            filterAlpha += 0.0005f;

            if(filterAlpha > 1F){
                filterAlpha = 1F;
                dayState = night;
            }
        }

        if(dayState == night){
            dayCounter++;

            if(dayCounter > dayLength){
                dayState = dawn;
                dayCounter = 0;
            }
        }

        if(dayState == dawn){
            filterAlpha -= 0.0005f;

            if(filterAlpha < 0F){
                filterAlpha = 0F;
                dayState = day;
            }
        }
    }

    public void draw(Graphics2D g2){
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, filterAlpha));
        g2.drawImage(darknessFilter, 0, 0, null);
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1F));

        // DEBUG
        String timeOfDay = "";
        if(gp.debug){
            switch(dayState){
                case day: timeOfDay = "Day"; break;
                case dusk: timeOfDay = "Dusk"; break;
                case night: timeOfDay = "Night"; break;
                case dawn: timeOfDay = "Dawn"; break;
            }

            // draw current time of day
            int x = gp.tileSize / 2;
            int y = (gp.tileSize * 11) + (gp.tileSize / 2);

            g2.setFont(gp.ui.dialogueFont.deriveFont(Font.PLAIN, 20F));
            g2.setColor(Color.BLACK);
            g2.drawString(timeOfDay, x, y);
            g2.setColor(Color.WHITE);
            g2.drawString(timeOfDay, x - 2, y - 2);
        }
    }
}
