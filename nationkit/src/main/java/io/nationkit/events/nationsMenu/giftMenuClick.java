package io.nationkit.events.nationsMenu;

import io.nationkit.files.giftConfig;
import io.nationkit.operator;
import io.nationkit.ui.nationsMenu.giftMenu;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class giftMenuClick implements Listener {
    private giftMenu gm = new giftMenu();
    private operator plugin;
    private giftConfig data;

    @EventHandler
    public void giftMenuClose(InventoryCloseEvent e){
        if(e.getView().getTitle().equalsIgnoreCase(ChatColor.RED + "Gifts are sent after closing this inventory!")){
            this.data = new giftConfig(plugin.getPlugin(operator.class));
            Player player = (Player) e.getPlayer();
            Inventory inventory = e.getInventory();
            String uuid_str = ChatColor.stripColor(inventory.getItem(17).getItemMeta().getDisplayName());
            List<ItemStack> giftList = new ArrayList<>();

            for(int i = 0; i < inventory.getSize(); i++){
                ItemStack itemStack = inventory.getItem(i);

                giftList.add(itemStack);

                data.getConfig().set("gifts." + uuid_str + "." + player.getUniqueId().toString(), giftList);
                data.saveConfig();
            }

            player.sendMessage(ChatColor.GRAY + "Gifts sent!");
        }
    }
}
