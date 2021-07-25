package io.nationkit.systems.nation;

import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.flags.Flags;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import io.nationkit.files.nationsConfig;
import io.nationkit.files.playersConfig;
import io.nationkit.listeners.dynmapListener;
import io.nationkit.operator;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.dynmap.DynmapCommonAPIListener;
import org.dynmap.markers.AreaMarker;
import org.dynmap.markers.MarkerSet;

import java.util.List;
import java.util.UUID;

public class nationSettings {
    private io.nationkit.listeners.dynmapListener dynmapListener = new dynmapListener();
    private operator plugin;
    private nationsConfig data;
    private playersConfig data1;
    WorldGuard worldGuard = WorldGuard.getInstance();
    RegionContainer worldContainer = worldGuard.getPlatform().getRegionContainer();

    public void setBanner(Player player, ItemStack banner){
        this.data = new nationsConfig(plugin.getPlugin(operator.class));
        this.data1 = new playersConfig(plugin.getPlugin(operator.class));
        Boolean hasNation = data1.getConfig().getBoolean("players." + player.getUniqueId().toString() + ".hasNation");
        Boolean isOwner = data1.getConfig().getBoolean("players." + player.getUniqueId().toString() + ".isOwner");
        String nation = data1.getConfig().getString("players." + player.getUniqueId().toString() + ".nation");

        if(hasNation && isOwner){
            if(banner.getType().equals(Material.BLACK_BANNER) || banner.getType().equals(Material.WHITE_BANNER) || banner.getType().equals(Material.BROWN_BANNER) ||
                    banner.getType().equals(Material.BLUE_BANNER) || banner.getType().equals(Material.PURPLE_BANNER) || banner.getType().equals(Material.CYAN_BANNER) || banner.getType().equals(Material.GRAY_BANNER) ||
                    banner.getType().equals(Material.GREEN_BANNER) || banner.getType().equals(Material.LIGHT_BLUE_BANNER) || banner.getType().equals(Material.LIGHT_GRAY_BANNER) || banner.getType().equals(Material.LIME_BANNER) ||
                    banner.getType().equals(Material.MAGENTA_BANNER) || banner.getType().equals(Material.ORANGE_BANNER) || banner.getType().equals(Material.PINK_BANNER) || banner.getType().equals(Material.RED_BANNER) ||
                    banner.getType().equals(Material.YELLOW_BANNER)){
                data.getConfig().set("nations." + nation + ".banner.material", banner);
                data.saveConfig();

                player.sendMessage(ChatColor.GRAY + "New banner for " + ChatColor.GOLD + nation + ChatColor.GRAY + " set!");
            }else{
                player.sendMessage(ChatColor.GRAY + "To set a banner for your nation, hold it in your main hand.");
            }
        }else{
            player.sendMessage(ChatColor.GRAY + "You must own a nation to set a banner!");
        }
    }

    public void invitePlayer(Player player, Player invitee){
        this.data = new nationsConfig(plugin.getPlugin(operator.class));
        this.data1 = new playersConfig(plugin.getPlugin(operator.class));
        Boolean hasNation = data1.getConfig().getBoolean("players." + player.getUniqueId().toString() + ".hasNation");
        Boolean isOwner = data1.getConfig().getBoolean("players." + player.getUniqueId().toString() + ".isOwner");
        String nation = data1.getConfig().getString("players." + player.getUniqueId().toString() + ".nation");
        List<String> invitedMembers = (List<String>) data.getConfig().getList("nation." + nation + ".invitedMembers");
        Location inviteeLocation = invitee.getLocation();

        if(hasNation){
            String privacySetting = data.getConfig().getString("nations." + nation + ".privacySetting");

            if(privacySetting.equalsIgnoreCase("open")){
                player.sendMessage(ChatColor.GRAY + "Your nation doesn't require invites, anyone can join!");
            }
            if(privacySetting.equalsIgnoreCase("limited")){
                if(invitee.isOnline()){
                    invitedMembers.add(invitee.getUniqueId().toString());
                    data.getConfig().set("nations." + nation + ".invitedMembers", invitedMembers);
                    data.saveConfig();
                    player.sendMessage(ChatColor.GOLD + invitee.getDisplayName() + ChatColor.GRAY + " has been invited to " + ChatColor.GOLD + nation + ChatColor.GRAY + "!");
                    invitee.sendMessage(ChatColor.GRAY + "You have been invited to join the nation: " + ChatColor.GOLD + nation + ChatColor.GRAY + "! Type " + ChatColor.GOLD + "/nation join " + nation);
                    invitee.playSound(inviteeLocation, Sound.ENTITY_VILLAGER_YES, 10, 1);
                }else{
                    player.sendMessage(ChatColor.GOLD + invitee.getDisplayName() + ChatColor.GRAY + " is not online!");
                }
            }
            if(privacySetting.equalsIgnoreCase("closed")){
                if(isOwner){
                    if(invitee.isOnline()) {
                        invitedMembers.add(invitee.getUniqueId().toString());
                        data.getConfig().set("nations." + nation + ".invitedMembers", invitedMembers);
                        data.saveConfig();
                        player.sendMessage(ChatColor.GOLD + nation + ChatColor.GRAY + " has been invited to " + ChatColor.GOLD + nation + ChatColor.GRAY + "!");
                        invitee.sendMessage(ChatColor.GRAY + "You have been invited to join the nation: " + ChatColor.GOLD + nation + ChatColor.GRAY + "! Type " + ChatColor.GOLD + "/nation join " + nation);
                        invitee.playSound(inviteeLocation, Sound.ENTITY_VILLAGER_YES, 10, 1);
                    }else{
                        player.sendMessage(ChatColor.GOLD + invitee.getDisplayName() + ChatColor.GRAY + " is not online!");
                    }
                }else{
                    player.sendMessage(ChatColor.GRAY + "Only the owner of this nation can invite members!");
                }
            }
        }else{
            player.sendMessage(ChatColor.GRAY +  "You must be a citizen or owner of a nation to create invites!");
        }
    }

    public void setName(Player player, String[] args){
        this.data = new nationsConfig(plugin.getPlugin(operator.class));
        this.data1 = new playersConfig(plugin.getPlugin(operator.class));
        DynmapCommonAPIListener.register(dynmapListener);
        com.sk89q.worldedit.entity.Player wgPlayer = WorldGuardPlugin.inst().wrapPlayer(player);
        RegionManager regionManager = worldContainer.get(wgPlayer.getWorld());
        Boolean hasNation = data1.getConfig().getBoolean("players." + player.getUniqueId().toString() + ".hasNation");
        Boolean isOwner = data1.getConfig().getBoolean("players." + player.getUniqueId().toString() + ".isOwner");
        String newNationName = args[1].replaceAll("[^a-zA-Z].*", "");
        String nation = data1.getConfig().getString("players." + player.getUniqueId().toString() + ".nation");
        String govType = data.getConfig().getString("nations." + nation + ".govType");
        String govTypeMat = data.getConfig().getString("nations." + nation + ".govTypeMat");
        String leaderType = data.getConfig().getString("nations." + nation + ".leaderType");
        String privacySetting = data.getConfig().getString("nations." + nation + ".privacySetting");
        String claimColor = data.getConfig().getString("nations." + nation + ".claimColor");
        ItemStack banner = data.getConfig().getItemStack("nations." + nation + ".banner.material");
        int memberLevel = data.getConfig().getInt("nations." + nation + ".memberLevel");
        int civicPoints = data.getConfig().getInt("nations." + nation + ".civicPoints");
        int level = data.getConfig().getInt("nations." + nation + ".level");
        int xp = data.getConfig().getInt("nations." + nation + ".xp");
        int power = data.getConfig().getInt("nations." + nation + ".power");
        int score = data.getConfig().getInt("nations." + nation + ".score");
        int researchPoints = data.getConfig().getInt("nations." + nation + ".researchPoints");
        int landClaims = data.getConfig().getInt("nations." + nation + ".landClaims");
        List<String> members = (List<String>) data.getConfig().getList("nations." + nation + ".members");
        List<String> claimList = (List<String>) data.getConfig().getList("nations." + nation + ".claimedChunks.landClaims");
        List<String> invitedMembers = (List<String>) data.getConfig().getList("nations." + nation + ".invitedMembers");
        List<String> nationList = (List<String>) data.getConfig().getList("nations.nationList");

        if(hasNation && isOwner){
            if(xp >= 100){
                if(nationList != null){
                    nationList.remove(nation);
                    nationList.add(newNationName);
                }

                data.getConfig().set("nations." + newNationName + ".members", members);
                data.getConfig().set("nations." + newNationName + ".owner", player.getUniqueId().toString());
                data.getConfig().set("nations." + newNationName + ".landClaims", landClaims);
                data.getConfig().set("nations." + newNationName + ".memberLevel", memberLevel);
                data.getConfig().set("nations." + newNationName + ".civicPoints", civicPoints);
                data.getConfig().set("nations." + newNationName + ".level", level);
                data.getConfig().set("nations." + newNationName + ".xp", xp);
                data.getConfig().set("nations." + newNationName + ".power", power);
                data.getConfig().set("nations." + newNationName + ".score", score);
                data.getConfig().set("nations." + newNationName + ".researchPoints", researchPoints);
                data.getConfig().set("nations." + newNationName + ".govType", govType);
                data.getConfig().set("nations." + newNationName + ".govTypeMat", govTypeMat);
                data.getConfig().set("nations." + newNationName + ".leaderType", leaderType);
                data.getConfig().set("nations." + newNationName + ".banner.material", banner);
                data.getConfig().set("nations." + newNationName + ".privacySetting", privacySetting);
                data.getConfig().set("nations." + newNationName + ".claimColor", claimColor);
                data.getConfig().set("nations." + newNationName + ".claimedChunks.claimList", claimList);
                data.getConfig().set("nations." + newNationName + ".invitedMembers", invitedMembers);
                data.getConfig().set("nations.nationList", nationList);

                if(claimList != null){
                    for(String claimID : claimList){
                        if(claimID != null){
                            int comparativeX = data.getConfig().getInt("nations." + nation + ".claimedChunks." + claimID + ".topLeftX");
                            int comparativeZ = data.getConfig().getInt("nations." + nation + ".claimedChunks." + claimID + ".topLeftZ");
                            MarkerSet markerSet = dynmapListener.getMarkerAPI().getMarkerSet(claimID);
                            AreaMarker areaMarker = markerSet.findAreaMarker(claimID);

                            regionManager.getRegion(claimID).setFlag(Flags.GREET_TITLE, ChatColor.WHITE + newNationName + ChatColor.GRAY + "'s Land");
                            markerSet.setMarkerSetLabel(newNationName);
                            areaMarker.setLabel(newNationName);
                            data.getConfig().set("nations." + newNationName + ".claimedChunks." + claimID + ".topLeftX", comparativeX);
                            data.getConfig().set("nations." + newNationName + ".claimedChunks." + claimID + ".topLeftZ", comparativeZ);
                        }
                    }
                }
                for(String member : members){
                    if(member != null){
                        data1.getConfig().set("players." + member + ".nation", newNationName);
                    }
                }

                data.getConfig().set("nations." + nation, null);
                data.getConfig().set("nations." + newNationName + ".xp", xp - 100);
                data.saveConfig();
                data1.saveConfig();
                player.sendMessage(ChatColor.GRAY + "Nation name changed to " + ChatColor.GOLD + newNationName + ChatColor.GRAY + "!");
                player.sendMessage(ChatColor.GOLD + "" + ChatColor.ITALIC + newNationName + ChatColor.RESET + "" + ChatColor.RED + " - 100 XP");
            }else{
                player.sendMessage(ChatColor.GRAY + "Your nation needs " + ChatColor.AQUA + "100 XP" + ChatColor.GRAY + " to change its name.");
            }
        }else{
            player.sendMessage(ChatColor.GRAY + "You must own a nation to set the nation name!");
        }
    }
}
