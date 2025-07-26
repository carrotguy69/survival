package com.carrotguy69.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static com.carrotguy69.Main.f;


public class BroadcastCommand implements CommandExecutor {

    boolean console = false;
    Player p = null;

    @Override
    public boolean onCommand(@NotNull CommandSender player, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if (!(player instanceof Player)) {
            console = true;
        }

        else {
            p = (Player) player;

            if (!(p.isOp())) {
                p.sendMessage(f("&cYou cannot use this command."));
                return true;
            }

            if (p.isOp()) {
                Bukkit.broadcastMessage(f(String.join(" ", args)));
            }
        }


        if (console) {
            Bukkit.broadcastMessage(f(String.join(" ", args)));
        }

        return true;

    }
}
