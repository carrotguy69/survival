package com.carrotguy69;

import com.carrotguy69.commands.*;
import com.carrotguy69.utils.ReadableTime;
import org.bukkit.*;
import org.bukkit.advancement.AdvancementDisplay;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.event.player.*;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import javax.security.auth.login.LoginException;
import java.util.*;


public final class Main extends JavaPlugin implements Listener {
    public static Main instance;

    @Override
    public void onEnable() {
        getLogger().info("Plugin Enabled!!!1");

        this.getCommand("broadcast").setExecutor(new BroadcastCommand());
        this.getCommand("discord").setExecutor(new DiscordCommand());
        this.getCommand("vanish").setExecutor(new VanishCommand(this));
        this.getCommand("unvanish").setExecutor(new UnvanishCommand(this));
        this.getCommand("mute").setExecutor(new MuteCommand(this));
        this.getCommand("unmute").setExecutor(new UnmuteCommand(this));
        this.getCommand("nickname").setExecutor(new NicknameCommand(this));
        this.getCommand("removenickname").setExecutor(new RemoveNicknameCommand(this));
        this.getCommand("fullbright").setExecutor(new FakeFullbrightCommand());
        this.getCommand("invsee").setExecutor(new InvSeeCommand());
        this.getCommand("echest").setExecutor(new EnderChestCommand());
        getServer().getPluginManager().registerEvents(this, this);


        try {
            new Discord(this).Discord();
        }
        catch (LoginException e) {
            throw new RuntimeException(e);
        }

        new BukkitRunnable() {public void run () {
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (!isMuted(p)) {
                    setMuted(p, 0);
                }
            }
//            updateTab();
        }}.runTaskTimer(this, 0L, 20L);

    }

    @Override
    public void onDisable() {
        getLogger().info("Plugin Disabled!");
        // Plugin shutdown logic
    }

    public boolean isVanished(Player p) {
        /* No support for booleans in persistent data, so we will use integers and return with a boolean instead. */
        NamespacedKey key = new NamespacedKey(this, "vanished");
        PersistentDataContainer data = p.getPersistentDataContainer();

        if (!data.has(key, PersistentDataType.INTEGER)) {
            data.set(key, PersistentDataType.INTEGER, 0);
            return false;
        }

        int vanished = data.get(key, PersistentDataType.INTEGER);

        return vanished != 0;
    }

    public void setVanished(Player p, boolean value) {
        NamespacedKey key = new NamespacedKey(this, "vanished");
        PersistentDataContainer data = p.getPersistentDataContainer();

        int val = 0;
        if (value) {val = 1;}

        data.set(key, PersistentDataType.INTEGER, val);
    }

    public boolean isMuted(Player p) {
        NamespacedKey key = new NamespacedKey(this, "muted");
        PersistentDataContainer data = p.getPersistentDataContainer();

        if (!data.has(key, PersistentDataType.LONG)) {
            data.set(key, PersistentDataType.LONG, 0L);
            return false;
        }

        long muted = data.get(key, PersistentDataType.LONG);
        long now = System.currentTimeMillis() / 1000;

        if (muted == 0) {
            return false;
        }

        if (muted <= now) { // this checks if a mute has expired
            data.set(key, PersistentDataType.LONG, 0L);
            return false;
        }
        return true;
    }

    public void setMuted(Player p, int duration) {
        NamespacedKey key = new NamespacedKey(this, "muted");
        PersistentDataContainer data = p.getPersistentDataContainer();
        long now = System.currentTimeMillis() / 1000;

        if (duration == 0) {
            data.set(key, PersistentDataType.LONG, 0L);
            return;
        }
        data.set(key, PersistentDataType.LONG, now + duration);
    }

    public void setNick(Player p, String name) {
        NamespacedKey key = new NamespacedKey(this, "nickname");
        PersistentDataContainer data = p.getPersistentDataContainer();

        data.set(key, PersistentDataType.STRING, name);

        p.setDisplayName(name);
    }

    public String getNick(Player p) {
        NamespacedKey key = new NamespacedKey(this, "nickname");
        PersistentDataContainer data = p.getPersistentDataContainer();

        return data.get(key, PersistentDataType.STRING);
    }

    public static String f(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    public void staffAlert(String message) {
        Webhook.staff(ChatColor.stripColor(f(message)));
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (p.hasPermission("survival.helper") || p.hasPermission("survival.moderator") || p.hasPermission("survival.admin") || p.hasPermission("survival.owner")) {
                p.sendMessage(f(message));
            }
        }
    }

    @EventHandler
    public void join_event(PlayerJoinEvent e) {
        e.setJoinMessage("");
        if (!isVanished(e.getPlayer())) {
            on_join(e.getPlayer());
        }
        else {
            e.getPlayer().sendMessage(f("&dYou joined the server in vanish!"));
        }
    }

    @EventHandler
    public void leave_event(PlayerQuitEvent e) {
        e.setQuitMessage("");
        if (!isVanished(e.getPlayer())) {
            on_leave(e.getPlayer());
        }
    }

    @EventHandler
    public void onAnvilPrepare(PrepareAnvilEvent event) {
        AnvilInventory anvil = event.getInventory();

        // Ensure a result exists
        if (event.getResult() == null) return;

        // Force the repair cost to never exceed 39 (prevents "Too Expensive!")
        Bukkit.getScheduler().runTaskLater(this, () -> {
            if (anvil.getRepairCost() > 39) {
                anvil.setRepairCost(39);
            }
        }, 1L);
    }



    @EventHandler
    public void interact(PlayerInteractEvent e) {
        Player p = e.getPlayer();

        if (e.getAction() == Action.RIGHT_CLICK_BLOCK && e.getClickedBlock() != null && p.getInventory().getItemInMainHand().toString().toLowerCase().contains("axe")) {
            String blockname = e.getClickedBlock().getType().toString().toLowerCase();
            if (blockname.contains("stripped_")) {
                BlockFace blockFace = e.getBlockFace();

                e.getClickedBlock().setType(Material.valueOf(blockname.replace("stripped_", "").toUpperCase()));
                e.getClickedBlock().getLocation().getWorld().playSound(e.getClickedBlock().getLocation(), Sound.ITEM_AXE_STRIP, 1.0f, 2.0f);

                try {
                    BlockData data = e.getClickedBlock().getBlockData();
                    if (data instanceof Directional) {
                        ((Directional) data).setFacing(blockFace);
                        e.getClickedBlock().setBlockData(data);
                    }
                }

                catch (Exception ex) {
                    getLogger().severe("ex stripped:" + ex);

                }
            }

        }
    }

//    @EventHandler
//    public void cancel_end(PlayerPortalEvent e) {
//        Block portalBlock = e.getFrom().getBlock();
//        if (portalBlock.getType() == Material.END_PORTAL) {
//            e.setCancelled(true); // Cancel the event
//            e.getPlayer().sendMessage(f("&cThe end is locked!"));
//        }
//    }

    public void on_join(Player p) {

        if (getNick(p) != null) {
            setNick(p, getNick(p));
        }

        if (p.hasPlayedBefore()) {
            Bukkit.broadcastMessage(f("&6&l" + p.getDisplayName() + " has joined the game."));
            Webhook.main("", "**%player%** has joined the game.".replace("%player%", p.getDisplayName()), 0x5aef40);
        }
        else {
            Bukkit.broadcastMessage(f("&6&l" + p.getDisplayName() + " has joined the game for the first time!"));
            Webhook.main("", "**%player%** has joined the game for the first time.".replace("%player%", p.getDisplayName()), 0x0ff3ff);
        }
    }

    public void on_leave(Player p) {
        Bukkit.broadcastMessage(f("&6&l" + p.getDisplayName() + " &ehas left the game."));
        Webhook.main("", "**%player%** has left the game.".replace("%player%", p.getDisplayName()), 0xfc4b4b);
    }

    @EventHandler
    public void server_list(ServerListPingEvent e) {
        int players = Bukkit.getOnlinePlayers().size();
        if (players > 0) {
            e.setMaxPlayers(Bukkit.getOnlinePlayers().size() + 1);
            return;
        }
        e.setMaxPlayers(69);

    }

    @EventHandler
    public void on_chat(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();
        String m = e.getMessage();


        if (m.toLowerCase().contains("nigger") || m.toLowerCase().contains("nigg") || m.toLowerCase().contains("fag") || m.toLowerCase().contains("n1g")) {
            setMuted(p, 86400);
            p.sendMessage(f("&cYou were muted by a moderator! You cannot use chat until your mute expires in 1 day."));
            e.setCancelled(true);
            return;
        }

        if (isMuted(p)) {
            p.sendMessage(f("&cYou are muted!"));
            e.setCancelled(true);
            return;
        }

        if (m.toLowerCase().contains("@everyone") || m.toLowerCase().contains("@here") || m.toLowerCase().contains("@&")) {
            p.sendMessage(f("&cPinging roles is disabled!"));
            e.setCancelled(true);
            return;
        }

        m = f(m);

        e.setCancelled(true);
        Bukkit.broadcastMessage(f("&f" + p.getDisplayName() + ": &r&f" + m));
        Webhook.main("**" + p.getDisplayName() + "**: " + ChatColor.stripColor(m));
    }

    @EventHandler
    public void on_death(PlayerDeathEvent e) {
        Player p = e.getEntity();
        Block b = p.getLocation().getBlock();
        String x = String.valueOf(b.getX());
        String y = String.valueOf(b.getY());
        String z = String.valueOf(b.getZ());

        p.sendMessage(f("&cYou died at x, y, z".replace("x", x).replace("y", y).replace("z", z)));

        String msg = e.getDeathMessage();
        Webhook.main("", msg, 0xba0303);
    }

    @EventHandler
    public void on_achievement(PlayerAdvancementDoneEvent e) {
        Player p = e.getPlayer();
        AdvancementDisplay advancement = e.getAdvancement().getDisplay();
        if (advancement == null) {
            return;
        }

        Webhook.main("", "**" + p.getDisplayName() + "** has made the advancement **" + advancement.getTitle() + "**.", 0xa82fe0);
    }

    @EventHandler
    public void on_break(BlockBreakEvent e) {
        Player p = e.getPlayer();
        Block b = e.getBlock();

        ArrayList<Material> flagged = new ArrayList<>();

        flagged.add(Material.DIAMOND_ORE);
        flagged.add(Material.DEEPSLATE_DIAMOND_ORE);
        flagged.add(Material.ANCIENT_DEBRIS);

        int x = b.getX();
        int y = b.getY();
        int z = b.getZ();

        if (flagged.contains(b.getType())) {
            staffAlert(p.getDisplayName() + " mined " + b.getType().name() + " at coordinates " + x + ", " + y + ", " + z);
        }
    }


}
