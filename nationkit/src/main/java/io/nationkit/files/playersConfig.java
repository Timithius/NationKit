package io.nationkit.files;

import io.nationkit.operator;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;

public class playersConfig {
    private operator plugin;
    private FileConfiguration dataConfig;
    private File configFile;

    public playersConfig(operator plugin){
        this.plugin = plugin;

        initConfig();
    }

    public void reloadConfig(){
        if(this.configFile == null){
            this.configFile = new File(
                    this.plugin.getDataFolder(), "players.yml"
            );
        }

        this.dataConfig = YamlConfiguration.loadConfiguration(this.configFile);

        InputStream defaultStream = this.plugin.getResource("players.yml");

        if(defaultStream != null){
            YamlConfiguration defaultConfig = YamlConfiguration.loadConfiguration(
                    new InputStreamReader(defaultStream)
            );

            this.dataConfig.setDefaults(defaultConfig);
        }
    }

    public FileConfiguration getConfig(){
        if(this.dataConfig == null){
            reloadConfig();
        }

        return this.dataConfig;
    }

    public void saveConfig(){
        if(this.dataConfig == null || this.configFile == null){
            return;
        }

        try{
            this.getConfig().save(this.configFile);
        }catch (Exception e){
            this.plugin.getLogger().log(Level.SEVERE, "§6[NationKit] §bCould not save config, does the server have file permissions?");
        }
    }

    public void initConfig(){
        if(this.getConfig() == null){
            this.configFile = new File(this.plugin.getDataFolder(), "players.yml");
        }

        if(!this.configFile.exists()){
            this.plugin.saveResource("players.yml", false);
        }
    }
}
