package io.nationkit.ui.nationsMenu;

import io.nationkit.files.nationsConfig;
import io.nationkit.files.playersConfig;
import io.nationkit.operator;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class nationSettingsMenu {
    private operator plugin;
    private nationsConfig data;
    private playersConfig data1;

    public void nationSettingsMenu(Player player){
        this.data = new nationsConfig(plugin.getPlugin(operator.class));
        this.data1 = new playersConfig(plugin.getPlugin(operator.class));
        String name = data1.getConfig().getString("players." + player.getUniqueId().toString() + ".nation");
        String privacySetting = data.getConfig().getString("nations." + name + ".privacySetting");
        ItemStack nationBanner = data.getConfig().getItemStack("nations." + name + ".banner.material");

        Inventory inventory = Bukkit.createInventory(null, 9, "Nation Settings");

        ItemStack nationName = new ItemStack(Material.OAK_SIGN);
        ItemStack nationBannerPreview = new ItemStack(nationBanner);
        ItemStack privacySettingPreview = null;
        ItemStack filler1 = new ItemStack(Material.IRON_BARS);
        ItemStack back = new ItemStack(Material.IRON_NUGGET);

        ItemMeta nationName_meta = nationName.getItemMeta();
        ItemMeta nationBannerPreview_meta = nationBannerPreview.getItemMeta();
        ItemMeta privacySettingPreview_meta = null;
        ItemMeta filler1_meta = filler1.getItemMeta();
        ItemMeta back_meta = back.getItemMeta();

        if(privacySetting.equalsIgnoreCase("open")){
            privacySettingPreview = new ItemStack(Material.LIME_DYE);
            privacySettingPreview_meta = privacySettingPreview.getItemMeta();
        }
        if(privacySetting.equalsIgnoreCase("limited")){
            privacySettingPreview = new ItemStack(Material.PINK_DYE);
            privacySettingPreview_meta = privacySettingPreview.getItemMeta();
        }
        if(privacySetting.equalsIgnoreCase("closed")){
            privacySettingPreview = new ItemStack(Material.GRAY_DYE);
            privacySettingPreview_meta = privacySettingPreview.getItemMeta();
        }

        nationName_meta.setDisplayName(ChatColor.GOLD + "Change your nation's name");
        nationName_meta.setLore(Arrays.asList(ChatColor.GRAY + "Currently: " + ChatColor.WHITE + name, ChatColor.GRAY + "Click this sign to change the name"));
        nationName.setItemMeta(nationName_meta);

        nationBannerPreview_meta.setDisplayName(ChatColor.GOLD + "Change your nation's banner");
        nationBannerPreview_meta.setLore(Arrays.asList(ChatColor.GRAY + "This is your current banner", ChatColor.GRAY + "Click it to replace it with a banner in your hand"));
        nationBannerPreview.setItemMeta(nationBannerPreview_meta);

        privacySettingPreview_meta.setDisplayName(ChatColor.GOLD + "Change your nation's privacy setting");
        privacySettingPreview_meta.setLore(Arrays.asList(ChatColor.GRAY + "Currently: " + ChatColor.WHITE + privacySetting, ChatColor.GREEN + "open: " +
                ChatColor.WHITE + "Anyone with can join your nation", ChatColor.LIGHT_PURPLE + "limited: " + ChatColor.WHITE + "Any citizen can invite any player",
                ChatColor.RED + "closed: " + ChatColor.WHITE + "You are the only person who can invite players"));
        privacySettingPreview.setItemMeta(privacySettingPreview_meta);

        filler1_meta.setDisplayName(" ");
        filler1.setItemMeta(filler1_meta);

        back_meta.setDisplayName(ChatColor.RED + "BACK");
        back.setItemMeta(back_meta);

        inventory.setItem(0, back);
        inventory.setItem(1, filler1);
        inventory.setItem(2, nationName);
        inventory.setItem(3, filler1);
        inventory.setItem(4, nationBannerPreview);
        inventory.setItem(5, filler1);
        inventory.setItem(6, privacySettingPreview);
        inventory.setItem(7, filler1);
        inventory.setItem(8, filler1);

        player.openInventory(inventory);
    }
}
