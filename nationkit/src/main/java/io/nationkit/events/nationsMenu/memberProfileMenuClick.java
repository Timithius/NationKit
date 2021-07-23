package io.nationkit.events.nationsMenu;

import io.nationkit.ui.nationsMenu.giftMenu;
import io.nationkit.ui.nationsMenu.membersMenu;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;


public class memberProfileMenuClick implements Listener {
    private membersMenu mm = new membersMenu();
    private giftMenu gm = new giftMenu();

    @EventHandler
    public void memberProfileMenuClick(InventoryClickEvent e){
        if(e.getView().getTitle().equalsIgnoreCase("Citizen Profile")){
            if(e.getCurrentItem() != null) {
                Player player = (Player) e.getWhoClicked();
                String targetUUIDString = null;

                for(int i = 0; i < e.getInventory().getSize(); i++){
                    ItemStack itemStack = e.getInventory().getItem(i);

                    if(itemStack.getType().equals(Material.NETHER_GOLD_ORE)){
                        targetUUIDString = ChatColor.stripColor(itemStack.getItemMeta().getDisplayName());
                    }
                }

                UUID target = UUID.fromString(targetUUIDString);
                String targetDisplayName = Bukkit.getOfflinePlayer(target).getName();

                if(e.getCurrentItem().getType().equals(Material.IRON_NUGGET)){
                    mm.membersMenu(player);
                }
                if(e.getCurrentItem().getType().equals(Material.POWERED_RAIL)){
                    Location location = Bukkit.getPlayer(target).getLocation();

                    player.closeInventory();
                    player.teleport(location);
                    player.playSound(location, Sound.BLOCK_COMPOSTER_FILL_SUCCESS, 5, 1);
                    player.playSound(location, Sound.BLOCK_DISPENSER_LAUNCH, 10, 1);
                    player.sendMessage(ChatColor.GRAY + "Taking you to " + ChatColor.YELLOW + targetDisplayName);
                }
                if(e.getCurrentItem().getType().equals(Material.WRITABLE_BOOK)){
                    player.closeInventory();
                    player.sendMessage(ChatColor.GRAY + "Type " + ChatColor.GOLD + "/nation message " + targetDisplayName + ChatColor.GRAY + " with your message at the end.");
                }
                if(e.getCurrentItem().getType().equals(Material.SHULKER_BOX)){
                    //gm.giftMenu(player, Bukkit.getPlayer(target));
                    player.closeInventory();
                    player.sendMessage(ChatColor.GRAY + "Feature coming soon, it's kinda hard to make.");
                }
            }

            e.setCancelled(true);
        }
    }
}
