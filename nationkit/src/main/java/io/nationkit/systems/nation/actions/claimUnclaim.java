package io.nationkit.systems.nation.actions;

import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.domains.DefaultDomain;
import com.sk89q.worldguard.protection.flags.Flags;
import com.sk89q.worldguard.protection.flags.RegionGroup;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedCuboidRegion;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import io.nationkit.files.nationsConfig;
import io.nationkit.files.playersConfig;
import io.nationkit.listeners.dynmapListener;
import io.nationkit.operator;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.dynmap.DynmapCommonAPIListener;
import org.dynmap.markers.AreaMarker;
import org.dynmap.markers.MarkerSet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class claimUnclaim {
    private io.nationkit.listeners.dynmapListener dynmapListener = new dynmapListener();
    private operator plugin;
    private nationsConfig data;
    private playersConfig data1;
    WorldGuard worldGuard = WorldGuard.getInstance();
    RegionContainer worldContainer = worldGuard.getPlatform().getRegionContainer();

    public void claim(Player player){
        this.data = new nationsConfig(plugin.getPlugin(operator.class));
        this.data1 = new playersConfig(plugin.getPlugin(operator.class));
        DynmapCommonAPIListener.register(dynmapListener);
        com.sk89q.worldedit.entity.Player wgPlayer = WorldGuardPlugin.inst().wrapPlayer(player);
        RegionManager regionManager = worldContainer.get(wgPlayer.getWorld());
        Location location = player.getLocation();
        Chunk playerChunk = location.getChunk();
        Boolean hasNation = data1.getConfig().getBoolean("players." + player.getUniqueId().toString() + ".hasNation");
        Boolean isOwner = data1.getConfig().getBoolean("players." + player.getUniqueId().toString() + ".hasNation");
        String nation = data1.getConfig().getString("players." + player.getUniqueId().toString() + ".nation");
        String claimColor = data.getConfig().getString("nations." + nation + ".claimColor");
        int landClaims = data.getConfig().getInt("nations." + nation + ".landClaims");
        int totalLandClaims = data.getConfig().getInt("nations." + nation + ".totalLandClaims");
        List<String> claimList = (List<String>) data.getConfig().getList("nations." + nation + ".claimedChunks.claimList");
        List<String> globalChunks = (List<String>) data.getConfig().getList("nations.globalChunks");
        List<String> members = (List<String>) data.getConfig().getList("nations." + nation + ".members");

        if(hasNation && isOwner){
            if(landClaims != 0){
                int minID = 5000;
                int maxID = 500000;
                int random_int = (int)Math.floor(Math.random()*(maxID-minID+1)+minID);
                String claimID;
                Location center = new Location(playerChunk.getWorld(), playerChunk.getX() << 4, 64, playerChunk.getZ() << 4).add(8, 0, 8);
                center.setY(center.getWorld().getHighestBlockYAt(center));
                Location topLeft = center.clone().add(8, 0, 8);
                Location topRight = center.clone().add(8, 0, -8);
                Location bottomLeft = center.clone().add(-8, 0, -8);
                boolean alreadyOwned = false;
                boolean alreadyOwnedByNation = false;
                String owningNation = null;

                if(claimList == null){
                    claimID = nation + random_int;
                    claimList = new ArrayList<String>(Collections.singleton(claimID));
                }else{
                    Boolean exists = false;

                    for(int i = 0; i < claimList.size(); i++){
                        if(claimList.contains(nation + random_int)){
                            exists = true;
                        }
                    }

                    if(!exists){
                        claimID = nation + random_int;
                    }else{
                        claimID = nation + random_int + random_int;
                    }

                    claimList.add(claimID);
                }
                if(globalChunks == null){
                    globalChunks = new ArrayList<String>(Collections.singleton(claimID));
                }else{
                    globalChunks.add(claimID);
                }

                for(int i = 0; i < claimList.size(); i++){
                    String nationClaimID = claimList.get(i);
                    int comparativeX = data.getConfig().getInt("nations." + nation + ".claimedChunks." + nationClaimID + ".topLeftX");
                    int comparativeZ = data.getConfig().getInt("nations." + nation + ".claimedChunks." + nationClaimID + ".topLeftZ");

                    if(comparativeX == topLeft.getX() && comparativeZ == topLeft.getZ()){
                        alreadyOwnedByNation = true;
                    }
                }
                for(int i = 0; i < globalChunks.size(); i++){
                    String globalClaimID = globalChunks.get(i);
                    String nationFromID = globalClaimID.replaceAll("[^a-zA-Z].*", "");
                    int comparativeX = data.getConfig().getInt("nations." + nationFromID + ".claimedChunks." + globalClaimID + ".topLeftX");
                    int comparativeZ = data.getConfig().getInt("nations." + nationFromID + ".claimedChunks." + globalClaimID + ".topLeftZ");

                    if(comparativeX == topLeft.getX() && comparativeZ == topLeft.getZ()){
                        alreadyOwned = true;
                        owningNation = nationFromID;
                    }
                }

                if(!alreadyOwned){
                    if (!alreadyOwnedByNation){
                        int red = 0xDA1F0B;
                        int green = 0x90DF15;
                        int blue = 0x42f4f1;
                        int color = 0;
                        int blockX = playerChunk.getX() * 16;
                        int blockZ = playerChunk.getZ() * 16;
                        BlockVector3 minVec = BlockVector3.at(blockX, 0, blockZ);
                        BlockVector3 maxVec = BlockVector3.at(blockX+15, 256, blockZ+15);
                        ProtectedRegion region = new ProtectedCuboidRegion(claimID, minVec, maxVec);
                        DefaultDomain memberDomain = region.getMembers();

                        for(Object member : members){
                            UUID uuid = UUID.fromString((String) member);

                            memberDomain.addPlayer(uuid);
                        }

                        region.setFlag(Flags.GREET_TITLE, ChatColor.WHITE + nation + ChatColor.GRAY + "'s Land");
                        region.setFlag(Flags.USE, StateFlag.State.ALLOW);
                        region.setFlag(Flags.USE.getRegionGroupFlag(), RegionGroup.MEMBERS);
                        region.setFlag(Flags.BUILD, StateFlag.State.ALLOW);
                        region.setFlag(Flags.BUILD.getRegionGroupFlag(), RegionGroup.MEMBERS);
                        region.setFlag(Flags.INTERACT, StateFlag.State.ALLOW);
                        region.setFlag(Flags.INTERACT.getRegionGroupFlag(), RegionGroup.MEMBERS);
                        region.setFlag(Flags.BLOCK_BREAK, StateFlag.State.ALLOW);
                        region.setFlag(Flags.BLOCK_BREAK.getRegionGroupFlag(), RegionGroup.MEMBERS);
                        region.setFlag(Flags.BLOCK_PLACE, StateFlag.State.ALLOW);
                        region.setFlag(Flags.BLOCK_PLACE.getRegionGroupFlag(), RegionGroup.MEMBERS);
                        region.setFlag(Flags.DAMAGE_ANIMALS, StateFlag.State.ALLOW);
                        region.setFlag(Flags.DAMAGE_ANIMALS.getRegionGroupFlag(), RegionGroup.MEMBERS);
                        region.setFlag(Flags.CHEST_ACCESS, StateFlag.State.ALLOW);
                        region.setFlag(Flags.CHEST_ACCESS.getRegionGroupFlag(), RegionGroup.MEMBERS);
                        region.setFlag(Flags.RIDE, StateFlag.State.ALLOW);
                        region.setFlag(Flags.RIDE.getRegionGroupFlag(), RegionGroup.MEMBERS);
                        region.setFlag(Flags.SLEEP, StateFlag.State.ALLOW);
                        region.setFlag(Flags.SLEEP.getRegionGroupFlag(), RegionGroup.MEMBERS);
                        region.setFlag(Flags.RESPAWN_ANCHORS, StateFlag.State.ALLOW);
                        region.setFlag(Flags.RESPAWN_ANCHORS.getRegionGroupFlag(), RegionGroup.MEMBERS);
                        region.setFlag(Flags.TNT, StateFlag.State.ALLOW);
                        region.setFlag(Flags.TNT.getRegionGroupFlag(), RegionGroup.MEMBERS);
                        region.setFlag(Flags.PLACE_VEHICLE, StateFlag.State.ALLOW);
                        region.setFlag(Flags.PLACE_VEHICLE.getRegionGroupFlag(), RegionGroup.MEMBERS);
                        region.setFlag(Flags.DESTROY_VEHICLE, StateFlag.State.ALLOW);
                        region.setFlag(Flags.DESTROY_VEHICLE.getRegionGroupFlag(), RegionGroup.MEMBERS);
                        region.setFlag(Flags.LIGHTER, StateFlag.State.ALLOW);
                        region.setFlag(Flags.LIGHTER.getRegionGroupFlag(), RegionGroup.MEMBERS);
                        region.setFlag(Flags.TRAMPLE_BLOCKS, StateFlag.State.ALLOW);
                        region.setFlag(Flags.TRAMPLE_BLOCKS.getRegionGroupFlag(), RegionGroup.MEMBERS);
                        region.setFlag(Flags.ITEM_FRAME_ROTATE, StateFlag.State.ALLOW);
                        region.setFlag(Flags.ITEM_FRAME_ROTATE.getRegionGroupFlag(), RegionGroup.MEMBERS);
                        region.setFlag(Flags.FIREWORK_DAMAGE, StateFlag.State.ALLOW);
                        region.setFlag(Flags.FIREWORK_DAMAGE.getRegionGroupFlag(), RegionGroup.MEMBERS);
                        region.setFlag(Flags.USE_ANVIL, StateFlag.State.ALLOW);
                        region.setFlag(Flags.USE_ANVIL.getRegionGroupFlag(), RegionGroup.MEMBERS);
                        region.setMembers(memberDomain);

                        if(claimColor.equalsIgnoreCase("red")){
                            color = red;
                        }
                        if(claimColor.equalsIgnoreCase("green")){
                            color = green;
                        }
                        if(claimColor.equalsIgnoreCase("blue")){
                            color = blue;
                        }

                        MarkerSet markerSet = dynmapListener.getMarkerAPI().createMarkerSet(claimID, nation, null, true);
                        double[] x = new double[]{topLeft.getX(), bottomLeft.getX()};
                        double[] z = new double[]{topLeft.getZ(), topRight.getZ()};
                        AreaMarker marker = markerSet.createAreaMarker(claimID, nation, true,
                                player.getLocation().getWorld().getName(), x, z, true);
                        marker.setFillStyle(0.25, color);
                        marker.setLineStyle(0, 0, 0);

                        regionManager.addRegion(region);
                        data.getConfig().set("nations." + nation + ".claimedChunks." + claimID + ".topLeftX", topLeft.getX());
                        data.getConfig().set("nations." + nation + ".claimedChunks." + claimID + ".topLeftZ", topLeft.getZ());
                        data.getConfig().set("nations." + nation + ".claimedChunks.claimList", claimList);
                        data.getConfig().set("nations.globalChunks", globalChunks);
                        data.getConfig().set("nations." + nation + ".landClaims", landClaims - 1);
                        data.saveConfig();

                        player.sendMessage(ChatColor.GRAY + "Land claimed! You now have " + ChatColor.RED + (landClaims - 1) + ChatColor.GRAY + " claims left. Claimed land is automatically protected, so long as your power is 5 or above.");
                    } else {
                        player.sendMessage(ChatColor.GRAY + " already owns this land.");
                    }
                }else{
                    player.sendMessage(ChatColor.GOLD + owningNation + ChatColor.GRAY + " already owns this land.");
                }
            }else{
                player.sendMessage(ChatColor.GRAY + "Your nation has no land claims left!");
            }
        }else{
            player.sendMessage(ChatColor.GRAY + "You cannot claim land unless you own a nation.");
        }
    }

    public void unclaim(Player player){
        this.data = new nationsConfig(plugin.getPlugin(operator.class));
        this.data1 = new playersConfig(plugin.getPlugin(operator.class));
        DynmapCommonAPIListener.register(dynmapListener);
        com.sk89q.worldedit.entity.Player wgPlayer = WorldGuardPlugin.inst().wrapPlayer(player);
        RegionManager regionManager = worldContainer.get(wgPlayer.getWorld());
        Location location = player.getLocation();
        Chunk playerChunk = location.getChunk();
        Boolean hasNation = data1.getConfig().getBoolean("players." + player.getUniqueId().toString() + ".hasNation");
        Boolean isOwner = data1.getConfig().getBoolean("players." + player.getUniqueId().toString() + ".hasNation");
        String nation = data1.getConfig().getString("players." + player.getUniqueId().toString() + ".nation");
        int landClaims = data.getConfig().getInt("nations." + nation + ".landClaims");
        int totalLandClaims = data.getConfig().getInt("nations." + nation + ".totalLandClaims");
        List<String> claimList = (List<String>) data.getConfig().getList("nations." + nation + ".claimedChunks.claimList");
        List<String> globalChunks = (List<String>) data.getConfig().getList("nations.globalChunks");

        if(hasNation && isOwner){
            Location center = new Location(playerChunk.getWorld(), playerChunk.getX() << 4, 64, playerChunk.getZ() << 4).add(8, 0, 8);
            center.setY(center.getWorld().getHighestBlockYAt(center));
            Location topLeft = center.clone().add(8, 0, 8);

            if(claimList != null){
                boolean claimedLand = false;

                for(int i = 0; i < claimList.size(); i++){
                    String claimID = claimList.get(i);
                    int comparativeX = data.getConfig().getInt("nations." + nation + ".claimedChunks." + claimID + ".topLeftX");
                    int comparativeZ = data.getConfig().getInt("nations." + nation + ".claimedChunks." + claimID + ".topLeftZ");

                    if(comparativeX == topLeft.getX() && comparativeZ == topLeft.getZ()){
                        MarkerSet markerSet = dynmapListener.getMarkerAPI().getMarkerSet(claimID);

                        if(claimList.size() == 1){
                            data.getConfig().set("nations." + nation + ".claimedChunks.claimList", null);
                        }else{
                            claimList.remove(claimID);
                            data.getConfig().set("nations." + nation + ".claimedChunks.claimList", claimList);
                        }

                        claimedLand = true;
                        markerSet.deleteMarkerSet();
                        regionManager.removeRegion(claimID);
                        globalChunks.remove(claimID);
                        data.getConfig().set("nations." + nation + ".landClaims", landClaims + 1);
                        data.getConfig().set("nations." + nation + ".claimedChunks." + claimID, null);
                        data.getConfig().set("nations.globalChunks", globalChunks);
                        data.getConfig().set("nations." + nation + ".totalLandClaims", totalLandClaims - 1);
                        data.saveConfig();
                        player.sendMessage(ChatColor.GRAY + "Land unclaimed! You now have " + ChatColor.RED + (landClaims + 1) + ChatColor.GRAY + " claims left.");
                    }
                }

                if(!claimedLand){
                    player.sendMessage(ChatColor.GOLD + nation + ChatColor.GRAY + " does not own this land.");
                }
            }else{
                player.sendMessage(ChatColor.GOLD + nation + ChatColor.GRAY + " has not claimed land yet.");
            }
        }else{
            player.sendMessage(ChatColor.GRAY + "You cannot unclaim land unless you own a nation.");
        }
    }
}
