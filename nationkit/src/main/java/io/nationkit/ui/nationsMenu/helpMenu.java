package io.nationkit.ui.nationsMenu;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class helpMenu {
    public void helpMenu(Player player){
        Inventory inventory = Bukkit.createInventory(null, 9, "Coming Soon");

        ItemStack sel_option1 = new ItemStack(Material.NETHER_STAR, 1);
        ItemStack back = new ItemStack(Material.BARRIER, 1);

        ItemMeta sel_option1_meta = sel_option1.getItemMeta();
        ItemMeta back_meta = back.getItemMeta();

        sel_option1_meta.setDisplayName(ChatColor.WHITE + "Coming Soon!");
        sel_option1_meta.setLore(Arrays.asList(ChatColor.WHITE + "Check back later..."));
        sel_option1_meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        sel_option1.setItemMeta(sel_option1_meta);

        back_meta.setDisplayName(ChatColor.RED + "" + ChatColor.BOLD + "BACK");
        back_meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        back.setItemMeta(back_meta);

        inventory.setItem(0, back);
        inventory.setItem(4, sel_option1);

        player.openInventory(inventory);
    }
}
