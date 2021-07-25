package io.nationkit.events.nationsMenu;

import io.nationkit.files.mainConfig;
import io.nationkit.files.nationsConfig;
import io.nationkit.files.playersConfig;
import io.nationkit.operator;
import io.nationkit.ui.nationsMenu.levelUpMenu;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class levelUpMenuClick implements Listener {
    private levelUpMenu levelUpMenu = new levelUpMenu();
    private operator plugin;
    private nationsConfig data;
    private playersConfig data1;
    private mainConfig data2;

    @EventHandler
    public void levelUpMenuClick(InventoryClickEvent e){
        Player player = (Player) e.getWhoClicked();
        this.data = new nationsConfig(plugin.getPlugin(operator.class));
        this.data1 = new playersConfig(plugin.getPlugin(operator.class));
        this.data2 = new mainConfig(plugin.getPlugin(operator.class));

        if(e.getView().getTitle().equalsIgnoreCase("Levels & Technology")){
            if(e.getCurrentItem() != null){
                if(e.getCurrentItem().getType().equals(Material.WRITABLE_BOOK)){
                    player.closeInventory();
                    player.sendMessage(ChatColor.GRAY + "This feature is coming soon, for now try to earn some more " + ChatColor.AQUA + "XP " + "by killing mobs!");
                }
                if(e.getCurrentItem().getType().equals(Material.LECTERN)){
                    player.closeInventory();
                    player.sendMessage(ChatColor.GRAY + "This feature is coming soon, for now try to earn some more " + ChatColor.AQUA + "XP " + "by killing mobs!");
                }
                if(e.getCurrentItem().getType().equals(Material.IRON_NUGGET)){
                    String name = data1.getConfig().getString("players." + player.getUniqueId().toString() + ".nation");
                    int level = data.getConfig().getInt("nations." + name + ".level");
                    int xp = data.getConfig().getInt("nations." + name + ".xp");
                    int neededXP = data2.getConfig().getInt("settings.levelThreshold." + level);
                    String levelUnlocks = data2.getConfig().getString("settings.levelUnlocks." + level);
                    int researchPoints = data.getConfig().getInt("nations." + name + ".rp");


                    levelUpMenu.levelUpMenu(player, level, xp, neededXP, levelUnlocks, researchPoints);
                }
            }

            e.setCancelled(true);
        }
    }
}
