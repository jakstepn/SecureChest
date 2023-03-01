package io.github.jakstepn;

import io.github.jakstepn.Factory.MainFactory;
import io.github.jakstepn.Gui.GuiHandler;
import io.github.jakstepn.Items.ItemManager;
import io.github.jakstepn.Listeners.ChestEventHandler;
import io.github.jakstepn.Listeners.PlayerEventHandler;
import io.github.jakstepn.Listeners.WorldEventHandler;
import io.github.jakstepn.Passwords.Listeners.PasswordEventHandler;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nullable;
import java.io.File;
import java.io.IOException;

public class Main extends JavaPlugin {

    private File customConfigFile;
    private FileConfiguration customConfig;
    private static String folderPath;

    public String getFolderPath() {
        return folderPath;
    }

    @Override
    public void onEnable() {
        new MainFactory().init(this);
        createCustomConfig();
        createChestFolder();
        folderPath = this.getDataFolder() + "/Chests/";
        ItemManager.addChestRecipes();
        getServer().getPluginManager().registerEvents(new PlayerEventHandler(this), this);
        getServer().getPluginManager().registerEvents(new ChestEventHandler(this), this);
        getServer().getPluginManager().registerEvents(new WorldEventHandler(this), this);
        getServer().getPluginManager().registerEvents(new GuiHandler(this), this);
        getServer().getPluginManager().registerEvents(new PasswordEventHandler(this), this);
    }

    @Override
    public void onDisable() {
    }

    private void createCustomConfig() {
        customConfigFile = new File(getDataFolder(), "custom.yml");
        if(!customConfigFile.exists()) {
            customConfigFile.getParentFile().mkdirs();
            saveResource("custom.yml", false);
        }

        customConfig = new YamlConfiguration();
        try {
            customConfig.load(customConfigFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    private void createChestFolder() {
        new File(getDataFolder() + "/Chests").mkdir();
    }
}
