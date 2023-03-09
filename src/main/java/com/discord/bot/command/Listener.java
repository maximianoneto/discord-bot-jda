package com.discord.bot.command;

import com.discord.bot.config.Config;
import com.discord.bot.lavaplayer.AudioPlayerSendHandler;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;


public class Listener extends ListenerAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(Listener.class);

    public void onReady(@Nonnull ReadyEvent readyEvent) {
        LOGGER.info(" {} is ready", readyEvent.getJDA().getSelfUser().getAsTag());
    }

    private AudioPlayerSendHandler audioPlayerSendHandler;

    @Override
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {
        String prefix = Config.get("prefix");
        String raw = event.getMessage().getContentRaw();

        //fazer uma verificação para validar quantas pessoas estão digitaram $Play
        if (raw.equalsIgnoreCase(prefix + "play")) {
            event.getChannel().sendMessage("A MÚSICA ESTÁ TOCANDO \uD83D\uDC69\u200D\uD83D\uDC67").queue();
        }

        if (raw.equalsIgnoreCase(prefix + "join")) {
         // bot entra no canal de voz
            VoiceChannel voiceChannel = Objects.requireNonNull(Objects.requireNonNull(event.getMember()).getVoiceState()).getChannel();
            event.getGuild().getAudioManager().openAudioConnection(voiceChannel);
        }

        if (raw.equalsIgnoreCase(prefix + "shutdown")) {
            if (event.getAuthor().getId().equals(Config.get("owner_id"))) {
                event.getChannel().sendMessage("SIM, LORD! ESTOU DESLIGANDO! \uD83D\uDE0C ").queue();
                event.getGuild().getAudioManager().closeAudioConnection();
                event.getJDA().shutdown();
            } else {
                event.getChannel().sendMessage("VOCÊ NÃO TEM PODER PRA ISSO PUTINHA! \uD83E\uDDD0").queue();
            }
        }

        if (raw.equalsIgnoreCase(prefix + "duardo")) {
            event.getChannel().sendMessage("https://www.youtube.com/watch?v=Z1xYQZ5wI0s").queue();
        }

        if (raw.equalsIgnoreCase(prefix + "ping")) {
            event.getChannel().sendMessage("Pong!").queue();
            String ping = String.valueOf(event.getJDA().getRestPing().complete());
            event.getChannel().sendMessage("Latência: " + ping + "ms").queue();
        }
        if (raw.equalsIgnoreCase(prefix + "whisky")) {
            event.getChannel().sendFile(new File("src/main/resources/Whisky_microfone.jpg")).queue();
        }
        if (raw.equalsIgnoreCase(prefix + "help")) {
            event.getChannel().sendMessage("```" +
                    "Comandos disponíveis: \n" +
                    " $help - Mostra os comandos disponíveis \n" +
                    " $ping - Mostra a latência do bot \n" +
                    " $whisky - Mostra uma imagem do whisky \n" +
                    " $duardo - Mostra um vídeo do duardo \n" +
                    " $shutdown - Desliga o bot \n" +
                    " $play - Toca a música \n" +
                    " $esculachar -  O bot responderá com uma série de insultos aleatórios \n" +
                    " $meme - O bot responderá com um meme aleatório \n" +
                    " $join - O bot entra no canal de voz \n" +
                    "```").queue();
        }
        // Comando "insultar": O bot responderá com uma série de insultos aleatórios para o usuário que solicitou o comando.
        if (raw.equalsIgnoreCase(prefix + "esculachar")) {
            Random random = new Random();
            int randomNum = random.nextInt(5);
            switch (randomNum) {
                case 0:
                    event.getChannel().sendMessage("VOCÊ É UM BOSTA! \uD83D\uDE0C").queue();
                    break;
                case 1:
                    event.getChannel().sendMessage("VOCÊ É UM MERDA! \uD83D\uDE0C").queue();
                    break;
                case 2:
                    event.getChannel().sendMessage("VOCÊ É UM FILHO DA PUTA! \uD83D\uDE0C").queue();
                    break;
                case 3:
                    event.getChannel().sendMessage("VOCÊ É UM CÚ! \uD83D\uDE0C").queue();
                    break;
                case 4:
                    event.getChannel().sendMessage("VOCÊ É UM BURRO! \uD83D\uDE0C").queue();
                    break;
            }
        }


        // Comando "meme": O bot enviará um meme aleatório relacionado ao tema escolhido pelo usuário.
        if (raw.equalsIgnoreCase(prefix + "meme")) {
            // faz uma requisição para uma API de memes para obter um meme aleatório
            try {
                URL url = new URL("https://api.imgflip.com/get_memes");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");

                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuffer content = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }
                in.close();

                // parseia a resposta da API e seleciona um meme aleatório
                ObjectMapper mapper = new ObjectMapper();
                JsonNode root = mapper.readTree(content.toString());
                JsonNode data = Optional.ofNullable(root.path("data")).orElse(null);
                if(data != null) {
                    // use REGEX e extrai do objeto JSON os dados do meme somente a url da imagem e o texto do meme
                    // {"success":true,"data":{"memes":[{"id":"181913649","name":"Drake Hotline Bling","url":"https:\/\/i.imgflip.com\/30b1gx.jpg","width":1200,"height":1200,"box_count":2,"captions":0},{"id":"87743020","name":"Two Buttons","url":"https:\/\/i.imgflip.com\/1g8my4.jpg","width":600,"height":908,"box_count":3,"captions":0}
                    String memes = data.path("memes").toString();
                    String[] memesArray = memes.split("\\},\\{");
                    String meme = memesArray[new Random().nextInt(memesArray.length)];
                    // regex para pegar a url {"success":true,"data":{"memes":[{"id":"181913649","name":"Drake Hotline Bling","url":"https:\/\/i.imgflip.com\/30b1gx.jpg","width":1200,"height":1200,"box_count":2,"captions":0}
                    String memeUrl = meme.substring(meme.indexOf("\"url\":\"") + 7, meme.indexOf("\",\"width\""));
                    String memeText = meme.split(",")[1].split(":")[1].replace("\"", "");
                    // para cada memeUrl extraido coloque num hashmap para evitar que o bot envie o mesmo meme duas vezes
                    HashMap<String, String> memesMap = new HashMap<>();
                    memesMap.put(memeUrl, memeText);

                    String memeUrlToSend = memesMap.keySet().stream().findFirst().get();
                    String memeTextToSend = memesMap.get(memeUrlToSend);
                    // converta a url da imagem para um objeto File e envie para o canal
                    URL memeUrlObj = new URL(memeUrlToSend);
                    BufferedImage image = ImageIO.read(memeUrlObj);
                    File memeFile = new File("meme.jpg");
                    ImageIO.write(image, "jpg", memeFile);
                    event.getChannel().sendFile(memeFile).queue();
                    event.getChannel().sendMessage(memeTextToSend).queue();
                }
            } catch (IOException e) {
                event.getChannel().sendMessage("Ocorreu um erro ao tentar obter um meme. Por favor, tente novamente mais tarde.").queue();
            }
        }

        if (raw.equalsIgnoreCase(prefix + "info")) {
            event.getChannel().sendMessage("```" +
                    "Bot criado por: " + Config.get("AUTHOR") + "\n" +
                    "```").queue();
        }

    }
}
