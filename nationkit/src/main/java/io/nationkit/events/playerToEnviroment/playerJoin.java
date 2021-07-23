package io.nationkit.events.playerToEnviroment;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class playerJoin implements Listener {
    @EventHandler
    public void playerJoin(PlayerJoinEvent e){
        Player player = e.getPlayer();

        player.sendMessage(ChatColor.AQUA + "Welcome!" + ChatColor.GRAY + " This server is running " + ChatColor.GREEN + "NationKit" + ChatColor.GRAY + ", a plugin in pre-aplha.");
        player.sendMessage(ChatColor.RED + "You may lose progress at this stage in development!");
    }
}
