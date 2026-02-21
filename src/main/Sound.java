package main;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.net.URL;

public class Sound {

    Clip clip;
    URL soundURL[] = new URL[30];
    FloatControl fc;
    int volumeScale = 3; // default volume
    float volume;

    public Sound(){
        int i = 0;

        soundURL[i] = getClass().getResource("/sound/music/BlueBoyAdventure.wav"); i++; // 0
        soundURL[i] = getClass().getResource("/sound/soundEffects/coin.wav"); i++; // 1
        soundURL[i] = getClass().getResource("/sound/soundEffects/powerup.wav"); i++; // 2
        soundURL[i] = getClass().getResource("/sound/soundEffects/unlock.wav"); i++; // 3
        soundURL[i] = getClass().getResource("/sound/soundEffects/fanfare.wav"); i++; // 4
        soundURL[i] = getClass().getResource("/sound/soundEffects/hitmonster.wav"); i++; // 5
        soundURL[i] = getClass().getResource("/sound/soundEffects/receivedamage.wav"); i++; // 6
        soundURL[i] = getClass().getResource("/sound/soundEffects/melee_low.wav"); i++; // 7
        soundURL[i] = getClass().getResource("/sound/soundEffects/melee_med.wav"); i++; // 8
        soundURL[i] = getClass().getResource("/sound/soundEffects/melee_high.wav"); i++; // 9
        soundURL[i] = getClass().getResource("/sound/soundEffects/cursor.wav"); i++; // 10
        soundURL[i] = getClass().getResource("/sound/soundEffects/burning.wav"); i++; // 11
        soundURL[i] = getClass().getResource("/sound/soundEffects/cuttree.wav"); i++; // 12
        soundURL[i] = getClass().getResource("/sound/soundEffects/gameover.wav"); i++; // 13
        soundURL[i] = getClass().getResource("/sound/soundEffects/sleep.wav"); i++; // 14
        soundURL[i] = getClass().getResource("/sound/soundEffects/blocked.wav"); i++; // 15
        soundURL[i] = getClass().getResource("/sound/soundEffects/parry.wav"); i++; // 16
        soundURL[i] = getClass().getResource("/sound/soundEffects/speak.wav"); i++; // 17
        soundURL[i] = getClass().getResource("/sound/music/Merchant.wav"); i++; // 18
        soundURL[i] = getClass().getResource("/sound/music/Dungeon.wav"); i++; // 19
    }

    public void setFile(int index){
        try{
           AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundURL[index]);
           clip = AudioSystem.getClip();
           clip.open(audioInputStream);
           fc = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
           checkVolume();
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

    public void checkVolume(){
        switch(volumeScale){
            case 0:
                volume = -80F; // no sound
                break;
            case 1:
                volume = -20F;
                break;
            case 2:
                volume = -12F;
                break;
            case 3:
                volume = -5F;
                break;
            case 4:
                volume = 1F;
                break;
            case 5:
                volume = 6F; // max volume
                break;
        }
        fc.setValue(volume);
    }
}
