package com.discord.bot.lavaplayer;

import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import org.springframework.stereotype.Service;

@Service
public class TrackScheduler extends AudioEventAdapter {
//    private final AudioManager player;
//    private final BlockingQueue<AudioTrack> queue;
//
//    public TrackScheduler(AudioManager player, BlockingQueue<AudioTrack> queue) {
//        this.player = player;
//
//        this.queue = queue;
//    }
//
//    public void queue(AudioTrack track) {
//        if(!player.onTrackEnd(track, AudioTrackEndReason.FINISHED)) {
//            queue.offer(track);
//        }
//        queue.offer(track);
//    }
//
//    public void nextTrack() {
//        player.loadAndPlay(String.valueOf(this.queue.poll()));
//    }
//
//    public void onTrackEnd(AudioPlayer player, AudioTrack track, AudioTrackEndReason endReason) {
//        if (endReason.mayStartNext) {
//            nextTrack();
//        }
//    }
}
