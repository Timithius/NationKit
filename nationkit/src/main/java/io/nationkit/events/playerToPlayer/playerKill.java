package io.nationkit.events.playerToPlayer;

import io.nationkit.files.nationsConfig;
import io.nationkit.files.playersConfig;
import io.nationkit.operator;
import net.kyori.adventure.audience.MessageType;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.Random;

public class playerKill implements Listener {
    private operator plugin;
    private nationsConfig data;
    private playersConfig data1;

    @EventHandler
    public void playerKill(PlayerDeathEvent e){
        Player slayer = e.getEntity().getKiller();
        Player victim = e.getEntity();
        this.data = new nationsConfig(plugin.getPlugin(operator.class));
        this.data1 = new playersConfig(plugin.getPlugin(operator.class));

        if(slayer != null){
            Boolean slayerHasNation = data1.getConfig().getBoolean("players." + slayer.getUniqueId().toString() + ".hasNation");
            Boolean vicitimHasNation = data1.getConfig().getBoolean("players." + victim.getUniqueId().toString() + ".hasNation");
            Boolean slayerIsOwner = data1.getConfig().getBoolean("players." + slayer.getUniqueId().toString() + ".isOwner");
            Boolean victimIsOwner = data1.getConfig().getBoolean("players." + victim.getUniqueId().toString() + ".isOwner");
            String slayerNation = data1.getConfig().getString("players." + slayer.getUniqueId().toString() + ".nation");
            String vicitimNation = data1.getConfig().getString("players." + victim.getUniqueId().toString() + ".nation");
            int slayerXP = data.getConfig().getInt("nations." + slayerNation + ".xp");
            int victimXP = data.getConfig().getInt("nations." + vicitimNation + ".xp");
            int slayerPower = data.getConfig().getInt("nations." + slayerNation + ".power");
            int victimPower = data.getConfig().getInt("nations." + vicitimNation + ".power");
            Location slayerLocation = slayer.getLocation();

            if(slayerHasNation){
                double chanceOfXP = Math.random();
                double xpEarnedChance = Math.random();
                int xpModifier = 0;

                if(xpEarnedChance < 0.5){
                    if(xpEarnedChance < 0.25){
                        if(xpEarnedChance < 0.15){
                            xpModifier = 120;
                        }else{
                            xpModifier = 50;
                        }
                    }else{
                        xpModifier = 15;
                    }
                }

                if(chanceOfXP < 0.50){
                    int xpEarned = 50 + xpModifier;
                    if(slayerIsOwner){
                        xpEarned = xpEarned + 25;
                    }
                    slayer.playSound(slayerLocation, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 10, 1);
                    slayer.playSound(slayerLocation, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 10, 50);

                    slayer.sendMessage(ChatColor.GRAY + "Earned " + ChatColor.AQUA + xpEarned + " XP" + ChatColor.GRAY + " for " + ChatColor.GOLD + slayerNation);
                    data.getConfig().set("nations." + slayerNation + ".xp", slayerXP + xpEarned);
                    data.saveConfig();
                }
            }
            if(vicitimHasNation){
                if(victimIsOwner){
                    int xpLost = 100;
                    int powerLost = 1;
                    if(slayerIsOwner){
                        xpLost = xpLost + 50;
                        powerLost = powerLost + 1;
                    }
                    if(victimXP < 100){
                        data.getConfig().set("nations." + vicitimNation + ".xp", 0);
                        data.saveConfig();
                    }else{
                        data.getConfig().set("nations." + vicitimNation + ".xp", victimXP - xpLost);
                        data.saveConfig();
                    }
                    if(victimPower == 1){
                        data.getConfig().set("nations." + vicitimNation + ".power", 1);
                        data.saveConfig();
                    }else{
                        data.getConfig().set("nations." + vicitimNation + ".power", victimPower - powerLost);
                        data.saveConfig();
                    }

                    victim.sendMessage(ChatColor.GRAY + "You were slain by " + ChatColor.GOLD + slayer.getDisplayName() + ChatColor.GRAY + "." + ChatColor.GOLD + vicitimNation + ChatColor.GRAY + " has lost " + ChatColor.AQUA + xpLost + " XP" + ChatColor.GRAY + " and " +
                            ChatColor.AQUA + powerLost + " Power");
                    data.getConfig().set("nations." + vicitimNation + ".xp", victimXP - xpLost);
                    data.saveConfig();
                }
            }
        }
    }
}
