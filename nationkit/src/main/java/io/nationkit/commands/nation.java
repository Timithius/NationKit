package io.nationkit.commands;

import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.domains.DefaultDomain;
import com.sk89q.worldguard.protection.flags.Flags;
import com.sk89q.worldguard.protection.flags.RegionGroup;
import com.sk89q.worldguard.protection.flags.RegionGroupFlag;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedCuboidRegion;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import io.nationkit.events.playerToPlayer.playerMailManager;
import io.nationkit.files.nationsConfig;
import io.nationkit.files.playersConfig;
import io.nationkit.listeners.dynmapListener;
import io.nationkit.operator;
import io.nationkit.ui.nationsMenu.mainMenu;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.dynmap.DynmapCommonAPI;
import org.dynmap.DynmapCommonAPIListener;
import org.dynmap.markers.AreaMarker;
import org.dynmap.markers.MarkerSet;

import java.util.*;
import java.util.logging.Logger;

public class nation implements CommandExecutor {
    private dynmapListener dynmapListener = new dynmapListener();
    private playerMailManager pmm = new playerMailManager();
    private mainMenu mm = new mainMenu();
    private operator plugin;
    private nationsConfig data;
    private playersConfig data1;

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
        if(sender instanceof Player){
            Player player = (Player) sender;
            this.data = new nationsConfig(plugin.getPlugin(operator.class));
            this.data1 = new playersConfig(plugin.getPlugin(operator.class));

            if(label.equalsIgnoreCase("nation")){
                if(args.length == 0){
                    mm.mainMenu(player);
                }
                if(args.length == 1){
                    Boolean hasNation = data1.getConfig().getBoolean("players." + player.getUniqueId().toString() + ".hasNation");
                    Boolean isOwner = data1.getConfig().getBoolean("players." + player.getUniqueId().toString() + ".isOwner");
                    String name = data1.getConfig().getString("players." + player.getUniqueId().toString() + ".nation");
                    List members = data.getConfig().getList("nations." + name + ".members");
                    int memberLevel = data.getConfig().getInt("nations." + name + ".memberLevel");
                    String claimColor = data.getConfig().getString("nations." + name + ".claimColor");

                    if(args[0].equalsIgnoreCase("help") || args[0].equalsIgnoreCase("setup") || args[0].equalsIgnoreCase("join") || args[0].equalsIgnoreCase("leave") || args[0].equalsIgnoreCase("message") || args[0].equalsIgnoreCase("name") || args[0].equalsIgnoreCase("invite")
                    || args[0].equalsIgnoreCase("claim") || args[0].equalsIgnoreCase("unclaim")){
                        if(args[0].equalsIgnoreCase("help")) {
                            player.sendMessage(ChatColor.BOLD + "" + ChatColor.GRAY + "==========" + ChatColor.GOLD + "NationKit Help" +
                                    ChatColor.GRAY + "==========" + ChatColor.RESET + "\n" + ChatColor.GRAY + "General Commands:" + "\n" + ChatColor.RED + "1." +
                                    ChatColor.WHITE + " /nation - opens up the Diplomacy Menu" + "\n" + ChatColor.RED + "2." + ChatColor.WHITE + " /nation setup - begin setting up your nation" + "\n" + ChatColor.RED + "3." + ChatColor.WHITE +
                                    " /nation leave - leaves your current nation" + "\n" + ChatColor.RED + "4." + ChatColor.WHITE + " /nation join - join a nation" + "\n" + ChatColor.RED + "5." + ChatColor.WHITE + " /nation banner - set your nation banner" + "\n" +
                                    ChatColor.RED + "6." + ChatColor.WHITE + " /nation message - too much to explain here, try /nation message help" + "\n" +ChatColor.RED + "7." + ChatColor.WHITE + " /nation settings - change your nation settings" + "\n" +
                                    ChatColor.RED + "8." + ChatColor.WHITE + " /nation invite <playername> - invite a player to join your nation");
                        }
                        if(args[0].equalsIgnoreCase("setup")) {
                            player.sendMessage(ChatColor.GRAY + "Usage: /nation setup <nation name>," +
                                    " ex. " + ChatColor.GOLD + "/nation setup Ethereal Warriors");
                        }
                        if(args[0].equalsIgnoreCase("join")) {
                            player.sendMessage(ChatColor.GRAY + "Usage: /nation join <nationname>, ex. " + ChatColor.GOLD + "/nation join Ethereal Warriors");
                        }
                        if(args[0].equalsIgnoreCase("leave")){
                            if(hasNation){
                                if(isOwner){
                                    player.sendMessage(ChatColor.GRAY + "You cannot leave a nation you own. You must disband it in the nation manager, leaving all who followed and trusted you behind. This is a big decision!");
                                }else{
                                    com.sk89q.worldedit.entity.Player wgPlayer = WorldGuardPlugin.inst().wrapPlayer(player);
                                    WorldGuard worldGuard = WorldGuard.getInstance();
                                    RegionContainer worldContainer = worldGuard.getPlatform().getRegionContainer();
                                    RegionManager regionManager = worldContainer.get(wgPlayer.getWorld());
                                    List<String> claimList = (List<String>) data.getConfig().getList("nations." + name + ".claimList");

                                    for(int i = 0; i < memberLevel; i++){
                                        if(members.get(i).equals(player.getUniqueId().toString())){
                                            members.remove(i);

                                            data.getConfig().set("nations." + name + ".members", members);
                                            data.getConfig().set("nations." + name + ".memberLevel", memberLevel - 1);
                                            data.saveConfig();
                                        }
                                    }
                                    if(claimList != null){
                                        for(int i = 0; i < claimList.size(); i++){
                                            String claimID = claimList.get(i);
                                            ProtectedRegion region = regionManager.getRegion(claimID);
                                            DefaultDomain memberDomain = region.getMembers();

                                            memberDomain.removePlayer(player.getUniqueId());
                                        }
                                    }

                                    data1.getConfig().set("players." + player.getUniqueId().toString() + ".hasNation", false);
                                    data1.getConfig().set("players." + player.getUniqueId().toString() + ".nation", null);
                                    data1.saveConfig();

                                    player.sendMessage(ChatColor.GRAY + "You have left " + ChatColor.GOLD + name + ChatColor.GRAY + "!");
                                }
                            }else{
                                player.sendMessage(ChatColor.GRAY + "You are not a citizen of any nation");
                            }
                        }
                        if(args[0].equalsIgnoreCase("message")){
                            player.sendMessage(ChatColor.GRAY + "Unknown usage, try " + ChatColor.GOLD + "/nation message help");
                        }
                        if(args[0].equalsIgnoreCase("name")){
                            player.sendMessage(ChatColor.GRAY + "Usage: /nation name <newname>, ex. " +ChatColor.GOLD + "/nation name New Nation Name");
                        }
                        if(args[0].equalsIgnoreCase("invite")){
                            player.sendMessage(ChatColor.GRAY + "Usage: /nation invite <playername>, ex. " + ChatColor.GOLD + "/nation invite Notch");
                        }
                        if(args[0].equalsIgnoreCase("claim")){
                            if(hasNation && isOwner){
                                DynmapCommonAPIListener.register(dynmapListener);
                                int landClaims = data.getConfig().getInt("nations." + name + ".landClaims");

                                if(landClaims != 0){
                                    int min = 5000;
                                    int max = 500000;
                                    int random_int = (int)Math.floor(Math.random()*(max-min+1)+min);
                                    String claimID;
                                    List<String> claimList = (List<String>) data.getConfig().getList("nations." + name + ".claimedChunks.claimList");
                                    List<String> globalChunks = (List<String>) data.getConfig().getList("nations.globalChunks");

                                    if(claimList == null){
                                        claimID = name + random_int;
                                        claimList = new ArrayList<String>(Collections.singleton(claimID));
                                    }else{
                                        Boolean exists = false;

                                        for(int i = 0; i < claimList.size(); i++){
                                            if(claimList.contains(name + random_int)){
                                                exists = true;
                                            }
                                        }
                                        if(!exists){
                                            claimID = name + random_int;
                                        }else{
                                            claimID = name + random_int + random_int;
                                        }
                                        claimList.add(claimID);
                                    }
                                    if(globalChunks == null){
                                        globalChunks = new ArrayList<String>(Collections.singleton(claimID));
                                    }else{
                                        globalChunks.add(claimID);
                                    }

                                    Location location = player.getLocation();
                                    Chunk playerChunk = location.getChunk();
                                    Location center = new Location(playerChunk.getWorld(), playerChunk.getX() << 4, 64, playerChunk.getZ() << 4).add(8, 0, 8);
                                    center.setY(center.getWorld().getHighestBlockYAt(center));
                                    Location topLeft = center.clone().add(8, 0, 8);
                                    Location topRight = center.clone().add(8, 0, -8);
                                    Location bottomLeft = center.clone().add(-8, 0, -8);

                                    boolean alreadyOwned = false;
                                    boolean alreadyOwnedByNation = false;
                                    String owningNation = null;

                                    for(int i = 0; i < claimList.size(); i++){
                                        String nationClaimID = claimList.get(i);

                                        if (data.getConfig().getInt("nations." + name + ".claimedChunks." + nationClaimID + ".topLeftX") == topLeft.getX()) {
                                            alreadyOwnedByNation = true;
                                        }
                                    }
                                    for(int i = 0; i < globalChunks.size(); i++){
                                        String globalClaimID = globalChunks.get(i);
                                        String nationFromID = globalClaimID.replaceAll("[^a-zA-Z].*", "");

                                        if (data.getConfig().getInt("nations." + nationFromID + ".claimedChunks." + globalClaimID + ".topLeftX") == topLeft.getX()) {
                                            alreadyOwned = true;
                                            owningNation = nationFromID;
                                        }
                                    }

                                    if(!alreadyOwned){
                                        if (!alreadyOwnedByNation) {
                                            com.sk89q.worldedit.entity.Player wgPlayer = WorldGuardPlugin.inst().wrapPlayer(player);
                                            WorldGuard worldGuard = WorldGuard.getInstance();
                                            RegionContainer worldContainer = worldGuard.getPlatform().getRegionContainer();
                                            RegionManager regionManager = worldContainer.get(wgPlayer.getWorld());
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

                                            for (Object member : members) {
                                                UUID uuid = UUID.fromString((String) member);

                                                memberDomain.addPlayer(uuid);
                                            }

                                            region.setFlag(Flags.GREET_TITLE, ChatColor.GOLD + name + ChatColor.WHITE + "'s Land");
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

                                            MarkerSet markerSet = dynmapListener.getMarkerAPI().createMarkerSet(claimID, name, null, true);
                                            double[] x = new double[]{topLeft.getX(), bottomLeft.getX()};
                                            double[] z = new double[]{topLeft.getZ(), topRight.getZ()};
                                            AreaMarker marker = markerSet.createAreaMarker(claimID, name, true,
                                                    player.getLocation().getWorld().getName(), x, z, true);
                                            marker.setFillStyle(0.25, color);
                                            marker.setLineStyle(0, 0, 0);

                                            regionManager.addRegion(region);
                                            data.getConfig().set("nations." + name + ".claimedChunks." + claimID + ".topLeftX", topLeft.getX());
                                            data.getConfig().set("nations." + name + ".claimedChunks.claimList", claimList);
                                            data.getConfig().set("nations.globalChunks", globalChunks);
                                            data.getConfig().set("nations." + name + ".landClaims", landClaims - 1);
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
                        if(args[0].equalsIgnoreCase("unclaim")){
                            if(hasNation && isOwner){
                                DynmapCommonAPIListener.register(dynmapListener);
                                int landClaims = data.getConfig().getInt("nations." + name + ".landClaims");
                                Location location = player.getLocation();
                                Chunk playerChunk = location.getChunk();
                                Location center = new Location(playerChunk.getWorld(), playerChunk.getX() << 4, 64, playerChunk.getZ() << 4).add(8, 0, 8);
                                center.setY(center.getWorld().getHighestBlockYAt(center));
                                Location topLeft = center.clone().add(8, 0, 8);
                                List<String> claimList = (List<String>) data.getConfig().getList("nations." + name + ".claimedChunks.claimList");
                                List<String> globalChunks = (List<String>) data.getConfig().getList("nations.globalChunks");

                                if(claimList != null){
                                    boolean claimedLand = false;

                                    for(int i = 0; i < claimList.size(); i++){
                                        String claimID = claimList.get(i);

                                        if(data.getConfig().getInt("nations." + name + ".claimedChunks." + claimID + ".topLeftX") == topLeft.getX()){
                                            com.sk89q.worldedit.entity.Player wgPlayer = WorldGuardPlugin.inst().wrapPlayer(player);
                                            MarkerSet markerSet = dynmapListener.getMarkerAPI().getMarkerSet(claimID);
                                            WorldGuard worldGuard = WorldGuard.getInstance();
                                            RegionContainer worldContainer = worldGuard.getPlatform().getRegionContainer();
                                            RegionManager regionManager = worldContainer.get(wgPlayer.getWorld());

                                            claimedLand = true;
                                            markerSet.deleteMarkerSet();
                                            regionManager.removeRegion(claimID);
                                            if(claimList.size() == 1){
                                                data.getConfig().set("nations." + name + ".claimedChunks.claimList", null);
                                            }else{
                                                claimList.remove(claimID);
                                                data.getConfig().set("nations." + name + ".claimedChunks.claimList", claimList);
                                            }

                                            globalChunks.remove(claimID);
                                            data.getConfig().set("nations." + name + ".landClaims", landClaims + 1);
                                            data.getConfig().set("nations." + name + ".claimedChunks." + claimID, null);
                                            data.getConfig().set("nations.globalChunks", globalChunks);
                                            data.saveConfig();
                                            player.sendMessage(ChatColor.GRAY + "Land unclaimed! You now have " + ChatColor.RED + (landClaims + 1) + ChatColor.GRAY + " claims left.");
                                        }
                                    }

                                    if(!claimedLand){
                                        player.sendMessage(ChatColor.GOLD + name + ChatColor.GRAY + " does not own this land.");
                                    }
                                }else{
                                    player.sendMessage(ChatColor.GOLD + name + ChatColor.GRAY + " has not claimed land yet.");
                                }
                            }else{
                                player.sendMessage(ChatColor.GRAY + "You cannot unclaim land unless you own a nation.");
                            }
                        }
                    }else{
                        player.sendMessage(ChatColor.GRAY + "Unknown command, try " + ChatColor.GOLD + "/nation help");
                    }
                }
                if(args.length == 2){
                    if(args[0].equalsIgnoreCase("setup") || args[0].equalsIgnoreCase("join") || args[0].equalsIgnoreCase("message") || args[0].equalsIgnoreCase("name") || args[0].equalsIgnoreCase("invite")){
                        Boolean hasNation = data1.getConfig().getBoolean("players." + player.getUniqueId().toString() + ".hasNation");
                        Boolean isOwner = data1.getConfig().getBoolean("players." + player.getUniqueId().toString() + ".isOwner");
                        String name = data1.getConfig().getString("players." + player.getUniqueId().toString() + ".nation");

                        if(args[0].equalsIgnoreCase("setup")){
                            if(!hasNation){
                                String nation = data.getConfig().getString("nations." + args[1]);
                                List<String> owner = List.of(player.getUniqueId().toString());
                                ItemStack defaultBanner = new ItemStack(Material.WHITE_BANNER);
                                Random random = new Random();
                                int choicePick = random.nextInt(4 - 1) + 1;
                                String red = "red";
                                String green = "green";
                                String blue = "blue";
                                String choice = "0";

                                if(choicePick == 1){
                                    choice = red;
                                }
                                if(choicePick == 2){
                                    choice = green;
                                }
                                if(choicePick == 3){
                                    choice = blue;
                                }

                                if(nation == null) {
                                    String newNationName = args[1].replaceAll("[^a-zA-Z].*", "");

                                    data1.getConfig().set("players." + player.getUniqueId().toString() + ".nation", newNationName);
                                    data1.getConfig().set("players." + player.getUniqueId().toString() + ".hasNation", true);
                                    data1.getConfig().set("players." + player.getUniqueId().toString() + ".isOwner", true);
                                    data.getConfig().set("nations." + newNationName + ".members", owner);
                                    data.getConfig().set("nations." + newNationName + ".owner", player.getUniqueId().toString());
                                    data.getConfig().set("nations." + newNationName + ".landClaims", 3);
                                    data.getConfig().set("nations." + newNationName + ".memberLevel", 1);
                                    data.getConfig().set("nations." + newNationName + ".civicPoints", 0);
                                    data.getConfig().set("nations." + newNationName + ".level", 1);
                                    data.getConfig().set("nations." + newNationName + ".xp", 25);
                                    data.getConfig().set("nations." + newNationName + ".power", 10);
                                    data.getConfig().set("nations." + newNationName + ".researchPoints", 1);
                                    data.getConfig().set("nations." + newNationName + ".govType", "&6Tribal");
                                    data.getConfig().set("nations." + newNationName + ".govTypeMat", "CRAFTING_TABLE");
                                    data.getConfig().set("nations." + newNationName + ".leaderType", "Chieftain");
                                    data.getConfig().set("nations." + newNationName + ".banner.material", defaultBanner);
                                    data.getConfig().set("nations." + newNationName + ".privacySetting", "open");
                                    data.getConfig().set("nations." + newNationName + ".claimColor", choice);

                                    player.sendMessage(ChatColor.GRAY + "Your nation " + ChatColor.GOLD + newNationName + ChatColor.GRAY + " has been created. Type " + ChatColor.GOLD + "/nation" + ChatColor.GRAY + " to manage it.");
                                    data.saveConfig();
                                    data1.saveConfig();
                                }else{
                                    player.sendMessage(ChatColor.GRAY + "That nation name already exists.");
                                }
                            }else{
                                player.sendMessage(ChatColor.GRAY + "You must leave your current nation before making a new one");
                            }
                        }
                        if(args[0].equalsIgnoreCase("join")){
                            if(!hasNation){
                                String nation = data.getConfig().getString("nations." + args[1]);
                                String privacySetting = data.getConfig().getString("nations." + args[1] + ".privacySetting");
                                String joinSuccess = ChatColor.GRAY + "You have joined " + ChatColor.GOLD + args[1] + ChatColor.GRAY + ".";

                                if(nation != null) {
                                    if(privacySetting.equalsIgnoreCase("open")) {
                                        com.sk89q.worldedit.entity.Player wgPlayer = WorldGuardPlugin.inst().wrapPlayer(player);
                                        WorldGuard worldGuard = WorldGuard.getInstance();
                                        RegionContainer worldContainer = worldGuard.getPlatform().getRegionContainer();
                                        RegionManager regionManager = worldContainer.get(wgPlayer.getWorld());
                                        List members = data.getConfig().getList("nations." + args[1] + ".members");
                                        List<String> claimList = (List<String>) data.getConfig().getList("nations." + args[1] + ".claimList");

                                        if(!members.contains(player.getUniqueId().toString())){
                                            members.add(player.getUniqueId().toString());
                                        }

                                        for(int i = 0; i < claimList.size(); i++){
                                            String claimID = claimList.get(i);
                                            ProtectedRegion region = regionManager.getRegion(claimID);
                                            DefaultDomain memberDomain = region.getMembers();

                                            memberDomain.addPlayer(player.getUniqueId());
                                        }

                                        int memberLevel = data.getConfig().getInt("nations." + args[1] + ".memberLevel");
                                        int newMemberLevel = memberLevel + 1;

                                        data1.getConfig().set("players." + player.getUniqueId().toString() + ".nation", args[1]);
                                        data1.getConfig().set("players." + player.getUniqueId().toString() + ".hasNation", true);
                                        data1.getConfig().set("players." + player.getUniqueId().toString() + ".isOwner", false);
                                        data.getConfig().set("nations." + args[1] + ".members", members);
                                        data.getConfig().set("nations." + args[1] + ".memberLevel", newMemberLevel);

                                        player.sendMessage(joinSuccess);
                                        data.saveConfig();
                                        data1.saveConfig();
                                    }else{
                                        List invitedMemebers = data.getConfig().getList("nations." + args[1] + ".invitedMembers");

                                        if(invitedMemebers != null && invitedMemebers.contains(player.getUniqueId().toString())){
                                            List members = data.getConfig().getList("nations." + args[1] + ".members");

                                            if (!members.contains(player.getUniqueId().toString())) {
                                                members.add(player.getUniqueId().toString());
                                            }

                                            int memberLevel = data.getConfig().getInt("nations." + args[1] + ".memberLevel");
                                            int newMemberLevel = memberLevel + 1;

                                            data1.getConfig().set("players." + player.getUniqueId().toString() + ".nation", args[1]);
                                            data1.getConfig().set("players." + player.getUniqueId().toString() + ".hasNation", true);
                                            data1.getConfig().set("players." + player.getUniqueId().toString() + ".isOwner", false);
                                            data.getConfig().set("nations." + args[1] + ".members", members);
                                            data.getConfig().set("nations." + args[1] + ".memberLevel", newMemberLevel);

                                            player.sendMessage(joinSuccess);
                                            data.saveConfig();
                                            data1.saveConfig();
                                        }else{
                                            player.sendMessage(ChatColor.GRAY + "Someone from " + ChatColor.GOLD + args[1] + ChatColor.GRAY + " must invite you before you can join!");
                                        }
                                    }
                                }else{
                                    player.sendMessage(ChatColor.GRAY + "That nation does not exist!");
                                }
                            }else{
                                player.sendMessage(ChatColor.GRAY + "You must leave your current nation before joining a new one.");
                            }
                        }
                        if(args[0].equalsIgnoreCase("message")){
                            if(args[1].equalsIgnoreCase("read") || args[1].equalsIgnoreCase("help")){
                                if(args[1].equalsIgnoreCase("read")) {
                                    pmm.playerMailRead(player, true);
                                }
                                if(args[1].equalsIgnoreCase("help")){
                                    player.sendMessage(ChatColor.BOLD + "" + ChatColor.GRAY + "==========" + ChatColor.GOLD + "NationKit Help" +
                                            ChatColor.GRAY + "==========" + ChatColor.RESET + "\n" + ChatColor.GRAY + "Message Commands:" + "\n" + ChatColor.RED + "1." +
                                            ChatColor.WHITE + " /nation message <playername> <message> - sends a message to a player (ex. /nation message Notch hello notch!)" + "\n" + ChatColor.RED + "2." + ChatColor.WHITE + " /nation message read - read your messages" + "\n" +
                                            ChatColor.RED + "3." + ChatColor.WHITE + " /nation message delete <number> - delete a message (ex. /nation message delete 5)");
                                }
                            }else{
                                player.sendMessage(ChatColor.GRAY + "Unknown usage, try " + ChatColor.GOLD + "/nation message help");
                            }
                        }
                        if(args[0].equalsIgnoreCase("banner")){
                            if(args[1].equalsIgnoreCase("create")){
                                if(hasNation && isOwner){
                                    ItemStack itemInMainHand = player.getInventory().getItemInMainHand();

                                    if(itemInMainHand.getType().equals(Material.BLACK_BANNER) || itemInMainHand.getType().equals(Material.WHITE_BANNER) || itemInMainHand.getType().equals(Material.BROWN_BANNER) ||
                                            itemInMainHand.getType().equals(Material.BLUE_BANNER) || itemInMainHand.getType().equals(Material.PURPLE_BANNER) || itemInMainHand.getType().equals(Material.CYAN_BANNER) || itemInMainHand.getType().equals(Material.GRAY_BANNER) ||
                                            itemInMainHand.getType().equals(Material.GREEN_BANNER) || itemInMainHand.getType().equals(Material.LIGHT_BLUE_BANNER) || itemInMainHand.getType().equals(Material.LIGHT_GRAY_BANNER) || itemInMainHand.getType().equals(Material.LIME_BANNER) ||
                                            itemInMainHand.getType().equals(Material.MAGENTA_BANNER) || itemInMainHand.getType().equals(Material.ORANGE_BANNER) || itemInMainHand.getType().equals(Material.PINK_BANNER) || itemInMainHand.getType().equals(Material.RED_BANNER) ||
                                            itemInMainHand.getType().equals(Material.YELLOW_BANNER)){
                                        data.getConfig().set("nations." + name + ".banner.material", itemInMainHand);
                                        data.saveConfig();

                                        player.sendMessage(ChatColor.GRAY + "New banner for " + ChatColor.GOLD + name + ChatColor.GRAY + " set!");
                                    }else{
                                        player.sendMessage(ChatColor.GRAY + "To set a banner for your nation, hold it in your main hand.");
                                    }
                                }else{
                                    player.sendMessage(ChatColor.GRAY + "You must own a nation to set a banner!");
                                }
                            }
                        }
                        if(args[0].equalsIgnoreCase("name")){
                            if(hasNation && isOwner){
                                List memberList = data.getConfig().getList("nations." + name + ".members");
                                int memberLevel = data.getConfig().getInt("nations." + name + ".memberLevel");
                                int civicPoints = data.getConfig().getInt("nations." + name + ".civicPoints");
                                int level = data.getConfig().getInt("nations." + name + ".level");
                                int xp = data.getConfig().getInt("nations." + name + ".xp");
                                int researchPoints = data.getConfig().getInt("nations." + name + ".researchPoints");
                                String govType = ChatColor.translateAlternateColorCodes('&', data.getConfig().getString("nations." + name + ".govType"));
                                String govTypeMat = data.getConfig().getString("nations." + name + ".govType");
                                String leaderType = data.getConfig().getString("nations." + name + ".leaderType");
                                ItemStack banner = data.getConfig().getItemStack("nations." + name + ".banner.material");
                                String privacySetting = data.getConfig().getString("nations." + name + ".privacySetting");

                                data.getConfig().set("nations", args[1]);
                                data.getConfig().set("nations." + args[1] + ".members", memberList);
                                data.getConfig().set("nations." + args[1] + ".memberLevel", memberLevel);
                                data.getConfig().set("nations." + args[1] + ".civicPoints", civicPoints);
                                data.getConfig().set("nations." + args[1] + ".level", level);
                                data.getConfig().set("nations." + args[1] + ".xp", xp);
                                data.getConfig().set("nations." + args[1] + ".researchPoints", researchPoints);
                                data.getConfig().set("nations." + args[1] + ".govType", govType);
                                data.getConfig().set("nations." + args[1] + ".govTypeMat", govTypeMat);
                                data.getConfig().set("nations." + args[1] + ".leaderType", leaderType);
                                data.getConfig().set("nations." + args[1] + ".banner.material", banner);
                                data.getConfig().set("nations." + args[1] + ".privacySetting", privacySetting);
                                data.getConfig().set("nations" + name, null);
                                data.saveConfig();

                                for (int i = 0; i < memberLevel; i++) {
                                    data1.getConfig().set("players." + String.valueOf(memberList.get(i)) + ".nation", args[1]);
                                    data1.saveConfig();
                                }

                                player.sendMessage(ChatColor.GRAY + "Nation name changed to " + ChatColor.GOLD + args[1] + ChatColor.GRAY + "!");
                            }else{
                                player.sendMessage(ChatColor.GRAY + "You must own a nation to set the nation name!");
                            }
                        }
                        if(args[0].equalsIgnoreCase("invite")){
                            String successMessage = ChatColor.GRAY + "You have been invited to join the nation: " + ChatColor.GOLD + name + ChatColor.GRAY + "! Type " + ChatColor.GOLD + "/nation join " + name;

                            if(hasNation){
                                String privacySetting = data.getConfig().getString("nations." + name + ".privacySetting");

                                if(privacySetting.equalsIgnoreCase("open")){
                                    player.sendMessage(ChatColor.GRAY + "Your nation doesn't require invites, anyone can join!");
                                }else{
                                    if(privacySetting.equalsIgnoreCase("limited")){
                                        List invitedMembers = data.getConfig().getList("nations." + name + ".invitedMembers");

                                        if(invitedMembers == null){
                                            Player invitee = Bukkit.getPlayer(Bukkit.getOfflinePlayer(args[1]).getUniqueId());
                                            List<String> newInvitedMembers = List.of(Bukkit.getOfflinePlayer(args[1]).getUniqueId().toString());

                                            if(invitee != null){
                                                Location inviteeLocation = invitee.getLocation();

                                                data.getConfig().set("nations." + name + ".invitedMembers", newInvitedMembers);
                                                data.saveConfig();

                                                player.sendMessage(ChatColor.GOLD + args[1] + ChatColor.GRAY + " has been invited to " + ChatColor.GOLD + name + ChatColor.GRAY + "!");
                                                invitee.sendMessage(successMessage);
                                                invitee.playSound(inviteeLocation, Sound.ENTITY_VILLAGER_YES, 10, 1);
                                            }else{
                                                player.sendMessage(ChatColor.GRAY + "That player does not exist, or has not played on the server before.");
                                            }
                                        }else{
                                            Player invitee = Bukkit.getPlayer(Bukkit.getOfflinePlayer(args[1]).getUniqueId());
                                            invitedMembers.add(UUID.fromString(Bukkit.getOfflinePlayer(args[1]).getUniqueId().toString()));

                                            if(invitee != null) {
                                                Location inviteeLocation = invitee.getLocation();

                                                data.getConfig().set("nations." + name + ".invitedMembers", invitedMembers);
                                                data.saveConfig();

                                                player.sendMessage(ChatColor.GOLD + args[1] + ChatColor.GRAY + " has been invited to " + ChatColor.GOLD + name + ChatColor.GRAY + "!");
                                                invitee.sendMessage(successMessage);
                                                invitee.playSound(inviteeLocation, Sound.ENTITY_VILLAGER_YES, 10, 1);
                                            }else{
                                                player.sendMessage(ChatColor.GRAY + "That player does not exist, or has not played on the server before.");
                                            }
                                        }
                                    }
                                    if(privacySetting.equalsIgnoreCase("closed")){
                                        if(isOwner){
                                            List invitedMembers = data.getConfig().getList("nations." + name + ".invitedMembers");

                                            if(invitedMembers == null){
                                                Player invitee = Bukkit.getPlayer(Bukkit.getOfflinePlayer(args[1]).getUniqueId());
                                                List<String> newInvitedMembers = List.of(Bukkit.getOfflinePlayer(args[1]).getUniqueId().toString());

                                                if(invitee != null){
                                                    Location inviteeLocation = invitee.getLocation();

                                                    data.getConfig().set("nations." + name + ".invitedMembers", newInvitedMembers);
                                                    data.saveConfig();

                                                    player.sendMessage(ChatColor.GOLD + args[1] + ChatColor.GRAY + " has been invited to " + ChatColor.GOLD + name + ChatColor.GRAY + "!");
                                                    invitee.sendMessage(successMessage);
                                                    invitee.playSound(inviteeLocation, Sound.ENTITY_VILLAGER_YES, 10, 1);
                                                }else{
                                                    player.sendMessage(ChatColor.GRAY + "That player does not exist, or has not played on the server before.");
                                                }
                                            }else{
                                                Player invitee = Bukkit.getPlayer(Bukkit.getOfflinePlayer(args[1]).getUniqueId());
                                                invitedMembers.add(Bukkit.getOfflinePlayer(args[1]).getUniqueId().toString());

                                                if(invitee != null){
                                                    Location inviteeLocation = invitee.getLocation();

                                                    data.getConfig().set("nations." + name + ".invitedMembers", invitedMembers);
                                                    data.saveConfig();

                                                    player.sendMessage(ChatColor.GOLD + args[1] + ChatColor.GRAY + " has been invited to " + ChatColor.GOLD + name + ChatColor.GRAY + "!");
                                                    invitee.sendMessage(successMessage);
                                                    invitee.playSound(inviteeLocation, Sound.ENTITY_VILLAGER_YES, 10, 1);
                                                }else{
                                                    player.sendMessage(ChatColor.GRAY + "That player does not exist, or has not played on the server before.");
                                                }
                                            }
                                        }else{
                                            player.sendMessage(ChatColor.GRAY + "Only the owner of this nation can invite members!");
                                        }
                                    }
                                }
                            }else{
                                player.sendMessage(ChatColor.GRAY +  "You must be a citizen or owner of a nation to create invites!");
                            }
                        }
                    }else{
                        player.sendMessage(ChatColor.GRAY + "Unknown command, try " + ChatColor.GOLD +
                                "/nation help");
                    }
                }
                if(args.length >= 3){
                    if(args[0].equalsIgnoreCase("message") || args[0].equalsIgnoreCase("setup") || args[0].equalsIgnoreCase("join") || args[0].equalsIgnoreCase("name")){
                        Boolean hasNation = data1.getConfig().getBoolean("players." + player.getUniqueId().toString() + ".hasNation");
                        Boolean isOwner = data1.getConfig().getBoolean("players." + player.getUniqueId().toString() + ".isOwner");
                        String name = data1.getConfig().getString("players." + player.getUniqueId().toString() + ".nation");

                        if(args[0].equalsIgnoreCase("message")){
                            if(!args[1].equalsIgnoreCase("delete")){
                                StringBuilder sb = new StringBuilder();

                                for(int i = 2; i < args.length; i++){
                                    sb.append(args[i]).append(" ");
                                }

                                String message = sb.toString().trim();

                                UUID target = Bukkit.getOfflinePlayer(args[1]).getUniqueId();

                                pmm.playerMailSend(player, args[1], target, message);
                            }else{
                                int messageToDelete = Integer.parseInt(args[2]);

                                if (messageToDelete != 0) {
                                    pmm.playerMailDelete(player, messageToDelete);
                                } else {
                                    player.sendMessage(ChatColor.GRAY + "You must provide a message number other than 0.");
                                }
                            }
                        }
                        if(args[0].equalsIgnoreCase("setup")){
                            if(!hasNation){
                                String nationNameClean = null;
                                StringBuilder sb = new StringBuilder();

                                for(int i = 1; i < args.length; i++){
                                    sb.append(args[i]).append(" ");
                                }

                                nationNameClean = sb.toString().trim();

                                String nation = data.getConfig().getString("nations." + nationNameClean);
                                List<String> owner = List.of(player.getUniqueId().toString());
                                ItemStack defaultBanner = new ItemStack(Material.WHITE_BANNER);

                                if(nation == null) {
                                    data1.getConfig().set("players." + player.getUniqueId().toString() + ".nation", nationNameClean);
                                    data1.getConfig().set("players." + player.getUniqueId().toString() + ".hasNation", true);
                                    data1.getConfig().set("players." + player.getUniqueId().toString() + ".isOwner", true);
                                    data.getConfig().set("nations", nationNameClean);
                                    data.getConfig().set("nations." + nationNameClean + ".members", owner);
                                    data.getConfig().set("nations." + nationNameClean + ".memberLevel", 1);
                                    data.getConfig().set("nations." + nationNameClean + ".civicPoints", 0);
                                    data.getConfig().set("nations." + nationNameClean + ".level", 1);
                                    data.getConfig().set("nations." + nationNameClean + ".xp", 25);
                                    data.getConfig().set("nations." + nationNameClean + ".researchPoints", 1);
                                    data.getConfig().set("nations." + nationNameClean + ".govType", "&6Tribal");
                                    data.getConfig().set("nations." + nationNameClean + ".govTypeMat", "CRAFTING_TABLE");
                                    data.getConfig().set("nations." + nationNameClean + ".leaderType", "Chieftain");
                                    data.getConfig().set("nations." + nationNameClean + ".banner.material", defaultBanner);
                                    data.getConfig().set("nations." + nationNameClean + ".privacySetting", "open");

                                    player.sendMessage(ChatColor.GRAY + "Your nation " + ChatColor.GOLD + nationNameClean + ChatColor.GRAY + " has been created. Type /nation to manage it!");
                                    data.saveConfig();
                                    data1.saveConfig();
                                }else{
                                    player.sendMessage(ChatColor.GRAY + "That nation name already exists.");
                                }
                            }else{
                                player.sendMessage(ChatColor.GRAY + "You must leave your current nation before making a new one");
                            }
                        }
                        if(args[0].equalsIgnoreCase("join")){
                            if(!hasNation){
                                String nationNameClean = null;
                                StringBuilder sb = new StringBuilder();

                                for(int i = 1; i < args.length; i++){
                                    sb.append(args[i]).append(" ");
                                }

                                nationNameClean = sb.toString().trim();

                                String nation = data.getConfig().getString("nations." + nationNameClean);
                                String privacySetting = data.getConfig().getString("nations." + nationNameClean + ".privacySetting");
                                String joinSuccess = ChatColor.GRAY + "You have joined " + ChatColor.GOLD + nationNameClean + ChatColor.GOLD + ".";

                                if(nation != null) {
                                    if(privacySetting.equalsIgnoreCase("open")) {
                                        List members = data.getConfig().getList("nations." + nationNameClean + ".members");

                                        if (!members.contains(player.getUniqueId().toString())) {
                                            members.add(player.getUniqueId().toString());
                                        }

                                        int memberLevel = data.getConfig().getInt("nations." + nationNameClean + ".memberLevel");
                                        int newMemberLevel = memberLevel + 1;

                                        data1.getConfig().set("players." + player.getUniqueId().toString() + ".nation", nationNameClean);
                                        data1.getConfig().set("players." + player.getUniqueId().toString() + ".hasNation", true);
                                        data1.getConfig().set("players." + player.getUniqueId().toString() + ".isOwner", false);
                                        data.getConfig().set("nations." + nationNameClean + ".members", members);
                                        data.getConfig().set("nations." + nationNameClean + ".memberLevel", newMemberLevel);

                                        player.sendMessage(joinSuccess);
                                        data.saveConfig();
                                        data1.saveConfig();
                                    }else{
                                        List invitedMemebers = data.getConfig().getList("nations." + nationNameClean + ".invitedMembers");

                                        if(invitedMemebers.contains(player.getUniqueId().toString())){
                                            List members = data.getConfig().getList("nations." + nationNameClean + ".members");

                                            if (!members.contains(player.getUniqueId().toString())) {
                                                members.add(player.getUniqueId().toString());
                                            }

                                            int memberLevel = data.getConfig().getInt("nations." + nationNameClean + ".memberLevel");
                                            int newMemberLevel = memberLevel + 1;

                                            data1.getConfig().set("players." + player.getUniqueId().toString() + ".nation", nationNameClean);
                                            data1.getConfig().set("players." + player.getUniqueId().toString() + ".hasNation", true);
                                            data1.getConfig().set("players." + player.getUniqueId().toString() + ".isOwner", false);
                                            data.getConfig().set("nations." + nationNameClean + ".members", members);
                                            data.getConfig().set("nations." + nationNameClean + ".memberLevel", newMemberLevel);

                                            player.sendMessage(joinSuccess);
                                            data.saveConfig();
                                            data1.saveConfig();
                                        }else{
                                            player.sendMessage(ChatColor.GRAY + "Someone from " + ChatColor.GOLD + nation + ChatColor.GRAY + " must invite you before you can join!");
                                        }
                                    }
                                }else{
                                    player.sendMessage(ChatColor.GRAY + "That nation does not exist!");
                                }
                            }else{
                                player.sendMessage(ChatColor.GRAY + "You must leave your current nation before joining a new one.");
                            }
                        }
                        if(args[0].equalsIgnoreCase("name")){
                            if(hasNation && isOwner){
                                String newNationNameClean = null;
                                StringBuilder sb = new StringBuilder();

                                for(int i = 1; i < args.length; i++){
                                    sb.append(args[i]).append(" ");
                                }

                                newNationNameClean = sb.toString().trim();

                                List memberList = data.getConfig().getList("nations." + name + ".members");
                                int memberLevel = data.getConfig().getInt("nations." + name + ".memberLevel");
                                int civicPoints = data.getConfig().getInt("nations." + name + ".civicPoints");
                                int level = data.getConfig().getInt("nations." + name + ".level");
                                int xp = data.getConfig().getInt("nations." + name + ".xp");
                                int researchPoints = data.getConfig().getInt("nations." + name + ".researchPoints");
                                String govType = ChatColor.translateAlternateColorCodes('&', data.getConfig().getString("nations." + name + ".govType"));
                                String govTypeMat = data.getConfig().getString("nations." + name + ".govType");
                                String leaderType = data.getConfig().getString("nations." + name + ".leaderType");
                                ItemStack banner = data.getConfig().getItemStack("nations." + name + ".banner.material");
                                String privacySetting = data.getConfig().getString("nations." + name + ".privacySetting");

                                data.getConfig().set("nations", newNationNameClean);
                                data.getConfig().set("nations." + newNationNameClean + ".members", memberList);
                                data.getConfig().set("nations." + newNationNameClean + ".memberLevel", memberLevel);
                                data.getConfig().set("nations." + newNationNameClean + ".civicPoints", civicPoints);
                                data.getConfig().set("nations." + newNationNameClean + ".level", level);
                                data.getConfig().set("nations." + newNationNameClean + ".xp", xp);
                                data.getConfig().set("nations." + newNationNameClean + ".researchPoints", researchPoints);
                                data.getConfig().set("nations." + newNationNameClean + ".govType", govType);
                                data.getConfig().set("nations." + newNationNameClean + ".govTypeMat", govTypeMat);
                                data.getConfig().set("nations." + newNationNameClean + ".leaderType", leaderType);
                                data.getConfig().set("nations." + newNationNameClean+ ".banner.material", banner);
                                data.getConfig().set("nations." + newNationNameClean + ".privacySetting", privacySetting);
                                data.getConfig().set("nations" + name, null);
                                data.saveConfig();

                                for (int i = 0; i < memberLevel; i++) {
                                    data1.getConfig().set("players." + String.valueOf(memberList.get(i)) + ".nation", newNationNameClean);
                                    data1.saveConfig();
                                }

                                player.sendMessage(ChatColor.GRAY + "Nation name changed to " + ChatColor.GOLD + newNationNameClean + ChatColor.GRAY + "!");
                            }else{
                                player.sendMessage(ChatColor.GRAY + "You own a nation to set the nation name!");
                            }
                        }
                    }else{
                        player.sendMessage(ChatColor.GRAY + "Unknown command, try " + ChatColor.GOLD +
                                "/nation help");
                    }
                }
            }
        }else{
            Logger logger = Bukkit.getLogger();

            logger.info("§6[NationKit] §bYou must a player to run this command!");
        }

        return false;
    }
}
