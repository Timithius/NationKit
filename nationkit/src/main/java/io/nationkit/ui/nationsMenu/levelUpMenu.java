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

public class levelUpMenu {
    public void levelUpMenu(Player player, int level, int currentXP, int nextLevelXP, String nextLevelUnlocks, int researchPoints){
        Inventory inventory = Bukkit.createInventory(null, 18, "Levels & Technology");

        ItemStack sel_option1 = new ItemStack(Material.EXPERIENCE_BOTTLE);
        ItemStack sel_option2 = new ItemStack(Material.WRITABLE_BOOK);
        ItemStack sel_option3 = new ItemStack(Material.LECTERN);
        ItemStack back = new ItemStack(Material.IRON_NUGGET);

        ItemMeta sel_option1_meta = sel_option1.getItemMeta();
        ItemMeta sel_option2_meta = sel_option2.getItemMeta();
        ItemMeta sel_option3_meta = sel_option3.getItemMeta();
        ItemMeta back_meta = back.getItemMeta();

        sel_option1_meta.setDisplayName(ChatColor.GREEN + "Current Level: " + ChatColor.AQUA + "" + ChatColor.BOLD + level);
        sel_option1_meta.setLore(Arrays.asList(ChatColor.WHITE + "XP Points to next level: " + ChatColor.AQUA + currentXP +
                ChatColor.WHITE + "/" + ChatColor.AQUA + nextLevelXP, ChatColor.WHITE + "Next Level Unlocks: " + ChatColor.GOLD + nextLevelUnlocks));
        sel_option1_meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        sel_option1.setItemMeta(sel_option1_meta);

        sel_option2_meta.setDisplayName(ChatColor.LIGHT_PURPLE + "Research");
        sel_option2_meta.setLore(Arrays.asList(ChatColor.WHITE + "Research technologies to get perks for your civ and members", ChatColor.WHITE + "Current Points: " + ChatColor.GOLD + researchPoints));
        sel_option2_meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        sel_option2.setItemMeta(sel_option2_meta);

        sel_option3_meta.setDisplayName(ChatColor.BLUE + "Governments");
        sel_option3_meta.setLore(Arrays.asList(ChatColor.WHITE + "Change or upgrade your government type"));
        sel_option3_meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        sel_option3.setItemMeta(sel_option3_meta);

        back_meta.setDisplayName(ChatColor.RED + "" + ChatColor.BOLD + "BACK");
        back.setItemMeta(back_meta);

        inventory.setItem(0, back);
        inventory.setItem(4, sel_option1);
        inventory.setItem(12, sel_option2);
        inventory.setItem(14, sel_option3);

        player.openInventory(inventory);
    }
}
