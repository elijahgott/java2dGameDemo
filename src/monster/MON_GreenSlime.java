package monster;

import entity.Entity;
import main.GamePanel;

import java.util.Random;

public class MON_GreenSlime extends Entity {

    GamePanel gp;
    public MON_GreenSlime(GamePanel gp) {
        super(gp);
        this.gp = gp;

        name = "Green Slime";
        type = 2;
        speed = 1;
        maxHealth = 4;
        health = maxHealth;

        solidArea.x = 4;
        solidArea.y = 18;
        solidArea.width = 40;
        solidArea.height = 30;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        getImage();
    }

    public void getImage() {
        up1 = setup("monster/slime/greenslime_down_1", gp.tileSize, gp.tileSize);
        up2 = setup("monster/slime/greenslime_down_2", gp.tileSize, gp.tileSize);
        down1 = setup("monster/slime/greenslime_down_1", gp.tileSize, gp.tileSize);
        down2 = setup("monster/slime/greenslime_down_2", gp.tileSize, gp.tileSize);
        left1 = setup("monster/slime/greenslime_down_1", gp.tileSize, gp.tileSize);
        left2 = setup("monster/slime/greenslime_down_2", gp.tileSize, gp.tileSize);
        right1 = setup("monster/slime/greenslime_down_1", gp.tileSize, gp.tileSize);
        right2 = setup("monster/slime/greenslime_down_2", gp.tileSize, gp.tileSize);
    }
    public void setAction() {
        actionLockCounter++;

        if(actionLockCounter == 120) {
            Random random = new Random();
            int i = random.nextInt(100) + 1; // random number from 1 - 100

            if (i <= 25) {
                direction = "up";
            } else if (i <= 50) {
                direction = "down";
            } else if (i <= 75) {
                direction = "left";
            } else {
                direction = "right";
            }

            actionLockCounter = 0;
        }
    }

}
