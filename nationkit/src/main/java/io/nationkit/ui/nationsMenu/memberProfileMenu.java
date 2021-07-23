package io.nationkit.ui.nationsMenu;

import io.nationkit.files.nationsConfig;
import io.nationkit.files.playersConfig;
import io.nationkit.objects.playerHead;
import io.nationkit.operator;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.UUID;

public class memberProfileMenu {
    private playerHead ph = new playerHead();
    private operator plugin;
    private nationsConfig data;
    private playersConfig data1;

    public void memberProfileMenu(Player player, UUID target){
        this.data = new nationsConfig(plugin.getPlugin(operator.class));
        this.data1 = new playersConfig(plugin.getPlugin(operator.class));
        Boolean isOwner = data1.getConfig().getBoolean("players." + player.getUniqueId().toString() + ".isOwner");
        String name = data1.getConfig().getString("players." + player.getUniqueId().toString() + ".nation");
        String leaderType = data.getConfig().getString("nations." + name + ".leaderType");

        Inventory inventory = Bukkit.createInventory(null, 9, "Citizen Profile");

        ItemStack fastTravel = new ItemStack(Material.POWERED_RAIL);
        ItemStack composeMessage = new ItemStack(Material.WRITABLE_BOOK);
        ItemStack giftItems = new ItemStack(Material.SHULKER_BOX);
        ItemStack filler1 = new ItemStack(Material.WHITE_STAINED_GLASS_PANE);
        ItemStack filler2 = new ItemStack(Material.NETHER_GOLD_ORE);
        ItemStack back = new ItemStack(Material.IRON_NUGGET);

        ItemMeta fastTravel_meta = fastTravel.getItemMeta();
        ItemMeta composeMessage_meta = composeMessage.getItemMeta();
        ItemMeta giftItems_meta = giftItems.getItemMeta();
        ItemMeta filler1_meta = filler1.getItemMeta();
        ItemMeta filler2_meta = filler2.getItemMeta();
        ItemMeta back_meta = back.getItemMeta();

        fastTravel_meta.setDisplayName(ChatColor.GOLD + "Fast Travel to " + Bukkit.getOfflinePlayer(target).getName());
        fastTravel.setItemMeta(fastTravel_meta);

        composeMessage_meta.setDisplayName(ChatColor.GREEN + "Send a message to " + Bukkit.getOfflinePlayer(target).getName());
        composeMessage.setItemMeta(composeMessage_meta);

        giftItems_meta.setDisplayName(ChatColor.LIGHT_PURPLE + "Gift items to " + Bukkit.getOfflinePlayer(target).getName());
        giftItems.setItemMeta(giftItems_meta);

        filler1_meta.setDisplayName(" ");
        filler1.setItemMeta(filler1_meta);

        filler2_meta.setDisplayName(ChatColor.BLACK + target.toString());
        filler2.setItemMeta(filler2_meta);

        back_meta.setDisplayName(ChatColor.RED + "BACK");
        back.setItemMeta(back_meta);

        String lore;

        if(isOwner){
            lore = ChatColor.GOLD + leaderType;
        }else{
            lore = ChatColor.GREEN + "Citizen";
        }

        inventory.setItem(0, back);
        inventory.setItem(1, ph.playerHead(Bukkit.getOfflinePlayer(target).getName(), ChatColor.YELLOW + Bukkit.getOfflinePlayer(target).getName(), lore));
        inventory.setItem(2, filler1);
        inventory.setItem(3, fastTravel);
        inventory.setItem(4, composeMessage);
        inventory.setItem(5, giftItems);
        inventory.setItem(6, filler1);
        inventory.setItem(7, filler1);
        inventory.setItem(8, filler2);

        player.openInventory(inventory);
    }
}
