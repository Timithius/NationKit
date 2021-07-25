package io.nationkit.events.playerToPlayer;

import io.nationkit.files.nationsConfig;
import io.nationkit.files.playersConfig;
import io.nationkit.operator;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class playerKill implements Listener {
    private operator plugin;
    private nationsConfig data;
    private playersConfig data1;

    @EventHandler
    public void playerKill(PlayerDeathEvent e){
        this.data = new nationsConfig(plugin.getPlugin(operator.class));
        this.data1 = new playersConfig(plugin.getPlugin(operator.class));
        Player slayer = e.getEntity().getKiller();
        Player victim = e.getEntity();

        if(slayer != null){
            Boolean slayerHasNation = data1.getConfig().getBoolean("players." + slayer.getUniqueId().toString() + ".hasNation");
            Boolean vicitimHasNation = data1.getConfig().getBoolean("players." + victim.getUniqueId().toString() + ".hasNation");
            Boolean slayerIsOwner = data1.getConfig().getBoolean("players." + slayer.getUniqueId().toString() + ".isOwner");
            Boolean victimIsOwner = data1.getConfig().getBoolean("players." + victim.getUniqueId().toString() + ".isOwner");
            String slayerNation = data1.getConfig().getString("players." + slayer.getUniqueId().toString() + ".nation");
            String victimNation = data1.getConfig().getString("players." + victim.getUniqueId().toString() + ".nation");
            int slayerXP = data.getConfig().getInt("nations." + slayerNation + ".xp");
            int victimXP = data.getConfig().getInt("nations." + victimNation + ".xp");
            int slayerPower = data.getConfig().getInt("nations." + slayerNation + ".power");
            int victimPower = data.getConfig().getInt("nations." + victimNation + ".power");
            int slayerScore = data.getConfig().getInt("nations." + slayerNation + ".score");
            int victimScore = data.getConfig().getInt("nations." + victimNation + ".score");
            Location slayerLocation = slayer.getLocation();

            if(slayerHasNation){
                double chanceOfXP = Math.random();
                double xpEarnedChance = Math.random();
                int xpModifier = 0;

                if(xpEarnedChance < 0.50){
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
                    int powerEarned = 1;
                    int scoreEarned = 25;

                    if(slayerIsOwner){
                        xpEarned = xpEarned + 25;
                        powerEarned = 2;
                        scoreEarned = 50;
                    }
                    if(vicitimHasNation){
                        xpEarned = xpEarned + 50;
                        powerEarned = 3;
                        scoreEarned = 100;
                    }

                    data.getConfig().set("nations." + slayerNation + ".xp", slayerXP + xpEarned);
                    data.getConfig().set("nations." + slayerNation + ".power", slayerPower + powerEarned);
                    data.getConfig().set("nations." + slayerNation + ".score", slayerScore + scoreEarned);
                    data.saveConfig();
                    slayer.playSound(slayerLocation, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 10, 1);
                    slayer.playSound(slayerLocation, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 10, 50);
                    slayer.sendMessage(ChatColor.GOLD + "" + ChatColor.ITALIC + slayerNation + ChatColor.RESET + "" + ChatColor.GREEN + "+" + xpEarned + " XP");
                    slayer.sendMessage(ChatColor.GOLD + "" + ChatColor.ITALIC + slayerNation + ChatColor.RESET + "" + ChatColor.GREEN + "+" + powerEarned + " power");
                    slayer.sendMessage(ChatColor.GOLD + "" + ChatColor.ITALIC + slayerNation + ChatColor.RESET + "" + ChatColor.GREEN + "+" + scoreEarned + " prestige");
                }
            }
            if(vicitimHasNation){
               int xpLost = 25;
               int powerLost = 1;
               int scoreLost = 10;

               if(slayerHasNation){
                    xpLost = 50;
                    powerLost = 2;
               }
               if(slayerIsOwner){
                   xpLost = 100;
                   powerLost = 3;
               }

               if(xpLost >= 100 && victimXP <= 100){
                  data.getConfig().set("nations." + victimNation + ".xp", 0);
                  data.saveConfig();
               }else{
                   data.getConfig().set("nations." + victimNation + ".xp", victimXP - xpLost);
                   data.saveConfig();
               }
               if(victimPower - powerLost < 1){
                   data.getConfig().set("nations." + victimNation + ".power", 1);
                   data.saveConfig();
               }else{
                   data.getConfig().set("nations." + victimNation + ".power", victimPower - powerLost);
                   data.saveConfig();
               }

               data.getConfig().set("nations." + victimNation + ".xp", victimXP - xpLost);
               data.getConfig().set("nations." + victimNation + ".power", victimPower - powerLost);
               data.getConfig().set("nations." + victimNation + ".score", victimScore - scoreLost);
               data.saveConfig();
               victim.sendMessage(ChatColor.GOLD + "" + ChatColor.ITALIC + victimNation + ChatColor.RESET + "" + ChatColor.RED + "-" + xpLost + " XP");
               victim.sendMessage(ChatColor.GOLD + "" + ChatColor.ITALIC + victimNation + ChatColor.RESET + "" + ChatColor.RED + "-" + powerLost + " power");
               victim.sendMessage(ChatColor.GOLD + "" + ChatColor.ITALIC + victimNation + ChatColor.RESET + "" + ChatColor.RED + "-" + scoreLost + " prestige");
            }
        }
    }
}
