package io.nationkit.events.nationsMenu;

import io.nationkit.ui.nationsMenu.mainMenu;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class helpMenuClick implements Listener {
    private mainMenu mainMenu = new mainMenu();

    @EventHandler
    public void helpMenuClick(InventoryClickEvent e){
        Player player = (Player) e.getWhoClicked();

        if(e.getView().getTitle().equalsIgnoreCase("Coming Soon")){
            if(e.getCurrentItem() != null){
                if(e.getCurrentItem().getType().equals(Material.BARRIER)){
                    mainMenu.mainMenu(player);
                }
            }

            e.setCancelled(true);
        }
    }
}
