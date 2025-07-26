package com.carrotguy69.commands;

import com.carrotguy69.Main;
import com.carrotguy69.utils.ReadableTime;
import com.carrotguy69.utils.TimeString;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

import static com.carrotguy69.Main.f;
import static com.carrotguy69.utils.ReadableTime.convert;

public class MuteCommand implements CommandExecutor {
    private final Main main;

    public MuteCommand(Main main) {this.main = main;}


    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {

        if (!(sender.hasPermission("survival.helper") || sender.hasPermission("survival.mod") || sender.isOp())) {
            sender.sendMessage(f("&cYou cannot use this command."));
            return true;
        }

        if (args.length == 0) /* there are 0 args! return! */ {
            sender.sendMessage(f("&cPlease specify a player!"));
            return true;
        }

        Player p = Bukkit.getPlayer(args[0]);
        if (args.length == 1) {
            if (p != null) {
                int duration = 600;
                main.setMuted(p, duration);
                p.sendMessage(f("&cYou were muted by a moderator! You cannot use chat until your mute expires in %duration%.".replace("%duration%", ReadableTime.convert(duration))));
                main.staffAlert("&l%player% &r&cwas muted for &l%duration% &r&cby &l%moderator%&r&c!".replace("%player%", p.getDisplayName()).replace("%duration%", ReadableTime.convert(duration)).replace("%moderator%", sender.getName()));
            }
            else {
                sender.sendMessage(f("&cThat player is not online!"));
            }
        }


        else {
            int duration = TimeString.convert(args[1]);

//            if (args.length > 2) {
//                List<String> r = Arrays.asList(args);
//                r.subList(2, r.size());
//                String reason = String.join(" ", r);
//            }

            if (p != null) {
                main.setMuted(p, duration);
                p.sendMessage(f("&cYou were muted by a moderator! You cannot use chat until your mute expires in %duration%.".replace("%duration%", ReadableTime.convert(duration))));
                main.staffAlert("&l%player% &r&cwas muted for &l%duration% &r&cby &l%moderator%&r&c!".replace("%player%", p.getDisplayName()).replace("%duration%", ReadableTime.convert(duration)).replace("%moderator%", sender.getName()));
            }
            else {
                sender.sendMessage(f("&cThat player is not online!"));
            }
        }
        return true;
    }
}
