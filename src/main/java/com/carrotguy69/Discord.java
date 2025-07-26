package com.carrotguy69;

import net.dv8tion.jda.api.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.AnnotatedEventManager;
import net.dv8tion.jda.api.hooks.SubscribeEvent;
import org.bukkit.Bukkit;

import javax.security.auth.login.LoginException;
import java.util.EventListener;

import static net.dv8tion.jda.api.JDABuilder.createDefault;

public class Discord implements EventListener {
    private final Main main;

    public Discord(Main main) {this.main = main;}


    public void Discord() throws LoginException {
        JDABuilder builder;
        createDefault("TOKEN")
                .setEventManager(new AnnotatedEventManager())
                .addEventListeners(new Discord(main))
                .setStatus(OnlineStatus.OFFLINE)
                .build();
    }

    @SubscribeEvent
    public void onMessage(MessageReceivedEvent e) throws InterruptedException {
        if (!(e.getAuthor().isBot() || e.getMessage().isWebhookMessage()) && e.getChannel().getId().equals("1061161703892586567")) {
            if (e.getMessage().getContentDisplay().length() > 200) {
                e.getMessage().addReaction("⚠").complete();
            }
            else {
                Bukkit.broadcastMessage("§9§lDISCORD§f§l " + e.getAuthor().getName() + ":§r " + e.getMessage().getContentDisplay().replace("§", ""));
            }
        }
    }

}
