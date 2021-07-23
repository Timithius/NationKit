package io.nationkit.ui.nationsMenu;

import io.nationkit.files.nationsConfig;
import io.nationkit.files.playersConfig;
import io.nationkit.operator;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class mainMenu {
    private operator plugin;
    private nationsConfig data;
    private playersConfig data1;

    public void mainMenu(Player player){
        this.data = new nationsConfig(plugin.getPlugin(operator.class));
        this.data1 = new playersConfig(plugin.getPlugin(operator.class));
        Inventory inventory = Bukkit.createInventory(null, 9,
                "Diplomacy Menu");
        Location location = player.getLocation();
        Boolean hasNation = data1.getConfig().getBoolean("players." + player.getUniqueId().toString() + ".hasNation");
        String name = data1.getConfig().getString("players." + player.getUniqueId().toString() + ".nation");
        int memberLevel = data.getConfig().getInt("nations." + name + ".memberLevel");
        ItemStack nationBanner = data.getConfig().getItemStack("nations." + name + ".banner.material");

        ItemStack help = new ItemStack(Material.BOOKSHELF, 1);
        ItemStack topPowers = new ItemStack(Material.END_CRYSTAL, 1);
        ItemStack conflicts = new ItemStack(Material.DIAMOND_SWORD, 1);
        ItemStack alliances = new ItemStack(Material.SHIELD, 1);
        ItemStack globalEconomy = new ItemStack(Material.GOLD_INGOT, 1);
        ItemStack nation = null;
        ItemStack tradeCenter = new ItemStack(Material.EMERALD, 1);
        ItemStack nationList = new ItemStack(Material.PAPER, 1);
        ItemStack news = new ItemStack(Material.HEART_OF_THE_SEA, 1);

        if(hasNation){
            nation = new ItemStack(nationBanner);
        }else{
            nation = new ItemStack(Material.WHITE_BANNER);
        }

        ItemMeta help_meta = help.getItemMeta();
        ItemMeta topPowers_meta = topPowers.getItemMeta();
        ItemMeta conflicts_meta = conflicts.getItemMeta();
        ItemMeta alliances_meta = alliances.getItemMeta();
        ItemMeta globalEconomy_meta = globalEconomy.getItemMeta();
        ItemMeta nation_meta = nation.getItemMeta();
        ItemMeta tradeCenter_meta = tradeCenter.getItemMeta();
        ItemMeta nationList_meta = nationList.getItemMeta();
        ItemMeta news_meta= news.getItemMeta();

        help_meta.setDisplayName(ChatColor.AQUA + "Tutorial & Help");
        help_meta.setLore(Arrays.asList(ChatColor.GRAY + "Get started"));
        help_meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        help.setItemMeta(help_meta);

        topPowers_meta.setDisplayName(ChatColor.BLUE + "Top Powers");
        topPowers_meta.setLore(Arrays.asList(ChatColor.GRAY + "Monitor the shift in power"));
        topPowers_meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        topPowers.setItemMeta(topPowers_meta);

        conflicts_meta.setDisplayName(ChatColor.RED + "Conflicts");
        conflicts_meta.setLore(Arrays.asList(ChatColor.GRAY + "View current wars or get involved yourself"));
        conflicts_meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        conflicts.setItemMeta(conflicts_meta);

        alliances_meta.setDisplayName(ChatColor.GREEN + "Alliances");
        alliances_meta.setLore(Arrays.asList(ChatColor.GRAY + "View current partnerships & power structures"));
        alliances_meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        alliances.setItemMeta(alliances_meta);

        globalEconomy_meta.setDisplayName(ChatColor.GOLD + "Global Economy");
        globalEconomy_meta.setLore(Arrays.asList(ChatColor.GRAY + "Watch current trends"));
        globalEconomy_meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        globalEconomy.setItemMeta(globalEconomy_meta);

        if(hasNation){
            nation_meta.setDisplayName(ChatColor.DARK_AQUA + "Nation Menu");
            nation_meta.setLore(Arrays.asList(ChatColor.GOLD + name, ChatColor.GRAY + "Members: " + ChatColor.RED + memberLevel));
            nation_meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_POTION_EFFECTS);
            nation.setItemMeta(nation_meta);
        }else{
            nation_meta.setDisplayName(ChatColor.DARK_AQUA + "Nation Menu");
            nation_meta.setLore(Arrays.asList(ChatColor.GRAY + "Start or join a nation here"));
            nation_meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            nation.setItemMeta(nation_meta);
        }

        tradeCenter_meta.setDisplayName(ChatColor.LIGHT_PURPLE + "Trade Center");
        tradeCenter_meta.setLore(Arrays.asList(ChatColor.GRAY + "Trade mass quantities with other nations"));
        tradeCenter_meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        tradeCenter.setItemMeta(tradeCenter_meta);

        nationList_meta.setDisplayName(ChatColor.DARK_GREEN + "Nation List");
        nationList_meta.setLore(Arrays.asList(ChatColor.GRAY + "List all nations"));
        nationList_meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        nationList.setItemMeta(nationList_meta);

        news_meta.setDisplayName(ChatColor.GOLD + "News");
        news_meta.setLore(Arrays.asList(ChatColor.GRAY + "What nations are doing"));
        news_meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        news.setItemMeta(news_meta);

        inventory.setItem(0, help);
        inventory.setItem(1, topPowers);
        inventory.setItem(2, conflicts);
        inventory.setItem(3, alliances);
        inventory.setItem(4, globalEconomy);
        inventory.setItem(5, nation);
        inventory.setItem(6, tradeCenter);
        inventory.setItem(7, nationList);
        inventory.setItem(8, news);

        player.openInventory(inventory);
        player.playSound(location, Sound.BLOCK_RESPAWN_ANCHOR_CHARGE, 10 , 1);
    }
}
