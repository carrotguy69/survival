package com.carrotguy69.commands;

import com.carrotguy69.utils.ReadableTime;
import com.carrotguy69.utils.TimeString;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static com.carrotguy69.Main.f;

public class InvSeeCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {

        if (args.length == 0) {
            sender.sendMessage(f("&cCould not find player!"));
            return true;
        }

        if (!sender.isOp()) {
            return true;
        }

        Player player = Bukkit.getPlayer(args[0]);

        if (player == null) {
            sender.sendMessage(f("&cCould not find player!"));
            return true;
        }

        sender.sendMessage(f(String.format("&aYou are viewing %s's inventory.", player.getName())));
        ((Player) sender).openInventory(player.getInventory());
        return true;
    }

}
