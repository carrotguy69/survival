package com.carrotguy69.commands;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static com.carrotguy69.Main.f;

public class DiscordCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender player, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if (!(player instanceof Player)) {
            Bukkit.getLogger().info("https://discord.gg/bTvtZCd7KH");
            return true;
        }

        Player p = (Player) player;

        TextComponent message = new TextComponent(f("&6Discord server: https://discord.gg/bTvtZCd7KH"));
        message.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://discord.gg/bTvtZCd7KH"));
        message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(f("&6Join our discord!")).create()));
        p.spigot().sendMessage(message);
        return true;

    }
}
