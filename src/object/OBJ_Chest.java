package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Chest extends Entity{
    GamePanel gp;

    public static final String objName = "Chest";

    public OBJ_Chest(GamePanel gp) {
        super(gp);
        this.gp = gp;

        name = objName;
        type = type_obstacle;

        image = setup("objects/chest", gp.tileSize, gp.tileSize);
        image2 = setup("objects/chest_open", gp.tileSize, gp.tileSize);
        // default image
        down1 = image;
        collision = true;

        solidArea.x = 4;
        solidArea.y = 24;
        solidArea.width = 40;
        solidArea.height = 24;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }

    public void setLoot(Entity loot) {
        this.loot = loot;
        setDialogue();
    }

    public void setDialogue(){
        dialogues[0][0] = "You open the chest and find a " + loot.name + "!\nYou look around and put it in your\npocket.";
        dialogues[1][0] = "You open the chest and find a " + loot.name + "!\nBut you cannot hold any more items!";
        dialogues[2][0] = "It's empty!";
    }

    public void interact(){
        if(!opened){
            gp.playSoundEffect(3);

            // immediately use blue heart when obtained
            if(loot.name.equals(OBJ_Heart_Blue.objName)){
                loot.use(gp.player);
                opened = true;
                down1 = image2;
                System.out.println(loot.name);
            }
            else if(gp.player.canObtainItem(loot)){
                // put item in inventory
                startDialogue(this, 0);
                opened = true;
                down1 = image2;
                System.out.println(loot.name);
            }
            else{
                // inventory full
                startDialogue(this, 1);
            }
        }
        else{
            // chest already opened
            startDialogue(this, 2);
        }
    }
}
