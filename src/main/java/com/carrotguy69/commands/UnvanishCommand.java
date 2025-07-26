package com.carrotguy69.commands;

import com.carrotguy69.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static com.carrotguy69.Main.f;

public class UnvanishCommand implements CommandExecutor {
    private final Main main;

    public UnvanishCommand(Main main) {this.main = main;}

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if (!(sender.isOp())) {
            sender.sendMessage(f("&cYou cannot use this command."));
            return true;
        }

        if (args.length == 0) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(f("&cPlease provide a player."));
                return true;
            }

            else {
                // modify the sending player
                if (main.isVanished((Player) sender)) {
                    main.setVanished((Player) sender, false);
                    main.on_join((Player) sender);
                }
                else {
                    sender.sendMessage(f("&cYou are not vanished!"));
                }
            }
        }

        else {
            Player p = Bukkit.getPlayer(args[0]);
            if (p != null) {
                if (!(p.isOnline())) {
                    sender.sendMessage(f("&cThis player is not online!"));
                    return true;
                }
            }
            else {
                sender.sendMessage(f("&cThis player is not online!"));
                return true;
            }

            if (main.isVanished(p)) {
                main.setVanished(p, false);
                main.on_join(p);
            }
            else {
                sender.sendMessage(f("&cThis player is not vanished!"));
            }
        }

        return true;
    }
}
