package object;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Key extends SuperObject{
    public OBJ_Key() {
        name = "Key";
        try{
            image = ImageIO.read(getClass().getResourceAsStream("/objects/key.png"));
        }
        catch(IOException e){
            e.printStackTrace();
        }

//        solidArea.x = 8; // set custom solid area
//        solidArea.y = 8; // set custom solid area

    }
}
