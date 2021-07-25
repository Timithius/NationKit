package io.nationkit.systems.nation.actions;

import io.nationkit.files.nationsConfig;
import io.nationkit.files.playersConfig;
import io.nationkit.listeners.dynmapListener;
import io.nationkit.operator;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class setupDisband {
    private io.nationkit.listeners.dynmapListener dynmapListener = new dynmapListener();
    private operator plugin;
    private nationsConfig data;
    private playersConfig data1;

    public void setup(Player player, String[] args){
        this.data = new nationsConfig(plugin.getPlugin(operator.class));
        this.data1 = new playersConfig(plugin.getPlugin(operator.class));
        Boolean hasNation = data.getConfig().getBoolean("players." + player.getUniqueId().toString() + ".hasNation");
        List<String> nationList = (List<String>) data.getConfig().getList("nations.nationList");

        if(!hasNation){
            String newNationName = args[1].replaceAll("[^a-zA-Z].*", "");
            List<String> members = List.of(player.getUniqueId().toString());
            List<String> invitedMembers = List.of("");
            ItemStack defaultBanner = new ItemStack(Material.WHITE_BANNER);
            String choice = "color";
            Random random = new Random();
            int choicePick = random.nextInt(4 - 1) + 1;

            if(choicePick == 1){
                choice = "red";
            }
            if(choicePick == 2){
                choice = "green";
            }
            if(choicePick == 3){
                choice = "blue";
            }

            if(nationList == null){
                nationList = List.of(newNationName);
                data.getConfig().set("nations." + newNationName + ".members", members);
                data.getConfig().set("nations." + newNationName + ".owner", player.getUniqueId().toString());
                data.getConfig().set("nations." + newNationName + ".landClaims", 3);
                data.getConfig().set("nations." + newNationName + ".memberLevel", 1);
                data.getConfig().set("nations." + newNationName + ".civicPoints", 0);
                data.getConfig().set("nations." + newNationName + ".level", 1);
                data.getConfig().set("nations." + newNationName + ".xp", 25);
                data.getConfig().set("nations." + newNationName + ".power", 10);
                data.getConfig().set("nations." + newNationName + ".score", 0);
                data.getConfig().set("nations." + newNationName + ".researchPoints", 1);
                data.getConfig().set("nations." + newNationName + ".govType", "&6Tribal");
                data.getConfig().set("nations." + newNationName + ".govTypeMat", "CRAFTING_TABLE");
                data.getConfig().set("nations." + newNationName + ".leaderType", "Chieftain");
                data.getConfig().set("nations." + newNationName + ".banner.material", defaultBanner);
                data.getConfig().set("nations." + newNationName + ".privacySetting", "open");
                data.getConfig().set("nations." + newNationName + ".claimColor", choice);
                data.getConfig().set("nations." + newNationName + ".invitedMembers", invitedMembers);
                data.getConfig().set("nations.nationList", nationList);
                data1.getConfig().set("players." + player.getUniqueId().toString() + ".nation", newNationName);
                data1.getConfig().set("players." + player.getUniqueId().toString() + ".hasNation", true);
                data1.getConfig().set("players." + player.getUniqueId().toString() + ".isOwner", true);
                data.saveConfig();
                data1.saveConfig();
                player.sendMessage(ChatColor.GRAY + "Your nation " + ChatColor.GOLD + newNationName + ChatColor.GRAY + " has been created. Type " + ChatColor.GOLD + "/nation" + ChatColor.GRAY + " to manage it.");
            }else{
               if(nationList.contains(newNationName)){
                   nationList.add(newNationName);
                   data.getConfig().set("nations." + newNationName + ".members", members);
                   data.getConfig().set("nations." + newNationName + ".owner", player.getUniqueId().toString());
                   data.getConfig().set("nations." + newNationName + ".landClaims", 3);
                   data.getConfig().set("nations." + newNationName + ".memberLevel", 1);
                   data.getConfig().set("nations." + newNationName + ".civicPoints", 0);
                   data.getConfig().set("nations." + newNationName + ".level", 1);
                   data.getConfig().set("nations." + newNationName + ".xp", 25);
                   data.getConfig().set("nations." + newNationName + ".power", 10);
                   data.getConfig().set("nations." + newNationName + ".score", 0);
                   data.getConfig().set("nations." + newNationName + ".researchPoints", 1);
                   data.getConfig().set("nations." + newNationName + ".govType", "&6Tribal");
                   data.getConfig().set("nations." + newNationName + ".govTypeMat", "CRAFTING_TABLE");
                   data.getConfig().set("nations." + newNationName + ".leaderType", "Chieftain");
                   data.getConfig().set("nations." + newNationName + ".banner.material", defaultBanner);
                   data.getConfig().set("nations." + newNationName + ".privacySetting", "open");
                   data.getConfig().set("nations." + newNationName + ".claimColor", choice);
                   data.getConfig().set("nations." + newNationName + ".invitedMembers", invitedMembers);
                   data.getConfig().set("nations.nationList", nationList);
                   data1.getConfig().set("players." + player.getUniqueId().toString() + ".nation", newNationName);
                   data1.getConfig().set("players." + player.getUniqueId().toString() + ".hasNation", true);
                   data1.getConfig().set("players." + player.getUniqueId().toString() + ".isOwner", true);
                   data.saveConfig();
                   data1.saveConfig();
                   player.sendMessage(ChatColor.GRAY + "Your nation " + ChatColor.GOLD + newNationName + ChatColor.GRAY + " has been created. Type " + ChatColor.GOLD + "/nation" + ChatColor.GRAY + " to manage it.");
               }
            }
        }else{
            player.sendMessage(ChatColor.GRAY + "You must leave your current nation before making a new one");
        }
    }
}
