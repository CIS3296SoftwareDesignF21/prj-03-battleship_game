package com.battleship.utils;

import com.battleship.gui.GameBoard;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class SoundEffects {

    /**
     * Private method to play a specific sound effect
     */
    private static void playEffect(AudioInputStream as) {
        AudioInputStream audioInputStream = null;
        Clip clip = null;
        try {
            audioInputStream = as;
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
        clip.start();
    }

    /**
     *  Play the boom sound whenever a player gets a hit
     */
    public static void playBoom(GameBoard gb) {
        try {
            playEffect(AudioSystem.getAudioInputStream(gb.getClass().getResource("/boom.wav")));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *  Play boom-goes-the-dynamite sound effect whenever a player gets a hit with a powerup
     */
    public static void playBoomDynamite(GameBoard gb) {
        try {
            playEffect(AudioSystem.getAudioInputStream(gb.getClass().getResource("/boomDynamite.wav")));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void playWinning(GameBoard gb) {
        try {
            playEffect(AudioSystem.getAudioInputStream(gb.getClass().getResource("/winning.wav")));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void playLosing(GameBoard gb) {
        try {
            playEffect(AudioSystem.getAudioInputStream(gb.getClass().getResource("/losing.wav")));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
