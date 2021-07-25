package io.nationkit.events.nationsMenu;

import io.nationkit.files.nationsConfig;
import io.nationkit.files.playersConfig;
import io.nationkit.files.tempConfig;
import io.nationkit.operator;
import io.nationkit.ui.nationsMenu.memberProfileMenu;
import io.nationkit.ui.nationsMenu.nationManagerMenu;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.UUID;

public class membersMenuClick implements Listener {
    private nationManagerMenu nationManagerMenu = new nationManagerMenu();
    private memberProfileMenu memberProfileMenu = new memberProfileMenu();
    private operator plugin;
    private nationsConfig data;
    private playersConfig data1;
    private tempConfig data2;

    @EventHandler
    public void membersMenuClick(InventoryClickEvent e){
        this.data1 = new playersConfig(plugin.getPlugin(operator.class));
        this.data2 = new tempConfig(plugin.getPlugin(operator.class));
        Player player = (Player) e.getWhoClicked();

        if(e.getView().getTitle().equalsIgnoreCase("Nation Citizens")){
            if(e.getCurrentItem() != null){
                if(e.getCurrentItem().getType().equals(Material.IRON_NUGGET)){
                    String name = data1.getConfig().getString("players." + player.getUniqueId().toString() + ".nation");
                    data2.getConfig().set("nations." + name + ".memberNamesToUUID", null);
                    data2.saveConfig();

                    nationManagerMenu.nationManagerMenu(player);
                }
                if(e.getCurrentItem().getType().equals(Material.PLAYER_HEAD)){
                    String targetDisplayName = e.getCurrentItem().getItemMeta().getDisplayName();
                    String name = data1.getConfig().getString("players." + player.getUniqueId().toString() + ".nation");
                    String targetUUIDString = data2.getConfig().getString("nations." + name + ".memberNamesToUUID." + ChatColor.stripColor(targetDisplayName));
                    UUID targetUUID = UUID.fromString(targetUUIDString);

                    memberProfileMenu.memberProfileMenu(player, targetUUID);

                    data2.getConfig().set("nations." + name + ".memberNamesToUUID", null);
                    data2.saveConfig();
                }
            }

            e.setCancelled(true);
        }
    }
}
