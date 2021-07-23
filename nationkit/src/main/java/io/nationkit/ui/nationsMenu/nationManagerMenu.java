package io.nationkit.ui.nationsMenu;

import io.nationkit.files.nationsConfig;
import io.nationkit.files.playersConfig;
import io.nationkit.objects.playerHead;
import io.nationkit.operator;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class nationManagerMenu {
    private playerHead ph;
    private operator plugin;
    private nationsConfig data;
    private playersConfig data1;

    public void nationManagerMenu(Player player){
        this.data = new nationsConfig(plugin.getPlugin(operator.class));
        this.data1 = new playersConfig(plugin.getPlugin(operator.class));
        Boolean hasNation = data1.getConfig().getBoolean("players." + player.getUniqueId().toString() + ".hasNation");
        Boolean isOwner = data1.getConfig().getBoolean("players." + player.getUniqueId().toString() + ".isOwner");
        String nationName = data1.getConfig().getString("players." + player.getUniqueId().toString() + ".nation");
        int memberlevel = data.getConfig().getInt("nations." + nationName + ".memberLevel");
        int nationLevel = data.getConfig().getInt("nations." + nationName + ".level");
        int xp = data.getConfig().getInt("nations." + nationName + ".xp");
        int power = data.getConfig().getInt("nations." + nationName + ".power");
        int researchPoints = data.getConfig().getInt("nations." + nationName + ".researchPoints");
        int civicPoints = data.getConfig().getInt("nations." + nationName + ".civicPoints");
        String nationGov = data.getConfig().getString("nations." + nationName + ".govType");
        String nationLT = data.getConfig().getString("nations." + nationName + ".leaderType");
        ItemStack nationBanner = data.getConfig().getItemStack("nations." + nationName + ".banner.material");

        if(!hasNation) {
            Inventory inventory = Bukkit.createInventory(null, 9, "Nation View");

            ItemStack createNation = new ItemStack(Material.NETHER_STAR);
            ItemStack filler = new ItemStack(Material.WHITE_STAINED_GLASS_PANE);
            ItemStack back = new ItemStack(Material.IRON_NUGGET);

            ItemMeta createNation_meta = createNation.getItemMeta();
            ItemMeta filler_meta = filler.getItemMeta();
            ItemMeta back_meta = back.getItemMeta();

            createNation_meta.setDisplayName(ChatColor.GREEN + "Create a Nation");
            createNation_meta.setLore(Arrays.asList(ChatColor.WHITE + "Begin a new empire"));
            createNation_meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            createNation.setItemMeta(createNation_meta);

            filler_meta.setDisplayName(" ");
            filler_meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            filler.setItemMeta(filler_meta);

            back_meta.setDisplayName(ChatColor.RED + "" + ChatColor.BOLD + "BACK");
            back.setItemMeta(back_meta);

            inventory.setItem(0, back);
            inventory.setItem(1, filler);
            inventory.setItem(2, filler);
            inventory.setItem(3, filler);
            inventory.setItem(4, createNation);
            inventory.setItem(5, filler);
            inventory.setItem(6, filler);
            inventory.setItem(7, filler);
            inventory.setItem(8, filler);

            player.openInventory(inventory);
        }
        if(hasNation && isOwner){
            this.ph = new playerHead();

            Inventory inventory = Bukkit.createInventory(null, 54, "Nation View");
            String membersClean;

            if(memberlevel != 1){
                membersClean = " members";
            }else{
                membersClean = " member";
            }

            ItemStack nationView = new ItemStack(nationBanner);
            ItemStack deleteNation = new ItemStack(Material.BARRIER);
            ItemStack governments = new ItemStack(Material.LECTERN);
            ItemStack nationSettings = new ItemStack(Material.COMMAND_BLOCK);
            ItemStack filler1 = new ItemStack(Material.SPRUCE_LOG);
            ItemStack filler2 = new ItemStack(Material.OAK_LEAVES);
            ItemStack back = new ItemStack(Material.IRON_NUGGET);

            ItemMeta nationView_meta = nationView.getItemMeta();
            ItemMeta deleteNation_meta = deleteNation.getItemMeta();
            ItemMeta governments_meta = governments.getItemMeta();
            ItemMeta nationSettings_meta = nationSettings.getItemMeta();
            ItemMeta filler1_meta = filler1.getItemMeta();
            ItemMeta filler2_meta = filler2.getItemMeta();
            ItemMeta back_meta = back.getItemMeta();

            nationView_meta.setDisplayName(ChatColor.WHITE + "" + ChatColor.BOLD + nationName);
            nationView_meta.setLore(Arrays.asList(ChatColor.GRAY + "Government: " + ChatColor.translateAlternateColorCodes('&', nationGov),
                    ChatColor.GRAY + "Level: " + ChatColor.AQUA + nationLevel, ChatColor.GRAY + "XP: " + ChatColor.AQUA + xp, ChatColor.GRAY + "Power: " + ChatColor.AQUA + power, ChatColor.GRAY + "Research Points: " +
                    ChatColor.AQUA + researchPoints, ChatColor.GRAY + "Civic Points: " + ChatColor.AQUA + civicPoints, ChatColor.GRAY + "Leader Type: " + ChatColor.GOLD + nationLT, ChatColor.RED + String.valueOf(memberlevel) + membersClean));
            nationView_meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_POTION_EFFECTS);
            nationView.setItemMeta(nationView_meta);

            deleteNation_meta.setDisplayName(ChatColor.RED + "" + ChatColor.BOLD + "" + ChatColor.ITALIC + "DELETE Nation");
            deleteNation_meta.setLore(Arrays.asList(ChatColor.WHITE + "Disbands your entire nation and all of its progress", ChatColor.RED + "(this cannot be undone)"));
            deleteNation_meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            deleteNation.setItemMeta(deleteNation_meta);

            governments_meta.setDisplayName(ChatColor.AQUA + "Government Menu");
            governments_meta.setLore(Arrays.asList(ChatColor.WHITE + "Take your nation in a new direction", ChatColor.GRAY + "Currently a " + ChatColor.translateAlternateColorCodes('&', nationGov)));
            governments_meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            governments.setItemMeta(governments_meta);

            nationSettings_meta.setDisplayName(ChatColor.YELLOW + "Nation Settings");
            nationSettings_meta.setLore(Arrays.asList(ChatColor.WHITE + "Change your nation's name, banner, and more"));
            nationSettings.setItemMeta(nationSettings_meta);

            filler1_meta.setDisplayName(" ");
            filler1.setItemMeta(filler1_meta);

            filler2_meta.setDisplayName(" ");
            filler2.setItemMeta(filler2_meta);

            back_meta.setDisplayName(ChatColor.RED + "" + ChatColor.BOLD + "BACK");
            back.setItemMeta(back_meta);

            inventory.setItem(0, back);
            inventory.setItem(1, filler2);
            inventory.setItem(2, filler2);
            inventory.setItem(3, filler2);
            inventory.setItem(4, filler2);
            inventory.setItem(5, filler2);
            inventory.setItem(6, filler2);
            inventory.setItem(7, filler2);
            inventory.setItem(8, filler1);
            inventory.setItem(9, filler2);
            inventory.setItem(13, nationView);
            inventory.setItem(17, filler2);
            inventory.setItem(18, filler2);
            inventory.setItem(26, filler2);
            inventory.setItem(27, filler2);
            inventory.setItem(29, ph.playerHead(Bukkit.getOfflinePlayer(player.getUniqueId()).getName(), ChatColor.YELLOW + "Nation Citizens", ChatColor.WHITE + "See all citizens in " + ChatColor.GOLD + nationName));
            inventory.setItem(31, governments);
            inventory.setItem(33, nationSettings);
            inventory.setItem(35, filler2);
            inventory.setItem(36, filler2);
            inventory.setItem(44, filler2);
            inventory.setItem(45, filler1);
            inventory.setItem(46, filler2);
            inventory.setItem(47, filler2);
            inventory.setItem(48, filler2);
            inventory.setItem(49, deleteNation);
            inventory.setItem(50, filler2);
            inventory.setItem(51, filler2);
            inventory.setItem(52, filler2);
            inventory.setItem(53, filler1);

            player.openInventory(inventory);
        }
        if(hasNation && !isOwner){
            this.ph = new playerHead();
            Inventory inventory = Bukkit.createInventory(null, 9, "Nation View");

            ItemStack nationInfo = new ItemStack(nationBanner);
            ItemStack deleteNation = new ItemStack(Material.BARRIER);
            ItemStack back = new ItemStack(Material.IRON_NUGGET);
            ItemStack filler = new ItemStack(Material.WHITE_STAINED_GLASS_PANE);

            ItemMeta nationInfo_meta = nationInfo.getItemMeta();
            ItemMeta deleteNation_meta = deleteNation.getItemMeta();
            ItemMeta back_meta = back.getItemMeta();
            ItemMeta filler_meta = filler.getItemMeta();

            nationInfo_meta.setDisplayName(ChatColor.WHITE + "" + ChatColor.BOLD + nationName);
            nationInfo_meta.setLore(Arrays.asList(ChatColor.GRAY + "Government: " + ChatColor.translateAlternateColorCodes('&', nationGov),
                    ChatColor.GRAY + "Level: " + ChatColor.AQUA + nationLevel, ChatColor.GRAY + "You are a " + ChatColor.GREEN + "citizen", ChatColor.RED + String.valueOf(memberlevel) + " members"));
            nationInfo_meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_POTION_EFFECTS);
            nationInfo.setItemMeta(nationInfo_meta);

            deleteNation_meta.setDisplayName(ChatColor.RED + "Leave Nation");
            deleteNation_meta.setLore(Arrays.asList(ChatColor.WHITE + "Break ties with those who love you and abandon your nation"));
            deleteNation_meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            deleteNation.setItemMeta(deleteNation_meta);

            back_meta.setDisplayName(ChatColor.RED + "BACK");
            back.setItemMeta(back_meta);

            filler_meta.setDisplayName(" ");
            filler.setItemMeta(filler_meta);

            inventory.setItem(0, back);
            inventory.setItem(1, filler);
            inventory.setItem(2, filler);
            inventory.setItem(3, ph.playerHead(Bukkit.getOfflinePlayer(player.getUniqueId()).getName(), ChatColor.YELLOW + "Nation Citizens", ChatColor.WHITE + "See all citizens in " + ChatColor.GOLD + nationName));
            inventory.setItem(4, nationInfo);
            inventory.setItem(5, filler);
            inventory.setItem(6, filler);
            inventory.setItem(7, filler);
            inventory.setItem(8, deleteNation);

            player.openInventory(inventory);
        }
    }
}
