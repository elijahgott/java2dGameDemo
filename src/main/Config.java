package main;

import java.io.*;

public class Config {
    GamePanel gp;

    public Config(GamePanel gp) {
        this.gp = gp;
    }

    public void saveConfig(){
        try {
            BufferedWriter bw = new  BufferedWriter(new FileWriter("config.txt"));

            // full screen
            if(gp.fullScreenOn){
                bw.write("ON");
            }
            else{
                bw.write("OFF");
            }
            // next line
            bw.newLine();

            // music volume
            bw.write(String.valueOf(gp.music.volumeScale));
            bw.newLine();

            // sound effect volume
            bw.write(String.valueOf(gp.soundEffect.volumeScale));
            bw.newLine();

            // add more later?

            bw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadConfig(){
        try {
            BufferedReader br = new BufferedReader(new FileReader("config.txt"));

            String s = br.readLine();

            // Full Screen
            if(s.equals("ON")){
                gp.fullScreenOn = true;
            }
            if(s.equals("OFF")){
                gp.fullScreenOn = false;
            }

            // music volume
            s = br.readLine();
            gp.music.volumeScale = Integer.parseInt(s);

            // sound effect volume
            s = br.readLine();
            gp.soundEffect.volumeScale = Integer.parseInt(s);

            // add more later?

            br.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
