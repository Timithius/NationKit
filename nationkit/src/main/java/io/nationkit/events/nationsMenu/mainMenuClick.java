package io.nationkit.events.nationsMenu;

import io.nationkit.files.nationsConfig;
import io.nationkit.files.playersConfig;
import io.nationkit.operator;
import io.nationkit.ui.nationsMenu.helpMenu;
import io.nationkit.ui.nationsMenu.nationManagerMenu;
import io.nationkit.ui.nationsMenu.topPowersMenu;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class mainMenuClick implements Listener {
    private helpMenu hm = new helpMenu();
    private topPowersMenu tpm = new topPowersMenu();
    private nationManagerMenu nmm = new nationManagerMenu();
    private operator plugin;
    private nationsConfig data;
    private playersConfig data1;

    @EventHandler
    public void mainMenuClick(InventoryClickEvent e){
        this.data = new nationsConfig(plugin.getPlugin(operator.class));
        this.data1 = new playersConfig(plugin.getPlugin(operator.class));
        Player player = (Player) e.getWhoClicked();
        Boolean hasNation = data1.getConfig().getBoolean("players." + player.getUniqueId().toString() + ".hasNation");
        String name = data1.getConfig().getString("players." + player.getUniqueId().toString() + ".nation");
        ItemStack nationBanner = data.getConfig().getItemStack("nations." + name + ".banner.material");

        if(e.getView().getTitle().equalsIgnoreCase("Diplomacy Menu")){
            if(e.getCurrentItem() != null){
                if(e.getCurrentItem().getType().equals(Material.BOOKSHELF)){
                    //tb.thBook(player);
                    player.closeInventory();
                    player.sendMessage(ChatColor.GRAY + "Working on the book. Ask in discord, or try " + ChatColor.GOLD + "/nation help");
                }
                if(e.getCurrentItem().getType().equals(Material.END_CRYSTAL)){
                    tpm.topPowersMenu(player);
                }
                if(e.getCurrentItem().getType().equals(Material.DIAMOND_SWORD)) {
                    hm.helpMenu(player);
                }
                if(e.getCurrentItem().getType().equals(Material.SHIELD)){
                    hm.helpMenu(player);
                }
                if(e.getCurrentItem().getType().equals(Material.GOLD_INGOT)){
                    hm.helpMenu(player);
                }
                if(hasNation){
                    if(e.getCurrentItem().getType().equals(nationBanner.getType())){
                        nmm.nationManagerMenu(player);
                    }
                }else{
                    if(e.getCurrentItem().getType().equals(Material.WHITE_BANNER)){
                        nmm.nationManagerMenu(player);
                    }
                }
                if(e.getCurrentItem().getType().equals(Material.EMERALD)){
                    hm.helpMenu(player);
                }
                if(e.getCurrentItem().getType().equals(Material.PAPER)){
                    hm.helpMenu(player);
                }
                if(e.getCurrentItem().getType().equals(Material.HEART_OF_THE_SEA)){
                    hm.helpMenu(player);
                }
            }

            e.setCancelled(true);
        }
    }
}
