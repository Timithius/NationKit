package io.nationkit.systems.nation.actions;

import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.domains.DefaultDomain;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import io.nationkit.files.nationsConfig;
import io.nationkit.files.playersConfig;
import io.nationkit.operator;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.List;

public class joinLeave {
    private operator plugin;
    private nationsConfig data;
    private playersConfig data1;
    WorldGuard worldGuard = WorldGuard.getInstance();
    RegionContainer worldContainer = worldGuard.getPlatform().getRegionContainer();

    public void join(Player player, String[] args){
        this.data = new nationsConfig(plugin.getPlugin(operator.class));
        this.data1 = new playersConfig(plugin.getPlugin(operator.class));
        com.sk89q.worldedit.entity.Player wgPlayer = WorldGuardPlugin.inst().wrapPlayer(player);
        RegionManager regionManager = worldContainer.get(wgPlayer.getWorld());
        Boolean hasNation = data1.getConfig().getBoolean("players." + player.getUniqueId().toString() + ".hasNation");

        if(!hasNation){
            String nation = data.getConfig().getString("nations." + args[1]);
            String privacySetting = data.getConfig().getString("nations." + args[1] + ".privacySetting");
            List<String> members = (List<String>) data.getConfig().getList("nations." + args[1] + ".members");
            List<String> claimList = (List<String>) data.getConfig().getList("nations." + args[1] + ".claimedChunks.claimList");
            List<String> invitedMemebers = (List<String>) data.getConfig().getList("nations." + args[1] + ".invitedMembers");
            int memberLevel = data.getConfig().getInt("nations." + args[1] + ".memberLevel");

            if(nation != null){
                if(privacySetting.equalsIgnoreCase("open")){
                    if(!members.contains(player.getUniqueId().toString())){
                        members.add(player.getUniqueId().toString());
                    }

                    if(claimList != null){
                        for(int i = 0; i < claimList.size(); i++){
                            String claimID = claimList.get(i);
                            ProtectedRegion region = regionManager.getRegion(claimID);
                            DefaultDomain memberDomain = region.getMembers();

                            memberDomain.addPlayer(player.getUniqueId());
                        }
                    }

                    data.getConfig().set("nations." + args[1] + ".members", members);
                    data.getConfig().set("nations." + args[1] + ".memberLevel", memberLevel + 1);
                    data1.getConfig().set("players." + player.getUniqueId().toString() + ".nation", args[1]);
                    data1.getConfig().set("players." + player.getUniqueId().toString() + ".hasNation", true);
                    data1.getConfig().set("players." + player.getUniqueId().toString() + ".isOwner", false);
                    data.saveConfig();
                    data1.saveConfig();
                    player.sendMessage(ChatColor.GRAY + "You have joined " + ChatColor.GOLD + args[1] + ChatColor.GRAY + ".");
                }else{
                    if(invitedMemebers != null && invitedMemebers.contains(player.getUniqueId().toString())){
                        if(!members.contains(player.getUniqueId().toString())){
                            members.add(player.getUniqueId().toString());
                        }

                        data.getConfig().set("nations." + args[1] + ".members", members);
                        data.getConfig().set("nations." + args[1] + ".memberLevel", memberLevel + 1);
                        data1.getConfig().set("players." + player.getUniqueId().toString() + ".nation", args[1]);
                        data1.getConfig().set("players." + player.getUniqueId().toString() + ".hasNation", true);
                        data1.getConfig().set("players." + player.getUniqueId().toString() + ".isOwner", false);
                        data.saveConfig();
                        data1.saveConfig();
                        player.sendMessage(ChatColor.GRAY + "You have joined " + ChatColor.GOLD + args[1] + ChatColor.GRAY + ".");
                    }else{
                        player.sendMessage(ChatColor.GRAY + "Someone from " + ChatColor.GOLD + args[1] + ChatColor.GRAY + " must invite you before you can join.");
                    }
                }
            }else{
                player.sendMessage(ChatColor.GRAY + "That nation does not exist.");
            }
        }else{
            player.sendMessage(ChatColor.GRAY + "You must leave your current nation before joining a new one.");
        }
    }

    public void leave(Player player){
        this.data = new nationsConfig(plugin.getPlugin(operator.class));
        this.data1 = new playersConfig(plugin.getPlugin(operator.class));
        com.sk89q.worldedit.entity.Player wgPlayer = WorldGuardPlugin.inst().wrapPlayer(player);
        RegionManager regionManager = worldContainer.get(wgPlayer.getWorld());
        Boolean hasNation = data1.getConfig().getBoolean("players." + player.getUniqueId().toString() + ".hasNation");
        Boolean isOwner = data1.getConfig().getBoolean("players." + player.getUniqueId().toString() + ".hasNation");
        String nation = data1.getConfig().getString("players." + player.getUniqueId().toString() + ".nation");
        int memberLevel = data.getConfig().getInt("nations." + nation + ".memberLevel");
        List<String> members = (List<String>) data.getConfig().getList("nations." + nation + ".members");
        List<String> claimList = (List<String>) data.getConfig().getList("nations." + nation + ".claimList");

        if(hasNation){
            if(!isOwner){
                if(claimList != null){
                    for(int i = 0; i < claimList.size(); i++){
                        String claimID = claimList.get(i);
                        ProtectedRegion region = regionManager.getRegion(claimID);
                        DefaultDomain memberDomain = region.getMembers();

                        memberDomain.removePlayer(player.getUniqueId());
                    }
                }

                members.remove(player.getUniqueId().toString());
                data.getConfig().set("nations." + nation + ".memberLevel", memberLevel - 1);
                data.getConfig().set("nations." + nation + ".members", members);
                data1.getConfig().set("players." + player.getUniqueId().toString() + ".hasNation", false);
                data1.getConfig().set("players." + player.getUniqueId().toString() + ".nation", null);
                data1.saveConfig();
                player.sendMessage(ChatColor.GRAY + "You have left " + ChatColor.GOLD + nation + ChatColor.GRAY + "!");
            }else{
                player.sendMessage(ChatColor.GRAY + "You cannot leave a nation you own. You must disband it in the nation manager, leaving all who followed and trusted you behind. This is a big decision!");
            }
        }else{
            player.sendMessage(ChatColor.GRAY + "You are not a citizen of a nation.");
        }
    }
}
