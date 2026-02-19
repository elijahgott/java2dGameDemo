package entity;

import main.GamePanel;
import object.*;

public class NPC_Merchant extends Entity {
    public NPC_Merchant(GamePanel gp) {
        super(gp);

        type = type_npc;
        direction = "down";
        speed = 1;
//        solidArea = new Rectangle(0, 0, 48, 48); // not used because default entity solid area is used

        getImage();
        setDialogue();
        setItems();
    }

    public void setDialogue(){
        dialogues[0][0] = "Wanna trade? ;P";
        dialogues[1][0] = "Okayy...\nByeeee!!! <3";
        dialogues[2][0] = "Too poor. Get out.";
        dialogues[3][0] = "You're too weak to carry any\nmore items!";
        dialogues[4][0] = "You cannot sell an equipped item!";
        dialogues[5][0] = "You do not have any more\nitems to sell!";
        dialogues[6][0] = "You're too big!\nI cannot hold any more items!\nSooo stuffed...";
    }

    public void speak(){
        facePlayer();
        // can add character specific stuff
        // merchant specific
        gp.gameState = gp.tradeState;

        gp.ui.npc = this;

    }

    public void setItems(){
        inventory.add(new OBJ_Shield_Wood(gp));
        inventory.add(new OBJ_Shield_Blue(gp));
        inventory.add(new OBJ_Boots(gp));
        inventory.add(new OBJ_Potion_Red(gp));
    }

    public void getImage(){
        up1 = setup("npc/merchant/merchant_down_1", gp.tileSize, gp.tileSize);
        up2 = setup("npc/merchant/merchant_down_2", gp.tileSize, gp.tileSize);
        down1 = setup("npc/merchant/merchant_down_1", gp.tileSize, gp.tileSize);
        down2 = setup("npc/merchant/merchant_down_2", gp.tileSize, gp.tileSize);
        left1 = setup("npc/merchant/merchant_down_1", gp.tileSize, gp.tileSize);
        left2 = setup("npc/merchant/merchant_down_2", gp.tileSize, gp.tileSize);
        right1 = setup("npc/merchant/merchant_down_1", gp.tileSize, gp.tileSize);
        right2 = setup("npc/merchant/merchant_down_2", gp.tileSize, gp.tileSize);
    }
}
