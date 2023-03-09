package com.discord.bot.lavaplayer;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;

public class PlayerManager {
    private static PlayerManager INSTANCE;

    private final AudioPlayerManager playerManager;

    public static PlayerManager getInstance() {
        return INSTANCE;
    }

    private PlayerManager(AudioPlayerManager playerManager) {
        this.playerManager = new DefaultAudioPlayerManager();

        AudioSourceManagers.registerRemoteSources(playerManager);
        AudioSourceManagers.registerLocalSource(playerManager);
    }

    private static PlayerManager create(AudioPlayerManager playerManager) {
        if (INSTANCE != null) {
            throw new IllegalStateException("PlayerManager is already initialized");
        }
        INSTANCE = new PlayerManager(playerManager);
        return INSTANCE;
    }
}

