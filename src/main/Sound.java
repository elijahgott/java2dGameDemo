package main;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.net.URL;

public class Sound {

    Clip clip;
    URL soundURL[] = new URL[30];

    public Sound(){
        int i = 0;

        soundURL[i] = getClass().getResource("/sound/BlueBoyAdventure.wav"); i++; // 0
        soundURL[i] = getClass().getResource("/sound/coin.wav"); i++; // 1
        soundURL[i] = getClass().getResource("/sound/powerup.wav"); i++; // 2
        soundURL[i] = getClass().getResource("/sound/unlock.wav"); i++; // 3
        soundURL[i] = getClass().getResource("/sound/fanfare.wav"); i++; // 4
        soundURL[i] = getClass().getResource("/sound/hitmonster.wav"); i++; // 5
        soundURL[i] = getClass().getResource("/sound/receivedamage.wav"); i++; // 6
        soundURL[i] = getClass().getResource("/sound/melee_low.wav"); i++; // 7
        soundURL[i] = getClass().getResource("/sound/melee_med.wav"); i++; // 8
        soundURL[i] = getClass().getResource("/sound/melee_high.wav"); i++; // 9
        soundURL[i] = getClass().getResource("/sound/cursor.wav"); i++; // 10
        soundURL[i] = getClass().getResource("/sound/burning.wav"); i++; // 11
        soundURL[i] = getClass().getResource("/sound/cuttree.wav"); i++; // 12
    }

    public void setFile(int index){
        try{
           AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundURL[index]);
           clip = AudioSystem.getClip();
           clip.open(audioInputStream);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public void play(){
        clip.start();
    }

    public void loop(){
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stop(){
        clip.stop();
    }
}
