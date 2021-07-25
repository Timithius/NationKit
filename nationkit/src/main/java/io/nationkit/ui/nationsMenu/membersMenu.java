package io.nationkit.ui.nationsMenu;

import io.nationkit.files.nationsConfig;
import io.nationkit.files.playersConfig;
import io.nationkit.files.tempConfig;
import io.nationkit.objects.playerHead;
import io.nationkit.operator;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.UUID;

public class membersMenu {
    private playerHead ph;
    private operator plugin;
    private nationsConfig data;
    private playersConfig data1;
    private tempConfig data2;
    
    public void membersMenu(Player player){
        this.ph = new playerHead();
        this.data = new nationsConfig(plugin.getPlugin(operator.class));
        this.data1 = new playersConfig(plugin.getPlugin(operator.class));
        this.data2 = new tempConfig(plugin.getPlugin(operator.class));
        String name = data1.getConfig().getString("players." + player.getUniqueId().toString() + ".nation");
        String leaderType = data.getConfig().getString("nations." + name + ".leaderType");
        List members = data.getConfig().getList("nations." + name + ".members");
        int memberLevel = data.getConfig().getInt("nations." + name + ".memberLevel");
        int memberListSize = 9;
        
        if(memberLevel <= 9){
            memberListSize = 9;
        }
        if(memberLevel <= 18 && memberLevel > 9){
            memberListSize = 18;
        }
        if(memberLevel <= 27 && memberLevel > 18){
            memberListSize = 27;
        }
        if(memberLevel <= 36 && memberLevel > 27){
            memberListSize = 36;
        }
        if(memberLevel <= 45 && memberLevel > 36){
            memberListSize = 45;
        }
        if(memberLevel <= 54 && memberLevel > 45){
            memberListSize = 54;
        }
        
        Inventory inventory = Bukkit.createInventory(null, memberListSize, "Nation Citizens");

        ItemStack back = new ItemStack(Material.IRON_NUGGET);
        ItemMeta back_meta = back.getItemMeta();

        back_meta.setDisplayName(ChatColor.RED + "BACK");
        back.setItemMeta(back_meta);

        inventory.setItem(0, back);

        for(int i = 0; i < members.size(); i++){
            Boolean isOwner = data1.getConfig().getBoolean("players." + members.get(i) + ".isOwner");
            UUID playerUUID = UUID.fromString((String) members.get(i));
            String lore;

            if(isOwner){
                lore = ChatColor.GOLD + leaderType;
            }else{
                lore = ChatColor.GREEN + "Citizen";
            }

            data2.getConfig().set("nations." + name + ".memberNamesToUUID." + Bukkit.getOfflinePlayer(playerUUID).getName(), (String) members.get(i));
            data2.saveConfig();

            inventory.setItem(i + 1, ph.playerHead(Bukkit.getOfflinePlayer(playerUUID).getName(), ChatColor.WHITE + Bukkit.getOfflinePlayer(playerUUID).getName(), lore));
        }

        player.openInventory(inventory);
    }
}
