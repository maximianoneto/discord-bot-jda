package com.discord.bot.bot;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;
import net.dv8tion.jda.api.audio.AudioSendHandler;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Service;

import java.nio.ByteBuffer;
import java.util.LinkedList;
import java.util.Queue;

@Service
public class AudioManager implements AudioSendHandler {

    private final Queue<AudioTrack> queue;
    private final AudioPlayerManager playerManager;
    private final AudioPlayer player;

    public AudioManager() {
        playerManager = new DefaultAudioPlayerManager();
        AudioSourceManagers.registerRemoteSources(playerManager);
        player = playerManager.createPlayer();
        queue = new LinkedList<>();

        player.addListener(event -> {
            if (event.player.getPlayingTrack() == null && !queue.isEmpty()) {
                player.startTrack(queue.poll(), false);
            }
        });
    }

    public void loadAndPlay(String trackUrl) {
        playerManager.loadItemOrdered(player, trackUrl, new AudioLoadResultHandler() {
            @Override
            public void trackLoaded(AudioTrack track) {
                queue.offer(track);
                if (player.getPlayingTrack() == null) {
                    player.startTrack(queue.poll(), false);
                }
            }

            @Override
            public void playlistLoaded(AudioPlaylist playlist) {
                playlist.getTracks().forEach(queue::offer);
            }

            @Override
            public void noMatches() {
                System.out.println("No matches found");
            }

            @Override
            public void loadFailed(FriendlyException exception) {
                System.out.println("Load failed");
            }
        });
    }

    public void skip() {
        player.stopTrack();
        player.startTrack(queue.poll(), false);
    }

    public AudioPlayer getPlayer() {
        return player;
    }

    public void stopMusic() {
        player.stopTrack();
    }

    public void stop() {
        player.stopTrack();
        queue.clear();
    }

    @Override
    public boolean canProvide() {
        return player.provide() != null;
    }

    @Nullable
    @Override
    public ByteBuffer provide20MsAudio() {
        return ByteBuffer.wrap(player.provide().getData());
    }
}

