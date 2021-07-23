package io.nationkit.events.playerToEnviroment;

import io.nationkit.files.nationsConfig;
import io.nationkit.files.playersConfig;
import io.nationkit.operator;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import java.util.Date;
import java.util.Random;

public class mobKill implements Listener {
    private operator plugin;
    private nationsConfig data;
    private playersConfig data1;

    @EventHandler
    public void gainXP(EntityDeathEvent e){
        this.data = new nationsConfig(plugin.getPlugin(operator.class));
        this.data1 = new playersConfig(plugin.getPlugin(operator.class));

        if(e.getEntity().getKiller() != null){
            Player slayer = e.getEntity().getKiller();
            Location slayerLocation = slayer.getLocation();
            Entity victim = e.getEntity();
            Boolean hasNation = data1.getConfig().getBoolean("players." + slayer.getUniqueId().toString() + ".hasNation");
            String name = data1.getConfig().getString("players." + slayer.getUniqueId().toString() + ".nation");
            int xp = data.getConfig().getInt("nations." + name + ".xp");

            if(hasNation){
                if(victim.getType().equals(EntityType.CHICKEN)){
                    Long cooldownStart = System.currentTimeMillis();
                    double chanceOfXP = Math.random();
                    double xpEarnedChance = Math.random();
                    int xpModifier = 0;

                    if(xpEarnedChance < 0.5){
                        if(xpEarnedChance < 0.25){
                            if(xpEarnedChance < 0.15){
                                xpModifier = 3;
                            }else{
                                xpModifier = 2;
                            }
                        }else{
                            xpModifier = 1;
                        }
                    }

                    if(chanceOfXP < 0.15  && System.currentTimeMillis() - cooldownStart > 10000){
                        int xpEarned = 1 + xpModifier;
                        slayer.playSound(slayerLocation, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 10, 1);
                        slayer.playSound(slayerLocation, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 10, 50);

                        slayer.sendMessage(ChatColor.GRAY + "Earned " + ChatColor.AQUA + xpEarned + " XP" + ChatColor.GRAY + " for " + ChatColor.GOLD + name);
                        data.getConfig().set("nations." + name + ".xp", xp + xpEarned);
                        data.saveConfig();
                    }
                }
            }
        }
    }
}
