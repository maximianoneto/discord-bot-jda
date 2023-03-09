package com.discord.bot.command;

import com.discord.bot.lavaplayer.AudioPlayerSendHandler;
import com.discord.bot.bot.AudioManager;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.Random;

public class UserListener extends ListenerAdapter {
    private AudioPlayerSendHandler audioPlayerSendHandler;

    @Override
    public void onGuildVoiceJoin(@NotNull GuildVoiceJoinEvent event) {
        VoiceChannel voiceChannel = Objects.requireNonNull(Objects.requireNonNull(event.getMember()).getVoiceState()).getChannel();
        // pegar o canal de voz que o bot está conectado
        VoiceChannel botVoiceChannel = Objects.requireNonNull(Objects.requireNonNull(event.getGuild().getSelfMember()).getVoiceState()).getChannel();

        if (voiceChannel.equals(botVoiceChannel)) {
            // reproduzir o arquivo .mp4
            AudioManager audioManager = new AudioManager();

            Random rand = new Random();
            int valor = rand.nextInt(2);
            if (valor == 0) {
                audioManager.loadAndPlay("https://www.youtube.com/watch?v=1yadx1wd6PQ");
            } else {
                audioManager.loadAndPlay("https://www.youtube.com/shorts/_rg7x7kiUJQ");
            }
            audioPlayerSendHandler = new AudioPlayerSendHandler(audioManager.getPlayer());
            event.getGuild().getAudioManager().setSendingHandler(audioPlayerSendHandler);
        }
    }
}
