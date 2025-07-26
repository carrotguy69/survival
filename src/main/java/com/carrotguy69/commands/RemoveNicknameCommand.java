package com.carrotguy69.commands;

import com.carrotguy69.Main;
import net.dv8tion.jda.api.interactions.commands.Command;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static com.carrotguy69.Main.f;

public class RemoveNicknameCommand implements CommandExecutor {

    private final Main main;

    public RemoveNicknameCommand(Main main) {this.main = main;}

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull org.bukkit.command.Command cmd, @NotNull String label, @NotNull String[] args) {
        Player player = (Player) sender;

        if (args.length > 0) {
            if (sender.isOp()) {
                player = Bukkit.getPlayer(args[0]);
            }
        }

        if (player == null) {
            sender.sendMessage(f("&cCould not find player!"));
            return true;
        }

        main.setNick(player, player.getName());
        player.sendMessage(f("&aNickname reset."));
        return true;
    }

}
