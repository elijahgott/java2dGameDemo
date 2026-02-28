package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Heart_Blue extends Entity {
    GamePanel gp;
    public static final String objName = "Blue Heart";

    public OBJ_Heart_Blue(GamePanel gp) {
        super(gp);
        this.gp = gp;

        name = objName;
        description = "The legendary Blue Heart.";
        type = type_consumable;

        down1 = setup("objects/blueheart");

        setDialogue();
    }

    public void setDialogue(){
        dialogues[0][0] = "Wow...it's so big and\nbeautiful...";
        dialogues[0][1] = "It's the Trifo-...\n...Blue Heart!";
    }

    public boolean use(Entity entity){
        gp.gameState = gp.cutsceneState;
        gp.cutsceneManager.sceneNum = gp.cutsceneManager.ending;

        return true;
    }
}
