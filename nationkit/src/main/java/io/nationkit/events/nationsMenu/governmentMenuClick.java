package io.nationkit.events.nationsMenu;

import io.nationkit.files.mainConfig;
import io.nationkit.files.nationsConfig;
import io.nationkit.files.playersConfig;
import io.nationkit.operator;
import io.nationkit.ui.nationsMenu.nationManagerMenu;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class governmentMenuClick implements Listener {
    private nationManagerMenu nationManagerMenu = new nationManagerMenu();
    private operator plugin;
    private nationsConfig data;
    private playersConfig data1;
    private mainConfig data2;

    @EventHandler
    public void governmentMenuClick(InventoryClickEvent e){
        Player player = (Player) e.getWhoClicked();
        this.data = new nationsConfig(plugin.getPlugin(operator.class));
        this.data1 = new playersConfig(plugin.getPlugin(operator.class));
        this.data2 = new mainConfig(plugin.getPlugin(operator.class));

        if(e.getView().getTitle().equalsIgnoreCase("Government Type")){
            if(e.getCurrentItem() != null){
                if(e.getCurrentItem().getType().equals(Material.IRON_NUGGET)){
                    nationManagerMenu.nationManagerMenu(player);
                }
                if(e.getCurrentItem().getType().equals(Material.CRAFTING_TABLE)){
                    String name = data1.getConfig().getString("players." + player.getUniqueId().toString() + ".nation");
                    String govTypeMat = data.getConfig().getString("nations." + name + ".govTypeMat");

                    if(!e.getCurrentItem().getLore().contains(ChatColor.YELLOW + "Current government type")){
                        if(Material.valueOf(govTypeMat) != Material.CRAFTING_TABLE) {
                            player.closeInventory();
                            player.sendMessage(ChatColor.GRAY + "Your nation cannot go back to a " + ChatColor.GOLD + "Tribal " + ChatColor.GRAY + "government.");
                        }else{
                            player.closeInventory();
                            player.sendMessage(ChatColor.GRAY + "Your nation is already " + ChatColor.GOLD + "Tribal" + ChatColor.GRAY + "!");
                        }
                    }
                }
                if(e.getCurrentItem().getType().equals(Material.ARROW)){
                    if(!e.getCurrentItem().getLore().contains(ChatColor.YELLOW + "Current government type")){
                        String name = data1.getConfig().getString("players." + player.getUniqueId().toString() + ".nation");
                        String govTypeMat = data.getConfig().getString("nations." + name + ".govTypeMat");
                        int civicPoints = data.getConfig().getInt("nations." + name + ".civicPoints");
                        int civicPointsRequired = data2.getConfig().getInt("settings.civicPointsRequired.cityState");

                        if(civicPoints > civicPointsRequired || civicPoints == civicPointsRequired){
                            if(Material.valueOf(govTypeMat) != Material.ARROW){
                                Location location = player.getLocation();

                                data.getConfig().set("nations." + name + ".civicPoints", civicPoints - civicPointsRequired);
                                data.getConfig().set("nations." + name + ".govType", "&7City State");
                                data.getConfig().set("nations." + name + ".govTypeMat", "ARROW");
                                data.getConfig().set("nations." + name + ".leaderType", "Head of State");

                                data.saveConfig();
                                player.closeInventory();
                                player.sendMessage(ChatColor.GRAY + "GOVERNMENT CHANGED! " + ChatColor.GOLD + name + ChatColor.GRAY +
                                        " is now a " + ChatColor.GRAY + "City State" + ChatColor.GRAY + ".");
                                Bukkit.broadcastMessage(ChatColor.GRAY + "The nation " + ChatColor.GOLD + name + ChatColor.GRAY + " has just upgraded their government to: " + ChatColor.GRAY + "City State");
                                player.playSound(location, Sound.ENTITY_ENDER_DRAGON_GROWL, 10, 1);
                                player.playSound(location, Sound.ENTITY_PLAYER_LEVELUP, 10, 1);
                            }else{
                                player.closeInventory();
                                player.sendMessage(ChatColor.GRAY +"Your nation is already a " + ChatColor.GRAY + "City State" + ChatColor.GRAY + "!");
                            }
                        }
                    }
                }
                if(e.getCurrentItem().getType().equals(Material.WHEAT)){
                    if(!e.getCurrentItem().getLore().contains(ChatColor.YELLOW + "Current government type")){
                        String name = data1.getConfig().getString("players." + player.getUniqueId().toString() + ".nation");
                        String govTypeMat = data.getConfig().getString("nations." + name + ".govTypeMat");
                        int civicPoints = data.getConfig().getInt("nations." + name + ".civicPoints");
                        int civicPointsRequired = data2.getConfig().getInt("settings.civicPointsRequired.feudalState");

                        if(civicPoints > civicPointsRequired || civicPoints == civicPointsRequired){
                            if(Material.valueOf(govTypeMat) != Material.WHEAT){
                                Location location = player.getLocation();

                                data.getConfig().set("nations." + name + ".civicPoints", civicPoints - civicPointsRequired);
                                data.getConfig().set("nations." + name + ".govType", "&6Feudal State");
                                data.getConfig().set("nations." + name + ".govTypeMat", "WHEAT");
                                data.getConfig().set("nations." + name + ".leaderType", "Lord");

                                data.saveConfig();
                                player.closeInventory();
                                player.sendMessage(ChatColor.GRAY + "GOVERNMENT CHANGED! " + ChatColor.GOLD + name + ChatColor.GRAY +
                                        " is now a " + ChatColor.GOLD + "Feudal State" + ChatColor.GRAY + ".");
                                Bukkit.broadcastMessage(ChatColor.GRAY + "The nation " + ChatColor.GOLD + name + ChatColor.GRAY + " has just upgraded their government to: " + ChatColor.GOLD + "Feudal State");
                                player.playSound(location, Sound.ENTITY_ENDER_DRAGON_GROWL, 10, 1);
                                player.playSound(location, Sound.ENTITY_PLAYER_LEVELUP, 10, 1);
                            }else{
                                player.closeInventory();
                                player.sendMessage(ChatColor.GRAY + "Your nation is already a " + ChatColor.GOLD + "Feudal State" + ChatColor.GRAY + "!");
                            }
                        }
                    }
                }
                if(e.getCurrentItem().getType().equals(Material.DIAMOND)){
                    if(!e.getCurrentItem().getLore().contains(ChatColor.YELLOW + "Current government type")){
                        String name = data1.getConfig().getString("players." + player.getUniqueId().toString() + ".nation");
                        String govTypeMat = data.getConfig().getString("nations." + name + ".govTypeMat");
                        int civicPoints = data.getConfig().getInt("nations." + name + ".civicPoints");
                        int civicPointsRequired = data2.getConfig().getInt("settings.civicPointsRequired.monarchy");

                        if(civicPoints > civicPointsRequired || civicPoints == civicPointsRequired){
                            if(Material.valueOf(govTypeMat) != Material.DIAMOND){
                                Location location = player.getLocation();

                                data.getConfig().set("nations." + name + ".civicPoints", civicPoints - civicPointsRequired);
                                data.getConfig().set("nations." + name + ".govType", "&bMonarchy");
                                data.getConfig().set("nations." + name + ".govTypeMat", "DIAMOND");
                                data.getConfig().set("nations." + name + ".leaderType", "Monarch");

                                data.saveConfig();
                                player.closeInventory();
                                player.sendMessage(ChatColor.GRAY + "GOVERNMENT CHANGED! " + ChatColor.GOLD + name + ChatColor.GRAY +
                                        " is now a " + ChatColor.AQUA + "Monarchy" + ChatColor.GRAY + ".");
                                Bukkit.broadcastMessage(ChatColor.GRAY + "The nation " + ChatColor.GOLD + name + ChatColor.GRAY + " has just upgraded their government to: " + ChatColor.AQUA + "Monarchy");
                                player.playSound(location, Sound.ENTITY_ENDER_DRAGON_GROWL, 10, 1);
                                player.playSound(location, Sound.ENTITY_PLAYER_LEVELUP, 10, 1);
                            }else{
                                player.closeInventory();
                                player.sendMessage(ChatColor.GRAY + "Your nation is already a " + ChatColor.AQUA + "Monarchy" + ChatColor.GRAY + "!");
                            }
                        }
                    }
                }
                if(e.getCurrentItem().getType().equals(Material.WRITABLE_BOOK)){
                    if(!e.getCurrentItem().getLore().contains(ChatColor.YELLOW + "Current government type")){
                        String name = data1.getConfig().getString("players." + player.getUniqueId().toString() + ".nation");
                        String govTypeMat = data.getConfig().getString("nations." + name + ".govTypeMat");
                        int civicPoints = data.getConfig().getInt("nations." + name + ".civicPoints");
                        int civicPointsRequired = data2.getConfig().getInt("settings.civicPointsRequired.theocracy");

                        if (civicPoints > civicPointsRequired || civicPoints == civicPointsRequired){
                            if(Material.valueOf(govTypeMat) != Material.WRITABLE_BOOK){
                                Location location = player.getLocation();

                                data.getConfig().set("nations." + name + ".civicPoints", civicPoints - civicPointsRequired);
                                data.getConfig().set("nations." + name + ".govType", "&cTheocracy");
                                data.getConfig().set("nations." + name + ".govTypeMat", "WRITEABLE_BOOK");
                                data.getConfig().set("nations." + name + ".leaderType", "Prophet");

                                data.saveConfig();
                                player.closeInventory();
                                player.sendMessage(ChatColor.GRAY + "GOVERNMENT CHANGED! " + ChatColor.GOLD + name + ChatColor.GRAY +
                                        " is now a " + ChatColor.RED + "Theocracy" + ChatColor.GRAY + ".");
                                Bukkit.broadcastMessage(ChatColor.GRAY + "The nation " + ChatColor.GOLD + name + ChatColor.GRAY + " has just upgraded their government to: " + ChatColor.RED + "Theocracy");
                                player.playSound(location, Sound.ENTITY_ENDER_DRAGON_GROWL, 10, 1);
                                player.playSound(location, Sound.ENTITY_PLAYER_LEVELUP, 10, 1);
                            }else{
                                player.closeInventory();
                                player.sendMessage(ChatColor.GRAY + "Your nation is already a " + ChatColor.RED + "Theocracy" + ChatColor.GRAY + "!");
                            }
                        }
                    }
                }
                if(e.getCurrentItem().getType().equals(Material.EMERALD)){
                    if(!e.getCurrentItem().getLore().contains(ChatColor.YELLOW + "Current government type")){
                        String name = data1.getConfig().getString("players." + player.getUniqueId().toString() + ".nation");
                        String govTypeMat = data.getConfig().getString("nations." + name + ".govTypeMat");
                        int civicPoints = data.getConfig().getInt("nations." + name + ".civicPoints");
                        int civicPointsRequired = data2.getConfig().getInt("settings.civicPointsRequired.merchantRepublic");

                        if(civicPoints > civicPointsRequired || civicPoints == civicPointsRequired){
                            if(Material.valueOf(govTypeMat) != Material.EMERALD){
                                Location location = player.getLocation();

                                data.getConfig().set("nations." + name + ".civicPoints", civicPoints - civicPointsRequired);
                                data.getConfig().set("nations." + name + ".govType", "&aMerchant Republic");
                                data.getConfig().set("nations." + name + ".govTypeMat", "EMERALD");
                                data.getConfig().set("nations." + name + ".leaderType", "Grand Mayor");

                                data.saveConfig();
                                player.closeInventory();
                                player.sendMessage(ChatColor.GRAY + "GOVERNMENT CHANGED! " + ChatColor.GOLD + name + ChatColor.GRAY +
                                        " is now a " + ChatColor.GREEN + "Merchant Republic" + ChatColor.GRAY + ".");
                                Bukkit.broadcastMessage(ChatColor.GRAY + "The nation " + ChatColor.GOLD + name + ChatColor.GRAY + " has just upgraded their government to: " + ChatColor.GREEN + "Merchant Republic");
                                player.playSound(location, Sound.ENTITY_ENDER_DRAGON_GROWL, 10, 1);
                                player.playSound(location, Sound.ENTITY_PLAYER_LEVELUP, 10, 1);
                            }else{
                                player.closeInventory();
                                player.sendMessage(ChatColor.GRAY + "[" + "Your nation is already a " + ChatColor.GREEN + "Merchant Republic" + ChatColor.GRAY + "!");
                            }
                        }
                    }
                }
                if(e.getCurrentItem().getType().equals(Material.QUARTZ_PILLAR)){
                    if(!e.getCurrentItem().getLore().contains(ChatColor.YELLOW + "Current government type")){
                        String name = data1.getConfig().getString("players." + player.getUniqueId().toString() + ".nation");
                        String govTypeMat = data.getConfig().getString("nations." + name + ".govTypeMat");
                        int civicPoints = data.getConfig().getInt("nations." + name + ".civicPoints");
                        int civicPointsRequired = data2.getConfig().getInt("settings.civicPointsRequired.aristocracy");

                        if(civicPoints > civicPointsRequired || civicPoints == civicPointsRequired){
                            if(Material.valueOf(govTypeMat) != Material.QUARTZ_PILLAR){
                                Location location = player.getLocation();

                                data.getConfig().set("nations." + name + ".civicPoints", civicPoints - civicPointsRequired);
                                data.getConfig().set("nations." + name + ".govType", "&fAristocracy");
                                data.getConfig().set("nations." + name + ".govTypeMat", "QUARTZ_PILLAR");
                                data.getConfig().set("nations." + name + ".leaderType", "Aristocracy");

                                data.saveConfig();
                                player.closeInventory();
                                player.sendMessage(ChatColor.GRAY + "GOVERNMENT CHANGED! " + ChatColor.GOLD + name + ChatColor.GRAY +
                                        " is now a " + ChatColor.WHITE + "Aristocracy" + ChatColor.GRAY + ".");
                                Bukkit.broadcastMessage(ChatColor.GRAY + "The nation " + ChatColor.GOLD + name + ChatColor.GRAY + " has just upgraded their government to: " + ChatColor.WHITE + "Aristocracy");
                                player.playSound(location, Sound.ENTITY_ENDER_DRAGON_GROWL, 10, 1);
                                player.playSound(location, Sound.ENTITY_PLAYER_LEVELUP, 10, 1);
                            }else{
                                player.closeInventory();
                                player.sendMessage(ChatColor.GRAY + "Your nation is already a " + ChatColor.WHITE + "Aristocracy" + ChatColor.GRAY + "!");
                            }
                        }
                    }
                }
                if(e.getCurrentItem().getType().equals(Material.WHITE_BANNER)){
                    if(!e.getCurrentItem().getLore().contains(ChatColor.YELLOW + "Current government type")){
                        String name = data1.getConfig().getString("players." + player.getUniqueId().toString() + ".nation");
                        String govTypeMat = data.getConfig().getString("nations." + name + ".govTypeMat");
                        int civicPoints = data.getConfig().getInt("nations." + name + ".civicPoints");
                        int civicPointsRequired = data2.getConfig().getInt("settings.civicPointsRequired.colonialEmpire");

                        if(civicPoints > civicPointsRequired || civicPoints == civicPointsRequired){
                            if(Material.valueOf(govTypeMat) != Material.WHITE_BANNER){
                                Location location = player.getLocation();

                                data.getConfig().set("nations." + name + ".civicPoints", civicPoints - civicPointsRequired);
                                data.getConfig().set("nations." + name + ".govType", "&9Colonial Empire");
                                data.getConfig().set("nations." + name + ".govTypeMat", "WHITE_BANNER");
                                data.getConfig().set("nations." + name + ".leaderType", "Your Majesty");

                                data.saveConfig();
                                player.closeInventory();
                                player.sendMessage(ChatColor.GRAY + "GOVERNMENT CHANGED! " + ChatColor.GOLD + name + ChatColor.GRAY +
                                        " is now a " + ChatColor.BLUE + "Colonial Empire" + ChatColor.GRAY + ".");
                                Bukkit.broadcastMessage(ChatColor.GRAY + "The nation " + ChatColor.GOLD + name + ChatColor.GRAY + " has just upgraded their government to: " + ChatColor.BLUE + "Colonial Empire");
                                player.playSound(location, Sound.ENTITY_ENDER_DRAGON_GROWL, 10, 1);
                                player.playSound(location, Sound.ENTITY_PLAYER_LEVELUP, 10, 1);
                            }else{
                                player.closeInventory();
                                player.sendMessage(ChatColor.GRAY + "Your nation is already a " + ChatColor.BLUE + "Colonial Empire" + ChatColor.GRAY + "!");
                            }
                        }
                    }
                }
                if(e.getCurrentItem().getType().equals(Material.TOTEM_OF_UNDYING)){
                    if(!e.getCurrentItem().getLore().contains(ChatColor.YELLOW + "Current government type")){
                        String name = data1.getConfig().getString("players." + player.getUniqueId().toString() + ".nation");
                        String govTypeMat = data.getConfig().getString("nations." + name + ".govTypeMat");
                        int civicPoints = data.getConfig().getInt("nations." + name + ".civicPoints");
                        int civicPointsRequired = data2.getConfig().getInt("settings.civicPointsRequired.socialistRepublic");

                        if(civicPoints > civicPointsRequired || civicPoints == civicPointsRequired){
                            if(Material.valueOf(govTypeMat) != Material.TOTEM_OF_UNDYING){
                                Location location = player.getLocation();

                                data.getConfig().set("nations." + name + ".civicPoints", civicPoints - civicPointsRequired);
                                data.getConfig().set("nations." + name + ".govType", "&6Socialist Republic");
                                data.getConfig().set("nations." + name + ".govTypeMat", "TOTEM_OF_UNDYING");
                                data.getConfig().set("nations." + name + ".leaderType", "Premier");

                                data.saveConfig();
                                player.closeInventory();
                                player.sendMessage(ChatColor.GRAY + "GOVERNMENT CHANGED! " + ChatColor.GOLD + name + ChatColor.GRAY +
                                        " is now a " + ChatColor.GOLD + "Socialist Republic" + ChatColor.GRAY + ".");
                                Bukkit.broadcastMessage(ChatColor.GRAY + "The nation " + ChatColor.GOLD + name + ChatColor.GRAY + " has just upgraded their government to: " + ChatColor.GOLD + "Socialist Republic");
                                player.playSound(location, Sound.ENTITY_ENDER_DRAGON_GROWL, 10, 1);
                                player.playSound(location, Sound.ENTITY_PLAYER_LEVELUP, 10, 1);
                            }else{
                                player.closeInventory();
                                player.sendMessage(ChatColor.GRAY + "Your nation is already a " + ChatColor.GOLD + "Socialist Republic" + ChatColor.GRAY + "!");
                            }
                        }
                    }
                }
                if(e.getCurrentItem().getType().equals(Material.IRON_PICKAXE)){
                    if(!e.getCurrentItem().getLore().contains(ChatColor.YELLOW + "Current government type")){
                        String name = data1.getConfig().getString("players." + player.getUniqueId().toString() + ".nation");
                        String govTypeMat = data.getConfig().getString("nations." + name + ".govTypeMat");
                        int civicPoints = data.getConfig().getInt("nations." + name + ".civicPoints");
                        int civicPointsRequired = data2.getConfig().getInt("settings.civicPointsRequired.communistState");

                        if (civicPoints > civicPointsRequired || civicPoints == civicPointsRequired){
                            if(Material.valueOf(govTypeMat) != Material.IRON_PICKAXE){
                                Location location = player.getLocation();

                                data.getConfig().set("nations." + name + ".civicPoints", civicPoints - civicPointsRequired);
                                data.getConfig().set("nations." + name + ".govType", "&cCommunist State");
                                data.getConfig().set("nations." + name + ".govTypeMat", "IRON_PICKAXE");
                                data.getConfig().set("nations." + name + ".leaderType", "General Secretary");

                                data.saveConfig();
                                player.closeInventory();
                                player.sendMessage(ChatColor.GRAY +"GOVERNMENT CHANGED! " + ChatColor.GOLD + name + ChatColor.GRAY +
                                        " is now a " + ChatColor.RED + "Communist State" + ChatColor.GRAY + ".");
                                Bukkit.broadcastMessage(ChatColor.GRAY + "The nation " + ChatColor.GOLD + name + ChatColor.GRAY + " has just upgraded their government to: " + ChatColor.RED + "Communist State");
                                player.playSound(location, Sound.ENTITY_ENDER_DRAGON_GROWL, 10, 1);
                                player.playSound(location, Sound.ENTITY_PLAYER_LEVELUP, 10, 1);
                            }else{
                                player.closeInventory();
                                player.sendMessage(ChatColor.GRAY + "Your nation is already a " + ChatColor.RED + "Communist State" + ChatColor.GRAY + "!");
                            }
                        }
                    }
                }
                if(e.getCurrentItem().getType().equals(Material.GOLDEN_HORSE_ARMOR)){
                    if(!e.getCurrentItem().getLore().contains(ChatColor.YELLOW + "Current government type")){
                        String name = data1.getConfig().getString("players." + player.getUniqueId().toString() + ".nation");
                        String govTypeMat = data.getConfig().getString("nations." + name + ".govTypeMat");
                        int civicPoints = data.getConfig().getInt("nations." + name + ".civicPoints");
                        int civicPointsRequired = data2.getConfig().getInt("settings.civicPointsRequired.democracy");

                        if(civicPoints > civicPointsRequired || civicPoints == civicPointsRequired){
                            if(Material.valueOf(govTypeMat) != Material.GOLDEN_HORSE_ARMOR) {
                                Location location = player.getLocation();

                                data.getConfig().set("nations." + name + ".civicPoints", civicPoints - civicPointsRequired);
                                data.getConfig().set("nations." + name + ".govType", "&6Democracy");
                                data.getConfig().set("nations." + name + ".govTypeMat", "GOLDEN_HORSE_ARMOR");
                                data.getConfig().set("nations." + name + ".leaderType", "President");

                                data.saveConfig();
                                player.closeInventory();
                                player.sendMessage(ChatColor.GRAY + "GOVERNMENT CHANGED! " + ChatColor.GOLD + name + ChatColor.GRAY +
                                        " is now a " + ChatColor.GOLD + "Democracy" + ChatColor.GRAY + ".");
                                Bukkit.broadcastMessage(ChatColor.GRAY + "The nation " + ChatColor.GOLD + name + ChatColor.GRAY + " has just upgraded their government to: " + ChatColor.GOLD + "Democracy");
                                player.playSound(location, Sound.ENTITY_ENDER_DRAGON_GROWL, 10, 1);
                                player.playSound(location, Sound.ENTITY_PLAYER_LEVELUP, 10, 1);
                            }else{
                                player.closeInventory();
                                player.sendMessage(ChatColor.GRAY + "Your nation is already a " + ChatColor.GOLD + "Democracy" + ChatColor.GRAY + "!");
                            }
                        }
                    }
                }
                if(e.getCurrentItem().getType().equals(Material.ENCHANTED_GOLDEN_APPLE)){
                    if(!e.getCurrentItem().getLore().contains(ChatColor.YELLOW + "Current government type")){
                        String name = data1.getConfig().getString("players." + player.getUniqueId().toString() + ".nation");
                        String govTypeMat = data.getConfig().getString("nations." + name + ".govTypeMat");
                        int civicPoints = data.getConfig().getInt("nations." + name + ".civicPoints");
                        int civicPointsRequired = data2.getConfig().getInt("settings.civicPointsRequired.federalRepublic");

                        if(civicPoints > civicPointsRequired || civicPoints == civicPointsRequired){
                            if(Material.valueOf(govTypeMat) != Material.ENCHANTED_GOLDEN_APPLE){
                                Location location = player.getLocation();

                                data.getConfig().set("nations." + name + ".civicPoints", civicPoints - civicPointsRequired);
                                data.getConfig().set("nations." + name + ".govType", "&aFederal Republic");
                                data.getConfig().set("nations." + name + ".govTypeMat", "ENCHANTED_GOLDEN_APPLE");
                                data.getConfig().set("nations." + name + ".leaderType", "President");

                                data.saveConfig();
                                player.closeInventory();
                                player.sendMessage(ChatColor.GRAY + "GOVERNMENT CHANGED! " + ChatColor.GOLD + name + ChatColor.GRAY +
                                        " is now a " + ChatColor.GREEN + "Federal Republic" + ChatColor.GRAY + ".");
                                Bukkit.broadcastMessage(ChatColor.GRAY + "The nation " + ChatColor.GOLD + name + ChatColor.GRAY + " has just upgraded their government to: " + ChatColor.GREEN + "Federal Republic");
                                player.playSound(location, Sound.ENTITY_ENDER_DRAGON_GROWL, 10, 1);
                                player.playSound(location, Sound.ENTITY_PLAYER_LEVELUP, 10, 1);
                            }else{
                                player.closeInventory();
                                player.sendMessage(ChatColor.GRAY + "Your nation is already a " + ChatColor.GREEN + "Federal Republic" + ChatColor.GRAY + "!");
                            }
                        }
                    }
                }
                if(e.getCurrentItem().getType().equals(Material.DIAMOND_AXE)){
                    if(!e.getCurrentItem().getLore().contains(ChatColor.YELLOW + "Current government type")){
                        String name = data1.getConfig().getString("players." + player.getUniqueId().toString() + ".nation");
                        String govTypeMat = data.getConfig().getString("nations." + name + ".govTypeMat");
                        int civicPoints = data.getConfig().getInt("nations." + name + ".civicPoints");
                        int civicPointsRequired = data2.getConfig().getInt("settings.civicPointsRequired.totalitarianState");

                        if (civicPoints > civicPointsRequired || civicPoints == civicPointsRequired){
                            if(Material.valueOf(govTypeMat) != Material.DIAMOND_AXE){
                                Location location = player.getLocation();

                                data.getConfig().set("nations." + name + ".civicPoints", civicPoints - civicPointsRequired);
                                data.getConfig().set("nations." + name + ".govType", "&9Totalitarian State");
                                data.getConfig().set("nations." + name + ".govTypeMat", "DIAMOND_AXE");
                                data.getConfig().set("nations." + name + ".leaderType", "Supreme Leader");

                                data.saveConfig();
                                player.closeInventory();
                                player.sendMessage(ChatColor.GRAY + "GOVERNMENT CHANGED! " + ChatColor.GOLD + name + ChatColor.GRAY +
                                        " is now a " + ChatColor.BLUE + "Totalitarian State" + ChatColor.GRAY + ".");
                                Bukkit.broadcastMessage(ChatColor.GRAY + "The nation " + ChatColor.GOLD + name + ChatColor.GRAY + " has just upgraded their government to: " + ChatColor.BLUE + "Totalitarian State");
                                player.playSound(location, Sound.ENTITY_ENDER_DRAGON_GROWL, 10, 1);
                                player.playSound(location, Sound.ENTITY_PLAYER_LEVELUP, 10, 1);
                            }else{
                                player.closeInventory();
                                player.sendMessage(ChatColor.GRAY + "Your nation is already a " + ChatColor.BLUE + "Totalitarian State" + ChatColor.GRAY + "!");
                            }
                        }
                    }
                }
                if(e.getCurrentItem().getType().equals(Material.ENCHANTED_BOOK)){
                    if(!e.getCurrentItem().getLore().contains(ChatColor.YELLOW + "Current government type")){
                        String name = data1.getConfig().getString("players." + player.getUniqueId().toString() + ".nation");
                        String govTypeMat = data.getConfig().getString("nations." + name + ".govTypeMat");
                        int civicPoints = data.getConfig().getInt("nations." + name + ".civicPoints");
                        int civicPointsRequired = data2.getConfig().getInt("settings.civicPointsRequired.neoliberalDemocracy");

                        if (civicPoints > civicPointsRequired || civicPoints == civicPointsRequired){
                            if(Material.valueOf(govTypeMat) != Material.ENCHANTED_BOOK){
                                Location location = player.getLocation();

                                data.getConfig().set("nations." + name + ".civicPoints", civicPoints - civicPointsRequired);
                                data.getConfig().set("nations." + name + ".govType", "&dNeoliberal Democracy");
                                data.getConfig().set("nations." + name + ".govTypeMat", "ENCHANTED_BOOK");
                                data.getConfig().set("nations." + name + ".leaderType", "President");

                                data.saveConfig();
                                player.closeInventory();
                                player.sendMessage(ChatColor.GRAY + "GOVERNMENT CHANGED! " + ChatColor.GOLD + name + ChatColor.GRAY +
                                        " is now a " + ChatColor.LIGHT_PURPLE + "Neoliberal Democracy" + ChatColor.GRAY + ".");
                                Bukkit.broadcastMessage(ChatColor.GRAY + "The nation " + ChatColor.GOLD + name + ChatColor.GRAY + " has just upgraded their government to: " + ChatColor.LIGHT_PURPLE + "Neoliberal Democracy");
                                player.playSound(location, Sound.ENTITY_ENDER_DRAGON_GROWL, 10, 1);
                                player.playSound(location, Sound.ENTITY_PLAYER_LEVELUP, 10, 1);
                            }else{
                                player.closeInventory();
                                player.sendMessage(ChatColor.GRAY + "Your nation is already a " + ChatColor.LIGHT_PURPLE + "Neoliberal Democracy" + ChatColor.GRAY + "!");
                            }
                        }
                    }
                }
            }

            e.setCancelled(true);
        }
    }
}
