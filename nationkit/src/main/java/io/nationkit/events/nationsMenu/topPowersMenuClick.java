package io.nationkit.events.nationsMenu;

import io.nationkit.ui.nationsMenu.mainMenu;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class topPowersMenuClick implements Listener {
    private mainMenu mainMenu = new mainMenu();

    @EventHandler
    public void topPowersMenuClick(InventoryClickEvent e){
        Player player = (Player) e.getWhoClicked();

        if(e.getView().getTitle().equalsIgnoreCase("Top Powers")){
            if(e.getCurrentItem() != null){
                if(e.getCurrentItem().getType().equals(Material.IRON_NUGGET)){
                    mainMenu.mainMenu(player);
                }
                if(e.getCurrentItem().getType().equals(Material.BLACK_BANNER)){
                    e.setCancelled(true);
                }
            }

            e.setCancelled(true);
        }
    }
}
