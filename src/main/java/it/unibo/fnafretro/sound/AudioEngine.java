package it.unibo.fnafretro.sound;

import java.io.IOException;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import it.unibo.fnafretro.gui.FnafrComponent;

/**
 * Classe per la gestione dei suoni in gioco.
 * @author  Luca Ponseggi
 */
public final class AudioEngine {

    private static final float BASE_VOLUME = -10.0f;

    private AudioEngine() { }

    /**
     * Carica un suono dalle risorse.
     * @param name  il nome del suono senza estensione
     * @return  la Clip generata
     */
    public static Clip loadSound(final String name) {
        try {
            final var stream = AudioSystem.getAudioInputStream(
                ClassLoader.getSystemResource(
                    FnafrComponent.RESOURCE_ROOT + "sounds/" + name + ".wav"
                )
            );

            final Clip clip = AudioSystem.getClip();
            clip.open(stream);

            ((FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN)).setValue(BASE_VOLUME);

            return clip;
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            return null;
        }
    }

    /**
     * Riproduce la clip una sola volta.
     * @param clip  la clip da riprodurre
     */
    public static void playOnce(final Clip clip) {
        clip.stop();
        clip.setMicrosecondPosition(0);
        clip.start();
    }
}
