package io.nationkit.commands;

import io.nationkit.systems.players.playerMailManager;
import io.nationkit.files.nationsConfig;
import io.nationkit.files.playersConfig;
import io.nationkit.operator;
import io.nationkit.systems.nation.actions.claimUnclaim;
import io.nationkit.systems.nation.actions.joinLeave;
import io.nationkit.systems.nation.actions.setupDisband;
import io.nationkit.systems.nation.nationSettings;
import io.nationkit.ui.nationsMenu.mainMenu;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.*;
import java.util.logging.Logger;

public class nation implements CommandExecutor {
    private playerMailManager playerMailManager = new playerMailManager();
    private mainMenu mainMenu = new mainMenu();
    private joinLeave joinLeave = new joinLeave();
    private claimUnclaim claimUnclaim = new claimUnclaim();
    private setupDisband setupDisband = new setupDisband();
    private nationSettings nationSettings = new nationSettings();
    private operator plugin;
    private nationsConfig data;
    private playersConfig data1;

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
        if(sender instanceof Player){
            this.data = new nationsConfig(plugin.getPlugin(operator.class));
            this.data1 = new playersConfig(plugin.getPlugin(operator.class));
            Player player = (Player) sender;

            if(label.equalsIgnoreCase("nation")){
                if(args.length == 0){
                    mainMenu.mainMenu(player);
                }
                if(args.length == 1){
                    if(args[0].equalsIgnoreCase("help") || args[0].equalsIgnoreCase("setup") || args[0].equalsIgnoreCase("join") || args[0].equalsIgnoreCase("leave") || args[0].equalsIgnoreCase("message") || args[0].equalsIgnoreCase("setname") || args[0].equalsIgnoreCase("invite")
                            || args[0].equalsIgnoreCase("claim") || args[0].equalsIgnoreCase("unclaim") || args[0].equalsIgnoreCase("setbanner")){
                        if(args[0].equalsIgnoreCase("help")){
                            player.sendMessage(ChatColor.BOLD + "" + ChatColor.GRAY + "==========" + ChatColor.GOLD + "NationKit Help" +
                                    ChatColor.GRAY + "==========" + ChatColor.RESET + "\n" + ChatColor.GRAY + "General Commands:" + "\n" + ChatColor.RED + "1." +
                                    ChatColor.WHITE + " /nation - opens up the Diplomacy Menu" + "\n" + ChatColor.RED + "2." + ChatColor.WHITE + " /nation setup - begin setting up your nation" + "\n" + ChatColor.RED + "3." + ChatColor.WHITE +
                                    " /nation leave - leaves your current nation" + "\n" + ChatColor.RED + "4." + ChatColor.WHITE + " /nation join - join a nation" + "\n" + ChatColor.RED + "5." + ChatColor.WHITE + " /nation setbanner - set the nation banner" + "\n" +
                                    ChatColor.RED + "6." + ChatColor.WHITE + " /nation setname - rename your nation" + "\n" + ChatColor.RED + "7." + ChatColor.WHITE + " /nation message - too much to explain here, try /nation message help" + "\n" + ChatColor.RED + "8." + ChatColor.WHITE + " /nation settings - change your nation settings" + "\n" +
                                    ChatColor.RED + "9." + ChatColor.WHITE + " /nation invite <playername> - invite a player to join your nation");
                        }
                        if(args[0].equalsIgnoreCase("setup")){
                            player.sendMessage(ChatColor.GRAY + "Usage: /nation setup <nation name>," +
                                    " ex. " + ChatColor.GOLD + "/nation setup Ethereal Warriors");
                        }
                        if(args[0].equalsIgnoreCase("join")){
                            player.sendMessage(ChatColor.GRAY + "Usage: /nation join <nationname>, ex. " + ChatColor.GOLD + "/nation join Ethereal Warriors");
                        }
                        if(args[0].equalsIgnoreCase("leave")){
                            joinLeave.leave(player);
                        }
                        if(args[0].equalsIgnoreCase("message")){
                            player.sendMessage(ChatColor.GRAY + "Unknown usage, try " + ChatColor.GOLD + "/nation message help");
                        }
                        if(args[0].equalsIgnoreCase("setname")){
                            player.sendMessage(ChatColor.GRAY + "Usage: /nation setname <newname>, ex. " + ChatColor.GOLD + "/nation setname New Nation Name");
                        }
                        if(args[0].equalsIgnoreCase("invite")){
                            player.sendMessage(ChatColor.GRAY + "Usage: /nation invite <playername>, ex. " + ChatColor.GOLD + "/nation invite Notch");
                        }
                        if(args[0].equalsIgnoreCase("claim")){
                            claimUnclaim.claim(player);
                        }
                        if(args[0].equalsIgnoreCase("unclaim")){
                            claimUnclaim.unclaim(player);
                        }
                        if(args[0].equalsIgnoreCase("setbanner")){
                            ItemStack itemStack = player.getInventory().getItemInMainHand();

                            nationSettings.setBanner(player, itemStack);
                        }
                    }else{
                        player.sendMessage(ChatColor.GRAY + "Unknown command, try " + ChatColor.GOLD + "/nation help");
                    }
                }
                if(args.length == 2){
                    if(args[0].equalsIgnoreCase("setup") || args[0].equalsIgnoreCase("join") || args[0].equalsIgnoreCase("message") || args[0].equalsIgnoreCase("setname") || args[0].equalsIgnoreCase("invite")){
                        if(args[0].equalsIgnoreCase("setup")){
                            setupDisband.setup(player, args);
                        }
                        if(args[0].equalsIgnoreCase("join")){
                            joinLeave.join(player, args);
                        }
                        if(args[0].equalsIgnoreCase("message")){
                            if(args[1].equalsIgnoreCase("read") || args[1].equalsIgnoreCase("help")){
                                if(args[1].equalsIgnoreCase("read")){
                                    playerMailManager.playerMailRead(player, true);
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
                        if(args[0].equalsIgnoreCase("setname")){
                            nationSettings.setName(player, args);
                        }
                        if(args[0].equalsIgnoreCase("invite")){
                            Player invitee = Bukkit.getPlayer(Bukkit.getOfflinePlayer(args[1]).getUniqueId());

                            nationSettings.invitePlayer(player, invitee);
                        }
                    }else{
                        player.sendMessage(ChatColor.GRAY + "Unknown command, try " + ChatColor.GOLD +
                                "/nation help");
                    }
                }
                if(args.length >= 3){
                    if(args[0].equalsIgnoreCase("message")){
                        if(!args[1].equalsIgnoreCase("delete")){
                            StringBuilder sb = new StringBuilder();

                            for(int i = 2; i < args.length; i++){
                                sb.append(args[i]).append(" ");
                            }

                            String message = sb.toString().trim();

                            UUID target = Bukkit.getOfflinePlayer(args[1]).getUniqueId();

                            playerMailManager.playerMailSend(player, args[1], target, message);
                        }else{
                           int messageToDelete = Integer.parseInt(args[2]);

                           if (messageToDelete != 0) {
                                playerMailManager.playerMailDelete(player, messageToDelete);
                           }else{
                                player.sendMessage(ChatColor.GRAY + "You must provide a message number other than 0.");
                           }
                        }
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