package com.discord.bot;

import com.discord.bot.bot.MusicCommand;
import com.discord.bot.command.Listener;
import com.discord.bot.command.UserListener;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.security.auth.login.LoginException;

@SpringBootApplication
public class BotApplication {
    private static JDA jda;
    public static void main(String[] args) throws LoginException {
        SpringApplication.run(BotApplication.class, args);
        String token = "NzA3MzMyNTExNjA4NDcxNTcy.Gi2Brn.LWj_dXb6wFWylaNNy9Qqc_4JJrScv8iVo2heHA";

        jda = JDABuilder.createDefault(token)
                .enableIntents(GatewayIntent.GUILD_MEMBERS)
                .enableCache(CacheFlag.VOICE_STATE)
                .enableIntents(GatewayIntent.GUILD_VOICE_STATES)
                .setMemberCachePolicy(MemberCachePolicy.ALL)
                .addEventListeners(new Listener())
                .addEventListeners(new MusicCommand())
                .addEventListeners(new UserListener())
                .setActivity(Activity.playing("$help"))
                .build();
    }

}
