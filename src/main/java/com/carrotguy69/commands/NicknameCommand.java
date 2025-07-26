package com.carrotguy69.commands;

import com.carrotguy69.Main;
import net.dv8tion.jda.api.interactions.commands.Command;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static com.carrotguy69.Main.f;

public class NicknameCommand implements CommandExecutor {

    private final Main main;

    public NicknameCommand(Main main) {this.main = main;}

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull org.bukkit.command.Command cmd, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can set a nickname.");
            return true;
        }

        Player player = (Player) sender;

        if (!player.isOp()) {
            return true;
        }

        if (args.length == 0) {
            player.sendMessage("Usage: /nickname <nickname>");
            return true;
        }

        if (!args[0].matches("[A-Za-z0-9_]{1,16}")) {
            player.sendMessage(f("&cInvalid Nickname!"));
            return true;
        }

        main.setNick(player, args[0]);

        player.sendMessage(f("&aYour nickname has been set to " + args[0]));
        return true;
    }

}
