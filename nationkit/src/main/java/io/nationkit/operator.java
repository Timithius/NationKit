package io.nationkit;

import io.nationkit.commands.debug;
import io.nationkit.commands.nation;
import io.nationkit.events.nationsMenu.*;
import io.nationkit.events.playerToEnviroment.mobKill;
import io.nationkit.events.playerToEnviroment.playerJoin;
import io.nationkit.events.playerToPlayer.playerKill;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class operator extends JavaPlugin {
    @Override
    public void onEnable(){
        this.getServer().getPluginManager().registerEvents(new levelUpMenuClick(), this);
        this.getServer().getPluginManager().registerEvents(new mainMenuClick(), this);
        this.getServer().getPluginManager().registerEvents(new topPowersMenuClick(), this);
        this.getServer().getPluginManager().registerEvents(new helpMenuClick(), this);
        this.getServer().getPluginManager().registerEvents(new nationManagerMenuClick(), this);
        this.getServer().getPluginManager().registerEvents(new governmentMenuClick(), this);
        this.getServer().getPluginManager().registerEvents(new membersMenuClick(), this);
        this.getServer().getPluginManager().registerEvents(new memberProfileMenuClick(), this);
        this.getServer().getPluginManager().registerEvents(new nationSettingsMenuClick(), this);
        this.getServer().getPluginManager().registerEvents(new giftMenuClick(), this);
        this.getServer().getPluginManager().registerEvents(new mobKill(), this);
        this.getServer().getPluginManager().registerEvents(new playerKill(), this);
        this.getServer().getPluginManager().registerEvents(new playerJoin(), this);

        Bukkit.getPluginCommand("nation").setExecutor(new nation());
        Bukkit.getPluginCommand("debug").setExecutor(new debug());
    }

    @Override
    public void onDisable(){

    }
}
