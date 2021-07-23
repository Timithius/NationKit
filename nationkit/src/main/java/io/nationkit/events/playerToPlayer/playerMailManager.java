package io.nationkit.events.playerToPlayer;

import io.nationkit.files.playerMailConfig;
import io.nationkit.files.playersConfig;
import io.nationkit.operator;
import net.kyori.adventure.audience.MessageType;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.*;

public class playerMailManager {
    private operator plugin;
    private playerMailConfig data;
    private playersConfig data1;

    public void playerMailSend(Player sender, String targetDisplayName, UUID target, String rawMessage){
        this.data = new playerMailConfig(plugin.getPlugin(operator.class));
        this.data1 = new playersConfig(plugin.getPlugin(operator.class));
        int currentMessages = data.getConfig().getInt("players." + target.toString() + ".currentMessages");
        int newCurrentMessages = currentMessages + 1;
        Boolean hasNation = data1.getConfig().getBoolean("players." + sender.toString() + ".hasNation");
        Player targetPlayer = Bukkit.getPlayer(target);
        Location location = targetPlayer.getLocation();

        if(!(currentMessages >= 5)) {
            if(hasNation) {
                data.getConfig().set("players." + target.toString() + ".currentMessages", newCurrentMessages);
                data.getConfig().set("players." + target.toString() + ".inbox." + newCurrentMessages, rawMessage);
                data.getConfig().set("players." + target.toString() + ".inboxMeta." + newCurrentMessages, sender.getDisplayName());
                data.saveConfig();
                sender.sendMessage(ChatColor.GRAY + "Mail sent to " + ChatColor.GOLD + targetDisplayName + ChatColor.GRAY + "!");

                if (targetPlayer.isOnline()) {
                    targetPlayer.sendMessage(ChatColor.GRAY + "You have received mail from " + ChatColor.GOLD + sender.getDisplayName() + ChatColor.GRAY + "! Type " + ChatColor.GOLD + "/nation message read");
                    targetPlayer.playSound(location, Sound.ENTITY_VILLAGER_YES, 10, 1);
                    targetPlayer.playSound(location, Sound.BLOCK_NOTE_BLOCK_CHIME, 10, 1);
                }
            }else{
                sender.sendMessage(ChatColor.GRAY + "You must be a citizen of a " + ChatColor.GOLD + "nation" + ChatColor.GRAY + " to send & receive messages.");
            }
        }else{
            if(targetPlayer.isOnline()){
                sender.sendMessage(ChatColor.GOLD + sender.getDisplayName() + ChatColor.GRAY + " is trying to send you a message, but your inbox is full. Type" +
                        ChatColor.GOLD + " /nation message read " + ChatColor.GRAY + "to begin receiving messages!");
            }

            sender.sendMessage(ChatColor.GRAY + "The inbox of " + ChatColor.GOLD + targetDisplayName + ChatColor.GRAY + " is full, they will not get this message!");
        }
    }

    public void playerMailRead(Player reader, Boolean seeMessages){
        this.data = new playerMailConfig(plugin.getPlugin(operator.class));
        this.data1 = new playersConfig(plugin.getPlugin(operator.class));
        Boolean hasNation = data1.getConfig().getBoolean("players." + reader.getUniqueId().toString() + ".hasNation");
        String name = data1.getConfig().getString("players." + reader.getUniqueId().toString() + ".nation");
        int currentMessages = data.getConfig().getInt("players." + reader.getUniqueId().toString() + ".currentMessages");

        if(!seeMessages){
            reader.sendMessage(ChatColor.GRAY + "You have " + ChatColor.RED + currentMessages + ChatColor.GRAY + " messages. To read them, type " +
                    ChatColor.GOLD + "/nation message read");
        }else{
            if(hasNation){
                reader.sendMessage(ChatColor.GRAY + "==========" + ChatColor.GOLD + "" + ChatColor.BOLD + name + " Mail" + ChatColor.RESET + "" + ChatColor.GRAY + "==========");
                reader.sendMessage(ChatColor.AQUA + "You have " + ChatColor.RED + currentMessages + ChatColor.AQUA + " messages.");
                for (int i = 1; i <= currentMessages; i++) {
                    String message = data.getConfig().getString("players." + reader.getUniqueId().toString() + ".inbox." + i);
                    String from = data.getConfig().getString("players." + reader.getUniqueId().toString() + ".inboxMeta." + i);

                    reader.sendMessage(ChatColor.RED + String.valueOf(i) + ChatColor.LIGHT_PURPLE + ". " + ChatColor.WHITE + "From: " +
                            ChatColor.AQUA + from + ChatColor.LIGHT_PURPLE + "\n" + ChatColor.WHITE + ChatColor.translateAlternateColorCodes('&', message) + ChatColor.GRAY + "\n" + "====================");
                }
                reader.sendMessage(ChatColor.GRAY + "To delete a message, type " + ChatColor.GOLD + "/nation message delete <number>");
            }else{
                reader.sendMessage(ChatColor.GRAY + "You must be a citizen of a " + ChatColor.GOLD + "nation" + ChatColor.GRAY + " to send & receive messages.");
            }
        }
    }

    public void playerMailDelete(Player reader, int messageNumber){
        this.data = new playerMailConfig(plugin.getPlugin(operator.class));
        this.data1 = new playersConfig(plugin.getPlugin(operator.class));
        Boolean hasnation = data1.getConfig().getBoolean("players." + reader.getUniqueId().toString() + ".hasNation");
        int currentMessages = data.getConfig().getInt("players." + reader.getUniqueId().toString() + ".currentMessages");

        if(hasnation){
            if(currentMessages != 0){
                String messageToDelete = data.getConfig().getString("players." + reader.getUniqueId().toString() + ".inbox." + messageNumber);
                String messageMetaToDelete = data.getConfig().getString("players." + reader.getUniqueId().toString() + ".inboxMeta." + messageNumber);

                if(messageToDelete != null && messageMetaToDelete != null){
                    data.getConfig().set("players." + reader.getUniqueId().toString() + ".inbox." + messageNumber, null);
                    data.getConfig().set("players." + reader.getUniqueId().toString() + ".inboxMeta." + messageNumber, null);

                    for(int i = messageNumber; i <= currentMessages; i++){
                        String oldMessageString = data.getConfig().getString("players." + reader.getUniqueId().toString() + ".inbox." + i);
                        String oldMessageMetaString = data.getConfig().getString("players." + reader.getUniqueId().toString() + ".inboxMeta." + i);

                        if(oldMessageString != null) {
                            int newMessageNumber = i - 1;

                            data.getConfig().set("players." + reader.getUniqueId().toString() + ".inbox." + i, null);
                            data.getConfig().set("players." + reader.getUniqueId().toString() + ".inbox." + newMessageNumber, oldMessageString);
                            data.saveConfig();
                        }
                        if(oldMessageMetaString != null){
                            int newMessageNumber = i - 1;

                            data.getConfig().set("players." + reader.getUniqueId().toString() + ".inboxMeta." + i, null);
                            data.getConfig().set("players." + reader.getUniqueId().toString() + ".inboxMeta." + newMessageNumber, oldMessageMetaString);
                            data.saveConfig();
                        }
                    }
                    data.getConfig().set("players." + reader.getUniqueId().toString() + ".currentMessages", currentMessages - 1);
                    data.saveConfig();
                    reader.sendMessage(ChatColor.GRAY + "Message " + ChatColor.RED + messageNumber + ChatColor.GRAY + " deleted!");
                }else{
                    reader.sendMessage(ChatColor.GRAY + "Mail slot " + ChatColor.RED + messageNumber + ChatColor.GRAY + " is empty.");
                }
            }else{
                reader.sendMessage(ChatColor.GRAY + "You currently have no messages.");
            }
        }else{
            reader.sendMessage(ChatColor.GRAY + "You must be a citizen of a " + ChatColor.GOLD + "nation" + ChatColor.GRAY + " to send & receive messages.");
        }
    }
}
