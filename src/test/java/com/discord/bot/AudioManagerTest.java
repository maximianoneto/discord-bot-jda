package com.discord.bot;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.discord.bot.bot.AudioManager;
import org.junit.jupiter.api.Test;

import java.nio.ByteBuffer;

public class AudioManagerTest {

    @Test
    public void testLoadAndPlay() {
        // Setup
        AudioManager audioManager = new AudioManager();
        String trackUrl = "some_url";

        // Execute
        audioManager.loadAndPlay(trackUrl);

        // Assert
        assertTrue(audioManager.getPlayer().getPlayingTrack() != null);
    }

    @Test
    public void testSkip() {
        // Setup
        AudioManager audioManager = new AudioManager();

        // Execute
        audioManager.skip();

        // Assert
        assertFalse(audioManager.getPlayer().getPlayingTrack() != null);
    }

    @Test
    public void testStopMusic() {
        // Setup
        AudioManager audioManager = new AudioManager();

        // Execute
        audioManager.stopMusic();

        // Assert
        assertEquals(audioManager.getPlayer().getPlayingTrack().getPosition(), 0);
    }

    @Test
    public void testStop() {
        // Setup
        AudioManager audioManager = new AudioManager();

        // Execute
        audioManager.stop();

        // Assert
        assertEquals(audioManager.getPlayer().getPlayingTrack(), null);
    }

    @Test
    public void testCanProvide() {
        // Setup
        AudioManager audioManager = new AudioManager();

        // Execute
        boolean canProvide = audioManager.canProvide();

        // Assert
        assertTrue(canProvide);
    }

    @Test
    public void testProvide20MsAudio() {
        // Setup
        AudioManager audioManager = new AudioManager();

        // Execute
        ByteBuffer audio = audioManager.provide20MsAudio();

        // Assert
        assertNotNull(audio);
    }
}