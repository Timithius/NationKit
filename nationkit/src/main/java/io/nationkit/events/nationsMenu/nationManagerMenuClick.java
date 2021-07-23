package io.nationkit.events.nationsMenu;

import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import io.nationkit.files.nationsConfig;
import io.nationkit.files.playersConfig;
import io.nationkit.listeners.dynmapListener;
import io.nationkit.operator;
import io.nationkit.ui.nationsMenu.*;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.dynmap.DynmapCommonAPIListener;
import org.dynmap.markers.MarkerSet;

import java.util.List;

public class nationManagerMenuClick implements Listener {
    private io.nationkit.listeners.dynmapListener dynmapListener = new dynmapListener();
    private mainMenu mm = new mainMenu();
    private nationManagerMenu nmm = new nationManagerMenu();
    private governmentMenu gm = new governmentMenu();
    private levelUpMenu lum = new levelUpMenu();
    private membersMenu memm = new membersMenu();
    private nationSettingsMenu nsm = new nationSettingsMenu();
    private operator plugin;
    private nationsConfig data;
    private playersConfig data1;

    @EventHandler
    public void nationManagerMenuClick(InventoryClickEvent e){
        Player player = (Player) e.getWhoClicked();
        this.data = new nationsConfig(plugin.getPlugin(operator.class));
        this.data1 = new playersConfig(plugin.getPlugin(operator.class));

        if(e.getView().getTitle().equalsIgnoreCase("Nation View")){
            if(e.getCurrentItem() != null) {
                if(e.getCurrentItem().getType().equals(Material.IRON_NUGGET)){
                    mm.mainMenu(player);
                }
                if(e.getCurrentItem().getType().equals(Material.NETHER_STAR)){
                    player.closeInventory();
                    player.sendMessage(ChatColor.GRAY + "If you're interested in setting up a nation, type" + ChatColor.GOLD + " /nation setup");
                    player.sendMessage(ChatColor.GRAY + "To join a nation, type" + ChatColor.GOLD + " /nation join <nation>");
                }
                if(e.getCurrentItem().getType().equals(Material.LECTERN)){
                    gm.governmentMenu(player);
                }
                if(e.getCurrentItem().getType().equals(Material.BARRIER)){
                    String name = data1.getConfig().getString("players." + player.getUniqueId().toString() + ".nation");
                    Boolean hasNation = data1.getConfig().getBoolean("players." + player.getUniqueId().toString() + ".hasNation");
                    Boolean isOwner = data1.getConfig().getBoolean("players." + player.getUniqueId().toString() + ".isOwner");

                    if(hasNation && !isOwner){
                        player.closeInventory();
                        data1.getConfig().set("players." + player.getUniqueId().toString() + ".nation", null);
                        data1.getConfig().set("players." + player.getUniqueId().toString() + ".hasNation", false);
                        data1.getConfig().set("players." + player.getUniqueId().toString() + ".isOwner", false);
                        data.getConfig().set("nations." + name + ".members." + player.getUniqueId().toString(), null);

                        data.saveConfig();
                        data1.saveConfig();
                        player.sendMessage(ChatColor.GRAY + "You have left the nation of " + ChatColor.GOLD + name + ChatColor.GRAY + ".");
                    }
                    if(hasNation && isOwner){
                        com.sk89q.worldedit.entity.Player wgPlayer = WorldGuardPlugin.inst().wrapPlayer(player);
                        DynmapCommonAPIListener.register(dynmapListener);
                        List<String> claimList = (List<String>) data.getConfig().getList("nations." + name + ".claimedChunks.claimList");
                        List<String> globalChunks = (List<String>) data.getConfig().getList("nations.globalChunks");
                        WorldGuard worldGuard = WorldGuard.getInstance();
                        RegionContainer worldContainer = worldGuard.getPlatform().getRegionContainer();
                        RegionManager regionManager = worldContainer.get(wgPlayer.getWorld());

                        if(claimList != null) {
                            for (String claimID : claimList) {
                                MarkerSet markerSet = dynmapListener.getMarkerAPI().getMarkerSet(claimID);

                                markerSet.deleteMarkerSet();
                                regionManager.removeRegion(claimID);
                            }
                            for(String claimID : claimList){
                                globalChunks.remove(claimID);
                                data.getConfig().set("nations.globalChunks", globalChunks);
                                data.saveConfig();
                            }
                        }


                        player.closeInventory();
                        data1.getConfig().set("players." + player.getUniqueId().toString() + ".nation", null);
                        data1.getConfig().set("players." + player.getUniqueId().toString() + ".hasNation", false);
                        data1.getConfig().set("players." + player.getUniqueId().toString() + ".isOwner", false);
                        data.getConfig().set("nations." + name, null);

                        data.saveConfig();
                        data1.saveConfig();
                        player.sendMessage(ChatColor.GRAY + "You have disbanded the nation of " + ChatColor.GOLD + name + ChatColor.GRAY + ".");
                    }
                }
                if(e.getCurrentItem().getType().equals(Material.PLAYER_HEAD)){
                    memm.membersMenu(player);
                }
                if(e.getCurrentItem().getType().equals(Material.COMMAND_BLOCK)){
                    nsm.nationSettingsMenu(player);
                }
            }

            e.setCancelled(true);
        }
    }
}
