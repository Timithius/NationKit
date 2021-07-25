package io.nationkit.commands;

import io.nationkit.files.nationsConfig;
import io.nationkit.files.playersConfig;
import io.nationkit.listeners.dynmapListener;
import io.nationkit.operator;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class debug implements CommandExecutor {
    private operator plugin;
    private dynmapListener dynmapListener = new dynmapListener();
    private nationsConfig data;
    private playersConfig data1;

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
        Player player = (Player) sender;

        player.sendMessage(ChatColor.YELLOW + "Dee'd all the bugs. Squeaky clean!");

        return false;
    }

}