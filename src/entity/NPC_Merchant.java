package entity;

import main.GamePanel;
import object.OBJ_Heart;
import object.OBJ_ManaCrystal;
import object.OBJ_Shield_Blue;

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
        dialogues[0] = "Wanna trade? ;P";
    }

    public void speak(){
        super.speak();

        // can add character specific stuff
        // merchant specific
        gp.gameState = gp.tradeState;

        gp.ui.npc = this;

    }

    public void setItems(){
        inventory.add(new OBJ_Heart(gp));
        inventory.add(new OBJ_ManaCrystal(gp));
        inventory.add(new OBJ_Shield_Blue(gp));
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
