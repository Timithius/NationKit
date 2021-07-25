package io.nationkit.ui.nationsMenu;

import io.nationkit.files.nationsConfig;
import io.nationkit.files.playersConfig;
import io.nationkit.operator;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class topPowersMenu {
    private operator plugin;
    private nationsConfig data;
    private playersConfig data1;

    public void topPowersMenu(Player player){
        this.data = new nationsConfig(plugin.getPlugin(operator.class));
        this.data1 = new playersConfig(plugin.getPlugin(operator.class));
        Inventory inventory = Bukkit.createInventory(null, 18, "Top Powers");

        ItemStack top1 = new ItemStack(Material.BLACK_BANNER);
        ItemStack top2 = new ItemStack(Material.BLACK_BANNER);
        ItemStack top3 = new ItemStack(Material.BLACK_BANNER);
        ItemStack top4 = new ItemStack(Material.BLACK_BANNER);
        ItemStack top5 = new ItemStack(Material.BLACK_BANNER);
        ItemStack top6 = new ItemStack(Material.BLACK_BANNER);
        ItemStack top7 = new ItemStack(Material.BLACK_BANNER);
        ItemStack top8 = new ItemStack(Material.BLACK_BANNER);
        ItemStack top9 = new ItemStack(Material.BLACK_BANNER);
        ItemStack back = new ItemStack(Material.IRON_NUGGET);

        ItemMeta top1_meta = top1.getItemMeta();
        ItemMeta top2_meta = top2.getItemMeta();
        ItemMeta top3_meta = top3.getItemMeta();
        ItemMeta top4_meta = top4.getItemMeta();
        ItemMeta top5_meta = top5.getItemMeta();
        ItemMeta top6_meta = top6.getItemMeta();
        ItemMeta top7_meta = top7.getItemMeta();
        ItemMeta top8_meta = top8.getItemMeta();
        ItemMeta top9_meta = top9.getItemMeta();
        ItemMeta back_meta = back.getItemMeta();

        top1_meta.setDisplayName(ChatColor.GREEN + "Placeholder");
        top1_meta.setLore(Arrays.asList(ChatColor.WHITE + "Slogan"));
        top1_meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        top1.setItemMeta(top1_meta);

        top2_meta.setDisplayName(ChatColor.GREEN + "Placeholder");
        top2_meta.setLore(Arrays.asList(ChatColor.WHITE + "Slogan"));
        top2_meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        top2.setItemMeta(top2_meta);

        top3_meta.setDisplayName(ChatColor.GREEN + "Placeholder");
        top3_meta.setLore(Arrays.asList(ChatColor.WHITE + "Slogan"));
        top3_meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        top3.setItemMeta(top3_meta);

        top4_meta.setDisplayName(ChatColor.GREEN + "Placeholder");
        top4_meta.setLore(Arrays.asList(ChatColor.WHITE + "Slogan"));
        top4_meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        top4.setItemMeta(top4_meta);

        top5_meta.setDisplayName(ChatColor.GREEN + "Placeholder");
        top5_meta.setLore(Arrays.asList(ChatColor.WHITE + "Slogan"));
        top5_meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        top5.setItemMeta(top5_meta);

        top6_meta.setDisplayName(ChatColor.GREEN + "Placeholder");
        top6_meta.setLore(Arrays.asList(ChatColor.WHITE + "Slogan"));
        top6_meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        top6.setItemMeta(top6_meta);

        top7_meta.setDisplayName(ChatColor.GREEN + "Placeholder");
        top7_meta.setLore(Arrays.asList(ChatColor.WHITE + "Slogan"));
        top7_meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        top7.setItemMeta(top7_meta);

        top8_meta.setDisplayName(ChatColor.GREEN + "Placeholder");
        top8_meta.setLore(Arrays.asList(ChatColor.WHITE + "Slogan"));
        top8_meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        top8.setItemMeta(top8_meta);

        top9_meta.setDisplayName(ChatColor.GREEN + "Placeholder");
        top9_meta.setLore(Arrays.asList(ChatColor.WHITE + "Slogan"));
        top9_meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        top9.setItemMeta(top9_meta);

        back_meta.setDisplayName(ChatColor.RED + "" + ChatColor.BOLD + "BACK");
        back_meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        back.setItemMeta(back_meta);

        inventory.setItem(0, back);
        inventory.setItem(9, top1);
        inventory.setItem(10, top2);
        inventory.setItem(11, top3);
        inventory.setItem(12, top4);
        inventory.setItem(13, top5);
        inventory.setItem(14, top6);
        inventory.setItem(15, top7);
        inventory.setItem(16, top8);
        inventory.setItem(17, top9);

        player.openInventory(inventory);
    }
}
