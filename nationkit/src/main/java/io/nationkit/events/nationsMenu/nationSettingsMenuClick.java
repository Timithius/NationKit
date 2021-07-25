package io.nationkit.events.nationsMenu;

import io.nationkit.files.nationsConfig;
import io.nationkit.files.playersConfig;
import io.nationkit.operator;
import io.nationkit.ui.nationsMenu.nationManagerMenu;
import io.nationkit.ui.nationsMenu.nationSettingsMenu;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class nationSettingsMenuClick implements Listener {
    private nationManagerMenu nationManagerMenu = new nationManagerMenu();
    private nationSettingsMenu nationSettingsMenu = new nationSettingsMenu();
    private operator plugin;
    private nationsConfig data;
    private playersConfig data1;

    @EventHandler
    public void nationSettingsMenuClick(InventoryClickEvent e){
        if(e.getView().getTitle().equalsIgnoreCase("Nation Settings")){
            if(e.getWhoClicked() instanceof Player){
                this.data = new nationsConfig(plugin.getPlugin(operator.class));
                this.data1 = new playersConfig(plugin.getPlugin(operator.class));
                Player player = (Player) e.getWhoClicked();
                String name = data1.getConfig().getString("players." + player.getUniqueId().toString() + ".nation");
                ItemStack nationBanner = data.getConfig().getItemStack("nations." + name + ".banner.material");
                Material nationBannerMaterial = nationBanner.getType();

                if(e.getCurrentItem().getType().equals(Material.IRON_NUGGET)){
                    nationManagerMenu.nationManagerMenu(player);
                }
                if(e.getCurrentItem().getType().equals(Material.OAK_SIGN)){
                    player.closeInventory();
                    player.sendMessage(ChatColor.GRAY + "Usage: /nation name <newname>, ex. " +ChatColor.GOLD + "/nation name New Nation Name");
                }
                if(e.getCurrentItem().getType().equals(nationBannerMaterial)){
                    ItemStack itemInMainHand = player.getInventory().getItemInMainHand();

                    player.closeInventory();

                    if(itemInMainHand.getType().equals(Material.BLACK_BANNER) || itemInMainHand.getType().equals(Material.WHITE_BANNER) || itemInMainHand.getType().equals(Material.BROWN_BANNER) ||
                            itemInMainHand.getType().equals(Material.BLUE_BANNER) || itemInMainHand.getType().equals(Material.PURPLE_BANNER) || itemInMainHand.getType().equals(Material.CYAN_BANNER) || itemInMainHand.getType().equals(Material.GRAY_BANNER) ||
                            itemInMainHand.getType().equals(Material.GREEN_BANNER) || itemInMainHand.getType().equals(Material.LIGHT_BLUE_BANNER) || itemInMainHand.getType().equals(Material.LIGHT_GRAY_BANNER) || itemInMainHand.getType().equals(Material.LIME_BANNER) ||
                            itemInMainHand.getType().equals(Material.MAGENTA_BANNER) || itemInMainHand.getType().equals(Material.ORANGE_BANNER) || itemInMainHand.getType().equals(Material.PINK_BANNER) || itemInMainHand.getType().equals(Material.RED_BANNER) ||
                            itemInMainHand.getType().equals(Material.YELLOW_BANNER)){
                        data.getConfig().set("nations." + name + ".banner.material", itemInMainHand);
                        data.saveConfig();

                        player.sendMessage(ChatColor.GRAY + "New banner for " + ChatColor.GOLD +
                                name + ChatColor.AQUA + " set!");
                    }else{
                        player.sendMessage(ChatColor.GRAY + "Hold a banner in your hand to set it as the new nation banner.");
                    }
                }
                if(e.getCurrentItem().getType().equals(Material.LIME_DYE)){
                    data.getConfig().set("nations." + name + ".privacySetting", "limited");
                    data.saveConfig();

                    nationSettingsMenu.nationSettingsMenu(player);
                }
                if(e.getCurrentItem().getType().equals(Material.PINK_DYE)){
                    data.getConfig().set("nations." + name + ".privacySetting", "closed");
                    data.saveConfig();

                    nationSettingsMenu.nationSettingsMenu(player);
                }
                if(e.getCurrentItem().getType().equals(Material.GRAY_DYE)){
                    data.getConfig().set("nations." + name + ".privacySetting", "open");
                    data.saveConfig();

                    nationSettingsMenu.nationSettingsMenu(player);
                }
            }

            e.setCancelled(true);
        }
    }
}
