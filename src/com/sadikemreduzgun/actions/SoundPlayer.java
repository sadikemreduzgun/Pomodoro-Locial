package com.sadikemreduzgun.actions;
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.*;

public class SoundPlayer {

    private File soundFile;

    public void playSound(int type) {

        try {
            // Load the sound file
            if (type == 0){
                soundFile = new File("sources/session_starting_sound.wav"); // Replace with the actual path to your sound file
            }
            else {
                soundFile = new File("sources/time_end_sound.wav"); // Replace with the actual path to your sound file

            }

            AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);

            // Get a clip resource
            Clip clip = AudioSystem.getClip();

            // Open audio clip and load samples from the audio input stream
            clip.open(audioIn);

            // Start playing the sound
            clip.start();

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
            ex.printStackTrace();
        }
    }

}
