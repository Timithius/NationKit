package io.nationkit.ui.nationsMenu;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class giftMenu {
    public void giftMenu(Player sender, Player target){
        Inventory inventory = Bukkit.createInventory(null, 18, ChatColor.RED + "Gifts are sent after closing this inventory!");

        ItemStack uuid = new ItemStack(Material.WHITE_STAINED_GLASS_PANE);
        ItemMeta uuid_meta = uuid.getItemMeta();

        uuid_meta.setDisplayName(ChatColor.BLACK + target.getUniqueId().toString());
        uuid.setItemMeta(uuid_meta);

        inventory.setItem(17, uuid);

        sender.openInventory(inventory);
    }
}
