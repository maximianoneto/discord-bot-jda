package com.discord.bot.bot;

import com.discord.bot.config.Config;
import com.discord.bot.lavaplayer.AudioPlayerSendHandler;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;

@Service
public class MusicCommand extends ListenerAdapter {
    String searchTerm = "music";
    String youtubeApiKey = "AIzaSyDBcyIpMSToCBY1d-ymtWtYdwAbYgrjgws";
    String youtubeApiUrl = "https://www.googleapis.com/youtube/v3/search?part=snippet&q=" + searchTerm + "&key=" + youtubeApiKey;
    private AudioManager audioManager = new AudioManager();
    private final String COMMAND = "$play";

    private final String COMMAND2 = "$skip";

    private AudioPlayerSendHandler audioPlayerSendHandler;
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        try {
            String raw = event.getMessage().getContentRaw();
            String prefix = Config.get("prefix");
            if (event.getMessage().getContentStripped().startsWith(COMMAND)) {
                String[] command = event.getMessage().getContentRaw().split(" ");
                event.getGuild().getAudioManager().openAudioConnection(Objects.requireNonNull(Objects.requireNonNull(event.getMember()).getVoiceState()).getChannel()); // Connect to the voice channel

                if (command.length == 2) {
                    String trackUrl = command[1];
                    // chamar o metódo de reprodução de música e
                    audioManager.loadAndPlay(trackUrl);
                    event.getChannel().sendMessage("SUA MÃE ESTÁ ESCUTANDO: \uD83D\uDC69\u200D\uD83D\uDC67 \n " + trackUrl).queue();
                    audioPlayerSendHandler = new AudioPlayerSendHandler(audioManager.getPlayer());
                    event.getGuild().getAudioManager().setSendingHandler(audioPlayerSendHandler);

                } else {
                    event.getChannel().sendMessage("POR FAVOR TENTE SER MENOS OTÁRIO PARA QUE A MÚSICA POSSA TOCAR. \uD83D\uDE12 ").queue();

                }
            }

            if (raw.equalsIgnoreCase(prefix + "randommusic")){
                try {
                    URL url = new URL(youtubeApiUrl);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setRequestProperty("Content-Type", "application/json");
                    connection.setDoOutput(true);

                    BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String inputLine;
                    StringBuffer content = new StringBuffer();
                    while ((inputLine = in.readLine()) != null) {
                        content.append(inputLine);
                    }
                    in.close();
                    connection.disconnect();
                    // {  "kind": "youtube#searchListResponse",  "etag": "J69KrVqmNNlsjW_pXKYZsS_CoAw",  "nextPageToken": "CAUQAA",  "regionCode": "BR",  "pageInfo": {    "totalResults": 1000000,    "resultsPerPage": 5  },  "items": [    {      "kind": "youtube#searchResult",      "etag": "xycJLhTgGMLLtSAkkgQRjq_I3Vc",      "id": {        "kind": "youtube#video",        "videoId": "U6JBZNAkp24"      }
                    // regex para tirar o id do vídeo
                    String videoId = content.toString().split("videoId\": \"")[1].split("\"")[0];
                    String videoUrl = "https://www.youtube.com/watch?v=" + videoId;
                    event.getChannel().sendMessage(videoUrl).queue();

                    event.getGuild().getAudioManager().openAudioConnection(Objects.requireNonNull(Objects.requireNonNull(event.getMember()).getVoiceState()).getChannel()); // Connect to the voice channel

                    // chamar o metódo de reprodução de música e
                    audioManager.loadAndPlay(videoUrl);
                    event.getChannel().sendMessage("SUA MÃE ESTÁ ESCUTANDO: \uD83D\uDC69\u200D\uD83D\uDC67 \n " + videoUrl).queue();
                    audioPlayerSendHandler = new AudioPlayerSendHandler(audioManager.getPlayer());
                    event.getGuild().getAudioManager().setSendingHandler(audioPlayerSendHandler);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if(event.getMessage().getContentStripped().startsWith("$stop")){
                event.getGuild().getAudioManager().closeAudioConnection();
                event.getChannel().sendMessage("A MÚSICA PAROU DE TOCAR \uD83D\uDE12 ").queue();
            }
            if(event.getMessage().getContentStripped().startsWith(COMMAND2)){
                // pular música
                audioManager.skip();
                event.getChannel().sendMessage("A MÚSICA FOI PULADA \uD83D\uDE12 ").queue();
            }
        }catch (FriendlyException e){
            e.printStackTrace();
        }
    }
}
