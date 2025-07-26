package com.carrotguy69.commands;

import com.carrotguy69.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static com.carrotguy69.Main.f;

public class UnmuteCommand implements CommandExecutor {
    private final Main main;

    public UnmuteCommand(Main main) {this.main = main;}


    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if (!(sender.hasPermission("survival.helper") || sender.hasPermission("survival.mod") || sender.isOp())) {
            sender.sendMessage(f("&cYou cannot use this command."));
            return true;
        }

        if (args.length == 0) /* there are 0 args! return! */ {
            sender.sendMessage(f("&cPlease provide a player!"));
        }

        else {
            Player p = Bukkit.getPlayer(args[0]);
            if (p != null) {
                main.setMuted(p, 0);
                main.staffAlert("&c%player% &r&cwas unmuted by &l%moderator%&r&c.".replace("%player%", p.getDisplayName()).replace("%moderator%", sender.getName()));
            }
            else {
                sender.sendMessage(f("&cThis player is not online!"));
            }
        }
        return true;

    }
}
